package com.vam.whitecoats.core.quickblox.qbmodels;

import java.io.Serializable;

public class MessageModel implements Serializable {

    private String dialogId;
    private String message;
    private long time;

    private int other_doc_id;
    //private String message_type;
    private String att_type;
    private int message_satus;
    /** attachement path**/
    private String attachUrl;
    private boolean isSync;
    private int senderId;

    private int msg_type;

    public int getAtt_status() {
        return att_status;
    }

    public void setAtt_status(int att_status) {
        this.att_status = att_status;
    }

    public String getAtt_qbid() {
        return att_qbid;
    }

    public void setAtt_qbid(String att_qbid) {
        this.att_qbid = att_qbid;
    }

    private int att_status;
    private String att_qbid;


    /*private boolean isRead;
    private boolean isDelivered;
    private String atttContentType;
*/

    /*attachment_type, int default(0) / image(1) / video(2) / pdf(3) / word(4)
    msg_status, int  notsent(0) / delivered(1) / read(2)*/

    /** Message ID **/
    private String id;




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

   /* public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }*/

    public String getAtt_type() {
        return att_type;
    }

    public void setAtt_type(String attt_type) {
        this.att_type = attt_type;
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

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

}