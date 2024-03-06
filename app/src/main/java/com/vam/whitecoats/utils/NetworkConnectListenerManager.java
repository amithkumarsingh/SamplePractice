package com.vam.whitecoats.utils;

import com.vam.whitecoats.ui.interfaces.NetworkConnectionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venuv on 3/7/2018.
 */

public class NetworkConnectListenerManager {

    public static List<NetworkConnectionListener> callBackCollection=new ArrayList<>();

    public static void registerNetworkListener(NetworkConnectionListener mListener){
        callBackCollection.add(mListener);
    }

    public static void removeNetworkListener(NetworkConnectionListener mListener){
        callBackCollection.remove(mListener);
    }

    public static List<NetworkConnectionListener> getRegisterListeners(){
        return callBackCollection;
    }
}
