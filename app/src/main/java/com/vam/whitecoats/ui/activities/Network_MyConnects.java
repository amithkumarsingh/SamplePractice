package com.vam.whitecoats.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.MessageEvent;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmMyContactsInfo;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.ConnectsRecyclerViewAdapter;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.FetchUserConnects;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by pardhasaradhid on 12/26/2017.
 */

public class Network_MyConnects extends BaseActionBarActivity {

    private TextView mTitleTextView;
    private ImageView next_button;
    private Realm realm;
    private RealmManager realmManager;
    private int doctorId;
    private ConnectsRecyclerViewAdapter connectsRecyclerViewAdapter;
    private ArrayList<ContactsInfo> contactslist = new ArrayList<>();
    private RecyclerView connectsRecyclerList;
    private SwipeRefreshLayout swipeContainer;
    private JSONObject requestData = new JSONObject();
    private ProgressBar connectsLoadingProgress;
    private boolean customBackButton=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_myconnects);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        next_button = (ImageView) mCustomView.findViewById(R.id.next_button);
        connectsRecyclerList = (RecyclerView) findViewById(R.id.connects_recycler_list);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        connectsLoadingProgress=(ProgressBar)findViewById(R.id.connectsLoadingProgress);
        connectsRecyclerList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        connectsRecyclerList.setLayoutManager(linearLayoutManager);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorId = realmManager.getDoc_id(realm);
        mTitleTextView.setText("My Connects");
        RealmResults<RealmMyContactsInfo> connectsList = realmManager.getMyContactsDB(realm);
        connectsRecyclerViewAdapter = new ConnectsRecyclerViewAdapter(mContext, contactslist);
        sortConnects(contactslist);
        connectsRecyclerViewAdapter.setConnectsList(connectsList);
        connectsRecyclerList.setAdapter(connectsRecyclerViewAdapter);
        try {
            requestData.put(RestUtils.TAG_DOC_ID, doctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!mySharedPref.getPrefsHelper().getPref("connects_sync_completed",false)){
            connectsLoadingProgress.setVisibility(View.VISIBLE);
            connectsRecyclerList.setVisibility(View.GONE);
            if (AppUtil.isConnectingToInternet(mContext)) {
                loadConnects(true);
            }
        }else{
            connectsLoadingProgress.setVisibility(View.GONE);
            connectsRecyclerList.setVisibility(View.VISIBLE);
        }

        /**
         * sync contacts
         *
         */
        /*if (MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_SYNC_CONTACTS_UPDATED, 0) == 0) {
            //MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SYNC_CONTACTS, true);
            if (AppUtil.isConnectingToInternet(mContext)) {
                loadConnects(true);
            }
        }*/
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(mContext)) {
                    loadConnects(false);
                } else {
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        connectsRecyclerList.setAdapter(connectsRecyclerViewAdapter);
        connectsRecyclerList.addItemDecoration(HeaderDecoration.with(connectsRecyclerList)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));


        next_button.setVisibility(View.GONE);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        updateMyConnectsBroadcastManagers();
    }


    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    private void loadConnects(final boolean isFromConnectSync) {
        if (!isFromConnectSync) {
            showProgress();
        }
        new FetchUserConnects(Network_MyConnects.this, doctorId, 0, false, realm, realmManager, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (!isFromConnectSync) {
                    hideProgress();
                }
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                connectsRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                if (!isFromConnectSync) {
                    hideProgress();
                    displayErrorScreen(errorResponse);
                }
            }
        });
        /*new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.RESTORE_USER_CONNECTS, requestData.toString(), "CONNECTS_FRAGMENT", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (!isFromConnectSync) {
                    hideProgress();
                }
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                try {
                    JSONObject responseJsonObj = new JSONObject(successResponse);
                    if (responseJsonObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SYNC_CONTACTS_UPDATED, 1);
                        JSONArray jsonArray = responseJsonObj.optJSONArray(RestUtils.TAG_DATA);
                        int size = jsonArray.length();
                        contactslist.clear();
                        for (int count = 0; count < size; count++) {
                            JSONObject jsonObj = jsonArray.optJSONObject(count);
                            ContactsInfo contactsInfo = new ContactsInfo();
                            contactsInfo.setDoc_id(jsonObj.optInt(RestUtils.TAG_DOC_ID));
                            contactsInfo.setNetworkStatus(jsonObj.optString(RestUtils.TAG_CONNECT_STATUS));
                            *//**
                             * Get the Details Json Object
                             *//*
                            JSONObject detailsJsonObj = new JSONObject(jsonObj.optString("card_info"));
                            contactsInfo.setEmail(detailsJsonObj.optString(RestUtils.TAG_CNT_EMAIL));
                            contactsInfo.setPhno(detailsJsonObj.optString(RestUtils.TAG_CNT_NUM));
                            contactsInfo.setQb_userid(detailsJsonObj.optInt(RestUtils.TAG_QB_USER_ID));
                            contactsInfo.setSpeciality(detailsJsonObj.optString(RestUtils.TAG_SPLTY));
                            contactsInfo.setSubSpeciality(detailsJsonObj.optString(RestUtils.TAG_SUB_SPLTY));
                            contactsInfo.setName((detailsJsonObj.has(RestUtils.TAG_USER_FULL_NAME)) ? detailsJsonObj.optString(RestUtils.TAG_USER_FULL_NAME) : detailsJsonObj.optString(RestUtils.TAG_FULL_NAME));
                            contactsInfo.setLocation(detailsJsonObj.optString(RestUtils.TAG_LOCATION));
                            contactsInfo.setWorkplace(detailsJsonObj.optString(RestUtils.TAG_WORKPLACE));
                            contactsInfo.setDesignation(detailsJsonObj.optString(RestUtils.TAG_DESIGNATION));
                            contactsInfo.setDegree(detailsJsonObj.optString(RestUtils.TAG_DEGREE));
                            contactsInfo.setPic_name(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                            contactsInfo.setPic_url(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
                            contactsInfo.setUserSalutation(detailsJsonObj.optString(RestUtils.TAG_USER_SALUTAION));
                            contactsInfo.setUserTypeId(detailsJsonObj.optInt(RestUtils.TAG_USER_TYPE_ID));
                            *//**
                             * Check whether doc_id exists in database, if exists then update it  else insert a new record.
                             *//*
                            boolean isDoctorExists = realmManager.isDoctorExists(realm, contactsInfo.getDoc_id());
                            if (isDoctorExists) {
                                realmManager.updateMyContacts(realm, contactsInfo);
                            } else {
                                realmManager.insertMyContacts(realm, contactsInfo, Integer.parseInt(contactsInfo.getNetworkStatus()));
                            }

                        }
                        connectsRecyclerViewAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                if (!isFromConnectSync) {
                    hideProgress();
                    String errorMsg = "Something went wrong,please try after sometime.";
                    displayErrorScreen(errorResponse);
                }
            }
        }).sendSinglePartRequest();*/
    }

    private void sortConnects(ArrayList<ContactsInfo> connectsList) {

        Collections.sort(connectsList, new Comparator<ContactsInfo>() {

            @Override
            public int compare(ContactsInfo lhs, ContactsInfo rhs) {
                try {
                    return lhs.getName().toUpperCase().compareTo(rhs.getName().toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!realm.isClosed()) {
            realm.close();
        }
        EventBus.getDefault().unregister(this);
    }


    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * This method simply displays the progress dialog if it
     * is currently not showing on UI.
     */
    public synchronized void showProgress() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        loadMyConnects();
    }

    private void loadMyConnects() {
        if (connectsRecyclerViewAdapter != null) {
//            contactslist.clear();
//            RealmResults<RealmMyContactsInfo> connectsList = realmManager.getMyContactsDB(realm);
//            contactslist.addAll(realmManager.getMyContacts(realm));
//            sortConnects(contactslist);
//            connectsRecyclerViewAdapter.setConnectsList(connectsList);
            connectsRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void updateMyConnectsBroadcastManagers() {
        BroadcastReceiver updateMyConnectsBroadcastReceiver = new UpdateMyConnectsBroadcastReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(updateMyConnectsBroadcastReceiver,
                new IntentFilter("update_my_connects"));
    }

    private class UpdateMyConnectsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (connectsRecyclerViewAdapter != null) {
//                contactslist.clear();
//                contactslist.addAll(realmManager.getMyContacts(realm));
//                sortConnects(contactslist);
                connectsRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    AppUtil.logUserUpShotEvent("ConnectsBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getEvent().equalsIgnoreCase("ConnectsFetchSuccess")) {
            connectsLoadingProgress.setVisibility(View.GONE);
            connectsRecyclerList.setVisibility(View.VISIBLE);
        } else {
            connectsLoadingProgress.setVisibility(View.GONE);
            Toast.makeText(Network_MyConnects.this, "Unable to load connects,Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("ConnectsDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
}
