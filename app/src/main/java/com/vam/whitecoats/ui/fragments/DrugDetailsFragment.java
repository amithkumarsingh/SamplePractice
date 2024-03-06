package com.vam.whitecoats.ui.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.AccessNetworkConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vam.whitecoats.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DrugDetailsFragment extends Fragment {
    private LinearLayout linearLayout;
    private String currentHeading = "";



    public DrugDetailsFragment() {
        // Required empty public constructor
    }
    public static DrugDetailsFragment newInstance(int value,String tabData,boolean isValueNull,String valueData,String tabName) {
        DrugDetailsFragment fragment = new DrugDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("value",value);
        args.putString("tabData", tabData);
        args.putBoolean("isValueNull",isValueNull);
        args.putString("valueData",valueData);
        args.putString("tabName",tabName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drug_details, container, false);
        linearLayout = view.findViewById(R.id.main_layout);
        String tabData = getArguments().getString("tabData");
        if(getArguments().getBoolean("isValueNull")){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,16,0,16);
            TextView label = new TextView(getContext());
            label.setText(getArguments().getString("tabName"));
            label.setLayoutParams(params);
            label.setTextSize(16);
            label.setTextColor(getResources().getColor(R.color.black));
            label.setTypeface(null,Typeface.BOLD);
            linearLayout.addView(label);
            String valueData = getArguments().getString("valueData");
            valueData = valueData.replace("@!","\u2022");
            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView header = new TextView(getContext());
            header.setText(valueData);
            header.setLayoutParams(params);
            header.setTextSize(14);
            header.setTextColor(getResources().getColor(R.color.black));
            linearLayout.addView(header);
        }
        else{
            try {
                JSONArray jsonArray= new JSONArray(tabData);
                if(getArguments().getString("tabName").equalsIgnoreCase("Interactions")){


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        if(currentHeading.equalsIgnoreCase(object.getString("value_type"))){
                            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            TextView header = new TextView(getContext());
                            header.setText(object.getString("label"));
                            header.setLayoutParams(valueParams);
                            header.setTextSize(14);
                            header.setTextColor(getResources().getColor(R.color.black));
                            linearLayout.addView(header);
                            TextView value = new TextView(getContext());
                            value.setText(object.getString("value"));
                            value.setLayoutParams(valueParams);
                            value.setTextSize(14);
                            value.setTextColor(getResources().getColor(R.color.black));
                            linearLayout.addView(value);
                        }
                        else{
                            currentHeading = object.getString("value_type");
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0,16,0,16);
                            TextView label = new TextView(getContext());
                            label.setText(jsonArray.getJSONObject(0).getString("value_type"));
                            label.setLayoutParams(params);
                            label.setTextSize(16);
                            label.setTextColor(getResources().getColor(R.color.black));
                            label.setTypeface(null,Typeface.BOLD);
                            linearLayout.addView(label);
                            LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            TextView header = new TextView(getContext());
                            header.setText(object.getString("label"));
                            header.setLayoutParams(valueParams);
                            header.setTextSize(14);
                            header.setTextColor(getResources().getColor(R.color.black));
                            linearLayout.addView(header);
                            TextView value = new TextView(getContext());
                            value.setText(object.getString("value"));
                            value.setLayoutParams(valueParams);
                            value.setTextSize(14);
                            value.setTextColor(getResources().getColor(R.color.black));
                            linearLayout.addView(value);

                        }
                    }
                }
                else{
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,16,0,16);
                        TextView label = new TextView(getContext());
                        label.setText(object.getString("label"));
                        label.setLayoutParams(params);
                        label.setTextSize(16);
                        label.setTextColor(getResources().getColor(R.color.black));
                        label.setTypeface(null,Typeface.BOLD);
                        linearLayout.addView(label);
                        TextView value = new TextView(getContext());
                        String data = object.optString("value");
                        if(object.getString("value_type").equalsIgnoreCase("LIST")){
                            data = data.replace("@!","\u2022");
                            value.setText(data);
                        }
                        else{
                            if(data.contains("$!")){
                                data = data.replace("$!","");
                                value.setText("["+data+" ]");
                            }
                            else{
                                value.setText(data);
                            }
                        }
                        value.setLayoutParams(valueParams);
                        value.setTextSize(14);
                        value.setTextColor(getResources().getColor(R.color.black));
                        linearLayout.addView(value);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return view;
    }
}