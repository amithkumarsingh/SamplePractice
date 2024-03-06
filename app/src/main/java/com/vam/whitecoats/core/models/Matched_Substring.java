package com.vam.whitecoats.core.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Matched_Substring implements Serializable {

    @SerializedName("length")
    private int length;

    @SerializedName("offset")
    private int offset;

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
