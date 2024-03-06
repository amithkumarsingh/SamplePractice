package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.HorizontalListDataObj;
import com.vam.whitecoats.core.realm.RealmFeedInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HorizontalListDataObj horizontalListDataObj;
    private Context mContext;
    private DisplayMetrics metrics;
    private ArrayList<String> data;
    private LayoutInflater mInflater;


    private int itemMargin=100;
    private int itemWidth=0;

    private RealmManager realmManager;
    Realm realm;
    private int rowPosition;
    private ArrayList<Integer> horizontalListData;
    private LayoutInflater mLayoutInflater;
    private int doctorId;
    public static int horizontalItemSelectedPosition =-1;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private View initialHorizontalLayout =null;

    public HorizontalRecyclerAdapter(Context context, DisplayMetrics metrics, HorizontalListDataObj horizontalListFeedDataObj, int position) {
        this.mInflater = LayoutInflater.from(context);
        this.metrics=metrics;
        this.mContext=context;
        this.horizontalListDataObj=horizontalListFeedDataObj;
        this.rowPosition =position;
        realmManager =new RealmManager(mContext);
        realm = Realm.getDefaultInstance();
        doctorId = realmManager.getDoc_id(realm);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.horizontal_list_row_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder!=null) {
            if(holder instanceof ItemViewHolder) {
                int currentItemWidth = itemWidth;
                if (position == 0) {
                    //currentItemWidth += itemMargin;
                    holder.itemView.setPadding(AppUtil.dpToPx(mContext, 4), 0, 0, 0);
                } else if (position == getItemCount() - 1) {
                    //currentItemWidth += itemMargin;
                    holder.itemView.setPadding(0, 0, AppUtil.dpToPx(mContext, 4), 0);
                }

                int height = holder.itemView.getLayoutParams().height;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(currentItemWidth, height);
                params.setMargins(AppUtil.dpToPx(mContext, 1), 0, AppUtil.dpToPx(mContext, 1), 0);
                holder.itemView.setLayoutParams(params);

                int feedId = horizontalListDataObj.getChildFeedIds().get(position);
                RealmFeedInfo feedObj = realmManager.getFeedJsonByFeedID(feedId);
                if (feedObj == null) {
                    return;
                }
                ItemViewHolder itemViewHolder = ((ItemViewHolder) holder);
                try {
                    JSONObject completeJson = new JSONObject(feedObj.getFeedsJson());
                    JSONObject feedInfoObj = completeJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                    JSONObject channelObj = realmManager.getChannelFromDB("listofChannels", feedObj.getChannelId());
                    if(channelObj==null){
                        return;
                    }
                    itemViewHolder.feedTitle.setText(feedInfoObj.optString(RestUtils.TAG_TITLE));
                    if (feedInfoObj.has(RestUtils.TAG_SHORT_DESC)) {
                        itemViewHolder.feedDesc.setText(feedInfoObj.optString(RestUtils.TAG_SHORT_DESC));
                    } else if (feedInfoObj.has(RestUtils.FEED_DESC)) {
                        itemViewHolder.feedDesc.setText(Html.fromHtml(feedInfoObj.optString(RestUtils.FEED_DESC)));
                    } else {
                        itemViewHolder.feedDesc.setVisibility(View.GONE);
                    }
                    if (!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) {
                        itemViewHolder.post_type.setVisibility(View.VISIBLE);
                        itemViewHolder.post_type.setText(feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase());
                    } else if (!feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).isEmpty()) {
                        itemViewHolder.post_type.setVisibility(View.VISIBLE);
                        itemViewHolder.post_type.setText(feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase());
                    } else {
                        itemViewHolder.post_type.setVisibility(View.GONE);
                    }
                    itemViewHolder.post_made_time.setText(DateUtils.longToMessageListHeaderDate(feedInfoObj.optLong(RestUtils.TAG_POSTING_TIME)));

                    JSONArray attachmentsArraydetail = feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                    if (feedInfoObj.optInt(RestUtils.TAG_TEMPLATE) == 1) {
                        itemViewHolder.doc_name_or_channel_name.setText(channelObj.optString(RestUtils.FEED_PROVIDER_NAME, RestUtils.TAG_COMMUNITY));
                        // set channel logo
                        if (channelObj.has(RestUtils.TAG_CHANNEL_LOGO) && !channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).isEmpty()) {
                            /*Picasso.with(mContext)
                                    .load(channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim()).placeholder(R.drawable.default_channel_logo)
                                    .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(itemViewHolder.doc_image_or_channel_image);*/
                            AppUtil.loadCircularImageUsingLib(mContext,channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim(),itemViewHolder.doc_image_or_channel_image,R.drawable.default_channel_logo);
                            itemViewHolder.doc_image_or_channel_image.setContentDescription(channelObj.optString(RestUtils.TAG_CHANNEL_LOGO));
                        } else {
                            itemViewHolder.doc_image_or_channel_image.setContentDescription("");
                            itemViewHolder.doc_image_or_channel_image.setImageResource(R.drawable.default_communitypic);
                        }
                    } else {
                        if (completeJson.has(RestUtils.TAG_POST_CREATOR) && completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).length() > 0) {
                            if (doctorId == completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optInt(RestUtils.TAG_DOC_ID)) {
                                itemViewHolder.doc_name_or_channel_name.setText(R.string.label_you);
                            } else {
                                itemViewHolder.doc_name_or_channel_name.setText(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_SALUTAION, "Dr. ") + " " + completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_FULL_NAME, "Name"));
                            }
                            if (completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).has(RestUtils.TAG_PROFILE_URL) && !completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).isEmpty()) {
                                /*Picasso.with(mContext)
                                        .load(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_profilepic)
                                        .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                        .into(itemViewHolder.doc_image_or_channel_image);*/
                                AppUtil.loadCircularImageUsingLib(mContext,completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim(),itemViewHolder.doc_image_or_channel_image,R.drawable.default_profilepic);
                                itemViewHolder.doc_image_or_channel_image.setContentDescription(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL));
                            } else {
                                itemViewHolder.doc_image_or_channel_image.setImageResource(R.drawable.default_profilepic);
                                itemViewHolder.doc_image_or_channel_image.setContentDescription("");
                            }
                        }
                    }
                    loadImageBasedOnType(attachmentsArraydetail, itemViewHolder.horizontal_feed_image_view_card, itemViewHolder.feeds_image, itemViewHolder.attachment_type_pdf, itemViewHolder.attachment_type_video, feedInfoObj);
                    if (feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0) > 0) {
                        itemViewHolder.viewCount.setVisibility(View.VISIBLE);
                        if (feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0) == 1) {
                            itemViewHolder.viewCount.setText(AppUtil.suffixNumber(feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " view");
                        } else {
                            itemViewHolder.viewCount.setText(AppUtil.suffixNumber(feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " views");
                        }
                    } else {
                        itemViewHolder.viewCount.setVisibility(View.GONE);
                    }

                    itemViewHolder.mParentView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int feedId = horizontalListDataObj.getChildFeedIds().get(position);
                            RealmFeedInfo feedObj = realmManager.getFeedJsonByFeedID(feedId);
                            if (feedObj == null) {
                                return;
                            }
                            horizontalItemSelectedPosition = position;
                            horizontalListDataObj.setSelectedPosition(position);
                            String eventName="";
                            JSONObject jsonObject=new JSONObject();
                            try {
                                jsonObject.put("DocID",realmManager.getUserUUID(realm));

                            if(horizontalListDataObj.getHorizontalListTitle().equalsIgnoreCase("Suggested Feeds")){
                                eventName="DashboardSuggestedFeedFullView";
                                jsonObject.put("ChannelID",feedObj.getChannelId());
                                jsonObject.put("FeedId",feedObj.getFeedId());
                            }else{
                                eventName="DashboardRelateddFeedFullView";
                                jsonObject.put("RelatedChannelID",feedObj.getChannelId());
                                jsonObject.put("RelatedFeedID",feedObj.getFeedId());
                                jsonObject.put("FeedID",horizontalListDataObj.getParentFeedId());
                                jsonObject.put("ChannelID",horizontalListDataObj.getParentChannelId());
                            }

                                    AppUtil.logUserUpShotEvent(eventName,AppUtil.convertJsonToHashMap(jsonObject));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            JSONObject channelObj = realmManager.getChannelFromDB("listofChannels", feedObj.getChannelId());
                            try {
                                JSONObject completeJson = new JSONObject(feedObj.getFeedsJson());
                                JSONObject feedInfoObj = completeJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                                if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                                    Intent in = new Intent(mContext, ContentFullView.class);
                                    in.putExtra(RestUtils.CHANNEL_ID, feedObj.getChannelId());
                                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJson.toString());
                                    mContext.startActivity(in);
                                } else {
                                    boolean isNetworkChannel = false;
                                    if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                                        isNetworkChannel = true;
                                    }
                                    Intent in = new Intent();
                                    if(feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                                        in.setClass(mContext, JobFeedCompleteView.class);
                                    }else{
                                        in.setClass(mContext,FeedsSummary.class);
                                    }
                                    in.putExtra(RestUtils.CHANNEL_ID, feedObj.getChannelId());
                                    in.putExtra(RestUtils.CHANNEL_NAME, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                                    in.putExtra(RestUtils.TAG_POSITION, rowPosition);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJson.toString());
                                    in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
                                    mContext.startActivity(in);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else if(holder instanceof LoadingViewHolder){

            }
        }
    }

    private void loadImageBasedOnType(JSONArray attachmentsArraydetail, CardView horizontal_feed_image_view_card, ImageView feeds_image, ImageView attachment_type_pdf, ImageView attachment_type_video, JSONObject feedInfoObj) {
        if(attachmentsArraydetail.length()>0){
            horizontal_feed_image_view_card.setVisibility(View.VISIBLE);
            String attachmentUrl = attachmentsArraydetail.optJSONObject(0).optString(RestUtils.ATTACH_SMALL_URL);
            String attachmentType = attachmentsArraydetail.optJSONObject(0).optString(RestUtils.ATTACHMENT_TYPE);
            if(attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)){
                attachment_type_pdf.setVisibility(View.GONE);
                attachment_type_video.setVisibility(View.GONE);
                feeds_image.setBackgroundColor(Color.parseColor("#E8E8E8"));
                feeds_image.setImageResource(R.drawable.ic_attachment_type_audio);
                feeds_image.setScaleType(ImageView.ScaleType.CENTER);
            }else {
                if (!attachmentUrl.isEmpty()) {
                    if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                        if (attachmentUrl.contains(RestUtils.TAG_TYPE_GIF)) {
                            attachment_type_video.setVisibility(View.GONE);
                            attachment_type_pdf.setVisibility(View.VISIBLE);
                            attachment_type_pdf.setImageResource(R.drawable.ic_attachment_type_gif);
                        }else {
                            attachment_type_pdf.setVisibility(View.GONE);
                            attachment_type_video.setVisibility(View.GONE);
                        }
                    } else if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                        attachment_type_pdf.setVisibility(View.VISIBLE);
                        attachment_type_video.setVisibility(View.GONE);
                    } else if (attachmentType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                        attachment_type_pdf.setVisibility(View.GONE);
                        attachment_type_video.setVisibility(View.VISIBLE);
                    }
                    /*Picasso.with(mContext)
                            .load(attachmentUrl.trim()).placeholder(R.drawable.default_channel_logo)
                            .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                            .into(feeds_image);*/
                    AppUtil.loadImageUsingGlide(mContext,attachmentUrl.trim(),feeds_image,R.drawable.default_channel_logo);




                } else {
                    horizontal_feed_image_view_card.setVisibility(View.GONE);
                }
            }

        }else{
            if (feedInfoObj.has(RestUtils.TAG_SMALL_IMAGE) && !feedInfoObj.optString(RestUtils.TAG_SMALL_IMAGE).isEmpty()) {
                horizontal_feed_image_view_card.setVisibility(View.VISIBLE);
                /*Picasso.with(mContext)
                        .load(feedInfoObj.optString(RestUtils.TAG_SMALL_IMAGE).trim()).placeholder(R.drawable.default_channel_logo)
                        .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(feeds_image);*/
                AppUtil.loadImageUsingGlide(mContext,feedInfoObj.optString(RestUtils.TAG_SMALL_IMAGE).trim(),feeds_image,R.drawable.default_channel_logo);
            }else{
                horizontal_feed_image_view_card.setVisibility(View.GONE);
            }
        }
    }
    @Override
    public int getItemCount() {
        return horizontalListDataObj==null ?0:horizontalListDataObj.getChildFeedIds().size();
    }
    @Override
    public int getItemViewType(int position) {
        return horizontalListDataObj.getChildFeedIds().get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView feedTitle,feedDesc,doc_name_or_channel_name,viewCount,post_type,post_made_time;
        ImageView feeds_image,attachment_type_pdf,attachment_type_video;
        FrameLayout horizontal_feed_image_view_lay;
        CardView horizontal_feed_image_view_card;
        RoundedImageView doc_image_or_channel_image;
        private View mParentView;
        ItemViewHolder(View itemView) {
            super(itemView);
            mParentView=itemView;
             feedTitle = itemView.findViewById(R.id.horizontal_feed_title);
             feedDesc=itemView.findViewById(R.id.horizontal_feed_description);
             feeds_image=itemView.findViewById(R.id.horizontal_feed_image_view);
             doc_name_or_channel_name=itemView.findViewById(R.id.doc_name_or_channel_name);
             doc_image_or_channel_image=itemView.findViewById(R.id.doc_image_or_channel_image);
             attachment_type_pdf=itemView.findViewById(R.id.attachment_type_pdf);
             attachment_type_video=itemView.findViewById(R.id.attachment_type_video);
             horizontal_feed_image_view_lay=itemView.findViewById(R.id.horizontal_feed_image_view_lay);
             horizontal_feed_image_view_card=itemView.findViewById(R.id.image_view_cardView);
             post_type=itemView.findViewById(R.id.post_type);
             post_made_time=itemView.findViewById(R.id.post_made_time);

             viewCount=itemView.findViewById(R.id.num_of_view);
            /*myTextView = itemView.findViewById(R.id.tvTitle);
            relativeLayout = itemView.findViewById(R.id.container);
            button = itemView.findViewById(R.id.btnToggle);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL)
                        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                    else{
                        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                    }
                }
            });*/
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.itemLoadingProgress);
        }
    }

    public void updateDisplayMetrics(){
        itemWidth = metrics.widthPixels - itemMargin * 2;
    }

    public void setItemMargin(int margin){
        this.itemMargin = margin;
    }

    public View getInitialHorizontalLayout(){
        return initialHorizontalLayout;
    }

    /*@Override
    public long getItemId(int position) {
        return horizontalListDataObj.getChildFeedIds().get(position).hashCode();
    }*/
}
