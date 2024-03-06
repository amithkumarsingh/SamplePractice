package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by lokeshl on 5/19/2015.
 */
public class AcademicInfo implements Serializable{
    private String degree;
    private String university;
    private String college;
    private int passing_year;
    private boolean currently_pursuing;


    private int acad_id;


    public int getAcad_id() {
        return acad_id;
    }

    public void setAcad_id(int acadId) {
        this.acad_id = acadId;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(int passYear) {
        this.passing_year = passYear;
    }


    public boolean isCurrently_pursuing() {
        return currently_pursuing;
    }

    public void setCurrently_pursuing(boolean currently_pursuing) {
        this.currently_pursuing = currently_pursuing;
    }
}
