package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationInfo {
    @SerializedName("c_msg_type")
    @Expose
    private Integer cMsgType;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("feed_id")
    @Expose
    private Integer feedId;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("channel_id")
    @Expose
    private Integer channelId;

    @SerializedName("timestamp")
    @Expose
    private Long timeStamp;
    @SerializedName("has_read")
    @Expose
    private boolean notificationRead;
    @SerializedName("is_personalized")
    @Expose
    private boolean is_personalized;
    @SerializedName("tag_id")
    @Expose
    private Integer tag_id;

    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public boolean getIs_personalized() {
        return is_personalized;
    }

    public void setIs_personalized(boolean is_personalized) {
        this.is_personalized = is_personalized;
    }

    public Integer getCMsgType() {
        return cMsgType;
    }

    public void setCMsgType(Integer cMsgType) {
        this.cMsgType = cMsgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFeedId() {
        return feedId;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(boolean notificationRead) {
        this.notificationRead = notificationRead;
    }
}
