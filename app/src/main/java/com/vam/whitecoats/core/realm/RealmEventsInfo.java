package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * class RealmEventsInfo
 * @version 1.0
 * @author satyasarathim
 * @Date - 25/10/2018
 * Copyright (c) 2018 .All rights reserved.
 */
public class RealmEventsInfo extends RealmObject {
    @PrimaryKey
    private int eventId;
    private String eventTitle;
    private String location;
    private long startDate;
    private long endDate;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
