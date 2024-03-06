package com.vam.whitecoats.core.models;

import com.vam.whitecoats.ui.interfaces.User;

import java.io.Serializable;

/**
 * Created by satyasarathim on 20-06-2016.
 */
public class Member implements Serializable, User {
    int doctorId;
    int qbUserId;
    int networkStatus;
    String contactEmail;
    String contactNumber;
    String speciality;
    String subspeciality;
    String fullName;
    String location;
    String workplace;
    String designation;
    String degrees;
    String profilePicName;
    String communityDesignation;
    String profile_pic_url;
    String userSalutation;
    int userTypeId;

    public String getPhno_vis() {
        return phno_vis;
    }

    public void setPhno_vis(String phno_vis) {
        this.phno_vis = phno_vis;
    }

    public String getEmail_vis() {
        return email_vis;
    }

    public void setEmail_vis(String email_vis) {
        this.email_vis = email_vis;
    }

    private String phno_vis;
    private String email_vis;

    public String getCommunityDesignation() {
        return communityDesignation;
    }

    public void setCommunityDesignation(String communityDesignation) {
        this.communityDesignation = communityDesignation;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getQbUserId() {
        return qbUserId;
    }

    public void setQbUserId(int qbUserId) {
        this.qbUserId = qbUserId;
    }

    public int getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(int networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getSubspeciality() {
        return subspeciality;
    }

    public void setSubspeciality(String speciality) {
        this.subspeciality = speciality;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getProfilePicName() {
        return profilePicName;
    }

    @Override
    public String getProfilePicUrl() {
        return profile_pic_url;
    }

    public String getUserSalutation() {
        return userSalutation;
    }


    public int getUserTypeId() {
        return userTypeId;
    }



    public void setUserSalutation(String mUserSalutation) {
        this.userSalutation=mUserSalutation;
    }


    public void setUserTypeId(int mUserTypeId) {
        this.userTypeId=mUserTypeId;
    }

    public void setProfilePicUrl(String mProfilePicUrl) {
        this.profile_pic_url = mProfilePicUrl;
    }

    public void setProfilePicName(String profilePicName) {
        this.profilePicName = profilePicName;
    }


}
