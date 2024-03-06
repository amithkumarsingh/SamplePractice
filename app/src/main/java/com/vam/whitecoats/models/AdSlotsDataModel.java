package com.vam.whitecoats.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdSlotsDataModel {

    @SerializedName("ads_definitions")
    @Expose
    private List<AdsDefinition> adsDefinitions = null;

    public List<AdsDefinition> getAdsDefinitions() {
        return adsDefinitions;
    }

    public void setAdsDefinitions(List<AdsDefinition> adsDefinitions) {
        this.adsDefinitions = adsDefinitions;
    }
}
