package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.BrandInfo;
import com.vam.whitecoats.core.models.DrugBrand;
import com.vam.whitecoats.core.models.DrugDetail;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.DrugsActivity;
import com.vam.whitecoats.ui.fragments.DrugBrandsFragment;
import com.vam.whitecoats.ui.fragments.DrugDetailsFragment;
import com.vam.whitecoats.ui.fragments.DrugFeedsFragment;
import com.vam.whitecoats.utils.AppUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class DrugsPagerAdapter extends FragmentPagerAdapter {
    private String genericName;
    private ArrayList<BrandInfo> brandInfoList;
    private boolean isBrandInfo;
    private final Realm realm;
    private final RealmManager realmManager;
    private final int drugId;
    private ArrayList<DrugBrand> drugBrandList;
    private Context context;
    private int noOfTabs;
    private List<DrugDetail> drugDetailList;
    private Map<Integer,List<DrugDetail>> dataMap;
    private String drugName;
    public DrugsPagerAdapter(@NonNull FragmentManager fm, Context context, int noOfTabs, List<DrugDetail> drugDetails, Map<Integer,List<DrugDetail>> dataMap, String drugName, ArrayList<DrugBrand> _drugbrandList, boolean _isBrandInfo, ArrayList<BrandInfo> _brandInfoList,String _genericName,int _drugId) {
        super(fm);
        this.context = context;
        this.noOfTabs = noOfTabs;
        this.drugDetailList = drugDetails;
        this.dataMap = dataMap;
        this.drugName = drugName;
        this.drugBrandList=_drugbrandList;
        this.drugId=_drugId;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(context);
        this.isBrandInfo=_isBrandInfo;
        this.brandInfoList=_brandInfoList;
        this.genericName=_genericName;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        DrugDetail drugDetail = drugDetailList.get(position);
        if(drugDetail.getLabel().equalsIgnoreCase("Feeds")){
            return DrugFeedsFragment.newInstance(drugName,drugId);
        }else if(drugDetail.getLabel().equalsIgnoreCase("Brands") || drugDetail.getLabel().equalsIgnoreCase("Manufacture/pricing")){
            return DrugBrandsFragment.newInstance(drugBrandList,isBrandInfo,brandInfoList,drugName,genericName);
        }
        else{
            List<DrugDetail> drugDetails = dataMap.get(drugDetail.getDrugAttributeId());
            if(drugDetails == null){
                return DrugDetailsFragment.newInstance(position,"",true,drugDetail.getValue(),drugDetail.getLabel());
            }
            else{
                Gson gson = new Gson();
                String json = gson.toJson(drugDetails);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return DrugDetailsFragment.newInstance(position,jsonArray==null?"":jsonArray.toString(),false,"",drugDetail.getLabel());
            }
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.drug_custom_tab, null);
        DrugDetail drugDetail = drugDetailList.get(position);
        TextView tv = (TextView) v.findViewById(R.id.drug_item_name);
        tv.setText(drugDetail.getLabel());
        ImageView img = (ImageView) v.findViewById(R.id.drug_item_icon);
        if(drugDetail.getLabel().equalsIgnoreCase("Adult Dosing")){
            img.setImageResource(R.drawable.adult_dosing);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Pediatric dosing"))
        {
            img.setImageResource(R.drawable.pediatric_dosing);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Contraindications/Cautions"))
        {
            img.setImageResource(R.drawable.contraindion_cautions);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Adverse Reactions"))
        {
            img.setImageResource(R.drawable.adverse_reactions);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Safety and Monitoring"))
        {
            img.setImageResource(R.drawable.safety_and_monitoring);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Pharmacology"))
        {
            img.setImageResource(R.drawable.pharmacology);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Interaction Characteristics"))
        {
            img.setImageResource(R.drawable.interaction_characteristics);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Black Box Warnings"))
        {
            img.setImageResource(R.drawable.black_box_warnings);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Dosing"))
        {
            img.setImageResource(R.drawable.dosing);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("interactions"))
        {
            img.setImageResource(R.drawable.interactions);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Brands"))
        {
            img.setImageResource(R.drawable.brands);
        }
        else if(drugDetail.getLabel().equalsIgnoreCase("Feeds"))
        {
            img.setImageResource(R.drawable.feeds);
        }
        else{
            img.setImageResource(R.drawable.default_icon);
        }
        if(position != 0){
            img.setImageAlpha(0x3F);
            tv.setTextColor(context.getResources().getColor(R.color.gray_1));
        }


//        img.setImageResource(imageResId[position]);
        return v;
    }

}
