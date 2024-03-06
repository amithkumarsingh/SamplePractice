package com.vam.whitecoats.async;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

/**
 * Created by venuv on 11/24/2016.
 */

public class LikeActionAsync extends AsyncTask<String, String, String> {


    private Context mContext;
    private String className;
    private ProgressDialogFragement progress;
    private String response;
    private OnTaskCompleted listener;
    private String url;
    protected AlertDialog.Builder builder;




    public LikeActionAsync(Context context, String url, OnTaskCompleted onTaskCompleted) {
        mContext = context;
        this.url = url;
        listener = onTaskCompleted;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            className = mContext.getClass().getName();
            HttpClient client = new HttpClient(mContext,url);
            client.connectForSinglepart(mContext);
            String reqData = params[0].toString();
            Log.d(className + " [ reqData ] ", reqData);
            client.addpara(RestUtils.TAG_REQ_DATA, reqData);
            response = client.getResponse();
            Log.d("LikeAction Async", "response" + response);
        } catch (SocketTimeoutException e) {
            response = "SocketTimeoutException";
            e.printStackTrace();
            return response;
        } catch (Exception e) {
            response = "Exception";
            e.printStackTrace();
            return response;
        }
        return response;
    }


    @Override
    protected void onPostExecute(String response) {
        if (response != null) {
            if (response.equals("SocketTimeoutException") || response.toLowerCase().equals("exception")) {
                Log.d("Exception_likeService", response);
                String error = "Network Error,pls try after some time.";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        error = jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE);
                        Log.d("Exception_likeService", error);
                    }
                    listener.onTaskCompleted(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (listener != null) {
                    listener.onTaskCompleted(response);
                } else {
                    Log.d("Exception_likeService", error);
                }

            } else {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                            Log.d("Exception_likeService", mContext.getResources().getString(R.string.session_timedout));
                        } else {
                            Log.d("Exception_likeService",mContext.getResources().getString(R.string.unknown_server_error));
                        }
                        listener.onTaskCompleted(response);
                    } else {
                        listener.onTaskCompleted(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {

        }
    }



}

