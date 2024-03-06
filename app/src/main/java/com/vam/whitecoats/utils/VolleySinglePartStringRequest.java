package com.vam.whitecoats.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
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
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by venuv on 3/10/2017.
 */

public class VolleySinglePartStringRequest {
    private String serviceName;
    private String className;
    private OnReceiveResponse responseCallBack;
    private String requestData,tag;
    private int requestType;
    private Context context;
    final private String requestURL;

    public VolleySinglePartStringRequest(Context mContext, int methodType, String url, String request,String mTag ,OnReceiveResponse onReceiveResponse) {
        this.className = mContext.getClass().getName();
        this.context=mContext;
        this.requestType=methodType;
        requestURL=url;
        this.requestData=request;
        this.responseCallBack=onReceiveResponse;
        this.tag=mTag;
        serviceName = (requestURL != null) ? requestURL.substring(requestURL.lastIndexOf("/") + 1, requestURL.length()) : "";
    }

    public void sendSinglePartRequest(){
        StringRequest stringRequest = new StringRequest(requestType, requestURL, response -> {
            Log.d(className, "response data" + response);
            if (response != null) {
                if (!response.isEmpty()) {
                    JSONObject jsonObject = null;
                    try {
                        //App_Application.getInstance().getRequestQueue().getCache().remove(requestURL);
                        jsonObject = new JSONObject(response);
                        if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                AppUtil.showSessionExpireAlert("Error", context.getResources().getString(R.string.session_timedout), context);
                            } else if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                AppUtil.AccessErrorPrompt(context, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            } else {
                                responseCallBack.onSuccessResponse(response);
                            }
                        } else {
                            responseCallBack.onSuccessResponse(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "" , serviceName, DateUtils.getCurrentTimeWithTimeZone(), "Success with empty response");
                    responseCallBack.onErrorResponse(null);
                }
            } else {

                AppUtil.logCustomEventsIntoFabric("SERVER_ERROR", "" , serviceName, DateUtils.getCurrentTimeWithTimeZone(), "Success with empty response");
                responseCallBack.onErrorResponse(null);
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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("Connection", "close");
                headers.put("X-DEVICE-ID", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("X-TIME-ZONE", AppUtil.getTimeZone());
                headers.put("X-STAY-LOGGEDIN", "yes");
                SharedPreferences httpshp = context.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
                final String spring_securityToken = httpshp.getString("SSTOKEN", "");
                if (spring_securityToken != null && !spring_securityToken.equals("")) {
                    headers.put("X-Auth-Token", spring_securityToken);
                }
                String cookies = MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_COOKIE, null);
                if (cookies != null) {
                    headers.put("Cookie", cookies);
                }
                headers.put("X-APP-VERSION", HttpClient.getVerionCode(context));
                headers.put("X-APP-CHANNEL-NAME", "ANDROID");
                MySharedPref mySharedPref = new MySharedPref(context);
                String reg_id ="";
                if(NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                    reg_id = mySharedPref.getPrefsHelper().getPref(MyFcmListenerService.PROPERTY_REG_ID, "");
                }
                String AndroidVersion = android.os.Build.VERSION.RELEASE;
                String devicemodel = android.os.Build.MODEL;
                String deviceMaker = Build.MANUFACTURER;
                if (!AndroidVersion.equals("") && !devicemodel.equals("")) {
                    headers.put("X-DEVICE-OS-VERSION", AndroidVersion);
                    headers.put("X-DEVICE-MODEL", devicemodel);
                }
                headers.put("X-DEVICE-MAKER", deviceMaker);
                headers.put("X_APP_NOTIFICATIONS_ID", reg_id);
                headers.put("Authorization", "Basic YWRtaW46ZDAzM2UyMmFlMzQ4YWViNTY2MGZjMjE0MGFlYzM1ODUwYzRkYTk5Nw==");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    Log.d(className + " [ reqData ] ", requestData);
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
        App_Application.getInstance().addToRequestQueue(stringRequest, tag);
    }
}
