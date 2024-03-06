package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.brandkinesis.BKUserInfo;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.callback.BKUserInfoCallback;
import com.flurry.android.FlurryAgent;
/*import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBGroupChat;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBMessageStatusesManager;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBRoster;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBMessageListener;
import com.quickblox.chat.listeners.QBMessageStatusListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.listeners.QBRosterListener;
import com.quickblox.chat.listeners.QBSubscriptionListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.model.QBPresence;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.core.request.QueryRule;
import com.quickblox.core.result.HttpStatus;
import com.quickblox.users.model.QBUser;*/
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.ConstsCore;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.GroupNotifyInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.MessageModel;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogMemInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogModel;
import com.vam.whitecoats.core.quickblox.qbrealm.RealmQBDialog;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.broadCasts.BroadCastAsync;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.FetchUserConnects;
import com.vam.whitecoats.utils.FileHelper;
import com.vam.whitecoats.utils.Foreground;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

/*import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.ping.PingManager;*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;


/**
 * Created by swathim on 5/8/2015.
 */
public class QBLogin {
    public static final String TAG = QBLogin.class.getSimpleName();
    //public static QBChatService chatService;
    private static Context context;
    private static LoginActivity loginActivity;
    private static DashboardActivity dashboardActivity;
    private static SplashScreenActivity splashScreenActivity;
    private static ChangePasswordActivity changePasswordActivity;
    private static String actname = "";
    private static OnTaskCompleted onTaskCompleteListener;
    private static String speciality;
    static ArrayList<String> dialogsRoomJIds = new ArrayList<String>();
    private static MySharedPref mySharedPref = null;
    private static Boolean is_user_active;
    //public static HashMap<String, QBChatDialog> dailogsHash = new HashMap<>();
    private static Integer networkNotificationCount;
    ArrayList<Integer> connectsQBIDs = new ArrayList<Integer>();


    private static int msg_type = 0;
    private static int skipMessages = 0;

    private static int doctorId;

    private static int login_doc_id = 0;
    private static String emailId;

    private static RealmBasicInfo basicInfo;
    public static String qb_hidden_dialog_id;


    public QBLogin(final Context context, String useremail, String password, String speciality, String actname, Boolean mIs_user_active) {
        this.context = context;
        this.actname = actname;
        this.speciality = speciality;
        this.is_user_active = mIs_user_active;
        onTaskCompleteListener = (OnTaskCompleted) context;
        if (actname.equals("Login")) {
            loginActivity = (LoginActivity) context;
            loginActivity.showProgress();
        } else if (actname.equals("StayLoggedIn")) {
            splashScreenActivity = (SplashScreenActivity) context;
        } else if (actname.equals(DashboardActivity.TAG)) {
            dashboardActivity = (DashboardActivity) context;
        } else {
            changePasswordActivity = (ChangePasswordActivity) context;
            changePasswordActivity.showProgress();
        }

        AppConstants.USER_LOGIN = useremail;
        AppConstants.USER_PASSWORD = password;
        mySharedPref = new MySharedPref(context);
        //createSession("qblogin", context);

        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Realm rmDb = Realm.getDefaultInstance();
                RealmManager rMgr = new RealmManager(context);
                dialogsRoomJIds.addAll(rMgr.getDialogsRoomJId(rmDb));
                connectsQBIDs.addAll(rMgr.getMyContactsQBId(rmDb));
                if (!rmDb.isClosed())
                    rmDb.close();
            }
        });

    }


    public QBLogin(Context context, String callFrom, OnTaskCompleted onTaskCompleted) {
        this.context = context;
        mySharedPref = new MySharedPref(context);
        onTaskCompleteListener = onTaskCompleted;
        if (callFrom.equalsIgnoreCase("NetworkChangeListener") || callFrom.equalsIgnoreCase("FromMandatory") || callFrom.equalsIgnoreCase("FromChangePassword") || callFrom.equalsIgnoreCase("FromDashBoard")) {
            //createSession(callFrom, context);
        } else {
            onTaskCompleteListener.onTaskCompleted("");
        }

    }

    //Create session and login to session
    /*public void createSession(final String callfrom, final Context contextv) {
        Log.i(TAG, "createSession()");
        Realm realm = null;
        RealmManager realmManager = null;
        skipMessages = 0;

        try {
            if (realm == null) {
                realm = Realm.getDefaultInstance();
                realmManager = new RealmManager(contextv);
                login_doc_id = realmManager.getDoc_id(realm);
                emailId = realmManager.getDoc_EmailId(realm);
            }
            final QBUser qbuser = new QBUser();
            basicInfo = realmManager.getRealmBasicInfo(realm);
            doctorId = realmManager.getDoc_id(realm);
            qbuser.setLogin(basicInfo.getQb_login());
            qbuser.setPassword(basicInfo.getPsswd());
            qb_hidden_dialog_id = basicInfo.getQb_hidden_dialog_id();
            //}
            Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());

            QBAuth.createSession(qbuser).performAsync(new QBEntityCallback<QBSession>() {
                @Override
                public void onSuccess(QBSession qbSession, Bundle bundle) {
                    Log.i(TAG, "QB Session created Successfully");
                    FlurryAgent.logEvent("QB Session created Successfully");
                    try {
                        qbuser.setId(qbSession.getUserId());
                        ((App_Application) contextv.getApplicationContext()).setCurrentUser(qbuser);
                        logInToChat(qbuser, callfrom, contextv);
                    } catch (Exception e) {
                        FlurryAgent.logEvent("QB Session created, Exception Follows");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(QBResponseException errors) {
                    FlurryAgent.logEvent("QB Session creation Failed");
                    Log.i(TAG, "QB Session creation Failed");
                    Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
                    *//**
                     * If the error is a Bad Timestamp (i.e UNPROCESSABLE ENTITY or Status 422)
                     *//*try {
                        if (errors.getHttpStatusCode() == HttpStatus.SC_UNPROCESSABLE_ENTITY && errors.getMessage().equalsIgnoreCase("base Bad timestamp")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(contextv);
                            dialog.setTitle("Incorrect Date & Time");
                            dialog.setMessage("Unable to login. Please update your time from the device settings and try again.");
                            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    contextv.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
                                }
                            });
                            dialog.show();
                            if (actname.equals("Login")) {
                                loginActivity.hideProgress();
                            }
                            return;
                        }

                        if (callfrom.equals("qblogin")) {
                            if (actname.equals("Login")) {
                                loginActivity.hideProgress();
                                mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                Intent i = new Intent(contextv, DashboardActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                contextv.startActivity(i);
                                loginActivity.finish();
                            } else {
                                onTaskCompleteListener.onTaskCompleted("");
                                if (changePasswordActivity != null) {
                                    changePasswordActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(contextv, "Unable to create session, please login again", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    changePasswordActivity.hideProgress();
                                    changePasswordActivity.finish();
                                    Intent i = new Intent(contextv, LoginActivity.class);
                                    contextv.startActivity(i);
                                }
                            }
                        } else {
                            onTaskCompleteListener.onTaskCompleted(ConstsCore.QB_LOGIN_ERROR);
                        }
                        // errors
                        if (contextv != null) {
                            *//*AlertDialog.Builder dialog = new AlertDialog.Builder(contextv);
                            try {
                                //dialog.setMessage("create session errors: " + errors).create().show();
                                //Toast.makeText(contextv, context.getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {

                            }*//*
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }*/


    //login to chat
