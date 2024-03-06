package com.vam.whitecoats.utils;

import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by venuv on 4/14/2018.
 */

public class ProfileUpdateCollectionManager {

    public static List<ProfileUpdatedListener> callBackCollection=new ArrayList<>();

    public static void registerListener(ProfileUpdatedListener mListener){
        callBackCollection.add(mListener);
    }

    public static void removeListener(ProfileUpdatedListener mListener){
        callBackCollection.remove(mListener);
    }

    public static List<ProfileUpdatedListener> getRegisterListeners(){
        return callBackCollection;
    }
}
