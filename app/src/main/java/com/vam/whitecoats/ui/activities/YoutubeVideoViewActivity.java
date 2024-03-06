package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.interfaces.AwsAndGoogleKey;
import com.vam.whitecoats.utils.AwsAndGoogleKeysServiceClass;
import com.vam.whitecoats.utils.Config;


public class YoutubeVideoViewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private YouTubePlayerView youTubePlayerView;
    private String videoId;
    private static final int RECOVERY_REQUEST = 1;
    private int user_id=0;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.youtube_video_activity);
        youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.YouTubePlayer);


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            videoId=extras.getString("video_id");
            user_id=extras.getInt("login_user_id",0);
        }

        if(videoId==null){
            Toast.makeText(this,"Unable to play video,please try again.",Toast.LENGTH_LONG).show();
            finish();
        }

        //Initialize the YouTube Player//
        //AIzaSyB6KD8Ou3n9-Sdkp36I2i_9BhNFATx3W5c--server
        //AIzaSyBxjGhHcPVxJnIoypDHZyh5blRwViBRUvU --local
        if(Config.GOOGLE_API_KEY!=null && !Config.GOOGLE_API_KEY.isEmpty()){
            youTubePlayerView.initialize(Config.GOOGLE_API_KEY,this);
        }else{
            getGoogleAPIKey();
        }

        /*youTubePlayerView.initialize("AIzaSyBxjGhHcPVxJnIoypDHZyh5blRwViBRUvU",
                new YouTubePlayer.OnInitializedListener() {
                    @Override

//If the YouTube Player is initialized successfully...//

                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

//..then start playing the following video//

                        if(!b) {
                            youTubePlayer.loadVideo(videoId);
                            //youTubePlayer.setFullscreen(true);
                            //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                        }

                    }

                    @Override

//If the initialization fails...//

                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

//...then display a toast//

                        Toast.makeText(YoutubeVideoViewActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }



    private void getGoogleAPIKey() {
        new AwsAndGoogleKeysServiceClass(YoutubeVideoViewActivity.this, user_id, "",(google_api_key, aws_key) -> {
            if(google_api_key!=null && !google_api_key.isEmpty()) {
                youTubePlayerView.initialize(google_api_key, this);
            }else{
                Toast.makeText(this,"Unable to play video,please try again.",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.loadVideo(videoId);
            //youTubePlayer.loadVideo("36YnV9STBqc");
            youTubePlayer.setFullscreen(true);
            youTubePlayer.setShowFullscreenButton(false);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            if(Config.GOOGLE_API_KEY!=null && !Config.GOOGLE_API_KEY.isEmpty()){
                getYouTubePlayerProvider().initialize(Config.GOOGLE_API_KEY,this);
            }else{
                getGoogleAPIKey();
            }
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.YouTubePlayer);
    }
}
