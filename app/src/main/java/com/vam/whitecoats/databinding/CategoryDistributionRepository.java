package com.vam.whitecoats.databinding;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CategoryDistributionRepository {
    private  int categoryId;
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private Context mContext;
    private  int user_id;
    private  boolean is_from_dashboard;
    private  int page_num;
    private MutableLiveData<CategoriesDistributionApiResponse> mutableLiveData;
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();
    public CategoryDistributionRepository(){
        mutableLiveData = new SingleLiveEvent<>();
    }


    public CategoryDistributionRepository(int doc_id, int category_id, int _pageNum) {
        this.user_id=doc_id;
        this.categoryId=category_id;
        this.page_num=_pageNum;
        mutableLiveData = new MutableLiveData<>();
    }
    public void setRequestData(int pageNumber,int docId,int categoryId){
        this.page_num = pageNumber;
        this.user_id = docId;
        this.categoryId = categoryId;
    }
    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }
    public MutableLiveData<CategoriesDistributionApiResponse> getUserCategoryDistributionList() {
        new VolleyModifiedSinglepartRequest(methodType, requestURL, prepareGetCategoryListRequest(), mTag, headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                String success_response="";
                JSONObject jsonObject = null;
                if(successResponse!=null){
                    success_response=successResponse;
                    try {
                        jsonObject = new JSONObject(success_response);
                        if(jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            mutableLiveData.setValue(new CategoriesDistributionApiResponse(success_response, true));
                        }else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            //Toast.makeText(mContext, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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
                mutableLiveData.setValue(new CategoriesDistributionApiResponse(error,false));
                Log.e("ERROR","error");


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
            reqObj.put("category_id",categoryId);
            reqObj.put(RestUtils.TAG_USER_ID, user_id);
            reqObj.put(RestUtils.PG_NUM,page_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

}
