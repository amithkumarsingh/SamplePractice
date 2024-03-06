package com.vam.whitecoats.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by satyasarathi on 14/06/2018.
 */
public class FeedInfo implements Parcelable {
    int feedID;
    int channelID;
    String articleID;
    int template;
    String title;
    String feedType;
    String articleType;
    String feedDesc;
    String longDesc;
    String shortDesc;
    String displayTag;
    String postingDate;
    String contentUrl;
    String largeImage;
    String mediumImage;
    String smallImage;
    String microImage;
    String attachmentName;
    String copyright;
    String wcCopyright;



    String feedSubType;
    long postingTime;
    long updatedTime;
    boolean isBookmarked;
    boolean isDeletable;
    boolean isEditable;
    boolean isEdited;
    JSONArray topics;
    JSONArray articleBody;
    JSONArray attachmentJson;
    JSONArray speciality;
    JSONObject feedInfoJson;
    JSONObject socialInteraction;
    JSONObject shareInfo;
    FeedSurvey surveyData;

    public JSONObject getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(JSONObject eventDetails) {
        this.eventDetails = eventDetails;
    }

    JSONObject eventDetails;

    //    List<FeedAttachment> feedAttachments;
    public FeedInfo() {

    }

    protected FeedInfo(Parcel in) {
        feedID = in.readInt();
        channelID = in.readInt();
        articleID = in.readString();
        template = in.readInt();
        title = in.readString();
        feedType = in.readString();
        articleType = in.readString();
        feedDesc = in.readString();
        longDesc = in.readString();
        shortDesc = in.readString();
        displayTag = in.readString();
        postingDate = in.readString();
        contentUrl = in.readString();
        largeImage = in.readString();
        mediumImage = in.readString();
        smallImage = in.readString();
        microImage = in.readString();
        attachmentName = in.readString();
        copyright = in.readString();
        wcCopyright = in.readString();
        feedSubType=in.readString();
        postingTime = in.readLong();
        updatedTime = in.readLong();
        isBookmarked = in.readByte() != 0;
        isDeletable = in.readByte() != 0;
        isEditable = in.readByte() != 0;
    }

    public static final Creator<FeedInfo> CREATOR = new Creator<FeedInfo>() {
        @Override
        public FeedInfo createFromParcel(Parcel in) {
            return new FeedInfo(in);
        }

        @Override
        public FeedInfo[] newArray(int size) {
            return new FeedInfo[size];
        }
    };

    public int getFeedID() {
        return feedID;
    }

    public void setFeedID(int feedID) {
        this.feedID = feedID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getFeedDesc() {
        return feedDesc;
    }

    public void setFeedDesc(String feedDesc) {
        this.feedDesc = feedDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDisplayTag() {
        return displayTag;
    }

    public void setDisplayTag(String displayTag) {
        this.displayTag = displayTag;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getMediumImage() {
        return mediumImage;
    }

    public void setMediumImage(String mediumImage) {
        this.mediumImage = mediumImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMicroImage() {
        return microImage;
    }

    public void setMicroImage(String microImage) {
        this.microImage = microImage;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getWcCopyright() {
        return wcCopyright;
    }

    public void setWcCopyright(String wcCopyright) {
        this.wcCopyright = wcCopyright;
    }

    public long getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(long postingTime) {
        this.postingTime = postingTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean getisEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public JSONArray getTopics() {
        return topics;
    }

    public void setTopics(JSONArray topics) {
        this.topics = topics;
    }

    public JSONArray getArticleBody() {
        return articleBody;
    }

    public void setArticleBody(JSONArray articleBody) {
        this.articleBody = articleBody;
    }

    public JSONArray getSpeciality() {
        return speciality;
    }

    public void setSpeciality(JSONArray speciality) {
        this.speciality = speciality;
    }

    public JSONObject getSocialInteraction() {
        return socialInteraction;
    }

    public JSONArray getAttachmentJson() {
        return attachmentJson;
    }

    public void setAttachmentJson(JSONArray attachmentJson) {
        this.attachmentJson = attachmentJson;
    }

    public void setSocialInteraction(JSONObject socialInteraction) {
        this.socialInteraction = socialInteraction;
    }

    public JSONObject getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(JSONObject shareInfo) {
        this.shareInfo = shareInfo;
    }

    public JSONObject getFeedInfoJson() {
        return feedInfoJson;
    }

    public void setFeedInfoJson(JSONObject feedInfoJson) {
        this.feedInfoJson = feedInfoJson;
    }

    public FeedSurvey getSurveyData() {
        return surveyData;
    }

    public void setSurveyData(FeedSurvey surveyData) {
        this.surveyData = surveyData;
    }

    public String getFeedSubType() {
        return feedSubType;
    }

    public void setFeedSubType(String feedSubType) {
        this.feedSubType = feedSubType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(feedID);
        parcel.writeInt(channelID);
        parcel.writeString(articleID);
        parcel.writeInt(template);
        parcel.writeString(title);
        parcel.writeString(feedType);
        parcel.writeString(articleType);
        parcel.writeString(feedDesc);
        parcel.writeString(longDesc);
        parcel.writeString(shortDesc);
        parcel.writeString(displayTag);
        parcel.writeString(postingDate);
        parcel.writeString(contentUrl);
        parcel.writeString(largeImage);
        parcel.writeString(mediumImage);
        parcel.writeString(smallImage);
        parcel.writeString(microImage);
        parcel.writeString(attachmentName);
        parcel.writeString(copyright);
        parcel.writeString(wcCopyright);
        parcel.writeString(feedSubType);
        parcel.writeLong(postingTime);
        parcel.writeLong(updatedTime);
        parcel.writeByte((byte) (isBookmarked ? 1 : 0));
        parcel.writeByte((byte) (isDeletable ? 1 : 0));
        parcel.writeByte((byte) (isEditable ? 1 : 0));
    }

    /*public List<FeedAttachment> getFeedAttachments() {
        return feedAttachments;
    }

    public void setFeedAttachments(List<FeedAttachment> feedAttachments) {
        this.feedAttachments = feedAttachments;
    }*/
}
