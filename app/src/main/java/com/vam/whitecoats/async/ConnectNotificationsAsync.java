package com.vam.whitecoats.async;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CaseRoomAttachmentsInfo;
import com.vam.whitecoats.core.models.CaseRoomInfo;
import com.vam.whitecoats.core.models.CaseRoomPatientDetailsInfo;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.GroupNotifyInfo;
import com.vam.whitecoats.core.quickblox.qbmodels.QBDialogMemInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.parser.HttpClient;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.NotificationsActivity;
import com.vam.whitecoats.ui.activities.VisitProfileActivity;
import com.vam.whitecoats.ui.dialogs.ProgressDialogFragement;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import io.realm.Realm;

/**
 * Created by swathim on 26-08-2015.
 */
public class ConnectNotificationsAsync extends AsyncTask<String, String, String> {

    private Context mContext;
    private String response;
    private String url;
    private OnTaskCompleted listener;
    protected ProgressDialogFragement progress;
    private NotificationsActivity notificationsActivity;
    private VisitProfileActivity visitProfileActivity;
    private DashboardActivity dashboardActivity;
  //  private CaseRoomSummary caseRoomSummary;
    private Realm realm;
    private RealmManager realmManager;
    private AlertDialog.Builder builder;
    private ContactsInfo notificationsInfo, ContactInfo;
    private GroupNotifyInfo groupNotifyInfo;
    private CaseroomNotifyInfo caseroomNotifyInfo;
    private String check_tag;
    int skipMessages = 0;
    private long member_added_date;

    public ConnectNotificationsAsync(Context mContext, String url, ContactsInfo notificationsInfo, OnTaskCompleted listener) {
        this.mContext = mContext;
        this.url = url;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        this.notificationsInfo = notificationsInfo;
        this.listener = (OnTaskCompleted) mContext;
        this.listener = listener;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);

