package com.vam.whitecoats.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CategoryDistributionInfo;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.ui.fragments.CategoriesRecentTab;
import com.vam.whitecoats.ui.fragments.CategoriesSpecialityTab;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class CategoryDistributionTabsActivity extends BaseActionBarActivity {

    private TabLayout tabLayout;
    private CategoriesRecentTab recentCategoriesFragment;
    private CategoriesSpecialityTab specialityCatergoriesFragment;
    public static final String TAG = CategoryDistributionTabsActivity.class.getSimpleName();
    private ArrayList<CategoryFeeds> feedsList;
    private ArrayList<CategoryDistributionInfo> category_distributionsList;
    private String categoryName;
    private int categoryId;
    private TextView mTitleTextView;
    private Realm realm;
    private RealmManager realmManager;
    private String categoryType;
    private EditText search_text_view;
    private boolean customBackButton = false;
    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private ViewPager2 mViewPager;
    private List<Fragment> mFragmentList;
    private List<String> mFragmentTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_distribution_tabs);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        mTitleTextView.setVisibility(View.GONE);
        actionBar.setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(CategoryDistributionTabsActivity.this);

        mViewPager = (ViewPager2) findViewById(R.id.categoryDistributionViewpager2);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        search_text_view = (EditText) findViewById(R.id.et_search_drug);

        feedsList = (ArrayList<CategoryFeeds>) getIntent().getSerializableExtra("feeds_list");
        category_distributionsList = (ArrayList<CategoryDistributionInfo>) getIntent().getSerializableExtra("Categories_Category_DistributionsList");
        categoryName = getIntent().getStringExtra("Category_Name");
        categoryId = getIntent().getIntExtra("Category_id", 0);
        categoryType = getIntent().getStringExtra("Category_type");
        EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate", 0, categoryId));

        if (!categoryName.isEmpty()) {
            mTitleTextView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(categoryName);
        }

        search_text_view.setHint("Search" + " " + categoryName);

        search_text_view.setOnClickListener(view -> {
            Intent intent = new Intent(this, CategorySearchActivity.class);
            intent.putExtra("CategoryId", categoryId);
            intent.putExtra("CategoryType", categoryType);
            intent.putExtra("CategoryName", categoryName);
            intent.putExtra("NavigationFrom", "CategoryDistribution");
            startActivity(intent);

        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

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

        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    protected void setCurrentActivity() {

    }

    private void setupViewPager(ViewPager2 mViewPager) {

        boolean fromDistributionRecent = true;

        recentCategoriesFragment = CategoriesRecentTab.newInstance(feedsList, categoryId, 0, "", 0, categoryType, fromDistributionRecent, categoryName, "");
        specialityCatergoriesFragment = CategoriesSpecialityTab.newInstance(category_distributionsList, categoryName, categoryId);

        mFragmentList = new ArrayList<>();
        mFragmentList.add(recentCategoriesFragment);
        mFragmentList.add(specialityCatergoriesFragment);

        mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add("Recent");
        mFragmentTitleList.add(category_distributionsList.get(0).getCategoryDistributionName());

        ViewPagerLoadFragmentsAdapter mCategoryDistributorAdapter = new ViewPagerLoadFragmentsAdapter(this,
                mFragmentList, mFragmentTitleList,"fromActivity");
        mViewPager.setAdapter(mCategoryDistributorAdapter);

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
                String eventName;
                if (mViewPager.getCurrentItem() == 0) {
                    eventName = "CategoryDistributionRecent";
                } else {
                    eventName = "CategoryDistributionSpeciality";
                }
                upshotEventData(0, 0, 0, "", eventName, "", "", " ", true);
                JSONObject jsonObjectEvent = new JSONObject();
                try {
                    jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                    jsonObjectEvent.put("CategoryName", categoryName);
                    String event_name = "";
                    if (mViewPager.getCurrentItem() == 0) {
                        event_name = "CategoryDistributionRecentTapped";
                    } else {
                        event_name = "CategoryDistributionSpecialityTapped";
                    }
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), event_name, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent), CategoryDistributionTabsActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);
            }
        });
        String eventName;
        if (mViewPager.getCurrentItem() == 0) {
            eventName = "CategoryDistributionRecent";
        } else {
            eventName = "CategoryDistributionSpeciality";
        }
        upshotEventData(0, 0, 0, "", eventName, "", "", " ", true);
    }

    //end
    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            JSONObject jsonObjectEvent = new JSONObject();
            try {
                jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                String eventName = "";
                if (mViewPager.getCurrentItem() == 0) {
                    eventName = "CategoryDistributionRecentFeedsDeviceBackTapped";
                } else {
                    eventName = "CategoryDistributionSpecialityDeviceBackTapped";
                }
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent), CategoryDistributionTabsActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            JSONObject jsonObjectEvent = new JSONObject();
            customBackButton = true;
            try {
                jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                String eventName = "";
                if (mViewPager.getCurrentItem() == 0) {
                    eventName = "CategoryDistributionRecentFeedsBackTapped";
                } else {
                    eventName = "CategoryDistributionSpecialityBackTapped";
                }
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent), CategoryDistributionTabsActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }
}