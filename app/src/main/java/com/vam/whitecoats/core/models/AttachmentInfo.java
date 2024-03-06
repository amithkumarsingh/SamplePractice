package com.vam.whitecoats.core.models;

import java.io.Serializable;

public class AttachmentInfo implements Serializable {

    boolean editPost;
    String fileAttachmentPath;
    String attachmentUrl;
    long attachmentSize;

    public long getAttachmentSize() {
        return attachmentSize;
    }

    public void setAttachmentSize(long attachmentSize) {
        this.attachmentSize = attachmentSize;
    }



    public String getAttachmentSmallUrl() {
        return attachmentSmallUrl;
    }

    public void setAttachmentSmallUrl(String attachmentSmallUrl) {
        this.attachmentSmallUrl = attachmentSmallUrl;
    }

    String attachmentSmallUrl;

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    String attachmentName;

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    String attachmentType;


    public boolean isEditPost() {
        return editPost;
    }

    public String getFileAttachmentPath() {
        return fileAttachmentPath;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setEditPost(boolean editPost) {
        this.editPost = editPost;
    }

    public void setFileAttachmentPath(String fileAttachmentPath) {
        this.fileAttachmentPath = fileAttachmentPath;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

}
