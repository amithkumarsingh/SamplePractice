package com.vam.whitecoats.ui.activities;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.vam.whitecoats.R;

public class VideoStreamActivity extends AppCompatActivity {

    private VideoView videoView;

    private MediaController mediaController;
    public int stopPosition=0;
    private MediaPlayer mediaPlayer;
    private String videoUrl;
    private ProgressBar videoLoadProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_stream);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = (VideoView) findViewById(R.id.videoView);

        videoLoadProgressBar=(ProgressBar)findViewById(R.id.videoLoadProgress);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            videoUrl=bundle.getString("VIDEO_URL");
        }
        if(videoUrl==null){
            Toast.makeText(this,"Unable to play video,please try again",Toast.LENGTH_SHORT).show();
            finish();
        }
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(this);
            // Set the videoView that acts as the anchor for the AudioController.
            mediaController.setAnchorView(videoView);
            // Set AudioController for VideoView
            videoView.setMediaController(mediaController);
        }


        try {
            //String videoUrl="https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
            Uri video = Uri.parse(videoUrl);
            videoView.setVideoURI(video);
            //videoView.setVideoURI(Uri.parse(String.valueOf(video)));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoView.requestFocus();

        // When the video file ready for playback.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                videoLoadProgressBar.setVisibility(View.GONE);
                videoView.seekTo(stopPosition);
                if (stopPosition == 0) {
                    videoView.start();
                }



            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });

    }




    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        //savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
//        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        /*stopPosition = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(stopPosition);
        videoView.start();*/
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPosition = videoView.getCurrentPosition(); //stopPosition is an int
        videoView.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        videoView.seekTo(stopPosition);
        videoView.start();
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
