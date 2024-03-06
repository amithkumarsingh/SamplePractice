package com.vam.whitecoats.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class FileDetails implements Parcelable {

    private String filePath;
    private String fileName;

    public FileDetails(){

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filePath);
        parcel.writeString(fileName);
    }

    public static final Creator<FileDetails> CREATOR = new Creator<FileDetails>() {
        @Override
        public FileDetails createFromParcel(Parcel source) {
            return new FileDetails(source);
        }

        @Override
        public FileDetails[] newArray(int size) {
            return new FileDetails[size];
        }
    };

    private FileDetails(Parcel in) {
        filePath = in.readString();
        fileName = in.readString();
    }
}
