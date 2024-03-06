package com.vam.whitecoats.databinding;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.core.models.DrugSubClassResponse;
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

public class DrugsRepository {
    private Map<String, String> headers;
    private int methodType;
    private int drugId;
    private int pageNo;
    private String requestURL,mTag;
    private Context mContext;
    private SingleLiveEvent<ApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<DrugSubClass> drugssList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();


    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }
    public void setRequestData(int id,int pageNo){
        this.drugId = id;
        this.pageNo = pageNo;
    }
    public SingleLiveEvent<ApiResponse> getDrugList() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, prepareGetCategoryListRequest(), mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new ApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            DrugSubClassResponse drugClassResponse = gson.fromJson(successResponse, DrugSubClassResponse.class);
                            List<DrugSubClass> responseList = drugClassResponse.getDrugs();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            if(pageNo == 0){
                                drugssList.clear();
                            }
                            drugssList.addAll(responseList);
                            mutableLiveData.setValue(new ApiResponse(drugssList));
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
                Log.e("ERROR",error);
            }
        });
        return mutableLiveData;
    }

    public LiveData<Boolean> isListExhausted(){
        return mIsListExhausted;
    }

    private String prepareGetCategoryListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.DRUG_SUB_CLASS_ID,drugId);
            reqObj.put(RestUtils.PG_NUM,pageNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }
}
