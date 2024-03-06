package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by tejaswini on 03-11-2015.
 */
public class RealmCaseRoomAttachmentsInfo extends RealmObject {
    @Required
    private String caseroom_summary_id;
    private String attachname;
    private int attachuploadstatus;
    private String qb_attach_id;

    public String getQb_attach_id() {
        return qb_attach_id;
    }

    public void setQb_attach_id(String qb_attach_id) {
        this.qb_attach_id = qb_attach_id;
    }


    public String getAttachname() {
        return attachname;
    }

    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    public String getCaseroom_summary_id() {
        return caseroom_summary_id;
    }

    public void setCaseroom_summary_id(String caseroom_summary_id) {
        this.caseroom_summary_id = caseroom_summary_id;
    }

    public int getAttachuploadstatus() {
        return attachuploadstatus;
    }

    public void setAttachuploadstatus(int attachuploadstatus) {
        this.attachuploadstatus = attachuploadstatus;
    }



}