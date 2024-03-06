package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 5/21/2015.
 */
public class RealmOnlinePub extends RealmObject {


    @Required
    private String id;
    private int pubId;
    private String pub_name;

    public String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

}
