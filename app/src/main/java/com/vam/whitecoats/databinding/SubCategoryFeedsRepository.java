package com.vam.whitecoats.databinding;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryDataResponse;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.CategoryFeedsDataResponse;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.FeedsApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubCategoryFeedsRepository {

    private  int page_num;
    private  int user_id;
    private  int sub_category_id;
    private  int category_id;
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private SingleLiveEvent<FeedsApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();
    private ArrayList<CategoryFeeds> feedCategoriesList = new ArrayList<>();

    public SubCategoryFeedsRepository(){

    }

    public SubCategoryFeedsRepository(int categoryIid, int subCategoryId, int docId,int pgNum) {
        this.category_id=categoryIid;
        this.sub_category_id=subCategoryId;
        this.user_id=docId;
        this.page_num=pgNum;
    }

    public void setRequestData(int categoryId, int subCategoryId, int docId,int pgNum){
        this.category_id=categoryId;
        this.sub_category_id=subCategoryId;
        this.user_id=docId;
        this.page_num=pgNum;
    }
    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }

    public SingleLiveEvent<FeedsApiResponse> getFeedsList() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, prepareGetFeedsListRequest(), mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new FeedsApiResponse(successResponse));
                        }else {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            CategoryFeedsDataResponse categoryDataResponse = gson.fromJson(successResponse, CategoryFeedsDataResponse.class);
                            List<CategoryFeeds> responseList = categoryDataResponse.getData().getFeedData();
                            if (responseList != null) {
                                if (responseList.size() != 0) {
                                    mIsListExhausted.setValue(false);
                                } else {
                                    mIsListExhausted.setValue(true);
                                }
                                if (page_num == 0) {
                                    feedCategoriesList.clear();
                                }
                                feedCategoriesList.addAll(responseList);
                                mutableLiveData.setValue(new FeedsApiResponse(feedCategoriesList));
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
                mutableLiveData.setValue(new FeedsApiResponse(error));
                Log.e("ERROR","error");
            }
        });
        return mutableLiveData;
    }

    public LiveData<Boolean> subCategoryFeedsIsListExhausted(){
        return mIsListExhausted;
    }

    private String prepareGetFeedsListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put("category_id", category_id);
            reqObj.put("sub_category_id",sub_category_id);
            reqObj.put("user_id",user_id);
            reqObj.put("pg_num",page_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }

}
