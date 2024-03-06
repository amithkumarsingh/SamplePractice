package com.vam.whitecoats.databinding;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryDataResponse;
import com.vam.whitecoats.core.models.SpecialitiesDataResponse;
import com.vam.whitecoats.core.models.SpecialitiesInfo;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.CatDistributionApiResponse;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecialityDistributionRepository {

    private  int categoryId;
    private  Integer cat_distribution_id;
    private  int user_id;
    private  int page_num;
    private Map<String, String> headers;
    private int methodType;
    private String requestURL,mTag;
    private SingleLiveEvent<CatDistributionApiResponse> mutableLiveData = new SingleLiveEvent<>();

    private MutableLiveData<Boolean> mIsListExhausted = new MutableLiveData<>();
    private ArrayList<SpecialitiesInfo> specialitiesList  = new ArrayList<>();

    public SpecialityDistributionRepository(){
    }

    public void setRequestData(int category_id, Integer categoryDistributionId, int doc_id, int pageNum){
        this.categoryId =category_id;
        this.cat_distribution_id=categoryDistributionId;
        this.user_id=doc_id;
        this.page_num=pageNum;
    }

    public void initRequest(int methodType, String url, String mTag, Map<String,String> headers){
        this.methodType=methodType;
        this.requestURL=url;
        this.mTag=mTag;
        this.headers=headers;
    }

    public SingleLiveEvent<CatDistributionApiResponse> getCatSpecialityList() {
        new VolleyModifiedSinglepartRequest( methodType, requestURL, prepareGetSpecialityListRequest(), mTag,headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                Log.i("response",successResponse);
                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new CatDistributionApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            SpecialitiesDataResponse specialitiesDataResponse = gson.fromJson(successResponse, SpecialitiesDataResponse.class);
                            List<SpecialitiesInfo> responseList = specialitiesDataResponse.getData().getSpecialities();
                            if(responseList.size()!=0) {
                                mIsListExhausted.setValue(false);
                            }else{
                                mIsListExhausted.setValue(true);
                            }
                            if(page_num==0){
                                specialitiesList.clear();
                            }
                            specialitiesList.addAll(responseList);
                            mutableLiveData.setValue(new CatDistributionApiResponse(specialitiesList));
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
                mutableLiveData.setValue(new CatDistributionApiResponse(error));
                Log.e("ERROR","error");
            }
        });
        return mutableLiveData;
    }
    public LiveData<Boolean> isListExhausted(){
        return mIsListExhausted;
    }

    private String prepareGetSpecialityListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put("category_id", categoryId);
            reqObj.put("cat_distribution_id",cat_distribution_id);
            reqObj.put("user_id",user_id);
            reqObj.put("pg_num",page_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }


}
