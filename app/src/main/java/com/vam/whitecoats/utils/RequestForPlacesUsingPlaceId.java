package com.vam.whitecoats.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RequestForPlacesUsingPlaceId {

    private Context context;
    private String placeId;


    public RequestForPlacesUsingPlaceId(Context mContext, String mPlaceId, String api_key, OnReceiveResponse callback) {

        this.context=mContext;
        this.placeId=mPlaceId;
        //VolleyLog.DEBUG = true;

        String BASE_URL_PLACEID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
        RequestQueue queue = SingletonRequestQueue.getInstance(context).getRequestQueue();
        String uri_page_one = null;
        try {

            uri_page_one = BASE_URL_PLACEID + URLEncoder.encode(placeId, "UTF-8") + "&key=" +api_key;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        queue.cancelAll("PLACES_TAG");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri_page_one, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccessResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(context, "Network error,please try again.", Toast.LENGTH_LONG).show();
                } else {
                    callback.onErrorResponse(error.getLocalizedMessage());
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Request.Priority getPriority() {
                return Request.Priority.IMMEDIATE;
            }
        };
        jsonObjectRequest.setTag("PLACES_TAG");
        queue.add(jsonObjectRequest);

        queue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                try {
                    if (request.getCacheEntry() != null) {
                        String cacheValue = new String(request.getCacheEntry().data, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
