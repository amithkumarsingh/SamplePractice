package com.vam.whitecoats.databinding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.core.models.NotificationInfoRoot;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.ApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationsRepository    {
    private Map<String, String> headers;
    private ArrayList<NotificationInfo> notificationList = new ArrayList<>();
    private SingleLiveEvent<ApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();
    private int methodType;
    private String requestURL,requestObj,mTag;
    /*public NotificationsRepository(int methodType, String url, String request, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.requestObj=request;
        this.mTag=mTag;
        this.headers=headers;
    }*/


    public void initRequest(int methodType, String url, String request, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.requestObj=request;
        this.mTag=mTag;
        this.headers=headers;
    }

    public SingleLiveEvent<ApiResponse> getNotificationList() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, requestObj, mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new ApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            NotificationInfoRoot notificationInfoRoot = gson.fromJson(successResponse, NotificationInfoRoot.class);
                            List<NotificationInfo> responseList = notificationInfoRoot.getData().getNotifications();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            notificationList.addAll(responseList);
                            mutableLiveData.setValue(new ApiResponse(notificationList));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                String error="";
                if(errorResponse!=null){
                    error=errorResponse;
                }
                mutableLiveData.setValue(new ApiResponse(error));
                Log.e("ERROR","error");
            }
        });
        return mutableLiveData;
    }
    public LiveData<Boolean> isListExhausted(){
        return mIsListExhausted;
    }
}
