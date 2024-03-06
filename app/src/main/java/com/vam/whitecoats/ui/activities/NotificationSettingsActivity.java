package com.vam.whitecoats.ui.activities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
//import com.quickblox.auth.session.QBSettings;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotificationSettingsInfo;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.NotificationSettingsAdapter;
import com.vam.whitecoats.ui.interfaces.NotificationSettingsSelection;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;



public class NotificationSettingsActivity extends BaseActionBarActivity {
    private RecyclerView settingsList;
    private NotificationSettingsAdapter notificationSettingsAdapter;
    //ArrayList<NotificationChannelInfo> mChannelList = new ArrayList<>();
    public static final String TAG = NotificationSettingsActivity.class.getSimpleName();
    private int docId = 0;
    private RealmManager realmManager;
    private Realm realm;
    private JSONObject notificationJsonRequest = null;
    protected View mCustomView;
    ToggleButton toggleButton_selectAll;
    private RealmResults<RealmNotificationSettingsInfo> mChannelList;
    int checkedItemCount, totalItemsCount;
    private ProgressBar toggleAllProgress;
    private MySharedPref sharePref;
    private boolean customBackButton=false;
    //Integer toggleTag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiction_setting);
        settingsList = findViewById(R.id.channels_list);
        toggleButton_selectAll = findViewById(R.id.toggleBtn_selectAll);
        toggleAllProgress=findViewById(R.id.allToggleProgress);
        sharePref=new MySharedPref(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(NotificationSettingsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        settingsList.setLayoutManager(mLayoutManager);

        this.realm = Realm.getDefaultInstance();
        this.realmManager = new RealmManager(this);
        docId = realmManager.getDoc_id(realm);
        setItemListData();

        mChannelList = realmManager.getNotificationSettingsFromDB();
        totalItemsCount = mChannelList.size();
        notificationSettingsAdapter = new NotificationSettingsAdapter(NotificationSettingsActivity.this, mChannelList, new NotificationSettingsSelection() {
            @Override
            public void toggleTapped(boolean checked, int index) {
                setItemUpdateListData(checked,mChannelList.get(index).getCategoryId());
            }
        });
        settingsList.setAdapter(notificationSettingsAdapter);


        mInflater = LayoutInflater.from(NotificationSettingsActivity.this);
        mCustomView = mInflater.inflate(R.layout.actionbar_profile, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        mTitleTextView.setText(getString(R.string.str_notification_settings_heading));
        next_button.setVisibility(View.GONE);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        toggleButton_selectAll.setTag(1);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                AppUtil.logUserUpShotEvent("NotificationSettingsDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        finish();

    }

    private void setItemListData() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.NOTIFICATION_SETTINGS_CATEGORIES, reqObj.toString(), "NOTIFICATION_SERVICE_CATEGORIES", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if (successResponse != null && !successResponse.isEmpty()) {
                        Log.i(TAG, "onSuccessResponse - " + successResponse);
                        try {
                            JSONObject responseObj = new JSONObject(successResponse);
                            JSONArray responseArray = new JSONArray();
                            if (responseObj.has(RestUtils.TAG_STATUS) && responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                if (responseObj.has(RestUtils.TAG_DATA)) {
                                    JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                    responseArray = dataObj.optJSONArray(RestUtils.TAG_NOTIFICAYTION_CATEGORIES);
                                    realmManager.clearNotificationCategoryRealmData();
                                    int len = responseArray.length();
                                    int count = 0;

                                    for (int i = 0; i < len; i++) {
                                        JSONObject channelResult = responseArray.optJSONObject(i);

                                        if(channelResult.optInt(RestUtils.TAG_CATEGORY_ID)==5){
                                           if(channelResult.optBoolean(RestUtils.TAG_IS_ENABLE)){
                                               //SubscribeService.subscribeToPushes(NotificationSettingsActivity.this, false);
                                               /*QBSettings.getInstance().setEnablePushNotification(true);
                                               //subscribe
                                               if(!QBSettings.getInstance().isEnablePushNotification()) {

                                               }*/
                                               sharePref.savePref("QB_PUSH_SUBSCRIPTION",true);
                                           }else{
                                               //unsubscribe
                                              /* QBSettings.getInstance().setEnablePushNotification(false);
                                               if(QBSettings.getInstance().isEnablePushNotification()) {

                                               }*/
                                               sharePref.savePref("QB_PUSH_SUBSCRIPTION",false);
                                               //SubscribeService.unSubscribeFromPushes(NotificationSettingsActivity.this);
                                           }
                                        }
                                        realmManager.insertNotificationSettngsInfo(realm, channelResult.optInt(RestUtils.TAG_CATEGORY_ID), channelResult.optBoolean(RestUtils.TAG_IS_ENABLE), channelResult.toString());
                                    }

                                    boolean isAllChecked = true;
                                    for (int index =0; index < mChannelList.size(); index++){
                                        if (mChannelList.get(index).isEnabled() == false) {
                                            isAllChecked = false;
                                        }
                                    }
//                                    checkedItemCount = count;

                                    setToggleButtonState(isAllChecked);
                                    notificationSettingsAdapter.setSelectedItemCount(checkedItemCount);
                                    notificationSettingsAdapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i(TAG, "onErrorResponse()");
                    if (errorResponse != null) {
                        //revert back to pre stage
                        try {
                            if (!errorResponse.isEmpty()) {
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                if (errorMessage != null && !errorMessage.isEmpty()) {
                                    Toast.makeText(NotificationSettingsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).sendSinglePartRequest();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //toggleButton_selectAll.setTag(1);
        toggleButton_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleButton_selectAll.getTag() == null || (Integer) toggleButton_selectAll.getTag() == 0){
                    toggleButton_selectAll.setTag(1);
                    return;
                }
                toggleAllProgress.setVisibility(View.VISIBLE);
                toggleButton_selectAll.setVisibility(View.GONE);
                realmManager.updateNotificationSettingsFromDB(isChecked);
                notificationSettingsAdapter.notifyDataSetChanged();
                checkAllOrNone(isChecked, mChannelList);
                if (isChecked) {
                    checkedItemCount = totalItemsCount;
                } else {
                    checkedItemCount = 0;
                }
                /*if (toggleButton_selectAll.isChecked()) {
                    checkAllOrNone(true, checkedItemCount);
                    toggleButton_selectAll.setChecked(true);
                } else {
                    checkedItemCount = 0;
                    checkAllOrNone(false, mChannelList);
                    toggleButton_selectAll.setChecked(false);
                }*/
            }
        });
    }


    private void setToggleButtonState(Boolean isAllChecked) {
        if (toggleButton_selectAll.isChecked() != isAllChecked) {
            toggleButton_selectAll.setTag(0);
            toggleButton_selectAll.setChecked(isAllChecked);
        }
    }

    private void setItemUpdateListData(boolean isChecked, int categoryId) {
        JSONObject mainObj = new JSONObject();
        JSONArray array = new JSONArray();
        try {

            JSONObject obj = new JSONObject();
            obj.put(RestUtils.TAG_CATEGORY_ID, categoryId);
            obj.put(RestUtils.TAG_IS_ENABLE, isChecked);
            array.put(obj);
            mainObj.put(RestUtils.TAG_USER_ID, docId);
            mainObj.put(RestUtils.TAG_NOTIFICAYTION_CATEGORIES, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setItemUpdateListData(mainObj,false,"NOTIFICATOIN_CATEGORY_ID"+categoryId);

    }

    private void checkAllOrNone(boolean isChecked, RealmResults<RealmNotificationSettingsInfo> mChannelList) {
        Log.i(TAG, "checkAllorNone() : "+isChecked);
        JSONObject mainObj = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            for (int index = 0; index < mChannelList.size(); index++) {
                JSONObject obj = new JSONObject();
                obj.put(RestUtils.TAG_CATEGORY_ID, mChannelList.get(index).getCategoryId());
                obj.put(RestUtils.TAG_IS_ENABLE, isChecked);
                array.put(obj);
            }
            mainObj.put(RestUtils.TAG_USER_ID, docId);
            mainObj.put(RestUtils.TAG_NOTIFICAYTION_CATEGORIES, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //volley service call
        setItemUpdateListData(mainObj,true,"NOTIFICATION_TOGGLE_ALL");

    }

    private void setItemUpdateListData(JSONObject mainObj,boolean isFromAllToggle,String tag) {
        App_Application.getInstance().cancelPendingRequests(tag);
        try {
            new VolleySinglePartStringRequest(NotificationSettingsActivity.this, Request.Method.POST, RestApiConstants.NOTIFICATION_SETTINGS_UPDATE, mainObj.toString(), tag, new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if(isFromAllToggle){
                        toggleAllProgress.setVisibility(View.GONE);
                        toggleButton_selectAll.setVisibility(View.VISIBLE);
                    }
                    if (successResponse != null && !successResponse.isEmpty()) {
                        Log.i(TAG, "onSuccessResponse - " + successResponse);
                        try {
                            JSONObject responseObj = new JSONObject(successResponse);
                            JSONArray responseArray = new JSONArray();
                            if (responseObj.has(RestUtils.TAG_STATUS) && responseObj.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                if (responseObj.has(RestUtils.TAG_DATA)) {
                                    JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                    dataObj.optString(RestUtils.TAG_USER_ID);
                                    responseArray = dataObj.optJSONArray(RestUtils.TAG_NOTIFICAYTION_CATEGORIES);
                                    int len = responseArray.length();
                                    int enabledCount = 0;
                                    for (int index = 0; index < len; index++) {
                                        JSONObject categoryResult = responseArray.optJSONObject(index);
                                        if(categoryResult.optInt(RestUtils.TAG_CATEGORY_ID)==5){
                                            if(categoryResult.optBoolean(RestUtils.TAG_IS_ENABLE)){
                                                //subscribe
                                                /*if(!QBSettings.getInstance().isEnablePushNotification()) {
                                                    QBSettings.getInstance().setEnablePushNotification(true);
                                                }*/
                                                sharePref.savePref("QB_PUSH_SUBSCRIPTION",true);
                                            }else{
                                                //unsubscribe
                                                /*if(QBSettings.getInstance().isEnablePushNotification()) {
                                                    QBSettings.getInstance().setEnablePushNotification(false);
                                                }*/
                                                sharePref.savePref("QB_PUSH_SUBSCRIPTION",false);
                                            }
                                        }
                                        realmManager.updateNotificationCategoryId(categoryResult.optInt(RestUtils.TAG_CATEGORY_ID), categoryResult.optBoolean(RestUtils.TAG_IS_ENABLE), categoryResult.toString());
                                        if (categoryResult.optBoolean(RestUtils.TAG_IS_ENABLE) == true) {
                                            enabledCount ++;
                                        }
                                    }

                                    boolean isAllChecked = true;
                                    for (int index =0; index < mChannelList.size(); index++){
                                        if (mChannelList.get(index).isEnabled() == false) {
                                            isAllChecked = false;
                                            break;
                                        }
                                    }
//                                    checkedItemCount = count;

                                    /*if(isAllChecked){
                                        //subscribe
                                        if(!QBSettings.getInstance().isEnablePushNotification()) {
                                            QBSettings.getInstance().setEnablePushNotification(true);
                                        }
                                    }else{
                                        //un
                                        if(QBSettings.getInstance().isEnablePushNotification()) {
                                            QBSettings.getInstance().setEnablePushNotification(false);
                                        }
                                    }*/
                                    setToggleButtonState(isAllChecked);
                                    notificationSettingsAdapter.notifyDataSetChanged();
//                                    if(enabledCount == mChannelList.size()) {
//                                        toggleButton_selectAll.setTag(0);
//                                        toggleButton_selectAll.setChecked(true);
//                                    }else {
//                                        toggleButton_selectAll.setTag(0);
//                                        toggleButton_selectAll.setChecked(false);
//                                    }
                                }
                            }
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i(TAG, "onErrorResponse()");
                    if(isFromAllToggle){
                        toggleAllProgress.setVisibility(View.GONE);
                        toggleButton_selectAll.setVisibility(View.VISIBLE);
                    }
                    if (errorResponse != null) {
                        //revert back to pre stage
                        try {
                            if (!errorResponse.isEmpty()) {
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                if (errorMessage != null && !errorMessage.isEmpty()) {
                                    Toast.makeText(NotificationSettingsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).sendSinglePartRequest();

        } catch (Exception e) {
            e.printStackTrace();
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
                    AppUtil.logUserUpShotEvent("NotificationSettingsBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
