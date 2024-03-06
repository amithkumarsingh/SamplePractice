package com.vam.whitecoats.core.quickblox.qbmodels;

import java.io.Serializable;

/**
 * Created by lokeshl on 9/28/2015.
 */
public class QBDialogModel implements Serializable {

    private int other_doc_id;
    private String last_msg;
    private long last_msg_time;
    private String dialog_id;
    private String dialog_type;
    private int unread_count;

    private String local_dialog_type;

    private String occupants_ids;


    private String dialog_title;
    private String dialog_pic_name;
    private String dialog_pic_url;
    private int last_msg_sent_qb_id;
    private int opponent_doc_id;
    private long dialog_creation_date;
    private String dialog_roomjid;
    private long member_added_date;


    public long getMember_added_date() {
        return member_added_date;
    }

    public void setMember_added_date(long member_added_date) {
        this.member_added_date = member_added_date;
    }

    public String getDialog_roomjid() {
        return dialog_roomjid;
    }

    public void setDialog_roomjid(String dialog_roomjid) {
        this.dialog_roomjid = dialog_roomjid;
    }

    public String getOccupants_ids() {
        return occupants_ids;
    }

    public void setOccupants_ids(String occupants_ids) {
        this.occupants_ids = occupants_ids;
    }


    public int getOther_doc_id() {
        return other_doc_id;
    }

    public void setOther_doc_id(int other_doc_id) {
        this.other_doc_id = other_doc_id;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public long getLast_msg_time() {
        return last_msg_time;
    }

    public void setLast_msg_time(long last_msg_time) {
        this.last_msg_time = last_msg_time;
    }

    public String getDialog_id() {
        return dialog_id;
    }

    public void setDialog_id(String dialog_id) {
        this.dialog_id = dialog_id;
    }

    public String getDialog_type() {
        return dialog_type;
    }

    public void setDialog_type(String dialog_type) {
        this.dialog_type = dialog_type;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getDialog_title() {
        return dialog_title;
    }

    public void setDialog_title(String dialog_title) {
        this.dialog_title = dialog_title;
    }

    public String getDialog_pic_name() {
        return dialog_pic_name;
    }

    public void setDialog_pic_name(String dialog_pic_name) {
        this.dialog_pic_name = dialog_pic_name;
    }

    public int getLast_msg_sent_qb_id() {
        return last_msg_sent_qb_id;
    }

    public void setLast_msg_sent_qb_id(int last_msg_sent_qb_id) {
        this.last_msg_sent_qb_id = last_msg_sent_qb_id;
    }

    public int getOpponent_doc_id() {
        return opponent_doc_id;
    }

    public void setOpponent_doc_id(int opponent_doc_id) {
        this.opponent_doc_id = opponent_doc_id;
    }

    public long getDialog_creation_date() {
        return dialog_creation_date;
    }

    public void setDialog_creation_date(long dialog_creation_date) {
        this.dialog_creation_date = dialog_creation_date;
    }

    public String getLocal_dialog_type() {
        return local_dialog_type;
    }

    public void setLocal_dialog_type(String local_dialog_type) {
        this.local_dialog_type = local_dialog_type;
    }

    public String getDialog_pic_url() {
        return dialog_pic_url;
    }

    public void setDialog_pic_url(String dialog_pic_url) {
        this.dialog_pic_url = dialog_pic_url;
    }



    @Override
    public String toString() {
        return "QBDialogModel{" +
                "other_doc_id=" + other_doc_id +
                ", last_msg='" + last_msg + '\'' +
                ", last_msg_time=" + last_msg_time +
                ", dialog_id='" + dialog_id + '\'' +
                ", dialog_type='" + dialog_type + '\'' +
                ", unread_count=" + unread_count +
                ", local_dialog_type='" + local_dialog_type + '\'' +
                ", occupants_ids='" + occupants_ids + '\'' +
                ", dialog_title='" + dialog_title + '\'' +
                ", dialog_pic_name='" + dialog_pic_name + '\'' +
                ", dialog_pic_url='" + dialog_pic_url + '\'' +
                ", last_msg_sent_qb_id=" + last_msg_sent_qb_id +
                ", opponent_doc_id=" + opponent_doc_id +
                ", dialog_creation_date=" + dialog_creation_date +
                ", dialog_roomjid='" + dialog_roomjid + '\'' +
                ", member_added_date=" + member_added_date +
                '}';
    }
}
