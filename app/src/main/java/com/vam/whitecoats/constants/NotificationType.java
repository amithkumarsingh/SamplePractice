package com.vam.whitecoats.constants;

/**
 * Created by satyasarathim on 02-11-2017.
 */

public enum NotificationType {
    INVITE_USER_FOR_CONNECT("INVITE_USER_FOR_CONNECT"),
    INVITE_USER_FOR_CONNECT_RESPONSE("INVITE_USER_FOR_CONNECT_RESPONSE"),
    DEFAULT_USER_CONNECT("DEFAULT_USER_CONNECT"),
    GROUP_CHAT_INVITE("GROUP_CHAT_INVITE"),
    CASEROOM_INVITE("CASEROOM_INVITE"),
    CHANNEL_POST("CHANNEL_POST"),
    CHANNEL_CONTENT_ARTICLE("CHANNEL_CONTENT_ARTICLE");

    private final String notificationType;
    NotificationType(String notificationType){
        this.notificationType=notificationType;
    }

    @Override
    public String toString() {
        return this.notificationType;
    }
}
