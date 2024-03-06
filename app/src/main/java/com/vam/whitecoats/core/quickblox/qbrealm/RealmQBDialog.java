package com.vam.whitecoats.core.quickblox.qbrealm;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by lokeshl on 9/28/2015.
 */
public class RealmQBDialog extends RealmObject implements Serializable{


    private int other_doc_id;
    private String last_msg;
    private long last_msg_time;
    private String dialog_id;
    private String dialog_type;
    private int unread_count;
    private String local_dialog_type;

    private String dialog_name;
    private String group_pic_path;
    private String other_doc_name;
    private int last_msg_sent_qb_id;
    private String group_roomjid;
    private String occupants_ids;
    private String group_pic_url;


    public int getLast_msg_sent_qb_id() {
        return last_msg_sent_qb_id;
    }

    public void setLast_msg_sent_qb_id(int last_msg_sent_qb_id) {
        this.last_msg_sent_qb_id = last_msg_sent_qb_id;
    }


    public String getOther_doc_name() {
        return other_doc_name;
    }

    public void setOther_doc_name(String other_doc_name) {
        this.other_doc_name = other_doc_name;
    }

    public String getGroup_roomjid() {
        return group_roomjid;
    }

    public void setGroup_roomjid(String group_roomjid) {
        this.group_roomjid = group_roomjid;
    }


    public String getDialog_name() {
        return dialog_name;
    }

    public void setDialog_name(String dialog_name) {
        this.dialog_name = dialog_name;
    }

    public String getGroup_pic_path() {
        return group_pic_path;
    }

    public void setGroup_pic_path(String group_pic_path) {
        this.group_pic_path = group_pic_path;
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

    public int getUnread_count() { return unread_count; }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getLocal_dialog_type() {
        return local_dialog_type;
    }

    public void setLocal_dialog_type(String local_dialog_type) {
        this.local_dialog_type = local_dialog_type;
    }

    public String getGroup_pic_url() {
        return group_pic_url;
    }

    public void setGroup_pic_url(String group_pic_url) {
        this.group_pic_url = group_pic_url;
    }
}
