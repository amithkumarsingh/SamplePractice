package com.vam.whitecoats.core.models;

/**
 * <h1>Channel DTO!</h1>
 * <p>
 * The Channel DTO simply set/get values for Channel params like title, logo etc..
 * It can be transferred to other classes/Activities/Fragments for data transfer.
 * </p>
 *
 * @author satyasarathim
 * @version 1.0
 * @since 06-06-2017
 *
 */

public class Channel {
    String sectionTitle;
    int channelId;
    String channelLogo;
    boolean isAdmin;
    String feedProviderType;
    String feedProviderSubType;
    String feedProviderName;
    int membersCount;
    int unreadFeeds;
    long latestFeedTime;
    boolean isSubscribed;
    boolean isMandatory;

    public boolean getIsMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }



    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelLogo() {
        return channelLogo;
    }

    public void setChannelLogo(String channelLogo) {
        this.channelLogo = channelLogo;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFeedProviderType() {
        return feedProviderType;
    }

    public void setFeedProviderType(String feedProviderType) {
        this.feedProviderType = feedProviderType;
    }

    public String getFeedProviderSubType() {
        return feedProviderSubType;
    }

    public void setFeedProviderSubType(String feedProviderSubType) {
        this.feedProviderSubType = feedProviderSubType;
    }

    public String getFeedProviderName() {
        return feedProviderName;
    }

    public void setFeedProviderName(String feedProviderName) {
        this.feedProviderName = feedProviderName;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getUnreadFeeds() {
        return unreadFeeds;
    }

    public void setUnreadFeeds(int unreadFeeds) {
        this.unreadFeeds = unreadFeeds;
    }

    public long getLatestFeedTime() {
        return latestFeedTime;
    }

    public void setLatestFeedTime(long latestFeedTime) {
        this.latestFeedTime = latestFeedTime;
    }


}
