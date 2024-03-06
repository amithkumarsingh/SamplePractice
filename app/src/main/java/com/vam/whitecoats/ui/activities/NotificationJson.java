package com.vam.whitecoats.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;

import com.vam.whitecoats.core.realm.RealmCaseRoomNotifications;
import com.vam.whitecoats.core.realm.RealmGroupNotifications;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotifications;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import io.realm.Realm;
import io.realm.RealmResults;



public class NotificationJson {
    private MySharedPref sharedPref;
    Context context;
    private SharedPreferences editor;

    public NotificationJson(Context context) {
        this.context = context;
        sharedPref = new MySharedPref(context);
    }


    public void startMigration() {
        ArrayList list = sortRequestsBasedOnTime(fetchConnects(), fetchCaseRooms(), fetchGroups());
        Collections.sort(list, new CustomComparator());
        startPrepareJSON(list);
    }

    private void startPrepareJSON(ArrayList list) {
        LinkedHashMap<String, JSONObject> listOfJSON = new LinkedHashMap<>();
        for (Object requestObject : list) {
            if (requestObject instanceof RealmNotifications) {
                listOfJSON.put("" + ((RealmNotifications) requestObject).getNotification_id(), prepareJSONForConnect((RealmNotifications) requestObject));
            } else if (requestObject instanceof RealmCaseRoomNotifications) {
                listOfJSON.put("" + ((RealmCaseRoomNotifications) requestObject).getCaseroom_notification_id(), prepareJSONForCase((RealmCaseRoomNotifications) requestObject));
            } else if (requestObject instanceof RealmGroupNotifications) {
                listOfJSON.put("" + ((RealmGroupNotifications) requestObject).getGroup_notification_id(), prepareJSONForGroup((RealmGroupNotifications) requestObject));
            }
        }
        if(listOfJSON.size()>0){
            RealmManager realmManager=new RealmManager(context);
            realmManager.insertNotificationInfoInMigration(listOfJSON);
        }
        sharedPref.savePref(MySharedPref.PREF_MIGRATION_FIRST_RUN, true);
    }

