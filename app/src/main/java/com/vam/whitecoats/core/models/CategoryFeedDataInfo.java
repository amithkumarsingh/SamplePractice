package com.vam.whitecoats.core.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryFeedDataInfo {

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_type")
    @Expose
    private String categoryType;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("unread_count")
    @Expose
    private Integer unreadCount;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("feed_data")
    @Expose
    private List<CategoryFeeds> feedData = null;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<CategoryFeeds> getFeedData() {
        return feedData;
    }

    public void setFeedData(List<CategoryFeeds> feedData) {
        this.feedData = feedData;
    }
}
