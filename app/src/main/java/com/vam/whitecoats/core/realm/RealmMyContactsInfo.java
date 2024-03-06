package com.vam.whitecoats.core.realm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 6/25/2015.
 */
public class RealmMyContactsInfo extends RealmObject implements Serializable{



    @Required
    private Integer doc_id;


    private String name;
    private String speciality;
    private String subspeciality;
    private String pic_name;
    private String degree;
    private String workplace;
    private String location;
    private String networkStatus;
    private String email;
    private String email_vis;
    private String phno;
    private String phno_vis;
    private String pic_url;
    private String user_salutation;
    private int user_type_id;
    private String UUID;


    public String getSubspeciality() {
        return subspeciality;
    }

    public void setSubspeciality(String subspeciality) {
        this.subspeciality = subspeciality;
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

    private int qb_userid;


    public Integer getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(Integer doc_id) {
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

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
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


    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
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

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
