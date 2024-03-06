package com.vam.whitecoats.ui.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.ui.fragments.NotifyConnectsFragment;
import com.vam.whitecoats.utils.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class NotificationsActivity extends BaseActionBarActivity {
    public static final String TAG = NotificationsActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private CoordinatorLayout timeLineContentLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private FeedNotificationFragment feedNotificationFragment;
    private NotifyConnectsFragment notifyConnectsFragment;


    private Realm realm;
    private RealmManager realmManager;
    private int connectslist;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private UpdateUIReceiver updateUIReceiver;
    TextView settings_button;
    private boolean customBackButton = false;

    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private ViewPager2 mViewPager;
    private List<String> mFragmentTitleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_pager);
        _initialize();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "InAppNotificationNetworkTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), NotificationsActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupViewPager(mViewPager);
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("TAB_NUMBER")) {
            if (getIntent().getExtras().getInt("TAB_NUMBER") == 1) {
                mViewPager.setCurrentItem(1);
            }
        }
        editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
    }
    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
//Start
    private void setupViewPager(ViewPager2 mViewPager) {
        Log.i(TAG, "NotificationsetupViewPager()");
        feedNotificationFragment = FeedNotificationFragment.newInstance("", "");
        notifyConnectsFragment = NotifyConnectsFragment.newInstance("", "");

        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(feedNotificationFragment);
        mFragmentList.add(notifyConnectsFragment);

        mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add(getString(R.string.tab_updates));
        mFragmentTitleList.add(getString(R.string.tab_network));

        ViewPagerLoadFragmentsAdapter mNotificationPageAdapter = new ViewPagerLoadFragmentsAdapter(this,
                mFragmentList, mFragmentTitleList,"fromActivity");

        mViewPager.setAdapter(mNotificationPageAdapter);

        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> {
            tab.setText(mFragmentTitleList.get(position));
        }).attach();

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled - " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected - " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);
            }
        });

    }
    //End

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();

        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancelAll();
        App_Application.setNumUnreadMessages(0);
        updateUIReceiver = new UpdateUIReceiver();
        LocalBroadcastManager.getInstance(App_Application.getInstance()).registerReceiver(updateUIReceiver, new IntentFilter("NotificationCount"));
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("NotificationsDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();
    }


    private void _initialize() {
        try {

            realmManager = new RealmManager(this);
            realm = Realm.getDefaultInstance();
            timeLineContentLayout = (CoordinatorLayout) findViewById(R.id.contentLayout);
            appBarLayout = (AppBarLayout) findViewById(R.id.timeLineAppBarLayout);
            toolbar = (Toolbar) findViewById(R.id.toolbar_community);
            mViewPager = (ViewPager2) findViewById(R.id.timeLineViewpager2);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            AppBarLayout.LayoutParams params =
                    (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);


            mInflater = LayoutInflater.from(NotificationsActivity.this);
            mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
            TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
            settings_button = (TextView) mCustomView.findViewById(R.id.settings_button);
            //back_button.setImageResource(R.drawable.ic_back);
            mTitleTextView.setText(getString(R.string.str_notification_heading));
            next_button.setVisibility(View.GONE);
            settings_button.setVisibility(View.VISIBLE);
            settings_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "NotificationSettingTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), NotificationsActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent NotificationSettingsActivity = new Intent(mContext, NotificationSettingsActivity.class);
                    startActivity(NotificationSettingsActivity);
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presentShowcaseSequence();
                }
            }, 1000);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                toolbar.addView(mCustomView);
            }
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            //actionBar.setCustomView(mCustomView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //coach mark for Notification Settings
    private void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 0) {
            MaterialShowcaseView.resetSingleUse(NotificationsActivity.this, "Sequence_CreateCaseroom");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_CreateCaseroom");
            sequence.setConfig(config);
            if (!editor.getBoolean("notification_settings_icon", false)) {
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(this)
                                .setTarget(settings_button)
                                .setDismissText("Got it")
                                .setDismissTextColor(Color.parseColor("#00a76d"))
                                .setMaskColour(Color.parseColor("#CC231F20"))
                                .setContentText(R.string.tap_to_coach_mark_notification_settings).setListener(new IShowcaseListener() {
                            @Override
                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                editor.edit().putBoolean("notification_settings_icon", true).commit();
                            }

                            @Override
                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                            }
                        })
                                .withCircleShape()
                                .build()
                );
            }
            sequence.start();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    private class UpdateUIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {

                //connectslist = realmManager.getNotificationCount(realm);
                connectslist = realmManager.getNotificationNetworkCount(realm);
                /*if (connectslist != 0) {
                    connects_count.setText("" + connectslist);
                } else {
                    connects_count.setText("");
                }*/

               /* groupslist = realmManager.getGroupNotificationCount(realm);
                if (groupslist != 0) {
                    groups_count.setText("" + groupslist);
                } else {
                    groups_count.setText("");
                }

                caseroomslist = realmManager.getCRNotificationCount(realm);
                if (caseroomslist != 0) {
                    caserooms_count.setText("" + caseroomslist);
                } else {
                    caserooms_count.setText("");
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("NotificationsBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
