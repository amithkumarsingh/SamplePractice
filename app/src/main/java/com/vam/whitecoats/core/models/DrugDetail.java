package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugDetail {
    @SerializedName("drug_name")
    @Expose
    private String drugName;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("drug_attribute_id")
    @Expose
    private int drugAttributeId;
    @SerializedName("level")
    @Expose
    private int level;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("value_type")
    @Expose
    private String valuetype;
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("seq_id")
    @Expose
    private int seqId;

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDrugAttributeId(int drugAttributeId) {
        this.drugAttributeId = drugAttributeId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getLabel() {
        return label;
    }

    public int getDrugAttributeId() {
        return drugAttributeId;
    }

    public int getLevel() {
        return level;
    }

    public String getValue() {
        return value;
    }

    public String getValuetype() {
        return valuetype;
    }

    public int getParentId() {
        return parentId;
    }

    public int getSeqId() {
        return seqId;
    }
}
