package com.vam.whitecoats.ui.interfaces;

import org.json.JSONObject;

/**
 * Created by venuv on 6/9/2017.
 */

public interface NetworkConnectionListener {
    void onNetworkReconnection();
    void onNetworkDisconnect();
}
