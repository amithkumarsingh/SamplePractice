package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by lokeshl on 5/21/2015.
 */
public class RealmProfessionalInfo extends RealmObject {
    @Required
    @PrimaryKey
    private Integer prof_id;

    private String workplace;
    private String designation;
    private String location;
    private String availableDays;
    private String workOptions;

    private long start_date;
    private long end_date;
    private long startTime;
    private long endTime;
    private boolean working_here;
    private boolean showOncard;

    public String getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public String getWorkOptions() {
        return workOptions;
    }

    public void setWorkOptions(String workOptions) {
        this.workOptions = workOptions;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isShowOncard() {
        return showOncard;
    }

    public void setShowOncard(boolean showOncard) {
        this.showOncard = showOncard;
    }




    public Integer getProf_id() {
        return prof_id;
    }

    public void setProf_id(Integer prof_id) {
        this.prof_id = prof_id;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }



    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getStart_date() {
        return start_date;
    }

    public void setStart_date(long startDate) {
        this.start_date = startDate;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long endDate) {
        this.end_date = endDate;
    }

    public boolean isWorking_here() {
        return working_here;
    }

    public void setWorking_here(boolean working_here) {
        this.working_here = working_here;
    }





}
