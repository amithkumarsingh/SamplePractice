package com.vam.whitecoats.utils;



import com.vam.whitecoats.ui.interfaces.UiUpdateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by venuv on 6/9/2017.
 */

public class CallbackCollectionManager {
    private static CallbackCollectionManager single_instance = null;
    public static CallbackCollectionManager getInstance()
    {
        if (single_instance == null)
            single_instance = new CallbackCollectionManager();

        return single_instance;
    }
    public List<UiUpdateListener> callBackCollection=new CopyOnWriteArrayList<>();

    public synchronized void registerListener(UiUpdateListener mListener){
        callBackCollection.add(mListener);
    }

    public synchronized void removeListener(UiUpdateListener mListener){
        callBackCollection.remove(mListener);
    }

    public synchronized List<UiUpdateListener> getRegisterListeners(){
        return callBackCollection;
    }
}
