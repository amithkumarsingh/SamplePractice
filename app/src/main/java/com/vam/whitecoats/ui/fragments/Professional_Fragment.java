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

public class Professional_Fragment extends Fragment {

    public static final String TAG = KnowledgeTabFragment.class.getSimpleName();
    private ViewPager2 mViewPager;
    private TabLayout tabLayout;
    public static String tabSelected = "";
    private RealmManager realmManager;
    private Realm realm;
    private int category_TagId = -1;
    private ArrayList<String> tabNames = new ArrayList<>();
    private ProfessionalJobsFragment professionalJobsFragment;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View getHTabView = inflater.inflate(R.layout.professional_tab_fragment, container, false);
        realmManager = new RealmManager(getActivity());
        realm = Realm.getDefaultInstance();//getInstance(this);
        _initialize(getHTabView);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                ContentFeedsFragment.subCategoryId = 0;
                ContentFeedsFragment.skillingFilterClicked = false;
//                tabSelected = "PROFESSIONAL_TAB";
//                CommunityTabFragment.tabSelected = "";
                ContentFeedsFragment.contentFeeds_pageNum = 0;
                ContentFeedsFragment.skilling_filters_page_num = 0;
                if (tab.getPosition() == 0) {
                    DashboardActivity.professionalCategoryTypeID = 8;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Feeds");
                        /*Issue with wc_user_event data*/
                        if (!DashboardActivity.isProfessionalTabFragInitFirstTime) {
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                        } else {
                            DashboardActivity.isProfessionalTabFragInitFirstTime = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 1) {
                    DashboardActivity.professionalCategoryTypeID = 9;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Skilling");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 2) {
                    DashboardActivity.professionalCategoryTypeID = 10;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Opportunities");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (tab.getPosition() == 3) {
                    DashboardActivity.professionalCategoryTypeID = 11;
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("TabName", "Partners");
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "ProfessionalTypeTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), getContext());
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Professional_Fragment newInstance(String param1, String param2) {
        Professional_Fragment fragment = new Professional_Fragment();
        return fragment;
    }

    private void setupViewPager(ViewPager2 mViewPager) {
        Log.i(TAG, "knowledgeSetupViewPager()");
        tabNames.add("Feeds");
        tabNames.add("Skilling");
        tabNames.add("Jobs");
        tabNames.add("Partners");

        /*Doing code optimization and parsing the list of fragments to the View pager adapter*/
        //Start

        professionalJobsFragment = ProfessionalJobsFragment.newInstance("fromProfessionalOpportunities");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ProfessionalFeedsAndOpportuniesFragments.newInstance("fromProfessionalFeeds"));
        fragmentList.add(ContentFeedsFragment.newInstance("fromProfessionalSkilling"));
        fragmentList.add(professionalJobsFragment);
        fragmentList.add(ContentChannelsFragment.newInstance("", "", "PROFESSIONAL_TAB"));

        ViewPagerLoadFragmentsAdapter professionTabAdapter = new ViewPagerLoadFragmentsAdapter(this, fragmentList, null);
        mViewPager.setAdapter(professionTabAdapter);
        //End

        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames.get(position));
            }
        }).attach();

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.e(TAG, "onPageScrolled - " + position);
            }

            @Override
            public void onPageSelected(int position) {
                //super.onPageSelected(position);
                Log.e(TAG, "onPageSelected - " + position);
                if (position == 0) {
                    //ContentFeedsFragment.navigationFrom = "fromProfessionalFeeds";
                } else if (position == 1) {
                    // ContentFeedsFragment.navigationFrom = "fromProfessionalSkilling";
                } else if (position == 2) {
                    setFilterStatus();
                    // professionTabAdapter.notifyItemChanged(position);
                    // ContentFeedsFragment.navigationFrom = "fromProfessionalOpportunities";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //super.onPageScrollStateChanged(state);
                Log.e(TAG, "onPageScrollStateChanged - " + state);
                tabSelected = "PROFESSIONAL_TAB";
                CommunityTabFragment.tabSelected = "";
                if (ContentFeedsFragment.skillingAdapter != null) {
                    ContentFeedsFragment.skillingAdapter.selectedPosition = -1;
                    ContentFeedsFragment.skillingAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void _initialize(View getHTabView) {
        try {
            mViewPager = (ViewPager2) getHTabView.findViewById(R.id.professionalViewpager2);
            tabLayout = (TabLayout) getHTabView.findViewById(R.id.professional_tabLay);
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
            if (categoryTagId == 8) {
                mViewPager.setCurrentItem(0, true);
            } else if (categoryTagId == 9) {
                mViewPager.setCurrentItem(1, true);
            } else if (categoryTagId == 10) {
                mViewPager.setCurrentItem(2, true);
            } else if (categoryTagId == 11) {
                mViewPager.setCurrentItem(3, true);
            }
        } else {
            setCategoryId(categoryTagId);
        }

    }

    public void setFilterStatus() {
        if (professionalJobsFragment != null && professionalJobsFragment.getView() != null) {
            // professionalJobsFragment.requestForContentFeeds(0,false,0);
            professionalJobsFragment.resetFilterStatus();
        }
    }


}
