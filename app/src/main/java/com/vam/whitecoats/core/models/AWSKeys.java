package com.vam.whitecoats.core.models;

import java.io.Serializable;

public class AWSKeys implements Serializable {

    public String AWS_ACCESS_KEY;
    public String AWS_SECRET_KEY;
    public String AWS_BUCKET;


    public String getAWS_ACCESS_KEY() {
        return AWS_ACCESS_KEY;
    }

    public void setAWS_ACCESS_KEY(String AWS_ACCESS_KEY) {
        this.AWS_ACCESS_KEY = AWS_ACCESS_KEY;
    }

    public String getAWS_SECRET_KEY() {
        return AWS_SECRET_KEY;
    }

    public void setAWS_SECRET_KEY(String AWS_SECRET_KEY) {
        this.AWS_SECRET_KEY = AWS_SECRET_KEY;
    }

    public String getAWS_BUCKET() {
        return AWS_BUCKET;
    }

    public void setAWS_BUCKET(String AWS_BUCKET) {
        this.AWS_BUCKET = AWS_BUCKET;
    }

}
