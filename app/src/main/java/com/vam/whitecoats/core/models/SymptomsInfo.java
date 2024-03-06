package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by tejaswini on 29-09-2015.
 */
public class SymptomsInfo implements Serializable{
    private String addSymptoms;
    private String duration;
    private String details;
    private  int sym_id;

    public int getSym_id() {
        return sym_id;
    }

    public void setSym_id(int sym_id) {
        this.sym_id = sym_id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAddSymptoms() {
        return addSymptoms;
    }

    public void setAddSymptoms(String addSymptoms) {
        this.addSymptoms = addSymptoms;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }






}
