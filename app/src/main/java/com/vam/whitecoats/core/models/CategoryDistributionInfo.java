package com.vam.whitecoats.core.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryDistributionInfo implements Serializable {

    @SerializedName("category_distribution_id")
    @Expose
    private Integer categoryDistributionId;
    @SerializedName("category_distribution_name")
    @Expose
    private String categoryDistributionName;
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
    @SerializedName("group_by")
    @Expose
    private String groupBy;

    public Integer getCategoryDistributionId() {
        return categoryDistributionId;
    }

    public void setCategoryDistributionId(Integer categoryDistributionId) {
        this.categoryDistributionId = categoryDistributionId;
    }

    public String getCategoryDistributionName() {
        return categoryDistributionName;
    }

    public void setCategoryDistributionName(String categoryDistributionName) {
        this.categoryDistributionName = categoryDistributionName;
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

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
}
