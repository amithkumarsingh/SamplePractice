package com.vam.whitecoats.repository;

import android.util.Log;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.models.AdSlotsResponseModel;
import com.vam.whitecoats.models.AdsDefinition;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.GetAdSlotsApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SingleLiveEvent;
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdSlotsRepository {
    private int doc_id;
    private int channel_id;
    private int feed_id;
    private Map<String,String> headers;
    private SingleLiveEvent<GetAdSlotsApiResponse> mutableLiveData = new SingleLiveEvent<>();
    private ArrayList<AdsDefinition> adsDefinitionsList = new ArrayList<>();

    public void initRequest(int docId, int channelId, int feedId, Map<String,String> _headers){
        doc_id=docId;
        channel_id=channelId;
        feed_id=feedId;
        headers=_headers;

    }

    public SingleLiveEvent<GetAdSlotsApiResponse> getFeedAdSlots() {
        new VolleyModifiedSinglepartRequest(Request.Method.POST, RestApiConstants.GET_FEED_AD_SLOTS_API, prepareGetFeedSlots(), "GET_FEED_SLOTS", headers, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {

                if(successResponse!=null) {
                    try {
                        JSONObject jsonObject=new JSONObject(successResponse);
                        Log.i("response",jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            mutableLiveData.setValue(new GetAdSlotsApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            AdSlotsResponseModel drugFeedsResponse = gson.fromJson(successResponse, AdSlotsResponseModel.class);
                            List<AdsDefinition> responseList = drugFeedsResponse.getData().getAdsDefinitions();

                            adsDefinitionsList.addAll(responseList);
                            mutableLiveData.setValue(new GetAdSlotsApiResponse(adsDefinitionsList));
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
                mutableLiveData.setValue(new GetAdSlotsApiResponse(error));
                Log.e("ERROR","error");


            }
        });
        return mutableLiveData;
    }

    private String prepareGetFeedSlots() {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.CHANNEL_ID,channel_id);
            reqObj.put(RestUtils.TAG_FEED_ID,feed_id);
            reqObj.put(RestUtils.TAG_USER_ID,doc_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("feeds params",reqObj.toString());
        return reqObj.toString();
    }


}
