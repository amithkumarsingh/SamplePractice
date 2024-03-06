package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by lokeshl on 5/1/2015.
 */
public class RealmNotifications extends RealmObject {

    @Required
    @PrimaryKey
    private String notification_id;
    private String notification_type;
    private int ackStatus;
    private boolean readStatus;

    private int doc_id;
    private String doc_pic;
    private String doc_pic_url;
    private String doc_name;
    private String doc_speciality;
    private String doc_sub_speciality;
    private String doc_workplace;
    private String doc_email;
    private String doc_phno;
    private String doc_location;
    private String doc_email_vis;
    private String doc_phno_vis;
    private int doc_qb_user_id;
    private long time;
    private String message;
    private int count_status;
    private String user_salutation;
    private int user_type_id;


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
    public String getDoc_sub_speciality() {
        return doc_sub_speciality;
    }

    public void setDoc_sub_speciality(String doc_sub_speciality) {
        this.doc_sub_speciality = doc_sub_speciality;
    }

    public String getDoc_pic() {
        return doc_pic;
    }

    public void setDoc_pic(String doc_pic) {
        this.doc_pic = doc_pic;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_speciality() {
        return doc_speciality;
    }

    public void setDoc_speciality(String doc_speciality) {
        this.doc_speciality = doc_speciality;
    }

    public String getDoc_workplace() {
        return doc_workplace;
    }

    public void setDoc_workplace(String doc_workplace) {
        this.doc_workplace = doc_workplace;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public String getDoc_phno() {
        return doc_phno;
    }

    public void setDoc_phno(String doc_phno) {
        this.doc_phno = doc_phno;
    }

    public String getDoc_location() {
        return doc_location;
    }

    public void setDoc_location(String doc_location) {
        this.doc_location = doc_location;
    }

    public String getDoc_email_vis() {
        return doc_email_vis;
    }

    public void setDoc_email_vis(String doc_email_vis) {
        this.doc_email_vis = doc_email_vis;
    }

    public String getDoc_phno_vis() {
        return doc_phno_vis;
    }

    public void setDoc_phno_vis(String doc_phno_vis) {
        this.doc_phno_vis = doc_phno_vis;
    }



    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    public int getAckStatus() {
        return ackStatus;
    }

    public void setAckStatus(int ackStatus) {
        this.ackStatus = ackStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public int getDoc_qb_user_id() {
        return doc_qb_user_id;
    }

    public void setDoc_qb_user_id(int doc_qb_user_id) {
        this.doc_qb_user_id = doc_qb_user_id;
    }
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCount_status() {
        return count_status;
    }

    public void setCount_status(int count_status) {
        this.count_status = count_status;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getDoc_pic_url() {
        return doc_pic_url;
    }

    public void setDoc_pic_url(String doc_pic_url) {
        this.doc_pic_url = doc_pic_url;
    }
}
