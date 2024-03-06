package com.vam.whitecoats.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.ActivityDrugClassBinding;
import com.vam.whitecoats.ui.adapters.DrugClassAdapter;
import com.vam.whitecoats.utils.ApiResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.DrugClassViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class DrugClassActivity extends BaseActionBarActivity {
    private RecyclerView drugClassRecycler;
    private DrugClassAdapter drugClassAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private boolean isListExhausted;
    int c = 0;
    int j=0;

    private ArrayList<Integer> groupData;


    private DrugClassViewModel drugClassViewModel;
    private EditText drugSearchEditText;
    private TextView mTitleTextView;
    private Realm realm;
    private RealmManager realmManager;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_class);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText("Drug Class");

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        upshotEventData(0,0,0,"","DrugClass","","", " ",true);


        drugClassViewModel= ViewModelProviders.of(this).get(DrugClassViewModel.class);
        drugClassViewModel.setIsEmptyMsgVisibility(false);


        if(AppUtil.isConnectingToInternet(DrugClassActivity.this)) {
            drugClassViewModel.displayLoader();
            setRequestData();
        }
        ActivityDrugClassBinding activityDrugClassBinding =  DataBindingUtil.setContentView(DrugClassActivity.this,R.layout.activity_drug_class);
        drugClassAdapter = new DrugClassAdapter((drugClass, position) -> {
            if (AppUtil.isConnectingToInternet(DrugClassActivity.this)) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("DrugClassName",drugClass.getDrugName());
                    jsonObject.put("DrugClassID",drugClass.getDrugId());
                    jsonObject.put(RestUtils.EVENT_DOC_SPECIALITY,realmManager.getDocSpeciality(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugClassSelection",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugClassActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(DrugClassActivity.this, DrugSubClassActivity.class);
                intent.putExtra("drugClassId", drugClass.getDrugId());
                startActivity(intent);
            }
        });
        groupData = new ArrayList<>();

        activityDrugClassBinding.setAdapter(drugClassAdapter);
        activityDrugClassBinding.setViewModel(drugClassViewModel);
        activityDrugClassBinding.setLifecycleOwner(this);

        View view = activityDrugClassBinding.getRoot();
        drugClassRecycler = activityDrugClassBinding.drugRecycler;
        swipeRefreshLayout = activityDrugClassBinding.drugClassRefresh;
        drugClassRecycler.setHasFixedSize(true);
        drugSearchEditText=findViewById(R.id.et_search_drug);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(DrugClassActivity.this)) {
                    setRequestData();
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrugClassActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        drugClassRecycler.setLayoutManager(linearLayoutManager);

        drugClassRecycler.addItemDecoration(new SimpleDividerItemDecoration(DrugClassActivity.this));
        drugClassViewModel.getAllDrugItems().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                drugClassAdapter.removeDummyItemFromList();
                groupData.clear();
                c=0;
                j=0;
                if(apiResponse==null){
                    return;
                }
                if(apiResponse.getError()==null){
                    drugClassViewModel.displayUIBasedOnCount(apiResponse.getObjList().size());
                    List<DrugClass> drugClassList = apiResponse.getObjList();
                    for(int i=0;i<drugClassList.size();i++){
                        DrugClass drugClass = drugClassList.get(i);
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
                    drugClassAdapter.setDataList(apiResponse.getObjList(),groupData);
                }else{
                    if(AppUtil.isJSONValid(apiResponse.getError())){
                        try {
                            JSONObject jsonObject=new JSONObject(apiResponse.getError());
                            if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")){
                                AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), DrugClassActivity.this);
                            }
                            else if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")){
                                AppUtil.AccessErrorPrompt(DrugClassActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
//                        swipeRefreshLayout.setRefreshing(false);
                        drugClassViewModel.setIsLoaderVisible(false);
                        drugClassViewModel.setIsEmptyMsgVisibility(true);
                        Toast.makeText(DrugClassActivity.this,"Something went wrong,please try again.",Toast.LENGTH_SHORT).show();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        drugClassViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted=aBoolean;
                if(aBoolean) {
                    //Toast.makeText(getActivity(),"No more categories",Toast.LENGTH_SHORT).show();
                    drugClassAdapter.setListExhausted();
                }
            }
        });
        drugSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(DrugClassActivity.this,DrugSearchActivity.class);
                in.putExtra("NavigationFrom","DrugClassActivity");
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        drugClassViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_DRUG_CLASS_LIST, "GET_DRUG_CLASS_LIST",AppUtil.getRequestHeaders(DrugClassActivity.this),null,"DrugClass");
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
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugClassDeviceBackTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),DrugClassActivity.this);
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
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugClassBackTapped",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugClassActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }
}