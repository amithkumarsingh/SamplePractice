package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by pardhasaradhid on 5/25/2016.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerAdapter";
    private final JSONArray channelsArray;
    private final OnSocialInteractionListener socialInteractionListener;


    private Context context;
    private ArrayList<JSONObject> channelsList;
    private OnCustomClickListener callback;
    private File myDirectory;
    private int mDoctorId;

    public MyRecycleAdapter(Context mContext, OnCustomClickListener mCallback, int doctorId, ArrayList<JSONObject> mChannelsList, JSONArray aggregatedfeeddataArray, OnSocialInteractionListener mOnSocialInteractionListener) {
        context = mContext;
        callback = mCallback;
        channelsList = mChannelsList;
        channelsArray = aggregatedfeeddataArray;
        socialInteractionListener = mOnSocialInteractionListener;
        mDoctorId = doctorId;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {

        //private ImageView imageView1, imageView2, imageView3;
        View channelNameLayout;
        TextView tcHealthDay, tvHealthDay, textView1, textView3, textView5, tvdate, noData;
        Button viewcompletedetails;
        private ViewGroup channelRootLayout, feedsLayout;
        private View customView;
        RoundedImageView channelLog;

        public DataObjectHolder(View itemView) {
            super(itemView);

            channelRootLayout = (ViewGroup) itemView.findViewById(R.id.channel_root_layout);
            feedsLayout = (ViewGroup) itemView.findViewById(R.id.dashboard_feeds_layout);
            channelNameLayout = (View) itemView.findViewById(R.id.channelNameLayout);
            tcHealthDay = (TextView) itemView.findViewById(R.id.tv_healthDay_title);
            tvHealthDay = (TextView) itemView.findViewById(R.id.tv_healthDay);
            viewcompletedetails = (Button) itemView.findViewById(R.id.viewcompletedetails);
            channelLog = (RoundedImageView) itemView.findViewById(R.id.channelLog);
            noData = (TextView) itemView.findViewById(R.id.noData);
            Log.i(LOG_TAG, "Adding Listener community_attachemt_layout");
        }
    }

    private void setItemData(final DataObjectHolder holder, final int postion) {

        JSONObject aggregatedfeedDataItem = null;
        JSONArray subAgrrementArray = null;
        JSONArray attachment_details_array = null;
        try {
            aggregatedfeedDataItem = channelsList.get(postion);
            aggregatedfeedDataItem = channelsList.get(postion);
            subAgrrementArray = aggregatedfeedDataItem.getJSONArray("feed_data");
            int feedsSize = aggregatedfeedDataItem.optInt("feed_count");
            int todayCount = aggregatedfeedDataItem.optInt("today_count");
            String moreCountText = "";
            if (todayCount > 3) {
                holder.tvHealthDay.setVisibility(View.VISIBLE);
            } else {
                holder.tvHealthDay.setVisibility(View.INVISIBLE);
            }
            holder.tcHealthDay.setText(aggregatedfeedDataItem.optString("feed_provider_name"));
            holder.tvHealthDay.setText("+" + (todayCount - 3) + " new");
            holder.viewcompletedetails.setText("View more from " + aggregatedfeedDataItem.optString("feed_provider_name"));
            holder.viewcompletedetails.setTransformationMethod(null);

            if (aggregatedfeedDataItem.optString("channel_logo") != null && !aggregatedfeedDataItem.optString("channel_logo").isEmpty()) {
                /*Picasso.with(context)
                        .load(aggregatedfeedDataItem.optString("channel_logo").trim()).resize(180, 180).centerInside()
                        .placeholder(R.drawable.default_communitypic) //this is optional the image to display while the url image is downloading
                        .error(R.drawable.default_communitypic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(holder.channelLog);*/
                        Glide.with(context)
                        .load(aggregatedfeedDataItem.optString("channel_logo").trim())
                        .circleCrop()
                        .apply(AppUtil.getRequestOptions(context, ContextCompat.getDrawable(context, R.drawable.default_communitypic)).override(180,180))
                        .into(holder.channelLog);
            } else {
                holder.channelLog.setImageResource(R.drawable.default_channel_logo);
            }


            if (aggregatedfeedDataItem != null && aggregatedfeedDataItem.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Community")) {
                holder.feedsLayout.removeAllViews();
                for (int i = 0; i < subAgrrementArray.length(); i++) {/*
                    final JSONObject subItem = subAgrrementArray.getJSONObject(i);
                    final int position = i;
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    holder.customView = inflater.inflate(R.layout.channel_main, null);
                    holder.customView.setTag(i);
                    ViewGroup feeds_layout = (ViewGroup) holder.customView.findViewById(R.id.feeds_layout);
                    LinearLayout createdby_layout = (LinearLayout) holder.customView.findViewById(R.id.createdby_layout);
                    createdby_layout.setVisibility(View.VISIBLE);
                    TextView feedTitle = (TextView) holder.customView.findViewById(R.id.name_channel);
                    TextView feedDes = (TextView) holder.customView.findViewById(R.id.tv_desc);
                    TextView feedTime = (TextView) holder.customView.findViewById(R.id.tv_time);
                    TextView createdBy = (TextView) holder.customView.findViewById(R.id.tv_createdBy);

                    final ImageView communityImgvw = (ImageView) holder.customView.findViewById(R.id.communityImage);
                    ImageView communityImgvw1=(ImageView) holder.customView.findViewById(R.id.secondImageofCommunity);
                    ImageView communityImgvw2=(ImageView) holder.customView.findViewById(R.id.thirdImageofCommunity);
                    TextView remainingCountText=(TextView)holder.customView.findViewById(R.id.remaining_images_count);

                    TextView label_attachment_name = (TextView) holder.customView.findViewById(R.id.label_attachment_name);
                    TextView label_attachment_size = (TextView) holder.customView.findViewById(R.id.label_attachment_size);
                    TextView comment_in_dashboard = (TextView) holder.customView.findViewById(R.id.comment_textview_dashboard);
                    final ImageView like_icon_dashboard=(ImageView)holder.customView.findViewById(R.id.like_icon_dashboard);
                    final TextView like_label_dashboard = (TextView) holder.customView.findViewById(R.id.like_textview_dashboard);
                    ImageView attachment_icon = (ImageView) holder.customView.findViewById(R.id.attachment_icon);
                    final ViewGroup community_attachemt_layout = (ViewGroup) holder.customView.findViewById(R.id.community_attachment_layout);
                    TextView like_count_dashboard = (TextView) holder.customView.findViewById(R.id.like_count_dashboard);
                    TextView comment_count_dashboard = (TextView) holder.customView.findViewById(R.id.comment_count_dashboard);
                    TextView view_count_dashboard=(TextView)holder.customView.findViewById(R.id.view_count_dashboard);
                    RelativeLayout socialBar_count_dashboard = (RelativeLayout) holder.customView.findViewById(R.id.socialBar_count_dashboard);
                    LinearLayout comment_layout_dashboard=(LinearLayout)holder.customView.findViewById(R.id.comment_layout_dashboard);
                    final LinearLayout like_in_dashboard=(LinearLayout)holder.customView.findViewById(R.id.like_layout_dashboard);
                    LinearLayout social_bar_dashboard=(LinearLayout)holder.customView.findViewById(R.id.social_bar_dashboard);
                    communityImgvw.setVisibility(View.GONE);
                    community_attachemt_layout.setVisibility(View.GONE);
                    feedTitle.setText(subItem.optString("title"));
                    feedTitle.setMaxLines(2);
                    feedTitle.setEllipsize(TextUtils.TruncateAt.END);
                    if(!subItem.optString("feed_desc").isEmpty()){
                        feedDes.setVisibility(View.VISIBLE);
                        feedDes.setText(subItem.optString("feed_desc"));
                        feedDes.setMaxLines(2);
                        feedDes.setEllipsize(TextUtils.TruncateAt.END);
                    }
                    final JSONObject socialInteractionObj = subItem.optJSONObject("socialInteraction");
                    if(socialInteractionObj!=null){
                        AppUtil.toggleSocialBarViewCount(socialInteractionObj,like_count_dashboard,comment_count_dashboard,view_count_dashboard,socialBar_count_dashboard,social_bar_dashboard,like_in_dashboard,comment_layout_dashboard);
                        //AppUtil.increasecommentsCountView(socialInteractionObj,comment_count_dashboard,socialBar_count_dashboard);


                        if(socialInteractionObj.optBoolean("isLike")){
                            like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                            like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                            like_in_dashboard.setTag(true);
                        }
                        else{
                            like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                            like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                            like_in_dashboard.setTag(false);
                        }
                    }
                    else{
                        like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                        like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                        like_in_dashboard.setTag(false);
                    }
                    if(mDoctorId==subItem.optInt("doc_id")){
                        createdBy.setText(R.string.label_you);
                    }
                    else {
                        createdBy.setText(subItem.optString("posted_by"));
                    }
                    if(!subItem.optString("posting_time").isEmpty()) {
                        feedTime.setText(DateUtils.longToMessageListHeaderDate(Long.parseLong(subItem.optString("posting_time"))));
                    }
                    else{
                        feedTime.setText("");
                    }

                    String communityIcon = subItem.optString("attachment_name");
                    attachment_details_array=subItem.optJSONArray("attachment_details");
                    if(attachment_details_array!=null && attachment_details_array.length()>0){
                        if(attachment_details_array.length()>1){
                            AppUtil.loadMultipleAttachments(context,attachment_details_array,communityImgvw,communityImgvw1,communityImgvw2,remainingCountText);
                        }else {
                            communityImgvw1.setVisibility(View.GONE);
                            communityImgvw2.setVisibility(View.GONE);
                            remainingCountText.setVisibility(View.GONE);
                            JSONObject attachment_details_obj = attachment_details_array.optJSONObject(0);
                            label_attachment_name.setText(attachment_details_obj.optString("attachment_s3_name"));
                            *//*if(attachment_details_obj.optString("attachment_size").isEmpty()){
                                label_attachment_size.setVisibility(View.GONE);
                            }
                            else {
                                label_attachment_size.setVisibility(View.VISIBLE);
                                label_attachment_size.setText(AppUtil.humanReadableByteCount(Long.parseLong(attachment_details_obj.optString("attachment_size")), true));
                            }*//*
                            if (attachment_details_obj.optString("attachment_type").equalsIgnoreCase("pdf")) {
                                attachment_icon.setVisibility(View.VISIBLE);
                                attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                                if (attachment_details_obj.optString("attachment_small_url") != null && !attachment_details_obj.optString("attachment_small_url").isEmpty()) {
                                    communityImgvw.setVisibility(View.VISIBLE);
                                    AppUtil.loadImageUsingPicasso(context, attachment_details_obj.optString("attachment_small_url"), communityImgvw);
                                }
                            } else if (attachment_details_obj.optString("attachment_type").equalsIgnoreCase("video")) {
                                attachment_icon.setVisibility(View.VISIBLE);
                                attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                                if (attachment_details_obj.optString("attachment_small_url") != null && !attachment_details_obj.optString("attachment_small_url").isEmpty()) {
                                    AppUtil.loadImageUsingPicasso(context, attachment_details_obj.optString("attachment_small_url"), communityImgvw);
                                    communityImgvw.setVisibility(View.VISIBLE);
                                }
                            } else if (attachment_details_obj.optString("attachment_type").equalsIgnoreCase("image")) {
                                attachment_icon.setVisibility(View.GONE);
                                if (attachment_details_obj.optString("attachment_original_url") != null && !attachment_details_obj.optString("attachment_original_url").isEmpty()) {
                                    AppUtil.loadImageUsingPicasso(context, attachment_details_obj.optString("attachment_original_url"), communityImgvw);
                                    communityImgvw.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                    else if (communityIcon != null && !communityIcon.isEmpty()&&communityIcon != "null") {
                        communityImgvw1.setVisibility(View.GONE);
                        communityImgvw2.setVisibility(View.GONE);
                        attachment_icon.setVisibility(View.GONE);
                        remainingCountText.setVisibility(View.GONE);
                        File myFile = new File(Environment.getExternalStorageDirectory() + "/Whitecoats/feed_pic/" + communityIcon);
                        if(myFile.exists()){

                            Picasso.with(context)
                                    .load(myFile).placeholder(R.drawable.default_image_bg)
                                    .error(R.drawable.default_image_bg)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(communityImgvw);
                            communityImgvw.setVisibility(View.VISIBLE);
                        }
                        else {
                            communityImgvw1.setVisibility(View.GONE);
                            communityImgvw2.setVisibility(View.GONE);
                            remainingCountText.setVisibility(View.GONE);
                            new ImageDownloaderTask(communityImgvw, context, new OnTaskCompleted() {
                                @Override
                                public void onTaskCompleted(String s) {
                                    try {

                                        JSONObject responseJson = new JSONObject(s);
                                        String original_link = responseJson.optString("original_link");
                                        String image_path = responseJson.optString("image_path");
                                        Picasso.with(context)
                                                .load(original_link).placeholder(R.drawable.default_image_bg)
                                                .error(R.drawable.default_image_bg)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                                .into(communityImgvw);
                                        communityImgvw.setVisibility(View.VISIBLE);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).execute(subItem.optString("attachment_name"), "feed_pic", "");
                        }
                    }
                    else{
                        communityImgvw1.setVisibility(View.GONE);
                        communityImgvw2.setVisibility(View.GONE);
                        communityImgvw.setVisibility(View.GONE);
                        remainingCountText.setVisibility(View.GONE);
                    }
                    final JSONObject finalAggregatedfeedDataItem1 = aggregatedfeedDataItem;
                    feeds_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(context, hFeedsSummary.class);
                            in.putExtra("channel_name", finalAggregatedfeedDataItem1.optString("feed_provider_name"));
                            in.putExtra("feed_obj", subItem.toString());
							in.putExtra("position",position);
                            in.putExtra("channel_id",finalAggregatedfeedDataItem1.optInt("channel_id"));
							//context.startActivity(in);
                            ((DashboardActivity) context).startActivityForResult(in, TimeLine.DELETE_ACTION);
                        }
                    });
                    like_in_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!AppUtil.isConnectingToInternet(context)){
                                Toast.makeText(context,context.getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Boolean isLiked=true;
                            if (AppConstants.IS_USER_VERIFIED_CONSTANT == 3) {
                                if ((Boolean) v.getTag()) {
                                    isLiked = false;
                                    like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                                    like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                                    like_in_dashboard.setTag(false);
                                } else {
                                    isLiked = true;
                                    like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                                    like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                                    like_in_dashboard.setTag(true);
                                }
                                if (AppConstants.likeActionList.contains(finalAggregatedfeedDataItem1.optInt("channel_id") + "_" + subItem.optInt("post_id"))) {
                                    return;
                                }
                                socialInteractionListener.onUIupdateForLike(subItem, finalAggregatedfeedDataItem1.optInt("channel_id"), isLiked, subItem.optInt("post_id"));
                                socialInteractionListener.onSocialInteraction(subItem,finalAggregatedfeedDataItem1.optInt("channel_id"),isLiked,subItem.optInt("post_id"));
                            }else if(AppConstants.IS_USER_VERIFIED_CONSTANT == 1){
                                AppUtil.AccessErrorPrompt(context,context.getString(R.string.mca_not_uploaded));
                            }else if (AppConstants.IS_USER_VERIFIED_CONSTANT == 2){
                                AppUtil.AccessErrorPrompt(context,context.getString(R.string.mca_uploaded_but_not_verified));
                            }
                        }
                    });
                    like_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent likeIntent= new Intent(context,CommentsActivity.class);
                            likeIntent.putExtra("post_name",subItem.optString("title"));
                            likeIntent.putExtra("keyboard_enable",true);
                            likeIntent.putExtra("feed_type_Id",subItem.optInt("post_id"));
                            likeIntent.putExtra("Navigation","fromLikesCount");
                            likeIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("likesCount"));
                            likeIntent.putExtra("channel_id",finalAggregatedfeedDataItem1.optInt("channel_id"));
                            context.startActivity(likeIntent);
                        }
                    });
                    comment_layout_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent= new Intent(context,CommentsActivity.class);
                            commentIntent.putExtra("post_name",subItem.optString("title"));
                            commentIntent.putExtra("keyboard_enable",true);
                            commentIntent.putExtra("feed_type_Id",subItem.optInt("post_id"));
                            commentIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("commentsCount"));
                            commentIntent.putExtra("channel_id",finalAggregatedfeedDataItem1.optInt("channel_id"));
                            context.startActivity(commentIntent);
                        }
                    });
                    comment_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent= new Intent(context,CommentsActivity.class);
                            commentIntent.putExtra("post_name",subItem.optString("title"));
                            commentIntent.putExtra("keyboard_enable",false);
                            commentIntent.putExtra("feed_type_Id",subItem.optInt("post_id"));
                            commentIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("commentsCount"));
                            commentIntent.putExtra("channel_id",finalAggregatedfeedDataItem1.optInt("channel_id"));
                            context.startActivity(commentIntent);
                        }
                    });
                    *//*comment_in_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "commented position" + position, Toast.LENGTH_SHORT).show();
                        }
                    });*//*

                    holder.feedsLayout.addView(holder.customView);
                    View seperatorView = new View(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    lp.setMargins(16,16,16,0);
                    seperatorView.setLayoutParams(lp);
                    seperatorView.setBackgroundColor(Color.parseColor("#26231f20"));
                    if (i != 2) {
                        holder.feedsLayout.addView(seperatorView);
                    }
                    if(i==2)
                        break;*/
                }
            } else if (aggregatedfeedDataItem != null && aggregatedfeedDataItem.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase("Content")) {
                holder.feedsLayout.removeAllViews();
                for (int i = 0; i < subAgrrementArray.length(); i++) {
                    /*{
                    final int position=i;
                    final JSONObject subItem = subAgrrementArray.getJSONObject(i);
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    holder.customView = inflater.inflate(R.layout.channel_main, null);
                    holder.customView.setTag(i);
                    ViewGroup feeds_layout = (ViewGroup) holder.customView.findViewById(R.id.feeds_layout);
                    TextView feedTitle = (TextView) holder.customView.findViewById(R.id.name_channel);
                    //TextView feedDes = (TextView) holder.customView.findViewById(R.id.tv_desc);
                    TextView feedTime = (TextView) holder.customView.findViewById(R.id.tv_time);
                    //ImageView feedImage = (ImageView) holder.customView.findViewById(R.id.feedImage);
                    final ImageView communityImgvw = (ImageView) holder.customView.findViewById(R.id.communityImage);
                    final TextView contentURL=(TextView)holder.customView.findViewById(R.id.content_url);
                    final LinearLayout like_in_dashboard = (LinearLayout) holder.customView.findViewById(R.id.like_layout_dashboard);
                    ViewGroup comment_in_dashboard = (ViewGroup) holder.customView.findViewById(R.id.comment_layout_dashboard);
                    final ImageView like_icon_dashboard=(ImageView)holder.customView.findViewById(R.id.like_icon_dashboard);
                    final TextView like_label_dashboard = (TextView) holder.customView.findViewById(R.id.like_textview_dashboard);
                    TextView like_count_dashboard = (TextView) holder.customView.findViewById(R.id.like_count_dashboard);
                    final TextView comment_count_dashboard = (TextView) holder.customView.findViewById(R.id.comment_count_dashboard);
                    TextView view_count_dashboard=(TextView)holder.customView.findViewById(R.id.view_count_dashboard);
                    RelativeLayout socialBar_count_dashboard = (RelativeLayout) holder.customView.findViewById(R.id.socialBar_count_dashboard);
                    LinearLayout comment_layout_dashboard=(LinearLayout)holder.customView.findViewById(R.id.comment_layout_dashboard);
                    LinearLayout social_bar_dashboard=(LinearLayout)holder.customView.findViewById(R.id.social_bar_dashboard);
                    communityImgvw.setVisibility(View.VISIBLE);
                    feedTitle.setText(subItem.optString("title"));
                   */
                }/* if(!subItem.optString("short_desc").isEmpty()){
                        feedDes.setVisibility(View.Go);
                        feedDes.setText(subItem.optString("short_desc"));
                    }*//*
                    final JSONObject socialInteractionObj = subItem.optJSONObject("socialInteraction");
                    if(socialInteractionObj!=null){

                        AppUtil.toggleSocialBarViewCount(socialInteractionObj,like_count_dashboard,comment_count_dashboard,view_count_dashboard,socialBar_count_dashboard,social_bar_dashboard,like_in_dashboard,comment_layout_dashboard);
                        if(socialInteractionObj.optBoolean("isLike")){
                            like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                            like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                            like_in_dashboard.setTag(true);
                        }
                        else{
                            like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                            like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                            like_in_dashboard.setTag(false);
                        }
                    }
                    else{
                        like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                        like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                        like_in_dashboard.setTag(false);
                    }

                    *//*feedDes.setEllipsize(TextUtils.TruncateAt.END);
                    feedDes.setMaxLines(3);*//*
                    contentURL.setText(subItem.optString("content_url"));
                    feedTime.setVisibility(View.GONE);
                    //holder.customView.setOnClickListener(this);
                    final JSONObject finalAggregatedfeedDataItem2 = aggregatedfeedDataItem;
                    feeds_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(context, "position" + position, Toast.LENGTH_SHORT).show();
                            //goToUrl(contentURL.getText().toString());
                            Intent in = new Intent(context, ContentFullView.class);
                            in.putExtra("channel_id",finalAggregatedfeedDataItem2.optInt("channel_id"));
                            in.putExtra("content_provider", finalAggregatedfeedDataItem2.optString("feed_provider_name"));
                            in.putExtra("content_obj", subItem.toString());
                            context.startActivity(in);
                        }
                    });
                    like_in_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean isLiked=true;
                            //AppConstants.likeActionList.add(finalAggregatedfeedDataItem2.optInt("channel_id")+"_"+subItem.optInt("article_id"));
                            if(!AppUtil.isConnectingToInternet(context)){
                                Toast.makeText(context,context.getResources().getString(R.string.no_internet_connection),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (AppConstants.IS_USER_VERIFIED_CONSTANT == 3) {
                                if ((Boolean) v.getTag()) {
                                    isLiked = false;
                                    like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                                    like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                                    like_in_dashboard.setTag(false);
                                } else {
                                    isLiked = true;
                                    like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                                    like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                                    like_in_dashboard.setTag(true);
                                }
                                if (AppConstants.likeActionList.contains(finalAggregatedfeedDataItem2.optInt("channel_id") + "_" + subItem.optInt("article_id"))) {
                                    return;
                                }
                                socialInteractionListener.onUIupdateForLike(subItem, finalAggregatedfeedDataItem2.optInt("channel_id"), isLiked, subItem.optInt("article_id"));
                                socialInteractionListener.onSocialInteraction(subItem,finalAggregatedfeedDataItem2.optInt("channel_id"),isLiked,subItem.optInt("article_id"));
                            }
                            else if(AppConstants.IS_USER_VERIFIED_CONSTANT == 1){
                                AppUtil.AccessErrorPrompt(context,context.getString(R.string.mca_not_uploaded));
                            }else if (AppConstants.IS_USER_VERIFIED_CONSTANT == 2){
                                AppUtil.AccessErrorPrompt(context,context.getString(R.string.mca_uploaded_but_not_verified));
                            }

                        }
                    });

                    like_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent likeIntent= new Intent(context,CommentsActivity.class);
                            likeIntent.putExtra("post_name",subItem.optString("title"));
                            likeIntent.putExtra("keyboard_enable",true);
                            likeIntent.putExtra("feed_type_Id",subItem.optInt("article_id"));
                            likeIntent.putExtra("Navigation","fromLikesCount");
                            likeIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("likesCount"));
                            likeIntent.putExtra("channel_id",finalAggregatedfeedDataItem2.optInt("channel_id"));
                            context.startActivity(likeIntent);
                        }
                    });

                    comment_layout_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent= new Intent(context,CommentsActivity.class);
                            commentIntent.putExtra("post_name",subItem.optString("title"));
                            commentIntent.putExtra("keyboard_enable",true);
                            commentIntent.putExtra("feed_type_Id",subItem.optInt("article_id"));
                            commentIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("commentsCount"));
                            commentIntent.putExtra("channel_id",finalAggregatedfeedDataItem2.optInt("channel_id"));
                            context.startActivity(commentIntent);
                        }
                    });
                    comment_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent= new Intent(context,CommentsActivity.class);
                            commentIntent.putExtra("post_name",subItem.optString("title"));
                            commentIntent.putExtra("keyboard_enable",false);
                            commentIntent.putExtra("feed_type_Id",subItem.optInt("article_id"));
                            commentIntent.putExtra("total_comments",(socialInteractionObj==null)?0:socialInteractionObj.optInt("commentsCount"));
                            commentIntent.putExtra("channel_id",finalAggregatedfeedDataItem2.optInt("channel_id"));
                            context.startActivity(commentIntent);
                        }
                    });
                    if(subItem.optString("small_image")!=null &&!subItem.optString("small_image").isEmpty()) {
                        Picasso.with(context)
                                .load(subItem.optString("small_image").trim()).resize(180, 180).centerInside()
                                .placeholder(R.drawable.default_image_bg) //this is optional the image to display while the url image is downloading
                                .error(R.drawable.default_image_bg)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(communityImgvw);
                    }
                    else{
                        communityImgvw.setVisibility(View.GONE);
                    }
                    holder.feedsLayout.addView(holder.customView);
                    View seperatorView = new View(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            1);
                    lp.setMargins(16,16,16,0);
                    seperatorView.setLayoutParams(lp);
                    seperatorView.setBackgroundColor(Color.parseColor("#26231f20"));
                    *//*if (i != (feedsSize-1)) {
                        holder.feedsLayout.addView(seperatorView);
                    }
*//*
                    if (i != 2) {
                        holder.feedsLayout.addView(seperatorView);
                    }
                    if(i==2)
                        break;
                }*/
            }
            final JSONObject finalAggregatedfeedDataItem = aggregatedfeedDataItem;
            holder.viewcompletedetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.isMediaServiceRequired = true;
                    Intent intent = new Intent(context, TimeLine.class);
                    intent.putExtra(RestUtils.FEED_PROVIDER_NAME, channelsList.get(postion).optString(RestUtils.FEED_PROVIDER_NAME));
                    intent.putExtra(RestUtils.CHANNEL_ID, channelsList.get(postion).optInt(RestUtils.CHANNEL_ID));
                    intent.putExtra(RestUtils.TAG_IS_ADMIN, channelsList.get(postion).optBoolean(RestUtils.TAG_IS_ADMIN));
                    intent.putExtra(RestUtils.TAG_FEED_PROVIDER_TYPE, finalAggregatedfeedDataItem.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));
                    context.startActivity(intent);
                }
            });
            if (subAgrrementArray != null && subAgrrementArray.length() > 3) {
                holder.viewcompletedetails.setVisibility(View.VISIBLE);

            } else {
                holder.viewcompletedetails.setVisibility(View.GONE);
            }
            if (subAgrrementArray != null && subAgrrementArray.length() == 0) {
                holder.noData.setText("No post available");
                holder.noData.setVisibility(View.VISIBLE);
            } else {
                holder.noData.setVisibility(View.GONE);
            }
            holder.channelNameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstants.isMediaServiceRequired = true;
                    Intent intent = new Intent(context, TimeLine.class);
                    intent.putExtra(RestUtils.FEED_PROVIDER_NAME, channelsList.get(postion).optString(RestUtils.FEED_PROVIDER_NAME));
                    intent.putExtra(RestUtils.CHANNEL_ID, channelsList.get(postion).optInt(RestUtils.CHANNEL_ID));
                    intent.putExtra(RestUtils.TAG_IS_ADMIN, channelsList.get(postion).optBoolean(RestUtils.TAG_IS_ADMIN));
                    intent.putExtra(RestUtils.TAG_FEED_PROVIDER_TYPE, finalAggregatedfeedDataItem.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));
                    context.startActivity(intent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

       /* if (channelsList.get(0).getArticlesList().size() > 3) {
        }
        else{
        }*/


    }

    /*private void loadImageUsingPicasso(Context mContext, String attachment_url, ImageView imageView) {
        Picasso.with(mContext)
                .load(attachment_url)
                .placeholder(R.drawable.default_image_bg)
                .error(R.drawable.default_image_bg)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(imageView);
    }*/

    @Override
    public MyRecycleAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channel_dashboard, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(MyRecycleAdapter.DataObjectHolder holder, int position) {
        setItemData(holder, position);
    }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    /**
     * Asynctask for downloading Community Image
     */


    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        context.startActivity(launchBrowser);
    }

}
