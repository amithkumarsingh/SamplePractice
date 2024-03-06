package com.vam.whitecoats.utils;

public class InviteConnectStatusUpdateEvent {
    public   String message;
    public InviteConnectStatusUpdateEvent(String _message){
        this.message=_message;

    }
    public String getMessage() {
        return message;
    }
}
