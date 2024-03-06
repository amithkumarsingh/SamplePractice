package com.vam.whitecoats.databinding;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryDataResponse;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.DrugFeedsResponse;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.DrugFeedsApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugFeedsRepository {
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,drugName,mTag;
    private  int page_num;
    private SingleLiveEvent<DrugFeedsApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<CategoryFeeds> categoriesList = new ArrayList<>();
    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();

    public void setRequestData(int pageNumber,String drugName){
        this.page_num = pageNumber;
        this.drugName = drugName;
    }
    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }
    public SingleLiveEvent<DrugFeedsApiResponse> getFeedsList() {
        new VolleyModifiedSinglepartRequest(methodType, requestURL, prepareGetCategoryListRequest(), mTag, headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {

                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new DrugFeedsApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            DrugFeedsResponse drugFeedsResponse = gson.fromJson(successResponse, DrugFeedsResponse.class);
                            List<CategoryFeeds> responseList = drugFeedsResponse.getData().getFeedData();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            if(page_num == 0){
                                categoriesList.clear();
                            }
                            categoriesList.addAll(responseList);
                            mutableLiveData.setValue(new DrugFeedsApiResponse(categoriesList));
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
                mutableLiveData.setValue(new DrugFeedsApiResponse(error));
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
            reqObj.put("drug_name",drugName);
            reqObj.put(RestUtils.PG_NUM,page_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("feeds params",reqObj.toString());
        return reqObj.toString();
    }
}
