package com.vam.whitecoats.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.ViewPagerLoadFragmentsAdapter;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssociationTimelineFragment extends Fragment {

    private JSONObject channelObj;
    private TabLayout tabLayout;
    private String responseString;
    private boolean loadOfflineData;
    private boolean isDashboard;
    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
    private List<String> mFragmentTitleList;
    private ViewPager2 viewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            responseString = getArguments().getString("responseString", "");
            loadOfflineData = getArguments().getBoolean("loadOfflineData", false);
            isDashboard = getArguments().getBoolean("isDashboard");
            try {
                channelObj = new JSONObject(getArguments().getString("channelObjString"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.association_timeline_layout, container, false);
        viewPager = (ViewPager2) view.findViewById(R.id.association_timeline_viewpager2);
        tabLayout = (TabLayout) view.findViewById(R.id.association_timeline_tabs);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager(viewPager);
    }

    public AssociationTimelineFragment() {
    }

    public static AssociationTimelineFragment newInstance(String channelObjString, String channelResponse, boolean loadOfflineData, boolean isDashboard) {
        AssociationTimelineFragment fragment = new AssociationTimelineFragment();
        Bundle args = new Bundle();
        args.putString("channelObjString", channelObjString);
        args.putString("responseString", channelResponse);
        args.putBoolean("loadOfflineData", loadOfflineData);
        args.putBoolean("isDashboard", isDashboard);
        fragment.setArguments(args);
        return fragment;
    }

    public void setCurrentChannelObj(JSONObject mChannelObj) {
        channelObj = mChannelObj;
    }

    /*Changing the viewpager to ViewPager2 and customizing the code to make reusable*/
//Start
    private void setupViewPager(ViewPager2 viewPager) {
        if (channelObj == null) {
            return;
        }
        Feeds_Fragment feeds_fragment = Feeds_Fragment.newInstance(getFeedParams());
        Directory_Fragment directory_fragment =  Directory_Fragment.newInstance("", "" + channelObj.optInt(RestUtils.CHANNEL_ID));
        Media_Fragment media_fragment =  Media_Fragment.newInstance("10", "" + channelObj.optInt(RestUtils.CHANNEL_ID));
        About_Fragment about_fragment =  About_Fragment.newInstance("", "" + channelObj.optInt(RestUtils.CHANNEL_ID), channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));

        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(feeds_fragment);
        mFragmentList.add(directory_fragment);
        mFragmentList.add(media_fragment);
        mFragmentList.add(about_fragment);


        mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add(getString(R.string.tab_feeds));
        mFragmentTitleList.add(getString(R.string.tab_members));
        mFragmentTitleList.add(getString(R.string.tab_media));
        mFragmentTitleList.add(getString(R.string.tab_about));

        ViewPagerLoadFragmentsAdapter adapter = new ViewPagerLoadFragmentsAdapter(this,mFragmentList,mFragmentTitleList);


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ffffff"));
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#00A76D"));

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(mFragmentTitleList.get(position));
        }).attach();


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

            }
        });
    }
//End
    private Bundle getFeedParams() {
        Bundle bundle = new Bundle();
        bundle.putString(RestUtils.FEED_PROVIDER_NAME, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
        bundle.putInt(RestUtils.CHANNEL_ID, channelObj.optInt(RestUtils.CHANNEL_ID));
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_TYPE, channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));
        bundle.putBoolean(RestUtils.TAG_IS_ADMIN, false);
        bundle.putString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, channelObj.optString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE));
        bundle.putString("channelTimelineResponse", responseString);
        bundle.putBoolean("loadOfflineData", loadOfflineData);
        bundle.putBoolean("isDashboard", isDashboard);
        isDashboard = false;
        return bundle;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            //setupViewPager(viewPager);
        }
    }
}
