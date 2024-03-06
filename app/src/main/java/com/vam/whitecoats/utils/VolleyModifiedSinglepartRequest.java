package com.vam.whitecoats.utils;

import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class VolleyModifiedSinglepartRequest {

    private String serviceName;

    public VolleyModifiedSinglepartRequest(int methodType, String requestURL, String requestData, String mTag , Map<String,String> headers, OnReceiveResponse responseCallBack){
        serviceName = (requestURL != null) ? requestURL.substring(requestURL.lastIndexOf("/") + 1, requestURL.length()) : "";
        StringRequest stringRequest = new StringRequest(methodType, requestURL, response -> {
            if (response != null) {
                if (!response.isEmpty()) {
                    responseCallBack.onSuccessResponse(response);

                } else {
                    //FlurryAgent.logEvent((requestURL != null) ? requestURL.substring(requestURL.lastIndexOf("/") + 1, requestURL.length()) : ""+" :Error :" + AppConstants.login_doc_id);
                    AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "" , serviceName, DateUtils.getCurrentTimeWithTimeZone(), "Success with empty response");
                    responseCallBack.onErrorResponse("Something went wrong, please try again");
                }
            } else {
                //FlurryAgent.logEvent((requestURL != null) ? requestURL.substring(requestURL.lastIndexOf("/") + 1, requestURL.length()) : ""+" :Error :" + AppConstants.login_doc_id);
                AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "" , serviceName, DateUtils.getCurrentTimeWithTimeZone(), "Success with empty response");
                responseCallBack.onErrorResponse("Something went wrong, please try again");
            }
        }, error -> {
            if (error == null || error.networkResponse == null) {
                AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "", serviceName, DateUtils.getCurrentTimeWithTimeZone(), "Volley error with empty response");
                Log.e(serviceName, "Unknown error");
                responseCallBack.onErrorResponse(null);
            } else {
                NetworkResponse response = error.networkResponse;
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    responseCallBack.onErrorResponse(RestUtils.TAG_NETWORK_ERROR_RETRY);
                } else if (error instanceof ServerError) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        responseCallBack.onErrorResponse(res);
                        // Now you can use any deserializer to make sense of data
                        //JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        responseCallBack.onErrorResponse(null);
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (Exception e2) {
                        responseCallBack.onErrorResponse(null);
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                } else if (response != null){
                    AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "", (requestURL != null) ? requestURL.substring(requestURL.lastIndexOf("/") + 1, requestURL.length()) : "", DateUtils.getCurrentTimeWithTimeZone(), (response == null) ? "Unknown error response" : "" + response.statusCode);

                    if ((response.statusCode == HttpStatusCodes.SC_NOT_FOUND || response.statusCode == HttpStatusCodes.SC_REQUEST_TIMEOUT)) {
                        responseCallBack.onErrorResponse(RestUtils.TAG_NETWORK_ERROR_RETRY);
                    } else if ((response.statusCode == HttpStatusCodes.SC_BAD_GATEWAY || response.statusCode == HttpStatusCodes.SC_SERVICE_UNAVAILABLE)) {
                        JSONObject errorObj = new JSONObject();
                        try {
                            errorObj.put(RestUtils.TAG_ERROR_CODE, response.statusCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        responseCallBack.onErrorResponse(errorObj.toString());
                    }
                }
                else {
                    responseCallBack.onErrorResponse(null);
                }
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    String encodedData = URLEncoder.encode(requestData, "UTF-8");
                    return encodedData == null ? null : (RestUtils.TAG_REQ_DATA + "=" + encodedData).getBytes();
                } catch (Exception uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestData, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    /*if (response.data.length > 10000) {
                        setShouldCache(false);
                    }*/
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, "UTF-8"));
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    jsonResponse.put("headers", new JSONObject(response.headers));
                    if (jsonResponse.optJSONObject("headers").has("Set-Cookie")) {
                        MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_COOKIE, jsonResponse.optJSONObject("headers").optString("Set-Cookie"));
                    }
                    return Response.success(jsonString,
                            AppUtil.parseIgnoreCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(6 * 10000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App_Application.getInstance().addToRequestQueue(stringRequest, mTag);
    }

}
