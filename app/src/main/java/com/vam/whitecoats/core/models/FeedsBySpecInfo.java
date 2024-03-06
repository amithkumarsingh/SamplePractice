package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedsBySpecInfo {
    @SerializedName("feed_id")
    @Expose
    private Integer feedId;
    @SerializedName("feed_type_id")
    @Expose
    private Integer feedTypeId;
    @SerializedName("channel_id")
    @Expose
    private Integer channelId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("attachment_details")
    @Expose
    private List<CategoryFeedAttachment> attachmentDetails = null;

    public Integer getFeedId() {
        return feedId;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }

    public Integer getFeedTypeId() {
        return feedTypeId;
    }

    public void setFeedTypeId(Integer feedTypeId) {
        this.feedTypeId = feedTypeId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public List<CategoryFeedAttachment> getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setAttachmentDetails(List<CategoryFeedAttachment> attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }
}
