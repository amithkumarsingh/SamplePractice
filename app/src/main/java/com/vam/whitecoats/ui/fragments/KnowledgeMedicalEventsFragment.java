package com.vam.whitecoats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vam.whitecoats.R;


public class KnowledgeMedicalEventsFragment extends Fragment {


    public static KnowledgeMedicalEventsFragment newInstance(String param1, String param2) {
        KnowledgeMedicalEventsFragment fragment = new KnowledgeMedicalEventsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getHTabView = inflater.inflate(R.layout.knowledge_tab_medical_events, container, false);


        return getHTabView;
    }

}
