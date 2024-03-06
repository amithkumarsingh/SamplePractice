package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by swathim on 01-10-2015.
 */
public class GroupNotifyInfo implements Serializable {


    private String group_notify_id;
    private String group_id;

    private String group_name;
    private String group_pic;
    private String group_pic_url;
    private long group_creation_time;
    private String group_admin_name;
    private String group_admin_pic;
    private int group_admin_Doc_id;
    private String group_admin_specialty;
    private String group_admin_sub_specialty;
    private String group_admin_workplace;
    private String group_admin_email;
    private String group_admin_phno;
    private String group_admin_email_vis;
    private String group_admin_phno_vis;
    private String group_admin_location;
    private String group_admin_qb_user_id;
    private String group_notification_type;
    private long group_notification_time;
    private String group_admin_pic_url;
    private String room_jid;
    private String user_salutation;
    private int user_type_id;



    public String getGroup_admin_sub_specialty() {
        return group_admin_sub_specialty;
    }

    public void setGroup_admin_sub_specialty(String group_admin_sub_specialty) {
        this.group_admin_sub_specialty = group_admin_sub_specialty;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_notify_id() {
        return group_notify_id;
    }

    public void setGroup_notify_id(String group_notify_id) {
        this.group_notify_id = group_notify_id;
    }

    public String getGroup_pic() {
        return group_pic;
    }

    public void setGroup_pic(String group_pic) {
        this.group_pic = group_pic;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public long getGroup_creation_time() {
        return group_creation_time;
    }

    public void setGroup_creation_time(long group_creation_time) {
        this.group_creation_time = group_creation_time;
    }

    public String getGroup_admin_name() {
        return group_admin_name;
    }

    public void setGroup_admin_name(String group_admin_name) {
        this.group_admin_name = group_admin_name;
    }

    public String getGroup_admin_specialty() {
        return group_admin_specialty;
    }

    public void setGroup_admin_specialty(String group_admin_specialty) {
        this.group_admin_specialty = group_admin_specialty;
    }

    public String getGroup_admin_pic() {
        return group_admin_pic;
    }

    public void setGroup_admin_pic(String group_admin_pic) {
        this.group_admin_pic = group_admin_pic;
    }

    public String getGroup_notification_type() {
        return group_notification_type;
    }

    public void setGroup_notification_type(String group_notification_type) {
        this.group_notification_type = group_notification_type;
    }

    public int getGroup_admin_Doc_id() {
        return group_admin_Doc_id;
    }

    public void setGroup_admin_Doc_id(int group_admin_Doc_id) {
        this.group_admin_Doc_id = group_admin_Doc_id;
    }


    public String getGroup_admin_email() {
        return group_admin_email;
    }

    public void setGroup_admin_email(String group_admin_email) {
        this.group_admin_email = group_admin_email;
    }

    public String getGroup_admin_phno() {
        return group_admin_phno;
    }

    public void setGroup_admin_phno(String group_admin_phno) {
        this.group_admin_phno = group_admin_phno;
    }

    public String getGroup_admin_email_vis() {
        return group_admin_email_vis;
    }

    public void setGroup_admin_email_vis(String group_admin_email_vis) {
        this.group_admin_email_vis = group_admin_email_vis;
    }

    public String getGroup_admin_phno_vis() {
        return group_admin_phno_vis;
    }

    public void setGroup_admin_phno_vis(String group_admin_phno_vis) {
        this.group_admin_phno_vis = group_admin_phno_vis;
    }

    public String getGroup_admin_location() {
        return group_admin_location;
    }

    public void setGroup_admin_location(String group_admin_location) {
        this.group_admin_location = group_admin_location;
    }

    public String getGroup_admin_qb_user_id() {
        return group_admin_qb_user_id;
    }

    public void setGroup_admin_qb_user_id(String group_admin_qb_user_id) {
        this.group_admin_qb_user_id = group_admin_qb_user_id;
    }

    public String getGroup_admin_workplace() {
        return group_admin_workplace;
    }

    public void setGroup_admin_workplace(String group_admin_workplace) {
        this.group_admin_workplace = group_admin_workplace;
    }

    public String getRoom_jid() {
        return room_jid;
    }

    public void setRoom_jid(String room_jid) {
        this.room_jid = room_jid;
    }

    public long getGroup_notification_time() {
        return group_notification_time;
    }

    public void setGroup_notification_time(long group_notification_time) {
        this.group_notification_time = group_notification_time;
    }

    public String getGroup_admin_pic_url() {
        return group_admin_pic_url;
    }

    public void setGroup_admin_pic_url(String group_admin_pic_url) {
        this.group_admin_pic_url = group_admin_pic_url;
    }

    public String getGroup_pic_url() {
        return group_pic_url;
    }

    public void setGroup_pic_url(String group_pic_url) {
        this.group_pic_url = group_pic_url;
    }

    public String getUser_salutation() {
        return user_salutation;
    }

    public void setUser_salutation(String user_salutation) {
        this.user_salutation = user_salutation;
    }

    public int getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(int user_type_id) {
        this.user_type_id = user_type_id;
    }
}
