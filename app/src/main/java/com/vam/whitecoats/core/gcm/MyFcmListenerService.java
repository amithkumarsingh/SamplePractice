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

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogModel;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmProfessionalInfo;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.SplashScreenActivity;
import com.vam.whitecoats.ui.broadCasts.BroadCastAsync;
import com.vam.whitecoats.ui.interfaces.ConnectNotificationPreDataListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.ConnectsNotificationInsertionTODB;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

import io.realm.Realm;



public class MyFcmListenerService extends FirebaseMessagingService {
    public static final String TAG = MyFcmListenerService.class.getSimpleName();
    private RealmBasicInfo basicInfo;
    private String qb_hidden_dialog_id;
    private int doctorId;
    private String KEY_MESSAGE = "message";
    private String KEY_TITLE = "title";
    private String KEY_DIALOG_ID = "dialog_id";
    private String USER_ID = "user_id";
    public static String KEY_IS_LIKE_OR_COMMENT = "isLikesOrComments";
    public static String KEY_IS_POST_OR_ARTICLE = "isPostOrArticle";
    public static String KEY_IS_USER_REJECT = "isUserReject";
    public static String KEY_HAS_IMAGE = "attachment-url";
    public static String KEY_IS_USER_APPROVED = "isUserApproved";
    /*public static String KEY_KNOWLEDGE_FEED_PAGE = "Knowledge_Feed_Page";
    public static String KEY_KNOWLEDGE_DRUG_REF_NOTIFICATION = "Knowledge_ DrugReference_Page";
    public static String KEY_KNOWLEDGE_MEDICAL_EVENTS_NOTIFICATION = "Knowledge_MedicalEvents_Page";
    public static String KEY_COMMUNITY_SPOTLIGHT_NOTIFICATION = "Community_Spotlight_Page";
    public static String KEY_COMMUNITY_FEED_NOTIFICATION = "Community_Feed_Page";
    public static String KEY_COMMUNITY_DOCTORS_PAGE_NOTIFICATION = "Community_Doctors_Page";
    public static String KEY_COMMUNITY_ORGANIZATION_NOTIFICATION = "Community_Organizations_Page";
    public static String KEY_PROFESSIONAL_FEEDS_NOTIFICATION = "Professional_Feed Page";
    public static String KEY_PROFESSIONAL_SKILLING_NOTIFICATION = "Professional_Skilling Page";
    public static String KEY_PROFESSIONAL_OPPORTUNITIES_NOTIFICATION = "Professional_Opportunities Page";
    public static String KEY_PROFESSIONAL_PARTNERS_NOTIFICATION = "Professional_Partners Page";
    public static String KEY_NOTIFICATION_TAB = "Notification_Tab";*/
    String imageUrl = "";
    private Context context;
    Realm realm = null;
    RealmManager realmManager = null;
    private SharedPreferences sharedPreferences;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_REG_ID = "registration_id";
    private MySharedPref sharedPref;
    private int networkNotificationCount = 0;
    private RealmProfessionalInfo realmProfessionalInfo;
    private ProfessionalInfo professionalInfo;
    private ProfessionalInfo realmProfessionInfo;
    private boolean deeplinkinVariable;

