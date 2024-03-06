package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class RealmMessages extends RealmObject {
    @Required
    @PrimaryKey
    private String id;
    private String dialogId;
    private String message;
    private long time;


    private int other_doc_id;
    private String message_type;
    private String att_type;
    private int message_satus;
    private int att_status;
    private String att_qbid;
    private String attachUrl;
    private boolean isSync;
    private int senderId;
    private int msg_type;

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public String getAtt_qbid() {
        return att_qbid;
    }

    public void setAtt_qbid(String att_qbid) {
        this.att_qbid = att_qbid;
    }

    public int getAtt_status() {
        return att_status;
    }

    public void setAtt_status(int att_status) {
        this.att_status = att_status;
    }



    /*private boolean isRead;
    private boolean isDelivered;
    private String atttContentType;
*/








    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean isSync) {
        this.isSync = isSync;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }
    public int getOther_doc_id() {
        return other_doc_id;
    }

    public void setOther_doc_id(int other_doc_id) {
        this.other_doc_id = other_doc_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getAtt_type() {
        return att_type;
    }

    public void setAtt_type(String att_type) {
        this.att_type = att_type;
    }

    public int getMessage_satus() {
        return message_satus;
    }

    public void setMessage_satus(int message_satus) {
        this.message_satus = message_satus;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }
    /*public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
    public String getAtttContentType() {
        return atttContentType;
    }

    public void setAtttContentType(String atttContentType) {
        this.atttContentType = atttContentType;
    }*/
      public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }


}