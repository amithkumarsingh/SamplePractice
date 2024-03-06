package com.vam.whitecoats.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vam.whitecoats.core.gcm.MyFcmListenerService;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiMethodCalls extends AppCompatActivity {

    public void getApiData(String url, JSONObject parameters, Activity activity, final ApiCallbackInterface callback) {

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.GET, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            //Process os success response
                            jsonObject.put("status", 200);
                            jsonObject.put("response", response);
                            callback.onSuccessResponse(jsonObject.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onErrorResponse(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
                JSONObject jsonObject = new JSONObject();
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 400);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            break;
                        case 422:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 422);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            break;

                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 500);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                    }
                }

                //callback.onErrorResponse(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
//                headers.put("App-Origin", AppConstants.APP_ORIGIN);
//                headers.put("Authorization", "Bearer " + AppConfigClass.loginToken);
                return headers;
            }
        };
        requestQueue.add(request_json);
        request_json.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void postApiData(String url, JSONObject parameters, Activity activity, final ApiCallbackInterface callback) {

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            //Process os success response
                            jsonObject.put("status", 200);
                            jsonObject.put("response", response);
                            callback.onSuccessResponse(jsonObject.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onErrorResponse(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volley error res", error.toString());
                JSONObject jsonObject = new JSONObject();
                String json = null;
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {
                    switch (response.statusCode) {
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 400);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            break;
                        case 422:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 422);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            break;

                        case 500:
                            json = new String(response.data);
                            json = trimMessage(json, "message");
                            if (json != null) {
                                try {
                                    jsonObject.put("status", 500);
                                    jsonObject.put("response", json);
                                    callback.onErrorResponse(jsonObject.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                    }
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                headers.put("Connection", "close");
                headers.put("X-DEVICE-ID", Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID));
                headers.put("X-TIME-ZONE", AppUtil.getTimeZone());
                headers.put("X-STAY-LOGGEDIN", "yes");
                SharedPreferences httpshp = activity.getSharedPreferences("SpringSecurityPREF", Context.MODE_PRIVATE);
                final String spring_securityToken = httpshp.getString("SSTOKEN", "");
                if (spring_securityToken != null && !spring_securityToken.equals("")) {
                    headers.put("X-Auth-Token", spring_securityToken);
                }
                String cookies = MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_COOKIE, null);
                if (cookies != null) {
                    headers.put("Cookie", cookies);
                }
                headers.put("X-APP-VERSION", HttpClient.getVerionCode(activity));
                headers.put("X-APP-CHANNEL-NAME", "ANDROID");
                MySharedPref mySharedPref = new MySharedPref(activity);
                String reg_id ="";
                if(NotificationManagerCompat.from(activity).areNotificationsEnabled()) {
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


//                headers.put("Content-Type", "application/json");
////                headers.put("App-Origin", AppConstants.APP_ORIGIN);
////                headers.put("Authorization", "Bearer " + AppConfigClass.loginToken);
                return headers;
            }
        };

        requestQueue.add(request_json);
        request_json.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

}
