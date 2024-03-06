package com.vam.whitecoats.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.PdfViewerActivity;
import com.vam.whitecoats.ui.activities.VideoStreamActivity;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.PlayerUtility;
import com.vam.whitecoats.utils.PlayerViewUpdateUtility;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

public class ImageViewPagerAdapter extends PagerAdapter {

    private final int feed_id,channel_id;
    private OnTaskCompleted onTaskCompleteListner;
    private String mTitle = "";
    private ArrayList<JSONObject> objectsList = new ArrayList<>();
    Activity mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<String> URLList = new ArrayList<>();
    private View audio_player_view;
    private ImageView img_audio;
    private PlayerViewUpdateUtility currentViewUtility;
    private ImageButton imgbtn_pause;
    private double timeElapsed = 0, finalTime = 0;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private int mediaFileLengthInMilliseconds;


    public PlayerViewUpdateUtility getCurrentViewUtility() {
        return currentViewUtility;
    }

    private void setCurrentViewUtility(PlayerViewUpdateUtility currentViewUtility) {
        this.currentViewUtility = currentViewUtility;
    }

    public ImageViewPagerAdapter(Activity context, ArrayList<JSONObject> objList, String title, int feedID, int channelID, OnTaskCompleted mOnTaskCompleted) {
        mContext = context;
        objectsList = objList;
        onTaskCompleteListner=mOnTaskCompleted;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTitle = title;
        feed_id=feedID;
        channel_id=channelID;
        for (int i = 0; i < objectsList.size(); i++) {
            JSONObject currentfileData = objectsList.get(i);
            if (currentfileData.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase("image")) {
                URLList.add(currentfileData.optString(RestUtils.ATTACH_ORIGINAL_URL));
            }
        }
    }

