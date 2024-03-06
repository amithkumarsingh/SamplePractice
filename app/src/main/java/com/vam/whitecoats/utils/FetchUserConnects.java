package com.vam.whitecoats.utils;

import android.content.Context;

import com.android.volley.Request;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.MessageEvent;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class FetchUserConnects {

    private Context context;
    private int userId,lastUserId;
    private boolean isInitialResponseCallback;
    private OnReceiveResponse callBack;
    private Realm realm;
    private RealmManager realmManager;
    private MySharedPref mySharedPref;
    private int iteration=0;

    public FetchUserConnects(Context mContext, int _userId, int _lastUserId, boolean _isInitialResponseCallBack, Realm _realm,RealmManager _realmManager,OnReceiveResponse _callBack){
        context=mContext;
        userId=_userId;
        lastUserId=_lastUserId;
        isInitialResponseCallback=_isInitialResponseCallBack;
        callBack=_callBack;
        realm= _realm;
        realmManager=_realmManager;
        mySharedPref=new MySharedPref(context);
        getUserConnectsAPICall(prepareRequestObj(userId,lastUserId));
    }

    private void getUserConnectsAPICall(String request){
        new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.FETCH_USER_CONNECTS_API, request, "FETCH_USER_CONNECTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                try {
                    JSONObject responseJsonObj = new JSONObject(successResponse);
                    if (responseJsonObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        JSONObject dataObj = responseJsonObj.optJSONObject(RestUtils.TAG_DATA);
                        JSONArray usersArray = dataObj.optJSONArray("users");
                        int limit = dataObj.optInt("limit");
                        realmManager.clearConnects();
                        for (int count = 0; count < usersArray.length(); count++) {
                            JSONObject jsonObj = usersArray.optJSONObject(count);
                            ContactsInfo contactsInfo = new ContactsInfo();
                            contactsInfo.setDoc_id(jsonObj.optInt(RestUtils.TAG_USER_ID));
                            contactsInfo.setNetworkStatus(jsonObj.optString(RestUtils.TAG_CONNECT_STATUS));
                            /**
                             * Get the Details Json Object
                             */
                            contactsInfo.setEmail(jsonObj.optString(RestUtils.TAG_CNT_EMAIL));
                            contactsInfo.setPhno(jsonObj.optString(RestUtils.TAG_CNT_NUM));
                            contactsInfo.setQb_userid(jsonObj.optInt(RestUtils.TAG_QB_USER_ID));
                            contactsInfo.setSpeciality(jsonObj.optString(RestUtils.TAG_SPLTY));
                            contactsInfo.setSubSpeciality(jsonObj.optString(RestUtils.TAG_SUB_SPLTY, ""));
                            contactsInfo.setName(jsonObj.optString(RestUtils.TAG_FNAME)+" "+jsonObj.optString(RestUtils.TAG_LNAME));
                            contactsInfo.setLocation(jsonObj.optString(RestUtils.TAG_LOCATION));
                            contactsInfo.setPic_url(jsonObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
                            contactsInfo.setUserSalutation(jsonObj.optString(RestUtils.TAG_USER_SALUTAION));
                            contactsInfo.setUserTypeId(jsonObj.optInt(RestUtils.TAG_USER_TYPE_ID));
                            contactsInfo.setPhno_vis(jsonObj.optString(RestUtils.TAG_CNNTMUNVIS));
                            contactsInfo.setEmail_vis(jsonObj.optString(RestUtils.TAG_CNNTEMAILVIS));
                            /**
                             * Check whether doc_id exists in database, if exists then update it  else insert a new record.
                             */
                            boolean isDoctorExists = realmManager.isDoctorExists(realm, contactsInfo.getDoc_id());
                            if (isDoctorExists) {
                                realmManager.updateMyContacts(realm, contactsInfo);
                            } else {
                                realmManager.insertMyContacts(realm, contactsInfo, Integer.parseInt(contactsInfo.getNetworkStatus()));
                            }
                            if(count== usersArray.length()-1){
                                lastUserId=jsonObj.optInt(RestUtils.TAG_USER_ID);
                                mySharedPref.savePref("last_doc_id",lastUserId);
                            }
                        }

                        if(callBack!=null) {
                            if (isInitialResponseCallback && iteration == 0) {
                                callBack.onSuccessResponse(successResponse);
                            } else if (!isInitialResponseCallback && usersArray.length()<limit) {
                                callBack.onSuccessResponse(successResponse);
                            }
                        }
                        if(usersArray.length()<limit){
                            mySharedPref.savePref("last_doc_id",0);
                            mySharedPref.savePref("connects_sync_completed",true);
                            EventBus.getDefault().post(new MessageEvent("ConnectsFetchSuccess"));
                        }else{
                            iteration++;
                            getUserConnectsAPICall(prepareRequestObj(userId,lastUserId));
                        }
                    }else if(responseJsonObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                        callBack.onSuccessResponse(successResponse);
                        EventBus.getDefault().post(new MessageEvent("ConnectsFetchFail"));
                    }
                } catch (JSONException e) {
                    if(callBack!=null){
                        callBack.onErrorResponse(successResponse);
                    }
                    EventBus.getDefault().post(new MessageEvent("ConnectsFetchFail"));
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if(callBack!=null){
                    callBack.onErrorResponse(errorResponse);
                }
                EventBus.getDefault().post(new MessageEvent("ConnectsFetchFail"));
            }
        }).sendSinglePartRequest();
    }

    private String prepareRequestObj(int userId, int lastUserId) {
        JSONObject requestObj=new JSONObject();

        try {
            requestObj.put(RestUtils.TAG_USER_ID,userId);
            requestObj.put("last_user_id",lastUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj.toString();
    }
}
