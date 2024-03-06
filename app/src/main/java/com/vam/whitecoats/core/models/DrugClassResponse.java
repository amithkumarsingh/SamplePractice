package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugClassResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DrugClass> drugs = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DrugClass> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugClass> drugs) {
        this.drugs = drugs;
    }
}
