package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.PdfViewerActivity;
import com.vam.whitecoats.ui.activities.VideoStreamActivity;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AudioPlayerClass;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 21-03-2017.
 */

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<JSONObject> itemsList;
    private Context mContext;
    private String section_name;
    private int section_count;
    ArrayList<String> URLList = new ArrayList<String>();
    private AudioPlayerClass audioPlayer;

    public SectionListDataAdapter(Context context, ArrayList<JSONObject> itemsList,String sectionName,int sectionCount) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.section_name=sectionName;
        this.section_count=sectionCount;

        /*for(int i =0;i<itemsList.size();i++)
        {
            JSONObject currentfileData = itemsList.get(i);
            URLList.add(currentfileData.optString("attachment_original_url"));
        }
        Log.i("UrlList",URLList.toString());*/
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.media_tab_list_item_child, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int position) {

        JSONObject currentFileData = itemsList.get(position);
        //holder.itemImage
        boolean isAudioType = false;
        if (section_name.equalsIgnoreCase(RestUtils.TAG_DOCUMENTS)) {
            holder.pdfImage.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.GONE);
        } else if (section_name.equalsIgnoreCase(RestUtils.TAG_VIDEOS)) {
            holder.videoImage.setVisibility(View.VISIBLE);
            holder.pdfImage.setVisibility(View.GONE);
            /*if (currentFileData.has(RestUtils.ATTACHMENT_TYPE) && currentFileData.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                isAudioType = true;
                holder.videoImage.setVisibility(View.GONE);
                holder.itemImage.setImageResource(R.drawable.audio_background);
            }*/
        } else if (section_name.equalsIgnoreCase("audios")) {
            isAudioType = true;
            holder.pdfImage.setVisibility(View.GONE);
            holder.videoImage.setVisibility(View.GONE);
            holder.itemImage.setImageResource(R.drawable.audio_background);
        } else if (section_name.equalsIgnoreCase(RestUtils.TAG_IMAGES)) {
            if (currentFileData.optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                holder.videoImage.setVisibility(View.GONE);
                holder.pdfImage.setVisibility(View.VISIBLE);
                holder.pdfImage.setImageResource(R.drawable.ic_attachment_type_gif);
            } else {
                holder.videoImage.setVisibility(View.GONE);
                holder.pdfImage.setVisibility(View.GONE);
            }
        }

        if(!isAudioType) {
            if (currentFileData.optString(RestUtils.ATTACH_SMALL_URL) != null && !currentFileData.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                /*Picasso.with(mContext)
                        .load(currentFileData.optString(RestUtils.ATTACH_SMALL_URL)).placeholder(R.drawable.default_image_feed)
                        .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(holder.itemImage);*/
                AppUtil.loadImageUsingGlide(mContext,currentFileData.optString(RestUtils.ATTACH_SMALL_URL),holder.itemImage,R.drawable.default_image_feed);
            } else {
                holder.itemImage.setImageResource(R.drawable.default_image_feed);
            }
        }
        holder.file_content_layout.setTag(currentFileData);

        holder.file_content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URLList.clear();
                JSONObject selectedMediaObj=(JSONObject)v.getTag();
                String attachment_original_url = selectedMediaObj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                if(selectedMediaObj.has(RestUtils.ATTACHMENT_TYPE)){
                    if (selectedMediaObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                        if(AppUtil.isConnectingToInternet(mContext)) {
                            //AppUtil.musicPlayer(mContext, attachment_original_url);
                            audioPlayer=new AudioPlayerClass(mContext,attachment_original_url);
                        }
                    }
                    else if(selectedMediaObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)){
                        Intent intent=new Intent(mContext,VideoStreamActivity.class);
                        intent.putExtra("VIDEO_URL",attachment_original_url);
                        mContext.startActivity(intent);
                    } else if (selectedMediaObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase("image")) {
                        Intent intent = new Intent(mContext, ImageViewer.class);
                        if (!attachment_original_url.isEmpty()) {
                            intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                            intent.putExtra(RestUtils.TAG_FILE_PATH, attachment_original_url);
                            URLList.add(attachment_original_url);
                        } else {
                            intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                            intent.putExtra(RestUtils.TAG_FILE_PATH, "");
                        }

                        intent.putStringArrayListExtra("URLList", URLList);
                        intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                        intent.putExtra(RestUtils.TAG_FEED_ID,selectedMediaObj.optString(RestUtils.FEED_ID));
                        intent.putExtra(RestUtils.CHANNEL_ID,selectedMediaObj.optString(RestUtils.CHANNEL_ID));
                        intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                        intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (position + 1) + " of " + section_count);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, PdfViewerActivity.class);
                        intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                        int feed_id=Integer.parseInt(selectedMediaObj.optString(RestUtils.FEED_ID));
                        int channel_id=Integer.parseInt(selectedMediaObj.optString(RestUtils.CHANNEL_ID));
                        intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                        intent.putExtra(RestUtils.CHANNEL_ID,channel_id);
                        intent.putExtra(RestUtils.ATTACHMENT_TYPE,RestUtils.TAG_TYPE_PDF);
                        intent.putExtra(RestUtils.ATTACHMENT_NAME, (position + 1) + " of " + section_count);
                        intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                        mContext.startActivity(intent);
                    }
                }
                else {
                    if (section_name.equalsIgnoreCase(RestUtils.TAG_VIDEOS)) {
                        Intent intent=new Intent(mContext,VideoStreamActivity.class);
                        intent.putExtra("VIDEO_URL",attachment_original_url);
                        mContext.startActivity(intent);
                    } else if (section_name.equalsIgnoreCase(RestUtils.TAG_IMAGES)) {
                        Intent intent = new Intent(mContext, ImageViewer.class);
                        if (!attachment_original_url.isEmpty()) {
                            intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                            intent.putExtra(RestUtils.TAG_FILE_PATH, attachment_original_url);
                            URLList.add(attachment_original_url);
                        } else {
                            intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                            intent.putExtra(RestUtils.TAG_FILE_PATH, "");
                        }

                        intent.putStringArrayListExtra("URLList", URLList);
                        intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                        intent.putExtra(RestUtils.TAG_FEED_ID,selectedMediaObj.optString(RestUtils.FEED_ID));
                        intent.putExtra(RestUtils.CHANNEL_ID,selectedMediaObj.optString(RestUtils.CHANNEL_ID));
                        intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                        intent.putExtra(RestUtils.TAG_IMAGE_POSITION, (position + 1) + " of " + section_count);
                        mContext.startActivity(intent);
                    } else {
                        Intent intent = new Intent(mContext, PdfViewerActivity.class);
                        intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                        int feed_id=Integer.parseInt(selectedMediaObj.optString(RestUtils.FEED_ID));
                        int channel_id=Integer.parseInt(selectedMediaObj.optString(RestUtils.CHANNEL_ID));
                        intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                        intent.putExtra(RestUtils.CHANNEL_ID,channel_id);
                        intent.putExtra(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_PDF);
                        intent.putExtra(RestUtils.ATTACHMENT_NAME, (position + 1) + " of " + section_count);
                        intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                        mContext.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage,pdfImage,videoImage;
        public RelativeLayout file_content_layout;
        public SingleItemRowHolder(View view) {
            super(view);
            this.itemImage = (ImageView) view.findViewById(R.id.media_tab_section_image);
            file_content_layout=(RelativeLayout)view.findViewById(R.id.file_content_layout);
            this.pdfImage=(ImageView)view.findViewById(R.id.pdf_icon);
            this.videoImage=(ImageView)view.findViewById(R.id.video_icon);



            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels/4;
            int height = displayMetrics.heightPixels/6;

            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(width, height);
            itemImage.setLayoutParams(relativeParams);

        }

    }

    public void pauseAudio(){
        if(audioPlayer!=null) {
            audioPlayer.pausePlaying();
        }
    }

    public void resumePlaying(){
        if(audioPlayer!=null) {
            audioPlayer.startPlaying();
        }
    }

}
