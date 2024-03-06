package com.vam.whitecoats.utils;

public enum TabsTagId {

    KNOWLEDGE_FEEDS(1),KNOWLEDGE_DRUG_REFERENCE(2),KNOWLEDGE_MEDICAL_EVENTS(3),COMMUNITY_SPOTLIGHTS(4),COMMUNITY_FEEDS(5),COMMUNITY_DOCTORS(6),
    COMMUNITY_ORGANIZATIONS(7),PROFESSIONAL_FEEDS(8),PROFESSIONAL_SKILLING(9),PROFESSIONAL_OPPORTUNITIES(10),PROFESSIONAL_PARTNERS(11);
    int tagId;

    private TabsTagId(int mValue) {
        this.tagId = mValue;
    }

    public int geTagId() {
        return tagId;
    }
}
