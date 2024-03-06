package com.vam.whitecoats.core.models;

import java.io.Serializable;
import java.util.Comparator;

import io.realm.annotations.PrimaryKey;

/**
 * Created by swathim on 6/25/2015.
 */
public class ContactsInfo implements Serializable {//,Comparable<ContactsInfo>{

    @PrimaryKey
    private int doc_id;

    private String name;
    private String speciality;
    private String subSpeciality;
    private String degree;
    private String workplace;
    private String location;
    private String networkStatus;
    private String email;

    private String email_vis;
    private String phno;
    private String phno_vis;
    private int qb_userid;
    private String designation;
    private String Community_designation;
    private String pic_data;
    private boolean readStatus;
    private String notification_id;
    private long time;
    private String message;
    private String str_accepted;
    private String notification_type;
    private String pic_name;            //  pic name used in searchcontacts
    private String profile_pic_name;
    private String profile_pic_original_url;
    private String profile_pic_small_url;
    private String cnt_email;            //  pic name used in searchcontacts
    private String cnt_num;            //  pic name used in searchcontacts
    private String full_name;
    private int id;
    private String follow_status;
    private boolean cardPopupNotNeeded;

    /**
     * Group Info for populating in chat messages tab
     **/
    private String group_id;
    private String group_name;
    private String group_pic_name;
    private String last_message;
    private String last_message_time;
    private String opponent_ids;
    private String pic_url;
    private String specialist;
    private String userSalutation;
    private int userTypeId;

    private int feedId;
    private int feedTypeId;
    private int channelId;
    private String title;
    private String shortDescription;
    private String smallImageUrl;
    private String attachmentType;

    private  String UUID;



    public String getSubSpeciality() {
        return subSpeciality;
    }

    public void setSubSpeciality(String subSpeciality) {
        this.subSpeciality = subSpeciality;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail_vis() {
        return email_vis;
    }

    public void setEmail_vis(String email_vis) {
        this.email_vis = email_vis;
    }

    public String getPhno_vis() {
        return phno_vis;
    }

    public void setPhno_vis(String phno_vis) {
        this.phno_vis = phno_vis;
    }

    public int getQb_userid() {
        return qb_userid;
    }

    public void setQb_userid(int qb_userid) {
        this.qb_userid = qb_userid;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(String networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getStr_accepted() {
        return str_accepted;
    }

    public void setStr_accepted(String str_accepted) {
        this.str_accepted = str_accepted;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public static Comparator<ContactsInfo> COMPARE_BY_NAME = new Comparator<ContactsInfo>() {
        public int compare(ContactsInfo one, ContactsInfo other) {
            return one.name.compareTo(other.name);
        }
    };

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_pic_name() {
        return group_pic_name;
    }

    public void setGroup_pic_name(String group_pic_name) {
        this.group_pic_name = group_pic_name;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(String last_message_time) {
        this.last_message_time = last_message_time;
    }

    public String getOpponent_ids() {
        return opponent_ids;
    }

    public void setOpponent_ids(String opponent_ids) {
        this.opponent_ids = opponent_ids;
    }

    public String getPic_data() {
        return pic_data;
    }

    public void setPic_data(String pic_data) {
        this.pic_data = pic_data;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getCommunity_designation() {
        return Community_designation;
    }

    public void setCommunity_designation(String community_designation) {
        Community_designation = community_designation;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getCnt_email() {
        return cnt_email;
    }

    public void setCnt_email(String cnt_email) {
        this.cnt_email = cnt_email;
    }

    public String getProfile_pic_name() {
        return profile_pic_name;
    }

    public void setProfile_pic_name(String profile_pic_name) {
        this.profile_pic_name = profile_pic_name;
    }

    public String getProfile_pic_original_url() {
        return profile_pic_original_url;
    }

    public void setProfile_pic_original_url(String profile_pic_original_url) {
        this.profile_pic_original_url = profile_pic_original_url;
    }

    public String getProfile_pic_small_url() {
        return profile_pic_small_url;
    }

    public void setProfile_pic_small_url(String profile_pic_small_url) {
        this.profile_pic_small_url = profile_pic_small_url;
    }

    public String getCnt_num() {
        return cnt_num;
    }

    public void setCnt_num(String cnt_num) {
        this.cnt_num = cnt_num;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getUserSalutation() {
        return userSalutation;
    }

    public void setUserSalutation(String userSalutation) {
        this.userSalutation = userSalutation;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
    }

    public int getFeedTypeId() {
        return feedTypeId;
    }

    public void setFeedTypeId(int feedTypeId) {
        this.feedTypeId = feedTypeId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollow_status() {
        return follow_status;
    }

    public void setFollow_status(String follow_status) {
        this.follow_status = follow_status;
    }

    public boolean isCardPopupNotNeeded() {
        return cardPopupNotNeeded;
    }

    public void setCardPopupNotNeeded(boolean cardPopupNotNeeded) {
        this.cardPopupNotNeeded = cardPopupNotNeeded;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
