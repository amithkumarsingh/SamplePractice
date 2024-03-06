package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrugDetailsAttributes {

    @SerializedName("brandInfo")
    @Expose
    private List<BrandInfo> brandInfo = null;
    @SerializedName("attributes")
    @Expose
    private List<DrugDetail> attributes = null;
    @SerializedName(value="brands",alternate = {"otherBrands"})
    @Expose
    private List<DrugBrand> brands = null;
    @SerializedName("drugInteractions")
    @Expose
    private List<DrugDetailsInteractions> drugInteractions = null;

    public List<DrugDetail> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DrugDetail> attributes) {
        this.attributes = attributes;
    }

    public List<DrugBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<DrugBrand> brands) {
        this.brands = brands;
    }

    public List<DrugDetailsInteractions> getDrugInteractions() {
        return drugInteractions;
    }

    public void setDrugInteractions(List<DrugDetailsInteractions> drugInteractions) {
        this.drugInteractions = drugInteractions;
    }

    public List<BrandInfo> getBrandInfo() {
        return brandInfo;
    }

    public void setBrandInfo(List<BrandInfo> brandInfo) {
        this.brandInfo = brandInfo;
    }
}

