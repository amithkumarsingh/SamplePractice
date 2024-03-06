package com.vam.whitecoats.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by pardhasaradhid on 10/26/2017.
 */

public class AudioUtility implements View.OnTouchListener {
    private final String audioSource;
    private View audio_player_view;
    private ImageButton imgbtn_play;
    private SeekBar seekBarProgress;
    private TextView tv_duration, tv_fileduration;
    MediaPlayer mPlayer;
    private Handler durationHandler = new Handler();
    private double timeElapsed = 0, finalTime = 0;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private int mediaFileLengthInMilliseconds;
    private ProgressBar audioProgress;

    public AudioUtility(Context context,View audioPlayerView, String audioURL) {
        this.audioSource = audioURL;
        InitUI(context,audioPlayerView, audioURL);
    }

    private void InitUI(final Context context,View audioView, final String audio_url) {
        audio_player_view = audioView;
        audio_player_view.setVisibility(View.VISIBLE);
        imgbtn_play = (ImageButton) audio_player_view.findViewById(R.id.playImgbtn);
        seekBarProgress = (SeekBar) audio_player_view.findViewById(R.id.seekBar);
        tv_duration = (TextView) audio_player_view.findViewById(R.id.durationTxt);
        tv_fileduration = (TextView) audio_player_view.findViewById(R.id.fileduration);
        audioProgress=(ProgressBar)audio_player_view.findViewById(R.id.audio_loading_progress);
        seekBarProgress.setOnTouchListener(this);
        seekBarProgress.setTag(1);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.reset();
        try {
            if (audio_url != null && !audio_url.isEmpty()) {
                mPlayer.setDataSource(audio_url);
                mPlayer.prepareAsync();
                mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        audioProgress.setVisibility(View.GONE);
                        imgbtn_play.setVisibility(View.VISIBLE);
                        mediaFileLengthInMilliseconds = mPlayer.getDuration(); // gets the song length in milliseconds from URL
                        finalTime = mPlayer.getDuration();
                        seekBarProgress.setMax((int) finalTime);
                        timeElapsed = mPlayer.getCurrentPosition();
                        seekBarProgress.setProgress((int) timeElapsed);
                        durationHandler.postDelayed(updateSeekBarTime, 100);
                        tv_duration.setText(AppUtil.milliSecondsToTimer(mPlayer.getCurrentPosition()));
                        tv_fileduration.setText(AppUtil.milliSecondsToTimer(mPlayer.getDuration()));
                    }
                });
                mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                        Log.e("bufferUpdate","buffer updated");
                    }
                });
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imgbtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for already playing
                if (mPlayer.isPlaying()) {
                    if (mPlayer != null) {
                        mPlayer.pause();
                        // Changing button image to play button
                        imgbtn_play.setImageResource(R.drawable.ic_playaudio);
                    }
                } else {
                    // Resume song
                    if (mPlayer != null) {
                        mPlayer.start();
                        // Changing button image to pause button
                        imgbtn_play.setImageResource(R.drawable.ic_pause);
                        tv_duration.post(mUpdateTime);
                    }
                }
                //call when mplayer completed
                MediaPlayer.OnCompletionListener cListener = new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        Log.v("Touch event", "mplayer is completed");
                        imgbtn_play.setImageResource(R.drawable.ic_playaudio);
                    }
                };
                mPlayer.setOnCompletionListener(cListener);
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
    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {

        public void run() {
            //get current position
            timeElapsed = mPlayer.getCurrentPosition();
            //set seekbar progress
            if ((int) seekBarProgress.getTag() != 10) {
                seekBarProgress.setProgress((int) timeElapsed);
            }
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;

            durationHandler.postDelayed(this, 100);
        }
    };
    private Runnable mUpdateTime = new Runnable() {
        public void run() {
            int currentDuration;
            if (mPlayer!=null && mPlayer.isPlaying()) {
                currentDuration = mPlayer.getCurrentPosition();
                updatePlayer(currentDuration);
                tv_duration.postDelayed(this, 1000);
            }else {
                tv_duration.removeCallbacks(this);
            }
        }
    };

    private void updatePlayer(int currentDuration){
        tv_duration.setText("" + AppUtil.milliSecondsToTimer((long) currentDuration));
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

    public void pauseAudio() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            imgbtn_play.setImageResource(R.drawable.ic_playaudio);
        }
    }

    public void resumeAudio() {
        if (mPlayer != null) {
            mPlayer.start();
            imgbtn_play.setImageResource(R.drawable.ic_pause);
        }
    }

    public void stopAudio() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }
}
