package com.vam.whitecoats.async;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

/*import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.result.HttpStatus;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBSubscription;*/
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.LoginActivity;
import com.vam.whitecoats.ui.activities.QBLogin;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by swathim on 31-08-2015.
 */
public class SingletonAsync extends AsyncTask<String, String, String> implements OnTaskCompleted {


    private Context mContext;
    private String className;
    private Realm realm;
    private RealmManager realmManager;
    private ProgressDialogFragement progress;
    private String response;
    private OnTaskCompleted listener;
    private String url;
    protected AlertDialog.Builder builder;

    /**
     * QuickBlox Login
     **/
    private QBLogin qbLogin;
    private HttpClient client;


    public SingletonAsync(Context context, String url) {
        mContext = context;
        this.url = url;
        listener = (OnTaskCompleted) context;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);

    }

    public SingletonAsync(Context context, String url, OnTaskCompleted onTaskCompleted) {
        mContext = context;
        this.url = url;
        listener = onTaskCompleted;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
    }

    @Override
    protected void onPreExecute() {
        className = mContext.getClass().getName();
        if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
            showProgress();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            client = new HttpClient(mContext,url);
            if (className.equals(RestUtils.LOGIN_ACTIVITY) || className.equals(RestUtils.CHANGE_PASSWORD_ACTIVITY)) {
                client.connectForMultipartCookie(mContext);
            } else {
                client.connectForSinglepart(mContext);
            }
            String reqData = params[0].toString();
            Log.d(className + " [ reqData ] ", reqData);
            if (className.equals(RestUtils.LOGIN_ACTIVITY) || className.equals(RestUtils.CHANGE_PASSWORD_ACTIVITY)) {
                client.addFormPart(RestUtils.TAG_REQ_DATA, reqData);
                client.finishMultipart();
                response = client.getLoginJSONResponse();
            } else {
                client.addpara(RestUtils.TAG_REQ_DATA, reqData);
                response = client.getResponse();
            }

            Log.d("Singleton Async", "response" + response);
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
        try {
            realm = Realm.getDefaultInstance();
            realmManager = new RealmManager(mContext);
            if (response != null) {
                if (response.isEmpty()) {
                    if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
                        hideProgress();
                    }
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                } else if (response.equals("SocketTimeoutException") || response.toLowerCase().equals("exception")) {
                    Log.d("Login Exception", response);
                    if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
                        hideProgress();
                    }
                    String error = "Network Error,pls try after some time.";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            error = jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (listener != null) {
                        listener.onTaskCompleted(response);
                    } else {
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                    }
                } /*else if (mContext.getClass().getName().equals(RestUtils.CHANGE_PASSWORD_ACTIVITY)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            JSONObject data = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            String qb_user_id = data.getString(RestUtils.TAG_QB_USER_ID);
                            String qb_user_login = data.getString(RestUtils.TAG_QB_USER_LOGIN);
                            boolean is_user_active = data.optBoolean("is_user_active", false);
                            *//***saving in realm database ***//*
                            String qb_password = MySharedPref.getPrefsHelper().getPref(MySharedPref.PREF_USER_PASSWORD).toString();
                            String qb_hidden_dialog_id = data.getString(RestUtils.TAG_USER_DIALOG_ID);
                            MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_MANDATORY_PROFILE_CHECK, false);
                            realmManager.updateQBBasicLogin(realm, qb_user_id, qb_user_login, qb_password, qb_hidden_dialog_id);
    //                            new ChangePasswordAckAsync(mContext).execute();
                            qbLogin = new QBLogin(mContext, qb_user_login, qb_password, "", "ChangePassword",is_user_active);
                            //hideProgress();
                        } else {
                            hideProgress();
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                showSessionExpireAlert("Error", "Your session has timed out. Please login again");
                            } else
                                Toast.makeText(mContext,"Unknown Server Error",Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }
                } */
                else {
                    if (!this.url.equalsIgnoreCase(RestApiConstants.UPDATE_PASSWORD)) {
                        if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
                            hideProgress();
                        }
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                showSimpleDialog("Error",mContext.getResources().getString(R.string.session_timedout));
                            } else if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                listener.onTaskCompleted(response);
                            } else {
                                if (this.url.equalsIgnoreCase(RestApiConstants.ACK_MANDATORY)) {
                                    listener.onTaskCompleted(response);
                                } else {
                                    if (jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE, "").isEmpty()) {
                                        Toast.makeText(mContext,mContext.getResources().getString(R.string.unknown_server_error), Toast.LENGTH_SHORT).show();
                                        //showSessionExpireAlert("Error", "Unknown Server Error", false);
                                    } else {
                                        Toast.makeText(mContext, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                                        //showSessionExpireAlert("Error", jsonObject.optString("errorMsg"), false);
                                    }
                                }

                            }
                        } else {
                            listener.onTaskCompleted(response);
                        }
                    } catch (Exception e) {
                        if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
                            hideProgress();
                        }
                        /*if(client!=null &&(client.getStatusCode()== HttpStatus.SC_BAD_GATEWAY || client.getStatusCode()==HttpStatus.SC_SERVICE_UNAVAILABLE)){
                            JSONObject errorObj=new JSONObject();
                            try {
                                errorObj.put(RestUtils.TAG_STATUS,RestUtils.TAG_ERROR);
                                errorObj.put(RestUtils.TAG_ERROR_CODE,client.getStatusCode());
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                            listener.onTaskCompleted(errorObj.toString());
                        }*/
                        e.printStackTrace();
                    }
                }
            } else {
                if (!className.equalsIgnoreCase(RestUtils.MYGCM_LISTENER_SERVICE)) {
                    hideProgress();
                }
                Toast.makeText(mContext, mContext.getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!realm.isClosed())
                realm.close();
        }
    }

    public synchronized void showProgress() {
        try {
            if (!progress.isAdded()) {
                progress.show(App_Application.getCurrentActivity().getFragmentManager(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void hideProgress() {
        if (progress != null && progress.getActivity() != null) {
            progress.dismissAllowingStateLoss();
        }
    }

    public void showSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(mContext);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    /*QBPushNotifications.getSubscriptions().performAsync(new QBEntityCallback<ArrayList<QBSubscription>>() {
                        @Override
                        public void onSuccess(ArrayList<QBSubscription> qbSubscriptions, Bundle bundle) {
                            String deviceId = AppUtil.getDeviceID(mContext);
                            *//**
                             * Get device Id
                             *//*
                            for (QBSubscription subscription : qbSubscriptions) {
                                if (subscription.getDevice().getId().equals(deviceId)) {
                                    *//*
                                     * Delete Subscription
                                     *//*
                                    QBPushNotifications.deleteSubscription(subscription.getId()).performAsync(new QBEntityCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid, Bundle bundle) {
                                           AppUtil.signOutFromQB();
                                        }

                                        @Override
                                        public void onError(QBResponseException e) {
                                            e.printStackTrace();
                                            *//*
                                             * Re-try Signing out
                                             *//*
                                            AppUtil.signOutFromQB();
                                        }
                                    });

                                    break;
                                }
                            }


                        }

                        @Override
                        public void onError(QBResponseException e) {
                            e.printStackTrace();
                            *//*
                             * Re-try signing out
                             *//*
                            AppUtil.signOutFromQB();
                        }
                    });*/
                    //AppUtil.signOutFromQB();
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.PREF_SESSION_TOKEN, "");
                    MySharedPref.getPrefsHelper().savePref(MySharedPref.STAY_LOGGED_IN, false);
                    Intent i = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(String s) {
        hideProgress();
    }
}
