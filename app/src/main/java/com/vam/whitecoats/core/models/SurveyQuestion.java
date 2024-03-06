package com.vam.whitecoats.core.models;

import org.json.JSONObject;

import java.util.List;

public class SurveyQuestion {
    private int questionId;
    private boolean isMandatory;
    private boolean isMultiSelect;
    private String question;
    private String description;
    private JSONObject questionJsonObj;
    private int highPercentage;
    private List<SurveyOption> options;
    private List<FeedAttachment> feedAttachments;
    private boolean isAttempted;
    private int participantCount;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public boolean isMultiSelect() {
        return isMultiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SurveyOption> getOptions() {
        return options;
    }

    public void setOptions(List<SurveyOption> surveyOptions) {
        this.options = surveyOptions;
    }

    public List<FeedAttachment> getFeedAttachments() {
        return feedAttachments;
    }

    public void setFeedAttachments(List<FeedAttachment> feedAttachments) {
        this.feedAttachments = feedAttachments;
    }

    public int getHighPercentage() {
        return highPercentage;
    }

    public void setHighPercentage(int highPercentage) {
        this.highPercentage = highPercentage;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
    }

    public JSONObject getQuestionJsonObj() {
        return questionJsonObj;
    }

    public void setQuestionJsonObj(JSONObject questionJsonObj) {
        this.questionJsonObj = questionJsonObj;
    }
}
