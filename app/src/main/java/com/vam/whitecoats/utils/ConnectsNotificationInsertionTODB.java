package com.vam.whitecoats.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.interfaces.ConnectNotificationPreDataListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.vam.whitecoats.utils.RestUtils.TAG_STATUS;
import static com.vam.whitecoats.utils.RestUtils.TAG_SUCCESS;

public class ConnectsNotificationInsertionTODB {

    private final Context context;
    private final RealmManager realmManager;
    private final Realm realm;
    private final int doc_id;
    private final boolean loadNext;
    private final boolean loadPreData;
    private final MySharedPref sharedPrefObj;
    private final ConnectNotificationPreDataListener listener;
    private final RealmResults<RealmNotificationInfo> notificationData;
    private long previousNotificationTimeRecieved;
    private long last_noti_time;

    public ConnectsNotificationInsertionTODB(Context mContext, RealmManager mRealmManager, Realm mRealm, int docID, boolean mLoadNext, boolean preData, RealmResults<RealmNotificationInfo> _notificationData, ConnectNotificationPreDataListener connectNotificationPreDataListener) {
        this.context = mContext;
        this.realmManager = mRealmManager;
        this.realm = mRealm;
        this.doc_id = docID;
        this.loadNext = mLoadNext;
        this.loadPreData = preData;
        sharedPrefObj = new MySharedPref(context);
        this.listener = connectNotificationPreDataListener;
        this.notificationData = _notificationData;
        getConnectNotification(prepareGetConnectsNotificationRequest(loadPreData, loadNext));
    }

