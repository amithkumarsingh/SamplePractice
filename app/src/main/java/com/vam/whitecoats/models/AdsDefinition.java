package com.vam.whitecoats.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class AdsDefinition {

    @SerializedName("slot_id")
    @Expose
    private Integer slotId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("dimension")
    @Expose
    private String dimension;
    @SerializedName("ad_source")
    @Expose
    private String adSource;
    @SerializedName("occurrence")
    @Expose
    private Integer occurrence;
    @SerializedName("max_limit")
    @Expose
    private Integer maxLimit;
    @SerializedName("source_slot_id")
    @Expose
    private String sourceSlotId;
    @SerializedName("ad_location_type_id")
    @Expose
    private Integer adLocationTypeId;
    @SerializedName("ad_slot_duration")
    @Expose
    private Integer adSlotDuration;

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getAdSource() {
        return adSource;
    }

    public void setAdSource(String adSource) {
        this.adSource = adSource;
    }

    public Integer getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Integer occurrence) {
        this.occurrence = occurrence;
    }

    public Integer getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Integer maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getSourceSlotId() {
        return sourceSlotId;
    }

    public void setSourceSlotId(String sourceSlotId) {
        this.sourceSlotId = sourceSlotId;
    }

    public Integer getAdLocationTypeId() {
        return adLocationTypeId;
    }

    public void setAdLocationTypeId(Integer adLocationTypeId) {
        this.adLocationTypeId = adLocationTypeId;
    }

    public Integer getAdSlotDuration() {
        return adSlotDuration;
    }

    public void setAdSlotDuration(Integer adSlotDuration) {
        this.adSlotDuration = adSlotDuration;
    }
}
