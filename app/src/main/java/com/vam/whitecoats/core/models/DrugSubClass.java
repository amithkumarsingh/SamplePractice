package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugSubClass {
    @SerializedName("drug_id")
    @Expose
    private Integer drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("drug_type")
    @Expose
    private String drugType;

    public Integer getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }
}
