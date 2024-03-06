package com.vam.whitecoats.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.vam.whitecoats.App_Application;

public class AppKillIdentifyService extends Service {

    @Override
    public void onCreate() {
        Log.d("TASK_REMOVE", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TASK_REMOVE", "onStartCommand");
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TASK_REMOVE", "onBind");
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        super.onTaskRemoved(rootIntent);
        //do something you want
        //stop service

        if(App_Application.sharedPref!=null){
            App_Application.sharedPref.savePref("APP_CLOSE_TIME",System.currentTimeMillis());
        }
        Log.d("TASK_REMOVE", "onTaskRemoved");
        this.stopSelf(); //this line is used to stop the service
    }


}
