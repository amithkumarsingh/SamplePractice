package com.vam.whitecoats.core.models;

public class MessageEvent {

    String event;
    public MessageEvent(String _event){
        this.event=_event;
    }

    public String getEvent() {
        return event;
    }
}
