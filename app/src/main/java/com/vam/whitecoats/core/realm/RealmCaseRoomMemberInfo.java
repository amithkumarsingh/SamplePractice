package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by tejaswini on 03-11-2015.
 */
public class RealmCaseRoomMemberInfo extends RealmObject {
    @Required
    private String caseroomid;
    private int mem_id;
    private int inviteresponse;
    private boolean isadmin;
    private long createddate;
    private long modifieddate;


    public int getInviteresponse() {
        return inviteresponse;
    }

    public void setInviteresponse(int inviteresponse) {
        this.inviteresponse = inviteresponse;
    }

    public String getCaseroomid() {
        return caseroomid;
    }

    public void setCaseroomid(String caseroomid) {
        this.caseroomid = caseroomid;
    }

    public int getMem_id() {
        return mem_id;
    }

    public void setMem_id(int mem_id) {
        this.mem_id = mem_id;
    }

    public boolean isadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
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

