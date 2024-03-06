package com.vam.whitecoats.databinding;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.models.DrugClassResponse;
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

public class DrugClassRepository {
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private SingleLiveEvent<ApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<DrugClass> drugsList = new ArrayList<>();
    private ArrayList<DrugSubClass> subclassList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();
    private String request;


    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers,String request){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
        this.request=request;
    }
    public SingleLiveEvent<ApiResponse> getDrugList(String type) {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, request, mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("drug response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new ApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if(type.equalsIgnoreCase("DrugClass")) {
                                DrugClassResponse drugClassResponse = gson.fromJson(successResponse, DrugClassResponse.class);
                                List<DrugClass> responseList = drugClassResponse.getDrugs();
                                if (responseList.size() != 0) {
                                    mIsListExhausted.setValue(false);
                                } else {
                                    mIsListExhausted.setValue(true);
                                }
                                drugsList.clear();
                                drugsList.addAll(responseList);
                                mutableLiveData.setValue(new ApiResponse(drugsList));
                            }else if(type.equalsIgnoreCase("DrugSubClass")){
                                DrugSubClassResponse drugClassResponse = gson.fromJson(successResponse, DrugSubClassResponse.class);
                                List<DrugSubClass> responseList = drugClassResponse.getDrugs();
                                if(responseList.size()!=0) {
                                    mIsListExhausted.setValue(false);
                                }else{
                                    mIsListExhausted.setValue(true);
                                }
                                subclassList.clear();
                                subclassList.addAll(responseList);
                                mutableLiveData.setValue(new ApiResponse(subclassList));
                            }
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
