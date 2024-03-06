package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by swathim on 01-12-2015.
 */
public class RealmCaseRoomNotifications extends RealmObject {
    @Required
    @PrimaryKey
    private String caseroom_notification_id;

    /** this is nothing but group id **/
    private String caseroom_id;
    private String caseroom_summary_id;
    private long caseroom_group_created_date;
    private String caseroom_group_xmpp_jid;
    private String case_heading;
    private String case_speciality;
    private String case_sub_speciality;
    private long time_received;
    private int doc_id;
    private String doc_qb_user_id;
    private String doc_name;
    private String doc_speciality;
    private String subSpeciality;
    private String doc_workplace;
    private String doc_location;
    private String doc_cnt_email;
    private String doc_cnt_num;
    private String doc_email_vis;
    private String doc_phno_vis;
    private String doc_pic_name;
    private String doc_pic_url;
    private String caseroom_notify_type;
    private int caseroom_ack_status;
    private int count_status;
    private String user_salutation;
    private int user_type_id;

    public String getCase_sub_speciality() {
        return case_sub_speciality;
    }

    public void setCase_sub_speciality(String case_sub_speciality) {
        this.case_sub_speciality = case_sub_speciality;
    }

    public String getSubSpeciality() {
        return subSpeciality;
    }

    public void setSubSpeciality(String subSpeciality) {
        this.subSpeciality = subSpeciality;
    }



    public int getCaseroom_ack_status() {
        return caseroom_ack_status;
    }

    public void setCaseroom_ack_status(int caseroom_ack_status) {
        this.caseroom_ack_status = caseroom_ack_status;
    }

    public String getCaseroom_notify_type() {
        return caseroom_notify_type;
    }

    public void setCaseroom_notify_type(String caseroom_notify_type) {
        this.caseroom_notify_type = caseroom_notify_type;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_qb_user_id() {
        return doc_qb_user_id;
    }

    public void setDoc_qb_user_id(String doc_qb_user_id) {
        this.doc_qb_user_id = doc_qb_user_id;
    }

    public String getDoc_workplace() {
        return doc_workplace;
    }

    public void setDoc_workplace(String doc_workplace) {
        this.doc_workplace = doc_workplace;
    }

    public String getDoc_location() {
        return doc_location;
    }

    public void setDoc_location(String doc_location) {
        this.doc_location = doc_location;
    }

    public String getDoc_cnt_email() {
        return doc_cnt_email;
    }

    public void setDoc_cnt_email(String doc_cnt_email) {
        this.doc_cnt_email = doc_cnt_email;
    }

    public String getDoc_cnt_num() {
        return doc_cnt_num;
    }

    public void setDoc_cnt_num(String doc_cnt_num) {
        this.doc_cnt_num = doc_cnt_num;
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

    public String getCaseroom_notification_id() {
        return caseroom_notification_id;
    }

    public void setCaseroom_notification_id(String caseroom_notification_id) {
        this.caseroom_notification_id = caseroom_notification_id;
    }

    public String getCaseroom_id() {
        return caseroom_id;
    }

    public void setCaseroom_id(String caseroom_id) {
        this.caseroom_id = caseroom_id;
    }

    public long getCaseroom_group_created_date() {
        return caseroom_group_created_date;
    }

    public void setCaseroom_group_created_date(long caseroom_group_created_date) {
        this.caseroom_group_created_date = caseroom_group_created_date;
    }

    public String getCaseroom_group_xmpp_jid() {
        return caseroom_group_xmpp_jid;
    }

    public void setCaseroom_group_xmpp_jid(String caseroom_group_xmpp_jid) {
        this.caseroom_group_xmpp_jid = caseroom_group_xmpp_jid;
    }

    public String getCase_heading() {
        return case_heading;
    }

    public void setCase_heading(String case_heading) {
        this.case_heading = case_heading;
    }

    public String getCase_speciality() {
        return case_speciality;
    }

    public void setCase_speciality(String case_speciality) {
        this.case_speciality = case_speciality;
    }

    public long getTime_received() {
        return time_received;
    }

    public void setTime_received(long time_received) {
        this.time_received = time_received;
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

    public String getDoc_pic_name() {
        return doc_pic_name;
    }

    public void setDoc_pic_name(String doc_pic_name) {
        this.doc_pic_name = doc_pic_name;
    }

    public String getCaseroom_summary_id() {
        return caseroom_summary_id;
    }

    public void setCaseroom_summary_id(String caseroom_summary_id) {
        this.caseroom_summary_id = caseroom_summary_id;
    }

    public int getCount_status() {
        return count_status;
    }

    public void setCount_status(int count_status) {
        this.count_status = count_status;
    }

    public String getDoc_pic_url() {
        return doc_pic_url;
    }

    public void setDoc_pic_url(String doc_pic_url) {
        this.doc_pic_url = doc_pic_url;
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
