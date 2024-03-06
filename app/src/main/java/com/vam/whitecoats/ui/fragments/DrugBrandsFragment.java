package com.vam.whitecoats.ui.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.BrandInfo;
import com.vam.whitecoats.core.models.DrugBrand;

import java.util.ArrayList;
import java.util.List;

public class DrugBrandsFragment extends Fragment {
    private LinearLayout linearLayout;

    public DrugBrandsFragment() {
    }
    public static DrugBrandsFragment newInstance(ArrayList<DrugBrand> brandsList, boolean isBrandInfo, ArrayList<BrandInfo> brandInfoList, String drugName,String genericName) {
        DrugBrandsFragment fragment = new DrugBrandsFragment();
        Bundle args = new Bundle();
        //args.putString("drugName",drugName);
        args.putParcelableArrayList("drugBrandsList",brandsList);
        args.putParcelableArrayList("brandInfoList",brandInfoList);
        args.putBoolean("isBrandInfo",isBrandInfo);
        args.putString("drugName",drugName);
        args.putString("genericName",genericName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_details, container, false);
        linearLayout = view.findViewById(R.id.main_layout);
        ArrayList<DrugBrand> drugBrandList = getArguments().getParcelableArrayList("drugBrandsList");
        ArrayList<BrandInfo> brandInfoList = getArguments().getParcelableArrayList("brandInfoList");
        String drugName = getArguments().getString("drugName", "");
        boolean isBrandInfo = getArguments().getBoolean("isBrandInfo");
        String genericName = getArguments().getString("genericName", "");
        if(isBrandInfo && brandInfoList!=null && brandInfoList.size()>0){
            LinearLayout.LayoutParams params10 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params10.setMargins(0,16,0,16);
            TextView label = new TextView(getActivity());
            String manufactureName="<i>"+ " by " + brandInfoList.get(0).getManufacturer()+"</i>";
            label.setText(drugName +" ");
            label.append(Html.fromHtml(manufactureName));
            label.setLayoutParams(params10);
            label.setTextSize(16);
            label.setTextColor(getResources().getColor(R.color.black));
            label.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(label);
            for(BrandInfo brandInfo:brandInfoList){
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(0,16,0,16);
                TextView label1 = new TextView(getActivity());
                String retailPrice="";
                if(brandInfo.getRetailPrice()!=null){
                    retailPrice=", ₹"+brandInfo.getRetailPrice();
                }
                label1.setText(brandInfo.getTypeName()+"("+brandInfo.getPacking()+"), "+ brandInfo.getStrength() +retailPrice);
                label1.setLayoutParams(params1);
                label1.setTextSize(14);
                label1.setTextColor(getResources().getColor(R.color.black));
                linearLayout.addView(label1);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,32,0,8);
            TextView othersBrandsLabel = new TextView(getActivity());
            othersBrandsLabel.setText("Other brands of "+genericName);
            othersBrandsLabel.setLayoutParams(params);
            othersBrandsLabel.setTextSize(16);
            othersBrandsLabel.setMaxLines(1);
            othersBrandsLabel.setEllipsize(TextUtils.TruncateAt.END);
            othersBrandsLabel.setTextColor(getResources().getColor(R.color.black));
            othersBrandsLabel.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(othersBrandsLabel);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
            params1.setMargins(0,8,0,8);
            View seperator=new View(getActivity());
            seperator.setLayoutParams(params1);
            seperator.setBackgroundColor(Color.LTGRAY);
            linearLayout.addView(seperator);
        }
        for(int i=0;i<drugBrandList.size();i++){
            DrugBrand brandObj = drugBrandList.get(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0,16,0,16);
            TextView label = new TextView(getActivity());
            String manufactureName="<i>"+ " by " + brandObj.getManufacturer()+"</i>";
            label.setText(brandObj.getDrugName() +" ");
            label.append(Html.fromHtml(manufactureName));
            label.setLayoutParams(params);
            label.setTextSize(16);
            label.setTextColor(getResources().getColor(R.color.black));
            label.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(label);
            for(int j=0;j<brandObj.getInfo().size();j++){
                BrandInfo infoObj = brandObj.getInfo().get(j);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params1.setMargins(0,16,0,16);
                TextView label1 = new TextView(getActivity());
                String retailPrice="";
                if(infoObj.getRetailPrice()!=null){
                    retailPrice=", ₹"+infoObj.getRetailPrice();
                }
                label1.setText(infoObj.getTypeName()+"("+infoObj.getPacking()+"), "+ infoObj.getStrength() +retailPrice);
                label1.setLayoutParams(params1);
                label1.setTextSize(14);
                label1.setTextColor(getResources().getColor(R.color.black));
                linearLayout.addView(label1);
            }

            if(i!=drugBrandList.size()-1){
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
                params1.setMargins(0,16,0,16);
                View seperator=new View(getActivity());
                seperator.setLayoutParams(params1);
                seperator.setBackgroundColor(Color.LTGRAY);
                linearLayout.addView(seperator);
            }
        }

        return view;
    }
}
