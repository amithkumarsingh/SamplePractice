package com.vam.whitecoats.ui.adapters;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.FeedCreator;
import com.vam.whitecoats.core.models.FeedInfo;
import com.vam.whitecoats.core.models.FeedSurvey;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.AudioController;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CommentsActivity;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.ImageViewer;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.PdfViewerActivity;
import com.vam.whitecoats.ui.activities.VideoStreamActivity;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.activities.WebViewActivity;
import com.vam.whitecoats.ui.activities.YoutubeVideoViewActivity;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AudioUtility;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

public class FeedSummaryAdapter extends PagerAdapter implements FeedsSummary.AudioFunctionListener {
    public static final String TAG = FeedSummaryAdapter.class.getSimpleName();
    //private final String feedSubType;
    private RealmBasicInfo basicInfo;
    private FeedsSummary.ViewPagerNavigationListener viewPagerNavigationListener;
    Activity mContext;
    Context audioLayoutContext;
    private ClipboardManager clipboardManager;
    FeedInfo mFeedInfo;
    FeedCreator mFeedCreator;
    LayoutInflater mLayoutInflater;
    LinearLayout surveyRootLayout, surveyOptionsLayout,webinar_time_date;
    Button surveySubmitBtn;
    RecyclerView thumbnailView, surveyRecyclerView;
    TextView surveyClosingTxtVw, surveyMoreOptionsTxtVw, surveyGreetTxt, surveyLaterMsgTxt;
    TextView tv_title, tv_postedTime, tv_createdBy, tv_article_type,tv_edit;
    TextView attachment_name;
    //FixedTextView tv_DescpView;
    ImageView attachment_icon, profileLogo_fullview, bookmark_community_fullView_imageView, attachment_video_type, context_image;
    LinearLayout bookmark_community_fullView;
    ViewGroup full_content_layout, attachment_layout;
    ViewPager viewPager;
    View feeds_last_comment_layout;
    boolean bHasClipChangedListener = false;
    Realm realm;
    RealmManager realmManager;
    ThumbnailViewAdapter thumbnailImageAdapter;
    ImageViewPagerAdapter imageViewAdapter;
    ArrayList<String> URLList;
    ArrayList<JSONObject> thumbnailsList;
    CustomLinearLayoutManager mLayoutManager;
    ViewPager.OnPageChangeListener pageChangeListener;
    RoundedImageView feeds_commented_doc_image;
    TextView feeds_commented_doc_name;
    AVLoadingIndicatorView recommendationsAvi;
    TextView feeds_commented_text;
    RecyclerView recommendations_list;
    TextView interested_article_textView;



    RecommendationsRecyclerAdapter recommendationsRecyclerAdapter;
    ArrayList<JSONObject> recommendationsList = new ArrayList<>();
    JSONArray recommendationJsonArray;
    File myFile;
    AudioUtility audioUtility;
    String attachment_original_url = "";
    String attachment_type;
    String label_attachment_name = "";
    JSONObject attachment_details_obj = null;
    FeedSurvey surveyData;
    String feed_image;
    int docID;
    String channelName;
    boolean postView;
    SurveyOptionsAdapter optionsAdapter;
    private NestedScrollView contentScrollView;
    private String isUptodatelink="https://whitecoats.com/external/uptodate";
    private LinearLayout type_bookmark_lay;
    private TextView date_webinar,webinar_time,already_registered_text,webinar_status,ended_text,webinar_type,tv_date_time_label,tv_organizer_details;
    private Button register_now,join_now,eoi_lay,view_recording_btn;
    private ImageView already_registered_icon;
    private JSONObject eventDetails;
    private ProgressBar progressBar,simplePB;
    private LinearLayout webinar_time_date_inner_lay;
    private RelativeLayout type_webinar_lay;
    private LinearLayout feed_desc_layout;

