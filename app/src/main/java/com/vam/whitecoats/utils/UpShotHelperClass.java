package com.vam.whitecoats.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.brandkinesis.BKProperties;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.brandkinesis.callback.BKActivityCallback;
import com.brandkinesis.callback.BKAuthCallback;
import com.brandkinesis.callback.BrandKinesisCallback;
import com.brandkinesis.utils.BKAppStatusUtil;
import com.vam.whitecoats.App_Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpShotHelperClass {

    // static variable single_instance of type Singleton
    private static UpShotHelperClass upshotInstance = null;

    // static method to create instance of Singleton class
    public static UpShotHelperClass getInstance() {
        if (upshotInstance == null)
            upshotInstance = new UpShotHelperClass();

        return upshotInstance;
    }

    //Register application in upshot
    public void registerAppInUpshot(Context mContext, BKAppStatusUtil.BKAppStatusListener bkAppStatusListener) {
        // registering  BKAppStatusListener listener
        BKAppStatusUtil.getInstance().register(mContext, bkAppStatusListener);
    }

    //init upshot sdk
    public void initUpshot(Context mContext) {

        Bundle data = new Bundle();
        data.putString(BKProperties.BK_APPLICATION_ID, App_Application.UPSHOT_APP_ID);
        data.putString(BKProperties.BK_APPLICATION_OWNER_ID, "648b3c8f-95bb-4594-aa26-f3c5a2d556cc");
        //data.putBoolean(BKProperties.BK_FETCH_LOCATION, true);
        data.putBoolean(BKProperties.BK_ENABLE_DEBUG_LOGS, false);
        data.putBoolean(BKProperties.BK_USE_EXTERNAL_STORAGE, false);
        data.putBoolean(BKProperties.BK_EXCEPTION_HANDLER, true);
        BrandKinesis bkInstance = BrandKinesis.getBKInstance();
        bkInstance.setBrandkinesisCallback(new BrandKinesisCallback() {
            @Override
            public void onActivityError(int i) {
            }

            @Override
            public void onActivityCreated(BKActivityTypes bkActivityTypes) {
            }

            @Override
            public void onActivityDestroyed(BKActivityTypes bkActivityTypes) {
            }

            @Override
            public void brandKinesisActivityPerformedActionWithParams(BKActivityTypes bkActivityTypes, Map<String, Object> map) {
                //Log.e("DEEP_LINK","brandKinesisActivityPerformedActionWithParams");
                new NavigationUtils(mContext, map);
            }

            @Override
            public void onAuthenticationError(String s) {
            }

            @Override
            public void onAuthenticationSuccess() {
            }

            @Override
            public void onBadgesAvailable(HashMap<String, List<HashMap<String, Object>>> hashMap) {

            }

            @Override
            public void getBannerView(View view, String s) {
            }

            @Override
            public void onErrorOccurred(int i) {
            }

            @Override
            public void brandkinesisCampaignDetailsLoaded() {

            }

            @Override
            public void onMessagesAvailable(List<HashMap<String, Object>> list) {
            }

            @Override
            public void onUserInfoUploaded(boolean b) {
            }

            @Override
            public void userStateCompletion(boolean b) {
            }
        });
        //By Default, Upshot downloads the data in the external SD card of the device.
        //If you want to download the data in the cache memory then pass false as the value in "key:value" as shown below
        // data.putBoolean(BKProperties.BK_USE_EXTERNAL_STORAGE, false);
        // intialize BrandKinesis (callback info described in following section)
        BrandKinesis.initialiseBrandKinesis(mContext, data, new BKAuthCallback() {
            @Override
            public void onAuthenticationError(String s) {
                Log.d("UPSHOT_AUTH", "Fail :" + s);
            }

            @Override
            public void onAuthenticationSuccess() {
                Log.d("UPSHOT_AUTH", "Success");
            }
        });
        BrandKinesis.getBKInstance().setDispatchEventTime(15000);
        BKUIComponents bkuiComponents = new BKUIComponents();
        BrandKinesis.getBKInstance().setUIPreferences(bkuiComponents);
    }
}
