package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationInfoRoot {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private NotificationInfoData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NotificationInfoData getData() {
        return data;
    }

    public void setData(NotificationInfoData data) {
        this.data = data;
    }

}
