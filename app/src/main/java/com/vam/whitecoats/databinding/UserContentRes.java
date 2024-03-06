package com.vam.whitecoats.databinding;

import android.app.Activity;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.utils.ApiCallbackInterface;
import com.vam.whitecoats.utils.ApiMethodCalls;
import com.vam.whitecoats.utils.SingleLiveEvent;

import org.json.JSONObject;

public class UserContentRes {
    private final ApiMethodCalls apiMethodCalls = new ApiMethodCalls();

    public SingleLiveEvent<String> getUserReviewPost(Activity activity, JSONObject resObj){
        SingleLiveEvent<String> contentPost = new SingleLiveEvent<>();
        String url = RestApiConstants.saveUserReportFeed;
        apiMethodCalls.postApiData(url, resObj, activity, new ApiCallbackInterface() {
            @Override
            public void onSuccessResponse(String response) {
                contentPost.postValue(response);
            }

            @Override
            public void onErrorResponse(String error) {
                contentPost.postValue(error);
            }
        });
        return  contentPost;
    }
}
