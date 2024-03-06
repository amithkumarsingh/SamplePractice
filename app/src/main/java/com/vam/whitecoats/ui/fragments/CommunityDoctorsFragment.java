package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vam.whitecoats.R;


public class CommunityDoctorsFragment extends Fragment {


    public static CommunityDoctorsFragment newInstance(String param1, String param2) {
        CommunityDoctorsFragment fragment = new CommunityDoctorsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.community_tab_doctors, container, false);


        return getHTabView;
    }

}
