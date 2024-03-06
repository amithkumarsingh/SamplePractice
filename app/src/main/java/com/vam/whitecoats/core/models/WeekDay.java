package com.vam.whitecoats.core.models;

public class WeekDay {
    int daySerialNumber;
    String name;
    String shortName;
    boolean isChecked;

    public int getDaySerialNumber() {
        return daySerialNumber;
    }

    public void setDaySerialNumber(int daySerialNumber) {
        this.daySerialNumber = daySerialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
