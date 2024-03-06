package com.vam.whitecoats.utils;

import android.content.Context;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class AwsAndGoogleKeysServiceClass {
    private final Context context;
    private final int userId;
    private final AwsAndGoogleKey callBack;
    private String UUID;
    private String google_api_key;
    private String aws_key;

    public AwsAndGoogleKeysServiceClass(Context mContext, int login_doc_id, String _UUID,AwsAndGoogleKey awsAndGoogleKey) {
        this.context=mContext;
        this.userId=login_doc_id;
        this.callBack=awsAndGoogleKey;
        this.UUID=_UUID;

        JSONObject requestData = new JSONObject();
        try {
            requestData.put(RestUtils.TAG_USER_ID, userId);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }

        new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.API_KEY, requestData.toString(), "MANDATORY_PROFILE_API_KEY", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                try {
                    DESedeEncryption myEncryptor = DESedeEncryption.getInstance();

                    JSONObject jsonObject = new JSONObject(successResponse);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        if(jsonObject.optString(RestUtils.TAG_DATA).contains("google_api_key")) {
                            String decriptString = myEncryptor.decrypt(jsonObject.optJSONObject(RestUtils.TAG_DATA).optString("google_api_key"));
                            JSONObject googleApiKeyObject = new JSONObject(decriptString);
                            google_api_key=googleApiKeyObject.optString("google_api_key");
                            if(google_api_key!=null && !google_api_key.isEmpty()){
                                JSONObject jsonObjectEvent=new JSONObject();
                                try {
                                    AppUtil.logUserActionEvent(login_doc_id, "FetchAWSAndLocationKey", jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),mContext);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                JSONObject jsonObjectEvent=new JSONObject();
                                jsonObjectEvent.put("error","Unable to fetch credentials");
                                AppUtil.logUserActionEvent(login_doc_id, "FetchAWSAndLocationKey", jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),mContext);
                            }

                        }else{
                            JSONObject jsonObjectEvent=new JSONObject();
                            jsonObjectEvent.put("error","Unable to fetch credentials");
                            AppUtil.logUserActionEvent(login_doc_id, "FetchAWSAndLocationKey", jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),mContext);
                        }
                      if(jsonObject.optString(RestUtils.TAG_DATA).contains("aws_api_key")){
                          aws_key = myEncryptor.decrypt(jsonObject.optJSONObject(RestUtils.TAG_DATA).optString("aws_api_key"));

                      }
                        Config.GOOGLE_API_KEY=google_api_key;
                        callBack.awsAndGoogleKey(google_api_key,aws_key);
                    }else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                       // google_api_key=context.getString(R.string.api_key);

                        String errorMsg="Unable to fetch credentials";
                        if(jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)){
                            errorMsg=jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                        AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", UUID==null?"":UUID, "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), errorMsg);
                        AppUtil.logUserActionEvent(login_doc_id, "FetchAWSInfo", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),mContext);
                        AppUtil.logUserActionEvent(login_doc_id, "FetchLocationKeyFail", jsonObject, AppUtil.convertJsonToHashMap(jsonObject),mContext);
                        //callBack.awsAndGoogleKey(google_api_key,"");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                String error = errorResponse;
                //google_api_key=context.getString(R.string.api_key);
                if (errorResponse != null && !errorResponse.isEmpty()) {
                    AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", UUID==null?"":UUID, "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), errorResponse);
                } else {
                    AppUtil.logCustomEventsIntoFabric("FetchAWSInfo", UUID==null?"":UUID, "get_aws_keys", DateUtils.getCurrentTimeWithTimeZone(), "Unable to fetch credentials");
                }
                JSONObject errorObj=new JSONObject();
                try {
                    errorObj.put("errorMsg","Unable to fetch credentials");
                    AppUtil.logUserActionEvent(userId, "FetchAWSInfo", errorObj, AppUtil.convertJsonToHashMap(errorObj),mContext);
                    AppUtil.logUserActionEvent(login_doc_id, "FetchLocationKeyFail", errorObj, AppUtil.convertJsonToHashMap(errorObj),mContext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //callBack.awsAndGoogleKey(google_api_key,"");
            }
        }).sendSinglePartRequest();
    }




}
