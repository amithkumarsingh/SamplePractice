package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by tejaswini on 03-11-2015.
 */
public class CaseRoomInfo implements Serializable {
    private String caseroom_summary_id;
    private  int docid;
    private String title;
    private  String speciality;
    private  String sub_speciality;
    private String query;
    private int status;
    private long createddate;
    private long modifieddate;


    public String getSub_speciality() {
        return sub_speciality;
    }

    public void setSub_speciality(String sub_speciality) {
        this.sub_speciality = sub_speciality;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public long getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(long last_message_time) {
        this.last_message_time = last_message_time;
    }

    private String last_message;
    private long last_message_time;



    public String getCaseroom_dialog_id() {
        return caseroom_dialog_id;
    }

    public void setCaseroom_dialog_id(String caseroom_dialog_id) {
        this.caseroom_dialog_id = caseroom_dialog_id;
    }

    private String caseroom_dialog_id;

    public boolean isP_details() {
        return p_details;
    }

    public void setP_details(boolean p_details) {
        this.p_details = p_details;
    }

    private boolean p_details;

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    private String attachments;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCaseroom_summary_id() {
        return caseroom_summary_id;
    }

    public void setCaseroom_summary_id(String caseroom_summary_id) {
        this.caseroom_summary_id = caseroom_summary_id;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateddate() {
        return createddate;
    }

    public void setCreateddate(long createddate) {
        this.createddate = createddate;
    }

    public long getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(long modifieddate) {
        this.modifieddate = modifieddate;
    }


}
