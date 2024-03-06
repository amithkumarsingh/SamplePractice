package com.vam.whitecoats.core.quickblox.qbrealm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 30-10-2015.
 */
public class RealmQBGroupDialog extends RealmObject implements Serializable{

    @Required
    private String dialog_id;
    private String dialog_title;
    private String dialog_pic_name;
    private String dialog_pic_url;
    private String last_msg_sent_qb_id;
    private String local_dialog_type;
    private String last_msg;
    private String last_msg_time;
    private String dialog_room_jid;
    private int opponent_doc_id;
    private long dialog_creation_date;
    private long member_added_date;


    public long getMember_added_date() {
        return member_added_date;
    }

    public void setMember_added_date(long member_added_date) {
        this.member_added_date = member_added_date;
    }

    public String getDialog_id() {
        return dialog_id;
    }

    public void setDialog_id(String dialog_id) {
        this.dialog_id = dialog_id;
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

    public int getOpponent_doc_id() {
        return opponent_doc_id;
    }

    public void setOpponent_doc_id(int opponent_doc_id) {
        this.opponent_doc_id = opponent_doc_id;
    }

    public String getLast_msg_sent_qb_id() {
        return last_msg_sent_qb_id;
    }

    public void setLast_msg_sent_qb_id(String last_msg_sent_qb_id) {
        this.last_msg_sent_qb_id = last_msg_sent_qb_id;
    }

    public long getDialog_creation_date() {
        return dialog_creation_date;
    }

    public void setDialog_creation_date(long dialog_creation_date) {
        this.dialog_creation_date = dialog_creation_date;
    }

    public String getLast_msg_time() {
        return last_msg_time;
    }

    public void setLast_msg_time(String last_msg_time) {
        this.last_msg_time = last_msg_time;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }

    public String getDialog_room_jid() {
        return dialog_room_jid;
    }

    public void setDialog_room_jid(String dialog_room_jid) {
        this.dialog_room_jid = dialog_room_jid;
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
}