    private JSONObject prepareJSONForConnect(RealmNotifications connectRequest) {
        try {
            JSONObject requestObject = new JSONObject();
            requestObject.put(RestUtils.TAG_NOTIFICATION_ID, connectRequest.getNotification_id());
            requestObject.put(RestUtils.TAG_TYPE, connectRequest.getNotification_type());
            requestObject.put(RestUtils.TAG_NOTIFICATION_MESSAGE, connectRequest.getMessage());
            requestObject.put(RestUtils.TAG_TIME_RECEIVED, connectRequest.getTime());

            JSONObject fromDoc = new JSONObject();
            fromDoc.put(RestUtils.TAG_DOC_ID, connectRequest.getDoc_id());
            fromDoc.put(RestUtils.TAG_CNT_EMAIL, connectRequest.getDoc_email());
            fromDoc.put(RestUtils.TAG_CNT_NUM, connectRequest.getDoc_phno());
            fromDoc.put(RestUtils.TAG_QB_USER_ID, connectRequest.getDoc_qb_user_id());
            fromDoc.put(RestUtils.TAG_SPLTY, connectRequest.getDoc_speciality());
            fromDoc.put(RestUtils.TAG_SUB_SPECIALITY, connectRequest.getDoc_sub_speciality());
            fromDoc.put(RestUtils.TAG_FULL_NAME, connectRequest.getDoc_name());
            fromDoc.put(RestUtils.TAG_USER_TYPE_ID, connectRequest.getUser_type_id());
            fromDoc.put(RestUtils.TAG_USER_SALUTAION, connectRequest.getUser_salutation());
            //fromDoc.put(RestUtils.TAG_USER_FULL_NAME,connectRequest.getUser_fu());
            fromDoc.put(RestUtils.TAG_LOCATION, connectRequest.getDoc_location());
            fromDoc.put(RestUtils.TAG_WORKPLACE, connectRequest.getDoc_workplace());
            //fromDoc.put(RestUtils.TAG_DESIGNATION,connectRequest.get() );
            fromDoc.put(RestUtils.TAG_PROFILE_PIC_NAME, connectRequest.getDoc_pic());
            fromDoc.put(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL, connectRequest.getDoc_pic_url());
            requestObject.put(RestUtils.TAG_FROM_DOC, fromDoc);

            return requestObject;

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private JSONObject prepareJSONForCase(RealmCaseRoomNotifications caseRequest) {
        try {
            JSONObject requestObject = new JSONObject();
            requestObject.put(RestUtils.TAG_TYPE, caseRequest.getCaseroom_notify_type());
            requestObject.put(RestUtils.TAG_NOTIFICATION_ID, caseRequest.getCaseroom_notification_id());
            // requestObject.put(RestUtils.TAG_NOTIFICATION_MESSAGE, caseRequest.());
            requestObject.put(RestUtils.TAG_TIME_RECEIVED, caseRequest.getTime_received());

            JSONObject fromCaseInviteInfo = new JSONObject();
            fromCaseInviteInfo.put(RestUtils.TAG_CASE_SUMMARY_ID, caseRequest.getCaseroom_summary_id());
            fromCaseInviteInfo.put(RestUtils.TAG_CASE_ROOM_ID, caseRequest.getCaseroom_id());
            //fromCaseInviteInfo.put(RestUtils.TAG_CASE_ROOM_TITLE, caseRequest.get);
            fromCaseInviteInfo.put(RestUtils.TAG_CASE_ROOM_SPLTY, caseRequest.getCase_speciality());
            fromCaseInviteInfo.put(RestUtils.TAG_XMPP_ROOM_JID, caseRequest.getCaseroom_group_xmpp_jid());
            fromCaseInviteInfo.put(RestUtils.TAG_CASE_ROOM_GROUP_CREATED_DATE, caseRequest.getCaseroom_group_created_date());
            requestObject.put("caseroom_invite_info", fromCaseInviteInfo);

            JSONObject fromInviterInfo = new JSONObject();
            fromInviterInfo.put(RestUtils.TAG_DOC_ID, caseRequest.getDoc_id());
            fromInviterInfo.put(RestUtils.TAG_CNT_EMAIL, caseRequest.getDoc_cnt_email());
            fromInviterInfo.put(RestUtils.TAG_CNT_NUM, caseRequest.getDoc_cnt_num());
            fromInviterInfo.put(RestUtils.TAG_QB_USER_ID, caseRequest.getDoc_qb_user_id());
            fromInviterInfo.put(RestUtils.TAG_SPLTY, caseRequest.getDoc_speciality());
            fromInviterInfo.put(RestUtils.TAG_SUB_SPECIALITY, caseRequest.getSubSpeciality());
            fromInviterInfo.put(RestUtils.TAG_FULL_NAME, caseRequest.getDoc_name());
            fromInviterInfo.put(RestUtils.TAG_USER_TYPE_ID, caseRequest.getUser_type_id());
            fromInviterInfo.put(RestUtils.TAG_USER_SALUTAION, caseRequest.getUser_salutation());
            //fromInviterInfo.put(RestUtils.TAG_USER_FULL_NAME,caseRequest.getUser_fu());
            fromInviterInfo.put(RestUtils.TAG_LOCATION, caseRequest.getDoc_location());
            fromInviterInfo.put(RestUtils.TAG_WORKPLACE, caseRequest.getDoc_workplace());
            //fromInviterInfo.put(RestUtils.TAG_DESIGNATION,caseRequest.get() );
            fromInviterInfo.put(RestUtils.TAG_PROFILE_PIC_NAME, caseRequest.getDoc_pic_name());
            fromInviterInfo.put(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL, caseRequest.getDoc_pic_url());
            requestObject.put("inviter_info", fromInviterInfo);

            return requestObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject prepareJSONForGroup(RealmGroupNotifications groupRequest) {
        try {
            JSONObject requestObject = new JSONObject();
            requestObject.put(RestUtils.TAG_TYPE, groupRequest.getGroup_notification_type());
            requestObject.put(RestUtils.TAG_NOTIFICATION_ID, groupRequest.getGroup_notification_id());
            // requestObject.put(RestUtils.TAG_NOTIFICATION_MESSAGE, groupRequest.());
            requestObject.put(RestUtils.TAG_TIME_RECEIVED, groupRequest.getGroup_notification_time());

            JSONObject fromGroupInfo = new JSONObject();
            fromGroupInfo.put(RestUtils.TAG_GROUP_ID, groupRequest.getGroup_id());
            fromGroupInfo.put(RestUtils.TAG_GROUP_PROFILE_IMG_NAME, groupRequest.getGroup_pic());
            //fromGroupInfo.put(RestUtils.TAG_CASE_ROOM_TITLE, groupRequest.get);
            //fromGroupInfo.put(RestUtils.TAG_CHANNEL_LOGO, groupRequest.getCase_speciality());
            //fromGroupInfo.put(RestUtils.TAG_LOG_SMALL_URL, groupRequest.get);
            fromGroupInfo.put(RestUtils.TAG_GROUP_TITLE, groupRequest.getGroup_name());
            //fromGroupInfo.put(RestUtils.TAG_XMPP_ROOM_JID, groupRequest.getGroup_);
            fromGroupInfo.put(RestUtils.TAG_GROUP_CREATION_TIME, groupRequest.getGroup_creation_time());
            requestObject.put("group_info", fromGroupInfo);

            JSONObject fromGroupInviterInfo = new JSONObject();
            fromGroupInviterInfo.put(RestUtils.TAG_DOC_ID, groupRequest.getGroup_admin_Doc_id());
            fromGroupInviterInfo.put(RestUtils.TAG_CNT_EMAIL, groupRequest.getGroup_admin_email());
            fromGroupInviterInfo.put(RestUtils.TAG_CNT_NUM, groupRequest.getGroup_admin_phno());
            fromGroupInviterInfo.put(RestUtils.TAG_QB_USER_ID, groupRequest.getGroup_admin_qb_user_id());
            fromGroupInviterInfo.put(RestUtils.TAG_SPLTY, groupRequest.getGroup_admin_specialty());
            fromGroupInviterInfo.put(RestUtils.TAG_SUB_SPECIALITY, groupRequest.getGroup_admin_sub_specialty());
            fromGroupInviterInfo.put(RestUtils.TAG_FULL_NAME, groupRequest.getGroup_admin_name());
            fromGroupInviterInfo.put(RestUtils.TAG_USER_TYPE_ID, groupRequest.getUser_type_id());
            fromGroupInviterInfo.put(RestUtils.TAG_USER_SALUTAION, groupRequest.getUser_salutation());
            //fromGroupInviterInfo.put(RestUtils.TAG_USER_FULL_NAME,groupRequest.getUser_fu());
            fromGroupInviterInfo.put(RestUtils.TAG_LOCATION, groupRequest.getGroup_admin_location());
            fromGroupInviterInfo.put(RestUtils.TAG_WORKPLACE, groupRequest.getGroup_admin_workplace());
            //fromGroupInviterInfo.put(RestUtils.TAG_DESIGNATION,groupRequest.get() );
            fromGroupInviterInfo.put(RestUtils.TAG_PROFILE_PIC_NAME, groupRequest.getGroup_pic());
            fromGroupInviterInfo.put(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL, groupRequest.getGroup_admin_pic_url());
            requestObject.put("inviter_info", fromGroupInviterInfo);


            return requestObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList sortRequestsBasedOnTime(RealmResults<RealmNotifications> connectRequest, RealmResults<RealmCaseRoomNotifications> caseRoomRequest, RealmResults<RealmGroupNotifications> groupRequest) {
        ArrayList list = new ArrayList();
        for (RealmNotifications connect : connectRequest) {
            list.add(connect);
        }
        for (RealmCaseRoomNotifications caseRoom : caseRoomRequest) {
            list.add(caseRoom);
        }

        for (RealmGroupNotifications group : groupRequest) {
            list.add(group);
        }
        return list;
    }

    public class CustomComparator implements Comparator<Object> {
        @Override
        public int compare(Object o1, Object o2) {
            long time1 = 0, time2 = 0;
            if (o1 instanceof RealmNotifications) {
                time1 = ((RealmNotifications) o1).getTime();
            } else if (o1 instanceof RealmCaseRoomNotifications) {
                time1 = ((RealmCaseRoomNotifications) o1).getTime_received();
            } else if (o1 instanceof RealmGroupNotifications) {
                time1 = ((RealmGroupNotifications) o1).getGroup_notification_time();
            }

            if (o2 instanceof RealmNotifications) {
                time2 = ((RealmNotifications) o2).getTime();
            } else if (o2 instanceof RealmCaseRoomNotifications) {
                time2 = ((RealmCaseRoomNotifications) o2).getTime_received();
            } else if (o2 instanceof RealmGroupNotifications) {
                time2 = ((RealmGroupNotifications) o2).getGroup_notification_time();
            }

            return ((Long) time1).compareTo(time2);
        }
    }


    private RealmResults<RealmNotifications> fetchConnects() {

        RealmResults<RealmNotifications> arrayList;
        RealmManager realmManager = new RealmManager(context);
        Realm realm = Realm.getDefaultInstance();
        arrayList = realmManager.getNotificationDB(realm);

        return arrayList;


    }

    private RealmResults<RealmCaseRoomNotifications> fetchCaseRooms() {

        RealmResults<RealmCaseRoomNotifications> caseroomNotifyInfoArrayList;
        RealmManager realmManager = new RealmManager(context);
        Realm realm = Realm.getDefaultInstance();
        caseroomNotifyInfoArrayList = realmManager.getCaseRoomNotificationDB(realm);

        return caseroomNotifyInfoArrayList;
    }


    private RealmResults<RealmGroupNotifications> fetchGroups() {

        RealmResults<RealmGroupNotifications> groupsarrayList;
        RealmManager realmManager = new RealmManager(context);
        Realm realm = Realm.getDefaultInstance();
        groupsarrayList = realmManager.getGroupNotificationDB(realm);

        return groupsarrayList;


    }


}