        if (mContext instanceof NotificationsActivity) {
            notificationsActivity = (NotificationsActivity) mContext;
        } else if (mContext instanceof DashboardActivity) {
            dashboardActivity = (DashboardActivity) mContext;
        } else if (mContext instanceof VisitProfileActivity) {
            visitProfileActivity = (VisitProfileActivity) mContext;
        } /*else if (mContext instanceof CaseRoomSummary) {
            caseRoomSummary = (CaseRoomSummary) mContext;
        }*/
    }
    public ConnectNotificationsAsync(Context mContext, String url, ContactsInfo notificationsInfo) {
        this.mContext = mContext;
        this.url = url;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        this.notificationsInfo = notificationsInfo;
        this.listener = (OnTaskCompleted) mContext;
        this.listener = listener;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);

        if (mContext instanceof NotificationsActivity) {
            notificationsActivity = (NotificationsActivity) mContext;
        } else if (mContext instanceof DashboardActivity) {
            dashboardActivity = (DashboardActivity) mContext;
        } else if (mContext instanceof VisitProfileActivity) {
            visitProfileActivity = (VisitProfileActivity) mContext;
        } /*else if (mContext instanceof CaseRoomSummary) {
            caseRoomSummary = (CaseRoomSummary) mContext;
        }*/
    }

    public ConnectNotificationsAsync(Context mContext, String url, GroupNotifyInfo groupNotifyInfo) {
        this.mContext = mContext;
        this.url = url;
        listener = (OnTaskCompleted) mContext;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        this.groupNotifyInfo = groupNotifyInfo;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);

        if (mContext instanceof NotificationsActivity) {
            notificationsActivity = (NotificationsActivity) mContext;
        } else if (mContext instanceof VisitProfileActivity) {
            visitProfileActivity = (VisitProfileActivity) mContext;
        } /*else if (mContext instanceof CaseRoomSummary) {
            caseRoomSummary = (CaseRoomSummary) mContext;
        }*/
    }

    public ConnectNotificationsAsync(Context mContext, String url, CaseroomNotifyInfo caseroomNotifyInfo) {
        this.mContext = mContext;
        this.url = url;
        listener = (OnTaskCompleted) mContext;
        progress = ProgressDialogFragement.newInstance(R.string.dlg_wait_please);
        this.caseroomNotifyInfo = caseroomNotifyInfo;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(mContext);

        if (mContext instanceof NotificationsActivity) {
            notificationsActivity = (NotificationsActivity) mContext;
        } else if (mContext instanceof VisitProfileActivity) {
            visitProfileActivity = (VisitProfileActivity) mContext;
        } /*else if (mContext instanceof CaseRoomSummary) {
            caseRoomSummary = (CaseRoomSummary) mContext;
        }*/
    }


    @Override
    protected void onPreExecute() {
        if (mContext instanceof NotificationsActivity) {

            notificationsActivity.showProgress();
        } else if (mContext instanceof DashboardActivity) {

            dashboardActivity = (DashboardActivity) mContext;
            dashboardActivity.showProgress();
        } else if (mContext instanceof VisitProfileActivity) {

            visitProfileActivity.showProgress();

        } /*else if (mContext instanceof CaseRoomSummary) {
            caseRoomSummary.showProgress();
        }*/

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpClient client = new HttpClient(mContext,url);
            client.connectForSinglepart(mContext);
            Log.d("", "reqData" + params[0].toString());
            client.addpara("reqData", params[0]);
            check_tag = params[1];
            response = client.getResponse();

            Log.d("ConnectInvite", "responce" + response);
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
    protected void onPostExecute(String s) {
        Log.d("auto response", "response" + s);
        listener.onTaskCompleted(response);
        if (response.equals("SocketTimeoutException") || response.equals("Exception")) {
            builder = new AlertDialog.Builder(mContext);
            builder.setMessage(mContext.getResources().getString(R.string.unabletosave) + "" + mContext.getResources().getString(R.string.trylater));
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            notificationsActivity.hideProgress();
            builder.create().show();

        } else if (mContext instanceof DashboardActivity) {
            try {
                JSONObject acceptjObject = new JSONObject(response);
                if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                    if (acceptjObject.has("resp_status") && acceptjObject.optString("resp_status").equalsIgnoreCase("accept")) {
                        Toast.makeText(mContext, "Connect request accepted", Toast.LENGTH_LONG).show();
                        realmManager.insertMyContacts(realm, notificationsInfo, 3);
                    }
                    realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                    //realmManager.deleteNetworkNotifications(realm, notificationsInfo.getNotification_id(), "notification");
                    dashboardActivity.hideProgress();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && (acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("108") || acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("114"))) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (realmManager.checkNotificationExists(realm, notificationsInfo.getNotification_id())) {
                                //realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                            } /*else {
                                //realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                            }*/
                        }
                    });
                    dashboardActivity.hideProgress();
                    builder.create().show();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                    dashboardActivity.hideProgress();
                    dashboardActivity.ShowServerErrorSimpleDialog("Error", mContext.getResources().getString(R.string.session_timedout));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                    dashboardActivity.hideProgress();
                    AppUtil.AccessErrorPrompt(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                    dashboardActivity.hideProgress();
                    Toast.makeText(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                /** firing Broadcast to update notification count **/
                Intent intent = new Intent("NotificationCount");
                LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
                dashboardActivity.hideProgress();
            }

        } else if (mContext instanceof NotificationsActivity && check_tag.equals("connects_notification")) {
            try {
                JSONObject acceptjObject = new JSONObject(response);
                if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                    if (acceptjObject.has("resp_status") && acceptjObject.optString("resp_status").equalsIgnoreCase("accept")) {
                        Toast.makeText(mContext, "Connect request accepted", Toast.LENGTH_LONG).show();
                        notificationsInfo.setNetworkStatus("3");
                        realmManager.insertMyContacts(realm, notificationsInfo);
                    }
                    //realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                    realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                    notificationsActivity.hideProgress();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && (acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("108") || acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("114"))) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (realmManager.checkNotificationExists(realm, notificationsInfo.getNotification_id())) {
//                                realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                            } /*else {
//                                realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                realmManager.deleteNetworkNotification(realm, notificationsInfo.getNotification_id());
                            }*/
                            listener.onTaskCompleted("");
                            Intent intent = new Intent("NotificationCount");
                            LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
                        }
                    });
                    notificationsActivity.hideProgress();
                    builder.create().show();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                    notificationsActivity.hideProgress();
                    notificationsActivity.ShowServerErrorSimpleDialog("Error", mContext.getResources().getString(R.string.session_timedout));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                    notificationsActivity.hideProgress();
                    AppUtil.AccessErrorPrompt(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                    notificationsActivity.hideProgress();
                    Toast.makeText(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                /** firing Broadcast to update notification count **/
                Intent intent = new Intent("NotificationCount");
                LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
                notificationsActivity.hideProgress();
            }

        } else if (mContext instanceof NotificationsActivity && check_tag.equals("group_notifications")) {
            try {
                JSONObject acceptjObject = new JSONObject(response);
                if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) || (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("112"))) {
                    //realmManager.deleteGroupNotification(realm, groupNotifyInfo.getGroup_notify_id().toString());
                    realmManager.deleteNetworkNotification(realm, groupNotifyInfo.getGroup_notify_id());
                    /** firing Broadcast to update notification count **/
                    Intent intent = new Intent("NotificationCount");
                    LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);

                    JSONObject jObject = acceptjObject.getJSONObject(RestUtils.TAG_DATA);
                    String group_id = jObject.optString(RestUtils.TAG_GROUP_ID);
                    member_added_date = jObject.optLong(RestUtils.TAG_MEMBER_ADDED_DATE);
                    final String dialogsRoomJId = jObject.optString(RestUtils.TAG_XMPP_ROOM_JID);
                    JSONArray jsonArray = jObject.getJSONArray(RestUtils.TAG_MEM_INFO);
                    saveGroupMembers(jsonArray, member_added_date, group_id);


                    if (realmManager.checkDialoginDB(realm, group_id) == 0) {
                        /*if (QbAuthUtils.isSessionActive()) {
                            connectQBForGroup(member_added_date, dialogsRoomJId);
                        } else {
                            QBLogin qbLogin = new QBLogin(mContext, "", new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    if (s.equalsIgnoreCase(ConstsCore.QB_LOGIN_SUCCESS)) {
                                        connectQBForGroup(member_added_date, dialogsRoomJId);
                                    } else {
                                        Toast.makeText(mContext, mContext.getResources().getString(R.string.exception_creating_qb), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }*/
                    }
                    notificationsActivity.hideProgress();
                    if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("112")) {
                        builder = new AlertDialog.Builder(mContext);
                        builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                        builder.setPositiveButton("OK", null);
                        notificationsActivity.hideProgress();
                        builder.create().show();
                    } else {
                        Toast.makeText(mContext, "Group request accepted", Toast.LENGTH_LONG).show();
                    }
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("601")) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           /* if (realmManager.checkGroupNetworkNotificationExists(realm, groupNotifyInfo.getGroup_notify_id())) {
                                realmManager.deleteNetworkGroupNotification(realm, groupNotifyInfo.getGroup_notify_id());
                            }*/
                            dialog.dismiss();

                        }
                    });
                    notificationsActivity.hideProgress();
                    builder.create().show();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                    notificationsActivity.hideProgress();
                    notificationsActivity.ShowServerErrorSimpleDialog("Error", "Your session has timed out. Please login again");
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                    notificationsActivity.hideProgress();
                    AppUtil.AccessErrorPrompt(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                    notificationsActivity.hideProgress();
                    Toast.makeText(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                notificationsActivity.hideProgress();
                e.printStackTrace();
            }
        } else if (mContext instanceof NotificationsActivity && check_tag.equals("caseroom_notifications")) {
            Log.d("ConnectNotify", "CaseRoom Tab : %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            try {
                JSONObject acceptjObject = new JSONObject(response);
                if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS) || (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("112"))) {

                    Toast.makeText(mContext, "Caseroom request accepted", Toast.LENGTH_LONG).show();


                    //realmManager.deleteCaseRoomNotification(realm, caseroomNotifyInfo.getCaseroom_notify_id().toString());
                    realmManager.deleteNetworkNotification(realm, caseroomNotifyInfo.getCaseroom_notify_id());
                    /** firing Broadcast to update notification count **/
                    Intent intent = new Intent("NotificationCount");
                    LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);

                    JSONObject jObject = acceptjObject.getJSONObject(RestUtils.TAG_DATA);
                    String caseroom_id = jObject.optString(RestUtils.TAG_CASE_ROOM_ID);
                    final long member_added_date = jObject.getLong(RestUtils.TAG_MEMBER_ADDED_DATE);
                    final String dialogsRoomJId = jObject.optString(RestUtils.TAG_XMPP_ROOM_JID);
                    JSONArray jsonArray = jObject.getJSONArray(RestUtils.TAG_MEM_INFO);
                    try {
                        //if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);
                            int invite_response = jsonArrayJSONObject.getInt(RestUtils.TAG_INVITE_RESPONSE);
                            boolean is_admin = jsonArrayJSONObject.getBoolean(RestUtils.TAG_IS_ADMIN);
                            JSONObject cardjObject = jsonArrayJSONObject.getJSONObject(RestUtils.TAG_CARD_INFO);
                            ContactsInfo contactsInfo = new ContactsInfo();
                            contactsInfo.setDoc_id(Integer.parseInt(cardjObject.optString(RestUtils.TAG_DOC_ID)));
                            contactsInfo.setPic_name(cardjObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                            contactsInfo.setPic_url(cardjObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
                            contactsInfo.setName((cardjObject.has(RestUtils.TAG_USER_FULL_NAME)) ? cardjObject.optString(RestUtils.TAG_USER_FULL_NAME) : cardjObject.optString(RestUtils.TAG_FULL_NAME));
                            contactsInfo.setSpeciality(cardjObject.optString(RestUtils.TAG_SPLTY));
                            contactsInfo.setSubSpeciality(cardjObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
                            contactsInfo.setWorkplace(cardjObject.optString(RestUtils.TAG_WORKPLACE));
                            contactsInfo.setLocation(cardjObject.optString(RestUtils.TAG_LOCATION));
                            contactsInfo.setEmail(cardjObject.optString(RestUtils.TAG_CNT_EMAIL));
                            contactsInfo.setPhno(cardjObject.optString(RestUtils.TAG_CNT_NUM));
                            contactsInfo.setUserSalutation(cardjObject.optString(RestUtils.TAG_USER_SALUTAION));
                            contactsInfo.setUserTypeId(cardjObject.optInt(RestUtils.TAG_USER_TYPE_ID));
//                                contactsInfo.setNetworkStatus("" + 0);
                            contactsInfo.setQb_userid(Integer.parseInt(cardjObject.optString(RestUtils.TAG_QB_USER_ID)));
                            realmManager.updateMyContacts(realm, contactsInfo);
                              /*  final ArrayList<ContactsInfo> contactslist = realmManager.getMyContacts(realm);
                                for (int c = 0; c < contactslist.size(); c++) {
                                    if (contactslist.get(c).getDoc_id() != contactsInfo.getDoc_id()) {
                                        ContactsInfo contactInfo = new ContactsInfo();
                                        contactInfo.setDoc_id(caseroomNotifyInfo.getDoc_id());
                                        contactInfo.setPic_name(caseroomNotifyInfo.getDoc_pic_name());
                                        contactsInfo.setPic_url(caseroomNotifyInfo.getDoc_pic_url());
                                        contactInfo.setName(caseroomNotifyInfo.getDoc_name());
                                        contactInfo.setSpeciality(caseroomNotifyInfo.getDoc_speciality());
                                        contactInfo.setSubSpeciality(caseroomNotifyInfo.getDoc_sub_speciality());
                                        contactInfo.setWorkplace(caseroomNotifyInfo.getDoc_workplace());
                                        contactInfo.setLocation(caseroomNotifyInfo.getDoc_location());
                                        contactInfo.setEmail(caseroomNotifyInfo.getDoc_cnt_email());
                                        contactInfo.setPhno(caseroomNotifyInfo.getDoc_cnt_num());
                                        contactInfo.setQb_userid(Integer.parseInt(caseroomNotifyInfo.getDoc_qb_user_id()));
                                        contactsInfo.setUserSalutation(caseroomNotifyInfo.getUser_salutation());
                                        contactsInfo.setUserTypeId(caseroomNotifyInfo.getUser_type_id());
                                        realmManager.insertMyContacts(realm, contactInfo);
                                    } else {
                                        realmManager.updateMyContacts(realm, contactsInfo);
                                    }
                                }*/
                            QBDialogMemInfo dialogMemInfo = new QBDialogMemInfo();
                            dialogMemInfo.setDialog_id(caseroomNotifyInfo.getCaseroom_id().toString());
                            dialogMemInfo.setDoc_id(contactsInfo.getDoc_id());
                            dialogMemInfo.setInvite_response(invite_response);
                            dialogMemInfo.setIs_admin(is_admin);

                            /** updating in members info **/
                            realmManager.updateDialogMem(realm, dialogMemInfo);
                        }
                        /*} else {
                            final ArrayList<ContactsInfo> contactslist = realmManager.getMyContacts(realm);
                            for (int c = 0; c < contactslist.size(); c++) {
                                if (contactslist.get(c).getDoc_id() == caseroomNotifyInfo.getDoc_id()) {
                                    ContactsInfo contactInfo = new ContactsInfo();
                                    contactInfo.setDoc_id(caseroomNotifyInfo.getDoc_id());
                                    contactInfo.setPic_name(caseroomNotifyInfo.getDoc_pic_name());
                                    contactInfo.setPic_url(caseroomNotifyInfo.getDoc_pic_url());
                                    contactInfo.setName(caseroomNotifyInfo.getDoc_name());
                                    contactInfo.setSpeciality(caseroomNotifyInfo.getDoc_speciality());
                                    contactInfo.setSubSpeciality(caseroomNotifyInfo.getDoc_sub_speciality());
                                    contactInfo.setWorkplace(caseroomNotifyInfo.getDoc_workplace());
                                    contactInfo.setLocation(caseroomNotifyInfo.getDoc_location());
                                    contactInfo.setEmail(caseroomNotifyInfo.getDoc_cnt_email());
                                    contactInfo.setPhno(caseroomNotifyInfo.getDoc_cnt_num());
                                    contactInfo.setQb_userid(Integer.parseInt(caseroomNotifyInfo.getDoc_qb_user_id()));
                                    realmManager.updateMyContacts(realm, ContactInfo);
                                }
                            }

                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        /** Inserting admin in Group MemInfo **/
                        QBDialogMemInfo qbDialogMemInfo = new QBDialogMemInfo();
                        qbDialogMemInfo.setDoc_id(caseroomNotifyInfo.getDoc_id());
                        qbDialogMemInfo.setIs_admin(true);
                        qbDialogMemInfo.setInvite_response(1);
                        qbDialogMemInfo.setDialog_id(caseroomNotifyInfo.getCaseroom_id().toString());
                        realmManager.updateDialogMem(realm, qbDialogMemInfo);

                        realmManager.updateDialog_memaddedDate(realm, member_added_date, caseroom_id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        /** updating yourself when accept button clicked **/
                        QBDialogMemInfo yourdialogMemInfo = new QBDialogMemInfo();
                        yourdialogMemInfo.setDialog_id(caseroomNotifyInfo.getCaseroom_id().toString());
                        yourdialogMemInfo.setDoc_id(realmManager.getDoc_id(realm));
                        yourdialogMemInfo.setInvite_response(1);
                        realmManager.updateDialogMem(realm, yourdialogMemInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /** inserting caseroom summary details into caseroominfo table and patient details table **/
                    JSONObject caseroominfojson = jObject.getJSONObject(RestUtils.TAG_CASE_ROOM_INFO);
                    JSONObject caseroom = caseroominfojson.getJSONObject(RestUtils.TAG_CASE_ROOM);
                    try {
                        CaseRoomInfo caseRoomInfo = new CaseRoomInfo();
                        caseRoomInfo.setCaseroom_summary_id(caseroom.optString(RestUtils.TAG_CASE_SUMMARY_ID));
                        caseRoomInfo.setCaseroom_dialog_id(caseroom_id);
                        caseRoomInfo.setTitle(caseroom.optString(RestUtils.TAG_TITLE));
                        caseRoomInfo.setSpeciality(caseroom.optString(RestUtils.TAG_SPLTY));
                        caseRoomInfo.setSub_speciality(caseroom.optString(RestUtils.TAG_SUB_SPLTY, ""));
                        caseRoomInfo.setDocid(caseroom.getInt(RestUtils.TAG_DOC_ID));
                        caseRoomInfo.setStatus(3);
                        caseRoomInfo.setQuery(caseroom.optString(RestUtils.QUERY));
                        caseRoomInfo.setAttachments(caseroom.optString(RestUtils.ATTACHMENTS));
                        caseRoomInfo.setCreateddate(0);
                        caseRoomInfo.setModifieddate(0);
                        caseRoomInfo.setLast_message("");
                        caseRoomInfo.setLast_message_time(new Date().getTime());
                        //realmManager.insertCaseRoomInfo(realm, caseRoomInfo);

                        String patientdetails = caseroominfojson.optString(RestUtils.PATIENT_DETAILS);
                        if (!patientdetails.equals("null")) {
                            caseRoomInfo.setP_details(true);
                            realmManager.insertCaseRoomInfo(realm, caseRoomInfo);
                            JSONObject patientdetailsjson = new JSONObject(patientdetails);
                            CaseRoomPatientDetailsInfo caseRoomPatientDetailsInfo = new CaseRoomPatientDetailsInfo();
                            caseRoomPatientDetailsInfo.setCaseroom_summary_id(caseroom.optString(RestUtils.TAG_CASE_SUMMARY_ID));
                            caseRoomPatientDetailsInfo.setPatage(patientdetailsjson.optString(RestUtils.AGE));
                            caseRoomPatientDetailsInfo.setPatgender(patientdetailsjson.optString(RestUtils.GENDER));
                            caseRoomPatientDetailsInfo.setSymptoms(patientdetailsjson.optString(RestUtils.SYMPTOMS));
                            caseRoomPatientDetailsInfo.setHistory(patientdetailsjson.optString(RestUtils.PATIENT_HISTORY).equalsIgnoreCase("{}") ? "" : patientdetailsjson.optString(RestUtils.PATIENT_HISTORY));
                            caseRoomPatientDetailsInfo.setVitals_anthropometry(patientdetailsjson.optString(RestUtils.VITALS_ANTROPOMETRY).equalsIgnoreCase("{}") ? "" : patientdetailsjson.optString(RestUtils.VITALS_ANTROPOMETRY));
                            caseRoomPatientDetailsInfo.setGeneral_examination(patientdetailsjson.optString(RestUtils.GENERAL_EXAMINATION).equalsIgnoreCase("{}") ? "" : patientdetailsjson.optString(RestUtils.GENERAL_EXAMINATION));
                            caseRoomPatientDetailsInfo.setSystemic_examination(patientdetailsjson.optString(RestUtils.SYSTEMIC_EXAMINATION).equalsIgnoreCase("{}") ? "" : patientdetailsjson.optString(RestUtils.SYSTEMIC_EXAMINATION));
                            realmManager.insertCaseRoomPatientDetailsInfo(realm, caseRoomPatientDetailsInfo);
                        } else {
                            caseRoomInfo.setP_details(false);
                            realmManager.insertCaseRoomInfo(realm, caseRoomInfo);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {

                        JSONArray attajsonArray = new JSONArray(caseroom.optString(RestUtils.ATTACHMENTS));
                        ArrayList<CaseRoomAttachmentsInfo> caseRoomAttachmentsInfoArrayList = new ArrayList<>();
                        for (int i = 0; i < attajsonArray.length(); i++) {
                            /** creating file name **/

                            File folder;
                            //folder = new File(Environment.getExternalStorageDirectory(), "/.Whitecoats/CaseRoom_Pic");
                            folder = AppUtil.getExternalStoragePathFile(mContext,".Whitecoats/CaseRoom_Pic");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
                            Date now = new Date();
                            String fileInitialName = false ? "" : "case_attach_";
                            final String fileName = fileInitialName + i + "_" + formatter.format(now) + ".jpg";
                            File f = new File(folder, fileName);
                            //f.createNewFile();
                            FileOutputStream fos = new FileOutputStream(f);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] bitmapdata = bos.toByteArray();
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();

                            JSONObject jsonObject = new JSONObject(attajsonArray.get(i).toString());
                            String content_id = jsonObject.optString("content_id");
                            String attach_name = jsonObject.optString(RestUtils.ATTACHMENT_NAME);
                            CaseRoomAttachmentsInfo cainfo = new CaseRoomAttachmentsInfo();
                            cainfo.setAttachname(fileName);
                            cainfo.setAttachuploadstatus(2);
                            cainfo.setCaseroom_summary_id(caseroom.optString(RestUtils.TAG_CASE_SUMMARY_ID));
                            cainfo.setQb_attach_id(content_id);
                            caseRoomAttachmentsInfoArrayList.add(cainfo);
                        }
                        realmManager.insertCaseRoomAttachmentsInfoArray(realm, caseRoomAttachmentsInfoArrayList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (realmManager.checkDialoginDB(realm, caseroomNotifyInfo.getCaseroom_id()) == 0) {
                        /*if (QbAuthUtils.isSessionActive()) {
                            connectQBForCaseRoom(member_added_date, dialogsRoomJId);
                        } else {
                            QBLogin qbLogin = new QBLogin(mContext, "", new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    if (s.equalsIgnoreCase(ConstsCore.QB_LOGIN_SUCCESS)) {
                                        connectQBForCaseRoom(member_added_date, dialogsRoomJId);
                                    } else {
                                        Toast.makeText(mContext, mContext.getResources().getString(R.string.exception_creating_qb), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }*/
                    }
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("601")) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                              /*  if (realmManager.checkGroupNotificationExists(realm, groupNotifyInfo.getGroup_notify_id().toString())) {
                                    realmManager.deleteGroupNotification(realm, groupNotifyInfo.getGroup_notify_id().toString());
                                }*/
                        }
                    });
                    if (mContext instanceof NotificationsActivity) {
                        notificationsActivity.hideProgress();
                    } /*else if (mContext instanceof CaseRoomSummary) {
                        caseRoomSummary.hideProgress();
                        caseRoomSummary.finish();
                    }*/
                    builder.create().show();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                    if (mContext instanceof NotificationsActivity) {
                        notificationsActivity.hideProgress();
                    } /*else if (mContext instanceof CaseRoomSummary) {
                        caseRoomSummary.hideProgress();
                        caseRoomSummary.finish();
                    }*/
                    notificationsActivity.ShowServerErrorSimpleDialog("Error", "Your session has timed out. Please login again");
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                    notificationsActivity.hideProgress();
                    AppUtil.AccessErrorPrompt(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                    notificationsActivity.hideProgress();
                    Toast.makeText(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                if (mContext instanceof NotificationsActivity) {
                    notificationsActivity.hideProgress();
                } /*else if (mContext instanceof CaseRoomSummary) {
                    caseRoomSummary.hideProgress();
                    caseRoomSummary.finish();
                }*/
                e.printStackTrace();
            }
        } else if (mContext instanceof VisitProfileActivity && check_tag.equals("connects_notification")) {
            try {
                JSONObject acceptjObject = new JSONObject(response);
                if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                    try {
                        int qb_userid = notificationsInfo.getQb_userid();
                        /*try {
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
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(mContext, "Connect request accepted", Toast.LENGTH_LONG).show();
                    realmManager.insertMyContacts(realm, notificationsInfo, 3);
                    realmManager.deleteNotification(realm, "" + notificationsInfo.getDoc_id(), RestUtils.TAG_DOC_ID);
                    //realmManager.deleteNetworkNotifications(realm, "" + notificationsInfo.getDoc_id(), RestUtils.TAG_DOC_ID);
                    visitProfileActivity.hideProgress();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("108")) {
                    builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (realmManager.checkNotificationExists(realm, notificationsInfo.getNotification_id())) {
                                realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                //realmManager.deleteNetworkNotifications(realm, notificationsInfo.getNotification_id(), "notification");
                            } /*else {
                                realmManager.deleteNotification(realm, notificationsInfo.getNotification_id(), "notification");
                                //realmManager.deleteNetworkNotifications(realm, notificationsInfo.getNotification_id(), "notification");
                            }*/
                        }
                    });
                    visitProfileActivity.hideProgress();
                    builder.create().show();
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                    visitProfileActivity.hideProgress();
                    visitProfileActivity.ShowServerErrorSimpleDialog("Error", mContext.getResources().getString(R.string.session_timedout));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                    visitProfileActivity.hideProgress();
                    AppUtil.AccessErrorPrompt(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                } else if (acceptjObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR) && acceptjObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                    visitProfileActivity.hideProgress();
                    Toast.makeText(mContext, acceptjObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                }
                /** firing Broadcast to update notification count **/
                Intent intent = new Intent("NotificationCount");
                LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
                visitProfileActivity.hideProgress();
            }

        }
        if (mContext instanceof NotificationsActivity) {
            notificationsActivity.hideProgress();
        }
    }


    private void saveGroupMembers(JSONArray jsonArray, long member_added_date, String group_id) throws JSONException {

        //if (jsonArray.length() > 0) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);
            int invite_response = jsonArrayJSONObject.getInt(RestUtils.TAG_INVITE_RESPONSE);
            boolean is_admin = jsonArrayJSONObject.getBoolean(RestUtils.TAG_IS_ADMIN);
            JSONObject cardjObject = jsonArrayJSONObject.getJSONObject(RestUtils.TAG_CARD_INFO);
            ContactsInfo contactsInfo = new ContactsInfo();
            contactsInfo.setDoc_id(Integer.parseInt(cardjObject.optString(RestUtils.TAG_DOC_ID)));
            contactsInfo.setPic_name(cardjObject.optString(RestUtils.TAG_PROFILE_PIC_NAME));
            contactsInfo.setPic_url(cardjObject.optString(RestUtils.TAG_PROFILE_PIC_URL, ""));
            contactsInfo.setName((cardjObject.has(RestUtils.TAG_USER_FULL_NAME)) ? cardjObject.optString(RestUtils.TAG_USER_FULL_NAME) : cardjObject.optString(RestUtils.TAG_FULL_NAME));
            contactsInfo.setSpeciality(cardjObject.optString(RestUtils.TAG_SPLTY));
            contactsInfo.setSubSpeciality(cardjObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
            contactsInfo.setWorkplace(cardjObject.optString(RestUtils.TAG_WORKPLACE));
            contactsInfo.setLocation(cardjObject.optString(RestUtils.TAG_LOCATION));
            contactsInfo.setEmail(cardjObject.optString(RestUtils.TAG_CNT_EMAIL));
            contactsInfo.setPhno(cardjObject.optString(RestUtils.TAG_CNT_NUM));
            //contactsInfo.setNetworkStatus(0 + "");
            contactsInfo.setQb_userid(Integer.parseInt(cardjObject.optString(RestUtils.TAG_QB_USER_ID)));
            contactsInfo.setUserSalutation(cardjObject.optString(RestUtils.TAG_USER_SALUTAION));
            contactsInfo.setUserTypeId(cardjObject.optInt(RestUtils.TAG_USER_TYPE_ID));
            //realmManager.insertMyContacts(realm, contactsInfo);
            realmManager.updateMyContacts(realm, contactsInfo);
                /*final ArrayList<ContactsInfo> contactslist = realmManager.getMyContacts(realm);
                for (int c = 0; c < contactslist.size(); c++) {
                    if (contactslist.get(c).getDoc_id() != contactsInfo.getDoc_id()) {
                        ContactsInfo contactInfo = new ContactsInfo();
                        contactInfo.setDoc_id(groupNotifyInfo.getGroup_admin_Doc_id());
                        contactInfo.setPic_name(groupNotifyInfo.getGroup_admin_pic());
                        contactInfo.setPic_url(groupNotifyInfo.getGroup_admin_pic_url());
                        contactInfo.setName(groupNotifyInfo.getGroup_admin_name());
                        contactInfo.setSpeciality(groupNotifyInfo.getGroup_admin_specialty());
                        contactInfo.setSubSpeciality(groupNotifyInfo.getGroup_admin_sub_specialty());
                        contactInfo.setWorkplace(groupNotifyInfo.getGroup_admin_workplace());
                        contactInfo.setLocation(groupNotifyInfo.getGroup_admin_location());
                        contactInfo.setEmail(groupNotifyInfo.getGroup_admin_email());
                        contactInfo.setPhno(groupNotifyInfo.getGroup_admin_phno());
                        contactInfo.setQb_userid(Integer.parseInt(groupNotifyInfo.getGroup_admin_qb_user_id()));
                        contactInfo.setUserTypeId(groupNotifyInfo.getUser_type_id());
                        contactInfo.setUserSalutation(groupNotifyInfo.getUser_salutation());
                        realmManager.updateMyContacts(realm, contactInfo);
                    }
                }*/
            QBDialogMemInfo dialogMemInfo = new QBDialogMemInfo();
            dialogMemInfo.setDialog_id(groupNotifyInfo.getGroup_id().toString());
            dialogMemInfo.setDoc_id(contactsInfo.getDoc_id());
            dialogMemInfo.setInvite_response(invite_response);
            dialogMemInfo.setIs_admin(is_admin);
            ArrayList<QBDialogMemInfo> dialogMemInfoArrayList = new ArrayList<>();
            dialogMemInfoArrayList.add(dialogMemInfo);
            /** inserting in members info **/
            realmManager.insertDialogMem(realm, dialogMemInfoArrayList);
        }
        /*} else {
            final ArrayList<ContactsInfo> contactslist = realmManager.getMyContacts(realm);
            for (int c = 0; c < contactslist.size(); c++) {
                if (contactslist.get(c).getDoc_id() == groupNotifyInfo.getGroup_admin_Doc_id()) {
                    ContactInfo = new ContactsInfo();
                    ContactInfo.setDoc_id(groupNotifyInfo.getGroup_admin_Doc_id());
                    ContactInfo.setPic_name(groupNotifyInfo.getGroup_admin_pic());
                    ContactInfo.setPic_url(groupNotifyInfo.getGroup_admin_pic_url());
                    ContactInfo.setName(groupNotifyInfo.getGroup_admin_name());
                    ContactInfo.setSpeciality(groupNotifyInfo.getGroup_admin_specialty());
                    ContactInfo.setSubSpeciality(groupNotifyInfo.getGroup_admin_sub_specialty());
                    ContactInfo.setWorkplace(groupNotifyInfo.getGroup_admin_workplace());
                    ContactInfo.setLocation(groupNotifyInfo.getGroup_admin_location());
                    ContactInfo.setEmail(groupNotifyInfo.getGroup_admin_email());
                    ContactInfo.setPhno(groupNotifyInfo.getGroup_admin_phno());
                    ContactInfo.setQb_userid(Integer.parseInt(groupNotifyInfo.getGroup_admin_qb_user_id()));
                    realmManager.updateMyContacts(realm, ContactInfo);
                }
            }

        }*/


        /** Inserting admin in Group MemInfo **/
        QBDialogMemInfo qbDialogMemInfo = new QBDialogMemInfo();
        qbDialogMemInfo.setDoc_id(groupNotifyInfo.getGroup_admin_Doc_id());
        qbDialogMemInfo.setIs_admin(true);
        qbDialogMemInfo.setInvite_response(1);
        qbDialogMemInfo.setDialog_id(groupNotifyInfo.getGroup_id());
        realmManager.updateDialogMem(realm, qbDialogMemInfo);

        realmManager.updateDialog_memaddedDate(realm, member_added_date, group_id);

        /** updating yourself when accept button clicked **/
        QBDialogMemInfo yourdialogMemInfo = new QBDialogMemInfo();
        yourdialogMemInfo.setDialog_id(groupNotifyInfo.getGroup_id().toString());
        yourdialogMemInfo.setDoc_id(realmManager.getDoc_id(realm));
        yourdialogMemInfo.setInvite_response(1);
        realmManager.updateDialogMem(realm, yourdialogMemInfo);

    }
}

