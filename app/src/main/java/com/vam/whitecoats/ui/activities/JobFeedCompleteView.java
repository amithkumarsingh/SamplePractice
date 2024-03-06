package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.doceree.androidadslibrary.ads.AdRequest;
import com.doceree.androidadslibrary.ads.DocereeAdListener;
import com.doceree.androidadslibrary.ads.DocereeAdView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.constants.SlotLocationType;
import com.vam.whitecoats.core.models.CustomModel;
import com.vam.whitecoats.core.realm.RealmAdSlotInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.models.AdsDefinition;
import com.vam.whitecoats.ui.adapters.JobInfoTabAdapter;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.GetAdSlotsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import io.realm.Realm;
import io.realm.RealmResults;

public class JobFeedCompleteView extends BaseActionBarActivity implements CustomModel.OnUiUpdateListener {
    public static final String TAG = JobFeedCompleteView.class.getSimpleName();
    private JobInfoTabAdapter tabPageAdpater;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ArrayList<String> tabNames = new ArrayList<>();
    private Realm realm;
    private RealmManager realmManager;
    private int login_doc_id;
    private String doc_name;
    private RealmBasicInfo basicInfo;
    private String channelName;
    private int channel_id;
    private JSONObject channelObj;
    private String feed_provider_type;
    private boolean isNetworkChannel;
    private int position;
    private String type;
    private String feedDataString;
    private JSONObject completeFeedObj;
    private JSONObject socialInteractionObj;
    private LinearLayout comment_layout_job_fullView;
    private RelativeLayout social_bar_job_fullview;
    private LinearLayout socialbar_layout_job_fullview;
    private TextView comments_count_job_fullview;
    private TextView likes_count_job_fullview;
    private TextView view_count_job_fullView;
    private TextView tv_share_count;
    private ViewGroup like_layout_job_fullview;
    private ImageView like_icon_job_fullview;
    private TextView like_texview_job_fullview;
    private ViewGroup share_layout_job_fullview;
    private ImageView bookmark_jobFullview_imageView;
    private JSONObject feedInfoObj;
    private boolean isBookmarked;
    private LinearLayout bookmark_jobFullview;
    private TextView feedTypeLable;
    private TextView tvFeedTitle;
    private TextView mTitleTextView;
    private ImageButton delete_btn, edit_btn;
    private LikeActionAsync likeAPICall;
    private String emailId;
    private String text_From_Server;
    private String url_From_server;
    private int feedId;
    private String feedTitle;
    private CallbackCollectionManager callbackManager;
    private boolean isDeletable;
    private boolean display_status;
    private RelativeLayout doceree_ad_layout;
    private GetAdSlotsViewModel getAdSlotsViewModel;

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobfeedcompleteview);
        AppUtil.createDirIfNotExists(this, "/JobFeedAttachments");
        CustomModel.getInstance().setListener(this);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = mCustomView.findViewById(R.id.title_communityHeading);
        delete_btn = mCustomView.findViewById(R.id.delete_btn);
        edit_btn = mCustomView.findViewById(R.id.edit_btn);
        edit_btn.setVisibility(View.GONE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        callbackManager = CallbackCollectionManager.getInstance();
        feedTypeLable = (TextView) findViewById(R.id.feed_type);
        tvFeedTitle = (TextView) findViewById(R.id.feed_title);
        comment_layout_job_fullView = (LinearLayout) findViewById(R.id.comment_action_layout);
        social_bar_job_fullview = (RelativeLayout) findViewById(R.id.socialBar_count);
        socialbar_layout_job_fullview = (LinearLayout) findViewById(R.id.social_bar_action);
        comments_count_job_fullview = (TextView) findViewById(R.id.comment_count);

        likes_count_job_fullview = (TextView) findViewById(R.id.like_count);
        view_count_job_fullView = (TextView) findViewById(R.id.view_count);
        tv_share_count = (TextView) findViewById(R.id.tv_share_count);

        doceree_ad_layout=findViewById(R.id.job_feed_doceree_ad_layout);

        like_layout_job_fullview = (ViewGroup) findViewById(R.id.like_action_layout);
        like_icon_job_fullview = (ImageView) findViewById(R.id.like_icon);
        like_texview_job_fullview = (TextView) findViewById(R.id.like_textview);
        share_layout_job_fullview = (ViewGroup) findViewById(R.id.share_layout_dashboard);
        tabLayout = findViewById(R.id.job_info_tab_layout);
        viewPager = findViewById(R.id.job_info_viewpager);
        bookmark_jobFullview_imageView = (ImageView) findViewById(R.id.bookmark_jobFullView_imageView);
        bookmark_jobFullview = (LinearLayout) findViewById(R.id.bookmark_jobFullview);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        emailId = realmManager.getDoc_EmailId(realm);
        doc_name = realmManager.getDoc_name(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        Bundle bundle = getIntent().getExtras();
        channelName = bundle.getString(RestUtils.CHANNEL_NAME, "");
        channel_id = bundle.getInt(RestUtils.CHANNEL_ID);
        channelObj = realmManager.getChannelFromDB("listofChannels", channel_id);
        getAdSlotsViewModel= ViewModelProviders.of(this).get(GetAdSlotsViewModel.class);
        if (channelObj != null) {
            feed_provider_type = channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
        }
        if (bundle.containsKey(RestUtils.TAG_IS_NETWORK_CHANNEL)) {
            isNetworkChannel = bundle.getBoolean(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
        } else {
            if (channelObj != null) {
                if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                    isNetworkChannel = true;
                } else {
                    isNetworkChannel = false;
                }
            }
        }

        position = bundle.getInt(RestUtils.TAG_POSITION);
        type = bundle.getString(RestUtils.TAG_TYPE);
        feedDataString = bundle.getString(RestUtils.TAG_FEED_OBJECT);
        if (isNetworkChannel) {
            mTitleTextView.setText("");
        } else {
            mTitleTextView.setText(channelName); // set Toolbar title
        }
        AppUtil.logFlurryEventWithDocIdAndEmailEvent("JOB_DETAILS_VIEW", basicInfo.getUserUUID() == null ? "" : basicInfo.getUserUUID(), emailId);
        try {
            completeFeedObj = new JSONObject(feedDataString);
            feedInfoObj = completeFeedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
            if (feedInfoObj == null) {
                return;
            }

            feedId = feedInfoObj.optInt(RestUtils.TAG_FEED_ID);
            feedTitle = feedInfoObj.optString(RestUtils.TAG_TITLE);
            feedTypeLable.setText(feedInfoObj.optString(RestUtils.TAG_DISPLAY));
            tvFeedTitle.setText(feedTitle);
            isBookmarked = feedInfoObj.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
            if (isBookmarked) {
                bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
            } else {
                bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
            }
            isDeletable = feedInfoObj.optBoolean(RestUtils.TAG_IS_DELETABLE);
            if (isDeletable) {
                delete_btn.setVisibility(View.VISIBLE);
            } else {
                delete_btn.setVisibility(View.GONE);
            }
            socialInteractionObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
            if (socialInteractionObj != null) {
                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, bookmark_jobFullview, tv_share_count);
                if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                    like_icon_job_fullview.setImageResource(R.drawable.ic_social_liked);
                    like_texview_job_fullview.setTextColor(Color.parseColor("#00A76D"));
                    like_layout_job_fullview.setTag(true);
                } else {
                    like_icon_job_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_job_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_job_fullview.setTag(false);
                }

            } else {
                like_icon_job_fullview.setImageResource(R.drawable.ic_social_like);
                like_texview_job_fullview.setTextColor(Color.parseColor("#DE231f20"));
                like_layout_job_fullview.setTag(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        display_status = feedInfoObj.optBoolean("display_status");
        tabNames.add("About Role");
        /*Checking the display_status flag to add/remove the organization tab in the screen*/
        if (display_status || (!feedInfoObj.isNull("about_us") && !feedInfoObj.optString("about_us").isEmpty())) {
            tabNames.add("Organization");
        }
        tabNames.add("Apply Now");


        tabPageAdpater = new JobInfoTabAdapter(this, feedDataString,
                login_doc_id, display_status, tabNames.size());
        viewPager.setAdapter(tabPageAdpater);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabNames.get(position));
            }
        }).attach();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        like_layout_job_fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppUtil.isConnectingToInternet(JobFeedCompleteView.this)) {
                    return;
                }
                if (likeAPICall != null && likeAPICall.getStatus() == AsyncTask.Status.RUNNING) {
                    return;
                }
                AppUtil.logUserEventWithParams("FullViewLikeFeed", basicInfo, feedId, channel_id, channelName, JobFeedCompleteView.this);
                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    Boolean isLiked = false;
                    JSONObject tempLikeObj = new JSONObject();
                    try {
                        tempLikeObj.put(RestUtils.TAG_DOC_ID, login_doc_id);
                        tempLikeObj.put(RestUtils.CHANNEL_ID, channel_id);
                        tempLikeObj.put(RestUtils.TAG_FEED_TYPE_ID, feedId);
                        if ((Boolean) v.getTag()) {
                            isLiked = false;
                            like_icon_job_fullview.setImageResource(R.drawable.ic_social_like);
                            like_texview_job_fullview.setTextColor(Color.parseColor("#DE231f20"));
                            like_layout_job_fullview.setTag(false);
                            if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) > 0) {
                                int likesCount = Integer.parseInt(socialInteractionObj.optString(RestUtils.TAG_LIKES_COUNT)) - 1;
                                socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                socialInteractionObj.put(RestUtils.TAG_IS_LIKE, false);
                                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, null, tv_share_count);
                            }
                        } else {
                            isLiked = true;
                            like_icon_job_fullview.setImageResource(R.drawable.ic_social_liked);
                            like_texview_job_fullview.setTextColor(Color.parseColor("#00A76D"));
                            like_layout_job_fullview.setTag(true);
                            if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) >= 0) {
                                int likesCount = Integer.parseInt(socialInteractionObj.optString(RestUtils.TAG_LIKES_COUNT)) + 1;
                                socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                socialInteractionObj.put(RestUtils.TAG_IS_LIKE, true);
                                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, null, tv_share_count);

                            }
                            AppUtil.loadBounceAnimation(JobFeedCompleteView.this, like_icon_job_fullview);
                        }
                        for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                            listener.updateUI(feedId, socialInteractionObj);
                        }
                        tempLikeObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    requestLikeService(isLiked);
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(JobFeedCompleteView.this, getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(JobFeedCompleteView.this, getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });
        comment_layout_job_fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.logUserEventWithParams("FullViewCommentFeedInitiation", basicInfo, feedId, channel_id, channelName, JobFeedCompleteView.this);
                Intent commentIntent = new Intent(JobFeedCompleteView.this, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedTitle);
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, true);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                startActivity(commentIntent);
            }
        });
        likes_count_job_fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent likeIntent = new Intent(JobFeedCompleteView.this, CommentsActivity.class);
                likeIntent.putExtra(RestUtils.TAG_POST_NAME, feedTitle);
                likeIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                likeIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                likeIntent.putExtra(RestUtils.NAVIGATATION, RestUtils.TAG_FROM_LIKES_COUNT);
                likeIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, (socialInteractionObj == null) ? 0 : socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT));
                likeIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                startActivity(likeIntent);
            }
        });
        comments_count_job_fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent commentIntent = new Intent(JobFeedCompleteView.this, CommentsActivity.class);
                commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedTitle);
                commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedId);
                commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                startActivity(commentIntent);
            }
        });
        share_layout_job_fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isConnectingToInternet(JobFeedCompleteView.this)) {
                    return;
                }
                try {
                    socialInteractionObj.put(RestUtils.TAG_SHARE_COUNT, socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT) + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, null, tv_share_count);
                for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                    listener.updateUI(feedId, socialInteractionObj);
                }
                AppUtil.logUserEventWithParams("FullViewShareFeedInitiation", basicInfo, feedId, channel_id, channelName, JobFeedCompleteView.this);
                Calendar calendar = Calendar.getInstance();
                long selectedTime = calendar.getTimeInMillis();
                JSONObject shareInfoObj = feedInfoObj.optJSONObject(RestUtils.TAG_SHARE_INFO);
                if (shareInfoObj != null) {
                    text_From_Server = shareInfoObj.optString(RestUtils.TAG_SERVER_TEXT);
                    url_From_server = shareInfoObj.optString(RestUtils.TAG_SERVER_URL);
                }
                if (feed_provider_type.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
                    String share_des = getString(R.string.share_des_text, realmManager.getDocSalutation(realm) + " " + doc_name, "post", " \"" + feedTitle + "\" ");
                    //String encryptedStringData=AppUtil.encryptFeedData(JobFeedCompleteView.this,feedId,doc_id,channel_id,selectedTime);
                    if (text_From_Server != null && !text_From_Server.isEmpty()) {
                        share_des = realmManager.getDocSalutation(realm) + " " + doc_name + " " + text_From_Server + " \"" + feedTitle + "\" ";
                    }
                    AppUtil.inviteToWhiteCoatsIntent(JobFeedCompleteView.this, share_des, "Share via", "");
                    final JSONObject shareFeedRequestObj = new JSONObject();
                    JSONArray feedDataArray = new JSONArray();
                    JSONObject innerObj = new JSONObject();
                    try {
                        shareFeedRequestObj.put(RestUtils.TAG_USER_ID, login_doc_id);
                        innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                        innerObj.put(RestUtils.TAG_FEED_ID, feedId);
                        innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                        feedDataArray.put(innerObj);
                        shareFeedRequestObj.put(RestUtils.TAG_SHARED_FEEDS, feedDataArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.requestForShareAFeed(JobFeedCompleteView.this, realmManager, shareFeedRequestObj.toString(), innerObj.toString());
                } else {
                    JSONObject encryptedStringData = AppUtil.encryptFeedData(JobFeedCompleteView.this, feedId, login_doc_id, channel_id, selectedTime, realmManager.getDocSalutation(realm) + " " + doc_name, "\"" + feedTitle + "\" ", text_From_Server, url_From_server);
                    if (encryptedStringData != null) {
                        final JSONObject shareFeedRequestObj = new JSONObject();
                        JSONObject innerObj = new JSONObject();
                        try {
                            shareFeedRequestObj.put(RestUtils.TAG_USER_ID, login_doc_id);
                            innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                            innerObj.put(RestUtils.TAG_FEED_ID, feedId);
                            innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                            shareFeedRequestObj.put(RestUtils.TAG_SHARE_URL, encryptedStringData.optString(RestUtils.TAG_ORIGINAL_URL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.requestForShortURL(JobFeedCompleteView.this, realmManager, shareFeedRequestObj.toString(), innerObj.toString(), encryptedStringData);
                    }
                }

            }
        });
        bookmark_jobFullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.getUserVerifiedStatus() == 3) {
                    if (isConnectingToInternet()) {
                        AppUtil.logUserEventWithParams("FullViewFeedBookmark", basicInfo, feedId, channel_id, channelName, JobFeedCompleteView.this);
                        if (isBookmarked) {
                            bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                        } else {
                            bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                            AppUtil.loadBounceAnimation(JobFeedCompleteView.this, bookmark_jobFullview_imageView);
                        }
                        try {
                            JSONObject requestObj = new JSONObject();
                            requestObj.put(RestUtils.TAG_DOC_ID, login_doc_id);
                            requestObj.put(RestUtils.TAG_FEED_ID, feedId);
                            requestObj.put(RestUtils.CHANNEL_ID, channel_id);
                            requestObj.put(RestUtils.TAG_IS_BOOKMARK, !isBookmarked); // on click set the opposite one.
                            requestBookmarkService(requestObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(JobFeedCompleteView.this, getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(JobFeedCompleteView.this, getString(R.string.mca_uploaded_but_not_verified));
                }

            }
        });

        delete_btn.setOnClickListener(v -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(JobFeedCompleteView.this);
            alertDialogBuilder.setMessage("Are you sure you want to delete this post?");
            alertDialogBuilder.setPositiveButton("Delete", (arg0, arg1) -> {
                JSONObject requestData = new JSONObject();
                try {
                    if (isConnectingToInternet()) {
                        requestData.put(RestUtils.CHANNEL_ID, channel_id);
                        requestData.put(RestUtils.TAG_POST_ID, feedId);
                        requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
                        showProgress();
                        new VolleySinglePartStringRequest(JobFeedCompleteView.this, Request.Method.POST, RestApiConstants.Community_Delete_Post, requestData.toString(), "DELETE_POST", new OnReceiveResponse() {
                            @Override
                            public void onSuccessResponse(String successResponse) {
                                hideProgress();
                                JSONObject responseObj = null;
                                try {
                                    responseObj = new JSONObject(successResponse);
                                    if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                        JSONObject postObj = new JSONObject();
                                        Toast.makeText(getApplicationContext(), "Post successfully deleted", Toast.LENGTH_SHORT).show();
                                        postObj.put(RestUtils.TAG_DOC_ID, login_doc_id);
                                        postObj.put(RestUtils.CHANNEL_ID, channel_id);
                                        postObj.put(RestUtils.TAG_POSITION, position);
                                        postObj.put(RestUtils.TAG_POST_ID, feedId);
                                        for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                            listener.notifyUIWithDeleteFeed(feedId, postObj);
                                        }
                                        finish();
                                    } else if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                        displayErrorScreen(successResponse);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onErrorResponse(String errorResponse) {
                                hideProgress();
                                displayErrorScreen(errorResponse);
                            }
                        }).sendSinglePartRequest();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        if (AppUtil.isConnectingToInternet(JobFeedCompleteView.this)) {
            viewCountService(login_doc_id, channel_id, feedId);
        }


        getAdSlotsViewModel.setRequestData(login_doc_id,channel_id,feedId,AppUtil.getRequestHeaders(this));
        getAdSlotsViewModel.getFeedAdSlots().observe(this, getAdSlotsApiResponse -> {
            if(getAdSlotsApiResponse==null){
                return;
            }
            if(getAdSlotsApiResponse.getError()==null && getAdSlotsApiResponse.getAdDefinitions().size()>0){
                AdsDefinition adDefinitionObj = getAdSlotsApiResponse.getAdDefinitions().get(0);
                try {
                    DocereeAdView docereeAdView = new DocereeAdView(this);
                    docereeAdView.setVisibility(View.GONE);
                    doceree_ad_layout.addView(docereeAdView);
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
                            AppUtil.logAdEvent(adDefinitionObj, "Feed_Ad_Clicked", login_doc_id, JobFeedCompleteView.this);
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
                                    doceree_ad_layout.setVisibility(View.GONE);
                                }
                            }, (adDefinitionObj.getAdSlotDuration())*1000L);
                            AppUtil.logAdEvent(adDefinitionObj, "Feed_Ad_loaded", login_doc_id, JobFeedCompleteView.this);
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
                doceree_ad_layout.addView(docereeAdView);
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
                        AppUtil.logAdEvent(adSlotInfo, "Job_Feed_Ad_loaded", realmManager.getDoc_id(realm), JobFeedCompleteView.this);
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
                                doceree_ad_layout.setVisibility(View.GONE);
                            }
                        }, (adSlotResults.get(0).getAd_slot_duration())*1000L);
                        AppUtil.logAdEvent(adSlotInfo, "Job_Feed_Ad_loaded", realmManager.getDoc_id(realm), JobFeedCompleteView.this);
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

    private void viewCountService(int doc_id, int channel_id, int feedTypeId) {
        JSONObject fullViewRequestJsonObject = new JSONObject();
        try {
            fullViewRequestJsonObject.put(RestUtils.TAG_DOC_ID, doc_id);
            fullViewRequestJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
            fullViewRequestJsonObject.put(RestUtils.FEED_ID, feedTypeId);
            //showProgress();
            new VolleySinglePartStringRequest(JobFeedCompleteView.this, Request.Method.POST, RestApiConstants.VIEW_FEED_REST, fullViewRequestJsonObject.toString(), "VIEW_FEED", new OnReceiveResponse() {
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
                                    Toast.makeText(JobFeedCompleteView.this, getString(R.string.label_bookmark_added), Toast.LENGTH_SHORT).show();
                                    bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                } else {
                                    Toast.makeText(JobFeedCompleteView.this, getString(R.string.label_bookmark_removed), Toast.LENGTH_SHORT).show();
                                    bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                                }
                                for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                    listener.onBookmark(dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED), dataObject.optInt(RestUtils.TAG_FEED_ID), true, dataObject.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                }
                            } else if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                String errorMsg = getResources().getString(R.string.somenthing_went_wrong);
                                if (jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)) {
                                    errorMsg = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                }
                                Toast.makeText(JobFeedCompleteView.this, errorMsg, Toast.LENGTH_SHORT).show();
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
                        bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                    } else {
                        bookmark_jobFullview_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                    }
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        try {
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                            if (!errorMessage.isEmpty()) {
                                Toast.makeText(JobFeedCompleteView.this, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
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

    private void requestLikeService(Boolean isLiked) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, feedId);
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put(RestUtils.TAG_TYPE, "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(JobFeedCompleteView.this)) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + feedId);
        }
        likeAPICall = new LikeActionAsync(JobFeedCompleteView.this, RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String sResponse) {
                if (AppConstants.likeActionList.contains(channel_id + "_" + feedId)) {
                    AppConstants.likeActionList.remove(channel_id + "_" + feedId);
                }
                if (sResponse != null) {
                    responseHandler(sResponse);
                }

            }
        });
        likeAPICall.execute(likeRequest.toString());
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
                JSONObject responseSocialInteractionObj = null;
                if (likeResponseObj != null) {
                    responseSocialInteractionObj = likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                }
                socialInteractionObj = responseSocialInteractionObj;
                if (responseSocialInteractionObj != null) {
                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, bookmark_jobFullview, tv_share_count);
                    if (responseSocialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                        like_icon_job_fullview.setImageResource(R.drawable.ic_social_liked);
                        like_texview_job_fullview.setTextColor(Color.parseColor("#00A76D"));
                        like_layout_job_fullview.setTag(true);
                    } else {
                        like_icon_job_fullview.setImageResource(R.drawable.ic_social_like);
                        like_texview_job_fullview.setTextColor(Color.parseColor("#DE231f20"));
                        like_layout_job_fullview.setTag(false);
                    }
                    for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                        listener.updateUI(feedId, responseSocialInteractionObj);
                    }
                } else {
                    like_icon_job_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_job_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_job_fullview.setTag(false);
                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_spam, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_report_to_spam:
                BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
                Bundle bundle = new Bundle();
                bundle.putInt("feedId", feedId);
                bundle.putInt("docId", login_doc_id);
                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheetReportSpam");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }}


    @Override
    public void onUiUpdateForComments(JSONObject mObj) {
        JSONObject socialInteractionObj = mObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
        if (socialInteractionObj != null) {
            AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_job_fullview, comments_count_job_fullview, view_count_job_fullView, social_bar_job_fullview, socialbar_layout_job_fullview, like_layout_job_fullview, comment_layout_job_fullView, share_layout_job_fullview, bookmark_jobFullview, tv_share_count);
            if (tabPageAdpater != null) {
                tabPageAdpater.updateLatestSocialInteractionData(socialInteractionObj);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
}

