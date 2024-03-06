package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class KnowledgeTabFragment extends Fragment {

    public static final String TAG = KnowledgeTabFragment.class.getSimpleName();
    private TabLayout tabLayout;
    private KnowledgeFeedFragment knowledgeFeedFragment;
    private KnowledgeMedicalEventsFragment knowledgeMedicalEventsFragment;
    private Feeds_Fragment feeds_Fragment;
    private KnowledgeDrugFragment knowledgeDrugFragment;
    private DashboardUpdatesFragment dashboardUpdatesFragment;
    private DashboardUpdatesFragment dashboardUpdatesFragmentEvent;
    private RealmManager realmManager;
    private Realm realm;
    private int category_TagId = -1;
    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private ViewPager2 mViewPager;
    private List<String> mFragmentTitleList;

    public static KnowledgeTabFragment newInstance(String param1, String param2) {
        KnowledgeTabFragment fragment = new KnowledgeTabFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.tab_fragment_knowledge, container, false);
        realmManager = new RealmManager(getActivity());
        realm = Realm.getDefaultInstance();//getInstance(this);

        _initialize(getHTabView);

        //   mViewPager.setOffscreenPageLimit(2);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                ContentFeedsFragment.subCategoryId = 0;
                ContentFeedsFragment.skillingFilterClicked = false;
                ContentFeedsFragment.contentFeeds_pageNum = 0;
                ContentFeedsFragment.skilling_filters_page_num = 0;
                /*if(tab.getPosition()==0) {
                    ContentFeedsFragment.navigationFrom = "fromKnowledgeFeeds";
                }else if(tab.getPosition()==2) {
                    ContentFeedsFragment.navigationFrom = "fromKnowledgeMedicalEvents";
                }*/

                if (tab.getPosition() == 0) {
                    DashboardActivity.knowledgeCategoryTypeID = 1;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Feeds");
                        /*Issue with wc_user_event data*/
                        if (!DashboardActivity.isKnowledgeTabFragInitFirstTime) {
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                        } else {
                            DashboardActivity.isKnowledgeTabFragInitFirstTime = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 1) {
                    DashboardActivity.knowledgeCategoryTypeID = 2;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Drug Reference");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 2) {
                    DashboardActivity.knowledgeCategoryTypeID = 3;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Medical Events");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeTabTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
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
        // setupViewPager(mViewPager);
//        if (getActivity().getIntent().getExtras() != null && getActivity().getIntent().getExtras().containsKey("TAB_NUMBER")) {
//            if (getActivity().getIntent().getExtras().getInt("TAB_NUMBER") == 1) {
//                mViewPager.setCurrentItem(1);
//            }
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (category_TagId > -1) {
                    tabNavigation(category_TagId);
                    category_TagId = -1;
                    //mySharedPref.savePref("is_community_navigation",false);
                }
            }
        }, 100);
        return getHTabView;
    }

    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
//Start
    private void setupViewPager(ViewPager2 mViewPager) {
        Log.i(TAG, "knowledgeSetupViewPager()");
        knowledgeDrugFragment = KnowledgeDrugFragment.newInstance("", "");

        dashboardUpdatesFragment = DashboardUpdatesFragment.newInstance(true);
        knowledgeMedicalEventsFragment = KnowledgeMedicalEventsFragment.newInstance("", "");
        dashboardUpdatesFragmentEvent = DashboardUpdatesFragment.newInstance(true);
        dashboardUpdatesFragment.setDashboardResponse("");
        dashboardUpdatesFragmentEvent.setDashboardResponse("");


        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(KnowledgeContentFeedsFragment.newInstance("fromKnowledgeFeeds"));
        mFragmentList.add(knowledgeDrugFragment);
        mFragmentList.add(KnowledgeMedicalEventsFeedsFragment.newInstance("fromKnowledgeMedicalEvents"));

        mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add("Feeds");
        mFragmentTitleList.add(getString(R.string.tab_drug_reference));
        mFragmentTitleList.add(getString(R.string.tab_medical_events));

        ViewPagerLoadFragmentsAdapter mKnowledgePagerAdapter = new ViewPagerLoadFragmentsAdapter(this, mFragmentList, mFragmentTitleList);


        mViewPager.setAdapter(mKnowledgePagerAdapter);


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
                if (position == 0) {
                    ContentFeedsFragment.navigationFrom = "fromKnowledgeFeeds";
                } else if (position == 2) {
                    ContentFeedsFragment.navigationFrom = "fromKnowledgeMedicalEvents";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);
            }
        });
    }
//end

    private void _initialize(View getHTabView) {
        try {
            mViewPager = (ViewPager2) getHTabView.findViewById(R.id.knowledgeViewpager2);
            tabLayout = (TabLayout) getHTabView.findViewById(R.id.knowledgeTabs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Bundle getFeedParams() {
        Log.i(TAG, "getFeedParams()");
        Bundle bundle = new Bundle();
        bundle.putString(RestUtils.FEED_PROVIDER_NAME, "IBCommunity");
        bundle.putInt(RestUtils.CHANNEL_ID, 3);
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_TYPE, "Community");
        bundle.putBoolean(RestUtils.TAG_IS_ADMIN, false);
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, "Association");
        bundle.putString("channelTimelineResponse", "");
        bundle.putBoolean("loadOfflineData", false);
        bundle.putBoolean("isDashboard", false);
//        isDashboard = false;
        return bundle;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(mViewPager);
    }

    public void setCategoryId(int tag_id) {
        category_TagId = tag_id;
    }

    public void tabNavigation(int categoryTagId) {

        if (mViewPager != null) {
            if (categoryTagId == 1) {
                mViewPager.setCurrentItem(0, true);
            } else if (categoryTagId == 2) {
                mViewPager.setCurrentItem(1, true);
            } else if (categoryTagId == 3) {
                mViewPager.setCurrentItem(2, true);
            }
        } else {
            setCategoryId(categoryTagId);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
