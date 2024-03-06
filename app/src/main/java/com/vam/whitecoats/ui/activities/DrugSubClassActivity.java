package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.doceree.androidadslibrary.ads.DocereeAdListener;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.constants.SlotLocationType;
import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.core.realm.RealmAdSlotInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.ActivityDrugSubClassBinding;
import com.vam.whitecoats.ui.adapters.DrugSubClassAdapter;
import com.vam.whitecoats.ui.interfaces.DrugSubClassListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.DrugClassViewModel;
import com.doceree.androidadslibrary.ads.AdRequest;
import com.doceree.androidadslibrary.ads.DocereeAdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DrugSubClassActivity extends BaseActionBarActivity {
    private RecyclerView drugSubClassRecycler;
    private DrugSubClassAdapter drugSubClassAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    int c = 0;
    int j=0;

    private boolean isListExhausted;
    private Intent intent;

    private int drugClassId;
    private ArrayList<Integer> groupData;
    private DrugClassViewModel drugSubClassViewModel;
    private EditText drugSearchEditText;
    private TextView mTitleTextView;
    //private DocereeAdView docereeAdView;
    private RealmManager realmManager;
    private Realm realm;
    private boolean customBackButton=false;
    private RelativeLayout rl_doceree_ad_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_sub_class);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText("Drug SubClass");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            drugClassId=bundle.getInt("drugClassId",0);
        }
        drugSubClassViewModel= ViewModelProviders.of(this).get(DrugClassViewModel.class);
        drugSubClassViewModel.setIsEmptyMsgVisibility(false);
        upshotEventData(0,0,0,"","DrugSubClass","","", " ",true);



        if(AppUtil.isConnectingToInternet(DrugSubClassActivity.this)) {
            drugSubClassViewModel.displayLoader();
            setRequestData();
        }
        ActivityDrugSubClassBinding activityDrugClassBinding =  DataBindingUtil.setContentView(DrugSubClassActivity.this,R.layout.activity_drug_sub_class);
        drugSubClassAdapter = new DrugSubClassAdapter(new DrugSubClassListener() {
            @Override
            public void onItemClick(DrugSubClass drugClass, Integer position) {
                if(AppUtil.isConnectingToInternet(DrugSubClassActivity.this)) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("DocID",realmManager.getUserUUID(realm));
                        jsonObject.put("DrugSubClassName",drugClass.getDrugName());
                        jsonObject.put("DrugSubClassID",drugClass.getDrugId());
                        jsonObject.put(RestUtils.EVENT_DOC_SPECIALITY,realmManager.getDocSpeciality(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugSubClassSelection",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugSubClassActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(DrugSubClassActivity.this, DrugsActivity.class);
                    intent.putExtra("drugSubClassId", drugClass.getDrugId());
                    startActivity(intent);
                }
            }
        });
        groupData = new ArrayList<>();

        activityDrugClassBinding.setAdapter(drugSubClassAdapter);
        activityDrugClassBinding.setViewModel(drugSubClassViewModel);
        activityDrugClassBinding.setLifecycleOwner(this);

        View view = activityDrugClassBinding.getRoot();
        drugSubClassRecycler = activityDrugClassBinding.drugSubClassRecycler;
        swipeRefreshLayout = activityDrugClassBinding.drugSubClassRefresh;
        rl_doceree_ad_layout=activityDrugClassBinding.docereeAdLayout;
        //rl_content_layout=activityDrugClassBinding.rlContentLayout;
        //drugSearchLayout=activityDrugClassBinding.drugSearchLayout;
        drugSubClassRecycler.setHasFixedSize(true);
        drugSearchEditText=findViewById(R.id.et_search_drug);
        //docereeAdView=findViewById(R.id.doceree_ad_view);
        RealmResults<RealmAdSlotInfo> adSlotResults = realmManager.getAdSlotInfoByLocation(SlotLocationType.DRUG_SUBCLASS_SLOT.getType());
        if(adSlotResults.size()>0){
            try {
                DocereeAdView docereeAdView = new DocereeAdView(this);
                docereeAdView.setVisibility(View.GONE);
                rl_doceree_ad_layout.addView(docereeAdView);
                docereeAdView.setAdSlotId(adSlotResults.get(0).getSource_slot_id());
                docereeAdView.setAdSize(adSlotResults.get(0).getDimensions());
                final RealmAdSlotInfo adSlotInfo = adSlotResults.get(0);
                AdRequest adRequest = new AdRequest.AdRequestBuilder().build();
                docereeAdView.loadAd(adRequest, new DocereeAdListener() {
                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        AppUtil.logAdEvent(adSlotInfo, "Ad_clicked", realmManager.getDoc_id(realm), DrugSubClassActivity.this);
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        docereeAdView.setVisibility(View.VISIBLE);
                        AppUtil.logAdEvent(adSlotInfo, "Ad_loaded", realmManager.getDoc_id(realm), DrugSubClassActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(String message) {
                        super.onAdFailedToLoad(message);
                        docereeAdView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }
                });
            }catch (IllegalArgumentException e){
                Log.e("LOADING_EXCEPTION",e.getLocalizedMessage());
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(DrugSubClassActivity.this)) {
                    setRequestData();
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrugSubClassActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        drugSubClassRecycler.setLayoutManager(linearLayoutManager);
        drugSubClassRecycler.addItemDecoration(new SimpleDividerItemDecoration(DrugSubClassActivity.this));

        drugSubClassViewModel.getAllDrugItems().observe(this, apiResponse -> {
            groupData.clear();
            c=0;
            j=0;
            if(apiResponse==null){
                return;
            }
            if(apiResponse.getError()==null){
                drugSubClassViewModel.displayUIBasedOnCount(apiResponse.getObjList().size());
                List<DrugSubClass> drugClassList = new ArrayList<>();
                drugClassList = apiResponse.getObjList();
                for(int i=0;i<drugClassList.size();i++){
                    DrugSubClass drugClass = drugClassList.get(i);
                    if(groupData.size() == 0){
                        groupData.add(c,j);
                        c++;
                        j++;
                    }
                    else{
                        if(drugClass.getDrugName().substring(0,1).equalsIgnoreCase(drugClassList.get(i-1).getDrugName().substring(0,1))){
                            groupData.add(c,j);
                            c++;
                            j++;
                        }
                        else{
                            j=0;
                            groupData.add(c,j);
                            c++;
                            j++;
                        }
                    }
                }
                Log.i("groupdata",groupData.toString());
                drugSubClassAdapter.setDataList(apiResponse.getObjList(),groupData);
            }else{
                if(AppUtil.isJSONValid(apiResponse.getError())){
                    try {
                        JSONObject jsonObject=new JSONObject(apiResponse.getError());
                        if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")){
                            AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), DrugSubClassActivity.this);
                        }
                        else if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")){
                            AppUtil.AccessErrorPrompt(DrugSubClassActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    drugSubClassViewModel.setIsLoaderVisible(false);
                    drugSubClassViewModel.setIsEmptyMsgVisibility(true);
                    Toast.makeText(DrugSubClassActivity.this,"Something went wrong,please try again.",Toast.LENGTH_SHORT).show();
                }
            }
            swipeRefreshLayout.setRefreshing(false);

        });
        drugSubClassViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted=aBoolean;
                if(aBoolean) {
                    drugSubClassAdapter.setListExhausted();
                }
            }
    });

        drugSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(DrugSubClassActivity.this,DrugSearchActivity.class);
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("NavigationFrom","DrugSubClassActivity");
                startActivity(in);
            }
        });

        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }
    private void setRequestData() {
        drugSubClassViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_DRUG_SUB_CLASS_LIST, "GET_DRUG_SUB_CLASS_LIST",AppUtil.getRequestHeaders(DrugSubClassActivity.this), prepareGetDrugSubclassRequest(),"DrugSubClass");
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugSubClassDeviceBackTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),DrugSubClassActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugSubClassBackTapped",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugSubClassActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }

    private String prepareGetDrugSubclassRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.DRUG_CLASS_ID,drugClassId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

}