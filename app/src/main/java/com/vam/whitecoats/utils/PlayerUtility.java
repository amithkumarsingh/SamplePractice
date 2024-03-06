package com.vam.whitecoats.utils;

import android.os.Handler;

/**
 * Created by pardhasaradhid on 10/30/2017.
 */

public class PlayerUtility {
    private Handler durationHandler = new Handler();
    PlayerViewUpdateUtility viewUpdateUtility;
    private Runnable updateSeekBarTime = new Runnable() {

        public void run() {
            //get current position
            if (viewUpdateUtility != null) {
                viewUpdateUtility.changeSeekValue();
            }
            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);
        }
    };

    public Handler getDurationHandler() {
        return durationHandler;
    }

    public Runnable getUpdateSeekBarTime() {
        return updateSeekBarTime;
    }

    public PlayerViewUpdateUtility getViewUpdateUtility() {
        return viewUpdateUtility;
    }
}
