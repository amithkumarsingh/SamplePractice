package com.vam.whitecoats.core.models;

import java.io.Serializable;

/**
 * Created by tejaswini on 03-11-2015.
 */
public class CaseRoomPatientDetailsInfo implements Serializable {

    private String caseroom_summary_id;
    private  String patgender;
    private String patage;
    private String symptoms;
    private String history;
    private  String vitals_anthropometry;
    private String   general_examination;
    private String systemic_examination;

    public String getVitals_anthropometry() {
        return vitals_anthropometry;
    }

    public void setVitals_anthropometry(String vitals_anthropometry) {
        this.vitals_anthropometry = vitals_anthropometry;
    }

    public String getCaseroom_summary_id() {
        return caseroom_summary_id;
    }

    public void setCaseroom_summary_id(String caseroom_summary_id) {
        this.caseroom_summary_id = caseroom_summary_id;
    }

    public String getPatgender() {
        return patgender;
    }

    public void setPatgender(String patgender) {
        this.patgender = patgender;
    }

    public String getPatage() {
        return patage;
    }

    public void setPatage(String patage) {
        this.patage = patage;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getGeneral_examination() {
        return general_examination;
    }

    public void setGeneral_examination(String general_examination) {
        this.general_examination = general_examination;
    }

    public String getSystemic_examination() {
        return systemic_examination;
    }

    public void setSystemic_examination(String systemic_examination) {
        this.systemic_examination = systemic_examination;
    }


}
