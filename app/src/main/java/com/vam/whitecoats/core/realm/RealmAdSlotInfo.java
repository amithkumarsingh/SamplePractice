package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;

public class RealmAdSlotInfo extends RealmObject {

    private int slot_id;
    private String location;
    private String dimensions;
    private String ad_source;
    private int occurance;
    private int max_limit;
    private String source_slot_id;
    private int ad_location_type_id;
    private int ad_slot_duration;

    public int getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getAd_source() {
        return ad_source;
    }

    public void setAd_source(String ad_source) {
        this.ad_source = ad_source;
    }

    public int getOccurance() {
        return occurance;
    }

    public void setOccurance(int occurance) {
        this.occurance = occurance;
    }

    public int getMax_limit() {
        return max_limit;
    }

    public void setMax_limit(int max_limit) {
        this.max_limit = max_limit;
    }

    public String getSource_slot_id() {
        return source_slot_id;
    }

    public void setSource_slot_id(String source_slot_id) {
        this.source_slot_id = source_slot_id;
    }

    public int getLocation_type() {
        return ad_location_type_id;
    }

    public void setLocation_type(int location_type) {
        this.ad_location_type_id = location_type;
    }

    public int getAd_slot_duration() {
        return ad_slot_duration;
    }

    public void setAd_slot_duration(int ad_slot_duration) {
        this.ad_slot_duration = ad_slot_duration;
    }
}
