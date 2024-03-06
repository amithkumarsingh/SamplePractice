package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.FeedSurvey;
import com.vam.whitecoats.core.models.SurveyOption;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.CommentsActivity;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by pardhasaradhid on 6/6/2016.
 */
public class AllFeedsAdapter extends RecyclerView.Adapter {

    private static String LOG_TAG = "AllFeedsAdapter";
    private static MyRecyclerViewAdapter.MyClickListener myClickListener;
    private final String feed_provider_type;
    private final String channel_name;
    private final OnSocialInteractionListener socialInteractionListener;
    private String doctorName;
    private final RealmManager realmManager;
    private final Realm realm;
    private OnFeedItemClickListener feedClickListener;
    private int channel_id;
    private int mDoctorID;
    private Context context;
    private ArrayList<JSONObject> feedsList = new ArrayList<>();
    //private final Activity context;

    private int lastVisibleItem;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private View customFeedsView;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String text_From_Server = "";
    private String url_From_server = "";
    private int lastPosition = -1;
    private LikeActionAsync likeAPICall;

    public void setLikeAPIAsync(LikeActionAsync _likeAPICall) {
        likeAPICall = _likeAPICall;
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ViewGroup socialBar_action_timeline;
        private final View mParentView;
        private final ImageView attachment_video_type1, attachment_video_type2;
        private final LinearLayout surveyRootLayout;
        private final TextView date_webinar, webinar_time, already_registered_text, edited_text_view, webinar_text, webinar_status, ended_text, tv_organizer_details;
        private final Button register_now, join_now;
        private final ImageView already_registered_icon, webinar_icon;
        private final ProgressBar progressBar, simplePB;
        private final LinearLayout webinar_time_date_inner_lay;
        private final Button eoi_lay, eoi_lay_article, view_recording_btn;
        private TextView community_feeds_descpfeeds, content_feeds_desc, tv_date_time_label;
        private final ImageView attachment_icon, attachment_icon1, attachment_icon2, like_icon_timeline, croppedImageView, croppedImageView1,
                croppedImageView2, bookmark_timeline_content_ImageView, bookmark_timeline_community_ImageView, attachment_video_type;
        TextView community_feeds_Title, communityPostCreatedBy, communityPublishedDate, content_feeds_Title, content_specialities, attachment_name, attachment_size, post_type_article, post_type_community, contentPublishedDate, timeline_commented_doc_name, timeline_commented_text, tv_edited, tv_edited_content;
        LinearLayout feedsFullView, postType_date_lay_community, postType_date_lay_content, createdby_layout_timeline, bookmark_timeline_content, bookmark_timeline_community;
        RelativeLayout socialBar_count_timeline, webinar_lay_timeLine, webinar_time_date_lay_fullview;
        TextView like_count_timeline, comment_count_timeline, like_label_timeline, view_count_timeline, remainingCountText, tv_share_count;
        private ViewGroup feedsRootLayout, attachment_layout, like_layout_timeline, comment_layout_timeline, share_layout_timeline;
        View timeline_latest_comment_view;
        RoundedImageView timeline_commented_doc_image, created_doc_picture;
        LinearLayout time_line_report_to_spam;
        LinearLayout bookmark_timeline_content_report_to_spam;

        public DataObjectHolder(View itemView) {
            super(itemView);
            mParentView = itemView;
            mParentView.setOnClickListener(this);
            community_feeds_Title = (TextView) itemView.findViewById(R.id.tv_feeds_title);
            community_feeds_descpfeeds = (TextView) itemView.findViewById(R.id.tv_feeds_descp);
            communityPostCreatedBy = (TextView) itemView.findViewById(R.id.post_createdBy);
            content_feeds_Title = (TextView) itemView.findViewById(R.id.tv_for_content_title);
            content_feeds_desc = (TextView) itemView.findViewById(R.id.tv_for_content_desc);
            content_specialities = (TextView) itemView.findViewById(R.id.tv_for_content_speciality);

            communityPublishedDate = (TextView) itemView.findViewById(R.id.tv_community_published_date);
            tv_edited = (TextView) itemView.findViewById(R.id.edited);
            //feedImage = (ImageView) itemView.findViewById(R.id.community_image);
            croppedImageView = (ImageView) itemView.findViewById(R.id.communityImage);

            croppedImageView1 = (ImageView) itemView.findViewById(R.id.secondImageofCommunity);
            croppedImageView2 = (ImageView) itemView.findViewById(R.id.thirdImageofCommunity);
            remainingCountText = (TextView) itemView.findViewById(R.id.remaining_images_count);

            post_type_article = (TextView) itemView.findViewById(R.id.post_type_article);
            post_type_community = (TextView) itemView.findViewById(R.id.post_type_community);
            contentPublishedDate = (TextView) itemView.findViewById(R.id.tv_community_descpfeeds_article);

            postType_date_lay_community = (LinearLayout) itemView.findViewById(R.id.postType_date_lay_community);
            postType_date_lay_content = (LinearLayout) itemView.findViewById(R.id.postType_date_lay_content);

            // feedsRootLayout = (ViewGroup) itemView.findViewById(R.id.feeds_Layout);
            feedsFullView = (LinearLayout) itemView.findViewById(R.id.feeds_Layout);
            socialBar_count_timeline = (RelativeLayout) itemView.findViewById(R.id.socialBar_count);
            attachment_layout = (ViewGroup) itemView.findViewById(R.id.community_attachment_layout_timeline);
            attachment_icon = (ImageView) itemView.findViewById(R.id.attachment_icon);
            attachment_icon1 = (ImageView) itemView.findViewById(R.id.attachment_icon1);
            attachment_icon2 = (ImageView) itemView.findViewById(R.id.attachment_icon2);
            attachment_name = (TextView) itemView.findViewById(R.id.label_attachment_name_timeline);
            attachment_size = (TextView) itemView.findViewById(R.id.label_attachment_size_timeline);

            like_count_timeline = (TextView) itemView.findViewById(R.id.like_count);
            comment_count_timeline = (TextView) itemView.findViewById(R.id.comment_count);
            view_count_timeline = (TextView) itemView.findViewById(R.id.view_count);
            tv_share_count = (TextView) itemView.findViewById(R.id.tv_share_count);
            socialBar_action_timeline = (ViewGroup) itemView.findViewById(R.id.social_bar_action);

            like_layout_timeline = (ViewGroup) itemView.findViewById(R.id.like_action_layout);
            comment_layout_timeline = (ViewGroup) itemView.findViewById(R.id.comment_action_layout);
            share_layout_timeline = (ViewGroup) itemView.findViewById(R.id.share_layout_dashboard);

            like_label_timeline = (TextView) itemView.findViewById(R.id.like_textview);
            like_icon_timeline = (ImageView) itemView.findViewById(R.id.like_icon);
            createdby_layout_timeline = (LinearLayout) itemView.findViewById(R.id.createdby_layout_timeline);
            created_doc_picture = (RoundedImageView) itemView.findViewById(R.id.created_doc_picture);

            attachment_video_type = (ImageView) itemView.findViewById(R.id.attachment_video_type);
            attachment_video_type1 = (ImageView) itemView.findViewById(R.id.attachment_video_type1);
            attachment_video_type2 = (ImageView) itemView.findViewById(R.id.attachment_video_type2);
            tv_edited_content = (TextView) itemView.findViewById(R.id.edited_content);

            /*LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            customFeedsView = inflater.inflate(R.layout.feeds_layout, null);
            community_feeds_Title.setText("title");
            community_feeds_descpfeeds.setText("feed_desc");*/

            timeline_latest_comment_view = itemView.findViewById(R.id.timeline_comment_viewgroup);
            timeline_commented_doc_image = (RoundedImageView) timeline_latest_comment_view.findViewById(R.id.commented_doc_pic);
            timeline_commented_doc_name = (TextView) timeline_latest_comment_view.findViewById(R.id.commented_doc_name);
            timeline_commented_text = (TextView) timeline_latest_comment_view.findViewById(R.id.latest_comment);
            bookmark_timeline_content = (LinearLayout) itemView.findViewById(R.id.bookmark_timeline_content);
            bookmark_timeline_content_ImageView = (ImageView) itemView.findViewById(R.id.bookmark_timeline_content_ImageView);
            bookmark_timeline_community_ImageView = (ImageView) itemView.findViewById(R.id.bookmark_timeline_community_ImageView);
            bookmark_timeline_community = (LinearLayout) itemView.findViewById(R.id.bookmark_timeline_community);
            time_line_report_to_spam = (LinearLayout) itemView.findViewById(R.id.time_line_report_to_spam);
            bookmark_timeline_content_report_to_spam = (LinearLayout) itemView.findViewById(R.id.bookmark_timeline_content_report_to_spam);
            surveyRootLayout = (LinearLayout) mParentView.findViewById(R.id.survey_root_layout);

            webinar_lay_timeLine = (RelativeLayout) itemView.findViewById(R.id.webinar_lay_timeLine);
            webinar_time_date_lay_fullview = (RelativeLayout) itemView.findViewById(R.id.webinar_time_date_lay_fullview);


            date_webinar = (TextView) mParentView.findViewById(R.id.date_webinar);
            webinar_time = (TextView) mParentView.findViewById(R.id.webinar_time);
            register_now = (Button) mParentView.findViewById(R.id.register_now);
            join_now = (Button) mParentView.findViewById(R.id.join_now);
            already_registered_icon = (ImageView) mParentView.findViewById(R.id.already_registered_icon);
            already_registered_text = (TextView) mParentView.findViewById(R.id.already_registered_text);
            edited_text_view = (TextView) itemView.findViewById(R.id.edited);
            webinar_text = (TextView) mParentView.findViewById(R.id.webinar_text);
            webinar_status = (TextView) mParentView.findViewById(R.id.webinar_status);
            progressBar = (ProgressBar) mParentView.findViewById(R.id.progressBar);
            simplePB = (ProgressBar) mParentView.findViewById(R.id.simplePB);
            ended_text = (TextView) mParentView.findViewById(R.id.ended_text);
            webinar_time_date_inner_lay = (LinearLayout) mParentView.findViewById(R.id.webinar_time_date_inner_lay);
            eoi_lay = (Button) mParentView.findViewById(R.id.if_interested_button);
            eoi_lay_article = (Button) mParentView.findViewById(R.id.if_interested_button_article);
            webinar_icon = (ImageView) mParentView.findViewById(R.id.webinar_icon);
            tv_date_time_label = (TextView) mParentView.findViewById(R.id.tv_webinar_date_time_label);
            tv_organizer_details = (TextView) mParentView.findViewById(R.id.tv_organizer);
            view_recording_btn = mParentView.findViewById(R.id.view_recording_btn);
        }

        @Override
        public void onClick(View view) {
            JSONObject feedInfoObj = feedsList.get(getAdapterPosition());
            if (feedClickListener != null) {
                JSONArray attachmentsList = feedInfoObj.optJSONObject(RestUtils.TAG_FEED_INFO).optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                ImageView contentImage = null;
                if (attachmentsList != null && attachmentsList.length() > 0) {
                    if (attachmentsList.length() == 2) {
                        contentImage = (ImageView) view.findViewById(R.id.secondImageofCommunity);
                    } else if (attachmentsList.length() == 1 || attachmentsList.length() >= 3) {
                        contentImage = (ImageView) view.findViewById(R.id.communityImage);
                    }
                }
                feedClickListener.onItemClickListener(feedInfoObj, false, channel_id, channel_name, contentImage, getAdapterPosition());
            } else {
                if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                    Intent in = new Intent(context, ContentFullView.class);
                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channel_name);
                    in.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedsList.get(getAdapterPosition()).toString());
                    context.startActivity(in);
                } else if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                    Intent in = new Intent(context, JobFeedCompleteView.class);
                    in.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    in.putExtra(RestUtils.CHANNEL_NAME, channel_name);
                    in.putExtra(RestUtils.TAG_POSITION, getAdapterPosition());
                    in.putExtra(RestUtils.TAG_FEED_OBJECT, feedsList.get(getAdapterPosition()).toString());
                    context.startActivity(in);
                } else {
                    Intent in = new Intent(context, FeedsSummary.class);
                    in.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    in.putExtra(RestUtils.CHANNEL_NAME, channel_name);
                    in.putExtra(RestUtils.TAG_POSITION, getAdapterPosition());
                    in.putExtra(RestUtils.TAG_FEED_OBJECT, feedsList.get(getAdapterPosition()).toString());
                    context.startActivity(in);
                }
            }
        }

        public void clearAnimation() {
            mParentView.clearAnimation();
        }
    }

    private void setFeedsItemData(final RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                final DataObjectHolder viewHolder = (DataObjectHolder) holder;
                final JSONObject feedInfoJsonObj = feedsList.get(position).optJSONObject(RestUtils.TAG_FEED_INFO);
                final boolean isBookmarked = feedInfoJsonObj.optBoolean(RestUtils.TAG_IS_BOOKMARKED, false);
                Log.e("AllFeedsAdapter", "Feed Title : " + feedInfoJsonObj.optString(RestUtils.TAG_TITLE) + " --IS_BOOKMARKED-" + isBookmarked);
                JSONObject postCreatorJsonObj = feedsList.get(position).optJSONObject(RestUtils.TAG_POST_CREATOR);
                viewHolder.attachment_icon.setVisibility(View.GONE);
                viewHolder.attachment_icon1.setVisibility(View.GONE);
                viewHolder.attachment_icon2.setVisibility(View.GONE);
                viewHolder.attachment_video_type1.setVisibility(View.GONE);
                viewHolder.attachment_video_type.setVisibility(View.GONE);
                viewHolder.attachment_video_type2.setVisibility(View.GONE);
                viewHolder.postType_date_lay_content.setVisibility(View.GONE);
                viewHolder.postType_date_lay_community.setVisibility(View.GONE);
                viewHolder.croppedImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                viewHolder.croppedImageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                viewHolder.croppedImageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                viewHolder.croppedImageView.setVisibility(View.GONE);
                viewHolder.croppedImageView1.setVisibility(View.GONE);
                viewHolder.croppedImageView2.setVisibility(View.GONE);
                viewHolder.surveyRootLayout.setVisibility(View.GONE);
                viewHolder.webinar_lay_timeLine.setVisibility(View.GONE);
                viewHolder.eoi_lay.setVisibility(View.GONE);
                viewHolder.webinar_time_date_lay_fullview.setVisibility(View.GONE);

                String feedDesc = "";
                String displayTag = "";

                if (feedInfoJsonObj != null && feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                    displayArticleUI(feedInfoJsonObj, viewHolder, isBookmarked);
                } else {
                    if (feedInfoJsonObj != null && feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
                        viewHolder.surveyRootLayout.setVisibility(View.VISIBLE);
                        viewHolder.surveyRootLayout.removeAllViews();
                        LinearLayout surveyOptionViewGroup = new LinearLayout(context);
                        surveyOptionViewGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        surveyOptionViewGroup.setOrientation(LinearLayout.VERTICAL);
                        displayTag = (!feedInfoJsonObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) ? feedInfoJsonObj.optString(RestUtils.TAG_DISPLAY).toUpperCase() : "";
                        feedDesc = (!feedInfoJsonObj.optString(RestUtils.FEED_DESC).isEmpty()) ? AppUtil.linkifyHtml(feedInfoJsonObj.optString(RestUtils.FEED_DESC), Linkify.WEB_URLS).toString() : "";
                        try {
                            FeedSurvey surveyData = AppUtil.getSurveyData(feedInfoJsonObj);
                            for (int i = 0; i < 2; i++) {
                                SurveyOption surveyOption = surveyData.getQuestions().get(0).getOptions().get(i);
                                View optionRowLayout = LayoutInflater.from(context).inflate(R.layout.survey_option_item, null);
                                CheckBox checkBox = optionRowLayout.findViewById(R.id.option_chk_box);
                                RadioButton radioButton = optionRowLayout.findViewById(R.id.option_radio_btn);
                                TextView optionTxt = optionRowLayout.findViewById(R.id.option_txt_vw);
                                TextView optionPercentTxt = optionRowLayout.findViewById(R.id.option_percent_txt);

                                // Survey option percentage condition
                                // <b> 1. </b> If user participated and survey is immediate (or) if survey is closed,then display results
                                if ((surveyData.isParticipated() && surveyData.isImmediate()) || !surveyData.isOpen()) {
                                    optionPercentTxt.setVisibility(View.VISIBLE);
                                    optionPercentTxt.setText(surveyOption.getParticipatedPercent() + "%");
                                    if (surveyOption.getParticipatedPercent() == surveyData.getQuestions().get(0).getHighPercentage()) {
                                        optionPercentTxt.setBackground(context.getResources().getDrawable(R.drawable.rounded_dark_blue));
                                    } else {
                                        optionPercentTxt.setBackground(context.getResources().getDrawable(R.drawable.rounded_light_blue));
                                    }
                                } else {
                                    optionPercentTxt.setVisibility(View.GONE);
                                }
                                // Select option condition
                                //<b> 2. </b> If survey is closed,then don't display select options
                                if (!surveyData.isOpen() || surveyData.isParticipated()) {
                                    radioButton.setVisibility(View.GONE);
                                    checkBox.setVisibility(View.GONE);
                                    if (surveyOption.isSelected()) {
                                        optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.border_text));
                                    } else {
                                        optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.option_shape_grey));
                                    }
                                } else {
                                    if (surveyData.getQuestions().get(0).isMultiSelect()) {
                                        checkBox.setVisibility(View.VISIBLE);
                                        radioButton.setVisibility(View.GONE);
                                        checkBox.setClickable(false);
                                        if (surveyOption.isSelected()) {
                                            optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.border_text));
                                            checkBox.setChecked(true);
                                        } else {
                                            optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.option_shape_grey));
                                            checkBox.setChecked(false);
                                        }
                                    } else {
                                        checkBox.setVisibility(View.GONE);
                                        radioButton.setVisibility(View.VISIBLE);
                                        radioButton.setClickable(false);
                                        if (surveyOption.isSelected()) {
                                            optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.border_text));
                                            radioButton.setChecked(true);
                                        } else {
                                            optionRowLayout.setBackground(context.getResources().getDrawable(R.drawable.option_shape_grey));
                                            radioButton.setChecked(false);
                                        }
                                    }
                                }


                                optionTxt.setText(surveyOption.getOption());
                                optionTxt.setMaxLines(1);
                                optionTxt.setEllipsize(TextUtils.TruncateAt.END);

                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                params.setMargins(0, 0, 0, 16);
                                optionRowLayout.setLayoutParams(params);
                                optionRowLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (viewHolder.mParentView != null) {
                                            viewHolder.mParentView.performClick();
                                        }
                                    }
                                });
                                surveyOptionViewGroup.addView(optionRowLayout);
                                //viewHolder.surveyOptionsLayout.addView(optionRowLayout);
                            }
                            LinearLayout surveyOtherInfoLayout = new LinearLayout(context);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.weight = 1.0f;
                            layoutParams.setMargins(0, 16, 0, 8);
                            surveyOtherInfoLayout.setLayoutParams(layoutParams);
                            surveyOtherInfoLayout.setOrientation(LinearLayout.HORIZONTAL);
                            //surveyOtherInfoLayout.set


                            //<b> 3. </b> If survey is open, then display close time otherwise display "Final Results".
                            TextView surveyCloseView = new TextView(context);
                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params1.weight = .4f;
                            params1.gravity = Gravity.START;
                            surveyCloseView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            surveyCloseView.setLayoutParams(params1);

                            TextView surveyMoreOptionsView = new TextView(context);
                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params2.weight = .6f;
                            //params2.gravity = Gravity.END;
                            surveyMoreOptionsView.setGravity(Gravity.RIGHT);
                            surveyMoreOptionsView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            surveyMoreOptionsView.setTextColor(ContextCompat.getColor(context, R.color.app_green));
                            surveyMoreOptionsView.setLayoutParams(params2);
                            surveyOtherInfoLayout.addView(surveyCloseView);
                            surveyOtherInfoLayout.addView(surveyMoreOptionsView);
                            viewHolder.surveyRootLayout.addView(surveyOptionViewGroup);
                            viewHolder.surveyRootLayout.addView(surveyOtherInfoLayout);

                            //<b> 3. </b> If survey is open, then display close time otherwise display "Final Results".
                            if (surveyData.isOpen()) {
                                String time = AppUtil.getSurveyClosingTime(surveyData.getCloseTime(), surveyData.getTimeStamp());
                                if (!time.isEmpty())
                                    surveyCloseView.setText("Survey closes in " + time);
                                else
                                    surveyCloseView.setText(context.getResources().getString(R.string.final_result_short_msg));
                            } else {
                                surveyCloseView.setText(context.getResources().getString(R.string.final_result_short_msg));
                            }
                            int totalOptions = surveyData.getQuestions().get(0).getOptions().size();
                            if (totalOptions > 2) {
                                surveyMoreOptionsView.setVisibility(View.VISIBLE);
                                surveyMoreOptionsView.setText("+" + (totalOptions - 2) + " more options");
                            } else {
                                surveyMoreOptionsView.setVisibility(View.GONE);
                            }
                            AppUtil.ifInterestedButtonVisibility(this.context, feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS), viewHolder.eoi_lay, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), mDoctorID);
                            // For loading attachments
                            loadFeedAttachment(viewHolder, surveyData.getQuestions().get(0).getQuestionJsonObj());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (feedInfoJsonObj.optString(RestUtils.TAG_FEED_SUB_TYPE).equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                        viewHolder.webinar_lay_timeLine.setVisibility(View.VISIBLE);
                        viewHolder.webinar_lay_timeLine.setBackgroundColor(context.getResources().getColor(R.color.app_green));
                        viewHolder.eoi_lay.setVisibility(View.GONE);
                        viewHolder.webinar_time_date_lay_fullview.setVisibility(View.VISIBLE);
                        if (feed_provider_type.equalsIgnoreCase(RestUtils.CONTENT)) {
                            displayArticleUI(feedInfoJsonObj, viewHolder, isBookmarked);
                        } else {
                            displayCommunityUI(feedInfoJsonObj, viewHolder, isBookmarked, holder, postCreatorJsonObj, position);
                        }
                        if (feedInfoJsonObj.has(RestUtils.TAG_EVENT_DETAILS)) {
                            JSONObject eventDetails = feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS);
                            if (eventDetails.optBoolean("is_registration_event")) {
                                //viewHolder.webinar_lay_timeLine.setBackgroundColor(Color.parseColor("#00A89D"));
                                //viewHolder.tv_date_time_label.setText("Organizer Details:");
                                //viewHolder.tv_date_time_label.append();
                                viewHolder.webinar_text.setText("EVENT");
                                //viewHolder.webinar_icon.s(R.drawable.notification_article_unread_icon);
                                AppUtil.processRegisterEventData(this.context, mDoctorID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), feedInfoJsonObj.optString(RestUtils.TITLE), eventDetails, viewHolder.date_webinar, viewHolder.webinar_time,
                                        viewHolder.register_now, viewHolder.join_now, viewHolder.already_registered_icon, viewHolder.already_registered_text, viewHolder.progressBar, viewHolder.webinar_status, viewHolder.webinar_time_date_inner_lay, viewHolder.ended_text, viewHolder.tv_date_time_label, viewHolder.tv_organizer_details);
                            } else {
                                viewHolder.tv_date_time_label.setVisibility(View.VISIBLE);
                                viewHolder.webinar_text.setText("WEBINAR");
                                //viewHolder.webinar_icon.setImageResource(R.drawable.webinar_video_icon);
                                AppUtil.processWebinarData(this.context, mDoctorID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), feedInfoJsonObj.optString(RestUtils.TITLE), eventDetails, viewHolder.date_webinar, viewHolder.webinar_time,
                                        viewHolder.register_now, viewHolder.join_now, viewHolder.already_registered_icon, viewHolder.already_registered_text, viewHolder.progressBar, viewHolder.webinar_status, viewHolder.webinar_time_date_inner_lay, viewHolder.ended_text, viewHolder.view_recording_btn, viewHolder.simplePB);
                            }
                        }
                        // socialInteractionUI(viewHolder,feedInfoJsonObj,holder,position);
                    } else {
                        viewHolder.webinar_lay_timeLine.setVisibility(View.GONE);
                        viewHolder.eoi_lay.setVisibility(View.GONE);
                        viewHolder.webinar_time_date_lay_fullview.setVisibility(View.GONE);
                        if (feed_provider_type.equalsIgnoreCase(RestUtils.CONTENT)) {
                            displayArticleUI(feedInfoJsonObj, viewHolder, isBookmarked);
                        } else {
                            displayCommunityUI(feedInfoJsonObj, viewHolder, isBookmarked, holder, postCreatorJsonObj, position);
                        }
                        //displayCommunityUI(feedInfoJsonObj, viewHolder, isBookmarked, holder, postCreatorJsonObj, position);
                    }
                }
                socialInteractionUI(viewHolder, feedInfoJsonObj, holder, position);
            } else {
                //((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }

    }

    private void socialInteractionUI(DataObjectHolder viewHolder, JSONObject feedInfoJsonObj, RecyclerView.ViewHolder holder, int position) {
        viewHolder.community_feeds_descpfeeds.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            /*if(viewHolder.feedsFullView!=null) {
                                viewHolder.feedsFullView.performClick();
                            }*/
                    if (viewHolder.mParentView != null) {
                        viewHolder.mParentView.performClick();
                    }
                }
                return true;
            }
        });
        String imageString = feedInfoJsonObj.optString(RestUtils.ATTACHMENT_NAME);

        final JSONObject socialInteractionObj = feedInfoJsonObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
        if (socialInteractionObj != null) {
            AppUtil.toggleSocialBarViewCount(socialInteractionObj, viewHolder.like_count_timeline, viewHolder.comment_count_timeline, viewHolder.view_count_timeline, viewHolder.socialBar_count_timeline, viewHolder.socialBar_action_timeline, viewHolder.like_layout_timeline, viewHolder.comment_layout_timeline, viewHolder.share_layout_timeline, viewHolder.bookmark_timeline_content, viewHolder.tv_share_count);
            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_BOOKMARK_ENABLED)) {
                viewHolder.bookmark_timeline_community.setVisibility(View.VISIBLE);
            } else {
                viewHolder.bookmark_timeline_community.setVisibility(View.GONE);
            }
            if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                viewHolder.like_icon_timeline.setImageResource(R.drawable.ic_social_liked);
                viewHolder.like_label_timeline.setTextColor(Color.parseColor("#00A76D"));
                viewHolder.like_layout_timeline.setTag(true);
            } else {
                viewHolder.like_icon_timeline.setImageResource(R.drawable.ic_social_like);
                viewHolder.like_label_timeline.setTextColor(Color.parseColor("#DE231f20"));
                viewHolder.like_layout_timeline.setTag(false);
            }
            //Handle last comment section ui
            if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                viewHolder.timeline_latest_comment_view.setVisibility(View.VISIBLE);
                AppUtil.displayLatestCommentUI(context, mDoctorID, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), viewHolder.timeline_commented_doc_image, viewHolder.timeline_commented_doc_name, viewHolder.timeline_commented_text);
            } else {
                viewHolder.timeline_latest_comment_view.setVisibility(View.GONE);
            }
        } else {
            viewHolder.like_icon_timeline.setImageResource(R.drawable.ic_social_like);
            viewHolder.like_label_timeline.setTextColor(Color.parseColor("#DE231f20"));
            viewHolder.like_layout_timeline.setTag(false);
            viewHolder.timeline_latest_comment_view.setVisibility(View.GONE);
        }
        viewHolder.like_layout_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtil.isConnectingToInternet(context)) {
                    return;
                }
                if (likeAPICall != null && likeAPICall.getStatus() == AsyncTask.Status.RUNNING) {
                    return;
                }
                Boolean isLiked = true;
                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {

                    if ((Boolean) v.getTag()) {
                        isLiked = false;
                        viewHolder.like_icon_timeline.setImageResource(R.drawable.ic_social_like);
                        viewHolder.like_label_timeline.setTextColor(Color.parseColor("#DE231f20"));
                        viewHolder.like_layout_timeline.setTag(false);
                    } else {
                        isLiked = true;
                        viewHolder.like_icon_timeline.setImageResource(R.drawable.ic_social_liked);
                        viewHolder.like_label_timeline.setTextColor(Color.parseColor("#00A76D"));
                        viewHolder.like_layout_timeline.setTag(true);
                        AppUtil.loadBounceAnimation(context, viewHolder.like_icon_timeline);
                    }
                    //notifyDataSetChanged();
                                /*if (AppConstants.likeActionList.contains(channel_id + "_" + feedsList.get(position).optInt("feed_id"))) {
                                    return;
                                }*/
                    JSONObject socialObj = socialInteractionObj;
                    try {
                        if (isLiked) {
                            socialObj.put(RestUtils.TAG_LIKES_COUNT, socialObj.optInt(RestUtils.TAG_LIKES_COUNT) + 1);
                            socialObj.put(RestUtils.TAG_IS_LIKE, isLiked);
                        } else {
                            if (socialObj.optInt(RestUtils.TAG_LIKES_COUNT) > 0) {
                                socialObj.put(RestUtils.TAG_LIKES_COUNT, socialObj.optInt(RestUtils.TAG_LIKES_COUNT) - 1);
                            }
                            socialObj.put(RestUtils.TAG_IS_LIKE, isLiked);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        listener.updateUI(feedsList.get(position).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID), socialObj);
                    }

                    //socialInteractionListener.onUIupdateForLike(feedsList.get(position), channel_id, isLiked, feedsList.get(position).optInt("article_id"));
                    socialInteractionListener.onSocialInteraction(feedsList.get(position), channel_id, isLiked, feedsList.get(position).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID));

                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });
        viewHolder.comment_layout_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoJsonObj.optString(RestUtils.TAG_TITLE));
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, true);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                context.startActivity(commentIntent);
            }
        });
        viewHolder.like_count_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent likeIntent = new Intent(context, CommentsActivity.class);
                likeIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoJsonObj.optString(RestUtils.TAG_TITLE));
                likeIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                likeIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                likeIntent.putExtra(RestUtils.NAVIGATATION, RestUtils.TAG_FROM_LIKES_COUNT);
                likeIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, (socialInteractionObj == null) ? 0 : socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT));
                likeIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                context.startActivity(likeIntent);
            }
        });
        viewHolder.comment_count_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoJsonObj.optString(RestUtils.TAG_TITLE));
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                context.startActivity(commentIntent);
            }
        });
        viewHolder.timeline_latest_comment_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(context, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoJsonObj.optString(RestUtils.TAG_TITLE));
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                context.startActivity(commentIntent);
            }
        });

        viewHolder.share_layout_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isConnectingToInternet(context)) {
                    return;
                }

                JSONObject socialObj = socialInteractionObj;
                try {
                    socialObj.put(RestUtils.TAG_SHARE_COUNT, socialObj.optInt(RestUtils.TAG_SHARE_COUNT) + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                    listener.updateUI(feedsList.get(position).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID), socialObj);
                }
                //boolean isFeedEligibleToShare = false;
                Calendar calendar = Calendar.getInstance();
                long selectedTime = calendar.getTimeInMillis();
                doctorName = realmManager.getDocSalutation(realm) + " " + realmManager.getDoc_name(realm);
                if (feedInfoJsonObj.has(RestUtils.TAG_SHARE_INFO)) {
                    JSONObject shareInfoObj = feedInfoJsonObj.optJSONObject(RestUtils.TAG_SHARE_INFO);
                    text_From_Server = shareInfoObj.optString(RestUtils.TAG_SERVER_TEXT);
                    url_From_server = shareInfoObj.optString(RestUtils.TAG_SERVER_URL);
                }

                if (feed_provider_type.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
                    String share_des = context.getString(R.string.share_des_text, doctorName, "post", "\"" + viewHolder.community_feeds_Title.getText().toString() + "\" ");
                    if (text_From_Server != null && !text_From_Server.isEmpty()) {
                        share_des = doctorName + " " + text_From_Server + " \"" + viewHolder.community_feeds_Title.getText().toString() + "\" ";
                    }
                    AppUtil.inviteToWhiteCoatsIntent(context, share_des, "Share via", "");
                    //isEncryptionSucess=true;
                    final JSONObject shareFeedRequestObj = new JSONObject();
                    JSONArray feedDataArray = new JSONArray();
                    JSONObject innerObj = new JSONObject();
                    try {
                        shareFeedRequestObj.put(RestUtils.TAG_USER_ID, mDoctorID);
                        innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                        innerObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                        innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                        feedDataArray.put(innerObj);
                        shareFeedRequestObj.put(RestUtils.TAG_SHARED_FEEDS, feedDataArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RealmManager realmManager = new RealmManager(context);
                    AppUtil.requestForShareAFeed(context, realmManager, shareFeedRequestObj.toString(), innerObj.toString());
                } else {
                    String feedTitle = viewHolder.content_feeds_Title.getText().toString();
                    if (feedInfoJsonObj != null && feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
                        feedTitle = viewHolder.community_feeds_Title.getText().toString();
                    }
                    JSONObject encryptedStringData = AppUtil.encryptFeedData(context, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), mDoctorID, channel_id, selectedTime, doctorName, "\"" + feedTitle + "\" ", text_From_Server, url_From_server);
                    if (encryptedStringData != null) {
                        final JSONObject shareFeedRequestObj = new JSONObject();
                        //JSONArray feedDataArray = new JSONArray();
                        JSONObject innerObj = new JSONObject();
                        try {
                            shareFeedRequestObj.put(RestUtils.TAG_USER_ID, mDoctorID);
                            innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                            innerObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                            //feedDataArray.put(innerObj);
                            shareFeedRequestObj.put(RestUtils.TAG_SHARE_URL, encryptedStringData.optString(RestUtils.TAG_ORIGINAL_URL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RealmManager realmManager = new RealmManager(context);
                        AppUtil.requestForShortURL(context, realmManager, shareFeedRequestObj.toString(), innerObj.toString(), encryptedStringData);
                    }
                }
                /*if (feedInfoJsonObj != null && (feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_POST)|| feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_CASE) || feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_EVENT_FEED_TYPE))) {
                    if(feed_provider_type.equalsIgnoreCase(RestUtils.CONTENT)){
                        isFeedEligibleToShare=true;
                    }

                }
                else {
                    isFeedEligibleToShare = true;
                }
                if(isFeedEligibleToShare){

                }*/

            }
        });
        setAnimation(holder.itemView, position);
    }

    private void displayCommunityUI(JSONObject feedInfoJsonObj, DataObjectHolder viewHolder, boolean isBookmarked, RecyclerView.ViewHolder holder, JSONObject postCreatorJsonObj, int position) {
        String feedTitle;
        viewHolder.communityPostCreatedBy.setVisibility(View.VISIBLE);
        viewHolder.postType_date_lay_community.setVisibility(View.VISIBLE);
        viewHolder.createdby_layout_timeline.setVisibility(View.VISIBLE);
        ((DataObjectHolder) holder).postType_date_lay_community.setVisibility(View.VISIBLE);
        viewHolder.bookmark_timeline_community.setVisibility(View.VISIBLE);
        viewHolder.bookmark_timeline_content.setVisibility(View.GONE);
        viewHolder.eoi_lay_article.setVisibility(View.GONE);

        if (mDoctorID == postCreatorJsonObj.optInt(RestUtils.TAG_DOC_ID)) {
            viewHolder.communityPostCreatedBy.setText(this.context.getString(R.string.label_you));
        } else {
            viewHolder.communityPostCreatedBy.setText(postCreatorJsonObj.optString(RestUtils.TAG_USER_SALUTAION) + " " + postCreatorJsonObj.optString(RestUtils.TAG_USER_FULL_NAME));
        }


        if (postCreatorJsonObj.has(RestUtils.TAG_PROFILE_URL) && !postCreatorJsonObj.optString(RestUtils.TAG_PROFILE_URL).isEmpty()) {
            /*Picasso.with(this.context)
                    .load(postCreatorJsonObj.optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_profilepic)
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(viewHolder.created_doc_picture);*/
            AppUtil.loadCircularImageUsingLib(context, postCreatorJsonObj.optString(RestUtils.TAG_PROFILE_URL).trim(), viewHolder.created_doc_picture, R.drawable.default_profilepic);
            viewHolder.created_doc_picture.setContentDescription(postCreatorJsonObj.optString(RestUtils.TAG_PROFILE_URL));
        } else {
            viewHolder.created_doc_picture.setImageResource(R.drawable.default_profilepic);
            viewHolder.created_doc_picture.setContentDescription("");
        }
        viewHolder.createdby_layout_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDoctorID == feedsList.get(position).optJSONObject(RestUtils.TAG_POST_CREATOR).optInt(RestUtils.TAG_DOC_ID)) {
                    return;
                }
                Intent intent = new Intent(AllFeedsAdapter.this.context, VisitOtherProfile.class);
                intent.putExtra(RestUtils.TAG_DOC_ID, feedsList.get(position).optJSONObject(RestUtils.TAG_POST_CREATOR).optInt(RestUtils.TAG_DOC_ID));
                AllFeedsAdapter.this.context.startActivity(intent);
            }
        });
        if (feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            feedTitle = "JOB OPENING: " + feedInfoJsonObj.optString(RestUtils.TAG_TITLE) + " with " + feedInfoJsonObj.optString("organization");
        } else {
            feedTitle = feedInfoJsonObj.optString(RestUtils.TAG_TITLE);
        }
        viewHolder.community_feeds_Title.setText(feedTitle);
        if (feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            viewHolder.community_feeds_descpfeeds.setVisibility(View.VISIBLE);
            viewHolder.community_feeds_descpfeeds.setText(AppUtil.linkifyHtml(feedInfoJsonObj.optString("job_description").replaceAll("<img .*?/>", ""), Linkify.WEB_URLS));
        } else if (!feedInfoJsonObj.optString(RestUtils.FEED_DESC).isEmpty()) {
            viewHolder.community_feeds_descpfeeds.setVisibility(View.VISIBLE);
            viewHolder.community_feeds_descpfeeds.setText(AppUtil.linkifyHtml(feedInfoJsonObj.optString(RestUtils.FEED_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS));
            viewHolder.community_feeds_descpfeeds.setMaxLines(2);
        } else {
            viewHolder.community_feeds_descpfeeds.setVisibility(View.GONE);
        }
        /**
         * For Bookmarks
         */

        if (isBookmarked) {
            viewHolder.bookmark_timeline_community_ImageView.setImageResource(R.drawable.ic_bookmarked_feed);
        } else {
            viewHolder.bookmark_timeline_community_ImageView.setImageResource(R.drawable.ic_bookmark_feed);
        }
        viewHolder.bookmark_timeline_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.getUserVerifiedStatus() == 3) {
                    if (AppUtil.isConnectingToInternet(AllFeedsAdapter.this.context)) {
                        if (isBookmarked) {
                            viewHolder.bookmark_timeline_community_ImageView.setImageResource(R.drawable.ic_bookmark_feed);
                        } else {
                            viewHolder.bookmark_timeline_community_ImageView.setImageResource(R.drawable.ic_bookmarked_feed);
                            AppUtil.loadBounceAnimation(AllFeedsAdapter.this.context, viewHolder.bookmark_timeline_community_ImageView);
                        }
                        try {
                            JSONObject requestObj = new JSONObject();
                            requestObj.put(RestUtils.TAG_DOC_ID, mDoctorID);
                            requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            requestObj.put(RestUtils.CHANNEL_ID, channel_id);
                            requestObj.put(RestUtils.TAG_IS_BOOKMARK, !isBookmarked); // on click set the opposite one.
                            requestBookmarkService(requestObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(AllFeedsAdapter.this.context, AllFeedsAdapter.this.context.getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(AllFeedsAdapter.this.context, AllFeedsAdapter.this.context.getString(R.string.mca_uploaded_but_not_verified));
                }

            }
        });

        viewHolder.time_line_report_to_spam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialInteractionListener.onReportSpam("SPAM_CLICK",feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID),mDoctorID);
            }
        });

        viewHolder.communityPublishedDate.setText(DateUtils.longToMessageListHeaderDate(feedInfoJsonObj.optLong(RestUtils.TAG_POSTING_TIME)));
        if (feedInfoJsonObj.optBoolean(RestUtils.TAG_IS_EDITED)) {
            if (feedInfoJsonObj.optString(RestUtils.TAG_ARTICLE_TYPE).length() < 11) {
                viewHolder.tv_edited.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_edited.setVisibility(View.GONE);
            }
        } else {
            viewHolder.tv_edited.setVisibility(View.GONE);
        }
        String displayTag = feedInfoJsonObj.optString(RestUtils.TAG_DISPLAY).toUpperCase();
        if (!displayTag.isEmpty()) {
            viewHolder.post_type_community.setText(displayTag);
        } else {
            ((DataObjectHolder) holder).post_type_community.setVisibility(View.GONE);
        }

        //AppUtil.ifInterestedButtonVisibility(this.context, feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS),viewHolder.eoi_lay, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), mDoctorID);

        JSONObject eventDetails = feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS);
        if (eventDetails != null) {
            if (eventDetails.optString("type").equalsIgnoreCase(RestUtils.TAG_EOI)) {
                viewHolder.eoi_lay.setVisibility(View.VISIBLE);
            } else {
                viewHolder.eoi_lay.setVisibility(View.GONE);
            }
        } else {
            viewHolder.eoi_lay.setVisibility(View.GONE);
        }

        viewHolder.eoi_lay.setOnClickListener(view -> {

            if (AppUtil.isConnectingToInternet(context)) {
                boolean userVerify = eventDetails.optBoolean("verify_user");
                if (userVerify) {
                    if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                        String ifInterestedLink = eventDetails.optString("link");
                        if (ifInterestedLink.isEmpty()) {
                            Toast.makeText(context, "Invalid link to join.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        JSONObject requestObj = new JSONObject();
                        try {
                            requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            AppUtil.sendUserActionEventAPICall(mDoctorID, "ifInterestedEvent", requestObj, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.openLinkInBrowser(ifInterestedLink, context);
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
                    }

                } else {
                    String ifInterestedLink = eventDetails.optString("link");
                    if (ifInterestedLink.isEmpty()) {
                        Toast.makeText(context, "Invalid link to join.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    JSONObject requestObj = new JSONObject();
                    try {
                        requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                        AppUtil.sendUserActionEventAPICall(mDoctorID, "ifInterestedEvent", requestObj, context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.openLinkInBrowser(ifInterestedLink, context);
                }


            }

        });


        // For loading attachments
        loadFeedAttachment(viewHolder, feedInfoJsonObj);

    }

    private void displayArticleUI(JSONObject feedInfoJsonObj, DataObjectHolder viewHolder, boolean isBookmarked) {
        String feedTitle;
        viewHolder.community_feeds_Title.setVisibility(View.GONE);
        viewHolder.bookmark_timeline_community.setVisibility(View.GONE);
        viewHolder.bookmark_timeline_content.setVisibility(View.VISIBLE);
        viewHolder.community_feeds_descpfeeds.setVisibility(View.GONE);
        viewHolder.content_feeds_Title.setVisibility(View.VISIBLE);
        viewHolder.content_feeds_desc.setVisibility(View.VISIBLE);
        viewHolder.postType_date_lay_content.setVisibility(View.VISIBLE);
        viewHolder.createdby_layout_timeline.setVisibility(View.GONE);
        viewHolder.eoi_lay.setVisibility(View.GONE);

        StringBuffer stBuff = new StringBuffer();
        if (feedInfoJsonObj.has(RestUtils.TAG_SPLTY)) {
            JSONArray specialtyArr = feedInfoJsonObj.optJSONArray(RestUtils.TAG_SPLTY);
            int specialtyLen = specialtyArr.length();
            for (int i = 0; i < specialtyLen; i++) {
                stBuff.append(specialtyArr.optString(i));
                if (i != specialtyLen - 1) {
                    stBuff.append(", ");
                }
            }
        }
        viewHolder.content_specialities.setVisibility(View.GONE);
        viewHolder.content_specialities.setText(stBuff.toString());

        if (feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            feedTitle = "JOB OPENING: " + feedInfoJsonObj.optString(RestUtils.TITLE) + " with " + feedInfoJsonObj.optString("organization");
        } else {
            feedTitle = feedInfoJsonObj.optString(RestUtils.TITLE);
        }

        viewHolder.content_feeds_Title.setText(feedTitle);
        if (feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
            viewHolder.content_feeds_desc.setText(AppUtil.linkifyHtml(feedInfoJsonObj.optString("job_description").replaceAll("<img .*?/>", ""), Linkify.WEB_URLS));
        } else {
            if (feedInfoJsonObj.has(RestUtils.TAG_SHORT_DESC)) {
                viewHolder.content_feeds_desc.setText(AppUtil.linkifyHtml(feedInfoJsonObj.optString(RestUtils.TAG_SHORT_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS));
            } else {
                viewHolder.content_feeds_desc.setText(AppUtil.linkifyHtml(feedInfoJsonObj.optString(RestUtils.FEED_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS));
            }
        }
        viewHolder.content_feeds_desc.setMaxLines(2);
        viewHolder.contentPublishedDate.setText(DateUtils.longToMessageListHeaderDate(feedInfoJsonObj.optLong(RestUtils.TAG_POSTING_TIME)));
        String displayTag = "";
        if (feedInfoJsonObj != null && feedInfoJsonObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
            displayTag = feedInfoJsonObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase();
        } else {
            displayTag = feedInfoJsonObj.optString(RestUtils.TAG_DISPLAY).toUpperCase();
        }
        if (!displayTag.isEmpty()) {
            viewHolder.post_type_article.setText(displayTag);
        } else {
            viewHolder.post_type_article.setVisibility(View.GONE);
        }
        if (feedInfoJsonObj.optBoolean(RestUtils.TAG_IS_EDITED)) {
            if (feedInfoJsonObj.optString(RestUtils.TAG_ARTICLE_TYPE).length() < 11) {
                viewHolder.tv_edited_content.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_edited_content.setVisibility(View.GONE);
            }
        } else {
            viewHolder.tv_edited_content.setVisibility(View.GONE);
        }
        /**
         * For Bookmarks
         */

        if (isBookmarked) {
            viewHolder.bookmark_timeline_content_ImageView.setImageResource(R.drawable.ic_bookmarked_feed);
        } else {
            viewHolder.bookmark_timeline_content_ImageView.setImageResource(R.drawable.ic_bookmark_feed);
        }
        viewHolder.bookmark_timeline_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.getUserVerifiedStatus() == 3) {
                    if (AppUtil.isConnectingToInternet(AllFeedsAdapter.this.context)) {
                        if (isBookmarked) {
                            viewHolder.bookmark_timeline_content_ImageView.setImageResource(R.drawable.ic_bookmark_feed);
                        } else {
                            viewHolder.bookmark_timeline_content_ImageView.setImageResource(R.drawable.ic_bookmarked_feed);
                            AppUtil.loadBounceAnimation(AllFeedsAdapter.this.context, viewHolder.bookmark_timeline_content_ImageView);
                        }
                        try {
                            JSONObject requestObj = new JSONObject();
                            requestObj.put(RestUtils.TAG_DOC_ID, mDoctorID);
                            requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            requestObj.put(RestUtils.CHANNEL_ID, channel_id);
                            requestObj.put(RestUtils.TAG_IS_BOOKMARK, !isBookmarked); // on click set the opposite one.
                            requestBookmarkService(requestObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(AllFeedsAdapter.this.context, AllFeedsAdapter.this.context.getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(AllFeedsAdapter.this.context, AllFeedsAdapter.this.context.getString(R.string.mca_uploaded_but_not_verified));
                }


            }
        });

        viewHolder.bookmark_timeline_content_report_to_spam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialInteractionListener.onReportSpam("SPAM_CLICK",feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID),mDoctorID);
            }
        });

        //AppUtil.ifInterestedButtonVisibility(this.context, feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS),viewHolder.eoi_lay, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID), mDoctorID);
        JSONObject eventDetails = feedInfoJsonObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS);
        if (eventDetails != null) {
            if (eventDetails.optString("type").equalsIgnoreCase(RestUtils.TAG_EOI)) {
                viewHolder.eoi_lay_article.setVisibility(View.VISIBLE);
            } else {
                viewHolder.eoi_lay_article.setVisibility(View.GONE);
            }
        } else {
            viewHolder.eoi_lay_article.setVisibility(View.GONE);
        }
        viewHolder.eoi_lay_article.setOnClickListener(view -> {

            if (AppUtil.isConnectingToInternet(context)) {
                boolean userVerify = eventDetails.optBoolean("verify_user");
                if (userVerify) {
                    if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                        String ifInterestedLink = eventDetails.optString("link");
                        if (ifInterestedLink.isEmpty()) {
                            Toast.makeText(context, "Invalid link to join.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        JSONObject requestObj = new JSONObject();
                        try {
                            requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                            AppUtil.sendUserActionEventAPICall(mDoctorID, "ifInterestedEvent", requestObj, context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.openLinkInBrowser(ifInterestedLink, context);
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(context, context.getString(R.string.mca_uploaded_but_not_verified));
                    }

                } else {
                    String ifInterestedLink = eventDetails.optString("link");
                    if (ifInterestedLink.isEmpty()) {
                        Toast.makeText(context, "Invalid link to join.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    JSONObject requestObj = new JSONObject();
                    try {
                        requestObj.put(RestUtils.TAG_FEED_ID, feedInfoJsonObj.optInt(RestUtils.TAG_FEED_ID));
                        AppUtil.sendUserActionEventAPICall(mDoctorID, "ifInterestedEvent", requestObj, context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.openLinkInBrowser(ifInterestedLink, context);
                }


            }

        });
        // For loading attachments
        loadFeedAttachment(viewHolder, feedInfoJsonObj);
    }

    private void loadFeedAttachment(DataObjectHolder viewHolder, JSONObject feedInfoJsonObj) {
        if (feedInfoJsonObj.has(RestUtils.ATTACHMENT_DETAILS) && feedInfoJsonObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS) != null && feedInfoJsonObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS).length() > 0) {
            JSONArray attachmentDetailsArr = feedInfoJsonObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
            if (attachmentDetailsArr.length() > 1) {
                AppUtil.loadMultipleAttachments(context, attachmentDetailsArr, viewHolder.croppedImageView, viewHolder.croppedImageView1, viewHolder.croppedImageView2, viewHolder.remainingCountText, viewHolder.attachment_icon, viewHolder.attachment_icon1, viewHolder.attachment_icon2, viewHolder.attachment_video_type, viewHolder.attachment_video_type1, viewHolder.attachment_video_type2);
            } else {
                viewHolder.croppedImageView.setVisibility(View.GONE);
                viewHolder.croppedImageView1.setVisibility(View.GONE);
                viewHolder.croppedImageView2.setVisibility(View.GONE);
                viewHolder.remainingCountText.setVisibility(View.GONE);
                viewHolder.attachment_video_type.setVisibility(View.GONE);
                JSONObject attachObj = attachmentDetailsArr.optJSONObject(0);
                String originalURL = attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                String attachType = attachObj.optString(RestUtils.ATTACHMENT_TYPE);
                String attachName = attachObj.optString(RestUtils.ATTACHMENT_S3_NAME);
                viewHolder.attachment_name.setText(attachName);

                if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                    viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                    viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                    if (attachObj.optString(RestUtils.ATTACH_SMALL_URL) != null && !attachObj.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                        AppUtil.loadImageUsingGlide(context, attachObj.optString(RestUtils.ATTACH_SMALL_URL), viewHolder.croppedImageView, R.drawable.default_image_feed);
                        viewHolder.croppedImageView.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.croppedImageView.setVisibility(View.GONE);
                    }
                } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                    viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                    viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                    viewHolder.attachment_video_type.setVisibility(View.VISIBLE);
                    viewHolder.attachment_video_type.setImageResource(R.drawable.ic_playvideo);
                    if (attachObj.optString(RestUtils.ATTACH_SMALL_URL) != null && !attachObj.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                        AppUtil.loadImageUsingGlide(context, attachObj.optString(RestUtils.ATTACH_SMALL_URL), viewHolder.croppedImageView, R.drawable.default_image_feed);
                        viewHolder.croppedImageView.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.croppedImageView.setVisibility(View.GONE);
                    }
                } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                    viewHolder.attachment_icon.setVisibility(View.GONE);
                    viewHolder.croppedImageView.setVisibility(View.VISIBLE);
                    viewHolder.croppedImageView.setBackgroundColor(Color.parseColor("#E8E8E8"));
                    viewHolder.croppedImageView.setImageResource(R.drawable.ic_attachment_type_audio);
                    viewHolder.croppedImageView.setScaleType(ImageView.ScaleType.CENTER);
                } else if (attachType.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE) && attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                    if (attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                        viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                        viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                    } else {
                        viewHolder.attachment_icon.setVisibility(View.GONE);
                    }
                    AppUtil.loadImageUsingGlide(context, attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL), viewHolder.croppedImageView, R.drawable.default_image_feed);
                    viewHolder.croppedImageView.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.croppedImageView.setVisibility(View.GONE);
                }
            }
        } else {
            viewHolder.croppedImageView1.setVisibility(View.GONE);
            viewHolder.croppedImageView2.setVisibility(View.GONE);
            viewHolder.remainingCountText.setVisibility(View.GONE);
            if (feedInfoJsonObj.has(RestUtils.TAG_LARGE_IMAGE) && !feedInfoJsonObj.optString(RestUtils.TAG_LARGE_IMAGE).isEmpty()) {
                viewHolder.croppedImageView.setVisibility(View.VISIBLE);
                AppUtil.loadImageUsingGlide(context, feedInfoJsonObj.optString(RestUtils.TAG_LARGE_IMAGE), viewHolder.croppedImageView, R.drawable.default_image_feed);
            } else {
                viewHolder.croppedImageView.setVisibility(View.GONE);
            }
        }
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(350);
            anim.setFillAfter(true);
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }

    private void requestBookmarkService(final JSONObject request) {
        Log.i("AllFeedsAdapter", "requestBookmarkService(JSONObject request)");
        try {
            // Service call
            new VolleySinglePartStringRequest(context, Request.Method.POST, RestApiConstants.BOOKMARK, request.toString(), "PERSIST_BOOKMARK", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    Log.i("AllFeedsAdapter", "onSuccessResponse()");
                    try {
                        if (successResponse != null) {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject dataObject = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                boolean isBookMarked = dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
                                if (isBookMarked) {
                                    Toast.makeText(context, context.getString(R.string.label_bookmark_added), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.label_bookmark_removed), Toast.LENGTH_SHORT).show();
                                }
                                for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                    listener.onBookmark(isBookMarked, dataObject.optInt(RestUtils.TAG_FEED_ID), false, dataObject.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = context.getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i("AllFeedsAdapter", "onErrorResponse() - " + errorResponse);
                    if (errorResponse != null) {
                        // revert changes to pre stage
                        notifyDataSetChanged();
                        if (!errorResponse.isEmpty()) {
                            try {
                                JSONObject jsonObject = new JSONObject(errorResponse);
                                String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                if (errorMessage != null && !errorMessage.isEmpty()) {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }).sendSinglePartRequest();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public AVLoadingIndicatorView timelineAvi;
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
            timelineAvi = (AVLoadingIndicatorView) v.findViewById(R.id.avIndicator);
        }
    }

    public AllFeedsAdapter(final Context mContext, OnCustomClickListener mCallback, int doctorID, ArrayList<JSONObject> listOfFeeds, RecyclerView recyclerView, String mFeed_provider_type, String mChannel_name, int mChannel_id, String doc_name, OnSocialInteractionListener onSocialInteractionListener, OnFeedItemClickListener mFeedItemClickListener) {
        context = mContext;
        //jsonDataModelClass = mJsonModelClassArrayList;
        feedsList = listOfFeeds;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(context);
        channel_name = mChannel_name;
        channel_id = mChannel_id;
        mDoctorID = doctorID;
        doctorName = doc_name;
        feed_provider_type = mFeed_provider_type;
        socialInteractionListener = onSocialInteractionListener;
        this.feedClickListener = mFeedItemClickListener;
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
                    .inflate(R.layout.feeds_layout, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }


        //DataObjectHolder dataObjectHolder =
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //holder.getAdapterPosition(position).
        setFeedsItemData(holder, position);
    }

    @Override
    public int getItemCount() {
        return feedsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return feedsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public long getItemId(int position) {
        return feedsList.get(position).hashCode();
    }


    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        //super.onViewDetachedFromWindow(holder);
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                ((DataObjectHolder) holder).clearAnimation();
            }
        }
    }
}