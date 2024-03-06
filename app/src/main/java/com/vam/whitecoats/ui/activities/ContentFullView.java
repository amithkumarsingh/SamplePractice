package com.vam.whitecoats.ui.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.doceree.androidadslibrary.ads.AdRequest;
import com.doceree.androidadslibrary.ads.DocereeAdListener;
import com.doceree.androidadslibrary.ads.DocereeAdView;
import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ImageDownloaderTask;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CustomModel;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.models.AdsDefinition;
import com.vam.whitecoats.ui.adapters.ImageViewPagerAdapter;
import com.vam.whitecoats.ui.adapters.RecommendationsRecyclerAdapter;
import com.vam.whitecoats.ui.adapters.ThumbnailViewAdapter;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.NetworkConnectionListener;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.AudioUtility;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.NetworkConnectListenerManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.UpShotHelperClass;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.GetAdSlotsViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class ContentFullView extends BaseActionBarActivity implements CustomModel.OnUiUpdateListener, NetworkConnectionListener {
    public static final String TAG = ContentFullView.class.getSimpleName();
    private TextView mTitleTextView;
    private TextView contentTitle, contentSpeciality, contentBlurb, contentPostedDate, copyRight;
    private ImageView content_image;
    private String content_large_image;
    private TextView article_type;
    private LinearLayout content_desc_layout, comment_layout_content_fullView, socialbar_layout_content_fullview, bookmark_contentFullview;
    private RelativeLayout social_bar_content_fullview;
    private TextView likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, tv_share_count;
    private ViewGroup like_layout_content_fullview, share_layout_content_fullview;
    private ImageView like_icon_content_fullview, attachment_video_type;
    private TextView like_texview_content_fullview;
    private Realm realm;
    private RealmManager realmManager;
    private int channel_id = 0;
    private String content_provider = "";
    private int feedId;
    private int doc_Id = 0;
    private String type;
    private int c_msg_type = 0;
    private ImageView channel_logo;
    private String micro_image;
    boolean isParallelDashboardCall = false;
    private ArrayList<String> URLList = new ArrayList<>();
    private JSONObject completeJson;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private JSONObject socialInteractionObj;
    private JSONObject jsonObj;
    private ClipboardManager clipboardManager;
    boolean bHasClipChangedListener = false;
    private String emailId;

    private RecyclerView thumbnailView;
    private ThumbnailViewAdapter thumbnailImageAdapter;
    private ArrayList<JSONObject> tumbnailAttachList = new ArrayList<>();
    private ViewPager viewPager;
    private ImageViewPagerAdapter imageViewAdapter;
    private ImageView attachment_icon, profileLogo_fullview, bookmark_contentFullview_imageView;
    private TextView attachment_name, tv_edited;
    private ViewGroup attachment_layout;
    private JSONObject attachment_details_obj = null;
    private String attachment_original_url = "";
    private String attachment_type;
    private String label_attachment_name = "";
    private ImageView context_image;
    File myFile;
    private AudioUtility audioUtility;
    private Activity contextForAudio;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private String attachment_small_url;
    boolean isBookmarked = false;
    private String docName = "";
    private TextView interested_article_textView;
    private RecyclerView recommendations_list;
    private RecommendationsRecyclerAdapter recommendationsRecyclerAdapter;
    private ArrayList<JSONObject> recommendationsList = new ArrayList<>();
    private JSONArray recommendationJsonArray;
    private View content_last_comment_layout;
    private RoundedImageView content_commented_doc_image;
    private TextView content_commented_doc_name;
    private TextView content_commented_text;
    private AVLoadingIndicatorView recommendationsAvi;
    private String text_From_Server = "";
    private String url_From_server = "";
    private boolean isRecommendationsUpdated;
    private String feedType = "", feedTitle = "", articalID = "";
    private String feedSubType = "";
    private Button btn_contact;
    private UpShotHelperClass upshotHelper;
    private RealmBasicInfo basicInfo;
    private NestedScrollView contentScrollView;
    private String contentFullviewtag = "Full View Tag";
    private BrandKinesis bkInstance;
    private CallbackCollectionManager callbackManager;
    private String isUptodatelink = "https://whitecoats.com/external/uptodate";
    private boolean customBackButton = false;
    private boolean likeAPIInprogress = false;
    private LikeActionAsync likeRequestCall;
    private RelativeLayout rl_doceree_ad_layout;
    private JSONObject channelObj;
    private GetAdSlotsViewModel getAdSlotsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_fullview);
        callbackManager = CallbackCollectionManager.getInstance();
        this.contextForAudio = this;
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        Bundle bundle = getIntent().getExtras();
        doc_Id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        docName = realmManager.getDoc_name(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        bkInstance = BrandKinesis.getBKInstance();
        bkInstance.getActivity(this, BKActivityTypes.ACTIVITY_ANY, contentFullviewtag);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (bundle != null) {
            channel_id = bundle.getInt(RestUtils.CHANNEL_ID);
            content_provider = bundle.getString(RestUtils.TAG_CONTENT_PROVIDER);
            type = bundle.getString(RestUtils.TAG_TYPE);
            isParallelDashboardCall = bundle.getBoolean(RestUtils.TAG_IS_PARALLEL_CALL, false);
            if (bundle.getString(RestUtils.TAG_C_MSG_TYPE) != null) {
                c_msg_type = Integer.parseInt(bundle.getString(RestUtils.TAG_C_MSG_TYPE));
            }
            Log.d(TAG, "c_msg_type : " + c_msg_type);
            channelObj = realmManager.getChannelFromDB("listofChannels", channel_id);
        }
        CustomModel.getInstance().setListener(this);
        NetworkConnectListenerManager.registerNetworkListener(this);
        boolean modelState = CustomModel.getInstance().getState();
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        mTitleTextView.setText(content_provider);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = (displayMetrics.widthPixels - 8) / 5;

        contentTitle = (TextView) findViewById(R.id.feed_title);
        contentSpeciality = (TextView) findViewById(R.id.content_speciality);
        contentBlurb = (TextView) findViewById(R.id.feed_short_desc);
        contentPostedDate = (TextView) findViewById(R.id.feed_posted_date);
        copyRight = (TextView) findViewById(R.id.feed_copyright_text);
        content_image = (ImageView) findViewById(R.id.context_image);
        channel_logo = (ImageView) findViewById(R.id.channelLogo_fullview);
        article_type = (TextView) findViewById(R.id.article_type);
        content_desc_layout = (LinearLayout) findViewById(R.id.content_desc_layout);
        comment_layout_content_fullView = (LinearLayout) findViewById(R.id.comment_action_layout);
        social_bar_content_fullview = (RelativeLayout) findViewById(R.id.socialBar_count);
        socialbar_layout_content_fullview = (LinearLayout) findViewById(R.id.social_bar_action);
        comments_count_content_fullview = (TextView) findViewById(R.id.comment_count);

        likes_count_content_fullview = (TextView) findViewById(R.id.like_count);
        view_count_content_fullView = (TextView) findViewById(R.id.view_count);
        tv_share_count = (TextView) findViewById(R.id.tv_share_count);

        attachment_video_type = (ImageView) findViewById(R.id.attachment_video_type);

        like_layout_content_fullview = (ViewGroup) findViewById(R.id.like_action_layout);

        like_icon_content_fullview = (ImageView) findViewById(R.id.like_icon);

        like_texview_content_fullview = (TextView) findViewById(R.id.like_textview);

        share_layout_content_fullview = (ViewGroup) findViewById(R.id.share_layout_dashboard);

        context_image = (ImageView) findViewById(R.id.context_image);
        attachment_name = (TextView) findViewById(R.id.label_attachment_name_summary);
        attachment_layout = (ViewGroup) findViewById(R.id.community_attachment_layout_summary);
        attachment_icon = (ImageView) findViewById(R.id.attachment_icon_summary);
        bookmark_contentFullview = (LinearLayout) findViewById(R.id.bookmark_contentFullview);
        bookmark_contentFullview_imageView = (ImageView) findViewById(R.id.bookmark_contentFullview_imageView);

        interested_article_textView = (TextView) findViewById(R.id.interested_article_textView);
        recommendations_list = (RecyclerView) findViewById(R.id.recommendations_list);

        content_last_comment_layout = findViewById(R.id.content_fullview_comment_layout);
        content_commented_doc_image = (RoundedImageView) content_last_comment_layout.findViewById(R.id.commented_doc_pic);
        content_commented_doc_name = (TextView) content_last_comment_layout.findViewById(R.id.commented_doc_name);
        content_commented_text = (TextView) content_last_comment_layout.findViewById(R.id.latest_comment);
        content_commented_text.setMaxLines(3);
        recommendationsAvi = (AVLoadingIndicatorView) findViewById(R.id.aviInRecommendationsList);
        btn_contact = findViewById(R.id.btn_contact_admin);
        contentScrollView = (NestedScrollView) findViewById(R.id.content_scroll_view);
        tv_edited = (TextView) findViewById(R.id.edited);
        rl_doceree_ad_layout = findViewById(R.id.article_doceree_ad_layout);
        getAdSlotsViewModel = ViewModelProviders.of(this).get(GetAdSlotsViewModel.class);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        recommendations_list.setNestedScrollingEnabled(false);
        recommendations_list.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(ContentFullView.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendations_list.setLayoutManager(mLayoutManager);
        recommendationsRecyclerAdapter = new RecommendationsRecyclerAdapter(ContentFullView.this, recommendationsList, doc_Id, recommendations_list, new OnCustomClickListener() {
            @Override
            public void OnCustomClick(View aView, int position) {
                if (recommendationsAvi.isShown()) {
                    return;
                }
                if (recommendationsAvi != null) {
                    recommendationsAvi.show();
                }
                JSONObject requestJsonObject = new JSONObject();
                try {
                    requestJsonObject.put(RestUtils.TAG_DOC_ID, doc_Id);
                    requestJsonObject.put(RestUtils.CHANNEL_ID, recommendationsList.get(position).optString(RestUtils.CHANNEL_ID));
                    requestJsonObject.put(RestUtils.FEED_ID, recommendationsList.get(position).optString(RestUtils.TAG_FEED_ID));
                    new VolleySinglePartStringRequest(ContentFullView.this, com.android.volley.Request.Method.POST, RestApiConstants.FEED_FULL_VIEW_UPDATED, requestJsonObject.toString(), "RECOMMENDATIONS_CONTENT_FULLVIEW", new OnReceiveResponse() {
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
                                    in.setClass(ContentFullView.this, ContentFullView.class);
                                    in.putExtra(RestUtils.TAG_CONTENT_OBJECT, completeJsonObject.toString());
                                    in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, contentProviderName);
                                } else {
                                    if (feed_obj.optString(RestUtils.TAG_FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                                        in.setClass(ContentFullView.this, JobFeedCompleteView.class);
                                    } else {
                                        in.setClass(ContentFullView.this, FeedsSummary.class);
                                    }
                                    in.putExtra(RestUtils.TAG_FEED_OBJECT, completeJsonObject.toString());
                                    if (feedProviderType.equalsIgnoreCase("Network")) {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, true);
                                    } else {
                                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
                                    }
                                    in.putExtra(RestUtils.CHANNEL_NAME, contentProviderName);
                                }
                                in.putExtra(RestUtils.CHANNEL_ID, channel_id);

                                startActivity(in);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(String errorResponse) {
                            if (recommendationsAvi != null) {
                                recommendationsAvi.hide();
                            }
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
                .build(0, ContentFullView.this));
        AppUtil.logFlurryEventWithDocIdAndEmailEvent("CONTENT_FULLVIEW", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);

        thumbnailView = (RecyclerView) findViewById(R.id.fullView_thumbnail_view);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, width);
        thumbnailView.setLayoutParams(parms);
        thumbnailView.setHasFixedSize(true);
        thumbnailView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        thumbnailImageAdapter = new ThumbnailViewAdapter(ContentFullView.this, tumbnailAttachList, viewPager);
        thumbnailView.setAdapter(thumbnailImageAdapter);
        try {
            completeJson = new JSONObject(getIntent().getStringExtra(RestUtils.TAG_CONTENT_OBJECT));
            if (completeJson.has(RestUtils.TAG_FEED_INFO)) {
                jsonObj = completeJson.optJSONObject(RestUtils.TAG_FEED_INFO);
            } else {
                jsonObj = completeJson;
            }
            feedId = jsonObj.optInt(RestUtils.TAG_FEED_ID);
            feedType = jsonObj.optString(RestUtils.FEED_TYPE);
            feedTitle = jsonObj.optString(RestUtils.TAG_TITLE);
            articalID = jsonObj.getString(RestUtils.TAG_ARTICLE_ID);
            feedSubType = jsonObj.optString(RestUtils.TAG_DISPLAY);
            isBookmarked = jsonObj.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
            AppUtil.logUserEventWithParams("FeedFullView", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
            contentTitle.setText(jsonObj.optString(RestUtils.TAG_TITLE));
            upshotEventData(feedId, channel_id, 0, "", "", "", "", " ", false);
            content_large_image = jsonObj.optString(RestUtils.TAG_LARGE_IMAGE);
            micro_image = jsonObj.optString("micro_image");
            copyRight.setText(jsonObj.optString("wc_copyright"));
            article_type.setText(jsonObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase());
            article_type.setBackgroundColor(AppUtil.getArticleTypeBGColor(jsonObj.optString(RestUtils.TAG_ARTICLE_TYPE).toLowerCase()));
            /**
             * For Bookmarks
             */
            if (isBookmarked) {
                bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
            } else {
                bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
            }
            contentBlurb.setText(jsonObj.optString(RestUtils.TAG_SHORT_DESC));
            socialInteractionObj = jsonObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
            if (socialInteractionObj != null) {
                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
                if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                    like_icon_content_fullview.setImageResource(R.drawable.ic_social_liked);
                    like_texview_content_fullview.setTextColor(Color.parseColor("#00A76D"));
                    like_layout_content_fullview.setTag(true);
                } else {
                    like_icon_content_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_content_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_content_fullview.setTag(false);
                }
                //Handle last comment section ui
                if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                    content_last_comment_layout.setVisibility(View.VISIBLE);
                    AppUtil.displayLatestCommentUI(ContentFullView.this, doc_Id, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), content_commented_doc_image, content_commented_doc_name, content_commented_text);
                } else {
                    content_last_comment_layout.setVisibility(View.GONE);
                }
            } else {
                like_icon_content_fullview.setImageResource(R.drawable.ic_social_like);
                like_texview_content_fullview.setTextColor(Color.parseColor("#DE231f20"));
                like_layout_content_fullview.setTag(false);
            }
            like_layout_content_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!AppUtil.isConnectingToInternet(ContentFullView.this)) {
                        return;
                    }
                    if (likeRequestCall != null && likeRequestCall.getStatus() == AsyncTask.Status.RUNNING) {
                        return;
                    }
                    AppUtil.logUserEventWithParams("FullViewLikeFeed", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                    /**/
                    if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                        Boolean isLiked = false;
                        JSONObject tempLikeObj = new JSONObject();
                        try {
                            tempLikeObj.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
                            tempLikeObj.put(RestUtils.CHANNEL_ID, channel_id);
                            tempLikeObj.put(RestUtils.FEED_TYPE_ID, jsonObj.optInt(RestUtils.TAG_FEED_ID));
                            if ((Boolean) v.getTag()) {
                                isLiked = false;
                                like_icon_content_fullview.setImageResource(R.drawable.ic_social_like);
                                like_texview_content_fullview.setTextColor(Color.parseColor("#DE231f20"));
                                like_layout_content_fullview.setTag(false);
                                if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) > 0) {
                                    int likesCount = Integer.parseInt(socialInteractionObj.optString("likesCount")) - 1;
                                    socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                    socialInteractionObj.put(RestUtils.TAG_IS_LIKE, false);

                                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
                                }
                            } else {
                                isLiked = true;
                                like_icon_content_fullview.setImageResource(R.drawable.ic_social_liked);
                                like_texview_content_fullview.setTextColor(Color.parseColor("#00A76D"));
                                like_layout_content_fullview.setTag(true);
                                if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) >= 0) {
                                    int likesCount = Integer.parseInt(socialInteractionObj.optString(RestUtils.TAG_LIKES_COUNT)) + 1;
                                    socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                    socialInteractionObj.put(RestUtils.TAG_IS_LIKE, true);

                                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
                                }
                                AppUtil.loadBounceAnimation(ContentFullView.this, like_icon_content_fullview);
                            }
                            for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                listener.updateUI(feedId, socialInteractionObj);
                            }

                            tempLikeObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        MakeLikeServiceCall(jsonObj, isLiked);
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(ContentFullView.this, getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(ContentFullView.this, getString(R.string.mca_uploaded_but_not_verified));
                    }
                }
            });
            if (jsonObj.has("article_body")) {
                JSONArray desc_array = jsonObj.optJSONArray("article_body");
                StringBuffer strBuff = new StringBuffer();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 10, 0, 0);
                for (int i = 0; i < desc_array.length(); i++) {
                    String style = "<style>body{font-size:16; color:#DE231f20)} a{color:#00A76D; text-decoration:none;}</style>";
                    WebView webView = new WebView(this);
                    webView.setId(R.id.feed_description_web);
                    webView.setLayoutParams(params);
                    webView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                    webView.clearCache(true);
                    webView.clearHistory();
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setHorizontalScrollBarEnabled(false);
                    String base64version = Base64.encodeToString((style + desc_array.optJSONObject(i).optString(RestUtils.TAG_ARTICLE_DESC).replaceAll("\n", "<p>")).getBytes(), Base64.DEFAULT);
                    webView.loadData(base64version, "text/html; charset=UTF-8", "base64");
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            String mimeType = AppUtil.getMimeType(url);
                            if (mimeType != null && mimeType.startsWith("image/")) {
                                URLList.clear();
                                Intent intent = new Intent(ContentFullView.this, ImageViewer.class);
                                URLList.add(url);
                                intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                intent.putExtra(RestUtils.TAG_FILE_PATH, url);
                                intent.putStringArrayListExtra("URLList", URLList);
                                intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                                intent.putExtra(RestUtils.TAG_IMAGE_POSITION, "");
                                startActivity(intent);
                                return true;
                            } else {
                                if (AppUtil.isYoutubeUrl(url)) {
                                    Intent in = new Intent(mContext, YoutubeVideoViewActivity.class);
                                    in.putExtra("video_id", AppUtil.getVideoIdFromYoutubeUrl(url));
                                    in.putExtra("login_user_id", doc_Id);
                                    mContext.startActivity(in);
                                } else if (url.contains("/external/")) {
                                    AppUtil.processUpToDateLink(ContentFullView.this, url, doc_Id);
                                } else if (AppUtil.isWhitecoatsUrl(url)) {
                                    AppUtil.openLinkInBrowser(url, mContext);
                                } else {
                                    Intent intent = new Intent(mContext, WebViewActivity.class);
                                    intent.putExtra("EXTERNAL_LINK", url);
                                    mContext.startActivity(intent);
                                }
                                return true;
                            }

                        }
                    });
                    content_desc_layout.addView(webView);
                    strBuff.append(desc_array.optJSONObject(i).optString(RestUtils.TAG_ARTICLE_DESC));
                    if (i != (desc_array.length() - 1)) {
                        strBuff.append("\n");
                    }
                    /*
                    call to action
                     */
                    if (desc_array.optJSONObject(i).has(RestUtils.TAG_CALLTOACTION)) {
                        JSONArray call_to_action = desc_array.optJSONObject(i).optJSONArray(RestUtils.TAG_CALLTOACTION);
                        if (call_to_action != null && call_to_action.length() > 0) {
                            String contact_Admin = call_to_action.optJSONObject(i).optString(RestUtils.TAG_BUTTON_LABEL);
                            String contact_Type = call_to_action.optJSONObject(i).optString(RestUtils.TAG_ACTION_TYPE);
                            btn_contact.setText(contact_Admin);
                            btn_contact.setVisibility(View.VISIBLE);
                            btn_contact.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AppUtil.logUserEventWithParams("FullViewCallToAction", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                                    // Create custom dialog object
                                    final Dialog dialog = new Dialog(ContentFullView.this);
                                    // Include layout file
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setContentView(R.layout.custom_dialog_box);
                                    TextView btn_ok = (TextView) dialog.findViewById(R.id.btn_contact_ok);
                                    TextView contactadmin_label = (TextView) dialog.findViewById(R.id.contact_label);
                                    contactadmin_label.setText(contact_Admin);
                                    LinearLayout contactLL = (LinearLayout) dialog.findViewById(R.id.contact_list);


                                    for (int k = 0; k < call_to_action.length(); k++) {
                                        try {

                                            JSONArray elements = call_to_action.getJSONObject(k).getJSONArray(RestUtils.TAG_ELEMENTS);
                                     /*
                                    adding UI elements to layout
                                     */
                                            for (int i = 0; i < elements.length(); i++) {
                                                LinearLayout actionLayout = new LinearLayout(ContentFullView.this);
                                                actionLayout.setOrientation(LinearLayout.HORIZONTAL);
                                                actionLayout.setPadding(0, 0, 0, 14);
                                                String element_type = elements.getJSONObject(i).optString(RestUtils.TAG_ELEMENT_LINK);
                                                String element_value = elements.getJSONObject(i).optString(RestUtils.TAG_ELEMENT_VALUE);

                                                TextView text_value = new TextView(ContentFullView.this);
                                                ImageView contact_image = new ImageView(mContext);
                                                contact_image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80, 60));
                                                contact_image.setMaxHeight(20);
                                                contact_image.setMaxWidth(20);
                                                if (element_type.isEmpty()) {
                                                    contact_image.setVisibility(View.GONE);
                                                    text_value.setPadding(32, 0, 12, 0);
                                                } else {
                                                    contact_image.setVisibility(View.VISIBLE);
                                                    text_value.setPadding(0, 0, 12, 0);

                                                    AppUtil.loadImageUsingGlide(ContentFullView.this, element_type, contact_image, R.drawable.default_image_feed);

                                                }
                                                actionLayout.addView(contact_image);
                                                text_value.setText(element_value);
                                                text_value.setTextSize(14);
                                                text_value.setMovementMethod(LinkMovementMethod.getInstance());
                                                Linkify.addLinks(text_value, Linkify.ALL);
                                                text_value.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        String textValue = text_value.getText().toString();
                                                        if (AppUtil.isPhoneNumber(textValue)) {
                                                            AppUtil.logUserEventWithParams("FullViewContactUsMobile", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                                                        } else if (AppUtil.isEmailAddress(textValue)) {
                                                            AppUtil.logUserEventWithParams("FullViewContactUsEmail", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                                                        } else if (AppUtil.isWebUrl(textValue)) {
                                                            AppUtil.logUserEventWithParams("FullViewContactUsURL", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                                                        }
                                                    }
                                                });
                                                text_value.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                                actionLayout.addView(text_value);
                                                contactLL.addView(actionLayout);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                            /*
                            Dialog button click
                             */
                                    btn_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.show();
                                }
                            });
                        }
                    }


                    JSONArray links_array = desc_array.optJSONObject(i).optJSONArray(RestUtils.TAG_LINKS);
                    for (int j = 0; j < links_array.length(); j++) {
                        TextView links_textview = new TextView(this);
                        links_textview.setLayoutParams(params);
                        links_textview.setTextSize(14);
                        links_textview.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                        String link_name = links_array.getJSONObject(j).optString(RestUtils.TAG_LINK_NAME);
                        String link_url = links_array.getJSONObject(j).optString(RestUtils.TAG_LINK_URL);
                        boolean matches = link_url.startsWith("http://")
                                || link_url.startsWith("https://");
                        if (!matches) {
                            link_url = "http://" + link_url;
                        }
                        String text = "<a href='" + link_url + "'> " + link_name + "</a>";
                        Spannable spannable = (Spannable) Html.fromHtml(text);
                        for (URLSpan u : spannable.getSpans(0, spannable.length(), URLSpan.class)) {
                            spannable.setSpan(new UnderlineSpan() {
                                public void updateDrawState(TextPaint tp) {
                                    tp.setColor(Color.parseColor("#00A76D"));
                                    tp.setUnderlineText(false);
                                }
                            }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0);
                        }
                        links_textview.setText(spannable);
                        if (j == links_array.length() - 1 && i != (desc_array.length() - 1)) {
                            links_textview.append("\n");
                        }
                        links_textview.setClickable(true);
                        links_textview.setMovementMethod(LinkMovementMethod.getInstance());
                        content_desc_layout.addView(links_textview);
                    }

                }
            }
            comment_layout_content_fullView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtil.logUserEventWithParams("FullViewCommentFeedInitiation", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                    Intent commentIntent = new Intent(ContentFullView.this, CommentsActivity.class);
                    commentIntent.putExtra(RestUtils.TAG_POST_NAME, jsonObj.optString(RestUtils.TAG_TITLE));
                    commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, true);
                    commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                    commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                    commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(commentIntent);
                }
            });
            likes_count_content_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent likeIntent = new Intent(ContentFullView.this, CommentsActivity.class);
                    likeIntent.putExtra(RestUtils.TAG_POST_NAME, jsonObj.optString(RestUtils.TAG_TITLE));
                    likeIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                    likeIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                    likeIntent.putExtra(RestUtils.NAVIGATATION, RestUtils.TAG_FROM_LIKES_COUNT);
                    likeIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, (socialInteractionObj == null) ? 0 : socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT));
                    likeIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(likeIntent);
                }
            });
            comments_count_content_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent commentIntent = new Intent(ContentFullView.this, CommentsActivity.class);
                    commentIntent.putExtra(RestUtils.TAG_POST_NAME, jsonObj.optString(RestUtils.TAG_TITLE));
                    commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                    commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                    commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                    commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(commentIntent);
                }
            });

            share_layout_content_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!AppUtil.isConnectingToInternet(ContentFullView.this)) {
                        return;
                    }
                    try {
                        socialInteractionObj.put(RestUtils.TAG_SHARE_COUNT, socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT) + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        listener.updateUI(feedId, socialInteractionObj);
                    }
                    AppUtil.logUserEventWithParams("FullViewShareFeedInitiation", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                    Calendar calendar = Calendar.getInstance();
                    long selectedTime = calendar.getTimeInMillis();
                    if (jsonObj.has(RestUtils.TAG_SHARE_INFO)) {
                        JSONObject shareInfoObj = jsonObj.optJSONObject(RestUtils.TAG_SHARE_INFO);
                        text_From_Server = shareInfoObj.optString(RestUtils.TAG_SERVER_TEXT);
                        url_From_server = shareInfoObj.optString(RestUtils.TAG_SERVER_URL);
                    }
                    JSONObject encryptedStringData = AppUtil.encryptFeedData(ContentFullView.this, feedId, doc_Id, channel_id, selectedTime, realmManager.getDocSalutation(realm) + " " + docName, "\"" + contentTitle.getText().toString() + "\" ", text_From_Server, url_From_server);
                    if (encryptedStringData != null) {
                        final JSONObject shareFeedRequestObj = new JSONObject();
                        JSONObject innerObj = new JSONObject();
                        try {
                            shareFeedRequestObj.put(RestUtils.TAG_USER_ID, doc_Id);
                            innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                            innerObj.put(RestUtils.TAG_FEED_ID, feedId);
                            innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                            shareFeedRequestObj.put(RestUtils.TAG_SHARE_URL, encryptedStringData.optString(RestUtils.TAG_ORIGINAL_URL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.requestForShortURL(ContentFullView.this, realmManager, shareFeedRequestObj.toString(), innerObj.toString(), encryptedStringData);
                    }
                }
            });
            bookmark_contentFullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppUtil.getUserVerifiedStatus() == 3) {
                        if (isConnectingToInternet()) {
                            AppUtil.logUserEventWithParams("FullViewFeedBookmark", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                            if (isBookmarked) {
                                bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                            } else {
                                bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                AppUtil.loadBounceAnimation(ContentFullView.this, bookmark_contentFullview_imageView);
                            }
                            try {
                                JSONObject requestObj = new JSONObject();
                                requestObj.put(RestUtils.TAG_DOC_ID, doc_Id);
                                requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                                requestObj.put(RestUtils.CHANNEL_ID, channel_id);
                                requestObj.put(RestUtils.TAG_IS_BOOKMARK, !isBookmarked); // on click set the opposite one.
                                requestBookmarkService(requestObj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(ContentFullView.this, getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(ContentFullView.this, getString(R.string.mca_uploaded_but_not_verified));
                    }

                }
            });

            try {
                contentPostedDate.setText(DateUtils.longToMessageListHeaderDate(Long.parseLong(jsonObj.optString(RestUtils.TAG_POSTING_TIME))));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //added for multiple images
            if (jsonObj.has(RestUtils.ATTACHMENT_DETAILS)) {
                JSONArray attachment_details_array = jsonObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                if (attachment_details_array != null && attachment_details_array.length() > 0) {

                    if (attachment_details_array.length() > 1) {
                        thumbnailView.setVisibility(View.VISIBLE);
                        viewPager.setVisibility(View.VISIBLE);
                        for (int i = 0; i < attachment_details_array.length(); i++) {
                            tumbnailAttachList.add(attachment_details_array.optJSONObject(i));
                        }
                        if (thumbnailImageAdapter != null) {
                            thumbnailImageAdapter.notifyDataSetChanged();
                        }
                        imageViewAdapter = new ImageViewPagerAdapter(ContentFullView.this, tumbnailAttachList, mTitleTextView.getText().toString(), feedId, channel_id, new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                supportStartPostponedEnterTransition();
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
                                thumbnailImageAdapter.notifyItemChanged(thumbnailImageAdapter.getItemSelectedPosition());
                                thumbnailImageAdapter.setItemSelectedPosition(position);
                                thumbnailImageAdapter.notifyItemChanged(position);
                                String attachment_type_text = tumbnailAttachList.get(position).optString(RestUtils.ATTACHMENT_TYPE);
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
                                    attachment_video_type.setVisibility(View.VISIBLE);
                                    attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                                    attachment_layout.setVisibility(View.VISIBLE);
                                } else if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                                    attachment_icon.setVisibility(View.GONE);
                                    attachment_layout.setVisibility(View.GONE);
                                    attachment_video_type.setVisibility(View.GONE);
                                } else if (attachment_type_text.equalsIgnoreCase(RestUtils.TAG_TYPE_IMAGE)) {
                                    if (tumbnailAttachList.get(position).optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
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
                    } else {
                        thumbnailView.setVisibility(View.GONE);
                        viewPager.setVisibility(View.GONE);
                        attachment_details_obj = attachment_details_array.optJSONObject(0);
                        attachment_original_url = attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                        attachment_small_url = attachment_details_obj.optString(RestUtils.ATTACH_SMALL_URL);
                        attachment_type = attachment_details_obj.optString(RestUtils.ATTACHMENT_TYPE);
                        label_attachment_name = attachment_details_obj.optString(RestUtils.ATTACHMENT_S3_NAME);
                        if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                            attachment_name.setText(R.string.tap_to_view);
                            attachment_icon.setVisibility(View.VISIBLE);
                            attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                            if (attachment_small_url != null && !attachment_small_url.isEmpty()) {
                                context_image.setVisibility(View.VISIBLE);
                                Glide.with(getApplicationContext())
                                        .load(attachment_small_url).listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }
                                        })
                                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                        .into(content_image);

                            } else {
                                context_image.setVisibility(View.GONE);
                            }
                            attachment_layout.setVisibility(View.VISIBLE);
                        } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                            attachment_name.setText(R.string.tap_to_play);
                            attachment_icon.setVisibility(View.VISIBLE);
                            attachment_video_type.setVisibility(View.VISIBLE);
                            attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                            if (attachment_small_url != null && !attachment_small_url.isEmpty()) {
                                context_image.setVisibility(View.VISIBLE);
                                Glide.with(getApplicationContext())
                                        .load(attachment_small_url).listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }
                                        })
                                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                        .into(content_image);
                            } else {
                                context_image.setVisibility(View.GONE);
                            }
                            attachment_layout.setVisibility(View.VISIBLE);
                        } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                            context_image.setVisibility(View.GONE);
                            audioUtility = new AudioUtility(contextForAudio, findViewById(R.id.audio_player_layout), attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL));
                            supportStartPostponedEnterTransition();

                        } else {
                            if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).contains(RestUtils.TAG_TYPE_GIF)) {
                                attachment_name.setText(R.string.tap_to_zoom);
                                attachment_icon.setVisibility(View.VISIBLE);
                                attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                            } else {
                                attachment_name.setText(R.string.tap_to_zoom);
                                attachment_icon.setVisibility(View.GONE);
                            }
                            if (attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL) != null && !attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL).isEmpty()) {
                                context_image.setVisibility(View.VISIBLE);
                                attachment_layout.setVisibility(View.VISIBLE);
                                Glide.with(getApplicationContext())
                                        .load(attachment_original_url).listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                supportStartPostponedEnterTransition();
                                                return false;
                                            }
                                        })
                                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                        .into(content_image);
                            } else {
                                context_image.setVisibility(View.GONE);
                                attachment_layout.setVisibility(View.GONE);
                            }
                        }
                    }
                } else if (content_large_image != null && !content_large_image.isEmpty() && !content_large_image.equals("null")) {
                    myFile = AppUtil.getExternalStoragePathFile(ContentFullView.this, ".Whitecoats/feed_pic/" + content_large_image);
                    if (myFile.exists()) {
                        context_image.setVisibility(View.VISIBLE);
                        context_image.setVisibility(View.VISIBLE);
                        attachment_layout.setVisibility(View.VISIBLE);
                        attachment_name.setText(R.string.tap_to_zoom);
                        attachment_icon.setVisibility(View.GONE);
                        Glide.with(getApplicationContext())
                                .load(content_large_image).listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        supportStartPostponedEnterTransition();
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        supportStartPostponedEnterTransition();
                                        return false;
                                    }
                                })
                                .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                .into(content_image);

                    } else {
                        new ImageDownloaderTask(context_image, this, new OnTaskCompleted() {
                            @Override
                            public void onTaskCompleted(String s) {
                                if (s != null) {
                                    if (s.equals("SocketTimeoutException") || s.equals("Exception")) {
                                        Log.i(TAG, "onTaskCompleted(String response) " + s);
                                        hideProgress();
                                        ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
                                    } else {
                                        try {
                                            context_image.setVisibility(View.VISIBLE);
                                            attachment_layout.setVisibility(View.VISIBLE);
                                            attachment_name.setText(R.string.tap_to_zoom);
                                            attachment_icon.setVisibility(View.GONE);
                                            JSONObject responseJson = new JSONObject(s);
                                            String original_link = responseJson.optString(RestUtils.TAG_ORIGINAL_LINK);

                                            Glide.with(getApplicationContext())
                                                    .load(original_link).listener(new RequestListener<Drawable>() {
                                                        @Override
                                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                            supportStartPostponedEnterTransition();
                                                            return false;
                                                        }

                                                        @Override
                                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                            supportStartPostponedEnterTransition();
                                                            return false;
                                                        }
                                                    })
                                                    .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                                                    .into(content_image);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }).execute(content_large_image, "feed_pic", "");
                    }
                } else {
                    context_image.setVisibility(View.GONE);
                    attachment_layout.setVisibility(View.GONE);
                }
            } else if (content_large_image != null && !content_large_image.isEmpty()) {
                attachment_type = "image";
                content_image.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext())
                        .load(content_large_image).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                supportStartPostponedEnterTransition();
                                return false;
                            }
                        })
                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                        .into(content_image);

            } else {
                content_image.setVisibility(View.GONE);
            }


            content_last_comment_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent commentIntent = new Intent(ContentFullView.this, CommentsActivity.class);
                    commentIntent.putExtra(RestUtils.TAG_POST_NAME, jsonObj.optString(RestUtils.TAG_TITLE));
                    commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                    commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                    commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                    commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(commentIntent);

                }
            });
            content_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    URLList.clear();
                    String image_url = "";
                    if (content_large_image != null && !content_large_image.isEmpty()) {
                        image_url = content_large_image;
                        if (attachment_type != null && !attachment_type.isEmpty()) {
                            if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_PDF)) {
                                Intent intent = new Intent(ContentFullView.this, PdfViewerActivity.class);
                                intent.putExtra(RestUtils.TAG_FEED_ID, feedId);
                                intent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                                intent.putExtra(RestUtils.ATTACHMENT_TYPE, attachment_type);
                                intent.putExtra(RestUtils.ATTACHMENT_NAME, jsonObj.optString(RestUtils.TAG_TITLE));
                                intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                                startActivity(intent);
                            } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_VIDEO)) {
                                Intent intent = new Intent(ContentFullView.this, VideoStreamActivity.class);
                                intent.putExtra("VIDEO_URL", attachment_original_url);
                                startActivity(intent);
                            } else if (attachment_type.equalsIgnoreCase(RestUtils.TAG_TYPE_AUDIO)) {
                                Intent intent = new Intent(ContentFullView.this, AudioController.class);
                                intent.putExtra(RestUtils.ATTACH_ORIGINAL_URL, attachment_original_url);
                                startActivity(intent);
                            } else {
                                if (attachment_details_obj != null) {
                                    image_url = attachment_details_obj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                                }
                                Intent intent = new Intent(ContentFullView.this, ImageViewer.class);
                                if (!image_url.isEmpty()) {
                                    URLList.add(image_url);
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, true);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, image_url);
                                } else {
                                    intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                                    intent.putExtra(RestUtils.TAG_FILE_PATH, getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + content_large_image);
                                    URLList.add(getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + content_large_image);
                                }
                                intent.putStringArrayListExtra("URLList", URLList);
                                intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                                intent.putExtra(RestUtils.TAG_IMAGE_POSITION, jsonObj.optString(RestUtils.TAG_TITLE));
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(ContentFullView.this, ImageViewer.class);
                            intent.putExtra(RestUtils.TAG_LOAD_USING_PICASSO, false);
                            intent.putExtra(RestUtils.TAG_FILE_PATH, getExternalFilesDir(null).getAbsolutePath() + "/.Whitecoats/feed_pic/" + content_large_image);
                            intent.putExtra(RestUtils.TAG_IS_OPTIONS_ENABLE, false);
                            intent.putExtra(RestUtils.TAG_IMAGE_POSITION, jsonObj.optString(RestUtils.TAG_TITLE));
                            startActivity(intent);
                        }
                    }
                }
            });

            if (micro_image != null && !micro_image.isEmpty()) {
                channel_logo.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext())
                        .load(micro_image)
                        .apply(AppUtil.getRequestOptions(mContext, ContextCompat.getDrawable(mContext, R.drawable.default_image_feed)))
                        .into(channel_logo);
            } else {
                channel_logo.setVisibility(View.GONE);
            }
            if (jsonObj.optBoolean(RestUtils.TAG_IS_EDITED)) {
                tv_edited.setVisibility(View.VISIBLE);
            } else {
                tv_edited.setVisibility(View.GONE);
            }
            StringBuffer stBuff = new StringBuffer();
            if (jsonObj.has(RestUtils.TAG_SPLTY)) {
                for (int i = 0; i < jsonObj.optJSONArray(RestUtils.TAG_SPLTY).length(); i++) {
                    stBuff.append(jsonObj.optJSONArray(RestUtils.TAG_SPLTY).optString(i));
                    if (i != jsonObj.optJSONArray(RestUtils.TAG_SPLTY).length() - 1) {
                        stBuff.append(", ");
                    }
                }
            }
            contentSpeciality.setText(stBuff.toString());
            if (AppUtil.isConnectingToInternet(ContentFullView.this)) {
                contentFullViewService(doc_Id, channel_id, feedId);
                recommendationsService(doc_Id, channel_id, feedId, feedType, feedSubType);
            }
            if (c_msg_type == 0 && type != null) {
                if (type.equalsIgnoreCase(RestUtils.TAG_TYPE_LIKE)) {
                    likes_count_content_fullview.performClick();
                } else if (type.equalsIgnoreCase(RestUtils.TAG_TYPE_COMMENT)) {
                    comments_count_content_fullview.performClick();
                }
            } else if (c_msg_type == 8) {
                likes_count_content_fullview.performClick();
            } else if (c_msg_type == 9) {
                comments_count_content_fullview.performClick();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        presentShowcaseSequence();
        upshotHelper = UpShotHelperClass.getInstance();
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
                    AppUtil.logUserEventWithParams("FullViewFullScroll", basicInfo, feedId, channel_id, content_provider, ContentFullView.this);
                }
            }
        });

        getAdSlotsViewModel.setRequestData(doc_Id, channel_id, feedId, AppUtil.getRequestHeaders(this));
        getAdSlotsViewModel.getFeedAdSlots().observe(this, getAdSlotsApiResponse -> {
            if (getAdSlotsApiResponse == null) {
                return;
            }
            if (getAdSlotsApiResponse.getError() == null && getAdSlotsApiResponse.getAdDefinitions().size() > 0) {
                AdsDefinition adDefinitionObj = getAdSlotsApiResponse.getAdDefinitions().get(0);
                try {
                    DocereeAdView docereeAdView = new DocereeAdView(ContentFullView.this);
                    docereeAdView.setVisibility(View.GONE);
                    rl_doceree_ad_layout.addView(docereeAdView);
                    docereeAdView.setAdSlotId(adDefinitionObj.getSourceSlotId());
                    docereeAdView.setAdSize(adDefinitionObj.getDimension());
                    AdRequest adRequest = new AdRequest.AdRequestBuilder().build();
                    docereeAdView.loadAd(adRequest, new DocereeAdListener() {

                        @Override
                        public void onAdOpened() {
                            super.onAdOpened();
                        }

                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                            AppUtil.logAdEvent(adDefinitionObj, "Feed_Ad_Clicked", doc_Id, ContentFullView.this);
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            docereeAdView.setVisibility(View.VISIBLE);
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // remove view
                                    rl_doceree_ad_layout.setVisibility(View.GONE);
                                }
                            }, (adDefinitionObj.getAdSlotDuration()) * 1000L);
                            AppUtil.logAdEvent(adDefinitionObj, "Feed_Ad_loaded", doc_Id, ContentFullView.this);
                        }

                        @Override
                        public void onAdFailedToLoad(String message) {
                            super.onAdFailedToLoad(message);
                            docereeAdView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAdLeftApplication() {
                            super.onAdLeftApplication();
                        }
                    });
                } catch (IllegalArgumentException e) {
                    Log.e("LOADING_EXCEPTION", e.getLocalizedMessage());
                }
            }
        });

        /*int ad_slot_type = SlotLocationType.ARTICLE_FULL_VIEW_SLOT.getType();
        if (channelObj != null && channelObj.optString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE).equalsIgnoreCase("Industry")) {
            ad_slot_type = SlotLocationType.INDUSTRY_FEED_SLOT.getType();
        }
        RealmResults<RealmAdSlotInfo> adSlotResults = realmManager.getAdSlotInfoByLocation(ad_slot_type);
        if (adSlotResults.size() > 0) {
            try {
                DocereeAdView docereeAdView = new DocereeAdView(this);
                docereeAdView.setVisibility(View.GONE);
                rl_doceree_ad_layout.addView(docereeAdView);
                docereeAdView.setAdSlotId(adSlotResults.get(0).getSource_slot_id());
                docereeAdView.setAdSize(adSlotResults.get(0).getDimensions());
                final RealmAdSlotInfo adSlotInfo = adSlotResults.get(0);
                AdRequest adRequest = new AdRequest.AdRequestBuilder().build();
                docereeAdView.loadAd(adRequest, new DocereeAdListener() {

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        AppUtil.logAdEvent(adSlotInfo, "Article_Ad_loaded", realmManager.getDoc_id(realm), ContentFullView.this);
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        docereeAdView.setVisibility(View.VISIBLE);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // remove view
                                rl_doceree_ad_layout.setVisibility(View.GONE);
                            }
                        }, (adSlotResults.get(0).getAd_slot_duration()) * 1000L);
                        AppUtil.logAdEvent(adSlotInfo, "Article_Ad_loaded", realmManager.getDoc_id(realm), ContentFullView.this);
                    }

                    @Override
                    public void onAdFailedToLoad(String message) {
                        super.onAdFailedToLoad(message);
                        docereeAdView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        super.onAdLeftApplication();
                    }
                });
            } catch (IllegalArgumentException e) {
                Log.e("LOADING_EXCEPTION", e.getLocalizedMessage());
            }
        }*/
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            Toast.makeText(ContentFullView.this, "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        RegPrimaryClipChanged();
        AppUtil.logScreenEvent("FeedFullViewTimeSpent");

    }

    @Override
    protected void onPause() {
        super.onPause();
        UnRegPrimaryClipChanged();
        if (imageViewAdapter != null && imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
            imageViewAdapter.getCurrentViewUtility().getmPlayer().pause();
        }
        if (audioUtility != null) {
            audioUtility.pauseAudio();
        }
    }

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

    private void contentFullViewService(int doc_id, int channel_id, int feedTypeId) {
        JSONObject fullViewRequestJsonObject = new JSONObject();
        try {
            fullViewRequestJsonObject.put(RestUtils.TAG_DOC_ID, doc_id);
            fullViewRequestJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
            fullViewRequestJsonObject.put(RestUtils.FEED_ID, feedTypeId);
            //showProgress();
            new VolleySinglePartStringRequest(ContentFullView.this, Request.Method.POST, RestApiConstants.VIEW_FEED_REST, fullViewRequestJsonObject.toString(), "VIEW_FEED", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    responseHandler(successResponse);
                }

                @Override
                public void onErrorResponse(String errorResponse) {

                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 1) {
            MaterialShowcaseView.resetSingleUse(ContentFullView.this, "Sequence_ContentFullview");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_ContentFullview");
            sequence.setConfig(config);
            if (!editor.getBoolean("ContentFullview", false)) {
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(this)
                                .setTarget(socialbar_layout_content_fullview)
                                .setDismissText("Got it")
                                .setDismissTextColor(Color.parseColor("#00a76d"))
                                .setMaskColour(Color.parseColor("#CC231F20"))
                                .setContentText(R.string.tap_to_coach_mark_contentFullView).setListener(new IShowcaseListener() {
                                    @Override
                                    public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                        editor.edit().putBoolean("ContentFullview", true).commit();
                                    }

                                    @Override
                                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                    }
                                })
                                .withRectangleShape()
                                .build()
                );
            }
            sequence.start();
        }
    }

    private void MakeLikeServiceCall(final JSONObject subItem, Boolean isLiked) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, subItem.optInt(RestUtils.TAG_FEED_ID));
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put(RestUtils.TAG_TYPE, "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(ContentFullView.this)) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + subItem.optInt(RestUtils.TAG_ARTICLE_ID));
        }


        likeRequestCall = new LikeActionAsync(ContentFullView.this, RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String sResponse) {
                if (AppConstants.likeActionList.contains(channel_id + "_" + subItem.optInt(RestUtils.TAG_ARTICLE_ID))) {
                    AppConstants.likeActionList.remove(channel_id + "_" + subItem.optInt(RestUtils.TAG_ARTICLE_ID));
                }
                if (sResponse != null) {
                    responseHandler(sResponse);
                }

            }
        });

        likeRequestCall.execute(likeRequest.toString());
    }

    private void responseHandler(String sResponse) {
        if (sResponse.equals("SocketTimeoutException") || sResponse.equals("Exception")) {
            Log.i(TAG, "responseHandler(String response) " + sResponse);
            Log.e("Error like response", getResources().getString(R.string.timeoutException));
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(sResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonObject != null && jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                JSONObject likeResponseObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                JSONObject responseSocialInteractionObj = likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                socialInteractionObj = responseSocialInteractionObj;
                if (responseSocialInteractionObj != null) {
                    AppUtil.toggleSocialBarViewCount(responseSocialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
                    if (responseSocialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                        like_icon_content_fullview.setImageResource(R.drawable.ic_social_liked);
                        like_texview_content_fullview.setTextColor(Color.parseColor("#00A76D"));
                        like_layout_content_fullview.setTag(true);
                    } else {
                        like_icon_content_fullview.setImageResource(R.drawable.ic_social_like);
                        like_texview_content_fullview.setTextColor(Color.parseColor("#DE231f20"));
                        like_layout_content_fullview.setTag(false);
                    }
                    for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                        listener.updateUI(feedId, responseSocialInteractionObj);
                    }
                } else {
                    like_icon_content_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_content_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_content_fullview.setTag(false);
                }

            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("FeedId", feedId);
                    jsonObject.put("ChannelID", channel_id);
                    AppUtil.logUserUpShotEvent("FeedFullViewBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;

            case R.id.action_report_to_spam:
                BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
                Bundle bundle = new Bundle();
                bundle.putInt("feedId", feedId);
                bundle.putInt("docId", doc_Id);
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheetReportSpam");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onUiUpdateForComments(JSONObject mObj) {
        JSONObject socialInteractionObj = mObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
        if (socialInteractionObj != null) {
            AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_content_fullview, comments_count_content_fullview, view_count_content_fullView, social_bar_content_fullview, socialbar_layout_content_fullview, like_layout_content_fullview, comment_layout_content_fullView, share_layout_content_fullview, bookmark_contentFullview, tv_share_count);
            if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                content_last_comment_layout.setVisibility(View.VISIBLE);
                AppUtil.displayLatestCommentUI(getApplicationContext(), doc_Id, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), content_commented_doc_image, content_commented_doc_name, content_commented_text);
            } else {
                content_last_comment_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();
        if (imageViewAdapter != null && imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
            imageViewAdapter.getCurrentViewUtility().getmPlayer().stop();
        }
        if (audioUtility != null) {
            audioUtility.stopAudio();
            audioUtility = null;
        }
        NetworkConnectListenerManager.removeNetworkListener(this);
        CustomModel.getInstance().setListener(null);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            if (!customBackButton) {
                customBackButton = false;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("FeedId", feedId);
                    jsonObject.put("ChannelID", channel_id);
                    AppUtil.logUserUpShotEvent("FeedFullViewDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, isParallelDashboardCall);
            startActivity(intent);
        }
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("FeedId", feedId);
                jsonObject.put("ChannelID", channel_id);
                AppUtil.logUserUpShotEvent("ContentFullViewDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ActivityCompat.finishAfterTransition(this);
    }


    private void requestBookmarkService(JSONObject request) {
        Log.i(TAG, "requestBookmarkService(JSONObject request)");
        try {
            // Service call
            new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.BOOKMARK, request.toString(), "PERSIST_BOOKMARK", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    Log.i(TAG, "onSuccessResponse()");
                    try {
                        if (successResponse != null) {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                JSONObject dataObject = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                isBookmarked = dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
                                if (isBookmarked) {
                                    Toast.makeText(ContentFullView.this, getString(R.string.label_bookmark_added), Toast.LENGTH_SHORT).show();
                                    bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                } else {
                                    Toast.makeText(ContentFullView.this, getString(R.string.label_bookmark_removed), Toast.LENGTH_SHORT).show();
                                    bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                                }
                                for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                    listener.onBookmark(dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED), dataObject.optInt(RestUtils.TAG_FEED_ID), true, dataObject.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(ContentFullView.this, errorMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    Log.i(TAG, "onErrorResponse() - " + errorResponse);
                    //  revert back to pre stage
                    if (isBookmarked) {
                        bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                    } else {
                        bookmark_contentFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                    }
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                Toast.makeText(ContentFullView.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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

    private void recommendationsService(final int doc_id, final int channel_id, final int feedTypeId, String feedType, String feedSubType) {
        JSONObject recommendationsJsonObject = new JSONObject();
        try {
            recommendationsJsonObject.put(RestUtils.TAG_DOC_ID, doc_id);
            recommendationsJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
            recommendationsJsonObject.put(RestUtils.TAG_FEED_ID, feedTypeId);
            recommendationsJsonObject.put(RestUtils.FEED_TYPE, feedType);
            recommendationsJsonObject.put(RestUtils.TAG_FEED_SUB_TYPE, feedSubType);

            new VolleySinglePartStringRequest(ContentFullView.this, Request.Method.POST, RestApiConstants.RECOMMENDATIONS_SERVICE, recommendationsJsonObject.toString(), "RECOMMENDATIONS_SERVICE", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    try {
                        JSONObject jsonObject = new JSONObject(successResponse);
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            JSONObject recommendationJsonObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);

                            if (recommendationJsonObj != null) {
                                isRecommendationsUpdated = true;
                                recommendationJsonArray = recommendationJsonObj.optJSONArray(RestUtils.TAG_RECOMMENDATIONS);
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
                    map.put("doc_id", "" + doc_id);
                    map.put("feed_id", "" + feedTypeId);
                    map.put("channel_id", "" + channel_id);
                    FlurryAgent.logEvent("Recommendations_error", map);
                    AppUtil.logSentryEventWithCustomParams("Recommendations_error", map);
                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkReconnection() {
        if (!isRecommendationsUpdated && doc_Id != 0) {
            recommendationsService(doc_Id, channel_id, feedId, feedType, feedSubType);
        }
    }

    @Override
    public void onNetworkDisconnect() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_spam, menu);
        return true;
    }


}
