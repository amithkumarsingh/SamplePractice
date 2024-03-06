package com.vam.whitecoats.core.models;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by swathim on 25-08-2015.
 */
public class ProfessionalInfo implements Serializable{
    private String workplace;
    private String designation;
    private String availableDays;
    private String workOptions;
    private long start_date;
    private long end_date;
    private long startTime;
    private long endTime;

    private boolean working_here;
    private String location;
    private boolean showOncard;

    private int prof_id;


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

    public int getProf_id() {
        return prof_id;
    }

    public void setProf_id(int prof_id) {
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

    public void setStart_date(long start_date) {
        this.start_date = start_date;
    }

    public long getEnd_date() {
        return end_date;
    }

    public void setEnd_date(long end_date) {
        this.end_date = end_date;
    }
    public boolean isWorking_here() {
        return working_here;
    }

    public void setWorking_here(boolean working_here) {
        this.working_here = working_here;
    }


}
