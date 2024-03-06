package com.vam.whitecoats.databinding;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.core.models.BrandInfo;
import com.vam.whitecoats.core.models.DrugBrand;
import com.vam.whitecoats.core.models.DrugDetail;
import com.vam.whitecoats.core.models.DrugDetailsInteractions;
import com.vam.whitecoats.core.models.DrugDetailsResponse;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.DrugDetailsApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugDetailsRepository {
    private Map<String, String> headers;
    private int methodType;
    private int drugId;
    private String requestURL, mTag;
    private SingleLiveEvent<DrugDetailsApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<DrugDetail> drugssList = new ArrayList<>();
    private ArrayList<DrugBrand> drugBrandsList = new ArrayList<>();
    private ArrayList<BrandInfo> brandInfoList = new ArrayList<>();
    private boolean isBrandInfo;

    public void initRequest(int methodType, String url, String mTag, Map<String, String> headers) {
        this.methodType = methodType;
        this.requestURL = url;
        this.mTag = mTag;
        this.headers = headers;
    }

    public void setRequestData(int id, boolean _isBrandInfo) {
        this.drugId = id;
        this.isBrandInfo = _isBrandInfo;
    }

    public SingleLiveEvent<DrugDetailsApiResponse> getDrugList() {
        new VolleyModifiedSinglepartRequest(methodType, requestURL, prepareGetCategoryListRequest(), mTag, headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (successResponse != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(successResponse);
                        Log.i("drug details response", jsonObject.toString());
                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new DrugDetailsApiResponse(successResponse));
                        } else {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            DrugDetailsResponse drugDetailsResponse = gson.fromJson(successResponse, DrugDetailsResponse.class);

                            List<DrugDetailsInteractions> drugDetailsInteractions = null;

                            if (drugDetailsResponse.getData().getAttributes() != null) {
                                List<DrugDetail> responseList = drugDetailsResponse.getData().getAttributes();
                                drugssList.addAll(responseList);

                            }

                            if (drugDetailsResponse.getData().getDrugInteractions() != null) {
                                drugDetailsInteractions = drugDetailsResponse.getData().getDrugInteractions();

                            }
                            if (drugDetailsResponse.getData().getBrands() != null) {
                                List<DrugBrand> drugBrands = drugDetailsResponse.getData().getBrands();
                                drugBrandsList.addAll(drugBrands);

                            }

                            if (drugDetailsResponse.getData().getBrandInfo() != null) {
                                List<BrandInfo> drugBrandInfoList = drugDetailsResponse.getData().getBrandInfo();
                                if (drugBrandInfoList != null) {
                                    brandInfoList.addAll(drugBrandInfoList);
                                }
                            }


                            //drugDetailsInteractions.addAll(drugDetailsInteractions);

                            mutableLiveData.setValue(new DrugDetailsApiResponse(drugssList, drugDetailsInteractions, drugBrandsList, brandInfoList));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onErrorResponse(String errorResponse) {
                String error = "";
                if (errorResponse != null) {
                    error = errorResponse;
                }
                mutableLiveData.setValue(new DrugDetailsApiResponse(error));
                Log.e("ERROR", error);
            }
        });
        return mutableLiveData;
    }

    private String prepareGetCategoryListRequest() {
        JSONObject reqObj = new JSONObject();
        try {
            if (isBrandInfo) {
                reqObj.put(RestUtils.BRAND_ID, drugId);
            } else {
                reqObj.put(RestUtils.DRUG_ID, drugId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("request params", reqObj.toString());
        return reqObj.toString();
    }
}
