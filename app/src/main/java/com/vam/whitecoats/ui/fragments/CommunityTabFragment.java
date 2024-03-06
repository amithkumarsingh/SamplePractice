package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.DrugsActivity;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class CommunityTabFragment extends Fragment {

    public static final String TAG = CommunityTabFragment.class.getSimpleName();
    private TabLayout tabLayout;
    private CommunitySpotliteFragment communitySpotliteFragment;
    private CommunityFeedFragment communityFeedFragment;
    private CommunityDoctorsFragment communityDoctorsFragment;
    private CommunityOrgnizationsFragment communityOrgnizationsFragment;

    private MyNetworkFragment myNetworkFragment;
    private DashboardUpdatesFragment dashboardUpdatesFragment;
    private DashboardUpdatesFragment dashboardUpdatesFragmentSpotlight;
    private ContentChannelsFragment channelsFragment;
    public static String tabSelected = "";
    private RealmManager realmManager;
    private Realm realm;
    MySharedPref mySharedPref = null;
    Boolean is_navigation;

    private DrugsActivity drugsActivity;
    private int category_TagId = -1;

    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private ViewPager2 mViewPager;
    private List<String> mFragmentTitleList;


    public static CommunityTabFragment newInstance(String param1, String param2) {
        CommunityTabFragment fragment = new CommunityTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.tab_fragment_community, container, false);
        realmManager = new RealmManager(getActivity());
        realm = Realm.getDefaultInstance();//getInstance(this);
        _initialize(getHTabView);
       /* if (mySharedPref == null)
            mySharedPref = new MySharedPref(getActivity());
        is_navigation = mySharedPref.getPref("is_community_navigation", false);*/


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                ContentFeedsFragment.subCategoryId = 0;
                ContentFeedsFragment.skillingFilterClicked = false;
                ContentFeedsFragment.contentFeeds_pageNum = 0;
                ContentFeedsFragment.skilling_filters_page_num = 0;
                /*if(tab.getPosition()==0) {
                    ContentFeedsFragment.navigationFrom = "fromCommunitySpotlight";
                }else if(tab.getPosition()==1){
                    ContentFeedsFragment.navigationFrom = "fromCommunityFeeds";
                }*/

                if (tab.getPosition() == 0) {
                    DashboardActivity.communityCategoryTypeID = 4;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Spotlights");
                        /*Issue with wc_user_event data*/
                        if (!DashboardActivity.isCommunityTabFragInitFirstTime) {
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                        } else {
                            DashboardActivity.isCommunityTabFragInitFirstTime = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 1) {
                    DashboardActivity.communityCategoryTypeID = 5;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Feeds");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 2) {
                    DashboardActivity.communityCategoryTypeID = 6;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Doctors");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 3) {
                    DashboardActivity.communityCategoryTypeID = 7;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Organizations");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "CommunityTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
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
        //setupViewPager(mViewPager);
//        if (getActivity().getIntent().getExtras() != null && getActivity().getIntent().getExtras().containsKey("TAB_NUMBER")) {
//            if (getActivity().getIntent().getExtras().getInt("TAB_NUMBER") == 1) {
//                mViewPager.setCurrentItem(1);
//            }
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (category_TagId > -1) {
                    navigateToNetworkTab(category_TagId);
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
        myNetworkFragment = MyNetworkFragment.newInstance("", "");
        channelsFragment = ContentChannelsFragment.newInstance("", "", "COMMUNITY_TAB");

        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(CommunitySpotlightFeedsFragment.newInstance("fromCommunitySpotlight"));
        mFragmentList.add(CommunityFeedsFragments.newInstance("fromCommunityFeeds"));
        mFragmentList.add(myNetworkFragment);
        mFragmentList.add(channelsFragment);

        mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add(getString(R.string.tab_spotlight));
        mFragmentTitleList.add("Feeds");
        mFragmentTitleList.add(getString(R.string.tab_doctor));
        mFragmentTitleList.add(getString(R.string.tab_organizations));


        ViewPagerLoadFragmentsAdapter mCommunityPagerAdapter = new ViewPagerLoadFragmentsAdapter(this, mFragmentList, mFragmentTitleList);

        dashboardUpdatesFragmentSpotlight = DashboardUpdatesFragment.newInstance(true);
        dashboardUpdatesFragment = DashboardUpdatesFragment.newInstance(true);
        dashboardUpdatesFragment.setDashboardResponse("");
        dashboardUpdatesFragmentSpotlight.setDashboardResponse("");

        mViewPager.setAdapter(mCommunityPagerAdapter);

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
                    ContentFeedsFragment.navigationFrom = "fromCommunitySpotlight";
                } else if (position == 1) {
                    ContentFeedsFragment.navigationFrom = "fromCommunityFeeds";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged - " + state);
                tabSelected = "COMMUNITY_TAB";
                Professional_Fragment.tabSelected = "";
            }
        });
    }
//end

    private void _initialize(View getHTabView) {
        try {
            mViewPager = (ViewPager2) getHTabView.findViewById(R.id.communityViewpager2);
            tabLayout = (TabLayout) getHTabView.findViewById(R.id.tabs);
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

    public void inviteToWhiteCoatsPopup() {
        mViewPager.setCurrentItem(2, true);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String link_val = "https://whitecoats.com/invite";
        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getActivity().getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
        getActivity().startActivity(Intent.createChooser(shareIntent, "Invite via"));
    }

    public void setCategoryId(int tag_id) {
        category_TagId = tag_id;
    }

    public void navigateToNetworkTab(int categoryTagId) {
        if (mViewPager != null && categoryTagId != -1) {
            if (categoryTagId == 4) {
                mViewPager.setCurrentItem(0, true);
            } else if (categoryTagId == 5) {
                mViewPager.setCurrentItem(1, true);
            } else if (categoryTagId == 6 || categoryTagId == 0) {
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(2, true);
                    }
                }, 500);*/
                mViewPager.setCurrentItem(2, true);
            } else if (categoryTagId == 7) {
                mViewPager.setCurrentItem(3, true);
            }
        } else {
            setCategoryId(categoryTagId);
        }
    }


}
