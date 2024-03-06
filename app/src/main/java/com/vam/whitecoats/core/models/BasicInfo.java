package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by lokeshl on 5/19/2015.
 */
public class BasicInfo implements Serializable {

    private String fname;
    private String lname;
    private String email;
    private String psswd;
    private String splty;
    private String subSpeciality;
    private String phone_num;
    private String about_me;
    private String profile_pic_path;
    private String reg_card_path;
    private String pic_name;
    private String website;
    private String blog_page;
    private String fb_page;
    private int phone_num_visibility;
    private String qb_login;
    private boolean email_verify;
    private boolean phone_verify;
    private String pic_url;
    private int email_visibility;
    private int doc_id, tot_contacts, tot_groups, tot_caserooms;
    private boolean rememberMe;
    private String qb_hidden_dialog_id;
    private int qb_userid;
    private int userType;
    private String userSalutation;
    private int feedCount;
    private int likeCount;
    private int shareCount;
    private int commentCount;
    private int followersCount;
    private int followingCount;
    private String specificAsk;
    private String linkedInPg;
    private String twitterPg;
    private String instagramPg;
    private String docProfileURL;
    private String docProfilePdfURL;
    private String userUUID;

    public int getConnect_status() {
        return connect_status;
    }

    public void setConnect_status(int connect_status) {
        this.connect_status = connect_status;
    }

    private int connect_status;

    private int overAllExperience;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getSpecificAsk() {
        return specificAsk;
    }

    public void setSpecificAsk(String specificAsk) {
        this.specificAsk = specificAsk;
    }

    public String getLinkedInPg() {
        return linkedInPg;
    }

    public void setLinkedInPg(String linkedInPg) {
        this.linkedInPg = linkedInPg;
    }

    public String getTwitterPg() {
        return twitterPg;
    }

    public void setTwitterPg(String twitterPg) {
        this.twitterPg = twitterPg;
    }

    public String getInstagramPg() {
        return instagramPg;
    }

    public void setInstagramPg(String instagramPg) {
        this.instagramPg = instagramPg;
    }

    public boolean isEmail_verify() {
        return email_verify;
    }

    public boolean isPhone_verify() {
        return phone_verify;
    }

    public int getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(int feedCount) {
        this.feedCount = feedCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserSalutation() {
        return userSalutation;
    }

    public void setUserSalutation(String userSalutation) {
        this.userSalutation = userSalutation;
    }

    public String getSubSpeciality() {
        return subSpeciality;
    }

    public void setSubSpeciality(String subSpeciality) {
        this.subSpeciality = subSpeciality;
    }

    public String getQb_hidden_dialog_id() {
        return qb_hidden_dialog_id;
    }

    public void setQb_hidden_dialog_id(String qb_hidden_dialog_id) {
        this.qb_hidden_dialog_id = qb_hidden_dialog_id;
    }


    public int getQb_userid() {
        return qb_userid;
    }

    public void setQb_userid(int qb_userid) {
        this.qb_userid = qb_userid;
    }


    public int getEmail_visibility() {
        return email_visibility;
    }

    public void setEmail_visibility(int email_visibility) {
        this.email_visibility = email_visibility;
    }


    public String getEmailvalidation() {
        return emailvalidation;
    }

    public void setEmailvalidation(String emailvalidation) {
        this.emailvalidation = emailvalidation;
    }

    private String emailvalidation;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getQb_login() {
        return qb_login;
    }

    public void setQb_login(String qb_login) {
        this.qb_login = qb_login;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsswd() {
        return psswd;
    }

    public void setPsswd(String psswd) {
        this.psswd = psswd;
    }

    public String getSplty() {
        return splty;
    }

    public void setSplty(String splty) {
        this.splty = splty;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAbout_me() {
        return about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public String getProfile_pic_path() {
        return profile_pic_path;
    }

    public void setProfile_pic_path(String profile_pic_path) {
        this.profile_pic_path = profile_pic_path;
    }

    public String getReg_card_path() {
        return reg_card_path;
    }

    public void setReg_card_path(String reg_card_path) {
        this.reg_card_path = reg_card_path;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBlog_page() {
        return blog_page;
    }

    public void setBlog_page(String blog_page) {
        this.blog_page = blog_page;
    }

    public String getFb_page() {
        return fb_page;
    }

    public void setFb_page(String fb_page) {
        this.fb_page = fb_page;
    }

    public int getPhone_num_visibility() {
        return phone_num_visibility;
    }

    public void setPhone_num_visibility(int phone_num_visibility) {
        this.phone_num_visibility = phone_num_visibility;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public int getTot_contacts() {
        return tot_contacts;
    }

    public void setTot_contacts(int tot_contacts) {
        this.tot_contacts = tot_contacts;
    }

    public int getTot_groups() {
        return tot_groups;
    }

    public void setTot_groups(int tot_groups) {
        this.tot_groups = tot_groups;
    }

    public int getTot_caserooms() {
        return tot_caserooms;
    }

    public void setTot_caserooms(int tot_caserooms) {
        this.tot_caserooms = tot_caserooms;
    }

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public boolean getEmail_verify() {
        return email_verify;
    }

    public void setEmail_verify(boolean email_verify) {
        this.email_verify = email_verify;
    }

    public boolean getPhone_verify() {
        return phone_verify;
    }

    public void setPhone_verify(boolean phone_verify) {
        this.phone_verify = phone_verify;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getDocProfileURL() {
        return docProfileURL;
    }

    public void setDocProfileURL(String docProfileURL) {
        this.docProfileURL = docProfileURL;
    }

    public String getDocProfilePdfURL() {
        return docProfilePdfURL;
    }

    public void setDocProfilePdfURL(String docProfilePdfURL) {
        this.docProfilePdfURL = docProfilePdfURL;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public int getOverAllExperience() {
        return overAllExperience;
    }

    public void setOverAllExperience(int overAllExperience) {
        this.overAllExperience = overAllExperience;
    }
}