    public FeedSummaryAdapter(Activity context, FeedInfo feedInfo, FeedCreator feedCreator, Bundle bundle, FeedsSummary.ViewPagerNavigationListener mViewPagerNavigationListener) {
        this.mContext = context;
        this.mFeedInfo = feedInfo;
        this.mFeedCreator = feedCreator;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        thumbnailsList = new ArrayList<>();
        URLList = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(context);
        docID = realmManager.getDoc_id(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        viewPagerNavigationListener = mViewPagerNavigationListener;
        if (bundle != null) {
            if (bundle.containsKey(RestUtils.CHANNEL_NAME))
                channelName = bundle.getString(RestUtils.CHANNEL_NAME);
            if (bundle.containsKey("fromMedia"))
                postView = bundle.getBoolean("fromMedia", false);
        }

    }


    @Override
    public int getCount() {
        if (mFeedInfo.getFeedType() != null && mFeedInfo.getFeedType().equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
            if (mFeedInfo.getSurveyData() != null)
                return mFeedInfo.getSurveyData().getQuestions().size();
            else
                return 1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fullview_pager_item, container, false);
        surveyRootLayout = itemView.findViewById(R.id.survey_root_full_layout);
        surveyOptionsLayout = itemView.findViewById(R.id.options_container_layout);
        surveySubmitBtn = itemView.findViewById(R.id.survey_submit_btn);
        surveyRecyclerView = itemView.findViewById(R.id.survey_recycler_view);
        thumbnailView = itemView.findViewById(R.id.fullView_thumbnail_view);
        surveyClosingTxtVw = itemView.findViewById(R.id.survey_close_date_txt);
        surveyMoreOptionsTxtVw = itemView.findViewById(R.id.survey_more_options_txt);
        surveyGreetTxt = itemView.findViewById(R.id.survey_greet_text);
        surveyLaterMsgTxt = itemView.findViewById(R.id.survey_later_result_text);
        tv_title = itemView.findViewById(R.id.feed_title);
        tv_edit = itemView.findViewById(R.id.edited);
        tv_postedTime = itemView.findViewById(R.id.feed_posted_date);
        tv_createdBy = itemView.findViewById(R.id.fullPost_createdBy);
        tv_article_type = itemView.findViewById(R.id.article_type_community);
        attachment_name = itemView.findViewById(R.id.label_attachment_name_summary);
        //tv_DescpView = itemView.findViewById(R.id.feed_short_desc);
        context_image = itemView.findViewById(R.id.context_image);
        attachment_icon = itemView.findViewById(R.id.attachment_icon_summary);
        attachment_video_type = itemView.findViewById(R.id.attachment_video_type);
        profileLogo_fullview = itemView.findViewById(R.id.prfLogo_fullview);
        bookmark_community_fullView_imageView = itemView.findViewById(R.id.bookmark_community_fullView_ImageView);
        bookmark_community_fullView = itemView.findViewById(R.id.bookmark_community_fullView);
        attachment_layout = itemView.findViewById(R.id.community_attachment_layout_summary);
        full_content_layout = itemView.findViewById(R.id.full_content_layout);
        viewPager = itemView.findViewById(R.id.view_pager);
        feeds_last_comment_layout = itemView.findViewById(R.id.feed_fullview_comment_layout);
        feeds_commented_doc_image = feeds_last_comment_layout.findViewById(R.id.commented_doc_pic);
        feeds_commented_doc_name = feeds_last_comment_layout.findViewById(R.id.commented_doc_name);
        feeds_commented_text = feeds_last_comment_layout.findViewById(R.id.latest_comment);
        recommendationsAvi = itemView.findViewById(R.id.aviInRecommendationsList);
        recommendations_list = itemView.findViewById(R.id.recommendations_list);
        interested_article_textView = itemView.findViewById(R.id.interested_article_textView);
        webinar_time_date=itemView.findViewById(R.id.webinar_time_date);
        type_bookmark_lay=itemView.findViewById(R.id.type_bookmark_lay);
        date_webinar=itemView.findViewById(R.id.date_webinar);
        webinar_time=itemView.findViewById(R.id.webinar_time);
        register_now=itemView.findViewById(R.id.register_now);
        join_now=itemView.findViewById(R.id.join_now);
        already_registered_icon=itemView.findViewById(R.id.already_registered_icon);
        already_registered_text = itemView.findViewById(R.id.already_registered_text);
        webinar_status=itemView.findViewById(R.id.webinar_status);
        ended_text=(TextView)itemView.findViewById(R.id.ended_text);
        simplePB =(ProgressBar) itemView.findViewById(R.id.simplePB);

        contentScrollView = itemView.findViewById(R.id.feed_content_scroll_view);
        progressBar=(ProgressBar)itemView.findViewById(R.id.progressBar);
        webinar_time_date_inner_lay=(LinearLayout)itemView.findViewById(R.id.webinar_time_date_inner_lay);
        type_webinar_lay=(RelativeLayout)itemView.findViewById(R.id.type_webinar_lay);
        webinar_type=(TextView)itemView.findViewById(R.id.webinar_type);
        feed_desc_layout = (LinearLayout) itemView.findViewById(R.id.feed_desc_layout);
        eoi_lay=(Button)itemView.findViewById(R.id.if_interested_button);
        tv_date_time_label=(TextView)itemView.findViewById(R.id.tv_webinar_date_time_label);
        tv_organizer_details=(TextView)itemView.findViewById(R.id.tv_organizer);
        view_recording_btn=itemView.findViewById(R.id.view_recording_btn);

        try {
            thumbnailsList.clear();
            feeds_commented_text.setMaxLines(3);
            recommendations_list.setNestedScrollingEnabled(false);
            recommendations_list.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recommendations_list.setLayoutManager(mLayoutManager);
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = (displayMetrics.widthPixels - 8) / 5;
            clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);

            surveyRecyclerView.setNestedScrollingEnabled(false);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width);
            thumbnailView.setLayoutParams(parms);
            thumbnailView.setHasFixedSize(true);
            thumbnailView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            thumbnailImageAdapter = new ThumbnailViewAdapter(mContext, thumbnailsList, viewPager);
            thumbnailView.setAdapter(thumbnailImageAdapter);
            feed_image = mFeedInfo.getAttachmentName();//navigating from media has different response where "attachment_name" will come
            tv_title.setText(mFeedInfo.getTitle());
            if(mFeedInfo.getisEdited() == true){
                tv_edit.setVisibility(View.VISIBLE);
            }else {
                tv_edit.setVisibility(View.GONE);
            }
            /*tv_DescpView.setText(AppUtil.linkifyHtml(mFeedInfo.getFeedDesc(), Linkify.WEB_URLS));
            tv_DescpView.setMovementMethod(BetterLinkMovementMethod.getInstance());
            BetterLinkMovementMethod.linkifyHtml(tv_DescpView).setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
                @Override
                public boolean onClick(TextView textView, String url) {
                    if (url.contains("/external/")) {
                        AppUtil.processUpToDateLink(mContext,url,docID);
                        return true;
                    }
                    return false;
                }
            });*/
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0, 10, 0, 0);
            String style="<style>body{font-size:16; color:#DE231f20)} a{color:#00A76D; text-decoration:none;}</style>";
            WebView webView=new WebView(mContext);
            webView.setId(R.id.feed_description_web);
            webView.setLayoutParams(params1);
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.clearCache(true);
            webView.clearHistory();
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setHorizontalScrollBarEnabled(false);
            String base64version = Base64.encodeToString((style+mFeedInfo.getFeedDesc().replaceAll("\n", "<p>")).getBytes(), Base64.NO_PADDING);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    String mimeType = AppUtil.getMimeType(url);
                    if (mimeType != null && mimeType.startsWith("image/")) {
                        URLList.clear();
                        Intent intent = new Intent(mContext, ImageViewer.class);
                        URLList.add(url);
                        intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                        intent.putExtra(RestUtils.TAG_FILE_PATH, url);
                        intent.putStringArrayListExtra("URLList", URLList);
                        intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                        intent.putExtra(RestUtils.TAG_IMAGE_POSITION, "");
                        mContext.startActivity(intent);
                        return true;
                    }else{
                        if(AppUtil.isYoutubeUrl(url)){
                            Intent in=new Intent(mContext, YoutubeVideoViewActivity.class);
                            in.putExtra("video_id",AppUtil.getVideoIdFromYoutubeUrl(url));
                            in.putExtra("login_user_id",docID);
                            mContext.startActivity(in);
                        }
                        else if(url.contains("/external/")){
                            AppUtil.processUpToDateLink(mContext,url,docID);
                        }
                        else if(AppUtil.isWhitecoatsUrl(url)){
                            AppUtil.openLinkInBrowser(url, mContext);
                        }else{
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("EXTERNAL_LINK", url);
                            mContext.startActivity(intent);
                           // mContext.finish();
                        }
                        return true;
                    }

                }
            });
            webView.loadData(base64version, "text/html; charset=UTF-8", "base64");
            feed_desc_layout.addView(webView);
            if (mFeedInfo.getFeedType()!=null && mFeedInfo.getFeedType().equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
                webinar_time_date.setVisibility(View.GONE);
                eoi_lay.setVisibility(View.GONE);
                type_webinar_lay.setVisibility(View.GONE);
                type_bookmark_lay.setVisibility(View.VISIBLE);
                surveyRootLayout.setVisibility(View.VISIBLE);
                surveyRecyclerView.setVisibility(View.VISIBLE);
                surveyRecyclerView.setHasFixedSize(true);
                mLayoutManager = new CustomLinearLayoutManager(mContext);
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                surveyRecyclerView.setLayoutManager(mLayoutManager);
                surveyOptionsLayout.setVisibility(View.GONE);
                surveyMoreOptionsTxtVw.setVisibility(View.GONE);
                //surveySubmitBtn.setVisibility(View.VISIBLE);
                // Align survey text to middle
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                surveyClosingTxtVw.setLayoutParams(params);
                surveyData = mFeedInfo.getSurveyData();
                if (surveyData != null) {
                    displaySurveyLayout(surveyData, channelName);
                    if (position == getCount() - 1) { // if last question, show submit button else show save button
                        surveySubmitBtn.setText("Submit");
                    } else {
                        surveySubmitBtn.setText("Save");
                    }
                    //displaySurveyLayout(surveyData, channelName);
                    tv_title.setText(surveyData.getQuestions().get(position).getQuestion());

                    optionsAdapter = new SurveyOptionsAdapter(mContext, surveyData, position);
                    surveyRecyclerView.setAdapter(optionsAdapter);
                    AppUtil.ifInterestedButtonVisibility(mContext,mFeedInfo.getEventDetails(),eoi_lay, mFeedInfo.getFeedID(), docID);
                    loadFeedAttachment(surveyData.getQuestions().get(position).getQuestionJsonObj(), itemView.findViewById(R.id.audio_player_layout));
                    surveySubmitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (position != getCount() - 1) { // If not the last question, then move next question
                                List options = FeedsSummary.questionsMap.get(surveyData.getQuestions().get(position).getQuestionId());
                                if (options.size() != 0) {
                                    viewPagerNavigationListener.moveToNextPage();
                                } else {
                                    Toast.makeText(mContext, mContext.getResources().getString(R.string.no_survey_options_selected), Toast.LENGTH_SHORT).show();
                                }
                            } else { // Submit the survey
                                JSONObject requestObject = optionsAdapter.getSurveyRequest(mFeedInfo.getFeedID(), mFeedInfo.getChannelID(), docID);
                                if (requestObject == null)
                                    return;
                                if (((BaseActionBarActivity) mContext).isConnectingToInternet()) {
                                    ((BaseActionBarActivity) mContext).showProgress();
                                    new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.UPDATE_SURVEY, requestObject.toString(), "UPDATE_SURVEY", new OnReceiveResponse() {
                                        @Override
                                        public void onSuccessResponse(String successResponse) {
                                            optionsAdapter.resetRefreshViewValue();
                                            ((BaseActionBarActivity) mContext).hideProgress();
                                            Log.i(TAG, "onSuccessResponse()");
                                            JSONObject responseObj = null;
                                            try {
                                                responseObj = new JSONObject(successResponse);
                                                if (responseObj.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                    if (responseObj.has(RestUtils.TAG_DATA)) {
                                                        JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA).optJSONObject(RestUtils.KEY_FEED_SURVEY);
                                                        for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                                            listener.notifyUIWithFeedSurveyResponse(mFeedInfo.getFeedID(), dataObj);
                                                        }
                                                        surveyData = AppUtil.processSurveyResponseInFullView(surveyData, dataObj);
                                                        displaySurveyLayout(surveyData, channelName);
                                                        optionsAdapter.notifyDataSetChanged();
                                                    }
                                                    // if survey submit successful, then display greet message
                                                    Toast.makeText(mContext, "Thank you for participating", Toast.LENGTH_SHORT).show();
                                                    //
                                                } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                                    String errorMsg = mContext.getResources().getString(R.string.unable_to_connect_server);
                                                    if (responseObj.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                                        errorMsg = responseObj.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                    }
                                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onErrorResponse(String errorResponse) {
                                            Log.i(TAG, "onErrorResponse()");
                                            optionsAdapter.resetRefreshViewValue();
                                            ((BaseActionBarActivity) mContext).hideProgress();
                                            Toast.makeText(mContext, "Something went wrong,please try after sometime.", Toast.LENGTH_SHORT).show();

                                        }
                                    }).sendSinglePartRequest();
                                }
                            }
                        }
                    });

                }

            }else {
                if (mFeedInfo.getFeedSubType()!=null && mFeedInfo.getFeedSubType().equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                    webinar_type.setVisibility(View.VISIBLE);
                    if(mFeedInfo.getEventDetails().optBoolean("is_registration_event")){
                        webinar_type.setText(mFeedInfo.getDisplayTag().toUpperCase());
                    }else {
                        webinar_type.setText(mFeedInfo.getFeedSubType().toUpperCase());
                    }
                    tv_article_type.setVisibility(View.GONE);
                    webinar_time_date.setVisibility(View.VISIBLE);
                    eoi_lay.setVisibility(View.GONE);
                    type_webinar_lay.setVisibility(View.VISIBLE);
                    loadFeedAttachment(mFeedInfo.getFeedInfoJson(), itemView.findViewById(R.id.audio_player_layout));

                } else if(mFeedInfo.getFeedSubType()!=null && mFeedInfo.getFeedSubType().equalsIgnoreCase("eoi")){
                    eoi_lay.setVisibility(View.VISIBLE);
                    loadFeedAttachment(mFeedInfo.getFeedInfoJson(), itemView.findViewById(R.id.audio_player_layout));
                }
                else {
                    tv_article_type.setVisibility(View.VISIBLE);
                    webinar_type.setVisibility(View.GONE);
                    tv_article_type.setText(mFeedInfo.getDisplayTag().toUpperCase());
                    webinar_time_date.setVisibility(View.GONE);
                    eoi_lay.setVisibility(View.GONE);
                    type_webinar_lay.setVisibility(View.GONE);
                    surveyRootLayout.setVisibility(View.GONE);
                    type_bookmark_lay.setVisibility(View.VISIBLE);
                    loadFeedAttachment(mFeedInfo.getFeedInfoJson(), itemView.findViewById(R.id.audio_player_layout));
                }
            }

            /**
             * For Bookmarks
             */
            if (mFeedInfo.isBookmarked()) {
                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
            } else {
                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmark_feed);
            }
            updateUserComments(mFeedInfo.getSocialInteraction());
            AppUtil.ifInterestedButtonVisibility(mContext, mFeedInfo.getEventDetails(),eoi_lay,mFeedInfo.getFeedID(), docID);

            tv_article_type.setText(mFeedInfo.getDisplayTag().toUpperCase());
            tv_article_type.setBackgroundColor(AppUtil.getArticleTypeBGColor(mFeedInfo.getDisplayTag().toLowerCase()));
                 eventDetails = mFeedInfo.getEventDetails();
                if(eventDetails!=null) {
                    if(eventDetails.optBoolean("is_registration_event")){
                        //tv_date_time_label.setVisibility(View.GONE);
                        AppUtil.processRegisterEventData(mContext, docID, mFeedInfo.getFeedID(), mFeedInfo.getTitle(), eventDetails, date_webinar, webinar_time,
                                register_now, join_now, already_registered_icon, already_registered_text, progressBar, webinar_status, webinar_time_date_inner_lay, ended_text,tv_date_time_label,tv_organizer_details);
                    }else {
                        //tv_date_time_label.setVisibility(View.VISIBLE);
                        AppUtil.processWebinarData(mContext, docID, mFeedInfo.getFeedID(), mFeedInfo.getTitle(), eventDetails, date_webinar, webinar_time,
                                register_now, join_now, already_registered_icon, already_registered_text, progressBar, webinar_status, webinar_time_date_inner_lay, ended_text,view_recording_btn,simplePB);
                    }

                }

            bookmark_community_fullView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppUtil.getUserVerifiedStatus() == 3) {
                        if (((BaseActionBarActivity) mContext).isConnectingToInternet()) {
                            /*if (mFeedInfo.isBookmarked()) {
                                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                            } else {
                                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                //AppUtil.loadBounceAnimation(mContext, bookmark_community_fullView_imageView);
                            }*/
                            AppUtil.logUserEventWithParams("FullViewFeedBookmark", basicInfo, mFeedInfo.getFeedID(), mFeedInfo.getChannelID(),channelName,mContext);
                            mFeedInfo.setBookmarked(!mFeedInfo.isBookmarked());
                            //notifyDataSetChanged();
                            if (mFeedInfo.isBookmarked()) {
                                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                            } else {
                                bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                            }
                            try {
                                JSONObject requestObj = new JSONObject();
                                requestObj.put(RestUtils.TAG_DOC_ID, docID);
                                requestObj.put(RestUtils.TAG_FEED_ID, mFeedInfo.getFeedID());
                                requestObj.put(RestUtils.CHANNEL_ID, mFeedInfo.getChannelID());
                                requestObj.put(RestUtils.TAG_IS_BOOKMARK, mFeedInfo.isBookmarked()); // on click set the opposite one.
                                requestBookmarkService(requestObj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                    }
                }
            });


            profileLogo_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (docID == mFeedCreator.getDocID()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra(RestUtils.TAG_DOC_ID, mFeedCreator.getDocID());
                    mContext.startActivity(intent);
                }
            });

            tv_createdBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (docID == mFeedCreator.getDocID()) {
                        return;
                    }
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra(RestUtils.TAG_DOC_ID, mFeedCreator.getDocID());
                    mContext.startActivity(intent);
                }
            });

            if (docID == mFeedCreator.getDocID()) {
                tv_createdBy.setText(R.string.label_you);
            } else {
                tv_createdBy.setText(mFeedCreator.getSalutation() + " " + mFeedCreator.getFullName());
            }
            if (!mFeedCreator.getProfileUrl().isEmpty()) {
               /* Picasso.with(mContext)
                        .load(mFeedCreator.getProfileUrl().trim())
                        .placeholder(R.drawable.default_channel_logo)
                        .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                        .into(profileLogo_fullview);*/
                AppUtil.loadCircularImageUsingLib(mContext,mFeedCreator.getProfileUrl().trim(),profileLogo_fullview,R.drawable.default_channel_logo);
            } else {
                profileLogo_fullview.setImageResource(R.drawable.default_profilepic);
            }

            tv_postedTime.setText(DateUtils.longToMessageListHeaderDate(mFeedInfo.getPostingTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        context_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image_url = "";
                if (postView) {
                    mContext.finish();
                    mContext.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    URLList.clear();
                    if (attachment_type != null && !attachment_type.isEmpty()) {
                        if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                            Intent intent = new Intent(mContext, PdfViewerActivity.class);
                            intent.putExtra(RestUtils.ATTACHMENT_TYPE, attachment_type);
                            intent.putExtra(RestUtils.ATTACHMENT_NAME, mFeedInfo.getTitle());
                            intent.putExtra(RestUtils.TAG_FEED_ID,mFeedInfo.getFeedID());
                            intent.putExtra(RestUtils.CHANNEL_ID,mFeedInfo.getChannelID());
                            intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                            mContext.startActivity(intent);
                        } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                            /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment_original_url));
                            intent.setDataAndType(Uri.parse(attachment_original_url), RestUtils.TAG_TYPE_VIDEO + "/*");
                            mContext.startActivity(intent);*/
                            Intent intent=new Intent(mContext,VideoStreamActivity.class);
                            intent.putExtra("VIDEO_URL",attachment_original_url);
                            mContext.startActivity(intent);
                        } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                            //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment_original_url));
                            Intent intent = new Intent(mContext, AudioController.class);
                            intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                            mContext.startActivity(intent);
                        } else {
                            if (attachment_details_obj != null) {
                                image_url = attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                            }
                            Intent intent = new Intent(mContext, ImageViewer.class);
                            if (!image_url.isEmpty()) {
                                URLList.add(image_url);
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, image_url);
                            } else {
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, mContext.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + feed_image);
                                URLList.add(mContext.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + feed_image);
                            }
                            intent.putStringArrayListExtra("URLList", URLList);
                            intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                            //intent.putExtra("selectedImagePostion", position);
                            intent.putExtra(RestUtils.TAG_IMAGE_POSITION, mFeedInfo.getTitle());
                            mContext.startActivity(intent);
                        }
                    } else {
                        Intent intent = new Intent(mContext, ImageViewer.class);
                        intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                        intent.putExtra(RestUtils.TAG_FILE_PATH, mContext.getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + feed_image);
                        intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                        intent.putExtra(RestUtils.TAG_IMAGE_POSITION, mFeedInfo.getTitle());
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        recommendationsRecyclerAdapter = new RecommendationsRecyclerAdapter(mContext, recommendationsList, docID, recommendations_list, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                if (recommendationsAvi.isShown()) {
                    return;
                }
                if (recommendationsAvi != null) {
                    recommendationsAvi.show();
                }

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                     jsonObject.put("ChannelID",mFeedInfo.getChannelID());
                        jsonObject.put("FeedId",mFeedInfo.getFeedID());
                        jsonObject.put("RelatedChannelID",recommendationsList.get(position).optString(RestUtils.CHANNEL_ID));
                        jsonObject.put("RelatedFeedID",recommendationsList.get(position).optString(RestUtils.TAG_FEED_ID));
                    AppUtil.logUserUpShotEvent("RelatedFeedFullView",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put(RestUtils.TAG_DOC_ID, docID);
                    requestJsonObject.put(RestUtils.CHANNEL_ID, recommendationsList.get(position).optString(RestUtils.CHANNEL_ID));
                    requestJsonObject.put(RestUtils.FEED_ID, recommendationsList.get(position).optString(RestUtils.TAG_FEED_ID));
                    new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "RECOMMENDATIONS_CONTENT_FULLVIEW", new OnReceiveResponse() {
                        @Override
                        public void onSuccessResponse(String successResponse) {
                            try {
                                if (recommendationsAvi != null) {
                                    recommendationsAvi.hide();
                                }
                                JSONObject responseJsonObject = new JSONObject(successResponse);
                                JSONObject completeJsonObject = responseJsonObject.getJSONObject(RestUtils.TAG_DATA);
                                String contentProviderName = completeJsonObject.optString(RestUtils.FEED_PROVIDER_NAME);
                                String feedProviderType = completeJsonObject.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
                                int channel_id = completeJsonObject.optInt(RestUtils.CHANNEL_ID);
                                JSONObject feed_obj = completeJsonObject.getJSONObject(RestUtils.TAG_FEED_INFO);
                                Intent in = new Intent();
                                if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase("article")) {
                                    in.setClass(mContext, ContentFullView.class);
                                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJsonObject.toString());
                                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, contentProviderName);
                                } else if(feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                                    in.setClass(mContext, JobFeedCompleteView.class);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.CHANNEL_NAME, "");
                                    } else {
                                        in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                    }
                                }
                                else {
                                    in.setClass(mContext, FeedsSummary.class);
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.CHANNEL_NAME, "");
                                    } else {
                                        in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                    }
                                }
                                in.putExtra(RestUtils.CHANNEL_ID, channel_id);

                                mContext.startActivity(in);
                                mContext.finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            if (recommendationsAvi != null) {
                                recommendationsAvi.hide();
                            }
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                        }
                    }).sendSinglePartRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        recommendations_list.setAdapter(recommendationsRecyclerAdapter);
        recommendations_list.addItemDecoration(HeaderDecoration.with(recommendations_list)
                .inflate(R.layout.empty_layout)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, mContext));
        if (recommendationsList.size() == 0) {
            interested_article_textView.setVisibility(View.GONE);
            recommendations_list.setVisibility(View.GONE);
        } else {
            interested_article_textView.setVisibility(View.VISIBLE);
            recommendations_list.setVisibility(View.VISIBLE);
        }
        if (mFeedInfo.getSocialInteraction() != null) {
            if (mFeedInfo.getSocialInteraction().has(RestUtils.TAG_COMMENT_INFO) && mFeedInfo.getSocialInteraction().optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && mFeedInfo.getSocialInteraction().optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                feeds_last_comment_layout.setVisibility(View.VISIBLE);
                AppUtil.displayLatestCommentUI(mContext, docID, mFeedInfo.getSocialInteraction().optJSONObject(RestUtils.TAG_COMMENT_INFO), feeds_commented_doc_image, feeds_commented_doc_name, feeds_commented_text);
            } else {
                feeds_last_comment_layout.setVisibility(View.GONE);
            }
        }

        feeds_last_comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(mContext, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, mFeedInfo.getTitle());
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, mFeedInfo.getFeedID());
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, mFeedInfo.getSocialInteraction().optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, mFeedInfo.getChannelID());
                mContext.startActivity(commentIntent);
            }
        });

        contentScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
                }

                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
                    AppUtil.logUserEventWithParams("FullViewFullScroll", basicInfo, mFeedInfo.getFeedID(), mFeedInfo.getChannelID(),channelName,mContext);
                }
            }
        });
        RegPrimaryClipChanged();
        container.addView(itemView);
        return itemView;
    }

    private void ifInterestedButtonVisibility() {
        JSONObject event_details = mFeedInfo.getEventDetails();
        if(event_details!=null){
            if(event_details.optString("type").equalsIgnoreCase("EOI")){
                eoi_lay.setVisibility(View.VISIBLE);
            }else{
               eoi_lay.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        UnRegPrimaryClipChanged();
    }

    private void loadFeedAttachment(JSONObject feedObj, View audioView) {
        final JSONArray attachment_details_array = feedObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
        if (attachment_details_array != null && attachment_details_array.length() > 0) {
            if (attachment_details_array.length() > 1) { // Multi attachment block
                thumbnailView.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                for (int i = 0; i < attachment_details_array.length(); i++) {
                    thumbnailsList.add(attachment_details_array.optJSONObject(i));
                }
                if (thumbnailImageAdapter != null) {
                    thumbnailImageAdapter.notifyDataSetChanged();
                }
                imageViewAdapter = new ImageViewPagerAdapter(mContext, thumbnailsList, mFeedInfo.getTitle(),mFeedInfo.getFeedID(),mFeedInfo.getChannelID(), new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(String s) {
                        ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                    }
                });
                viewPager.setAdapter(imageViewAdapter);
                pageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrollStateChanged(int arg0) {
                    }

                    @Override
                    public void onPageScrolled(int arg0, float arg1, int arg2) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        String attachment_type_text = thumbnailsList.get(position).optString(RestUtils.ATTACHMENT_TYPE);
                        thumbnailImageAdapter.notifyItemChanged(thumbnailImageAdapter.getItemSelectedPosition());
                        thumbnailImageAdapter.setItemSelectedPosition(position);
                        thumbnailImageAdapter.notifyItemChanged(position);

                        if (imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
                            imageViewAdapter.getCurrentViewUtility().getmPlayer().pause();
                            imageViewAdapter.getCurrentViewUtility().getImgbtn_play().setImageResource(R.drawable.ic_playaudio);
                        }
                        if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                            attachment_name.setText(R.string.tap_to_view);
                            attachment_icon.setVisibility(View.VISIBLE);
                            attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                            attachment_layout.setVisibility(View.VISIBLE);
                            attachment_video_type.setVisibility(View.GONE);
                        } else if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                            attachment_name.setText(R.string.tap_to_play);
                            attachment_icon.setVisibility(View.VISIBLE);
                            attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                            attachment_video_type.setVisibility(View.VISIBLE);
                            attachment_layout.setVisibility(View.VISIBLE);
                        } else if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                            attachment_layout.setVisibility(View.GONE);
                            attachment_video_type.setVisibility(View.GONE);
                        } else if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                            if (thumbnailsList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                                attachment_name.setText(R.string.tap_to_zoom);
                                attachment_icon.setVisibility(View.VISIBLE);
                                attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                                attachment_layout.setVisibility(View.VISIBLE);
                                attachment_video_type.setVisibility(View.GONE);
                            } else {
                                attachment_name.setText(R.string.tap_to_zoom);
                                attachment_icon.setVisibility(View.GONE);
                                attachment_layout.setVisibility(View.VISIBLE);
                                attachment_video_type.setVisibility(View.GONE);
                            }
                        }
                    }
                };
                viewPager.addOnPageChangeListener(pageChangeListener);
                viewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        pageChangeListener.onPageSelected(viewPager.getCurrentItem());
                    }
                });
            } else { // Single attachment block
                thumbnailView.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                attachment_details_obj = attachment_details_array.optJSONObject(0);
                attachment_original_url = attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                attachment_type = attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE);
                label_attachment_name = attachment_details_obj.optString(RestUtils.ATTACHMENT_S3_NAME);
                if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                    attachment_name.setText(R.string.tap_to_view);
                    attachment_icon.setVisibility(View.VISIBLE);
                    attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                    if (attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                        context_image.setVisibility(View.VISIBLE);
                        Glide.with(mContext)
                                .load(attachment_details_array.optJSONObject(0).optString(RestUtils.ATTACH_SMALL_URL)).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                return false;
                            }
                        })
                                .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                .into(context_image);
                    } else {
                        context_image.setVisibility(View.GONE);
                    }
                    attachment_layout.setVisibility(View.VISIBLE);
                } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                    attachment_name.setText(R.string.tap_to_play);
                    attachment_icon.setVisibility(View.VISIBLE);
                    attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                    attachment_video_type.setVisibility(View.VISIBLE);
                    if (attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL).isEmpty()) {
                        context_image.setVisibility(View.VISIBLE);
                        Glide.with(mContext)
                                .load(attachment_details_array.optJSONObject(0).optString(RestUtils.ATTACH_SMALL_URL)).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                return false;
                            }
                        })
                                .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                .into(context_image);

                    } else {
                        context_image.setVisibility(View.GONE);
                    }
                    attachment_layout.setVisibility(View.VISIBLE);
                } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                    context_image.setVisibility(View.GONE);
                    audioUtility = new AudioUtility(mContext, audioView, attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL));
                    ((FeedsSummary) mContext).supportStartPostponedEnterTransition();
                    attachment_layout.setVisibility(View.GONE);
                } else {
                    if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                        attachment_name.setText(R.string.tap_to_zoom);
                        attachment_icon.setVisibility(View.VISIBLE);
                        attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                        if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                            context_image.setVisibility(View.VISIBLE);
                            attachment_layout.setVisibility(View.VISIBLE);
                            Glide.with(mContext)
                                    .load(attachment_details_array.optJSONObject(0).optString(RestUtils.ATTACH_ORIGINAL_URL)).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                    return false;
                                }
                            })
                                    .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                    .into(context_image);

                        } else {
                            context_image.setVisibility(View.GONE);
                            attachment_layout.setVisibility(View.GONE);
                        }
                    } else {
                        attachment_name.setText(R.string.tap_to_zoom);
                        attachment_icon.setVisibility(View.GONE);
                        if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                            context_image.setVisibility(View.VISIBLE);
                            attachment_layout.setVisibility(View.VISIBLE);
                            Glide.with(mContext)
                                    .load(attachment_details_array.optJSONObject(0).optString(RestUtils.ATTACH_ORIGINAL_URL)).listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    ((BaseActionBarActivity) mContext).supportStartPostponedEnterTransition();
                                    return false;
                                }
                            })
                                    .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                    .into(context_image);

                        } else {
                            context_image.setVisibility(View.GONE);
                            attachment_layout.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }else {
            context_image.setVisibility(View.GONE);
            attachment_layout.setVisibility(View.GONE);
        }

    }




    public void updateUserComments(JSONObject socialInteractionObj) {
        if(mFeedInfo!=null){
            mFeedInfo.setSocialInteraction(socialInteractionObj);
        }
        if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
            feeds_last_comment_layout.setVisibility(View.VISIBLE);
            AppUtil.displayLatestCommentUI(mContext, docID, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), feeds_commented_doc_image, feeds_commented_doc_name, feeds_commented_text);
        } else {
            feeds_last_comment_layout.setVisibility(View.GONE);
        }
    }

    public void recommendationsService(final int docID, final int channel_id,
                                       final int feedTypeId, String feedType, String feedSubType) {
        JSONObject recommendationsJsonObject = new JSONObject();
        try {
            recommendationsJsonObject.put(RestUtils.TAG_DOC_ID, docID);
            recommendationsJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
            recommendationsJsonObject.put(RestUtils.TAG_FEED_ID, feedTypeId);
            recommendationsJsonObject.put(RestUtils.FEED_TYPE, feedType);
            recommendationsJsonObject.put(RestUtils.TAG_FEED_SUB_TYPE, feedSubType);

            new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.RECOMMENDATIONS_SERVICE, recommendationsJsonObject.toString(), "RECOMMENDATIONS_SERVICE", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        JSONObject jsonObject = new JSONObject(successResponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            JSONObject recommendationJsonObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);

                            if (recommendationJsonObj != null) {
                                FeedsSummary.isRecommendationsUpdated = true;
                                recommendationJsonArray = recommendationJsonObj.optJSONArray(RestUtils.TAG_RECOMMENDATIONS);
                                recommendationsList.clear();
                                for (int i = 0; i < recommendationJsonArray.length(); i++) {
                                    recommendationsList.add(recommendationJsonArray.optJSONObject(i));
                                }
                                if (recommendationsList.size() == 0) {
                                    interested_article_textView.setVisibility(View.GONE);
                                    recommendations_list.setVisibility(View.GONE);
                                } else {
                                    interested_article_textView.setVisibility(View.VISIBLE);
                                    recommendations_list.setVisibility(View.VISIBLE);
                                }
                                if (recommendationsRecyclerAdapter != null) {
                                    recommendationsRecyclerAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("doc_id", "" + docID);
                    map.put("feed_id", "" + feedTypeId);
                    map.put("channel_id", "" + channel_id);
                    FlurryAgent.logEvent("Recommendations_error", map);
                    AppUtil.logSentryEventWithCustomParams("Recommendations_error",map);
                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displaySurveyLayout(FeedSurvey surveyData, String channelName) {
        if (surveyData.isOpen() && !surveyData.isParticipated()) {
            if (surveyData.isEligible()) {
                surveySubmitBtn.setVisibility(View.VISIBLE);
                surveyLaterMsgTxt.setVisibility(View.GONE);
            } else {
                surveySubmitBtn.setVisibility(View.GONE);
                surveyLaterMsgTxt.setVisibility(View.VISIBLE);
                surveyLaterMsgTxt.setText("This is a community survey, only for the community members of " + channelName);
            }
            String time = AppUtil.getSurveyClosingTime( surveyData.getCloseTime(), surveyData.getTimeStamp());
            if (!time.isEmpty())
                surveyClosingTxtVw.setText("Survey closes in " + time);
            else
                surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));

        } else if (surveyData.isParticipated()) {
            surveySubmitBtn.setVisibility(View.GONE);
            if (!surveyData.isOpen()) {// If survey is closed
                //Final result, hide submit button
                surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));
                surveyLaterMsgTxt.setVisibility(View.GONE);
                surveyGreetTxt.setVisibility(View.VISIBLE);
            } else if (surveyData.isOpen()) { // If survey is open
                surveyGreetTxt.setVisibility(View.VISIBLE);
                surveyLaterMsgTxt.setVisibility(View.VISIBLE);
                String time = AppUtil.getSurveyClosingTime(surveyData.getCloseTime(), surveyData.getTimeStamp());
                if (!time.isEmpty())
                    surveyClosingTxtVw.setText("Survey closes in " + time);
                else
                    surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));
            }
            if (surveyData.isImmediate()) {// If results has to display immediately
                String time = AppUtil.getSurveyClosingTime(surveyData.getCloseTime(), surveyData.getTimeStamp());
                if (!time.isEmpty())
                    surveyClosingTxtVw.setText("Survey closes in " + time);
                else
                    surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));
                surveyGreetTxt.setVisibility(View.VISIBLE);
                if (surveyData.isOpen()) {
                    surveyLaterMsgTxt.setVisibility(View.VISIBLE);
                    surveyLaterMsgTxt.setText(mContext.getString(R.string.results_msg_for_immediate_survey));
                } else {
                    surveyLaterMsgTxt.setVisibility(View.GONE);
                }
            }
            if (!surveyData.isEligible()) { // If user not eligible to participate in survey
                //in eligible message
                surveyLaterMsgTxt.setVisibility(View.VISIBLE);
                surveyLaterMsgTxt.setText("This is a community survey, only for the community members of " + channelName);
                //Display Survey close time
                surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));
            }
        } else if (!surveyData.isOpen()) { //If survey is closed
            // Final results
            surveySubmitBtn.setVisibility(View.GONE);
            surveyClosingTxtVw.setText(mContext.getString(R.string.final_survey_result_msg));
            surveyLaterMsgTxt.setVisibility(View.GONE);
        }
    }

    private void requestBookmarkService(JSONObject request) {
        Log.i(TAG, "requestBookmarkService(JSONObject request)");
        try {
            // Service call
            new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.BOOKMARK, request.toString(), "PERSIST_BOOKMARK", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    Log.i(TAG, "onSuccessResponse()");
                    try {
                        if (successResponse != null) {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject dataObject = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                boolean isBookmarked = dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
                                if (isBookmarked) {
                                    Toast.makeText(mContext, mContext.getString(R.string.label_bookmark_added), Toast.LENGTH_SHORT).show();
                                    //bookmarkImage.setImageResource(R.drawable.ic_bookmarked_feed);
                                } else {
                                    Toast.makeText(mContext, mContext.getString(R.string.label_bookmark_removed), Toast.LENGTH_SHORT).show();
                                    //bookmarkImage.setImageResource(R.drawable.ic_bookmark_feed);
                                }
                                mFeedInfo.setBookmarked(isBookmarked);
                                //notifyDataSetChanged();
                                if (mFeedInfo.isBookmarked()) {
                                    bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                } else {
                                    bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                                }
                                for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                    listener.onBookmark(dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED), dataObject.optInt(RestUtils.TAG_FEED_ID), true, dataObject.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = mContext.getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i(TAG, "onErrorResponse() - " + errorResponse);
                    // Revert back to pre stage
                    /*if (mFeedInfo.isBookmarked()) {
                        FeedSummaryAdapter.this.bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                    } else {
                        FeedSummaryAdapter.this.bookmark_community_fullView_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                    }*/
                    mFeedInfo.setBookmarked(!mFeedInfo.isBookmarked());
                    notifyDataSetChanged();
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                Toast.makeText(mContext, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).sendSinglePartRequest();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            Toast.makeText(mContext, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    };

    private void RegPrimaryClipChanged() {
        if (!bHasClipChangedListener) {
            clipboardManager.addPrimaryClipChangedListener(mPrimaryClipChangedListener);
            bHasClipChangedListener = true;
        }
    }

    private void UnRegPrimaryClipChanged() {
        if (bHasClipChangedListener) {
            clipboardManager.removePrimaryClipChangedListener(mPrimaryClipChangedListener);
            bHasClipChangedListener = false;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void playAudio() {

    }

    @Override
    public void pauseAudio() {
        if (audioUtility != null) {
            audioUtility.pauseAudio();
        }
        if (imageViewAdapter!=null && imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
            imageViewAdapter.getCurrentViewUtility().getmPlayer().pause();
            imageViewAdapter.getCurrentViewUtility().getImgbtn_play().setImageResource(R.drawable.ic_playaudio);
        }
    }

    @Override
    public void stopAudio() {
        if (audioUtility != null) {
            audioUtility.stopAudio();
            audioUtility = null;
        }
        if (imageViewAdapter!=null && imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
            imageViewAdapter.getCurrentViewUtility().getmPlayer().stop();
        }
    }
}
