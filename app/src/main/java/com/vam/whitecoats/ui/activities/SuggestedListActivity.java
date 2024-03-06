package com.vam.whitecoats.ui.activities;


import android.os.Bundle;
import android.util.Log;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SuggestedListActivity extends MyBookmarksActivity {

    public static final String TAG = SuggestedListActivity.class.getSimpleName();
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void requestBookmarksList(JSONObject request, boolean isPullToRefresh, String URL, boolean isFromLoadMore) {
        Log.d(TAG, "requestBookmarksList: ");
        super.requestBookmarksList(getRequestObj(), false, RestApiConstants.GET_SUGGESTED_FEEDS_LIST_API, false);
    }


    private JSONObject getRequestObj() {
        Log.d(TAG, "getRequestObj: ");
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_DOC_ID, doctorID);
            requestObj.put(RestUtils.TAG_PAGE_NUM, index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestObj;
    }

    @Override
    protected String getScreenName() {
        return "Suggested Feeds";
    }


    @Override
    protected void sendRefreshDataRequest() {
        index=0;
        super.requestBookmarksList(getRequestObj(), true, RestApiConstants.GET_SUGGESTED_FEEDS_LIST_API, false);
    }

    @Override
    protected void sendLoadmoreDataRequest() {
        index++;
        String activityName = mContext.toString();
        if(activityName.contains("SuggestedListActivity")){
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("PageNumber",index++);
                AppUtil.logUserUpShotEvent("SuggestedFeedsScroll",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.requestBookmarksList(getRequestObj(), false, RestApiConstants.GET_SUGGESTED_FEEDS_LIST_API, true);
    }
}