    /**
     * Called when message is received.
     *
     * @param message .
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.i(TAG, "onMessageReceived() ");
        String from = message.getFrom();
        Map<String, String> data = message.getData();
        Log.d(TAG, "FCM Map - " + data.toString());
        Bundle convertedData = convertMapToBundle(data);
        Log.d(TAG, "Converted Bundle - " + convertedData.toString());
        String msg = null;
        String dialogId = null;
        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(getApplicationContext());
            sharedPref = new MySharedPref(getApplicationContext());
            if (convertedData.containsKey(RestUtils.TAG_NOTIFICATION_ID) && realmManager.getDoc_id(realm) > 0) {
                JSONObject notificationEchoRequest = new JSONObject();
                JSONArray eventsObjArray = new JSONArray();
                JSONObject eventObj = new JSONObject();
                JSONObject eventDataObj = new JSONObject();
                eventDataObj.put(RestUtils.TAG_NOTIFICATION_ID, convertedData.getString(RestUtils.TAG_NOTIFICATION_ID));
                eventDataObj.put(RestUtils.TAG_TIMESTAMP, AppUtil.getTimeWithTimeZone());
                eventObj.put(RestUtils.TAG_EVENT_TYPE, "notification_event");
                eventObj.put(RestUtils.TAG_EVENT_DATA, eventDataObj);
                eventsObjArray.put(eventObj);
                notificationEchoRequest.put(RestUtils.TAG_USER_ID, realmManager.getDoc_id(realm));
                notificationEchoRequest.put(RestUtils.TAG_USER_EVENTS, eventsObjArray);
                notificationEchoRequest.put(RestUtils.TAG_COUNT, 0);
                new VolleySinglePartStringRequest(getApplicationContext(), Request.Method.POST, RestApiConstants.LOG_USER_EVENT_API, notificationEchoRequest.toString(), "TAG_NOTIFICATION_ECHO", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        try {
                            JSONObject responseObj = new JSONObject(successResponse);
                            if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                            } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                realmManager.insertOrUpdateEventDataDB("notification_event", eventDataObj.toString(), System.currentTimeMillis());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        realmManager.insertOrUpdateEventDataDB("notification_event", eventDataObj.toString(), System.currentTimeMillis());
                    }
                }).sendSinglePartRequest();
            }
            if (convertedData.containsKey(KEY_MESSAGE)) {
                msg = convertedData.getString(KEY_MESSAGE);
            }
            if (convertedData.containsKey(KEY_DIALOG_ID)) {
                dialogId = convertedData.getString(KEY_DIALOG_ID);
            }
            if (convertedData.containsKey(KEY_HAS_IMAGE)) {
                imageUrl = convertedData.getString(KEY_HAS_IMAGE);
            } else {
                imageUrl = "";
            }

            if (convertedData.containsKey(RestUtils.TAG_C_MSG_TYPE)) {
                String c_msg_type = convertedData.getString(RestUtils.TAG_C_MSG_TYPE);
                boolean needToNavigate = true;
                Intent in = new Intent();
                if (c_msg_type.equalsIgnoreCase("5") || c_msg_type.equalsIgnoreCase("6")) {
                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        JSONObject postObj = new JSONObject();
                        postObj.put(RestUtils.TAG_FEED_ID, convertedData.getInt(RestUtils.FEED_TYPE_ID));
                        postObj.put(RestUtils.CHANNEL_ID, convertedData.getInt(RestUtils.CHANNEL_ID));
                        postObj.put("isFromNotification", true);
                        listener.notifyUIWithNewData(postObj);
                    }
                } else if (c_msg_type.equalsIgnoreCase("3")) {
                    int doc_id = realmManager.getDoc_id(realm);
                    new ConnectsNotificationInsertionTODB(getApplicationContext(), realmManager, realm, doc_id, false, true, realmManager.getNotificationDataFromDB(), new ConnectNotificationPreDataListener() {
                        @Override
                        public void notifyUIWithPreData(JSONArray data) {
                            if (data.length() > 0) {
                                networkNotificationCount = sharedPref.getPref("networkNotificationCount", 0);
                                for (int i = 0; i < data.length(); i++) {
                                    networkNotificationCount++;
                                }
                                sharedPref.savePref("networkNotificationCount", networkNotificationCount);
                                new BroadCastAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            }

                        }
                    });
                }/*else if(c_msg_type.equalsIgnoreCase("18")){

                    in.setClass(context, PreferencesActivity.class);
                }
                if(needToNavigate) {
                    context.startActivity(in);
                }*/
            }
            if (msg == null || msg.isEmpty()) {
                return;
            }
            realmProfessionalInfo = new RealmProfessionalInfo();
            professionalInfo = new ProfessionalInfo();
            realmProfessionInfo = realmManager.getProfessionalInfoOfShowoncard(realm);
            basicInfo = realmManager.getRealmBasicInfo(realm);
            qb_hidden_dialog_id = basicInfo.getQb_hidden_dialog_id();
            doctorId = basicInfo.getDoc_id();
            Log.d(TAG, "Hidden Dialog Id - " + qb_hidden_dialog_id);
            Log.d(TAG, "Doctor Id - " + doctorId);
            if (dialogId != null) {
                if (!sharedPref.getPref("QB_PUSH_SUBSCRIPTION", true)) {
                    return;
                }
                QBDialogModel selectedDialog = realmManager.getCRQBDialogs(realm, dialogId);
                Log.d(TAG, "selectedDialog- " + selectedDialog);
                /*if (from.startsWith("/topics/")) {
                    // message received from some topic.
                } else {
                    // normal downstream message.
                }*/
                if (Foreground.get().isBackground()) {
                    sendNotification(convertedData, selectedDialog, dialogId);
                }
            } else {
                if (Foreground.get().isBackground()) {
                    sendNotification(convertedData);
                } else {
                    String cType = convertedData.getString(RestUtils.TAG_C_MSG_TYPE);
                    String promptMsg = msg;
                    if (cType.equalsIgnoreCase("7")) {
                        sharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, 3);
                        AppUtil.logUserVerificationInfoEvent(sharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED));
                        if (App_Application.getCurrentActivity() != null) {
                            App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(App_Application.getCurrentActivity());
                                    builder.setMessage(promptMsg);
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                                }
                            });
                        }
                    } else if (cType.equalsIgnoreCase("10")) {
                        sharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, 1);
                        if (App_Application.getCurrentActivity() != null) {
                            App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtil.AccessErrorPrompt(App_Application.getCurrentActivity(), promptMsg);
                                }
                            });
                        }
                    }
                }
            }
            /*
            generate urbanairship notification
             */
            //AirshipFirebaseMessagingService.processMessageSync(getApplicationContext(), message);

       /*     UAirship.shared().getInAppMessagingManager()
                    .setAdapterFactory(InAppMessage.TYPE_MODAL, new InAppMessageAdapter.Factory() {
                        @Override
                        public InAppMessageAdapter createAdapter(InAppMessage inAppMessage) {
                            return new CustomInAppMessageAdapter(inAppMessage);
                        }
                    });*/
        } catch (Exception e) {
            e.printStackTrace();
        }/* finally {
            if (realm != null && !realm.isClosed())
                realm.close();
        }*/

    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param data        GCM message received.
     * @param dialogModel Chat Dialog
     */
    private void sendNotification(Bundle data, QBDialogModel dialogModel, String qbDialogId) {
        Log.i(TAG, "sendNotification(String message, QBDialogModel dialogModel) ");
        Intent intent = new Intent(this, SplashScreenActivity.class);
        if (dialogModel != null) {
            String localDialogType = dialogModel.getLocal_dialog_type();
            Log.d(TAG, "localDialogType - " + localDialogType);

            Bundle bundle = new Bundle();
            String message = data.getString(KEY_MESSAGE);
            if (message.endsWith("...")) {
                bundle.putString("message", message.replace("...", ""));
            } else {
                bundle.putString("message", message);
            }
            bundle.putString("dialog_id", dialogModel.getDialog_id());
            bundle.putString(RestUtils.TAG_NOTIFICATION_ID, data.getString(RestUtils.TAG_NOTIFICATION_ID));
            bundle.putBoolean(RestUtils.TAG_IS_FROM_NOTIFICATION, true);
            bundle.putBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, data.getBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION));
            intent.putExtras(bundle);
            generateNotification(intent, data);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("qb_dialog_id", qbDialogId);
            bundle.putBoolean(RestUtils.TAG_IS_FROM_NOTIFICATION, true);
            bundle.putString(RestUtils.TAG_NOTIFICATION_ID, data.getString(RestUtils.TAG_NOTIFICATION_ID));
            bundle.putBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, data.getBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION));
            bundle.putBoolean("non_stored_dialog_msg", true);
            intent.putExtras(bundle);
            generateNotification(intent, data);
        }

    }

    private void sendNotification(Bundle data) {
        Log.i(TAG, "sendNotification(Bundle data)");
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        String cType = data.getString(RestUtils.TAG_C_MSG_TYPE);
        Log.i(TAG, "C Type - " + cType);
        if (cType != null) {
            if (cType.equalsIgnoreCase("5")) { // CHANNEL_POST
                data.putBoolean(KEY_IS_POST_OR_ARTICLE, true);
            } else if (cType.equalsIgnoreCase("6")) { // CHANNEL_CONTENT_ARTICLE
                data.putBoolean(KEY_IS_POST_OR_ARTICLE, true);
            } else if (cType.equalsIgnoreCase("8")) { // like
                data.putBoolean(KEY_IS_LIKE_OR_COMMENT, true);
            } else if (cType.equalsIgnoreCase("9")) { // comment
                data.putBoolean(KEY_IS_LIKE_OR_COMMENT, true);
            } else if (cType.equalsIgnoreCase("10")) { // MCI reject
                sharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, 1);
                data.putBoolean(KEY_IS_USER_REJECT, true);
            } else if (cType.equalsIgnoreCase("7")) {// MCI accept
                sharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, 3);
                data.putBoolean(KEY_IS_USER_APPROVED, true);
            } /*else if (cType.equalsIgnoreCase("20")) {// knowledge feed page
                sharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, 3);
                data.putBoolean(KEY_KNOWLEDGE_FEED_PAGE, true);
                data.putBoolean(KEY_KNOWLEDGE_DRUG_REF_NOTIFICATION, true);
                data.putBoolean(KEY_KNOWLEDGE_MEDICAL_EVENTS_NOTIFICATION, true);
                data.putBoolean(KEY_COMMUNITY_SPOTLIGHT_NOTIFICATION, true);
                data.putBoolean(KEY_COMMUNITY_FEED_NOTIFICATION, true);
                data.putBoolean(KEY_COMMUNITY_DOCTORS_PAGE_NOTIFICATION, true);
                data.putBoolean(KEY_COMMUNITY_ORGANIZATION_NOTIFICATION, true);
                data.putBoolean(KEY_PROFESSIONAL_FEEDS_NOTIFICATION, true);
                data.putBoolean(KEY_PROFESSIONAL_OPPORTUNITIES_NOTIFICATION, true);
                data.putBoolean(KEY_PROFESSIONAL_PARTNERS_NOTIFICATION, true);
                data.putBoolean(KEY_PROFESSIONAL_SKILLING_NOTIFICATION, true);
            } else if (cType.equalsIgnoreCase("3")) {
                data.putBoolean(KEY_NOTIFICATION_TAB, true);
            }*/
        }
        data.putBoolean(RestUtils.TAG_IS_FROM_NOTIFICATION, true);
        data.putString(RestUtils.TAG_NOTIFICATION_ID, data.getString(RestUtils.TAG_NOTIFICATION_ID));
        data.putBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, data.getBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION));
        intent.putExtras(data);
        generateNotification(intent, data);
    }

    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    private void generateNotification(Intent intent, Bundle data) {
        Log.i(TAG, "generateNotification(Intent intent)");

        ActivityManager manager = (ActivityManager) getApplication().getSystemService(ACTIVITY_SERVICE);
        int sizeStack = manager.getRunningTasks(2).size();
        Bundle bundle = new Bundle();
        if (sizeStack > 1) {
            deeplinkinVariable = true;

        } else {
            deeplinkinVariable = false;

        }
        bundle.putBoolean("stackBoolValue", deeplinkinVariable);
        intent.putExtras(bundle);
        /*for (int i = 0; i < sizeStack; i++) {

            ComponentName cn = manager.getRunningTasks(2).get(i).topActivity;
            Log.e("task name", cn.getClassName());
        }*/

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        String CHANNEL_ID = "NotificationChannel";
       /* PendingIntent pendingIntent = PendingIntent.getActivity(this, iUniqueId *//* Request code *//*, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);*/
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(this, iUniqueId /* Request code */, intent,
                    PendingIntent.FLAG_IMMUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity(this, iUniqueId /* Request code */, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "NotificationChannel", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        //notification personalization logic
        String lastLoginDate = sharedPref.getPref(MySharedPref.PREF_LAST_LOGIN_TIME);
        String onBoardDate = sharedPref.getPref(MySharedPref.PREF_ON_BOARD_DATE);
        String location = realmProfessionInfo.getLocation();
        String message = data.getString(KEY_MESSAGE);

        if (data.getBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION)) {
            // String msg = message;
            message = message.replace("@name", basicInfo.getUser_salutation() + " " + basicInfo.getFname() + " " + basicInfo.getLname());
            message = message.replace("@speciality", basicInfo.getSplty());
            message = message.replace("@lastLoginDate", lastLoginDate);
            message = message.replace("@onboardDate", onBoardDate);
            message = message.replace("@location", location);
            /*if(message.contains("@name")){
                message.replace("@name","Hi " +basicInfo.getUser_salutation() + basicInfo.getFname() + " " + basicInfo.getLname()+",");
            }
            if(message.contains("@speciality")){
                message.replaceAll("@speciality",basicInfo.getSplty());
            }*/
            /*if(message.contains("@lastLoginDate")){
                message.replaceAll("@lastLoginDate",lastLoginDate);
            }
            if(message.contains("@onboardDate")){
                message.replaceAll("@onboardDate", basicInfo.getLname());
            }
            if(message.contains("@location")){
                message.replaceAll("@location",basicInfo.getUser_salutation());
            }*/
            //msg = data.(KEY_MESSAGE);

        }
        if (imageUrl != null && !imageUrl.isEmpty()) { // for image notification
            Bitmap imageBitmap = getBitmapFromUrl(imageUrl);
            imageUrl = "";
            notificationBuilder.setContentTitle("WhiteCoats")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLargeIcon(imageBitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imageBitmap))
                    /*.addAction(R.drawable.ic_social_like, "LIKE", null)
                    .addAction(R.drawable.ic_social_comment, "COMMENT", null)*/
                    .setContentIntent(pendingIntent)
                    .setContentText(message).setChannelId(CHANNEL_ID);

        } else {
            notificationBuilder.setContentTitle("WhiteCoats")
                    .setContentTitle("WhiteCoats")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message).setChannelId(CHANNEL_ID);
        }
        //}
        /*else {
            String message = data.getString(KEY_MESSAGE);
            if (imageUrl != null && !imageUrl.isEmpty()) { // for image notification
                Bitmap imageBitmap = getBitmapFromUrl(imageUrl);
                imageUrl = "";
                notificationBuilder.setContentTitle("WhiteCoats")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setLargeIcon(imageBitmap)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imageBitmap))
                        *//*.addAction(R.drawable.ic_social_like, "LIKE", null)
                        .addAction(R.drawable.ic_social_comment, "COMMENT", null)*//*
                        .setContentIntent(pendingIntent)
                        .setContentText(message).setChannelId(CHANNEL_ID);

            } else {
                notificationBuilder.setContentTitle("WhiteCoats")
                        .setContentTitle("WhiteCoats")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentText(message).setChannelId(CHANNEL_ID);
            }
        }*/

       /* String message=data.getString(KEY_MESSAGE);
        if (imageUrl!=null && !imageUrl.isEmpty()) { // for image notification
            Bitmap imageBitmap = getBitmapFromUrl(imageUrl);
            imageUrl="";
            notificationBuilder.setContentTitle("WhiteCoats")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setLargeIcon(imageBitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imageBitmap))
                    *//*.addAction(R.drawable.ic_social_like, "LIKE", null)
                    .addAction(R.drawable.ic_social_comment, "COMMENT", null)*//*
                    .setContentIntent(pendingIntent)
                    .setContentText(message).setChannelId(CHANNEL_ID);

        } else {
            notificationBuilder.setContentTitle("WhiteCoats")
                    .setContentTitle("WhiteCoats")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setContentText(message).setChannelId(CHANNEL_ID);
        }*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(getResources().getColor(R.color.app_green));
            notificationBuilder.setSmallIcon(R.drawable.ic_notification);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_appicon);
        }
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m /* ID of notification */, notification);
    }

    private Bundle convertMapToBundle(Map<String, String> map) {
        Log.i(TAG, "convertMapToBundle(Map<String,String> map)");
        Bundle bundle = new Bundle();
        if (map.containsKey("dialog")) {
            bundle.putString("dialog", map.get("dialog"));
        }
        if (map.containsKey(KEY_DIALOG_ID)) {
            bundle.putString(KEY_DIALOG_ID, map.get(KEY_DIALOG_ID));
        }
        if (map.containsKey(USER_ID)) {
            bundle.putString(USER_ID, map.get(USER_ID));
        }
        if (map.containsKey(RestUtils.CHANNEL_ID)) {
            bundle.putInt(RestUtils.CHANNEL_ID, Integer.parseInt(map.get(RestUtils.CHANNEL_ID)));
        }
        if (map.containsKey(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION)) {
            //bundle.putInt(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, Integer.parseInt(map.get(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION)));
            bundle.putBoolean(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, Boolean.parseBoolean(map.get(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION)));
        }

        if (map.containsKey(RestUtils.FEED_TYPE_ID)) {
            bundle.putInt(RestUtils.FEED_TYPE_ID, Integer.parseInt(map.get(RestUtils.FEED_TYPE_ID)));
        } else if (map.containsKey(RestUtils.TAG_FEED_ID)) {
            bundle.putInt(RestUtils.FEED_TYPE_ID, Integer.parseInt(map.get(RestUtils.TAG_FEED_ID)));
        }
        if (map.containsKey(RestUtils.TAG_TYPE)) {
            bundle.putString(RestUtils.TAG_TYPE, map.get(RestUtils.TAG_TYPE));
        }
        if (map.containsKey(RestUtils.NOTIFICATION_TAG_ID)) {
            bundle.putString(RestUtils.NOTIFICATION_TAG_ID, map.get(RestUtils.NOTIFICATION_TAG_ID));
        }
        if (map.containsKey(KEY_TITLE)) {
            bundle.putString(KEY_MESSAGE, map.get(KEY_TITLE));
        }
        if (map.containsKey(KEY_MESSAGE)) {
            bundle.putString(KEY_MESSAGE, map.get(KEY_MESSAGE));
        }
        if (map.containsKey(RestUtils.TAG_C_MSG_TYPE)) {
            bundle.putString(RestUtils.TAG_C_MSG_TYPE, map.get(RestUtils.TAG_C_MSG_TYPE));
        }
        if (map.containsKey(KEY_HAS_IMAGE)) {
            bundle.putString(KEY_HAS_IMAGE, map.get(KEY_HAS_IMAGE));
        }

        if (map.containsKey("com.urbanairship.push.ALERT")) {
            bundle.putString(KEY_MESSAGE, map.get("com.urbanairship.push.ALERT"));
        }

        if (map.containsKey("alert")) {
            bundle.putString(KEY_MESSAGE, map.get("alert"));
        }

        if (map.containsKey(RestUtils.TAG_NOTIFICATION_ID)) {
            bundle.putString(RestUtils.TAG_NOTIFICATION_ID, map.get(RestUtils.TAG_NOTIFICATION_ID));
        }
        return bundle;
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]

            /** Deprecated, as with FCM implementation the library does it itself. **/

            /*InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);*/
            // [END get_token]

            Log.i(TAG, "GCM Registration Token: " + s);
            storeRegistrationId(s);
            sendRegistrationToServer(s);

            // Subscribe to topic channels
            //subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            sharedPreferences.edit().putBoolean(AppConstants.SENT_TOKEN_TO_SERVER, true).apply();
            // [END register_for_gcm]
            // Notify Urban Airship that the token is refreshed.
            //AirshipFirebaseInstanceIdService.processTokenRefresh(context);
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

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        String response;

        try {
            JSONObject object = new JSONObject();
            object.put(RestUtils.API_KEY,"");
            object.put(RestUtils.REG_ID, token);

            try {
                HttpClient client = new HttpClient(getApplicationContext(), RestApiConstants.PUSH_GCM_NOTIFICATION);
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
}
