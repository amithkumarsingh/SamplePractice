package com.vam.whitecoats.core.realm;

import androidx.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by swathim on 5/20/2015.
 */
public class RealmProfessionalMembership extends RealmObject{

    @Required
    private Integer prof_mem_id;
    private String membership_name;
    private String type;
    private String award_name;
    private int award_id;
    private long award_year;
    private String presented_at;

    public Integer getProf_mem_id() {
        return prof_mem_id;
    }

    public void setProf_mem_id(Integer prof_mem_id) {
        this.prof_mem_id = prof_mem_id;
    }

     public String getMembership_name() {
        return membership_name;
    }

    public void setMembership_name(String membership_name) {
        this.membership_name = membership_name;
    }

    public String getAward_name() {
        return award_name;
    }

    public void setAward_name(String award_name) {
        this.award_name = award_name;
    }

    public int getAward_id() {
        return award_id;
    }

    public void setAward_id(int award_id) {
        this.award_id = award_id;
    }

    public long getAward_year() {
        return award_year;
    }

    public void setAward_year(long award_year) {
        this.award_year = award_year;
    }

    public String getPresented_at() {
        return presented_at;
    }

    public void setPresented_at(String presented_at) {
        this.presented_at = presented_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
