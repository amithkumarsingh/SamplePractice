package com.vam.whitecoats.core.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by venuv on 5/19/2016.
 */
public class ChanneslAndFeedsInfo implements Serializable{
    private String feed_provider_name;
    private String feed_provider_subtype;
    private String feed_provider_type;
    private int latest_feed_time ;
    private int today_count;
    private int channel_id;


    public String getFeed_provider_name() {
        return feed_provider_name;
    }

    public void setFeed_provider_name(String feed_provider_name) {
        this.feed_provider_name = feed_provider_name;
    }

    public String getFeed_provider_subtype() {
        return feed_provider_subtype;
    }

    public void setFeed_provider_subtype(String feed_provider_subtype) {
        this.feed_provider_subtype = feed_provider_subtype;
    }

    public String getFeed_provider_type() {
        return feed_provider_type;
    }

    public void setFeed_provider_type(String feed_provider_type) {
        this.feed_provider_type = feed_provider_type;
    }

    public int getLatest_feed_time() {
        return latest_feed_time;
    }

    public void setLatest_feed_time(int latest_feed_time) {
        this.latest_feed_time = latest_feed_time;
    }

    public int getToday_count() {
        return today_count;
    }

    public void setToday_count(int today_count) {
        this.today_count = today_count;
    }


    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }
}
