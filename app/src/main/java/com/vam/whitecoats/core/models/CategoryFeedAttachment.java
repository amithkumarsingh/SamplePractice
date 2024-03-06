package com.vam.whitecoats.core.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryFeedAttachment implements Serializable {

    @SerializedName("attachment_original_url")
    @Expose
    private String attachmentOriginalUrl;
    @SerializedName("attachment_small_url")
    @Expose
    private String attachmentSmallUrl;
    @SerializedName("attachment_type")
    @Expose
    private String attachmentType;

    public String getAttachmentOriginalUrl() {
        return attachmentOriginalUrl;
    }

    public void setAttachmentOriginalUrl(String attachmentOriginalUrl) {
        this.attachmentOriginalUrl = attachmentOriginalUrl;
    }

    public String getAttachmentSmallUrl() {
        return attachmentSmallUrl;
    }

    public void setAttachmentSmallUrl(String attachmentSmallUrl) {
        this.attachmentSmallUrl = attachmentSmallUrl;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }
}