    private void getConnectNotification(String prepareGetConnectsNotificationRequest) {
        new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.FETCH_USER_CONNECTS_NOTIFICATIONS_API, prepareGetConnectsNotificationRequest.toString(), "FETCH_CONNECT_NOTIFICATION", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(successResponse);
                    if (jsonObject.getString(TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                        String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                        if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                            errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                        }
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.getString(TAG_STATUS).equals((TAG_SUCCESS))) {
                        JSONArray data = jsonObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray("notif_data");
                        last_noti_time = jsonObject.optJSONObject(RestUtils.TAG_DATA).optLong("prev_noti_time");
                        sharedPrefObj.savePref("last_noti_time", last_noti_time);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject notificationObj = data.optJSONObject(i);
                            if (notificationObj != null) {
                                if (i == data.length() - 1) {
                                    previousNotificationTimeRecieved = notificationObj.optLong("time_recieved");
                                    sharedPrefObj.savePref("last_notification_time_recieved", previousNotificationTimeRecieved);
                                }
                                String ntfy_Type = notificationObj.optString(RestUtils.TAG_TYPE);
                                String ntfyId = notificationObj.optString("noti_id");
                                long notification_time_recieved = notificationObj.optLong("time_recieved");
                                //realmManager.insertNotifiInfoInMigration(realm, notificationObj.optString("noti_id"), notificationObj.toString(), true, false);
                                if (ntfy_Type.equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
                                    final ContactsInfo notify_contactsInfo = new ContactsInfo();
                                    notify_contactsInfo.setNotification_type(ntfy_Type);
                                    notify_contactsInfo.setNotification_id(ntfyId);
                                    JSONObject fromdocjObject = notificationObj.getJSONObject(RestUtils.TAG_NOTI_INFO);
                                    notify_contactsInfo.setDoc_id(Integer.parseInt(fromdocjObject.getString(RestUtils.TAG_USER_ID)));
                                    notify_contactsInfo.setName(fromdocjObject.optString(RestUtils.TAG_USER_SALUTAION) + fromdocjObject.optString(RestUtils.TAG_FNAME) + " " + fromdocjObject.optString(RestUtils.TAG_LNAME));
                                    notify_contactsInfo.setSpeciality(fromdocjObject.optString(RestUtils.TAG_SPLTY));
                                    notify_contactsInfo.setSubSpeciality(fromdocjObject.optString(RestUtils.TAG_SUB_SPLTY, ""));
                                    notify_contactsInfo.setLocation(fromdocjObject.getString(RestUtils.TAG_LOCATION));
                                    notify_contactsInfo.setPic_url(fromdocjObject.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL, ""));
                                    notify_contactsInfo.setEmail(fromdocjObject.getString(RestUtils.TAG_CNT_EMAIL));
                                    notify_contactsInfo.setPhno(fromdocjObject.getString(RestUtils.TAG_CNT_NUM));
                                    notify_contactsInfo.setTime(notificationObj.getLong(RestUtils.TAG_TIME_RECEIVED));
                                    notify_contactsInfo.setUserSalutation(fromdocjObject.optString(RestUtils.TAG_USER_SALUTAION));
                                    notify_contactsInfo.setUserTypeId(fromdocjObject.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    notify_contactsInfo.setMessage(fromdocjObject.optString(RestUtils.TAG_INVITE_TEXT));
                                    realmManager.insertNotifiInfoInMigration(realm, ntfyId, notificationObj.toString(), true, false, notification_time_recieved);
                                } else if (ntfy_Type.equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT_RESPONSE.name()) || ntfy_Type.equalsIgnoreCase(NotificationType.DEFAULT_USER_CONNECT.name())) {
                                    final ContactsInfo notify_contactsInfo = new ContactsInfo();
                                    notify_contactsInfo.setNotification_type(ntfy_Type);
                                    notify_contactsInfo.setNotification_id(ntfyId);
                                    int doc_id=0;
                                    JSONObject notification_info_object = notificationObj.optJSONObject("noti_info");
                                    if (notification_info_object != null) {
                                        notify_contactsInfo.setDoc_id(Integer.parseInt(notification_info_object.optString(RestUtils.TAG_USER_ID)));
                                        notify_contactsInfo.setName(notification_info_object.optString(RestUtils.TAG_USER_SALUTAION) + notification_info_object.optString(RestUtils.TAG_FNAME) + " " + notification_info_object.optString(RestUtils.TAG_LNAME));
                                        notify_contactsInfo.setSpeciality(notification_info_object.optString(RestUtils.TAG_SPLTY));
                                        notify_contactsInfo.setSubSpeciality(notification_info_object.optString(RestUtils.TAG_SUB_SPLTY, ""));
                                        notify_contactsInfo.setLocation(notification_info_object.optString(RestUtils.TAG_LOCATION));
                                        notify_contactsInfo.setPic_url(notification_info_object.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                        notify_contactsInfo.setEmail(notification_info_object.optString(RestUtils.TAG_CNT_EMAIL));
                                        notify_contactsInfo.setPhno(notification_info_object.optString(RestUtils.TAG_CNT_NUM));
                                        notify_contactsInfo.setUserSalutation(notification_info_object.optString(RestUtils.TAG_USER_SALUTAION));
                                        notify_contactsInfo.setUserTypeId(notification_info_object.optInt(RestUtils.TAG_USER_TYPE_ID));
                                        notify_contactsInfo.setPhno_vis(notification_info_object.optString(RestUtils.TAG_CNNTMUNVIS));
                                        notify_contactsInfo.setEmail_vis(notification_info_object.optString(RestUtils.TAG_CNNTEMAILVIS));

                                        doc_id = Integer.parseInt(notification_info_object.optString(RestUtils.TAG_USER_ID));
                                    }
                                    notify_contactsInfo.setTime(notificationObj.optLong(RestUtils.TAG_TIME_RECEIVED));
                                    if (ntfy_Type.equals(NotificationType.DEFAULT_USER_CONNECT.name())) {
                                        notify_contactsInfo.setMessage(notificationObj.optString("notificationMsg"));
                                    } else {
                                        notify_contactsInfo.setMessage(RestUtils.TAG_ACCEPTED_FRD_REQUEST);
                                    }
                                    realmManager.insertMyContacts(realm, notify_contactsInfo);
                                    realmManager.insertNotifiInfoInMigration(realm, ntfyId, notificationObj.toString(), true, false, notification_time_recieved);
                                    realmManager.updateMyContactsStatus(realm, doc_id, 3);
                                }

                            }
                        }

                        if (context.getClass().getSimpleName().equalsIgnoreCase("DashboardActivity") && !loadPreData) {
                            if (data.length() == 0) {
                                sharedPrefObj.savePref("connects_notification_sync_completed", false);
                            } else {
                                getConnectNotification(prepareGetConnectsNotificationRequest(loadPreData, loadNext));
                            }
                        }
                        listener.notifyUIWithPreData(data);
                    }
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                JSONObject jsonObject = null;
                /*ENGG-3376 -- Added the Exception in catch block to avoid the crash */
                try {
                    jsonObject = new JSONObject(errorResponse);
                    String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                    if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                        errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                    }
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).sendSinglePartRequest();


    }

    private String prepareGetConnectsNotificationRequest(boolean loadPreData, boolean loadNext) {
        JSONObject reqObj = new JSONObject();
        try {
            reqObj.put(RestUtils.TAG_USER_ID, doc_id);
            if (loadPreData) {
                long predateNotificationTimeReceived;
                if (notificationData.size() == 0) {
                    predateNotificationTimeReceived = sharedPrefObj.getPref("last_noti_time", 0L);
                } else {
                    String data = notificationData.get(0).getNotifyData();
                    JSONObject jsonObject = new JSONObject(data);
                    predateNotificationTimeReceived = jsonObject.optLong(RestUtils.TAG_TIME_RECEIVED);
                }
                reqObj.put("prev_notif_time", predateNotificationTimeReceived);
                reqObj.put("load_next", false);
            } else {
                previousNotificationTimeRecieved = sharedPrefObj.getPref("last_notification_time_recieved", 0L);
                reqObj.put("prev_notif_time", previousNotificationTimeRecieved);
                reqObj.put("load_next", true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqObj.toString();
    }
}
