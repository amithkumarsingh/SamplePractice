package com.vam.whitecoats.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vam.whitecoats.R;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

public class AudioPlayerClass {

    private ImageButton imgbtn_play;
    private PlayerViewUpdateUtility viewUtility;
    private PlayerUtility utility;

    public AudioPlayerClass(Context mContext, String attachment_original_url) {
        final int[] mediaFileLengthInMilliseconds = new int[1];
        final double[] timeElapsed = {0};
        final double[] finalTime = { 0 };
        final PlayerViewUpdateUtility[] currentViewUtility = new PlayerViewUpdateUtility[1];
        View modelBottomSheet = LayoutInflater.from(mContext).inflate(R.layout.activity_audio_player_acivity, null);
        BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        dialog.setContentView(modelBottomSheet);
        ImageView img_audio = (ImageView) modelBottomSheet.findViewById(R.id.musicImg);
        imgbtn_play = (ImageButton) modelBottomSheet.findViewById(R.id.playImgbtn);
        SeekBar seekBarProgress = (SeekBar) modelBottomSheet.findViewById(R.id.seekBar);
        ProgressBar mProgressBar = (ProgressBar) modelBottomSheet.findViewById(R.id.audio_loading_progress);
        TextView tv_duration = (TextView) modelBottomSheet.findViewById(R.id.durationTxt);
        TextView tv_fileduration = (TextView) modelBottomSheet.findViewById(R.id.fileduration);
        seekBarProgress.setTag(1);
        utility = new PlayerUtility();
        viewUtility = new PlayerViewUpdateUtility(utility, new MediaPlayer(), seekBarProgress, tv_duration, imgbtn_play, mProgressBar);
        seekBarProgress.setOnTouchListener(viewUtility);
        viewUtility.getmPlayer().setAudioStreamType(AudioManager.STREAM_MUSIC);
        startPlaying();
        try {
            if (attachment_original_url != null && !attachment_original_url.isEmpty()) {
                viewUtility.getmPlayer().setDataSource(attachment_original_url);
                viewUtility.getmPlayer().prepareAsync();
                viewUtility.getmPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        viewUtility.getMprogressbar().setVisibility(View.GONE);
                        imgbtn_play.setVisibility(View.VISIBLE);
                        mediaFileLengthInMilliseconds[0] = viewUtility.getmPlayer().getDuration(); // gets the song length in milliseconds from URL
                        finalTime[0] = viewUtility.getmPlayer().getDuration();
                        seekBarProgress.setMax((int) finalTime[0]);
                        timeElapsed[0] = viewUtility.getmPlayer().getCurrentPosition();
                        seekBarProgress.setProgress((int) timeElapsed[0]);
                        utility.getDurationHandler().postDelayed(utility.getUpdateSeekBarTime(),100);
                        //durationHandler.postDelayed(updateSeekBarTime, 100);
                        tv_duration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getCurrentPosition()));
                        tv_fileduration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getDuration()));
                        imgbtn_play.performClick();

                    }
                });
                viewUtility.getmPlayer().setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                        Log.e("bufferUpdate", "buffer updated");
                    }
                });
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaFileLengthInMilliseconds[0] = viewUtility.getmPlayer().getDuration(); // gets the song length in milliseconds from URL
        finalTime[0] = viewUtility.getmPlayer().getDuration();
        seekBarProgress.setMax((int) finalTime[0]);
        timeElapsed[0] = viewUtility.getmPlayer().getCurrentPosition();
        seekBarProgress.setProgress((int) timeElapsed[0]);
        tv_fileduration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getDuration()));
        imgbtn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for already playing
                currentViewUtility[0] = viewUtility;
                if (viewUtility.getmPlayer().isPlaying()) {
                    pausePlaying();
                } else {
                    // Resume song
                    startPlaying();
                    //call when mplayer completed
                    MediaPlayer.OnCompletionListener cListener = new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mp) {
                            Log.v("Touch event", "mplayer is completed");
                            imgbtn_play.setImageResource(R.drawable.ic_playaudio);
                            dialog.dismiss();
                        }
                    };
                    viewUtility.getmPlayer().setOnCompletionListener(cListener);
                }
            }
        });




        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (viewUtility.getmPlayer() == null) {
                    return;
                }
                if (!fromUser) {
                    // We're not interested in programmatically generated changes to
                    // the progress bar's position.
                    return;
                }
                long duration = viewUtility.getmPlayer().getDuration();
                Log.v("Seekbar progress", "Progress value " + seekBar.getTag());
                if ((int) seekBar.getTag() == 1) {
                    viewUtility.getmPlayer().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                stopPlaying();

            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) modelBottomSheet.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();



        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            viewUtility.getmPlayer().stop();
                            dialog.cancel();
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            viewUtility.getmPlayer().stop();
                            dialog.cancel();
                            state = "HIDDEN";
                            break;
                        }
                    }

                    // Toast.makeText(mContext, "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
        dialog.show();
    }

    public void stopPlaying() {
        if (viewUtility!=null && viewUtility.getmPlayer() != null) {
            viewUtility.getmPlayer().stop();
        }
    }

    public void pausePlaying() {
        if (viewUtility!=null && viewUtility.getmPlayer() != null) {
            viewUtility.getmPlayer().pause();
            // Changing button image to play button
            imgbtn_play.setImageResource(R.drawable.ic_playaudio);
        }
    }

    public void startPlaying() {
        if (viewUtility!=null && viewUtility.getmPlayer() != null) {
            viewUtility.getmPlayer().start();
            // Changing button image to pause button
            imgbtn_play.setImageResource(R.drawable.ic_pause);
        }
    }

    public MediaPlayer getAudioPlayer(){
        return viewUtility.getmPlayer();
    }

}
