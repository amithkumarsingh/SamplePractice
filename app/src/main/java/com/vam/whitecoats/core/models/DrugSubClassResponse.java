package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugSubClassResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<DrugSubClass> drugs = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DrugSubClass> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugSubClass> drugs) {
        this.drugs = drugs;
    }
}
