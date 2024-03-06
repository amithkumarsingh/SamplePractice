package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.google.android.material.tabs.TabLayout;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.DrugDetail;
import com.vam.whitecoats.core.models.DrugDetailsInteractions;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.DrugsPagerAdapter;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.viewmodel.DrugDetailsViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


public class DrugDetailsActivity extends BaseActionBarActivity {
    private ViewPager viewPager;
    DrugsPagerAdapter drugsPagerAdapter;
    private TabLayout tabLayout;
    private DrugDetailsViewModel drugDetailsViewModel;
    private Intent intent;
    private int drugId;
    private String drugName;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private EditText drugSearchEditText;
    private TextView tvGenericName;
    private String genericName;
    private boolean isBrandInfo;
    private TextView mTitleTextView;
    List<DrugDetail> finalAdapterData = new ArrayList<>();
    private Realm realm;
    private RealmManager realmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_details);


        viewPager = findViewById(R.id.drugs_view_pager);
        tabLayout = findViewById(R.id.drug_tab_layout);
        avLoadingIndicatorView = findViewById(R.id.drug_details_aviInExplore);
        drugSearchEditText = findViewById(R.id.et_search_drug);
        tvGenericName = findViewById(R.id.tv_generic_name);

        intent = getIntent();
        drugId = intent.getIntExtra("drugId", 0);
        drugName = intent.getStringExtra("drugName");
        genericName = intent.getExtras().getString("genericName", "");
        isBrandInfo = intent.getExtras().getBoolean("isBrandInfo", false);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText(drugName);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);


        setTitle(drugName);
        if (!genericName.isEmpty()) {
            tvGenericName.setVisibility(View.VISIBLE);
            tvGenericName.setText(genericName);
        }

        drugDetailsViewModel = ViewModelProviders.of(this).get(DrugDetailsViewModel.class);

        if (AppUtil.isConnectingToInternet(DrugDetailsActivity.this)) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            setRequestData(drugId, isBrandInfo);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView imageView = view.findViewById(R.id.drug_item_icon);
                    imageView.setImageAlpha(0xFF);
                    TextView textView = view.findViewById(R.id.drug_item_name);
                    textView.setTextColor(getResources().getColor(R.color.black));
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("DrugName", drugName);
                    jsonObject.put("DrugNameID", drugId);
                    DrugDetail drugDetail = new DrugDetail();
                    int position = tab.getPosition();
                    drugDetail = finalAdapterData.get(position);
                    jsonObject.put("DrugInfoCategoryName", drugDetail.getLabel());
                    jsonObject.put("DrugInfoCategoryID", drugDetail.getDrugAttributeId());
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugInfoCategoryTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), DrugDetailsActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    ImageView imageView = view.findViewById(R.id.drug_item_icon);
                    imageView.setImageAlpha(0x3F);
                    TextView textView = view.findViewById(R.id.drug_item_name);
                    textView.setTextColor(getResources().getColor(R.color.gray_1));
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        drugSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DrugDetailsActivity.this, DrugSearchActivity.class);
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
        drugDetailsViewModel.getDrugDetails().observe(this, apiResponse -> {
            if (apiResponse == null) {
                return;
            }
            if (apiResponse.getError() == null) {
                List<DrugDetail> adapterData = new ArrayList<>();

                Map<Integer, List<DrugDetail>> dataMap = new HashMap<>();
                if (apiResponse.getDrugDetails() != null) {
                    for (int i = 0; i < apiResponse.getDrugDetails().size(); i++) {
                        DrugDetail drugDetail = apiResponse.getDrugDetails().get(i);
                        if (drugDetail.getParentId() == 0) {
                            adapterData.add(drugDetail);
                        } else {
                            List<DrugDetail> dataMapVvalues = new ArrayList<>();
                            if (dataMap.containsKey(drugDetail.getParentId())) {
                                dataMapVvalues.addAll(dataMap.get(drugDetail.getParentId()));
                                if (drugDetail.getValue() != null) {
                                    dataMapVvalues.add(drugDetail);
                                    dataMap.put(drugDetail.getParentId(), dataMapVvalues);
                                }
                            } else {
                                if (drugDetail.getValue() != null) {
                                    dataMapVvalues.add(drugDetail);
                                    dataMap.put(drugDetail.getParentId(), dataMapVvalues);
                                }
                            }
                        }
                    }
                }

                for (int i = 0; i < adapterData.size(); i++) {
                    DrugDetail drugDetail = adapterData.get(i);
                    if (dataMap.containsKey(drugDetail.getDrugAttributeId())) {
                        finalAdapterData.add(drugDetail);
                    } else if (drugDetail.getValue() != null && !drugDetail.getValue().isEmpty()) {
                        finalAdapterData.add(drugDetail);
                    }
                }
                if (apiResponse.getDrugBrandList().size() > 0) {
                    DrugDetail drugDetail = new DrugDetail();
                    if (isBrandInfo) {
                        drugDetail.setLabel("Manufacture/pricing");
                    } else {
                        drugDetail.setLabel("Brands");
                    }
                    drugDetail.setDrugAttributeId(0002);
                    finalAdapterData.add(drugDetail);
                }
                List<DrugDetail> dataMapInteractionValues = new ArrayList<>();
                if (apiResponse.getDrugDetailsInteractions() != null) {
                    for (int i = 0; i < apiResponse.getDrugDetailsInteractions().size(); i++) {
                        DrugDetail drugDetail = new DrugDetail();
                        DrugDetailsInteractions drugDetailsInteractions = apiResponse.getDrugDetailsInteractions().get(i);
                        drugDetail.setLabel(drugDetailsInteractions.getDrug1() + " + " + drugDetailsInteractions.getDrug2());
                        drugDetail.setValuetype(drugDetailsInteractions.getType());
                        drugDetail.setValue(drugDetailsInteractions.getInteractionDetails());
                        drugDetail.setParentId(0001);
                        dataMapInteractionValues.add(drugDetail);
                        dataMap.put(0001, dataMapInteractionValues);
                    }
                }

                if (dataMapInteractionValues.size() > 0) {
                    DrugDetail drugDetail = new DrugDetail();
                    drugDetail.setLabel("Interactions");
                    drugDetail.setDrugAttributeId(0001);
                    finalAdapterData.add(drugDetail);
                }

                DrugDetail drugDetail1 = new DrugDetail();
                drugDetail1.setLabel("Feeds");
                drugDetail1.setDrugAttributeId(0003);
                finalAdapterData.add(drugDetail1);


                drugsPagerAdapter = new DrugsPagerAdapter(getSupportFragmentManager(), DrugDetailsActivity.this, finalAdapterData.size(), finalAdapterData, dataMap, drugName, apiResponse.getDrugBrandList(), isBrandInfo, apiResponse.getDrugBrandInfoList(), genericName, drugId);
                viewPager.setAdapter(drugsPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    tab.setCustomView(drugsPagerAdapter.getTabView(i));
                }

            } else {
                if (AppUtil.isJSONValid(apiResponse.getError())) {
                    try {
                        JSONObject jsonObject = new JSONObject(apiResponse.getError());
                        if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                            AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), DrugDetailsActivity.this);
                        } else if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                            AppUtil.AccessErrorPrompt(DrugDetailsActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                        } else if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                            Toast.makeText(DrugDetailsActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DrugDetailsActivity.this, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(DrugDetailsActivity.this, "Something went wrong,please try again.", Toast.LENGTH_SHORT).show();
                }
            }
            avLoadingIndicatorView.setVisibility(View.GONE);
        });
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    private void setRequestData(int id, boolean isBrandInfo) {
        drugDetailsViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_DRUG_INFO, "GET_DRUG_INFO", AppUtil.getRequestHeaders(DrugDetailsActivity.this), id, isBrandInfo);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();

        }
        return true;
    }
}