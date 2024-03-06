package com.vam.whitecoats.core.models;

import java.util.List;

public class FeedSurvey {
    private boolean isOpen;
    private boolean isEligible;
    private boolean isParticipated;
    private boolean isImmediate;


    private long closeTime;
    private long timeStamp;
    private String ineligibleMsg;
    private List<SurveyQuestion> questions;

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public boolean isEligible() {
        return isEligible;
    }

    public void setEligible(boolean eligible) {
        isEligible = eligible;
    }

    public boolean isParticipated() {
        return isParticipated;
    }

    public void setParticipated(boolean participated) {
        isParticipated = participated;
    }

    public boolean isImmediate() {
        return isImmediate;
    }

    public void setImmediate(boolean immediate) {
        isImmediate = immediate;
    }





    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIneligibleMsg() {
        return ineligibleMsg;
    }

    public void setIneligibleMsg(String ineligibleMsg) {
        this.ineligibleMsg = ineligibleMsg;
    }

    public List<SurveyQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SurveyQuestion> questions) {
        this.questions = questions;
    }
}
