package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.PdfViewerActivity;
import com.vam.whitecoats.ui.activities.VideoStreamActivity;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AudioPlayerClass;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by venu on 3/23/17.
 */
public class MediaFilesListAdapter extends RecyclerView.Adapter {

    private final RecyclerView mediaRecycler;
    private final String sectionName;
    private final int sectionCount;
    private GridLayoutManager gridLayoutManager;
    private Context context;
    private ArrayList<JSONObject> filesList = new ArrayList<>();
    ArrayList<String> URLList = new ArrayList<String>();

    private int lastVisibleItem;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    public static final int VIEW_ITEM_WIHT_TITLE = 2;
    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;
    private View customFeedsView;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private AudioPlayerClass audioPlayer;


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        private final ImageView filePreview,pdfIcon,videoIcon;
        private final ViewGroup fileContentLayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            fileContentLayout = (ViewGroup) itemView.findViewById(R.id.file_content_layout);
            filePreview = (ImageView) itemView.findViewById(R.id.media_tab_section_image);
            pdfIcon = (ImageView) itemView.findViewById(R.id.pdf_icon);
            videoIcon = (ImageView) itemView.findViewById(R.id.video_icon);
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int height = displayMetrics.heightPixels/6;
            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            filePreview.setLayoutParams(relativeParams);

        }
    }

    private void setFileData(final RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                //((DataObjectHolder)holder).fileContentLayout.setTag(position);
                //JSONObject currentfileData = filesList.get(position);
                //URLList.add(currentfileData.optString("attachment_original_url"));
                boolean isAudioType=false;
                if(filesList.get(position).has(RestUtils.ATTACHMENT_TYPE)) {
                    if (filesList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                        ((DataObjectHolder) holder).videoIcon.setVisibility(View.VISIBLE);
                    } else if (filesList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                        ((DataObjectHolder) holder).pdfIcon.setVisibility(View.VISIBLE);
                    }else if(filesList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase("image")){
                        if(filesList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)){
                            ((DataObjectHolder) holder).pdfIcon.setVisibility(View.VISIBLE);
                            ((DataObjectHolder) holder).pdfIcon.setImageResource(R.drawable.ic_attachment_type_gif);
                        }else {
                            ((DataObjectHolder) holder).pdfIcon.setVisibility(View.GONE);
                        }
                    }
                    else if (filesList.get(position).optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                        isAudioType=true;
                        ((DataObjectHolder) holder).videoIcon.setVisibility(View.GONE);
                        /*((DataObjectHolder) holder).videoIcon.setImageResource(R.drawable.ic_attachment_type_audio);
                        ((DataObjectHolder)holder).filePreview.setBackgroundColor(Color.parseColor("#E8E8E8"));*/
                        ((DataObjectHolder) holder).filePreview.setImageResource(R.drawable.audio_background);
                    }

                }else{
                    if (sectionName.equalsIgnoreCase(RestUtils.TAG_VIDEOS)) {
                        ((DataObjectHolder) holder).videoIcon.setVisibility(View.VISIBLE);
                    } else if (sectionName.equalsIgnoreCase(RestUtils.TAG_DOCUMENTS)) {
                        ((DataObjectHolder) holder).pdfIcon.setVisibility(View.VISIBLE);
                    }
                }
                if(!isAudioType){
                    loadImagesUsingLib(context, filesList.get(position).optString(RestUtils.ATTACH_SMALL_URL), ((DataObjectHolder) holder).filePreview);
                }

                ((DataObjectHolder)holder).fileContentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        URLList.clear();
                        //int position = (int) v.getTag();
                        JSONObject fileDataObj = filesList.get(position);
                        String attachment_original_url = fileDataObj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                        if(fileDataObj.has(RestUtils.ATTACHMENT_TYPE)){
                            if (fileDataObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                                Intent intent=new Intent(context,VideoStreamActivity.class);
                                intent.putExtra("VIDEO_URL",attachment_original_url);
                                context.startActivity(intent);
                            }
                            else if (fileDataObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                                if(AppUtil.isConnectingToInternet(context)) {
                                    //AppUtil.musicPlayer(context, attachment_original_url);
                                    audioPlayer=new AudioPlayerClass(context,attachment_original_url);
                                }
                            }else if (fileDataObj.optString(RestUtils.ATTACHMENT_TYPE).equalsIgnoreCase("image")) {
                                URLList.add(attachment_original_url);
                                Intent intent = new Intent(context, ImageViewer.class);
                                if (!attachment_original_url.isEmpty()) {
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, attachment_original_url);
                                } else {
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, "");
                                }
                                intent.putStringArrayListExtra("URLList", URLList);
                                intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                                intent.putExtra(RestUtils.TAG_FEED_ID,fileDataObj.optString(RestUtils.FEED_ID));
                                intent.putExtra(RestUtils.CHANNEL_ID,fileDataObj.optString(RestUtils.CHANNEL_ID));
                                intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                                intent.putExtra(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_IMAGE);
                                intent.putExtra(RestUtils.TAG_IMAGE_POSITION, fileDataObj.optInt(RestUtils.TAG_COUNT)+" of "+sectionCount);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, PdfViewerActivity.class);
                                intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                                int feed_id=Integer.parseInt(fileDataObj.optString(RestUtils.FEED_ID));
                                int channel_id=Integer.parseInt(fileDataObj.optString(RestUtils.CHANNEL_ID));
                                intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                                intent.putExtra(RestUtils.CHANNEL_ID,channel_id);
                                intent.putExtra(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_PDF);
                                intent.putExtra(RestUtils.ATTACHMENT_NAME, fileDataObj.optInt(RestUtils.TAG_COUNT)+" of "+sectionCount);
                                intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                                context.startActivity(intent);
                            }
                        }
                        else{
                            if (sectionName.equalsIgnoreCase(RestUtils.TAG_VIDEOS)) {
                                Intent intent=new Intent(context,VideoStreamActivity.class);
                                intent.putExtra("VIDEO_URL",attachment_original_url);
                                context.startActivity(intent);
                            }
                            else if (sectionName.equalsIgnoreCase("Audios")) {
                                if(AppUtil.isConnectingToInternet(context)) {
                                    //AppUtil.musicPlayer(context, attachment_original_url);
                                    audioPlayer=new AudioPlayerClass(context,attachment_original_url);
                                }
                            }
                            else if (sectionName.equalsIgnoreCase(RestUtils.TAG_IMAGES)) {
                                Intent intent = new Intent(context, ImageViewer.class);
                                if (!attachment_original_url.isEmpty()) {
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, attachment_original_url);
                                } else {
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, "");
                                }
                                intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                                intent.putExtra(RestUtils.TAG_FEED_ID,fileDataObj.optString(RestUtils.FEED_ID));
                                intent.putExtra(RestUtils.CHANNEL_ID,fileDataObj.optString(RestUtils.CHANNEL_ID));
                                intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                                intent.putExtra(RestUtils.TAG_IMAGE_POSITION, fileDataObj.optInt(RestUtils.TAG_COUNT)+" of "+sectionCount);
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, PdfViewerActivity.class);
                                intent.putExtra(RestUtils.NAVIGATATION,RestUtils.TAG_POST_BTN_ENABLE);
                                int feed_id=Integer.parseInt(fileDataObj.optString(RestUtils.FEED_ID));
                                int channel_id=Integer.parseInt(fileDataObj.optString(RestUtils.CHANNEL_ID));
                                intent.putExtra(RestUtils.TAG_FEED_ID,feed_id);
                                intent.putExtra(RestUtils.CHANNEL_ID,channel_id);
                                intent.putExtra(RestUtils.ATTACHMENT_TYPE, RestUtils.TAG_TYPE_PDF);
                                intent.putExtra(RestUtils.ATTACHMENT_NAME, fileDataObj.optInt(RestUtils.TAG_COUNT)+" of "+sectionCount);
                                intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                                context.startActivity(intent);
                            }
                        }

                    }
                });

            }
            else if(holder instanceof SectionHeaderViewHolder){

                ((SectionHeaderViewHolder) holder).sectionHeaderText.setText(filesList.get(position).optString("title").toUpperCase());
            }else {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }

    }

    private void loadImagesUsingLib(Context mContext, String attachment_url, ImageView imageView) {
        if(attachment_url!=null && !attachment_url.trim().isEmpty()) {
            /*Picasso.with(mContext)
                    .load(attachment_url).placeholder(R.drawable.default_image_feed)
                    .error(R.drawable.default_image_feed)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(imageView);*/
            AppUtil.loadImageUsingGlide(mContext,attachment_url,imageView,R.drawable.default_image_feed);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public static class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView sectionHeaderText;

        public SectionHeaderViewHolder(View v) {
            super(v);
            sectionHeaderText = (TextView) v.findViewById(R.id.section_header_text);
        }
    }
    public MediaFilesListAdapter(Context mContext, ArrayList<JSONObject> mediaList, RecyclerView recyclerView,String mSectionName,int mSectionCount) {
        context = mContext;
        filesList=mediaList;
        mediaRecycler=recyclerView;
        sectionName=mSectionName;
        sectionCount=mSectionCount;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            gridLayoutManager = (GridLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            /*if (dy > 0) //check for scroll down
                            {*/
                            visibleItemCount = gridLayoutManager.getChildCount();
                            totalItemCount = gridLayoutManager.getItemCount();
                            pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();
                            lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                            if (!loading) {
                                if (lastVisibleItem != RecyclerView.NO_POSITION && lastVisibleItem == (totalItemCount - 1)) {
                                    if (onLoadMoreListener != null && AppUtil.isConnectingToInternet(context)) {
                                        onLoadMoreListener.onLoadMore();
                                        loading = true;
                                    } else {
                                        loading = false;
                                    }
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    });

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.media_tab_list_item_child, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else if(viewType == VIEW_ITEM_WIHT_TITLE){
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.section_heading, parent, false);
            viewHolder = new SectionHeaderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setFileData(holder, position);
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int displayView;
        if(filesList.get(position)==null){
            displayView=VIEW_PROG;
        }
        else if(filesList.get(position).has(RestUtils.TAG_TITLE)){
            displayView=VIEW_ITEM_WIHT_TITLE;
        }
        else{
            displayView=VIEW_ITEM;
        }
        return displayView;

    }



    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


    public void pauseAudio(){
        if(audioPlayer!=null) {
            audioPlayer.pausePlaying();
        }
    }

    public void resumeAudio(){
        if(audioPlayer!=null) {
            audioPlayer.startPlaying();
        }
    }
}