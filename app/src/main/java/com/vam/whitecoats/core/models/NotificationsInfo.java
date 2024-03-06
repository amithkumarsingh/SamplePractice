package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by swathim on 23-10-2015.
 */
public class NotificationsInfo implements Serializable {


    private String notification_id;
    private String notification_type;

    private String doc_id;
    private String doc_pic;
    private String doc_name;
    private String doc_speciality;
    private String doc_workplace;
    private String doc_email;
    private String doc_phno;
    private String doc_location;
    private String doc_email_vis;
    private String doc_phno_vis;
    private String qb_user_id;

    private String message;


    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
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

    public String getQb_user_id() {
        return qb_user_id;
    }

    public void setQb_user_id(String qb_user_id) {
        this.qb_user_id = qb_user_id;
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


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
