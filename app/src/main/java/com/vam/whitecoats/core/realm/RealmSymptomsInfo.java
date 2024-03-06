package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by tejaswini on 05-10-2015.
 */
public class RealmSymptomsInfo extends RealmObject {
    @Required
    private  Integer sym_id;
    private String addSymptons;
    private  String duration;
    private String details;

    public Integer getSym_id() {
        return sym_id;
    }

    public void setSym_id(Integer sym_id) {
        this.sym_id = sym_id;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



    public String getAddSymptons() {
        return addSymptons;
    }

    public void setAddSymptons(String addSymptons) {
        this.addSymptons = addSymptons;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


}
