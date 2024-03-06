package com.vam.whitecoats.constants;

public enum SlotLocationType {
    DASHBOARD_TOP_SLOT(1),
    MID_POST_SLOT(2),
    DRUG_SUBCLASS_SLOT(3),
    ARTICLE_FULL_VIEW_SLOT(4);

    int type;
    SlotLocationType(int mType){
        this.type=mType;
    }

    public int getType(){
        return type;
    }
}
