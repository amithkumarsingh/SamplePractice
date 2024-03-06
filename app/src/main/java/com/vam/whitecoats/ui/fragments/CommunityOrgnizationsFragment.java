package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vam.whitecoats.R;


public class CommunityOrgnizationsFragment extends Fragment {


    public static CommunityOrgnizationsFragment newInstance(String param1, String param2) {
        CommunityOrgnizationsFragment fragment = new CommunityOrgnizationsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.community_tab_organization, container, false);


        return getHTabView;
    }

}
