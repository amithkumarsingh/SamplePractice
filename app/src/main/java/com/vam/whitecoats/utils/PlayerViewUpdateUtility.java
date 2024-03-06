package com.vam.whitecoats.utils;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.adapters.ImageViewPagerAdapter;
import com.vam.whitecoats.ui.adapters.PlayViewChangeDelegate;

/**
 * Created by pardhasaradhid on 10/30/2017.
 */

public class PlayerViewUpdateUtility implements View.OnTouchListener,PlayViewChangeDelegate {
    MediaPlayer mPlayer;
    SeekBar seekBarProgress;
    TextView tv_duration, tv_fileduration;
    PlayerUtility playerUtility;
    ImageButton imgbtn_play;
    ProgressBar mprogressbar;

    public ProgressBar getMprogressbar() {
        return mprogressbar;
    }


    private PlayerViewUpdateUtility() {}

    public MediaPlayer getmPlayer() {

        return mPlayer;
    }

    public ImageButton getImgbtn_play() {
        return imgbtn_play;
    }

    public PlayerViewUpdateUtility(PlayerUtility player, MediaPlayer mPlayer, SeekBar seekBarProgress, TextView tv_duration, ImageButton imgbtn_play,ProgressBar mprogressbar) {
        this.mPlayer = mPlayer;
        this.seekBarProgress = seekBarProgress;
        player.getDurationHandler().postDelayed(player.getUpdateSeekBarTime(), 100);
        this.tv_duration = tv_duration;
        this.imgbtn_play = imgbtn_play;
        this.mprogressbar = mprogressbar;
        player.viewUpdateUtility = this;
        playerUtility = player;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.seekBar) {
            /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
            if (mPlayer != null && mPlayer.isPlaying()) {
                SeekBar sb = (SeekBar) v;
                Log.v("Motion event", "Event state" + event.getAction());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sb.setTag(10);
                    Log.v("Touch event", "Event is down");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sb.setTag(1);
                    long duration = mPlayer.getDuration();
                    long newposition = (duration * sb.getProgress()) / 1000L;
                    sb.setProgress((int) newposition);
                    Log.v("Touch event", "Event is Up");
                } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.v("Touch event", "Event is release");
                } else if (event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                    Log.v("Touch event", "Event is Press");
                }
            }
        }
        return false;
    }

    @Override
    public void changeSeekValue() {
        double timeElapsed = mPlayer.getCurrentPosition();
        //set seekbar progress
        if ((int) seekBarProgress.getTag() != 10) {
            seekBarProgress.setProgress((int) timeElapsed);
        }
        tv_duration.setText(AppUtil.milliSecondsToTimer(mPlayer.getCurrentPosition()));
    }
}