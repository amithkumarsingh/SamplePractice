package com.vam.whitecoats.constants;

public enum VIEWTYPE {
    TEXT_VIEW(1),BUTTON(2),RADIO_BUTTON(3),CHECK_BOX(4),LINEAR_LAYOUT(5);
    private int value;
    VIEWTYPE(int value){
        this.value=value;
    }
}
