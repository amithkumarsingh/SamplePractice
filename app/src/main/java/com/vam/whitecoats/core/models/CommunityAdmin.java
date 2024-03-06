package com.vam.whitecoats.core.models;

import com.vam.whitecoats.ui.interfaces.User;

import java.io.Serializable;

/**
 * Created by pardhasaradhid on 6/13/2016.
 */
public class CommunityAdmin implements Serializable, User {

    public int doctorId;
    public String contactEmail;
    public String contactNumber;
    public int qbUserId;
    public String fullName;
    public String location;
    public String workplace;
    public String speciality;
    public String sub_speciality;
    public String designation;
    public String degrees;
    public String profilePicName;
    String profile_pic_url;
    public String communityDesignation;
    public int networkStatus;
    String userSalutation;
    int userTypeId;


    public String getSub_speciality() {
        return sub_speciality;
    }

    public void setSub_speciality(String sub_speciality) {
        this.sub_speciality = sub_speciality;
    }


    public int getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(int networkStatus) {
        this.networkStatus = networkStatus;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public int getQbUserId() {
        return qbUserId;
    }

    public void setQbUserId(int qbUserId) {
        this.qbUserId = qbUserId;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
    public void setProfilePicUrl(String mProfilePicUrl) {
        this.profile_pic_url = mProfilePicUrl;
    }

    public void setProfilePicName(String profilePicName) {
        this.profilePicName = profilePicName;
    }

    public String getCommunityDesignation() {
        return communityDesignation;
    }

    public void setCommunityDesignation(String communityDesignation) {
        this.communityDesignation = communityDesignation;
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
}
