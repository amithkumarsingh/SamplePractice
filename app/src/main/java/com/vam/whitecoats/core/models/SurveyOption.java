package com.vam.whitecoats.core.models;

public class SurveyOption {
    private int optionId;
    private int participatedPercent;
    private String option;
    private boolean isSelected;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getParticipatedPercent() {
        return participatedPercent;
    }

    public void setParticipatedPercent(int participatedPercent) {
        this.participatedPercent = participatedPercent;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
