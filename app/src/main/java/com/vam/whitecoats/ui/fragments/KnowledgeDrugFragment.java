package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.ActivityDrugClassBinding;
import com.vam.whitecoats.databinding.KnowledgeTabDrugBinding;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.activities.DrugSearchActivity;
import com.vam.whitecoats.ui.activities.DrugSubClassActivity;
import com.vam.whitecoats.ui.activities.DrugsActivity;
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


public class KnowledgeDrugFragment extends Fragment {


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


    public static KnowledgeDrugFragment newInstance(String param1, String param2) {
        KnowledgeDrugFragment fragment = new KnowledgeDrugFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View getHTabView = inflater.inflate(R.layout.knowledge_tab_drug, container, false);
//        Intent intent = new Intent(getActivity(), DrugsActivity.class);
//        startActivity(intent);


//        mInflater = LayoutInflater.from(this);
//        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
//        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        mTitleTextView.setVisibility(View.VISIBLE);
//        mTitleTextView.setText("Drug Class");

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
//        upshotEventData(0,0,0,"","DrugClass","","", " ",true);


        drugClassViewModel= ViewModelProviders.of(this).get(DrugClassViewModel.class);
        drugClassViewModel.setIsEmptyMsgVisibility(false);


        if(AppUtil.isConnectingToInternet(getActivity())) {
            drugClassViewModel.displayLoader();
            setRequestData();
        }
        KnowledgeTabDrugBinding knowledgeTabDrugBinding = DataBindingUtil.inflate(inflater,R.layout.knowledge_tab_drug,container,false);
        View getHTabView=knowledgeTabDrugBinding.getRoot();

        drugClassAdapter = new DrugClassAdapter((drugClass, position) -> {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("DrugClassName",drugClass.getDrugName());
                    jsonObject.put("DrugClassID",drugClass.getDrugId());
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugClassSelection",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getActivity(), DrugSubClassActivity.class);
                intent.putExtra("drugClassId", drugClass.getDrugId());
                startActivity(intent);
            }
        });
        groupData = new ArrayList<>();

        knowledgeTabDrugBinding.setAdapter(drugClassAdapter);
        knowledgeTabDrugBinding.setViewModel(drugClassViewModel);
        knowledgeTabDrugBinding.setLifecycleOwner(this);

        View view = knowledgeTabDrugBinding.getRoot();
        drugClassRecycler = knowledgeTabDrugBinding.drugRecycler;
        swipeRefreshLayout = knowledgeTabDrugBinding.drugClassRefresh;
        drugClassRecycler.setHasFixedSize(true);
        drugSearchEditText=getHTabView.findViewById(R.id.et_search_drug);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(getActivity())) {
                    setRequestData();
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        drugClassRecycler.setLayoutManager(linearLayoutManager);

        drugClassRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        drugClassViewModel.getAllDrugItems().observe(this.getActivity(), new Observer<ApiResponse>() {
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
                                AppUtil.showSessionExpireAlert("Error", getResources().getString(R.string.session_timedout), getActivity());
                            }
                            else if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")){
                                AppUtil.AccessErrorPrompt(getActivity(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
//                        swipeRefreshLayout.setRefreshing(false);
                        drugClassViewModel.setIsLoaderVisible(false);
                        drugClassViewModel.setIsEmptyMsgVisibility(true);
                        Toast.makeText(getActivity(),"Something went wrong,please try again.",Toast.LENGTH_SHORT).show();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        drugClassViewModel.isListExhausted().observe(getActivity(), new Observer<Boolean>() {
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
                Intent in=new Intent(getActivity(), DrugSearchActivity.class);
                in.putExtra("NavigationFrom","DrugClassActivity");
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

//        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(mCustomView);




        return getHTabView;
    }


    private void setRequestData() {
        drugClassViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_DRUG_CLASS_LIST, "GET_DRUG_CLASS_LIST",AppUtil.getRequestHeaders(getActivity()),null,"DrugClass");
    }

//    @Override
//    protected void setCurrentActivity() {
//        App_Application.setCurrentActivity(getActivity());
//    }

//    @Override
//    public void onBackPressed() {
//        if(!customBackButton) {
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("DocID", realmManager.getUserUUID(realm));
//                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugClassDeviceBackTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),DrugClassActivity.this);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        super.onBackPressed();
//    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"DrugClassBackTapped",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),getActivity());
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            onBackPressed();

        }
        return true;
    }

}
