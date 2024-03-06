package com.vam.whitecoats.ui.adapters;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CategoryFeedAttachment;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class FeedsCategoryDistributionAdapter extends RecyclerView.Adapter {
    private final CategoryFeedsItemClickListener listner;
    private ArrayList<CategoryFeeds> dataList = new ArrayList<>();
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    public FeedsCategoryDistributionAdapter(CategoryFeedsItemClickListener listener) {
        this.listner=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.no_sub_category_distribution_item, viewGroup, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                DataObjectHolder viewHolder = (DataObjectHolder) holder;
                CategoryFeeds dataModel = dataList.get(position);
                viewHolder.feed_title.setText(dataModel.getTitle());
                if(dataModel.getShortDesc()!=null) {
                    viewHolder.feed_desc.setVisibility(View.VISIBLE);
                    viewHolder.feed_desc.setText(AppUtil.linkifyHtml(dataModel.getShortDesc(), Linkify.WEB_URLS));
                }else{
                    viewHolder.feed_desc.setVisibility(View.GONE);
                }
                if (dataModel.getAttachmentDetails()!=null && dataModel.getAttachmentDetails().size()>0) {
                    CategoryFeedAttachment attachmentDetailsObj = dataModel.getAttachmentDetails().get(0);
                    String attachmentUrl =attachmentDetailsObj.getAttachmentOriginalUrl();
                    if(!attachmentUrl.isEmpty()) {
                        ((DataObjectHolder) holder).card_lay.setVisibility(View.VISIBLE);
                        String attachmentType =attachmentDetailsObj.getAttachmentType();
                        if(attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)){
                            ((DataObjectHolder) holder).articleImage1.setVisibility(View.GONE);
                            ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                            ((DataObjectHolder) holder).feed_image.setBackgroundResource(R.drawable.audio_rounded_outline);
                            ((DataObjectHolder) holder).feed_image.setImageResource(R.drawable.ic_attachment_type_audio);
                            ((DataObjectHolder) holder).feed_image.setScaleType(ImageView.ScaleType.CENTER);
                        }else{
                            if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                                if (attachmentDetailsObj.getAttachmentSmallUrl().contains(RestUtils.TAG_TYPE_GIF)) {
                                    ((DataObjectHolder) holder).attachment_video_play.setVisibility(View.GONE);
                                    ((DataObjectHolder) holder).articleImage1.setVisibility(View.VISIBLE);
                                    ((DataObjectHolder) holder).articleImage1.setImageResource(R.drawable.ic_attachment_type_gif);
                                } else {
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
                            if(attachmentDetailsObj.getAttachmentSmallUrl()!=null) {
                                ((DataObjectHolder) holder).feed_image.setBackgroundResource(R.drawable.round_outline);
                                ((DataObjectHolder) holder).feed_image.setScaleType(ImageView.ScaleType.FIT_XY);
                                AppUtil.loadImageUsingGlide(((DataObjectHolder) holder).feed_image.getContext(),attachmentDetailsObj.getAttachmentSmallUrl(),((DataObjectHolder) holder).feed_image,R.drawable.feeds_ph_logo);
                                ((DataObjectHolder) holder).feed_image.setClipToOutline(true);
                            }
                        }
                    }else{
                        ((DataObjectHolder) holder).card_lay.setVisibility(View.GONE);
                    }

                } else {
                    ((DataObjectHolder) holder).card_lay.setVisibility(View.GONE);
                }
                ((DataObjectHolder)holder).feeds_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listner.onItemClicked(dataModel);
                    }
                });
            } else if (holder instanceof SubCategoriesAdapter.ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setPadding(0, 40, 0, 40);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(dataList!=null){
            return dataList.size();
        }else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_PROG : VIEW_ITEM;
    }

    public void setDataList(ArrayList<CategoryFeeds> feedsList) {
        dataList=feedsList;
        notifyDataSetChanged();

    }

    public void setListExhausted() {
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder{

        private  RelativeLayout feeds_lay;
        private  FrameLayout card_lay;
        private  ImageView attachment_video_play,articleImage1,articleImage2;
        private  TextView feed_desc;
        private  TextView feed_title;
        private  ImageView feed_image;

        public DataObjectHolder(@NonNull View itemView) {
            super(itemView);
            feed_image = itemView.findViewById(R.id.category_feed_image);
            feed_title = itemView.findViewById(R.id.feed_title);
            feed_desc=itemView.findViewById(R.id.feed_desc);
            feeds_lay=itemView.findViewById(R.id.feeds_lay);
            card_lay=itemView.findViewById(R.id.card_lay);
            attachment_video_play=itemView.findViewById(R.id.attachment_video_play);
            articleImage1 = (ImageView) itemView.findViewById(R.id.attachment_icon1);
            articleImage2 = (ImageView) itemView.findViewById(R.id.attachment_icon2);
        }
    }

    public  class ProgressViewHolder extends RecyclerView.ViewHolder{

        private final AVLoadingIndicatorView progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = (AVLoadingIndicatorView) itemView.findViewById(R.id.avIndicator);

        }
    }
    public void addDummyItemToList(){
        dataList.add(null);
        notifyItemInserted(this.dataList.size());
    }
    public void removeDummyItemFromList(){
        dataList.remove(null);
        notifyItemRemoved(dataList.size());
    }
}
