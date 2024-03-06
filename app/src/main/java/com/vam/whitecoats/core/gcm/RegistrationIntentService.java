/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vam.whitecoats.core.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
/*
import com.google.firebase.iid.FirebaseInstanceId;
*/
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class RegistrationIntentService /*extends IntentService*/ {

  /*  private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private Context context;
    SharedPreferences sharedPreferences;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_REG_ID = "registration_id";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]

            *//** Deprecated, as with FCM implementation the library does it itself. **//*

            *//*InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);*//*
            // [END get_token]

            String token = FirebaseInstanceId.getInstance().getToken();
            Log.i(TAG, "GCM Registration Token: " + token);
            storeRegistrationId(token);
            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(AppConstants.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    *//**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     *//*
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        String response;

        try {
            JSONObject object = new JSONObject();
            object.put(RestUtils.API_KEY,"");
            object.put(RestUtils.REG_ID, token);

            try {
                HttpClient client = new HttpClient(getApplicationContext(),RestApiConstants.PUSH_GCM_NOTIFICATION);
                client.connectForSinglepart(context);
                String reqData = object.toString();

                Log.d("reqData", reqData);
                client.addpara(RestUtils.TAG_REQ_DATA, reqData);
                //client.finishMultipart();
                response = client.getResponse();
            } catch (SocketTimeoutException e) {
                response = "SocketTimeoutException";
            } catch (Exception e) {
                response = "Exception";
            }
            if (response != null) {
                if (response.equals("SocketTimeoutException") || response.toLowerCase().contains("exception")) {
                    sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, false).apply();
                } else {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, true).apply();

                    } else {
                        sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, false).apply();
                    }
                }
            } else {
                sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, false).apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    *//**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     *//*
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]

    private void storeRegistrationId(String regId) {
        final SharedPreferences prefs = getGCMPreferences();
        int appVersion = App_Application.getInstance().getAppVersion();
        Log.i(TAG, "Saving RegId and App version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private SharedPreferences getGCMPreferences() {

        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    }
*/
}
