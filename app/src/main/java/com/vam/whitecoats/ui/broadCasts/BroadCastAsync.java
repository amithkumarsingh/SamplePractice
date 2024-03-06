package com.vam.whitecoats.ui.broadCasts;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.vam.whitecoats.App_Application;

/**
 * Created by lokeshl on 1/20/2016.
 */
public class BroadCastAsync extends AsyncTask<String,String,String> {

    String br_response;

    @Override
    protected String doInBackground(String... params) {
        /** firing Broadcast to update notification count **/
        br_response= "NotificationCount";
        Intent intent = new Intent("NotificationCount");
        LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);

        return br_response;
    }
}
