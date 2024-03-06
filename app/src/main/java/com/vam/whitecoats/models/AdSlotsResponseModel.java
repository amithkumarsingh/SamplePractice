package com.vam.whitecoats.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdSlotsResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private AdSlotsDataModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdSlotsDataModel getData() {
        return data;
    }

    public void setData(AdSlotsDataModel data) {
        this.data = data;
    }

}
