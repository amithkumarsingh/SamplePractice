package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.vam.whitecoats.R;
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

public class HorizontalListAdapter extends PagerAdapter {
    private RealmManager realmManager;
    Realm realm;
    private int rowPosition;
    private Context mContext;
    private ArrayList<Integer> horizontalListData;
    private LayoutInflater mLayoutInflater;
    private int doctorId;
    public static int horizontalItemSelectedPosition =-1;

    public HorizontalListAdapter(Context mContext, ArrayList<Integer> horizontalListFeedData,int position) {
        this.mContext=mContext;
        this.horizontalListData=horizontalListFeedData;
        this.rowPosition =position;
        realmManager =new RealmManager(mContext);
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // Since we want to put 2 additional pages at left & right,
        // the actual size will plus 2.
        return horizontalListData.size() == 0 ? 0 : horizontalListData.size()>2 ?horizontalListData.size() + 2:horizontalListData.size();
    }

    public int getRealCount() {
        return horizontalListData.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.horizontal_list_row_item, container, false);
        TextView feedTitle = itemView.findViewById(R.id.horizontal_feed_title);
        TextView feedDesc=itemView.findViewById(R.id.horizontal_feed_description);
        ImageView feeds_image=itemView.findViewById(R.id.horizontal_feed_image_view);
        TextView doc_name_or_channel_name=itemView.findViewById(R.id.doc_name_or_channel_name);
        RoundedImageView doc_image_or_channel_image=itemView.findViewById(R.id.doc_image_or_channel_image);
        ImageView attachment_type_pdf=itemView.findViewById(R.id.attachment_type_pdf);
        ImageView attachment_type_video=itemView.findViewById(R.id.attachment_type_video);
        FrameLayout horizontal_feed_image_view_lay=itemView.findViewById(R.id.horizontal_feed_image_view_lay);
        CardView horizontal_feed_image_view_card=itemView.findViewById(R.id.image_view_cardView);
        TextView post_type=itemView.findViewById(R.id.post_type);
        TextView post_made_time=itemView.findViewById(R.id.post_made_time);

        TextView viewCount=itemView.findViewById(R.id.num_of_view);
        int modelPosition=position;
        realm = Realm.getDefaultInstance();
        doctorId = realmManager.getDoc_id(realm);

        if(horizontalListData.size()>2) {
            modelPosition = mapPagerPositionToModelPosition(position);
        }
        int feedId = horizontalListData.get(modelPosition);
        RealmFeedInfo feedObj = realmManager.getFeedJsonByFeedID(feedId);
        if(feedObj==null){
            return itemView;
        }
        try {
            JSONObject completeJson = new JSONObject(feedObj.getFeedsJson());
            JSONObject feedInfoObj=completeJson.optJSONObject(RestUtils.TAG_FEED_INFO);
            JSONObject channelObj = realmManager.getChannelFromDB("listofChannels", feedObj.getChannelId());
            feedTitle.setText(feedInfoObj.optString(RestUtils.TAG_TITLE));
            if(feedInfoObj.has(RestUtils.TAG_SHORT_DESC)) {
                feedDesc.setText(feedInfoObj.optString(RestUtils.TAG_SHORT_DESC));
            }else if(feedInfoObj.has(RestUtils.FEED_DESC)){
                feedDesc.setText(Html.fromHtml(feedInfoObj.optString(RestUtils.FEED_DESC)));
            }else{
                feedDesc.setVisibility(View.GONE);
            }
            if(!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) {
                post_type.setVisibility(View.VISIBLE);
                post_type.setText(feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase());
            }else if(!feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).isEmpty()) {
                post_type.setVisibility(View.VISIBLE);
                post_type.setText(feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase());
            }
            else{
                post_type.setVisibility(View.GONE);
            }
            post_made_time.setText(DateUtils.longToMessageListHeaderDate(feedInfoObj.optLong(RestUtils.TAG_POSTING_TIME)));

