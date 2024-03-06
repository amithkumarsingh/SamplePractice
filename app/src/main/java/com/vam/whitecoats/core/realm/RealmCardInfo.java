package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by swathim on 11-09-2015.
 */
public class RealmCardInfo extends RealmObject{

    @Required
    @PrimaryKey
    private String qb_user_id;
    private String full_name;
    private String splty;
    private String notify_pic;
    private String phone;
    private String location;
    private String email;
    private String workplace;
    private String degrees;


    public String getQb_user_id() {
        return qb_user_id;
    }

    public void setQb_user_id(String qb_user_id) {
        this.qb_user_id = qb_user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSplty() {
        return splty;
    }

    public void setSplty(String splty) {
        this.splty = splty;
    }

    public String getNotify_pic() {
        return notify_pic;
    }

    public void setNotify_pic(String notify_pic) {
        this.notify_pic = notify_pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

}
