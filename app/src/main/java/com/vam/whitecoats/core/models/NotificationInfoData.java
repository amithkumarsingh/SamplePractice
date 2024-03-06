package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationInfoData {
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("notifications")
    @Expose
    private List<NotificationInfo> notifications = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<NotificationInfo> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationInfo> notifications) {
        this.notifications = notifications;
    }

}
