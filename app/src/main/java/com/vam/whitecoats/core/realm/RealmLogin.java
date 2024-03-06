package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by swathim on 5/7/2015.
 */
public class RealmLogin extends RealmObject {
    @Required
    @PrimaryKey
    private String qb_user_id;

    private String qb_user_login;
    private String qb_user_password;


    public void setQb_user_id(String qb_user_id) {
        this.qb_user_id = qb_user_id;
    }

    public String getQb_user_id() {
        return qb_user_id;
    }

    public void setQb_user_login(String qb_user_login) {
        this.qb_user_login = qb_user_login;
    }

    public String getQb_user_login() {
        return qb_user_login;
    }

    public void setQb_user_password(String qb_user_password) {
        this.qb_user_password = qb_user_password;
    }

    public String getQb_user_password() {
        return qb_user_password;
    }

}
