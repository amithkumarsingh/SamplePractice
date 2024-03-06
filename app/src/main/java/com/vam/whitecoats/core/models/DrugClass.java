package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugClass {
    @SerializedName("drug_id")
    @Expose
    private Integer drugId;
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("drug_type")
    @Expose
    private String drugType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("last_modified_date")
    @Expose
    private String lastModifiedDate;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;

    public Integer getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public String getDescription() {
        return description;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Integer getDeleted() {
        return deleted;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
