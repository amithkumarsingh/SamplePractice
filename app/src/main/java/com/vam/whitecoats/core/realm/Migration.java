/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vam.whitecoats.core.realm;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;


public class Migration implements RealmMigration {
    public static final String TAG = Migration.class.getSimpleName();

    @Override
    public void migrate(final DynamicRealm realm, long oldVersion, long newVersion) {
        Log.i(TAG, "migrate()");
        Log.i(TAG, "old version - " + oldVersion + "\n New version - " + newVersion);
        RealmSchema schema = realm.getSchema();


        if (oldVersion == 0) {
            try {


                schema.get("RealmBasicInfo").addField("qb_hidden_dialog_id", String.class).addField("pic_name", String.class);
                schema.get("RealmCaseRoomNotifications").addField("count_status", int.class).removeField("doc_pic_path");
                schema.get("RealmGroupNotifications").removeField("group_pic_path").removeField("group_admin_pic_path");
                schema.get("RealmMyContactsInfo").removeField("download_pic_path");
                schema.get("RealmNotifications").removeField("doc_pic_path");
                schema.get("RealmQBGroupDialog").removeField("dialog_pic_path");
                //schema.get("RealmQBDialog").addField("last_msg_sent_qb_id", int.class);

                schema.get("RealmAcademicInfo").setNullable("degree", true);
                schema.get("RealmAcademicInfo").setNullable("university", true);
                schema.get("RealmAcademicInfo").setNullable("college", true);
                schema.get("RealmAcademicInfo").addIndex("acad_id");


                schema.get("RealmBasicInfo").setNullable("fname", true);
                schema.get("RealmBasicInfo").setNullable("lname", true);
                schema.get("RealmBasicInfo").setNullable("email", true);
                schema.get("RealmBasicInfo").setNullable("psswd", true);
                schema.get("RealmBasicInfo").setNullable("splty", true);
//                schema.get("RealmBasicInfo").setNullable("subSpeciality", true);
                schema.get("RealmBasicInfo").setNullable("phone_num", true);
                schema.get("RealmBasicInfo").setNullable("about_me", true);
                schema.get("RealmBasicInfo").setNullable("profile_pic_path", true);
                schema.get("RealmBasicInfo").setNullable("reg_card_path", true);
                //schema.get("RealmBasicInfo").setNullable("pic_name", true);
                schema.get("RealmBasicInfo").setNullable("website", true);
                schema.get("RealmBasicInfo").setNullable("blog_page", true);
                schema.get("RealmBasicInfo").setNullable("fb_page", true);
                schema.get("RealmBasicInfo").setNullable("emailvalidation", true);
                schema.get("RealmBasicInfo").setNullable("qb_login", true);
                //schema.get("RealmBasicInfo").setNullable("qb_hidden_dialog_id", true);

                schema.get("RealmCardInfo").setNullable("full_name", true);
                schema.get("RealmCardInfo").setNullable("splty", true);
                schema.get("RealmCardInfo").setNullable("notify_pic", true);
                schema.get("RealmCardInfo").setNullable("phone", true);
                schema.get("RealmCardInfo").setNullable("location", true);
                schema.get("RealmCardInfo").setNullable("email", true);
                schema.get("RealmCardInfo").setNullable("workplace", true);
                schema.get("RealmCardInfo").setNullable("degrees", true);

                schema.get("RealmCaseRoomAttachmentsInfo").setNullable("attachname", true);
                schema.get("RealmCaseRoomAttachmentsInfo").setNullable("qb_attach_id", true);

                schema.get("RealmQBDialog").setNullable("last_msg", true);
                schema.get("RealmQBDialog").setNullable("dialog_id", true);
                schema.get("RealmQBDialog").setNullable("dialog_type", true);
                schema.get("RealmQBDialog").setNullable("local_dialog_type", true);
                schema.get("RealmQBDialog").setNullable("dialog_name", true);
                schema.get("RealmQBDialog").setNullable("group_pic_path", true);
                schema.get("RealmQBDialog").setNullable("other_doc_name", true);
                schema.get("RealmQBDialog").setNullable("group_roomjid", true);
                schema.get("RealmQBDialog").setNullable("occupants_ids", true);

                schema.get("RealmQBGroupDialog").setNullable("dialog_title", true);
                schema.get("RealmQBGroupDialog").setNullable("dialog_pic_name", true);
                schema.get("RealmQBGroupDialog").setNullable("last_msg_sent_qb_id", true);
                schema.get("RealmQBGroupDialog").setNullable("last_msg", true);
                schema.get("RealmQBGroupDialog").setNullable("last_msg_time", true);
                schema.get("RealmQBGroupDialog").setNullable("dialog_room_jid", true);
                schema.get("RealmQBGroupDialog").setNullable("local_dialog_type", true);

                schema.get("RealmCaseRoomInfo").setNullable("title", true);
                schema.get("RealmCaseRoomInfo").setNullable("speciality", true);
                schema.get("RealmCaseRoomInfo").setNullable("query", true);
                schema.get("RealmCaseRoomInfo").setNullable("attachments", true);
                schema.get("RealmCaseRoomInfo").setNullable("last_message", true);
                schema.get("RealmCaseRoomInfo").setNullable("caseroom_dialog_id", true);

                schema.get("RealmCaseRoomNotifications").setNullable("caseroom_id", true);
                schema.get("RealmCaseRoomNotifications").setNullable("caseroom_summary_id", true);
                schema.get("RealmCaseRoomNotifications").setNullable("caseroom_group_xmpp_jid", true);
                schema.get("RealmCaseRoomNotifications").setNullable("case_heading", true);
                schema.get("RealmCaseRoomNotifications").setNullable("case_speciality", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_qb_user_id", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_name", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_speciality", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_workplace", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_location", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_cnt_email", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_cnt_num", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_email_vis", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_phno_vis", true);
                schema.get("RealmCaseRoomNotifications").setNullable("doc_pic_name", true);
                schema.get("RealmCaseRoomNotifications").setNullable("caseroom_notify_type", true);

                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("patgender", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("patage", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("symptoms", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("history", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("vitals_anthropometry", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("general_examination", true);
                schema.get("RealmCaseRoomPatientDetailsInfo").setNullable("systemic_examination", true);

                schema.get("RealmGroupNotifications").setNullable("group_id", true);
                schema.get("RealmGroupNotifications").setNullable("group_name", true);
                schema.get("RealmGroupNotifications").setNullable("group_pic", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_name", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_pic", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_specialty", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_workplace", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_email", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_phno", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_email_vis", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_phno_vis", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_location", true);
                schema.get("RealmGroupNotifications").setNullable("group_admin_qb_user_id", true);
                schema.get("RealmGroupNotifications").setNullable("group_notification_type", true);

                schema.get("RealmLogin").setNullable("qb_user_login", true);
                schema.get("RealmLogin").setNullable("qb_user_password", true);

                schema.get("RealmMessages").setNullable("dialogId", true);
                schema.get("RealmMessages").setNullable("message", true);
                schema.get("RealmMessages").setNullable("message_type", true);
                schema.get("RealmMessages").setNullable("att_type", true);
                schema.get("RealmMessages").setNullable("att_qbid", true);
                schema.get("RealmMessages").setNullable("attachUrl", true);

                schema.get("RealmMyContactsInfo").setNullable("name", true);
                schema.get("RealmMyContactsInfo").setNullable("speciality", true);
                schema.get("RealmMyContactsInfo").setNullable("pic_name", true);
                schema.get("RealmMyContactsInfo").setNullable("degree", true);
                schema.get("RealmMyContactsInfo").setNullable("workplace", true);
                schema.get("RealmMyContactsInfo").setNullable("location", true);
                schema.get("RealmMyContactsInfo").setNullable("networkStatus", true);
                schema.get("RealmMyContactsInfo").setNullable("email", true);
                schema.get("RealmMyContactsInfo").setNullable("email_vis", true);
                schema.get("RealmMyContactsInfo").setNullable("phno", true);
                schema.get("RealmMyContactsInfo").setNullable("phno_vis", true);

                schema.get("RealmNotifications").setNullable("notification_type", true);
                schema.get("RealmNotifications").setNullable("doc_pic", true);
                schema.get("RealmNotifications").setNullable("doc_name", true);
                schema.get("RealmNotifications").setNullable("doc_speciality", true);
                schema.get("RealmNotifications").setNullable("doc_workplace", true);
                schema.get("RealmNotifications").setNullable("doc_email", true);
                schema.get("RealmNotifications").setNullable("doc_phno", true);
                schema.get("RealmNotifications").setNullable("doc_location", true);
                schema.get("RealmNotifications").setNullable("doc_email_vis", true);
                schema.get("RealmNotifications").setNullable("doc_phno_vis", true);
                schema.get("RealmNotifications").setNullable("message", true);

                schema.get("RealmOnlinePub").setNullable("pub_name", true);

                schema.get("RealmPrintPub").setNullable("print_name", true);

                schema.get("RealmProfessionalInfo").setNullable("workplace", true);
                schema.get("RealmProfessionalInfo").setNullable("designation", true);
                schema.get("RealmProfessionalInfo").setNullable("location", true);
                schema.get("RealmProfessionalInfo").addIndex("prof_id");

                schema.get("RealmProfessionalMembership").setNullable("membership_name", true);

                schema.get("RealmPublications").setNullable("title", true);
                schema.get("RealmPublications").setNullable("authors", true);
                schema.get("RealmPublications").setNullable("journal", true);
                schema.get("RealmPublications").setNullable("web_page", true);
                schema.get("RealmPublications").setNullable("type", true);

                schema.get("RealmSymptomsInfo").setNullable("addSymptons", true);
                schema.get("RealmSymptomsInfo").setNullable("duration", true);
                schema.get("RealmSymptomsInfo").setNullable("details", true);

              /*
                RealmBasicInfo  ---- + qb_hidden_dialog_id;, + pic_name;
                RealmCaseroomNotification ----  - doc_pic_path;, + count_status;
                RealmGroupNotifications ---- - group_pic_path;,- group_admin_pic_path;
                RealmMyContactsInfo     ---- - download_pic_path;
                RealmNotifications      ---- - doc_pic_path;
                RealmQBGroupDialog      ---- - dialog_pic_path;
                 */
                oldVersion++;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Migration Exception", "" + e);
            }
        }
        /*
         * Migrate from version 1 to 2
         */
        if (oldVersion == 1) {
            schema.get("RealmBasicInfo")
                    .addField("subSpeciality", String.class)
                    .addField("mobileVerified", boolean.class)
                    .addField("emailVerified", boolean.class);
            oldVersion++;
        }

        if (oldVersion == 2) {
            schema.create("RealmFeedInfo")
                    .addField("feedChannelId", String.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                    .addField("feedId", int.class)
                    .addField("createdOrUpdatedTime", long.class)
                    .addField("channelId", int.class)
                    .addField("docId", int.class)
                    .addField("feedsJson", String.class);
            schema.create("RealmFeedsList")
                    .addField("feed_Id", int.class, FieldAttribute.PRIMARY_KEY).addRealmListField("feedsList", schema.get("RealmFeedInfo"));
            schema.get("RealmMyContactsInfo")
                    .addField("pic_url", String.class);
            schema.get("RealmBasicInfo")
                    .addField("pic_url", String.class);
            schema.get("RealmCaseRoomNotifications")
                    .addField("doc_pic_url", String.class);
            schema.get("RealmNotifications")
                    .addField("doc_pic_url", String.class);
            schema.get("RealmGroupNotifications")
                    .addField("group_pic_url", String.class).addField("group_admin_pic_url", String.class);
            schema.get("RealmQBDialog")
                    .addField("group_pic_url", String.class);
            schema.get("RealmQBGroupDialog")
                    .addField("dialog_pic_url", String.class);
            oldVersion++;
        }

        if (oldVersion == 3) {
            schema.create("RealmShareFailedFeedInfo")
                    .addField("id", int.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                    .addField("feedData", String.class);
            oldVersion++;
        }
        if (oldVersion == 4) {
            schema.get("RealmCaseRoomNotifications")
                    .addField("subSpeciality", String.class)
                    .addField("case_sub_speciality", String.class);
            schema.get("RealmMyContactsInfo")
                    .addField("subspeciality", String.class);
            schema.get("RealmGroupNotifications")
                    .addField("group_admin_sub_specialty", String.class);
            schema.get("RealmNotifications")
                    .addField("doc_sub_speciality", String.class);
            schema.get("RealmCaseRoomInfo")
                    .addField("sub_speciality", String.class);

            oldVersion++;
        }
        if (oldVersion == 8) {
            schema.get("RealmBasicInfo")
                    .addField("user_salutation", String.class)
                    .addField("user_type_id", int.class)
                    .addField("feedCount", int.class)
                    .addField("likesCount", int.class)
                    .addField("shareCount", int.class)
                    .addField("commentsCount", int.class);
            schema.get("RealmBasicInfo").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setString("user_salutation", "Dr. ");
                    obj.setInt("user_type_id", 1);
                    obj.setInt("feedCount", 0);
                    obj.setInt("likesCount", 0);
                    obj.setInt("shareCount", 0);
                    obj.setInt("commentsCount", 0);
                }
            });
            schema.get("RealmMyContactsInfo")
                    .addField("user_salutation", String.class)
                    .addField("user_type_id", int.class);
            schema.get("RealmMyContactsInfo").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setString("user_salutation", "");
                    obj.setInt("user_type_id", 1);
                }
            });
            schema.get("RealmNotifications")
                    .addField("user_salutation", String.class)
                    .addField("user_type_id", int.class);
            schema.get("RealmNotifications").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setString("user_salutation", "");
                    obj.setInt("user_type_id", 1);
                }
            });
            schema.get("RealmCaseRoomNotifications")
                    .addField("user_salutation", String.class)
                    .addField("user_type_id", int.class);
            schema.get("RealmCaseRoomNotifications").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setString("user_salutation", "");
                    obj.setInt("user_type_id", 1);
                }
            });
            schema.get("RealmGroupNotifications")
                    .addField("user_salutation", String.class)
                    .addField("user_type_id", int.class);
            schema.get("RealmProfessionalMembership")
                    .addField("type", String.class)
                    .addField("award_name", String.class)
                    .addField("award_id", int.class)
                    .addField("award_year", long.class)
                    .addField("presented_at", String.class);
            schema.get("RealmGroupNotifications").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setString("user_salutation", "");
                    obj.setInt("user_type_id", 1);
                }
            });
            oldVersion++;
        }
        if (oldVersion == 9) {
            if (!schema.contains("RealmChannelsInfo")) {
                schema.create("RealmChannelsInfo")
                        .addField("channelsListKey", String.class)
                        .addField("channelsList", String.class);
            }
            oldVersion++;
        }
        if (oldVersion == 10) {
            RealmObjectSchema basicInfoSchema = schema.get("RealmBasicInfo");
            if (!basicInfoSchema.hasField("specificAsk")) {
                basicInfoSchema.addField("specificAsk", String.class);
            }
            if (!basicInfoSchema.hasField("linkedInPg")) {
                basicInfoSchema.addField("linkedInPg", String.class);
            }
            if (!basicInfoSchema.hasField("twitterPg")) {
                basicInfoSchema.addField("twitterPg", String.class);
            }
            if (!basicInfoSchema.hasField("instagramPg")) {
                basicInfoSchema.addField("instagramPg", String.class);
            }
            if (!basicInfoSchema.hasField("followersCount")) {
                basicInfoSchema.addField("followersCount", int.class);
            }
            if (!basicInfoSchema.hasField("followingCount")) {
                basicInfoSchema.addField("followingCount", int.class);
            }

            if (!schema.contains("RealmEventsInfo")) {
                schema.create("RealmEventsInfo")
                        .addField("eventId", int.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                        .addField("eventTitle", String.class)
                        .addField("location", String.class)
                        .addField("startDate", long.class)
                        .addField("endDate", long.class);
            }
            if (!schema.contains("RealmAreasOfInterestInfo")) {
                schema.create("RealmAreasOfInterestInfo")
                        .addField("interestId", int.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                        .addField("interestName", String.class);
            }
            RealmObjectSchema profInfoSchema = schema.get("RealmProfessionalInfo");
            if (!profInfoSchema.hasField("availableDays")) {
                profInfoSchema.addField("availableDays", String.class);
            }
            if (!profInfoSchema.hasField("workOptions")) {
                profInfoSchema.addField("workOptions", String.class);
            }
            if (!profInfoSchema.hasField("startTime")) {
                profInfoSchema.addField("startTime", long.class);
            }
            if (!profInfoSchema.hasField("endTime")) {
                profInfoSchema.addField("endTime", long.class);
            }
            oldVersion++;
        }

        if (oldVersion == 11) {
            if (!schema.contains("UserEvents")) {
                schema.create("UserEvents")
                        .addField("eventName", String.class)
                        .addField("eventData", String.class)
                        .addField("eventTime", long.class);
            }
            oldVersion++;
        }

        if (oldVersion == 12) {
            RealmObjectSchema basicInfoSchema = schema.get("RealmBasicInfo");
            if (!basicInfoSchema.hasField("docProfileURL")) {
                basicInfoSchema.addField("docProfileURL", String.class);
            }
            oldVersion++;
        }
        if (oldVersion == 13) {
            RealmObjectSchema basicInfoSchema = schema.get("RealmBasicInfo");
            if (!basicInfoSchema.hasField("docProfilePdfURL")) {
                basicInfoSchema.addField("docProfilePdfURL", String.class);
            }
            oldVersion++;
        }
        if (oldVersion == 14) {
            RealmObjectSchema basicInfoSchema = schema.get("RealmBasicInfo");
            if (!basicInfoSchema.hasField("userUUID")) {
                basicInfoSchema.addField("userUUID", String.class);
            }
            oldVersion++;
        }

        if (oldVersion == 15) {
            if (!schema.contains("RealmChannelFeedInfo")) {
                schema.create("RealmChannelFeedInfo")
                        .addField("feedChannelId", String.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                        .addField("feedId", int.class)
                        .addField("createdOrUpdatedTime", long.class)
                        .addField("channelId", int.class)
                        .addField("docId", int.class)
                        .addField("feedsJson", String.class);
            }

            if (!schema.contains("RealmChannelFeedsList")) {
                schema.create("RealmChannelFeedsList")
                        .addField("feed_Id", int.class, FieldAttribute.PRIMARY_KEY).addRealmListField("feedsList", schema.get("RealmChannelFeedInfo"));
            }
            oldVersion++;
        }

        if (oldVersion == 16) {
            if (!schema.contains("RealmNotificationInfo")) {
                schema.create("RealmNotificationInfo")
                        .addField("notificationID", String.class, FieldAttribute.PRIMARY_KEY, FieldAttribute.REQUIRED)
                        .addField("notifyData", String.class)
                        .addField("isAcknowledged", boolean.class)
                        .addField("readStatus", boolean.class);
            }

            if (!schema.contains("RealmNotificationSettingsInfo")) {
                schema.create("RealmNotificationSettingsInfo")
                        .addField("categoryId", int.class)
                        .addField("jsonData", String.class)
                        .addField("isEnabled", boolean.class);
            }
            RealmObjectSchema basicInfoSchema=schema.get("RealmBasicInfo");
            if(!basicInfoSchema.hasField("overAllExperience")){
                basicInfoSchema .addField("overAllExperience", int.class);
            }
            schema.get("RealmBasicInfo").transform(new RealmObjectSchema.Function() {
                @Override
                public void apply(DynamicRealmObject obj) {
                    obj.setLong("overAllExperience", 999);
                    Log.e("OldVersion","999");
                }
            });
            oldVersion++;
        }
        if(oldVersion == 17){
            RealmObjectSchema basicInfoSchema=schema.get("RealmMyContactsInfo");
            if(!basicInfoSchema.hasField("UUID")){
                basicInfoSchema .addField("UUID", String.class);
            }
            RealmObjectSchema RealmNotificationInfoSchema=schema.get("RealmNotificationInfo");
            if(!RealmNotificationInfoSchema.hasField("receivedTime")){
                RealmNotificationInfoSchema .addField("receivedTime", long.class);
            }
            oldVersion++;
        }
        if (oldVersion == 18) {
            if (!schema.contains("RealmAdSlotInfo")) {
                schema.create("RealmAdSlotInfo")
                        .addField("slot_id", int.class)
                        .addField("location", String.class)
                        .addField("dimensions", String.class)
                        .addField("ad_source", String.class)
                        .addField("occurance", int.class)
                        .addField("max_limit", int.class)
                        .addField("source_slot_id",String.class)
                        .addField("ad_location_type_id",int.class);
            }
            oldVersion++;
        }

        if(oldVersion == 19){
            RealmObjectSchema realmAdSlotInfoSchema=schema.get("RealmAdSlotInfo");
            if(!realmAdSlotInfoSchema.hasField("ad_slot_duration")){
                realmAdSlotInfoSchema .addField("ad_slot_duration", int.class);
            }
            oldVersion++;
        }
    }
}
