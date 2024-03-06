package com.vam.whitecoats.constants;

/**
 * Created by pardhasaradhid on 4/25/2017.
 */

public enum DIRECTORY {
    CHAT("chat"),CASEROOM("caseroom"),POST("post"), COMMENT("comment");
    private String value;
    private DIRECTORY(String value){
        this.value=value;
    }
}
