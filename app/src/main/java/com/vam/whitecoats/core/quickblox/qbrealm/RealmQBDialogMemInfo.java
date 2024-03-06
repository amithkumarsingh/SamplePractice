package com.vam.whitecoats.core.quickblox.qbrealm;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 31-10-2015.
 */
public class RealmQBDialogMemInfo extends RealmObject implements Serializable {

    @Required
    private String dialog_id;
    private int doc_id;
    private int invite_response;
    private boolean is_admin;


    public String getDialog_id() {
        return dialog_id;
    }

    public void setDialog_id(String dialog_id) {
        this.dialog_id = dialog_id;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public int getInvite_response() {
        return invite_response;
    }

    public void setInvite_response(int invite_response) {
        this.invite_response = invite_response;
    }

    public boolean is_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

}
