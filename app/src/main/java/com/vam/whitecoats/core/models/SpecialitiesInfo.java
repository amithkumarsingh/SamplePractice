package com.vam.whitecoats.core.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpecialitiesInfo implements Serializable {

    @SerializedName("speciality_id")
    @Expose
    private Integer specialityId;
    @SerializedName("speciality_name")
    @Expose
    private String specialityName;
    @SerializedName("feed_count")
    @Expose
    private Integer feedCount;

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Integer getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(Integer feedCount) {
        this.feedCount = feedCount;
    }

}
