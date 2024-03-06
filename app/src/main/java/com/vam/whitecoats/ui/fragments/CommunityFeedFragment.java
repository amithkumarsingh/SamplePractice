package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vam.whitecoats.R;


public class CommunityFeedFragment extends Fragment {


    public static CommunityFeedFragment newInstance(String param1, String param2) {
        CommunityFeedFragment fragment = new CommunityFeedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.community_tab_feed, container, false);


        return getHTabView;
    }

}
