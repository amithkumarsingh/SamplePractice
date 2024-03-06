package com.vam.whitecoats.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.RevealBackgroundView;
import com.vam.whitecoats.ui.fragments.About_Fragment;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.fragments.Directory_Fragment;
import com.vam.whitecoats.ui.fragments.Feeds_Fragment;
import com.vam.whitecoats.ui.fragments.Media_Fragment;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class TimeLine extends BaseActionBarActivity implements RevealBackgroundView.OnStateChangeListener {
    public static final String TAG = TimeLine.class.getSimpleName();
    public static final int DELETE_ACTION = 1003;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    TextView mTitleTextView;
    private ImageView next_button;
    private int channelID = 0;
    private String communityTitle = "";
    private About_Fragment about_fragment;
    private boolean isAdmin;
    private Feeds_Fragment feeds_fragment;
    private Media_Fragment media_fragment;
    private Directory_Fragment directory_fragment;
    private static TimeLine instance;
    private NewFeedReceiver newFeedReceiver;
    private String feedProviderType;
    private String feedProviderSubType;
    private ImageButton delete_btn;
    private LayoutInflater mInflater;
    private View mCustomView;
    private AppBarLayout appBarLayout;
    //Progress Dialog to display user.
    private ProgressDialog mProgressDialog;
    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();
    private RevealBackgroundView vRevealBackground;
    private CoordinatorLayout timeLineContentLayout;
    private Realm realm;
    private RealmManager realmManager;
    private boolean customBackButton;
    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private ViewPager2 viewPager;
    private ViewPagerLoadFragmentsAdapter adapter;
    private List<String> mFragmentTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        instance = this;
        setContentView(R.layout.activity_community_pager);
        timeLineContentLayout = (CoordinatorLayout) findViewById(R.id.contentLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.timeLineAppBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_community);
        viewPager = (ViewPager2) findViewById(R.id.timeLineViewpager2);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        // vRevealBackground=(RevealBackgroundView)findViewById(R.id.vRevealBackground);
        //setupRevealBackground(savedInstanceState);
        /*
         * Set up the Actionbar
         */
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        next_button = (ImageView) mCustomView.findViewById(R.id.next_button);
        delete_btn = (ImageButton) mCustomView.findViewById(R.id.delete_btn);
        delete_btn.setVisibility(View.INVISIBLE);
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

        /**
         * Get the Bundle params from Dashboard {@link MyRecycleAdapter #holder.channelNameLayout.onClick and
         * #holder.viewcompletedetails.onClick }.
         */
        Bundle bundle = null;
        try {
            bundle = getIntent().getExtras();
            communityTitle = bundle.getString(RestUtils.FEED_PROVIDER_NAME);
            channelID = bundle.getInt(RestUtils.CHANNEL_ID);
            isAdmin = bundle.getBoolean(RestUtils.TAG_IS_ADMIN);
            feedProviderType = bundle.getString(RestUtils.TAG_FEED_PROVIDER_TYPE);
            feedProviderSubType = bundle.getString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE);


        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * Simple Progress Dialog to show loading action to user where
         * in background all subscribed channels are downloaded.
         */
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));

        mTitleTextView.setText(communityTitle);
        /*
         * Set up the view pager for Timeline
         *
         */
        //setupViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                try {
                    Fragment currentFragment = adapter.getCurrentFragment(tab.getPosition());
                    if (currentFragment != null) {
                        if (tab.getPosition() == 1) {
                            View fragmentView = currentFragment.getView();
                            RecyclerView mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view);//mine one is RecyclerView
                            if (mRecyclerView != null) {
                                CustomLinearLayoutManager layoutManager = (CustomLinearLayoutManager) mRecyclerView
                                        .getLayoutManager();
                                layoutManager.scrollToPositionWithOffset(0, 0);
                                if (layoutManager.getItemCount() > 5) {
                                    if (layoutManager.findFirstVisibleItemPosition() > 4) {
                                        layoutManager.scrollToPositionWithOffset(4, 0);
                                        mRecyclerView.smoothScrollToPosition(0);
                                    }

                                } else {
                                    mRecyclerView.smoothScrollToPosition(0);
                                }
                                if (appBarLayout != null) {
                                    appBarLayout.setExpanded(true, true);
                                }

                            }
                            //getSupportActionBar().show();
                        }
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
        });
        /*
         * Intent Filter to fire on New Feed,
         * Broadcast to be notified
         */
        IntentFilter filter = new IntentFilter();
        filter.addAction("NEW_FEED_ACTION");
        newFeedReceiver = new NewFeedReceiver();
        registerReceiver(newFeedReceiver, filter);
        setupViewPager(viewPager);
        String eventName = "";
        if (viewPager.getCurrentItem() == 0) {
            eventName = "ChannelAbout";
        } else if (viewPager.getCurrentItem() == 1) {
            eventName = "ChannelFeeds";
        } else if (viewPager.getCurrentItem() == 2) {
            eventName = "ChannelMembers";
        } else if (viewPager.getCurrentItem() == 3) {
            eventName = "ChannelMediaHome";
        }
        upshotEventData(0, channelID, 0, "", eventName, "", "", " ", false);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
    }

    private void setupViewPager(ViewPager2 viewPager) {
        Log.i(TAG, "setupViewPager()");
        viewPager.setOffscreenPageLimit(3);

        about_fragment = About_Fragment.newInstance("", "" + channelID, feedProviderType);
        feeds_fragment = Feeds_Fragment.newInstance(getFeedParams());

        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();

        mFragmentList.add(about_fragment);
        mFragmentTitleList.add(getString(R.string.tab_about));
        mFragmentList.add(feeds_fragment);
        mFragmentTitleList.add(getString(R.string.tab_feeds));
        if (feedProviderType != null && feedProviderType.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
            directory_fragment = Directory_Fragment.newInstance("", "" + channelID);
            // media_fragment = Media_Fragment.newInstance("10", "" + channelID);

            mFragmentList.add(directory_fragment);
            mFragmentTitleList.add(getString(R.string.tab_members));
        }

        media_fragment = Media_Fragment.newInstance("10", "" + channelID);
        mFragmentList.add(media_fragment);
        mFragmentTitleList.add(getString(R.string.tab_media));

        adapter = new ViewPagerLoadFragmentsAdapter(this, mFragmentList, mFragmentTitleList,
                "fromActivity");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(mFragmentTitleList.get(position));
        }).attach();

        /*
         * viewpager listener to handle the actions made on pager.
         */
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled - " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG, "onPageSelected - " + position);
                /*
                 * Based on viewpager position and selected tab, we are hitting respective services.
                 * This is to avoid unnecessary request to services which are not required to load.
                 */
                /**
                 * Feeds_Fragment has the service call in it.
                 */
                String eventName = "";
                if (position == 0) {
                    eventName = "ChannelAbout";
                } else if (position == 1) {
                    eventName = "ChannelFeeds";
                } else if (position == 2) {
                    eventName = "ChannelMembers";
                } else if (position == 3) {
                    eventName = "ChannelMedia";
                }
                upshotEventData(0, channelID, 0, "", eventName, "", "", " ", false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);


            }
        });

        Log.i("Name", "" + adapter.getPageTitle(1));

    }

    private Bundle getFeedParams() {
        Log.i(TAG, "getFeedParams()");
        Bundle bundle = new Bundle();
        bundle.putString(RestUtils.FEED_PROVIDER_NAME, communityTitle);
        bundle.putInt(RestUtils.CHANNEL_ID, channelID);
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_TYPE, feedProviderType);
        bundle.putBoolean(RestUtils.TAG_IS_ADMIN, isAdmin);
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, feedProviderSubType);
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult()");
        if (resultCode == Feeds_Fragment.POST_ACTION) {
            feeds_fragment.handleOnActivityResults(requestCode, resultCode, data);
            if (data != null) {
                try {
                    JSONObject post_obj = new JSONObject(data.getExtras().getString("POST_OBJ"));
                    post_obj.put(RestUtils.CHANNEL_ID, channelID);
                    //DashboardUpdatesFragment.updateDashboardWithNewFeed(post_obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == DELETE_ACTION) {
            feeds_fragment.handleOnActivityResults(requestCode, resultCode, data);
            if (data != null) {
                try {
                    JSONObject post_obj = new JSONObject(data.getExtras().getString("POST_OBJ"));
                    post_obj.put(RestUtils.CHANNEL_ID, channelID);
                    DashboardUpdatesFragment.updateDashboardWithDeletedFeed(post_obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Feeds_Fragment.PREFERENCE_ACTION) {
            feeds_fragment.handleOnActivityResults(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) {
            if (about_fragment != null) {
                about_fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public static TimeLine getInstance() {
        return instance;
    }

    @Override
    protected void onDestroy() {
        if (newFeedReceiver != null) {
            unregisterReceiver(newFeedReceiver);
            newFeedReceiver = null;
        }
        super.onDestroy();
        instance = null;
    }

    @Override
    public void onStateChange(int state) {

    }

    private void animateTabOptions() {
        tabLayout.setTranslationY(-tabLayout.getHeight());
        tabLayout.animate().translationY(0).setDuration(300).setStartDelay(USER_OPTIONS_ANIMATION_DELAY).setInterpolator(INTERPOLATOR);
    }

    public class NewFeedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equalsIgnoreCase("NEW_FEED_ACTION")) {
                feeds_fragment.handleOnActivityResults(0, Feeds_Fragment.POST_ACTION, intent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, getString(R.string._onCreateOptionsMenu));
        if (feedProviderType != null && feedProviderType.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
            getMenuInflater().inflate(R.menu.menu_community, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("ChannelID", channelID);
                    String eventName = "";
                    if (viewPager.getCurrentItem() == 0) {
                        eventName = "ChannelAboutBackTapped";
                    } else if (viewPager.getCurrentItem() == 1) {
                        eventName = "ChannelFeedsBackTapped";
                    } else if (viewPager.getCurrentItem() == 2) {
                        eventName = "ChannelMembersBackTapped";
                    } else if (viewPager.getCurrentItem() == 3) {
                        eventName = "ChannelMediaHomeBackTapped";
                    }
                    AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            case R.id.action_search:
                Log.i(TAG, getString(R.string._onOptionsItemSelected));
                Intent intent = new Intent(TimeLine.this, SearchContactsActivity.class);
                intent.putExtra(RestUtils.NAVIGATATE_FROM, TAG);
                intent.putExtra(RestUtils.CHANNEL_ID, channelID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
            //userPhotosAdapter.setLockedAnimations(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("ChannelID", channelID);
                String eventName = "";
                if (viewPager.getCurrentItem() == 0) {
                    eventName = "ChannelAboutDeviceBackTapped";
                } else if (viewPager.getCurrentItem() == 1) {
                    eventName = "ChannelFeedsDeviceBackTapped";
                } else if (viewPager.getCurrentItem() == 2) {
                    eventName = "ChannelMembersDeviceBackTapped";
                } else if (viewPager.getCurrentItem() == 3) {
                    eventName = "ChannelMediaHomeDeviceBackTapped";
                }
                AppUtil.logUserUpShotEvent(eventName, AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
}