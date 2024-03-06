package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.StrongBoxUnavailableException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.ActivityDrugsBinding;
import com.vam.whitecoats.ui.adapters.DrugAdapter;
import com.vam.whitecoats.ui.interfaces.DrugSubClassListener;
import com.vam.whitecoats.utils.ApiResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.DrugsViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class DrugsActivity extends BaseActionBarActivity {
    private RecyclerView drugRecycler;
    private DrugAdapter drugAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isListExhausted;
    private int page_no = 0;
    private Intent intent;

    private DrugsViewModel drugsViewModel;
    private int drugSubClassId;
    private boolean loading = false;
    private EditText drugSearchEditText;
    private TextView mTitleTextView;
    private Realm realm;
    private RealmManager realmManager;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drugs);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText("Drugs");
        //intent = getIntent();

        drugsViewModel= ViewModelProviders.of(this).get(DrugsViewModel.class);
        drugsViewModel.setIsEmptyMsgVisibility(false);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        upshotEventData(0,0,0,"","DrugName","","", " ",true);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            drugSubClassId = bundle.getInt("drugSubClassId",0);
        }

        if(AppUtil.isConnectingToInternet(DrugsActivity.this)) {
            drugsViewModel.displayLoader();
            setRequestData(drugSubClassId,page_no);
        }
        ActivityDrugsBinding activityDrugsBinding =  DataBindingUtil.setContentView(DrugsActivity.this,R.layout.activity_drugs);
        drugAdapter = new DrugAdapter(new DrugSubClassListener() {
            @Override
            public void onItemClick(DrugSubClass drugClass, Integer position) {
                if(AppUtil.isConnectingToInternet(DrugsActivity.this)) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("DocID",realmManager.getUserUUID(realm));
                        jsonObject.put("DrugName",drugClass.getDrugName());
                        jsonObject.put("DrugNameID",drugClass.getDrugId());
                        jsonObject.put(RestUtils.EVENT_DOC_SPECIALITY,realmManager.getDocSpeciality(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugNameSelection",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugsActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(DrugsActivity.this, DrugDetailsActivity.class);
                    intent.putExtra("drugId", drugClass.getDrugId());
                    intent.putExtra("drugName", drugClass.getDrugName());
                    startActivity(intent);
                }

            }
        });

        activityDrugsBinding.setAdapter(drugAdapter);
        activityDrugsBinding.setViewModel(drugsViewModel);
        activityDrugsBinding.setLifecycleOwner(this);

        View view = activityDrugsBinding.getRoot();
        drugRecycler = activityDrugsBinding.drugRecycler;
        swipeRefreshLayout = activityDrugsBinding.drugsRefersh;
        drugRecycler.setHasFixedSize(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(DrugsActivity.this)) {
                    isListExhausted=false;
                    page_no = 0;
                    setRequestData(drugSubClassId,page_no);
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrugsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        drugRecycler.setLayoutManager(linearLayoutManager);

        drugRecycler.addItemDecoration(new SimpleDividerItemDecoration(DrugsActivity.this));
        drugSearchEditText=findViewById(R.id.et_search_drug);

        drugRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    if(isListExhausted || !AppUtil.isConnectingToInternet(DrugsActivity.this)){
                        return;
                    }
                    int visibleItemCount = recyclerView.getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (!loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            drugAdapter.addDummyItemToList();
                            loading = true;
                            //Do pagination.. i.e. fetch new data
                            page_no += 1;
                            setRequestData(drugSubClassId,page_no);
                        }
                        else{
                            loading = false;
                        }
                    }
                }
            }
        });

        drugsViewModel.getAllDrugItems().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                drugAdapter.removeDummyItemFromList();
                if(apiResponse==null){
                    return;
                }
                if(apiResponse.getError()==null){
                    drugsViewModel.displayUIBasedOnCount(apiResponse.getObjList().size());
                    drugAdapter.setDataList(apiResponse.getObjList());
                }else{
                    if(AppUtil.isJSONValid(apiResponse.getError())){
                        try {
                            JSONObject jsonObject=new JSONObject(apiResponse.getError());
                            if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")){
                                AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), DrugsActivity.this);
                            }
                            else if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")){
                                AppUtil.AccessErrorPrompt(DrugsActivity.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(DrugsActivity.this,"Something went wrong,please try again.",Toast.LENGTH_SHORT).show();
                    }
                    if(page_no == 0)
                    {
                        drugsViewModel.setIsEmptyMsgVisibility(true);
                    }
                }
                loading = false;
                drugsViewModel.setIsLoaderVisible(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        drugsViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted=aBoolean;
                if(aBoolean) {
                    drugAdapter.setListExhausted();
                }
            }
        });

        drugSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(DrugsActivity.this,DrugSearchActivity.class);
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("NavigationFrom","DrugsActivity");
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

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void setRequestData(int id,int pageNo) {
        drugsViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_DRUG_LIST, "GET_DRUG_LIST",AppUtil.getRequestHeaders(DrugsActivity.this),id,pageNo);
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugNameDeviceBackTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),DrugsActivity.this);
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
            customBackButton= true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugNameBackTapped",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),DrugsActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }
}