            JSONArray attachmentsArraydetail = feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
            if (feedInfoObj.optInt(RestUtils.TAG_TEMPLATE) == 1) {
                    doc_name_or_channel_name.setText(channelObj.optString(RestUtils.FEED_PROVIDER_NAME, RestUtils.TAG_COMMUNITY));
                    // set channel logo
                    if (channelObj.has(RestUtils.TAG_CHANNEL_LOGO) && !channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).isEmpty()) {
                        /*Picasso.with(mContext)
                                .load(channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim()).placeholder(R.drawable.default_channel_logo)
                                .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(doc_image_or_channel_image);*/
                        AppUtil.loadCircularImageUsingLib(mContext,channelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim(),doc_image_or_channel_image,R.drawable.default_channel_logo);
                        doc_image_or_channel_image.setContentDescription(channelObj.optString(RestUtils.TAG_CHANNEL_LOGO));
                    } else {
                        doc_image_or_channel_image.setContentDescription("");
                        doc_image_or_channel_image.setImageResource(R.drawable.default_communitypic);
                    }
            } else {
                if (completeJson.has(RestUtils.TAG_POST_CREATOR) && completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).length() > 0) {
                    if (doctorId == completeJson.optInt(RestUtils.TAG_DOC_ID)) {
                        doc_name_or_channel_name.setText(R.string.label_you);
                    } else {
                        doc_name_or_channel_name.setText(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_SALUTAION, "Dr. ") + " " + completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_FULL_NAME, "Name"));
                    }
                    if (completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).has(RestUtils.TAG_PROFILE_URL) && !completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).isEmpty()) {
                        /*Picasso.with(mContext)
                                .load(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_profilepic)
                                .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                .into(doc_image_or_channel_image);*/
                        AppUtil.loadCircularImageUsingLib(mContext,completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim(),doc_image_or_channel_image,R.drawable.default_profilepic);
                        doc_image_or_channel_image.setContentDescription(completeJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL));
                    } else {
                        doc_image_or_channel_image.setImageResource(R.drawable.default_profilepic);
                        doc_image_or_channel_image.setContentDescription("");
                    }
                }
            }
            loadImageBasedOnType(attachmentsArraydetail,horizontal_feed_image_view_card,feeds_image,attachment_type_pdf,attachment_type_video,feedInfoObj);
                if (feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0) > 0) {
                    viewCount.setVisibility(View.VISIBLE);
                    if (feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0) == 1) {
                        viewCount.setText(AppUtil.suffixNumber(feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " view");
                    } else {
                        viewCount.setText(AppUtil.suffixNumber(feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optInt(RestUtils.TAG_VIEW_COUNT, 0)) + " views");
                    }
                } else {
                    viewCount.setVisibility(View.GONE);
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int finalModelPosition = modelPosition;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int feedId = horizontalListData.get(finalModelPosition);
                RealmFeedInfo feedObj = realmManager.getFeedJsonByFeedID(feedId);
                if(feedObj==null){
                    return ;
                }
                horizontalItemSelectedPosition =finalModelPosition;
                JSONObject channelObj = realmManager.getChannelFromDB("listofChannels", feedObj.getChannelId());
                try {
                    JSONObject completeJson = new JSONObject(feedObj.getFeedsJson());
                    JSONObject feedInfoObj=completeJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                    if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                        Intent in = new Intent(mContext, ContentFullView.class);
                        in.putExtra(RestUtils.CHANNEL_ID, feedObj.getChannelId());
                        in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJson.toString());
                        mContext.startActivity(in);
                    } else {
                        boolean isNetworkChannel=false;
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
        container.addView(itemView);
        return itemView;
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
                    AppUtil.loadImageUsingGlide(mContext,attachmentUrl.trim(),feeds_image,R.drawable.default_channel_logo);
                } else {
                    horizontal_feed_image_view_card.setVisibility(View.GONE);
                }
            }

        }else{
            if (feedInfoObj.has(RestUtils.TAG_SMALL_IMAGE) && !feedInfoObj.optString(RestUtils.TAG_SMALL_IMAGE).isEmpty()) {
                horizontal_feed_image_view_card.setVisibility(View.VISIBLE);
                AppUtil.loadImageUsingGlide(mContext,feedInfoObj.optString(RestUtils.TAG_SMALL_IMAGE).trim(),feeds_image,R.drawable.default_channel_logo);
            }else{
                horizontal_feed_image_view_card.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private int mapPagerPositionToModelPosition(int pagerPosition) {
        // Put last page model to the first position.
        if (pagerPosition == 0) {
            return getRealCount() - 1;
        }
        // Put first page model to the last position.
        if (pagerPosition == getRealCount() + 1) {
            return 0;
        }
        return pagerPosition - 1;
    }



}
