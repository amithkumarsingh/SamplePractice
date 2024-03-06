package com.vam.whitecoats.databinding;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.CategoryFeedsDataResponse;
import com.vam.whitecoats.core.models.FeedsBySpecDataResponse;
import com.vam.whitecoats.core.models.FeedsBySpecInfo;
import com.vam.whitecoats.core.models.SpecialitiesDataResponse;
import com.vam.whitecoats.core.models.SpecialitiesInfo;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.CatDistributionApiResponse;
import com.vam.whitecoats.utils.FeedsApiResponse;
import com.vam.whitecoats.utils.FeedsBySpecApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedsBySpecialitiesRepository {

    private  int speciality_id;
    private  int page_num;
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private SingleLiveEvent<FeedsBySpecApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mIsListExhausted = new SingleLiveEvent<>();
    private ArrayList<FeedsBySpecInfo> feedCategoriesList = new ArrayList<>();
    private int categoryId;

    public FeedsBySpecialitiesRepository(){
    }

    public FeedsBySpecialitiesRepository(int specialityId,int pageNum) {
        this.speciality_id=specialityId;
        this.page_num=pageNum;
    }

    public void setRequestData(int specialityId,int pageNum,int category_id){
        this.speciality_id=specialityId;
        this.page_num=pageNum;
        this.categoryId=category_id;
    }
    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }

    public SingleLiveEvent<FeedsBySpecApiResponse> getFeedsListBasedOnSpeciality() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, prepareGetFeedsListRequest(), mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i("response",successResponse);
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new FeedsBySpecApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            FeedsBySpecDataResponse feedsBySpecDataResponse = gson.fromJson(successResponse, FeedsBySpecDataResponse.class);
                            List<FeedsBySpecInfo> responseList = feedsBySpecDataResponse.getData().getFeedData();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            if(page_num==0){
                                feedCategoriesList.clear();
                            }
                            feedCategoriesList.addAll(responseList);
                            mutableLiveData.setValue(new FeedsBySpecApiResponse(feedCategoriesList));
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
                mutableLiveData.setValue(new FeedsBySpecApiResponse(error));
                Log.e("ERROR","error");
            }
        });
        return mutableLiveData;
    }
    public LiveData<Boolean> feedsBasedOnSpecIsListExhausted(){
        return mIsListExhausted;
    }
    private String prepareGetFeedsListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put("speciality_id", speciality_id);
            reqObj.put("pg_num",page_num);
            reqObj.put("category_id",categoryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }
}
