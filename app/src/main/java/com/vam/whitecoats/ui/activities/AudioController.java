package com.vam.whitecoats.ui.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.utils.RestUtils;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by pardhasaradhid on 10/23/2017.
 */

public class AudioController extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "AudioController";

    ImageButton imgbtn_play, imgbtn_pause;
    ImageView img_audio;
    TextView tv_duration;
    SeekBar seekBarProgress;
    MediaPlayer mPlayer;
    private Handler durationHandler = new Handler();
    private double timeElapsed = 0, finalTime = 0;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class
    String url = "https://www.ssaurel.com/tmp/mymusic.mp3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        initializeUI();
        Bundle bundle = getIntent().getExtras();
        String attachment_original_url = bundle.getString(RestUtils.ATTACH_ORIGINAL_URL);
        seekBarProgress.setOnTouchListener(this);
        seekBarProgress.setTag(1);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        imgbtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mPlayer.setDataSource(url);
                    mPlayer.prepare();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaFileLengthInMilliseconds = mPlayer.getDuration(); // gets the song length in milliseconds from URL
                mPlayer.start();
                finalTime = mPlayer.getDuration();
                seekBarProgress.setMax((int) finalTime);
                timeElapsed = mPlayer.getCurrentPosition();
                seekBarProgress.setProgress((int) timeElapsed);
                durationHandler.postDelayed(updateSeekBarTime, 100);
                if(mPlayer != null){
                }
            }
        });

        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer == null) {
                    return;
                }
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }
                long duration = mPlayer.getDuration();
                Log.v("Seekbar progress", "Progress value " + seekBar.getTag());
                if ((int) seekBar.getTag() == 1) {
                    mPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });
        Log.i(TAG, TAG);
    }

    public void initializeUI() {
        Log.i(TAG, TAG);
        img_audio = (ImageView) findViewById(R.id.musicImg);
        imgbtn_play = (ImageButton) findViewById(R.id.playImgbtn);
        //imgbtn_pause = (ImageButton) findViewById(R.id.pauseImgbtn);
        seekBarProgress = (SeekBar) findViewById(R.id.seekBar);
        tv_duration = (TextView) findViewById(R.id.durationTxt);
    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {

        public void run() {
            Log.i(TAG, TAG);
            //get current position
            timeElapsed = mPlayer.getCurrentPosition();
            //set seekbar progress
            if ((int) seekBarProgress.getTag() != 10) {
                seekBarProgress.setProgress((int) timeElapsed);
            }
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            tv_duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));

            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);
        }
    };


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, TAG);
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
}