//    public void logInToChat(final QBUser qbuser, final String callfrom, final Context contextv) {
//        Log.i(TAG, "logInToChat()");
//        final Realm realm = Realm.getDefaultInstance();
//        final RealmManager realmManager = new RealmManager(contextv);
//        /*login_doc_id = realmManager.getDoc_id(realm);
//        emailId=realmManager.getDoc_EmailId(realm);*/
//        try {
//            QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
//            chatServiceConfigurationBuilder.setSocketTimeout(60); //Sets chat socket's read timeout in seconds
//            chatServiceConfigurationBuilder.setKeepAlive(true); //Sets connection socket's keepAlive option.
//            chatServiceConfigurationBuilder.setUseTls(true); //Sets the TLS security mode used when making the connection. By default TLS is disabled.
////            chatServiceConfigurationBuilder.setAutojoinEnabled(true);
//            chatService = QBChatService.getInstance();
//            chatService.setConfigurationBuilder(chatServiceConfigurationBuilder);
//            // adding connection listener
//            chatService.addConnectionListener(connectionListener);
//            chatService.setUseStreamManagement(true);
//            /**
//             * value Changed we should allow reconnection if user gets disconnected from Chat
//             * Changed By : Satya
//             */
//            chatService.setReconnectionAllowed(true);
//            Log.e("QBLogin", "chatService.isLoggedIn() : " + chatService.isLoggedIn());
//            final Boolean mandatory_profile_check = mySharedPref.getPref(MySharedPref.PREF_MANDATORY_PROFILE_CHECK, null);
//            if (chatService.isLoggedIn()) {
//                Log.i("TAG", "already login onSuccess");
//                QBIncomingMessagesManager incomingMessagesManager = QBChatService.getInstance().getIncomingMessagesManager();
//                incomingMessagesManager.addDialogMessageListener(chatMessageListener);
//                callMessageStatus();
//                syncMessages(0, false);
//                joinGroupDialogs(0);
//                DiscussionHistory history = new DiscussionHistory();
//                history.setMaxStanzas(0);
//
//                if (callfrom.equals("qblogin")) {
//                    if (actname.equals("Login")) {
//                        loginActivity.hideProgress();
//                        if (speciality != null) {
//                            if ((mandatory_profile_check != null && !is_user_active)) {
//                                Intent i = new Intent(contextv, MandatoryProfileInfo.class);
//                                contextv.startActivity(i);
//                                loginActivity.finish();
//                            } else {
//                                mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
//                                Intent i = new Intent(contextv, DashboardActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                contextv.startActivity(i);
//                                loginActivity.finish();
//                            }
//                        } else {
//                            mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
//                            Intent i = new Intent(contextv, DashboardActivity.class);
//                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            contextv.startActivity(i);
//                            loginActivity.finish();
//                        }
//                    } else {
//                        if (changePasswordActivity != null) {
//                            changePasswordActivity.hideProgress();
//                            changePasswordActivity.finish();
//                        }
//                        onTaskCompleteListener.onTaskCompleted("");
//                        Intent i = new Intent(contextv, DashboardActivity.class);
//                        contextv.startActivity(i);
//                    }
//                } else {
//                    onTaskCompleteListener.onTaskCompleted(ConstsCore.QB_LOGIN_SUCCESS);
//                }
//            } else {
//                final int conntectCount = realmManager.getConnectsCount(realm);
//                Log.i(TAG, "logInToChat() : Login TO ChatService");
//
//                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
//                chatService.login(qbuser, new QBEntityCallback() {
//                    @Override
//                    public void onSuccess(Object o, Bundle bundle) {
//                        FlurryAgent.logEvent("Login to QB Chat Successful");
//                        Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
//                        try {
//                            /**
//                             * Call connection service and restore all connects for existing users.
//                             */
//                            JSONObject requestData = new JSONObject();
//                            try {
//                                requestData.put(RestUtils.TAG_DOC_ID, doctorId);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if (!mySharedPref.getPref("connects_sync_completed",false)) {
//                                if (!callfrom.equalsIgnoreCase("FromMandatory")) {
//                                    Log.i(TAG, "Restoring user connects");
//                                    Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
//                                    if (loginActivity != null) {
//                                        loginActivity.showProgress();
//                                    }
//                                    new FetchUserConnects(context, login_doc_id,mySharedPref.getPref("last_doc_id",0) , true, realm, realmManager, new OnReceiveResponse() {
//                                        @Override
//                                        public void onSuccessResponse(String successResponse) {
//                                            if (loginActivity != null) {
//                                                loginActivity.hideProgress();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onErrorResponse(String errorResponse) {
//                                            if (loginActivity != null) {
//                                                loginActivity.hideProgress();
//                                            }
//                                        }
//                                    });
//                                    /*new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.RESTORE_USER_CONNECTS, requestData.toString(), "QB_LOGIN", new OnReceiveResponse() {
//                                        @Override
//                                        public void onSuccessResponse(String successResponse) {
//                                            if (loginActivity != null) {
//                                                loginActivity.hideProgress();
//                                            }
//                                            Log.i(TAG, "ChatService.login : onTaskCompleted()");
//                                            Log.d(TAG, "Response string :" + successResponse);
//                                            Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
//                                            try {
//                                                JSONObject responseJsonObj = new JSONObject(successResponse);
//                                                if (responseJsonObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
//                                                    JSONArray jsonArray = responseJsonObj.optJSONArray(RestUtils.TAG_DATA);
//                                                    int size = jsonArray.length();
//                                                    for (int count = 0; count < size; count++) {
//                                                        JSONObject jsonObj = jsonArray.optJSONObject(count);
//                                                        ContactsInfo contactsInfo = new ContactsInfo();
//                                                        contactsInfo.setDoc_id(jsonObj.optInt(RestUtils.TAG_DOC_ID));
//                                                        contactsInfo.setNetworkStatus(jsonObj.optString(RestUtils.TAG_CONNECT_STATUS));
//                                                        *//**
//                                                         * Get the Details Json Object
//                                                         *//*
//                                                        JSONObject detailsJsonObj = new JSONObject(jsonObj.optString("card_info"));
//                                                        contactsInfo.setEmail(detailsJsonObj.optString(RestUtils.TAG_CNT_EMAIL));
//                                                        contactsInfo.setPhno(detailsJsonObj.optString(RestUtils.TAG_CNT_NUM));
//                                                        contactsInfo.setQb_userid(detailsJsonObj.optInt(RestUtils.TAG_QB_USER_ID));
//                                                        contactsInfo.setSpeciality(detailsJsonObj.optString(RestUtils.TAG_SPLTY));
//                                                        contactsInfo.setSubSpeciality(detailsJsonObj.optString(RestUtils.TAG_SUB_SPLTY, ""));
//                                                        contactsInfo.setName((detailsJsonObj.has(RestUtils.TAG_USER_FULL_NAME)) ? detailsJsonObj.optString(RestUtils.TAG_USER_FULL_NAME) : detailsJsonObj.optString(RestUtils.TAG_FULL_NAME));
//                                                        contactsInfo.setLocation(detailsJsonObj.optString(RestUtils.TAG_LOCATION));
//                                                        contactsInfo.setWorkplace(detailsJsonObj.optString(RestUtils.TAG_WORKPLACE));
//                                                        contactsInfo.setDesignation(detailsJsonObj.optString(RestUtils.TAG_DESIGNATION));
//                                                        contactsInfo.setDegree(detailsJsonObj.optString(RestUtils.TAG_DEGREE));
//                                                        contactsInfo.setPic_name(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_NAME));
//                                                        contactsInfo.setPic_url(detailsJsonObj.optString(RestUtils.TAG_PROFILE_PIC_URL));
//                                                        contactsInfo.setUserSalutation(detailsJsonObj.optString(RestUtils.TAG_USER_SALUTAION));
//                                                        contactsInfo.setUserTypeId(detailsJsonObj.optInt(RestUtils.TAG_USER_TYPE_ID));
//                                                        *//**
//                                                         * Check whether doc_id exists in database, if exists then update it  else insert a new record.
//                                                         *//*
//                                                        boolean isDoctorExists = realmManager.isDoctorExists(realm, contactsInfo.getDoc_id());
//                                                        if (isDoctorExists) {
//                                                            realmManager.updateMyContacts(realm, contactsInfo);
//                                                        } else {
//                                                            realmManager.insertMyContacts(realm, contactsInfo, Integer.parseInt(contactsInfo.getNetworkStatus()));
//                                                        }
//                                                    }
//                                                }
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onErrorResponse(String errorResponse) {
//                                            if (loginActivity != null) {
//                                                loginActivity.hideProgress();
//                                            }
//                                            //Toast.makeText(context,context.getString(R.string.unable_to_connect_server),Toast.LENGTH_SHORT).show();
//                                        }
//                                    }).sendSinglePartRequest();*/
//                                }
//                            }
//                            /*if (actname.length() > 0) {
//                                if (actname.equals("Login")) {
//                                    playServicesHelper = new PlayServicesHelper(loginActivity);
//                                } else if (actname.equals("StayLoggedIn")) {
//                                    playServicesHelper = new PlayServicesHelper(splashScreenActivity);
//                                } else {
//                                    playServicesHelper = new PlayServicesHelper(changePasswordActivity);
//                                }
//                            }
//                            if (callfrom.equalsIgnoreCase("FromMandatory")) {
//                                playServicesHelper = new PlayServicesHelper((MandatoryProfileInfo) contextv);
//                            } else if (callfrom.equalsIgnoreCase("FromChangePassword")) {
//                                playServicesHelper = new PlayServicesHelper((ChangePasswordActivity) contextv);
//                            } else if (callfrom.equalsIgnoreCase("FromDashBoard")) {
//                                playServicesHelper = new PlayServicesHelper((DashboardActivity) contextv);
//                            }*/
//                            /**
//                             * Commented as too many duplicate calls to this method, above we are already calling this method.
//                             * Changed By : Satya
//                             */
////                            playServicesHelper.checkPlayServices();
//                            chatService.startAutoSendPresence(60);
//                            QBIncomingMessagesManager incomingMessagesManager = QBChatService.getInstance().getIncomingMessagesManager();
//                            incomingMessagesManager.addDialogMessageListener(chatMessageListener);
//                            callMessageStatus();
//                            syncMessages(0, false);
//                            joinGroupDialogs(0);
//                            DiscussionHistory history = new DiscussionHistory();
//                            history.setMaxStanzas(0);
//
//
//                            //incomingMessagesManager.addMessageListener(chatMessageListener);
//                            //chatService.getPrivateChatManager().addPrivateChatManagerListener(privateChatManagerListener);
//                            Log.i(TAG, "login onSuccess");
//                            if (callfrom.equals("qblogin")) {
//                                if (actname.equals("Login")) {
//                                    loginActivity.hideProgress();
//                                    if (speciality != null) {
//                                        if ((mandatory_profile_check != null && !is_user_active)) {
//                                            Intent i = new Intent(contextv, MandatoryProfileInfo.class);
//                                            contextv.startActivity(i);
//                                            loginActivity.finish();
//                                        } else {
//                                            mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
//                                            Intent i = new Intent(contextv, DashboardActivity.class);
//                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                            contextv.startActivity(i);
//                                            loginActivity.finish();
//                                        }
//                                    } else {
//                                        mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
//                                        Intent i = new Intent(contextv, DashboardActivity.class);
//                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        contextv.startActivity(i);
//                                        loginActivity.finish();
//                                    }
//                                } else {
//                                    onTaskCompleteListener.onTaskCompleted("");
//                                    if (changePasswordActivity != null) {
//                                        changePasswordActivity.runOnUiThread(new Runnable() {
//                                            public void run() {
//                                                Toast.makeText(changePasswordActivity, "Password has been updated successfully.", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                        changePasswordActivity.hideProgress();
//                                        changePasswordActivity.finish();
//                                        Intent i = new Intent(contextv, DashboardActivity.class);
//                                        contextv.startActivity(i);
//                                    }
//                                }
//                            } else {
//                                onTaskCompleteListener.onTaskCompleted(ConstsCore.QB_LOGIN_SUCCESS);
//                            }
//                        } catch (Exception e) {
//                            Log.e("TAG", "SmackException.NotLoggedInException" + e);
//                            e.printStackTrace();
//                            if (callfrom.equals("qblogin")) {
//                                if (actname.equals("Login")) {
//                                    loginActivity.hideProgress();
//                                } else {
//                                    onTaskCompleteListener.onTaskCompleted("");
//                                    if (changePasswordActivity != null) {
//                                        changePasswordActivity.runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(contextv, "Unable to create session, please login again", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                        changePasswordActivity.hideProgress();
//                                        changePasswordActivity.finish();
//                                        Intent i = new Intent(contextv, DashboardActivity.class);
//                                        contextv.startActivity(i);
//                                    }
//                                }
//                            } else {
//                                onTaskCompleteListener.onTaskCompleted(ConstsCore.QB_LOGIN_ERROR);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(QBResponseException errors) {
//                        FlurryAgent.logEvent("Login to QB Chat Failed-" + errors.getLocalizedMessage());
//                        Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
//                        try {
//                            if (callfrom.equals("qblogin")) {
//                                if (actname.equals("Login")) {
//                                    loginActivity.hideProgress();
//                                    /**
//                                     * Toast or (other functions dealing with UI) needs to be called from Main thread.
//                                     */
//                                    /*loginActivity.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toast.makeText(context, context.getString(R.string.alert_login_failed), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });*/
//                                    mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
//                                    Intent i = new Intent(contextv, DashboardActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    contextv.startActivity(i);
//                                    loginActivity.finish();
//                                } else {
//                                    onTaskCompleteListener.onTaskCompleted("");
//                                    if (changePasswordActivity != null) {
//                                        changePasswordActivity.runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                Toast.makeText(contextv, "Unable to create session, please login again", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                                        changePasswordActivity.hideProgress();
//                                        changePasswordActivity.finish();
//                                        Intent i = new Intent(contextv, LoginActivity.class);
//                                        contextv.startActivity(i);
//                                    }
//                                    //changePasswordActivity.hideProgress();
//                                }
//                            } else {
//                                onTaskCompleteListener.onTaskCompleted(ConstsCore.QB_LOGIN_ERROR);
//                            }
//                            /*AlertDialog.Builder dialog = new AlertDialog.Builder(contextv);
//                            dialog.setMessage("chat login errors: " + errors).create().show();*/
//                        } catch (Exception e) {
//                            FlurryAgent.logEvent("onError() Exception");
//                            e.printStackTrace();
//                        }
//                        AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
//                    }
//
//                });
//            }
//           /* NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            nMgr.cancelAll();*/
//            App_Application.setNumUnreadMessages(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (!realm.isClosed())
//                realm.close();
//        }
//
//    }

    /*private void syncMessages(int skipCount, boolean isRecursive) {
        Log.e(TAG, "syncMessages()");
        //final ArrayList<QBChatDialog> updateDialogList = new ArrayList<QBChatDialog>();
        try {
            App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Realm realm = Realm.getDefaultInstance();
                    final RealmManager realmManager = new RealmManager(context);
                    final QBChatDialog hiddenDialog = new QBChatDialog();
                    hiddenDialog.setDialogId(basicInfo.getQb_hidden_dialog_id());

                    QBRequestGetBuilder customObjectRequestBuilder = new QBRequestGetBuilder();
                    customObjectRequestBuilder.setLimit(100);
                    customObjectRequestBuilder.setSkip(skipCount);
                    RealmResults<RealmQBDialog> listOfDBDlgs = realmManager.getQBDialogsDB(realm);
                    if (listOfDBDlgs.size() > 0 && isRecursive == false) {
                        customObjectRequestBuilder.gt("last_message_date_sent", "" + (listOfDBDlgs.get(0).getLast_msg_time() / 1000));
//                        customObjectRequestBuilder.addRule("last_message_date_sent",QueryRule.GT,listOfDBDlgs.get(0).getLast_msg_time());
                    }
                    customObjectRequestBuilder.sortDesc("last_message_date_sent");
//                    Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
                    try {
                        QBRestChatService.getChatDialogs(null, customObjectRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
                            @Override
                            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {
                                Log.e(TAG, "onSuccess() - QBRestChatService.getChatDialogs");
                                Log.e(TAG, "QBChatDialogs - " + qbChatDialogs);
//                                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
                                *//**
                                 * Get the dialog details that was deleted in offline
                                 *//*
                                ArrayList<QBDialogModel> deletedDialogs = realmManager.getOfflineDeletedDialogs(realm);
//                                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time: " + DateUtils.getCurrentTime());
                                for (QBChatDialog dialog : qbChatDialogs) {
                                    Log.e(TAG, "Inside LOOOOOOOOOOOP");
                                    *//**
                                     * Check whether the current dialog is a hidden dialog.
                                     * #Note : Hidden dialogs are the messages we got when a new member added in a group
                                     * or else opponent accepted your connect request.
                                     * #E.g : Dr. XYZ added in XXXX group.(Hidden dialog)
                                     *//*
                                    String qbDialogid = dialog.getDialogId();
                                    //if (!hiddenDialog.getDialogId().equals(qbDialogid)) {
                                    *//**
                                     * Check for unread count and also check in database whether dialog exist or not.
                                     *//*
                                    int unreadQBMessagesCount = dialog.getUnreadMessageCount();
                                    if (dialog.getUnreadMessageCount() != null && unreadQBMessagesCount != 0 && realmManager.checkDialoginDB(realm, qbDialogid) != 0) {
                                        *//**
                                         * Check whether there are any offline deleted dialogs
                                         *//*
                                        if (deletedDialogs.size() > 0) {
                                            *//**
                                             * Check whether this QBDialog is an offline deleted dialog or not
                                             *//*
                                            Log.i(TAG, "Offline deleted dialogs clearance");
//                                                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , Start Time : " + DateUtils.getCurrentTime());
                                            for (QBDialogModel dialogModel : deletedDialogs) {
                                                String offlineDeletedDialogId = dialogModel.getDialog_id();
                                                if (qbDialogid.equals(offlineDeletedDialogId)) {
                                                    int unreadCount = dialogModel.getUnread_count();
                                                    int newUnreadCount = unreadQBMessagesCount - unreadCount;
                                                    if (newUnreadCount > 0) {
                                                        dialog.setUnreadMessageCount(newUnreadCount);
                                                        //updateDialogList.add(dialog);
                                                        dailogsHash.put(dialog.getDialogId(), dialog);
                                                        realmManager.updateQBDialog(realm, dialog);
                                                    } else {
                                                        realmManager.deleteConversationDialogfromLocal(realm, offlineDeletedDialogId);
                                                        *//**
                                                         * Now delete dialog from QB after deleting from locally
                                                         *//*
                                                        QBRestChatService.deleteDialog(dialogModel.getDialog_id(), false).performAsync(new QBEntityCallback<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid, Bundle bundle) {

                                                            }

                                                            @Override
                                                            public void onError(QBResponseException e) {

                                                            }
                                                        });
                                                            *//*QBPrivateChatManager manager = QBChatService.getInstance().getPrivateChatManager();
                                                            try {
                                                                manager.deleteDialog(dialogModel.getDialog_id());
                                                            } catch (QBResponseException e) {
                                                                e.printStackTrace();
                                                            }*//*
                                                        QBLogin.updateDashboardDialogCount();
                                                    }

                                                } else {
                                                    //updateDialogList.add(dialog);
                                                    dailogsHash.put(dialog.getDialogId(), dialog);
                                                    realmManager.updateQBDialog(realm, dialog);
                                                }
                                            }
                                            Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
                                        } else {
                                            //updateDialogList.add(dialog);
                                            dailogsHash.put(dialog.getDialogId(), dialog);
                                            realmManager.updateQBDialog(realm, dialog);
                                        }
                                    } else if (realmManager.checkDialoginDB(realm, dialog.getDialogId()) == 0) {

                                        //used for 1-1 tile deletion
                                        //QBDialogModel qBDialogModel = realmManager.getCRQBDialogs(realm, dialog.getDialogId());
                                        //dialog.set

                                        if (realmManager.checkGroupDialoginDB(realm, dialog.getDialogId()) == 0) {
                                            final QBDialogModel groupDialogModel = new QBDialogModel();
                                            groupDialogModel.setDialog_id(dialog.getDialogId());
                                            groupDialogModel.setDialog_title(dialog.getName());
                                            groupDialogModel.setDialog_pic_name("");
                                            *//**
                                             * rename group pic
                                             *//*
                                                *//*File from = new File(selectedImagePath);
                                                File to = new File(Environment.getExternalStorageDirectory() + "/Whitecoats/Group_Pic/" + group_img_name);
                                                from.renameTo(to);
                                                from.setWritable(true);
                                                from.delete();*//*
                                            groupDialogModel.setOpponent_doc_id(0);
                                            groupDialogModel.setDialog_creation_date(dialog.getCreatedAt().getTime());
                                            groupDialogModel.setLast_msg_time(dialog.getCreatedAt().getTime());
                                            groupDialogModel.setDialog_roomjid(dialog.getRoomJid());
                                            groupDialogModel.setLocal_dialog_type("groupchat");
                                            *//**
                                             * Insert dialog to database
                                             *//*
                                            realmManager.insertGroupDialog(realm, groupDialogModel);
                                            ArrayList<QBDialogMemInfo> dialogMemInfos = new ArrayList<>();
                                            int doc_id = realmManager.getDoc_id(realm);
                                            for (int i = 0; i < dialog.getOccupants().size(); i++) {
                                                QBDialogMemInfo dialogMemInfo = new QBDialogMemInfo();
                                                dialogMemInfo.setDialog_id(dialog.getDialogId());
                                                dialogMemInfo.setDoc_id(realmManager.getDocIdByQBId(realm, dialog.getOccupants().get(i).intValue()));
                                                if (dialog.getUserId().longValue() == dialog.getOccupants().get(i).longValue()) {
                                                    dialogMemInfo.setIs_admin(true);
                                                    dialogMemInfo.setInvite_response(1);
                                                } else {
                                                    dialogMemInfo.setIs_admin(false);
                                                    dialogMemInfo.setInvite_response(0);
                                                }
                                                dialogMemInfos.add(dialogMemInfo);
                                            }
                                            *//**
                                             * saving in members info
                                             *//*
                                            realmManager.insertDialogMem(realm, dialogMemInfos);
                                            dialog.setLastMessage("");
                                            dialog.setLastMessageDateSent(dialog.getCreatedAt().getTime() / 1000);
                                        }
                                        //updateDialogList.add(dialog);
                                        dailogsHash.put(dialog.getDialogId(), dialog);
                                        Log.d("insertQBDialog", "insertQBDialog");
                                        realmManager.insertQBDialog(realm, dialog);

                                    }
                                    *//*} else if (hiddenDialog.getDialogId().equals(dialog.getDialogId())) {
                                        updateDialogList.add(dialog);
                                        dailogsHash.put(dialog.getDialogId(), dialog);
                                        dialog.setUnreadMessageCount(0);
                                        realmManager.updateQBDialog(realm, dialog);
                                    }*//*
                                    if (hiddenDialog.getDialogId().equals(dialog.getDialogId())) {
                                        Log.e("HIDDEN_DIALOG","hiddenDialogMsg");
                                        loadNotificationMessages(dialog, 0);
                                    }
                                    new SyncQBMessages(context, dialog, hiddenDialog).loadChatMessages();
                                }

//                                    }
//                                }

//                                Log.e(TAG, "Line No : " + Thread.currentThread().getStackTrace()[2].getLineNumber() + " , End Time: " + DateUtils.getCurrentTime());
                                //markallmessagesAsRead();
                                updateDashboardDialogCount();
                                if (bundle.getInt("total_entries") > (bundle.getInt("skip") + bundle.getInt("limit"))) {
                                    syncMessages(bundle.getInt("skip") + bundle.getInt("limit"), true);
                                    return;
                                }
                                *//*RealmResults<RealmQBDialog> listOfDBDlgs = realmManager.getGroupDialogsDB(realm);
                                for(RealmQBDialog dialog:listOfDBDlgs) {
                                    joinGroupDialogs(dialog.getDialog_id());
                                }*//*

                                if (!realm.isClosed())
                                    realm.close();
                                AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLogin_syncMsgsuccess", "" + login_doc_id, emailId);
                            }

                            @Override
                            public void onError(QBResponseException e) {
                                Log.d("Qblogin ", "getChatDialogs");
                                AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);

                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return updateDialogList;
    }*/

    /*private static void loadNotificationMessages(QBChatDialog dialog, int skipCount) {
        Log.i(TAG, "loadNotificationMessages()");
        Realm realm = Realm.getDefaultInstance();
        RealmManager realmManager = new RealmManager(context);
        *//*login_doc_id = realmManager.getDoc_id(realm);
        emailId=realmManager.getDoc_EmailId(realm);*//*
        try {
            long lastMessageDateSent = realmManager.getLastMessagesdate(realm, dialog.getDialogId());

            if (lastMessageDateSent == 0)
                lastMessageDateSent = ConstsCore.ZERO_LONG_VALUE;

            //getting messages from qb by sending last message time
            QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
            messageGetBuilder.setSkip(skipCount);
            messageGetBuilder.setLimit(dialog.getUnreadMessageCount());
            messageGetBuilder.sortDesc(com.quickblox.chat.Consts.MESSAGE_DATE_SENT);
            skipMessages += ConstsCore.DIALOG_MESSAGES_PER_PAGE;
            if (lastMessageDateSent != ConstsCore.ZERO_LONG_VALUE) {
                messageGetBuilder.gt(com.quickblox.chat.Consts.MESSAGE_DATE_SENT, lastMessageDateSent);
            }
            if(dialog.getUnreadMessageCount()==0){
                return;
            }
            QBRestChatService.getDialogMessages(dialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
                @Override
                public void onSuccess(ArrayList<QBChatMessage> historyMessages, Bundle bundle) {
                    Log.i(TAG, "QBChatService.getDialogMessages : " + historyMessages.size());
                    try {
                        for (QBChatMessage historyMessage : historyMessages) {
                            Log.e(TAG, "upDateNotification() - from loadNotificationMessages() ");
                            JSONObject ntfy_Data;
                            String ntfy_Type;
                            boolean isDefaultConnect = false;
                            try {
                                if (historyMessage.getProperty("c_ntfy_data") != null) {
                                    ntfy_Data = new JSONObject(historyMessage.getProperty("c_ntfy_data").toString());
                                    ntfy_Type = ntfy_Data.optString(RestUtils.TAG_TYPE);
                                    isDefaultConnect = ntfy_Type.equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (SplashScreenActivity.bundle == null && !isDefaultConnect && historyMessage.getProperty("c_ntfy_data") != null) {
                                Bundle messageBundle = new Bundle();
                                messageBundle.putString("message", historyMessage.getBody());
                                messageBundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
                                SplashScreenActivity.bundle = messageBundle;
                            } else if (SplashScreenActivity.bundle != null && SplashScreenActivity.bundle.getString("message") == null) {
                                SplashScreenActivity.bundle.putString("message", historyMessage.getBody());
                                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
                            } else if (SplashScreenActivity.bundle != null && historyMessage.getBody().contains(SplashScreenActivity.bundle.getString("message"))) {
                                SplashScreenActivity.bundle.putString("message", historyMessage.getBody());
                                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, false);
                            }
                            upDateNotification(historyMessage, "loadFromNotifications");
                            saveMessageToCache(historyMessage, "", "", "", "incoming", 9, msg_type);
                        }
                        if (bundle.getInt("total_entries") > (bundle.getInt("skip") + bundle.getInt("limit"))) {
                            loadNotificationMessages(dialog, bundle.getInt("skip") + bundle.getInt("limit"));
                            return;
                        } else {
                            markallmessagesAsRead(dialog.getDialogId());
                        }

                    } catch (IndexOutOfBoundsException e) {
                        Log.d("Exception", "" + e);
                    } catch (RealmException re) {
                        Log.d("RealmException", " c" + re);
                        re.printStackTrace();
                    } catch (Exception e) {
                        Log.d("Exception", "" + e);
                    }
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.e(TAG, "QBResponseException fetching dialog messages");
                    e.printStackTrace();
                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }*/

    /*private static void callMessageStatus() {
        Log.i(TAG, "callMessageStatus");
        try {
            QBMessageStatusesManager messageStatusesManager;
            QBMessageStatusListener messageStatusListener;
            messageStatusesManager = QBChatService.getInstance().getMessageStatusesManager();
            messageStatusListener = new QBMessageStatusListener() {
                @Override
                public void processMessageDelivered(String messageId, String dialogId, Integer userId) {
                    Log.i(TAG, "processMessageDelivered");
                    final String messageIdtemp = messageId;
                    if (ChatActivity.getInstance() != null)
                        ChatActivity.getInstance().updateMessageStatusDeliveredLocal(messageId);
                    else {
                        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Realm rmDb = Realm.getDefaultInstance();
                                RealmManager rManager = new RealmManager(context);
                                rManager.updateMessageStatus(rmDb, messageIdtemp, 2);
                                if (!rmDb.isClosed())
                                    rmDb.close();
                            }
                        });
                    }

                }

                @Override
                public void processMessageRead(String messageId, String dialogId, Integer userId) {
                    Log.i("QBLogin", "processMessageRead");
                    final Realm realm = Realm.getDefaultInstance();
                    final RealmManager realmManager = new RealmManager(context);
                    final String messageIdtemp = messageId;
                    if (ChatActivity.getInstance() != null)
                        ChatActivity.getInstance().updateMessageStatusReadLocal(messageId);

                    else {
                        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Realm mRealm = Realm.getDefaultInstance();
                                RealmManager mRealmManager = new RealmManager(context);
                                mRealmManager.updateMessageStatus(mRealm, messageIdtemp, 3);
                                if (!mRealm.isClosed())
                                    mRealm.close();
                            }
                        });
                    }
                    if (!realm.isClosed())
                        realm.close();
                }
            };
            messageStatusesManager.addMessageStatusListener(messageStatusListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*static ConnectionListener connectionListener = new ConnectionListener() {

        @Override
        public void connected(XMPPConnection connection) {
            Log.i(TAG, "connected(XMPPConnection connection)");
            //update_dialogs = syncMessages();
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean b) {
            Log.i(TAG, "authenticated(XMPPConnection connection, boolean b)");

        }

        @Override
        public void connectionClosed() {
            Log.i(TAG, "connectionClosed()");

        }

        @Override
        public void connectionClosedOnError(Exception e) {
            // connection closed on error. It will be established soon
            Log.i(TAG, "connectionClosedOnError " + e);
        }

        @Override
        public void reconnectingIn(int seconds) {
            Log.i(TAG, "reconnectingIn " + seconds);
            PingManager.setDefaultPingInterval(1);
            Log.v("PingManagerQBLogin", "Ping Successful");
        }

        @Override
        public void reconnectionSuccessful() {
            Log.i(TAG, "reconnectionSuccessful()");
        }

        @Override
        public void reconnectionFailed(Exception e) {
            Log.e(TAG, "reconnectionFailed " + e);

        }
    };*/


    /*public static QBChatDialogMessageListener chatMessageListener = new QBChatDialogMessageListener() {
        @Override
        public void processMessage(String dialogId, final QBChatMessage chatMessage, Integer senderId) {
            if (chatMessage == null) {
                return;
            }
            final QBChatDialog chatDialog = getChatDialogByDailogId(dialogId);
            if (chatDialog != null && chatDialog.getType() == QBDialogType.GROUP) {
                if (chatMessage.getSenderId() != null && chatMessage.getSenderId().longValue() == chatMessage.getRecipientId().longValue()) {
                    return;
                }
                App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Realm realm = Realm.getDefaultInstance();
                        final RealmManager realmManager = new RealmManager(context);
                        login_doc_id = realmManager.getDoc_id(realm);
                        emailId = realmManager.getDoc_EmailId(realm);
                        Log.d("MessageListener Group", "processMessage at qblogin");
                        try {
                            Date memaddeddate = null;
                            final String dialog_type = realmManager.getDialogType(realm, chatMessage.getDialogId());
                            final long memaddedDate = realmManager.getDialog_memaddedDate(realm, chatMessage.getDialogId());
                            if (memaddedDate != 0) {
                                memaddeddate = new Date(memaddedDate);
                            }
                            Date datesent = new Date(chatMessage.getDateSent() * 1000);
                            if (chatMessage.getBody() != null) {
                                JSONObject jsonObject = new JSONObject(chatMessage.getProperties());
                                msg_type = 0;
                                if (dialog_type.equals("groupchat")) {
                                    if (memaddeddate != null) {
                                        if (jsonObject.has("c_ntfy_data")) {
                                            customParameterDataInsertion(jsonObject, chatMessage);
                                            processGroupMessage(chatMessage, chatDialog, false);
                                        } else {
                                            processGroupMessage(chatMessage, chatDialog, false);
                                        }
                                    } else {
                                        if (jsonObject.has("c_ntfy_data")) {
                                            customParameterDataInsertion(jsonObject, chatMessage);
                                            processGroupMessage(chatMessage, chatDialog, false);
                                        } else {
                                            processGroupMessage(chatMessage, chatDialog, false);
                                        }
                                    }
                                } else if (dialog_type.equals("caseroom") && jsonObject.has("c_ntfy_data")) {
                                    customParameterDataInsertion(jsonObject, chatMessage);
                                    processGroupMessage(chatMessage, chatDialog, true);
                                } else {
                                    processGroupMessage(chatMessage, chatDialog, true);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("QBLogin", "Exception at saving data");
                        }
                        if (!realm.isClosed())
                            realm.close();

                    }
                });
            } else {
                processOnetoOneMessage(dialogId, chatMessage);
            }
        }

        @Override
        public void processError(String dialogId, QBChatException exception, QBChatMessage message, Integer senderId) {
            Log.d(TAG, "Exception-" + exception);
        }
    };*/

    /*public static QBMessageListener<QBPrivateChat> privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
        @Override
        public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {
            Log.d("MessageListener Private", "processMessage at qblogin");
            //Intent intent = new Intent("processMessage");
            //LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
            //processOnetoOneMessage(privateChat, chatMessage);
        }

        @Override
        public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage) {
            Log.d("processMessage", "processError");
        }
    };*/
    /*public static QBMessageListener<QBGroupChat> groupChatMessageListener = new QBMessageListener<QBGroupChat>() {
        @Override
        public void processMessage(final QBGroupChat privateChat, final QBChatMessage chatMessage) {

            if (chatMessage.getSenderId().longValue() == chatMessage.getRecipientId().longValue()) {
                return;
            }
            App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Realm realm = Realm.getDefaultInstance();
                    final RealmManager realmManager = new RealmManager(context);
                    login_doc_id = realmManager.getDoc_id(realm);
                    emailId = realmManager.getDoc_EmailId(realm);
                    Log.d("MessageListener Group", "processMessage at qblogin");
                    try {
                        Date memaddeddate = null;
                        final String dialog_type = realmManager.getDialogType(realm, chatMessage.getDialogId());
                        final long memaddedDate = realmManager.getDialog_memaddedDate(realm, chatMessage.getDialogId());
                        if (memaddedDate != 0) {
                            memaddeddate = new Date(memaddedDate);
                        }
                        Date datesent = new Date(chatMessage.getDateSent() * 1000);
                        if (chatMessage.getBody() != null) {
                            JSONObject jsonObject = new JSONObject(chatMessage.getProperties());
                            msg_type = 0;
                            if (dialog_type.equals("groupchat")) {
                                if (memaddeddate != null) {
                                    if (memaddeddate.before(datesent) && jsonObject.has("c_ntfy_data")) {
                                        customParameterDataInsertion(jsonObject, chatMessage);
                                        //processGroupMessage(chatMessage, privateChat, false);
                                    } else if (memaddeddate.before(datesent)) {
                                        //processGroupMessage(chatMessage, privateChat, false);
                                    }
                                } else {
                                    if (jsonObject.has("c_ntfy_data")) {
                                        customParameterDataInsertion(jsonObject, chatMessage);
                                        //processGroupMessage(chatMessage, privateChat, false);
                                    } else {
                                        //processGroupMessage(chatMessage, privateChat, false);
                                    }
                                }
                            } else if (dialog_type.equals("caseroom") && jsonObject.has("c_ntfy_data")) {
                                customParameterDataInsertion(jsonObject, chatMessage);
                                //processGroupMessage(chatMessage, privateChat, true);
                            } else {
                                //processGroupMessage(chatMessage, privateChat, true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("QBLogin", "Exception at saving data");
                    }
                    if (!realm.isClosed())
                        realm.close();
                }
            });
        }

        @Override
        public void processError(QBGroupChat privateChat, QBChatException error, QBChatMessage originMessage) {
            Log.d("processMessage", "processError");
        }
    };*/

    /*private static void processOnetoOneMessage(String dialogId, final QBChatMessage chatMessage) {
        Log.e(TAG, "processOnetoOneMessage()");
        JSONObject ntfy_Data;
        String ntfy_Type;
        boolean isDefaultConnect = false;
        boolean isGroupInvite = false;
        boolean isCaseroomInvite = false;
        try {
            if (chatMessage.getProperty("c_ntfy_data") != null) {
                ntfy_Data = new JSONObject(chatMessage.getProperty("c_ntfy_data").toString());
                ntfy_Type = ntfy_Data.optString(RestUtils.TAG_TYPE);
                isDefaultConnect = ntfy_Type.equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name());
                isGroupInvite = ntfy_Type.equalsIgnoreCase(NotificationType.GROUP_CHAT_INVITE.name());
                isCaseroomInvite = ntfy_Type.equalsIgnoreCase(NotificationType.CASEROOM_INVITE.name());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (chatMessage.getDateSent() > 0)
            chatMessage.setDateSent(chatMessage.getDateSent());
        else
            chatMessage.setDateSent(Long.parseLong(chatMessage.getProperties().get("date_sent")));

        if (Foreground.get().isBackground()) {
            generateNotification(chatMessage, "private");
        }

        if (qb_hidden_dialog_id.equalsIgnoreCase(chatMessage.getDialogId())) { //Message is from hidden dialog handle message into notifications
            Log.e(TAG, "upDateNotification() - from processOnetoOneMessage ");
            if (SplashScreenActivity.bundle == null && chatMessage.getProperty("c_ntfy_data") != null) {
                Bundle bundle = new Bundle();
                bundle.putString("message", chatMessage.getBody());
                bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
                SplashScreenActivity.bundle = bundle;
            } else if (SplashScreenActivity.bundle != null && SplashScreenActivity.bundle.getString("message") == null) {
                SplashScreenActivity.bundle.putString("message", chatMessage.getBody());
                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
            } else if (SplashScreenActivity.bundle != null && chatMessage.getBody().contains(SplashScreenActivity.bundle.getString("message"))) {
                SplashScreenActivity.bundle.putString("message", chatMessage.getBody());
                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, false);
            } else if (SplashScreenActivity.bundle != null && (isGroupInvite || isCaseroomInvite)) {
                SplashScreenActivity.bundle.putString("message", chatMessage.getBody());
                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
            }
            upDateNotification(chatMessage, "loadFromChatMessage");
            saveMessageToCache(chatMessage, "", "", "", "incoming", 9, 0);
            try {
                QBChatDialog chatDialog = getChatDialogByDailogId(dialogId);
                if (chatDialog != null) {
                    chatDialog.readMessage(chatMessage);
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        } else {
            if (chatMessage.getAttachments().size() != 0)
                for (QBAttachment attachment : chatMessage.getAttachments()) {
                    try {

                        File f = createAndGetFile(attachment);
                        if (Foreground.get().isForeground() && ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(chatMessage.getDialogId())) {
                            //Save message att when in chat window
                            ChatActivity.getInstance().savePrivateAttToCachefromchat(chatMessage, f.getPath(), attachment.getType(), attachment.getId(), "incoming", 0);
                            if (chatMessage.isMarkable() && Foreground.get().isForeground()) {
                                QBChatDialog chatDialog = getChatDialogByDailogId(dialogId);
                                if (chatDialog != null) {
                                    chatDialog.readMessage(chatMessage);
                                }
                            }
                        } else {
                            //Save message att when out side of chat window
                            saveMessageToCache(chatMessage, f.getPath(), attachment.getType(), attachment.getId(), "incoming", 0, 0);
                            updateDashboardDialogCount();
                        }
                    } catch (XMPPException e) {
                        Log.w("QBLogin", e);
                    } catch (SmackException.NotConnectedException e) {
                        Log.w("QBLogin", e);
                    }
                }
            else {
                try {
                    if (Foreground.get().isForeground() && ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(chatMessage.getDialogId())) {
                        //Save message when in chat window
                        ChatActivity.getInstance().savePrivateMessageToCachefromchat(chatMessage, "incoming", 0);
                        if (chatMessage.isMarkable()) {
                            QBChatDialog chatDialog = getChatDialogByDailogId(dialogId);
                            if (chatDialog != null) {
                                chatDialog.readMessage(chatMessage);
                            } else {
                                QBRestChatService.getChatDialogById(dialogId).performAsync(new QBEntityCallback<QBChatDialog>() {
                                    @Override
                                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                                        dailogsHash.put(qbChatDialog.getDialogId(), qbChatDialog);
                                        qbChatDialog.readMessage(chatMessage, new QBEntityCallback() {
                                            @Override
                                            public void onSuccess(Object o, Bundle bundle) {

                                            }

                                            @Override
                                            public void onError(QBResponseException e) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(QBResponseException e) {

                                    }
                                });
                            }
                        }
                    } else {
                        //Save message when out side of chat window
                        saveMessageToCache(chatMessage, "", "", "", "incoming", 9, 0);
                        if (!qb_hidden_dialog_id.equals(chatMessage.getDialogId())) {
                            updateDashboardDialogCount();
                        } else {
                            QBChatDialog chatDialog = getChatDialogByDailogId(dialogId);
                            if (chatDialog != null) {
                                chatDialog.readMessage(chatMessage);
                            }
                        }
                    }
                } catch (XMPPException e) {
                    Log.w("QBLogin", e);
                } catch (SmackException.NotConnectedException e) {
                    Log.w("QBLogin", e);
                }
            }
        }
    }*/

    /*private static void processGroupMessage(QBChatMessage chatMessage, QBChatDialog chatDialog, boolean isCaseRoom) {
        Log.i(TAG, "processGroupMessage()");
        *//*JSONObject ntfy_Data;
        String ntfy_Type;
        boolean isDefaultConnect=false;
        try {
            if(chatMessage.getProperty("c_ntfy_data")!=null) {
                ntfy_Data = new JSONObject(chatMessage.getProperty("c_ntfy_data").toString());
                ntfy_Type = ntfy_Data.optString(RestUtils.TAG_TYPE);
                isDefaultConnect = ntfy_Type.equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (chatMessage.getProperty("c_msg_type") != null && Integer.parseInt(chatMessage.getProperties().get("c_msg_type")) == 2) {
        }else{
            if (SplashScreenActivity.bundle == null && chatMessage.getProperty("c_ntfy_data") != null) {
                Bundle bundle = new Bundle();
                bundle.putString("message", chatMessage.getBody());
                bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
                SplashScreenActivity.bundle = bundle;
            } else if (SplashScreenActivity.bundle != null && SplashScreenActivity.bundle.getString("message") == null) {
                SplashScreenActivity.bundle.putString("message", chatMessage.getBody());
                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, true);
            } else if (SplashScreenActivity.bundle != null && chatMessage.getBody().contains(SplashScreenActivity.bundle.getString("message"))) {
                SplashScreenActivity.bundle.putString("message", chatMessage.getBody());
                SplashScreenActivity.bundle.putBoolean(RestUtils.IS_HOME_LAUNCH, false);
            }
        }*//*
        SplashScreenActivity.bundle = null;

        if (chatMessage.getDateSent() > 0)
            chatMessage.setDateSent(chatMessage.getDateSent());
        else
            chatMessage.setDateSent(Long.parseLong(chatMessage.getProperties().get("date_sent")));
        if (Foreground.get().isBackground()) {
            if (!isCaseRoom) {
                generateNotification(chatMessage, "groupchat");
            } else {
                generateNotification(chatMessage, "caseroom");
            }
        }
        if (chatMessage.getAttachments().size() != 0)
            for (QBAttachment attachment : chatMessage.getAttachments()) {
                File f = createAndGetFile(attachment);
                try {
                    if (Foreground.get().isForeground() && ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(chatMessage.getDialogId())) {
                        //Save message att when in chat window
                        ChatActivity.getInstance().savePrivateAttToCachefromchat(chatMessage, f.getPath(), attachment.getType(), attachment.getId(), "incoming", 0);
                        if (chatMessage.isMarkable()) {
                            if (chatDialog != null) {
                                chatDialog.readMessage(chatMessage);
                            }
                        }
                    } else {
                        //Save message att when out side of chat window
                        saveMessageToCache(chatMessage, f.getPath(), attachment.getType(), attachment.getId(), "incoming", 0, 0);
                        updateDashboardDialogCount();
                    }
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
                break;
            }
        else {
            try {
                if (Foreground.get().isForeground() && ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(chatMessage.getDialogId())) {
                    //Save message when in chat window
                    ChatActivity.getInstance().savePrivateMessageToCachefromchat(chatMessage, "incoming", msg_type);
                    if (chatMessage.isMarkable()) {
                        if (chatDialog != null) {
                            chatDialog.readMessage(chatMessage);
                        }
                    }
                } else {
                    //Save message when out side of chat window
                    saveMessageToCache(chatMessage, "", "", "", "incoming", 9, msg_type);
                    updateDashboardDialogCount();
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*private static void generateNotification(final QBChatMessage chatMessage, final String type) {
        Log.i(TAG, "generateNotification(final QBChatMessage chatMessage, final String type)");
        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Realm realm = Realm.getDefaultInstance();
                final RealmManager realmManager = new RealmManager(context);
                login_doc_id = realmManager.getDoc_id(realm);
                emailId = realmManager.getDoc_EmailId(realm);
                Intent intent = null;
                try {

                    final String messageDialogId = chatMessage.getDialogId();
                    final App_Application app_application = App_Application.getInstance();
                    final int numUnreadMessages = app_application.getNumUnreadMessages();

                    if (messageDialogId.equals(qb_hidden_dialog_id)) {
                        try {
                            final JSONObject ntfy_Data = new JSONObject(chatMessage.getProperty("c_ntfy_data").toString());
                            final String ntfy_Type = ntfy_Data.getString(RestUtils.TAG_TYPE);
                            if (ntfy_Type.equalsIgnoreCase(NotificationType.CHANNEL_POST.name())) {

                                final JSONObject tempObj = ntfy_Data.optJSONObject(RestUtils.TAG_POST_DATA);
                                try {
                                    final JSONObject jsonObject = new JSONObject();
                                    jsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                                    jsonObject.put(RestUtils.CHANNEL_ID, tempObj.optInt(RestUtils.CHANNEL_ID));
                                    jsonObject.put(RestUtils.FEED_ID, tempObj.optInt(RestUtils.TAG_POST_ID));
                                    *//*
                                     * Generate notification
                                     *//*
                                    intent = new Intent(App_Application.getCurrentActivity(), EmptyActivity.class);
                                    intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
                                    generateNotificationBadge(app_application, numUnreadMessages, intent, type, chatMessage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (ntfy_Type.equalsIgnoreCase(NotificationType.CHANNEL_CONTENT_ARTICLE.name())) {
                                final JSONObject respJsonObj = ntfy_Data.optJSONObject(RestUtils.FEED_DATA);
                                try {
                                    final JSONObject reqObject = new JSONObject();
                                    reqObject.put(RestUtils.TAG_DOC_ID, doctorId);
                                    reqObject.put(RestUtils.CHANNEL_ID, respJsonObj.optInt(RestUtils.CHANNEL_ID));
                                    reqObject.put(RestUtils.FEED_ID, respJsonObj.optInt(RestUtils.TAG_FEED_ID));
                                    *//*
                                     * Generate notification
                                     *//*
                                    intent = new Intent(App_Application.getCurrentActivity(), EmptyActivity.class);
                                    intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, reqObject.toString());
                                    generateNotificationBadge(app_application, numUnreadMessages, intent, type, chatMessage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (ntfy_Type.equalsIgnoreCase(RestUtils.TAG_USER_REJECTED) || ntfy_Type.equalsIgnoreCase(RestUtils.TAG_USER_VERIFIED)) {
                                intent = new Intent(context, DashboardActivity.class);
                            } else {
                                intent = new Intent(context, NotificationsActivity.class);
                            }

                            if (ntfy_Type.equals(NotificationType.INVITE_USER_FOR_CONNECT.name()) || ntfy_Type.equals(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name())) {
                                intent.putExtra("TAB_NUMBER", 1);
                            } else if (ntfy_Type.equals(NotificationType.GROUP_CHAT_INVITE.name())) {
                                intent.putExtra("TAB_NUMBER", 1);
                            } else if (ntfy_Type.equals(NotificationType.CASEROOM_INVITE.name())) {
                                intent.putExtra("TAB_NUMBER", 1);
                            } else if (ntfy_Type.equals(NotificationType.DEFAULT_USER_CONNECT.name())) {
                                intent = null;

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        final QBChatDialog dialog = new QBChatDialog();
                        QBDialogModel qBDialogModel = new QBDialogModel();
                        if (numUnreadMessages >= 0) {
                            dialog.setDialogId("0");
                            boolean setIntentNow = true;
                            try {
                                qBDialogModel = realmManager.getCRQBDialogs(realm, messageDialogId);
                                if (qBDialogModel != null) {
                                    dialog.setDialogId(qBDialogModel.getDialog_id());
                                    dialog.setType(QBDialogType.parseByCode(Integer.parseInt(qBDialogModel.getDialog_type())));
                                    ArrayList<Integer> occ_ids = new ArrayList<Integer>();
                                    for (String ids : qBDialogModel.getOccupants_ids().split(",")) {
                                        occ_ids.add(Integer.parseInt(ids));
                                    }
                                    dialog.setOccupantsIds(occ_ids);
                                    dialog.setRoomJid(qBDialogModel.getDialog_roomjid());
                                } else { //If dialog is not available in Realm fetch dialog from QB if network is available
                                    QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
                                    //                            requestBuilder.setLimit(10);
                                    //                            requestBuilder.eq("_id ",Integer.parseInt(messageDialogId));
                                    requestBuilder.addRule("_id", QueryRule.EQ, messageDialogId);
                                    //                            dialog.setDialogId(messageDialogId);
                                    setIntentNow = false;
                                    QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                        @Override
                                        public void onSuccess(ArrayList<QBChatDialog> dialogs, Bundle mBundle) {
                                            //                                    int totalEntries = args.getInt("total_entries");
                                            Log.v("QBLogin", "Recieved message for the dialog " + dialogs.size());
                                            if (dialogs.size() > 0) {
                                                realmManager.insertQBDialog(realm, dialogs.get(0));
                                                if (ChatActivity.getInstance() != null && !ChatActivity.getDialogId().equals(dialog.getDialogId())) {
                                                    ChatActivity.getInstance().finish();//finish();
                                                }
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("group", type);
                                                bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialogs.get(0));

                                                if (type.equalsIgnoreCase("groupchat")) {
                                                    bundle.putSerializable(ChatActivity.EXTRA_MODE, ChatActivity.Mode.GROUP);
                                                } else if (type.equalsIgnoreCase("caseroom")) {
                                                    bundle.putSerializable(ChatActivity.EXTRA_MODE, ChatActivity.Mode.CASE_ROOM);
                                                } else {
                                                    bundle.putSerializable(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                                                }

                                                Intent intent = new Intent(context, ChatActivity.class);
                                                intent.putExtras(bundle);
                                                generateNotificationBadge(app_application, numUnreadMessages, intent, type, chatMessage);
                                            }
                                        }

                                        @Override
                                        public void onError(QBResponseException errors) {
                                            Log.v("QBLogin", "Recieved message for the dialog Error " + errors.getLocalizedMessage());
                                            mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                            Intent intent = new Intent(context, DashboardActivity.class);
                                            intent.putExtra("from", "notification");
                                            generateNotificationBadge(app_application, numUnreadMessages, intent, type, chatMessage);
                                            AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d("ChatActivity", "" + ChatActivity.getDialogId());
                            Log.d("dialog.getDialogId()", "" + dialog.getDialogId());
                            if (!dialog.getDialogId().equals("0")) {
                                if (ChatActivity.getInstance() != null && !ChatActivity.getDialogId().equals(dialog.getDialogId())) {
                                    ChatActivity.getInstance().finish();//finish();
                                }
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("group", type);
                                bundle.putSerializable(ChatActivity.EXTRA_DIALOG, dialog);
                                if (type.equals("groupchat"))
                                    bundle.putSerializable(ChatActivity.EXTRA_MODE, ChatActivity.Mode.GROUP);
                                else
                                    bundle.putSerializable(ChatActivity.EXTRA_MODE, ChatActivity.Mode.PRIVATE);
                                intent = new Intent(context, ChatActivity.class);
                                intent.putExtras(bundle);
                            } else if (setIntentNow) {
                                mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                                intent = new Intent(context, DashboardActivity.class);
                                intent.putExtra("tabid", 0);
                            }
                        } else {
                            mySharedPref.savePref(mySharedPref.STAY_LOGGED_IN, mySharedPref.getPref(MySharedPref.PREF_REMEMBER_ME, false));
                            intent = new Intent(context, DashboardActivity.class);
                            intent.putExtra("from", "notification");
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("typeOfMessages", type);

                            intent.putExtras(bundle);
                            Log.v("QBLogin", "Setting notification type " + type);
                        }
                    }
                    if (intent != null) {
                        generateNotificationBadge(app_application, numUnreadMessages, intent, type, chatMessage);
                        intent = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (!realm.isClosed())
                        realm.close();
                }

            }
        });
    }*/

    /*private static void generateNotificationBadge(App_Application app_application, int numUnreadMessages, Intent intent, String type, final QBChatMessage chatMessage) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmManager realmManager = new RealmManager(context);
        try {
            app_application.setNumUnreadMessages(++numUnreadMessages);
            int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
            String CHANNEL_ID = "NotificationChannel";
            //FLAG_ONE_SHOT,FLAG_CANCEL_CURRENT;
//       PendingIntent pendingIntent = PendingIntent.getActivity(context, iUniqueId, intent, numUnreadMessages == 2 ? PendingIntent.FLAG_CANCEL_CURRENT : PendingIntent.FLAG_ONE_SHOT);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, iUniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            String sendername = realmManager.getContactnameByQBID(realm, chatMessage.getSenderId());

            if (type.equals("groupchat") || type.equals("caseroom")) {
                String groupname = realmManager.getGroupName(realm, chatMessage.getDialogId());
                sendername = sendername + " @\" " + groupname + " \" : ";
            } else if (!sendername.equals("")) {
                sendername = sendername + " : ";
            }
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("WhiteCoats")
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri).setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(sendername + chatMessage.getBody()))
                    .setContentText(sendername + chatMessage.getBody());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setColor(context.getResources().getColor(R.color.app_green));
                notificationBuilder.setSmallIcon(R.drawable.ic_notification);
            } else {
                notificationBuilder.setSmallIcon(R.drawable.ic_appicon);
            }
            notificationManager.notify((int) chatMessage.getDateSent(), notificationBuilder.build());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }*/

    /*private static void upDateNotification(QBChatMessage chatMessage, String flowFrom) {
        Log.e(TAG, "upDateNotification()");
        networkNotificationCount=mySharedPref.getPref("networkNotificationCount",0);
        final Realm realm = Realm.getDefaultInstance();
        final RealmManager realmManager = new RealmManager(context);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        try {
            String messageBody = chatMessage.getBody();
            Log.d(TAG, "Message Body : " + messageBody);
            JSONObject ntfy_Data = new JSONObject(chatMessage.getProperty("c_ntfy_data").toString());
            Log.d(TAG, "Notify Data : " + ntfy_Data);
            Log.d(TAG, "Splash Bundle : " + SplashScreenActivity.bundle);
            String ntfyId = ntfy_Data.optString(RestUtils.TAG_NOTIFICATION_ID);
            final String ntfy_Type = ntfy_Data.optString(RestUtils.TAG_TYPE);
            if (SplashScreenActivity.bundle != null && messageBody.contains(SplashScreenActivity.bundle.getString("message"))) {
                if (ntfy_Type.equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
                    final ContactsInfo notify_contactsInfo = new ContactsInfo();
                    notify_contactsInfo.setNotification_type(ntfy_Type);
                    notify_contactsInfo.setNotification_id(ntfyId);
                    JSONObject fromdocjObject = ntfy_Data.getJSONObject(RestUtils.TAG_FROM_DOC);
                    notify_contactsInfo.setDoc_id(Integer.valueOf(fromdocjObject.getString(RestUtils.TAG_DOC_ID)));
                    notify_contactsInfo.setName((fromdocjObject.has(RestUtils.TAG_USER_FULL_NAME)) ? fromdocjObject.getString(RestUtils.TAG_USER_FULL_NAME) : fromdocjObject.getString(RestUtils.TAG_FULL_NAME));
                    notify_contactsInfo.setSpeciality(fromdocjObject.optString(RestUtils.TAG_SPLTY));
                    notify_contactsInfo.setSubSpeciality(fromdocjObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
                    notify_contactsInfo.setLocation(fromdocjObject.getString(RestUtils.TAG_LOCATION));
                    notify_contactsInfo.setPic_data(fromdocjObject.getString(RestUtils.TAG_PROFILE_PIC_NAME));
                    notify_contactsInfo.setPic_name(fromdocjObject.getString(RestUtils.TAG_PROFILE_PIC_NAME));
                    notify_contactsInfo.setPic_url(fromdocjObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                    notify_contactsInfo.setWorkplace(fromdocjObject.getString(RestUtils.TAG_WORKPLACE));
                    notify_contactsInfo.setEmail(fromdocjObject.getString(RestUtils.TAG_CNT_EMAIL));
                    notify_contactsInfo.setPhno(fromdocjObject.getString(RestUtils.TAG_CNT_NUM));
                    notify_contactsInfo.setQb_userid(Integer.parseInt(fromdocjObject.getString(RestUtils.TAG_QB_USER_ID)));
                    notify_contactsInfo.setTime(ntfy_Data.getLong(RestUtils.TAG_TIME_RECEIVED));
                    notify_contactsInfo.setUserSalutation(fromdocjObject.optString(RestUtils.TAG_USER_SALUTAION));
                    notify_contactsInfo.setUserTypeId(fromdocjObject.optInt(RestUtils.TAG_USER_TYPE_ID));
                    JSONObject todocjObject = ntfy_Data.getJSONObject(RestUtils.TAG_TO_DOC);
                    notify_contactsInfo.setMessage(todocjObject.getString(RestUtils.TAG_INVITE_TEXT));
                    App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Realm mRealm = Realm.getDefaultInstance();
                            RealmManager mRealmManager = new RealmManager(context);
                            //mRealmManager.insertNotification(mRealm, notify_contactsInfo);
                            //insert notify connect
                            mRealmManager.insertNotifiInfoInMigration(mRealm, ntfyId, ntfy_Data.toString(), true, false,0);
                            if (!mRealm.isClosed())
                                mRealm.close();
                            networkNotificationCount++;

                        }
                    });
                } else if (ntfy_Type.equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name()) || ntfy_Type.equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name())) {

                    final ContactsInfo notify_contactsInfo = new ContactsInfo();
                    notify_contactsInfo.setNotification_type(ntfy_Type);
                    notify_contactsInfo.setNotification_id(ntfyId);
                    JSONObject todocjObject = ntfy_Data.optJSONObject(RestUtils.TAG_FROM_DOC);
                    notify_contactsInfo.setDoc_id(Integer.parseInt(todocjObject.optString(RestUtils.TAG_DOC_ID)));
                    notify_contactsInfo.setName((todocjObject.has(RestUtils.TAG_USER_FULL_NAME)) ? todocjObject.optString(RestUtils.TAG_USER_FULL_NAME) : todocjObject.optString(RestUtils.TAG_FULL_NAME));
                    notify_contactsInfo.setSpeciality(todocjObject.optString(RestUtils.TAG_SPLTY));
                    notify_contactsInfo.setSubSpeciality(todocjObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
                    notify_contactsInfo.setLocation(todocjObject.optString(RestUtils.TAG_LOCATION));
                    notify_contactsInfo.setPic_data(todocjObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                    notify_contactsInfo.setPic_name(todocjObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                    notify_contactsInfo.setPic_url(todocjObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                    notify_contactsInfo.setPic_url(todocjObject.optString(RestUtils.TAG_PROFILE_PIC_URL));
                    notify_contactsInfo.setWorkplace(todocjObject.optString(RestUtils.TAG_WORKPLACE));
                    notify_contactsInfo.setEmail(todocjObject.optString(RestUtils.TAG_CNT_EMAIL));
                    notify_contactsInfo.setPhno(todocjObject.optString(RestUtils.TAG_CNT_NUM));
                    notify_contactsInfo.setQb_userid(Integer.parseInt(todocjObject.optString(RestUtils.TAG_QB_USER_ID)));
                    notify_contactsInfo.setTime(ntfy_Data.optLong(RestUtils.TAG_TIME_RECEIVED));
                    notify_contactsInfo.setUserSalutation(todocjObject.optString(RestUtils.TAG_USER_SALUTAION));
                    notify_contactsInfo.setUserTypeId(todocjObject.optInt(RestUtils.TAG_USER_TYPE_ID));
                    if (ntfy_Type.equals(NotificationType.DEFAULT_USER_CONNECT.name())) {
                        notify_contactsInfo.setMessage(ntfy_Data.optString(RestUtils.TAG_NOTIFICATION_MESSAGE));
                    } else {
                        notify_contactsInfo.setMessage(RestUtils.TAG_ACCEPTED_FRD_REQUEST);
                    }
                    final int doc_id = Integer.parseInt(todocjObject.optString(RestUtils.TAG_DOC_ID));
                    App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Realm mRealm = Realm.getDefaultInstance();
                                RealmManager mRealmManager = new RealmManager(context);
                                //mRealmManager.insertNotification(mRealm, notify_contactsInfo);
                                //insert notify connect response
                                mRealmManager.insertNotifiInfoInMigration(mRealm, ntfyId, ntfy_Data.toString(), true, false,0);
                                mRealmManager.insertMyContacts(mRealm, notify_contactsInfo);
                                mRealmManager.updateMyContactsStatus(mRealm, doc_id, 3);
                                networkNotificationCount++;
                            *//*if (ntfy_Type.equals(NotificationType.DEFAULT_USER_CONNECT.name())) {
                                mRealmManager.insertMyContacts(mRealm, notify_contactsInfo);
                                mRealmManager.updateMyContactsStatus(mRealm, doc_id, 3);
                            } else {
                                mRealmManager.updateMyContactsStatus(mRealm, doc_id, 3);
                            }*//*
                                if (ntfy_Type.equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name())) {
                                    updateMyConnects();
                                }
                                int qb_userid = notify_contactsInfo.getQb_userid();
                                QBChatDialog dialog = DialogUtils.buildPrivateDialog(qb_userid);
                                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                                    @Override
                                    public void onSuccess(QBChatDialog result, Bundle params) {
                                        Realm mRealmDb = Realm.getDefaultInstance();
                                        RealmManager mRealmMgr = new RealmManager(context);
                                        mRealmMgr.insertQBDialog(mRealmDb, result);
                                        if (!mRealmDb.isClosed())
                                            mRealmDb.close();
                                    }

                                    @Override
                                    public void onError(QBResponseException errors) {
                                        Log.v("Notification Service", "" + errors.toString());
                                        AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
                                    }
                                });

                                try {
                                    QBRoster chatRoster = QBChatService.getInstance().getRoster();
                                    if (chatRoster.contains(qb_userid))
                                        chatRoster.subscribe(qb_userid);
                                    else
                                        chatRoster.createEntry(qb_userid, null);
                                } catch (XMPPException e) {
                                    e.printStackTrace();
                                } catch (SmackException.NotLoggedInException e) {
                                    e.printStackTrace();
                                } catch (SmackException.NotConnectedException e) {
                                    e.printStackTrace();
                                } catch (SmackException.NoResponseException e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("", "");
                            }
                        }
                    });

                } else if (ntfy_Data.getString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.GROUP_CHAT_INVITE.name())) {

                    final GroupNotifyInfo groupNotifyInfo = new GroupNotifyInfo();
                    groupNotifyInfo.setGroup_notification_type(ntfy_Data.optString(RestUtils.TAG_TYPE));
                    groupNotifyInfo.setGroup_notify_id(ntfyId);
                    groupNotifyInfo.setGroup_notification_time(ntfy_Data.optLong(RestUtils.TAG_TIME_RECEIVED));
                    final JSONObject inviter_infojObject = ntfy_Data.optJSONObject(RestUtils.TAG_INVITER_INFO);
                    groupNotifyInfo.setGroup_admin_Doc_id(Integer.parseInt(inviter_infojObject.optString(RestUtils.TAG_DOC_ID)));
                    groupNotifyInfo.setGroup_admin_name((inviter_infojObject.has(RestUtils.TAG_USER_FULL_NAME)) ? inviter_infojObject.optString(RestUtils.TAG_USER_FULL_NAME) : inviter_infojObject.optString(RestUtils.TAG_FULL_NAME));
                    groupNotifyInfo.setGroup_admin_email(inviter_infojObject.optString(RestUtils.TAG_CNT_EMAIL));
                    groupNotifyInfo.setGroup_admin_phno(inviter_infojObject.optString(RestUtils.TAG_CNT_NUM));
                    groupNotifyInfo.setGroup_admin_specialty(inviter_infojObject.optString(RestUtils.TAG_SPLTY));
                    groupNotifyInfo.setGroup_admin_sub_specialty(inviter_infojObject.optString(RestUtils.TAG_SUB_SPLTY));
                    groupNotifyInfo.setGroup_admin_location(inviter_infojObject.optString(RestUtils.TAG_LOCATION));
                    groupNotifyInfo.setGroup_admin_workplace(inviter_infojObject.optString(RestUtils.TAG_WORKPLACE));
                    groupNotifyInfo.setGroup_admin_qb_user_id(inviter_infojObject.optString(RestUtils.TAG_QB_USER_ID));
                    groupNotifyInfo.setGroup_admin_pic_url(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                    groupNotifyInfo.setUser_salutation(inviter_infojObject.optString(RestUtils.TAG_USER_SALUTAION));
                    groupNotifyInfo.setUser_type_id(inviter_infojObject.optInt(RestUtils.TAG_USER_TYPE_ID));
                    if (inviter_infojObject.getString(RestUtils.TAG_PROFILE_PIC_NAME).equals("null")) {
                        groupNotifyInfo.setGroup_admin_pic(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                    } else {
                        groupNotifyInfo.setGroup_admin_pic(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                        groupNotifyInfo.setGroup_admin_pic_url(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                        final int doc_id = Integer.parseInt(inviter_infojObject.optString(RestUtils.TAG_DOC_ID));
                        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Realm mRealm = Realm.getDefaultInstance();
                                RealmManager mRealmManager = new RealmManager(context);
                                mRealmManager.insertGroupAdminPicName(mRealm, doc_id, groupNotifyInfo.getGroup_admin_pic(), groupNotifyInfo.getGroup_admin_pic_url());
                                if (!mRealm.isClosed())
                                    mRealm.close();
                            }
                        });
                    }
                    JSONObject group_infojObject = ntfy_Data.optJSONObject(RestUtils.TAG_GROUP_INFO);
                    if (group_infojObject.getLong(RestUtils.TAG_GROUP_CREATION_TIME) != 0) {
                        groupNotifyInfo.setGroup_creation_time(group_infojObject.optLong(RestUtils.TAG_GROUP_CREATION_TIME));
                    }
                    groupNotifyInfo.setRoom_jid(group_infojObject.optString(RestUtils.TAG_XMPP_ROOM_JID));
                    groupNotifyInfo.setGroup_name(group_infojObject.optString(RestUtils.TAG_GROUP_TITLE));
                    groupNotifyInfo.setGroup_pic(group_infojObject.optString(RestUtils.TAG_GROUP_PROFILE_IMG_NAME));
                    groupNotifyInfo.setGroup_pic_url(group_infojObject.optString(RestUtils.TAG_LOG_SMALL_URL));
                    groupNotifyInfo.setGroup_id(group_infojObject.optString(RestUtils.TAG_GROUP_ID));

                    *//** Inserting group info **//*
                    final QBDialogModel dialogModel = new QBDialogModel();
                    dialogModel.setDialog_id(groupNotifyInfo.getGroup_id());
                    dialogModel.setDialog_title(groupNotifyInfo.getGroup_name());
                    dialogModel.setDialog_pic_name(groupNotifyInfo.getGroup_pic());
                    dialogModel.setDialog_pic_url(groupNotifyInfo.getGroup_pic_url());
                    dialogModel.setOpponent_doc_id(0);
                    dialogModel.setDialog_creation_date(groupNotifyInfo.getGroup_creation_time());
                    dialogModel.setDialog_roomjid(groupNotifyInfo.getRoom_jid());
                    dialogModel.setLocal_dialog_type("groupchat");
                    App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Realm mRealm = Realm.getDefaultInstance();
                            RealmManager mRealmManager = new RealmManager(context);
                            //mRealmManager.insertGroupNotification(mRealm, groupNotifyInfo);
                            //insert notify single
                            mRealmManager.insertNotifiInfoInMigration(mRealm, ntfyId, ntfy_Data.toString(), true, false,0);
                            //mRealmManager.insertNotifiInfoInMigration(mRealm,);

                            mRealmManager.insertGroupDialog(mRealm, dialogModel);
                            if (!mRealm.isClosed())
                                mRealm.close();
                            networkNotificationCount++;
                        }
                    });
                } else if (ntfy_Data.getString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.CASEROOM_INVITE.name())) {

                    final CaseroomNotifyInfo caseroomNotifyInfo = new CaseroomNotifyInfo();
                    caseroomNotifyInfo.setCaseroom_notify_type(ntfy_Data.optString(RestUtils.TAG_TYPE));
                    caseroomNotifyInfo.setCaseroom_notify_id(ntfyId);
                    caseroomNotifyInfo.setTime_received(ntfy_Data.optLong(RestUtils.TAG_TIME_RECEIVED));
                    JSONObject cr_infojObject = ntfy_Data.optJSONObject(RestUtils.TAG_CASE_ROOM_INVITE_INFO);
                    caseroomNotifyInfo.setCase_heading(cr_infojObject.optString(RestUtils.TAG_CASE_ROOM_TITLE));
                    caseroomNotifyInfo.setCaseroom_summary_id(cr_infojObject.optString(RestUtils.TAG_CASE_SUMMARY_ID));
                    caseroomNotifyInfo.setCaseroom_id(cr_infojObject.optString(RestUtils.TAG_CASE_ROOM_ID));
                    caseroomNotifyInfo.setCaseroom_group_xmpp_jid(cr_infojObject.optString(RestUtils.TAG_XMPP_ROOM_JID));
                    caseroomNotifyInfo.setCase_speciality(cr_infojObject.optString(RestUtils.TAG_CASE_ROOM_SPLTY));
                    caseroomNotifyInfo.setCaseroom_group_created_date(cr_infojObject.optLong(RestUtils.TAG_CASE_ROOM_CREATED_DATE));
                    JSONObject inviter_infojObject = ntfy_Data.getJSONObject(RestUtils.TAG_INVITER_INFO);
                    caseroomNotifyInfo.setDoc_id(inviter_infojObject.optInt(RestUtils.TAG_DOC_ID));
                    caseroomNotifyInfo.setDoc_qb_user_id(inviter_infojObject.optString(RestUtils.TAG_QB_USER_ID));
                    caseroomNotifyInfo.setDoc_name((inviter_infojObject.has(RestUtils.TAG_USER_FULL_NAME)) ? inviter_infojObject.optString(RestUtils.TAG_USER_FULL_NAME) : inviter_infojObject.optString(RestUtils.TAG_FULL_NAME));
                    caseroomNotifyInfo.setDoc_speciality(inviter_infojObject.optString(RestUtils.TAG_SPLTY));
                    caseroomNotifyInfo.setDoc_sub_speciality(inviter_infojObject.optString(RestUtils.TAG_SUB_SPLTY));
                    caseroomNotifyInfo.setDoc_workplace(inviter_infojObject.optString(RestUtils.TAG_WORKPLACE));
                    caseroomNotifyInfo.setDoc_location(inviter_infojObject.optString(RestUtils.TAG_LOCATION));
                    caseroomNotifyInfo.setDoc_cnt_email(inviter_infojObject.optString(RestUtils.TAG_CNT_EMAIL));
                    caseroomNotifyInfo.setDoc_cnt_num(inviter_infojObject.optString(RestUtils.TAG_CNT_NUM));
                    caseroomNotifyInfo.setDoc_pic_name(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                    caseroomNotifyInfo.setDoc_pic_url(inviter_infojObject.optString(RestUtils.TAG_PROFILE_PIC_URL));
                    caseroomNotifyInfo.setUser_salutation(inviter_infojObject.optString(RestUtils.TAG_USER_SALUTAION));
                    caseroomNotifyInfo.setUser_type_id(inviter_infojObject.optInt(RestUtils.TAG_USER_TYPE_ID));

                    *//** Inserting group info **//*
                    final QBDialogModel dialogModel = new QBDialogModel();
                    dialogModel.setDialog_id(caseroomNotifyInfo.getCaseroom_id());
                    dialogModel.setDialog_title(caseroomNotifyInfo.getCase_heading());
                    dialogModel.setDialog_pic_name("");
                    dialogModel.setOpponent_doc_id(0);
                    dialogModel.setDialog_creation_date(caseroomNotifyInfo.getCaseroom_group_created_date());
                    dialogModel.setDialog_roomjid(caseroomNotifyInfo.getCaseroom_group_xmpp_jid());
                    dialogModel.setLocal_dialog_type("caseroom");
                    App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Realm mRealm = Realm.getDefaultInstance();
                            RealmManager mRealmManager = new RealmManager(context);
                            mRealmManager.insertCaseRoomNotification(mRealm, caseroomNotifyInfo);
                            //insert notify caseroom single
                            mRealmManager.insertNotifiInfoInMigration(mRealm, ntfyId, ntfy_Data.toString(), true, false,0);
                            mRealmManager.insertGroupDialog(mRealm, dialogModel);
                            if (!mRealm.isClosed())
                                mRealm.close();

                            networkNotificationCount++;
                        }
                    });
                } else if (ntfy_Data.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.CHANNEL_POST.name()) && flowFrom.equalsIgnoreCase("loadFromChatMessage")) {

                    JSONObject postObj = ntfy_Data.optJSONObject(RestUtils.TAG_POST_DATA);
//                post_obj.put("channel_id", postObj.optInt("channel_id"));
                    JSONObject socialInteraction = new JSONObject();
                    socialInteraction.put(RestUtils.TAG_IS_LIKE_ENABLED, true);
                    socialInteraction.put(RestUtils.TAG_IS_COMMENT_ENABLED, true);
                    socialInteraction.put(RestUtils.TAG_IS_SHARE_ENABLED, true);
                    socialInteraction.put(RestUtils.TAG_IS_LIKE, false);
                    socialInteraction.put(RestUtils.TAG_LIKES_COUNT, 0);
                    socialInteraction.put(RestUtils.TAG_COMMENTS_COUNT, 0);
                    socialInteraction.put("shareCount", 0);
                    postObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteraction);

                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        listener.notifyUIWithNewData(postObj);
                    }
                    // If app is in background
                    if (Foreground.get().isBackground()) {
                        generateNotification(chatMessage, NotificationType.CHANNEL_POST.name());
                    }
                    *//*
                     * If user navigates through push notification then
                     *  . the value for Bundle exists
                     *  . RestUtils.IS_HOME_LAUNCH  is false;
                     *  . Navigate to Full view
                     *
                     * otherwise if user navigates on tap of app icon
                     *   . bundle is null.
                     *   . RestUtils.IS_HOME_LAUNCH  is true;
                     *   . Don't Navigate to Full view
                     *
                     *//*

                    FlurryAgent.logEvent(TAG + "updateNotification()" + ": Time-" + DateUtils.getCurrentTime() + ",Splash Bundle - " + SplashScreenActivity.bundle + ",IsAppBackground - " + Foreground.get().isBackground());
                    if (SplashScreenActivity.bundle != null && !SplashScreenActivity.bundle.getBoolean(RestUtils.IS_HOME_LAUNCH, false)) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                            jsonObject.put(RestUtils.CHANNEL_ID, postObj.optInt(RestUtils.CHANNEL_ID));
                            jsonObject.put(RestUtils.FEED_ID, postObj.optInt(RestUtils.TAG_POST_ID));
                            *//*
                             * Forward to Empty activity
                             *//*
                            Intent intent = new Intent(App_Application.getCurrentActivity(), EmptyActivity.class);
                            intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
                            App_Application.getCurrentActivity().startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else if (ntfy_Data.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.CHANNEL_CONTENT_ARTICLE.name()) && flowFrom.equalsIgnoreCase("loadFromChatMessage")) {
                    JSONObject contentJsonObj = ntfy_Data.optJSONObject(RestUtils.FEED_DATA);
                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        listener.notifyUIWithNewData(contentJsonObj);
                    }
                    if (Foreground.get().isBackground()) {
                        generateNotification(chatMessage, NotificationType.CHANNEL_CONTENT_ARTICLE.name());
                    }
                    if (SplashScreenActivity.bundle != null && !SplashScreenActivity.bundle.getBoolean(RestUtils.IS_HOME_LAUNCH, false)) {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                            jsonObject.put(RestUtils.CHANNEL_ID, contentJsonObj.optInt(RestUtils.CHANNEL_ID));
                            jsonObject.put(RestUtils.FEED_ID, contentJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            *//*
                             * Forward to Empty activity
                             *//*
                            Intent intent = new Intent(App_Application.getCurrentActivity(), EmptyActivity.class);
                            intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
                            App_Application.getCurrentActivity().startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (ntfy_Data.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(RestUtils.TAG_USER_VERIFIED) && flowFrom.equalsIgnoreCase("loadFromChatMessage")) {
                    final String msg = ntfy_Data.optString("notification_message");
                    mySharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, ntfy_Data.optInt("is_user_verified", 3));
                    AppUtil.logUserVerificationInfoEvent(mySharedPref.getPref(MySharedPref.PREF_IS_USER_VERIFIED));
                    if (App_Application.getCurrentActivity() != null) {
                        App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(App_Application.getCurrentActivity());
                                builder.setMessage(msg);
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

                    if (Foreground.get().isBackground()) {
                        generateNotification(chatMessage, RestUtils.TAG_USER_VERIFIED);
                    }
                } else if (ntfy_Data.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(RestUtils.TAG_USER_REJECTED) && flowFrom.equalsIgnoreCase("loadFromChatMessage")) {
                    final String msg = ntfy_Data.optString("notification_message");
                    //AppConstants.IS_USER_VERIFIED_CONSTANT = ntfy_Data.optInt("is_user_verified");
                    mySharedPref.savePref(MySharedPref.PREF_IS_USER_VERIFIED, ntfy_Data.optInt("is_user_verified", 1));

                    if (Foreground.get().isBackground()) {
                        generateNotification(chatMessage, RestUtils.TAG_USER_REJECTED);
                    } else {
                        if (SplashScreenActivity.bundle != null && !SplashScreenActivity.bundle.getBoolean(RestUtils.IS_HOME_LAUNCH, false)) {
                            if (App_Application.getCurrentActivity() != null) {
                                Intent rejectIntent = new Intent(App_Application.getCurrentActivity(), MCACardUploadActivity.class);
                                App_Application.getCurrentActivity().startActivity(rejectIntent);
                                App_Application.getCurrentActivity().finish();
                            }
                        } else {
                            if (App_Application.getCurrentActivity() != null) {
                                App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AppUtil.AccessErrorPrompt(App_Application.getCurrentActivity(), msg);
                                    }
                                });
                            }
                        }
                    }
                }
                if (SplashScreenActivity.bundle != null) {
                    SplashScreenActivity.bundle = null;
                }
            }
            *//*else if (SplashScreenActivity.bundle != null) {
                SplashScreenActivity.bundle = null;
            }*//*

            mySharedPref.savePref("networkNotificationCount",networkNotificationCount);

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("processMessage", "Exception " + e);

        } finally {
            if (!realm.isClosed())
                realm.close();
        }
        new BroadCastAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }*/

    /*public static QBIsTypingListener<QBPrivateChat> privateChatIsTypingListener = new QBIsTypingListener<QBPrivateChat>() {
        @Override
        public void processUserIsTyping(QBPrivateChat privateChat, Integer userId) {
            Log.d("privateTypingListener", "processUserIsTyping");
            if (ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(privateChat.getDialogId()))
                notifyMessageTyping(true, privateChat.getDialogId(), userId);
        }

        @Override
        public void processUserStopTyping(QBPrivateChat privateChat, Integer userId) {
            Log.d("privateTypingListener", "processUserStopTyping");
            if (ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(privateChat.getDialogId()))
                notifyMessageTyping(false, privateChat.getDialogId(), userId);
        }
    };
    public static QBIsTypingListener<QBGroupChat> groupChatIsTypingListener = new QBIsTypingListener<QBGroupChat>() {
        @Override
        public void processUserIsTyping(QBGroupChat privateChat, Integer userId) {
            Log.d("groupTypingListener", "processUserIsTyping");
            if (ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(privateChat.getDialogId()))
                notifyMessageTyping(true, privateChat.getDialogId(), userId);
        }

        @Override
        public void processUserStopTyping(QBGroupChat privateChat, Integer userId) {
            Log.d("groupTypingListener", "processUserStopTyping");
            if (ChatActivity.getInstance() != null && ChatActivity.getDialogId().equals(privateChat.getDialogId()))
                notifyMessageTyping(false, privateChat.getDialogId(), userId);
        }
    };*/

    protected static void notifyMessageTyping(boolean isTyping, String dialogId, Integer userId) {
        Intent intent = new Intent("typing_message");
        intent.putExtra("isTyping", isTyping);
        intent.putExtra("dialogId", dialogId);
        intent.putExtra("userId", userId);
        LocalBroadcastManager.getInstance(App_Application.getCurrentActivity()).sendBroadcast(intent);
    }

    protected static void updateDialogs() {
        Intent intent = new Intent("update_dialogs");
        LocalBroadcastManager.getInstance(App_Application.getCurrentActivity()).sendBroadcast(intent);
    }

    protected static void updateCaseDialogs() {
        Intent intent = new Intent("update_case_dialogs");
        LocalBroadcastManager.getInstance(App_Application.getCurrentActivity()).sendBroadcast(intent);
    }

    protected static void updateDialogsCount() {
        Intent intent = new Intent("update_dialogs_count");
        LocalBroadcastManager.getInstance(App_Application.getCurrentActivity()).sendBroadcast(intent);
    }

    protected static void updateMyConnects() {
        Intent intent = new Intent("update_my_connects");
        LocalBroadcastManager.getInstance(App_Application.getCurrentActivity()).sendBroadcast(intent);
    }

    public static void updateDashboardDialogCount() {
        if (App_Application.getCurrentActivity() instanceof DashboardActivity) {
            updateDialogs();
        }
        updateDialogsCount();
    }

    /*private static File createAndGetFile(QBAttachment attachment) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        Date now = new Date();
        String fileName = "";
        if (attachment.getType().equals("image"))
            fileName = "att_" + formatter.format(now) + ".jpg";
        else
            fileName = "att_" + formatter.format(now) + ".mp4";
        return new File(FileHelper.createAttFilesFolder(), fileName);
    }*/

    /*public static void saveMessageToCache(final QBChatMessage chatMessage, String path, String type, String aat_qb_id, String messagetype, int att_state, final int msg_type) {
        Log.i(TAG, "saveMessageToCache()");
        final Realm realm = Realm.getDefaultInstance();
        final RealmManager realmManager = new RealmManager(context);
        try {
            final MessageModel messageModel = new MessageModel();
            messageModel.setId(chatMessage.getId());
            messageModel.setMessage(chatMessage.getBody());
            messageModel.setDialogId(chatMessage.getDialogId());
            if (messagetype.equals("incoming")) {
                if (chatMessage.getSenderId() != null) {
                    messageModel.setSenderId(chatMessage.getSenderId());
                }
            } else {
                messageModel.setSenderId(realmManager.getQB_user_id(realm));
            }
            messageModel.setMessage_satus((2));
            messageModel.setSync(true);
            messageModel.setTime(chatMessage.getDateSent());
            if (msg_type != 0) {
                messageModel.setMsg_type(msg_type);
            } else {
                messageModel.setMsg_type(1);
            }
            messageModel.setAtt_qbid(aat_qb_id);
            messageModel.setAtt_status(att_state);
            messageModel.setAttachUrl(path);
            messageModel.setAtt_type(type);
            App_Application.getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Realm realmInstance = Realm.getDefaultInstance();
                    final RealmManager mRealmMgr = new RealmManager(context);
                    try {
                        mRealmMgr.insertMessage(realmInstance, messageModel);
                        boolean foundDialog = mRealmMgr.updateUnreadDialogCount(realmInstance, messageModel);
                        if (!foundDialog) {

                            QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
//                            requestBuilder.setLimit(10);
//                            requestBuilder.eq("_id ",Integer.parseInt(messageDialogId));
                            requestBuilder.addRule("_id", QueryRule.EQ, messageModel.getDialogId());
//                            dialog.setDialogId(messageDialogId);

                            QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
                                @Override
                                public void onSuccess(ArrayList<QBChatDialog> dialogs, Bundle bundle) {
                                    Realm realmDb = Realm.getDefaultInstance();
                                    RealmManager realmMgr = new RealmManager(context);
                                    Log.v("QBLogin", "Recieved message for the dialog " + dialogs.size());
                                    if (dialogs.size() > 0) {
                                        dailogsHash.put(dialogs.get(0).getDialogId(), dialogs.get(0));
                                        realmMgr.insertQBDialog(realmDb, dialogs.get(0));
                                        updateDialogs();
                                        updateDashboardDialogCount();
                                    }
                                    if (!realmDb.isClosed())
                                        realmDb.close();
                                }

                                @Override
                                public void onError(QBResponseException errors) {
                                    Log.v("QBLogin", "Recieved message for the dialog Error " + errors.getLocalizedMessage());
                                    AppUtil.logFlurryEventWithDocIdAndEmailEvent("QBLoginExcep", login_doc_id + "", emailId);
                                }
                            });
                        }
                        if (msg_type == 2) {
                            mRealmMgr.updateCrStatus(realmInstance, messageModel.getDialogId(), messageModel.getMessage());
                        }
                    } finally {
                        if (!realmInstance.isClosed())
                            realmInstance.close();
                    }

                }
            });
        } catch (RealmException re) {
            Log.d("RealmException", " c" + re);
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }*/


    /*static QBSubscriptionListener subscriptionListener = new QBSubscriptionListener() {
        @Override
        public void subscriptionRequested(int userId) {
            try {
                QBRoster chatRoster = QBChatService.getInstance().getRoster();
                chatRoster.confirmSubscription(userId);
            } catch (SmackException.NotConnectedException e) {
                Log.d("QBLogin Exception", "NotConnectedException");


            } catch (SmackException.NotLoggedInException e) {
                Log.d("QBLogin Exception", "NotLoggedInException");


            } catch (XMPPException e) {
                Log.d("QBLogin Exception", "XMPPException");


            } catch (SmackException.NoResponseException e) {
                Log.d("QBLogin Exception", "NoResponseException");


            }
        }
    };

    static QBRosterListener rosterListener = new QBRosterListener() {
        @Override
        public void entriesDeleted(Collection<Integer> userIds) {
            Log.d("QBLogin", "entriesDeleted");
        }

        @Override
        public void entriesAdded(Collection<Integer> userIds) {
            Log.d("QBLogin", "entriesAdded");

        }

        @Override
        public void entriesUpdated(Collection<Integer> userIds) {
            Log.d("QBLogin", "entriesUpdated");

        }

        @Override
        public void presenceChanged(QBPresence presence) {
           *//* final Calendar c = Calendar.getInstance();
            c.getTime();*//*
            Log.d("QBLogin", "presenceChanged" + presence.getStatus());
            *//*if(presence.getStatus().equals("online")){
                Log.d("QBLogin","presence changed to online");
            }else if(presence.getStatus().equals("offline")){
                Log.d("QBLogin","presence changed to offline");
            }*//*
        }
    };

    public static void customParameterDataInsertion(JSONObject jsonObject, QBChatMessage chatMessage) {
        final Realm realm = Realm.getDefaultInstance();
        final RealmManager realmManager = new RealmManager(context);
        try {
            msg_type = jsonObject.getInt("c_msg_type");
            String cardstring = jsonObject.getString("c_ntfy_data");
            JSONObject cardjsonobject = new JSONObject(cardstring);
            ContactsInfo contactsInfo = new ContactsInfo();
            contactsInfo.setDoc_id(cardjsonobject.optInt(RestUtils.TAG_DOC_ID));
            contactsInfo.setQb_userid(cardjsonobject.optInt("qb_user_id"));
            contactsInfo.setName((cardjsonobject.has(RestUtils.TAG_USER_FULL_NAME)) ? cardjsonobject.optString(RestUtils.TAG_USER_FULL_NAME) : cardjsonobject.optString(RestUtils.TAG_FULL_NAME));
            contactsInfo.setSpeciality(cardjsonobject.getString(RestUtils.TAG_SPLTY));
            contactsInfo.setSubSpeciality(cardjsonobject.optString(RestUtils.TAG_SUB_SPLTY, ""));
            contactsInfo.setLocation(cardjsonobject.optString(RestUtils.TAG_LOCATION));
            contactsInfo.setWorkplace(cardjsonobject.optString(RestUtils.TAG_WORKPLACE));
            contactsInfo.setDegree(cardjsonobject.optString(RestUtils.TAG_DEGREES));
            contactsInfo.setPic_name(cardjsonobject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
            contactsInfo.setPic_url(cardjsonobject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
            contactsInfo.setEmail(cardjsonobject.optString(RestUtils.TAG_CNT_EMAIL));
            contactsInfo.setPhno(cardjsonobject.optString(RestUtils.TAG_CNT_NUM));
            contactsInfo.setUserSalutation(cardjsonobject.optString(RestUtils.TAG_USER_SALUTAION));
            contactsInfo.setUserTypeId(cardjsonobject.optInt(RestUtils.TAG_USER_TYPE_ID));
            realmManager.insertMyContacts(realm, contactsInfo);
            QBDialogMemInfo qbDialogMemInfo = new QBDialogMemInfo();
            qbDialogMemInfo.setDialog_id(chatMessage.getDialogId());
            qbDialogMemInfo.setDoc_id(cardjsonobject.getInt(RestUtils.TAG_DOC_ID));
            qbDialogMemInfo.setInvite_response(1);
            realmManager.updateDialogMem(realm, qbDialogMemInfo);

            realmManager.updateOccIds(realm, contactsInfo.getQb_userid(), chatMessage.getDialogId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }

    public static void markallmessagesAsRead(final String dialogId) {
        QBRestChatService.markMessagesAsRead(dialogId, null).performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.v("onSuccess", "markMessagesAsRead" + dialogId);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.v("onError", "markMessagesAsRead" + e.getMessage());
                AppUtil.logFlurryEventWithDocIdAndEmailEvent("markMessagesAsRead", login_doc_id + "", emailId);
            }
        });
    }


    public static QBChatDialog getChatDialogByDailogId(String mDailogId) {
        return dailogsHash.get(mDailogId);
    }


    private void joinGroupDialogs(int skipCount) {
        QBRequestGetBuilder customObjectRequestBuilder = new QBRequestGetBuilder();
//        customObjectRequestBuilder.eq("type",""+QBDialogType.GROUP);
        //customObjectRequestBuilder.eq("_id",dialogID);
        customObjectRequestBuilder.setLimit(100);
        customObjectRequestBuilder.setSkip(skipCount);
        QBRestChatService.getChatDialogs(QBDialogType.GROUP, customObjectRequestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {
                for (QBChatDialog chatDialog : qbChatDialogs) {
                    DiscussionHistory history = new DiscussionHistory();
                    history.setMaxStanzas(0);
                    chatDialog.join(history, new QBEntityCallback() {
                        @Override
                        public void onSuccess(Object o, Bundle bundle) {
                            Log.v("QBLogin", "Group join success : " + chatDialog.getDialogId());
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Log.v("QBLogin", "Group join failed : " + chatDialog.getDialogId());
                        }
                    });
                }
                *//*if(qbChatDialogs.size() > 0) {
                    try {
                        DiscussionHistory history = new DiscussionHistory();
                        history.setMaxStanzas(0);
                        qbChatDialogs.get(0).join(history, new QBEntityCallback() {
                            @Override
                            public void onSuccess(Object o, Bundle bundle) {
                                Log.v("QBLogin","Group joined");
                            }

                            @Override
                            public void onError(QBResponseException e) {
                                Log.v("QBLogin","Group failed");
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*//*

                if (bundle.getInt("total_entries") > (bundle.getInt("skip") + bundle.getInt("limit"))) {
                    joinGroupDialogs(bundle.getInt("skip") + bundle.getInt("limit"));
                    return;
                }
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });

    }*/
}
