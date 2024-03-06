package com.vam.whitecoats.core.realm;

import io.realm.RealmObject;

public class RealmChannelsInfo extends RealmObject {

    private String channelsListKey;
    private String channelsList;

    public String getChannelsListKey() {
        return channelsListKey;
    }

    public void setChannelsListKey(String channelsListKey) {
        this.channelsListKey = channelsListKey;
    }

    public String getChannelsList() {
        return channelsList;
    }

    public void setChannelsList(String channelsList) {
        this.channelsList = channelsList;
    }
}