    @Override
    public int getCount() {
        return objectsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_page, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
        View audio_player_view = itemView.findViewById(R.id.audio_player_view_in_viewpager);

        final ImageButton imgbtn_play;
        final ProgressBar mProgressBar;
        final SeekBar seekBarProgress;
        final TextView tv_duration, tv_fileduration;

        img_audio = (ImageView) audio_player_view.findViewById(R.id.musicImg);
        imgbtn_play = (ImageButton) audio_player_view.findViewById(R.id.playImgbtn);
        seekBarProgress = (SeekBar) audio_player_view.findViewById(R.id.seekBar);
        mProgressBar = (ProgressBar) audio_player_view.findViewById(R.id.audio_loading_progress);
        tv_duration = (TextView) audio_player_view.findViewById(R.id.durationTxt);
        tv_fileduration = (TextView) audio_player_view.findViewById(R.id.fileduration);

        final String attachType = objectsList.get(position).optString(RestUtils.ATTACHMENT_TYPE);
        final String attachment_original_url = objectsList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL);
        if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF) || attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
            //onTaskCompleteListner.onTaskCompleted("");
            Glide.with(mContext)
                    .load(objectsList.get(position).optString(RestUtils.ATTACH_SMALL_URL)).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //supportStartPostponedEnterTransition();
                    onTaskCompleteListner.onTaskCompleted("");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //supportStartPostponedEnterTransition();
                    onTaskCompleteListner.onTaskCompleted("");
                    return false;
                }
            }).apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                    .into(imageView);
        } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
            onTaskCompleteListner.onTaskCompleted("");
            imageView.setVisibility(View.GONE);
            audio_player_view.setVisibility(View.VISIBLE);
            seekBarProgress.setTag(1);
            mFormatBuilder = new StringBuilder();
            mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
            final PlayerUtility utility = new PlayerUtility();
            final PlayerViewUpdateUtility viewUtility = new PlayerViewUpdateUtility(utility, new MediaPlayer(), seekBarProgress, tv_duration, imgbtn_play, mProgressBar);
            seekBarProgress.setOnTouchListener(viewUtility);
            viewUtility.getmPlayer().setAudioStreamType(AudioManager.STREAM_MUSIC);
            viewUtility.getmPlayer().reset();
            try {
                if (attachment_original_url != null && !attachment_original_url.isEmpty()) {
                    viewUtility.getmPlayer().setDataSource(attachment_original_url);
                    viewUtility.getmPlayer().prepareAsync();
                    viewUtility.getmPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            viewUtility.getMprogressbar().setVisibility(View.GONE);
                            imgbtn_play.setVisibility(View.VISIBLE);
                            mediaFileLengthInMilliseconds = viewUtility.getmPlayer().getDuration(); // gets the song length in milliseconds from URL
                            finalTime = viewUtility.getmPlayer().getDuration();
                            seekBarProgress.setMax((int) finalTime);
                            timeElapsed = viewUtility.getmPlayer().getCurrentPosition();
                            seekBarProgress.setProgress((int) timeElapsed);
                            utility.getDurationHandler().postDelayed(utility.getUpdateSeekBarTime(),100);
                            //durationHandler.postDelayed(updateSeekBarTime, 100);
                            tv_duration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getCurrentPosition()));
                            tv_fileduration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getDuration()));
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
            mediaFileLengthInMilliseconds = viewUtility.getmPlayer().getDuration(); // gets the song length in milliseconds from URL
            finalTime = viewUtility.getmPlayer().getDuration();
            seekBarProgress.setMax((int) finalTime);
            timeElapsed = viewUtility.getmPlayer().getCurrentPosition();
            seekBarProgress.setProgress((int) timeElapsed);
            tv_fileduration.setText(AppUtil.milliSecondsToTimer(viewUtility.getmPlayer().getDuration()));
            imgbtn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        // check for already playing
                        currentViewUtility = viewUtility;
                        if (viewUtility.getmPlayer().isPlaying()) {
                            if (viewUtility.getmPlayer() != null) {
                                viewUtility.getmPlayer().pause();
                                // Changing button image to play button
                                imgbtn_play.setImageResource(R.drawable.ic_playaudio);
                            }
                        } else {
                            // Resume song
                            if (viewUtility.getmPlayer() != null) {
                                viewUtility.getmPlayer().start();
                                // Changing button image to pause button
                                imgbtn_play.setImageResource(R.drawable.ic_pause);
                            }
                        }
                        //call when mplayer completed
                        MediaPlayer.OnCompletionListener cListener = new MediaPlayer.OnCompletionListener() {

                            public void onCompletion(MediaPlayer mp) {
                                Log.v("Touch event", "mplayer is completed");
                                imgbtn_play.setImageResource(R.drawable.ic_playaudio);
                            }
                        };
                        viewUtility.getmPlayer().setOnCompletionListener(cListener);

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
        } else {
            //onTaskCompleteListner.onTaskCompleted("");
            Glide.with(mContext)
                    .asBitmap().load(attachment_original_url)
                    .listener(new RequestListener<Bitmap>() {
                                  @Override
                                  public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                                      onTaskCompleteListner.onTaskCompleted("");
                                      return false;
                                  }

                                  @Override
                                  public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                                      // zoomImage.setImage(ImageSource.bitmap(bitmap));
                                      //saveImage(bitmap);
//                                      AppUtil.saveImage(bitmap);
                                      onTaskCompleteListner.onTaskCompleted("");
                                      return false;
                                  }
                              }
                    ) .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                    .into(imageView);

           /* Glide.with(mContext)
                    .load(attachment_original_url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    //supportStartPostponedEnterTransition();
                    onTaskCompleteListner.onTaskCompleted("");
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    //supportStartPostponedEnterTransition();
                    onTaskCompleteListner.onTaskCompleted("");
                    return false;
                }
            })
                    .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                    .into(imageView);*/
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedImagePosition=0;
                if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                    intent.putExtra(RestUtils.CHANNEL_ID,channel_id);
                    intent.putExtra(RestUtils.ATTACHMENT_TYPE, attachType);
                    intent.putExtra(RestUtils.ATTACHMENT_NAME, mTitle);
                    intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                    mContext.startActivity(intent);
                } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                    Intent intent=new Intent(mContext,VideoStreamActivity.class);
                    intent.putExtra("VIDEO_URL",attachment_original_url);
                    mContext.startActivity(intent);
                    /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment_original_url));
                    intent.setDataAndType(Uri.parse(attachment_original_url), "video/*");
                    mContext.startActivity(intent);*/
                } else {
                    String image_url = attachment_original_url;
                    Intent intent = new Intent(mContext, ImageViewer.class);
                    if (!image_url.isEmpty()) {
                        intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                        intent.putExtra(RestUtils.TAG_FILE_PATH, image_url);
                    }
                    intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                    for(int j=0;j<URLList.size();j++){
                        if(URLList.get(j).equalsIgnoreCase(objectsList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL))){
                            selectedImagePosition=j;
                        }
                    }
                    intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (selectedImagePosition + 1) + " of " + URLList.size());
                    intent.putExtra("selectedImagePostion", selectedImagePosition);
                    intent.putStringArrayListExtra("URLList", URLList);
                    mContext.startActivity(intent);
                }
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }
}

