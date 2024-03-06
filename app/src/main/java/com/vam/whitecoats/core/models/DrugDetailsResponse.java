package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrugDetailsResponse {
    @SerializedName("data")
    @Expose
    private DrugDetailsAttributes data;
    @SerializedName("status")
    @Expose
    private String status;

    public DrugDetailsAttributes getData() {
        return data;
    }

    public void setData(DrugDetailsAttributes data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
