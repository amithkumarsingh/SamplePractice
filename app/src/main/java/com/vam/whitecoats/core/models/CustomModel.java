package com.vam.whitecoats.core.models;

import org.json.JSONObject;

/**
 * Created by tejaswini on 26-11-2016.
 */
public class CustomModel {

    public interface OnUiUpdateListener {
        void onUiUpdateForComments(JSONObject mObj);
    }

    private static CustomModel mInstance;
    private OnUiUpdateListener mListener;
    private boolean mState;

    private CustomModel() {}

    public static CustomModel getInstance() {
        if(mInstance == null) {
            mInstance = new CustomModel();
        }
        return mInstance;
    }

    public void setListener(OnUiUpdateListener listener) {
        mListener = listener;
    }

    public void changeState(boolean state,JSONObject mJsonObj) {
        if(mListener != null) {
            mState = state;
            notifyStateChange(mJsonObj);
        }
    }

    public boolean getState() {
        return mState;
    }

    private void notifyStateChange(JSONObject mJsonObj) {
        mListener.onUiUpdateForComments(mJsonObj);
    }
}
