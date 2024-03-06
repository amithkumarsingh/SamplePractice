package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tejaswini on 16-11-2017.
 */

public class RecommendationsRecyclerAdapter extends RecyclerView.Adapter {
    public static final String TAG = RecommendationsRecyclerAdapter.class.getSimpleName();
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private static final int VIEW_FOOTER = 2;
    private final OnCustomClickListener clickListner;
    Context context;
    private final ArrayList<JSONObject> recommendationsArray;
    int doc_id;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int lastVisibleItem;
    private com.vam.whitecoats.ui.activities.FeedSearchActivity mfeedSearchActivity;


    public RecommendationsRecyclerAdapter(Context mContext, ArrayList<JSONObject> recommendationsList, int mdoc_Id, RecyclerView recyclerView, OnCustomClickListener onCustomClickListener) {
        context = mContext;
        recommendationsArray = recommendationsList;
        doc_id = mdoc_Id;
        clickListner = onCustomClickListener;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            /*if (dy > 0) //check for scroll down
                            {*/
                            visibleItemCount = linearLayoutManager.getChildCount();
                            totalItemCount = linearLayoutManager.getItemCount();
                            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                            lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                            if (dy < 0) {
                                return;
                            }
                            if (!loading) {
                                if (lastVisibleItem != RecyclerView.NO_POSITION && lastVisibleItem == (totalItemCount - 1)) {
                                    if (AppUtil.isConnectingToInternet(context)) {
                                        if (onLoadMoreListener != null){
                                            onLoadMoreListener.onLoadMore();
                                            loading = true;
                                        }
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recommendations_list, parent, false);
            viewHolder = new DataObjectHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }

       /* View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendations_list, parent, false);
        viewHolder = new DataObjectHolder(view);*/
        return viewHolder;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public AVLoadingIndicatorView timelineAvi;
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
            timelineAvi=(AVLoadingIndicatorView)v.findViewById(R.id.avIndicator);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                ((DataObjectHolder) holder).attachment_video_type.setVisibility(View.GONE);
                ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                ((DataObjectHolder) holder).articleTitle.setText(recommendationsArray.get(position).optString(RestUtils.TAG_TITLE));
                String description = "";
                description = recommendationsArray.get(position).optString(RestUtils.TAG_SHORT_DESC);
                ((DataObjectHolder) holder).articleBlurb.setText(AppUtil.linkifyHtml(description, Linkify.WEB_URLS));
                if (recommendationsArray.get(position).has(RestUtils.FEED_DESC)) {
                    description = recommendationsArray.get(position).optString(RestUtils.FEED_DESC);
                } else {
                    description = recommendationsArray.get(position).optString(RestUtils.TAG_SHORT_DESC);
                }
                if (context.getClass().getName().equalsIgnoreCase("com.vam.whitecoats.ui.activities.FeedSearchActivity") || context.getClass().getName().equalsIgnoreCase("com.vam.whitecoats.ui.activities.GlobalSearchActivity")) {
                    if (recommendationsArray.get(position).has("attachment_details")) {
                        JSONArray attachmentDetails = recommendationsArray.get(position).optJSONArray("attachment_details");

                        if (attachmentDetails.length() > 0) {
                            ((DataObjectHolder) holder).articleImage.setVisibility(View.VISIBLE);
                            String attachmentType = attachmentDetails.optJSONObject(0).optString("attachment_type");
                            String original_url = attachmentDetails.optJSONObject(0).optString("attachment_original_url");
                            String small_url = attachmentDetails.optJSONObject(0).optString("attachment_small_url");

                            switch (attachmentType.toLowerCase()) {
                                case RestUtils.TAG_TYPE_VIDEO:
                                    ((DataObjectHolder) holder).attachment_video_type.setVisibility(View.VISIBLE);
                                    ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.VISIBLE);
                                    ((DataObjectHolder) holder).articleImage1.setVisibility(View.VISIBLE);
                                    ((DataObjectHolder) holder).articleImage1.setImageResource(R.drawable.ic_attachment_type_video);
                                    if (!small_url.isEmpty()) {
                                        AppUtil.loadAttachmentsBasedOnType(context, attachmentDetails.optJSONObject(0), ((DataObjectHolder) holder).articleImage, ((DataObjectHolder) holder).articleImage1, ((DataObjectHolder) holder).attachment_video_play);
                                    } else {
                                        ((DataObjectHolder) holder).articleImage.setVisibility(View.GONE);
                                    }
                                    break;
                                /**
                                 * Multimedia article
                                 */
                                case RestUtils.TAG_TYPE_AUDIO:
                                    ((DataObjectHolder) holder).attachment_video_type.setVisibility(View.GONE);
                                    ((DataObjectHolder) holder).articleImage.setBackgroundColor(Color.parseColor("#E8E8E8"));
                                    ((DataObjectHolder) holder).articleImage.setImageResource(R.drawable.ic_attachment_type_audio);
                                    ((DataObjectHolder) holder).articleImage.setScaleType(ImageView.ScaleType.CENTER);
                                    ((DataObjectHolder) holder).articleImage.setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                                    if (!small_url.isEmpty()) {
                                        AppUtil.loadAttachmentsBasedOnType(context, attachmentDetails.optJSONObject(0), ((DataObjectHolder) holder).articleImage, ((DataObjectHolder) holder).articleImage1,((DataObjectHolder) holder).attachment_video_play);

                                    } else {
                                        ((DataObjectHolder) holder).articleImage.setVisibility(View.GONE);
                                    }
                                    break;
                            }
                        }else{
                            ((DataObjectHolder) holder).articleImage.setVisibility(View.GONE);
                        }
                    }
                } else {
                    String attachmentUrl=recommendationsArray.get(position).optString(RestUtils.TAG_SMALL_IMAGE_URL);
                        ((DataObjectHolder) holder).feedImage_lay.setVisibility(View.VISIBLE);
                        String attachmentType = recommendationsArray.get(position).optString(RestUtils.ATTACHMENT_TYPE);
                        if(attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)){
                            ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                            ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                            ((DataObjectHolder) holder).articleImage.setBackgroundColor(Color.parseColor("#E8E8E8"));
                            ((DataObjectHolder) holder).articleImage.setImageResource(R.drawable.ic_attachment_type_audio);
                            ((DataObjectHolder) holder).articleImage.setScaleType(ImageView.ScaleType.CENTER);
                        }else {
                            if (!attachmentUrl.isEmpty()) {
                                ((DataObjectHolder) holder).feedImage_lay.setVisibility(View.VISIBLE);
                                if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                                    if (attachmentUrl.contains(RestUtils.TAG_TYPE_GIF)) {
                                        ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                                        ((DataObjectHolder) holder).articleImage1.setVisibility(View.VISIBLE);
                                        ((DataObjectHolder) holder).articleImage1.setImageResource(R.drawable.ic_attachment_type_gif);
                                    }else {
                                        ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                                        ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                                    }
                                } else if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                                    ((DataObjectHolder) holder).articleImage1.setVisibility(View.VISIBLE);
                                    ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                                    ((DataObjectHolder) holder).articleImage1.setImageResource(R.drawable.ic_attachment_type_pdf);
                                } else if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                                    ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                                    ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.VISIBLE);
                                }
                                /*Picasso.with(context)
                                        .load(attachmentUrl.trim()).placeholder(R.drawable.default_channel_logo)
                                        .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                        .into(((DataObjectHolder) holder).articleImage);*/
                                AppUtil.loadImageUsingGlide(context,attachmentUrl.trim(),((DataObjectHolder) holder).articleImage,R.drawable.default_channel_logo);


                            } else {
                                ((DataObjectHolder) holder).feedImage_lay.setVisibility(View.GONE);
                            }
                        }



                    ((DataObjectHolder) holder).articleBlurb.setText(AppUtil.linkifyHtml(description, Linkify.WEB_URLS));
                }
                ((DataObjectHolder) holder).recommendations_list_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AppUtil.isConnectingToInternet(context)) {
                            if (clickListner != null) {
                                clickListner.OnCustomClick(v, position);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return recommendationsArray.size();
    }

    private class DataObjectHolder extends RecyclerView.ViewHolder {

        private final ImageView articleImage,articleImage1,attachment_video_type,articleImage2,attachment_video_play;
        private final TextView articleTitle, articleBlurb;
        private final RelativeLayout recommendations_list_item;
        private final FrameLayout feedImage_lay;

        public DataObjectHolder(View itemView) {
            super(itemView);

            articleImage = (ImageView) itemView.findViewById(R.id.recommendations_list_image);
            articleImage1 = (ImageView) itemView.findViewById(R.id.attachment_icon1);
            articleImage2 = (ImageView) itemView.findViewById(R.id.attachment_icon2);
            attachment_video_type = (ImageView) itemView.findViewById(R.id.attachment_video_type);
            articleTitle = (TextView) itemView.findViewById(R.id.recommendations_list_tile);
            articleBlurb = (TextView) itemView.findViewById(R.id.recommendations_list_blurb);
            attachment_video_play=(ImageView)itemView.findViewById(R.id.attachment_video_play);
            recommendations_list_item = (RelativeLayout) itemView.findViewById(R.id.recommendations_list_item);
            feedImage_lay=(FrameLayout)itemView.findViewById(R.id.attachmenticon);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return recommendationsArray.get(position) != null ? VIEW_ITEM : VIEW_PROG;

    }
}
