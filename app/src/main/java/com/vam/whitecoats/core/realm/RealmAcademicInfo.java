package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by lokeshl on 5/19/2015.
 */
public class RealmAcademicInfo extends RealmObject implements Serializable{
    @Index
    @Required
    @PrimaryKey
    private Integer acad_id;

    private String degree;
    private String university;
    private String college;
    private int passing_year;
    private boolean currently_pursuing;

    public Integer getAcad_id() {
        return acad_id;
    }

    public void setAcad_id(Integer acadId) {
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
