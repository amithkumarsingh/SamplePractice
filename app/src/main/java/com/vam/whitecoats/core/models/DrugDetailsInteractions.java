package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugDetailsInteractions {
    @SerializedName("drug_id1")
    @Expose
    private int drugId1;
    @SerializedName("drug_id2")
    @Expose
    private int drugId2;
    @SerializedName("drug_id_2_is_tag")
    @Expose
    private int drugId2IsTag;
    @SerializedName("drug1")
    @Expose
    private String drug1;
    @SerializedName("drug2")
    @Expose
    private String drug2;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("interaction_details")
    @Expose
    private String interactionDetails;

    public int getDrugId1() {
        return drugId1;
    }

    public int getDrugId2() {
        return drugId2;
    }

    public int getDrugId2IsTag() {
        return drugId2IsTag;
    }

    public String getDrug1() {
        return drug1;
    }

    public String getDrug2() {
        return drug2;
    }

    public String getType() {
        return type;
    }

    public String getInteractionDetails() {
        return interactionDetails;
    }

    public void setDrugId1(int drugId1) {
        this.drugId1 = drugId1;
    }

    public void setDrugId2(int drugId2) {
        this.drugId2 = drugId2;
    }

    public void setDrugId2IsTag(int drugId2IsTag) {
        this.drugId2IsTag = drugId2IsTag;
    }

    public void setDrug1(String drug1) {
        this.drug1 = drug1;
    }

    public void setDrug2(String drug2) {
        this.drug2 = drug2;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInteractionDetails(String interactionDetails) {
        this.interactionDetails = interactionDetails;
    }
}
