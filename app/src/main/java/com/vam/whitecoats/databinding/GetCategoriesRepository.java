package com.vam.whitecoats.databinding;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryDataResponse;
import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.core.models.NotificationInfoRoot;
import com.vam.whitecoats.ui.interfaces.CategoriesListInterface;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.ApiResponse;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetCategoriesRepository {
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private  Context mContext;
    private  int user_id;
    private  String tabName;
    private  int page_num;
    private SingleLiveEvent<CategoriesApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();

    public GetCategoriesRepository(){

    }
    public GetCategoriesRepository(int doc_id, String tabName, int _pageNum) {
        this.user_id=doc_id;
        this.tabName=tabName;
        this.page_num=_pageNum;
    }

    public void setRequestData(int pageNumber,int docId,String tabName){
        this.page_num = pageNumber;
        this.user_id = docId;
        this.tabName = tabName;
    }

    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }

    public SingleLiveEvent<CategoriesApiResponse> getUserCategoryList() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, prepareGetCategoryListRequest(), mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new CategoriesApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            CategoryDataResponse categoryDataResponse = gson.fromJson(successResponse, CategoryDataResponse.class);
                            List<Category> responseList = categoryDataResponse.getData().getCategories();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            if(page_num == 0){
                                categoriesList.clear();
                            }
                            categoriesList.addAll(responseList);
                            mutableLiveData.setValue(new CategoriesApiResponse(categoriesList));
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
                mutableLiveData.setValue(new CategoriesApiResponse(error));
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
            reqObj.put(RestUtils.TAG_USER_ID, user_id);
            reqObj.put(RestUtils.PG_NUM,page_num);
            reqObj.put("request_location",tabName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }
}
