package com.vam.whitecoats.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import com.doceree.androidadslibrary.ads.AdRequest;
import com.doceree.androidadslibrary.ads.DocereeAdListener;
import com.doceree.androidadslibrary.ads.DocereeAdView;
import com.google.android.material.tabs.TabLayout;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.brandkinesis.BrandKinesis;
import com.brandkinesis.activitymanager.BKActivityTypes;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CustomModel;
import com.vam.whitecoats.core.models.FeedCreator;
import com.vam.whitecoats.core.models.FeedInfo;
import com.vam.whitecoats.core.models.SurveyQuestion;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.models.AdsDefinition;
import com.vam.whitecoats.ui.adapters.FeedSummaryAdapter;
import com.vam.whitecoats.ui.adapters.SurveyOptionsAdapter;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.NetworkConnectionListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.NetworkConnectListenerManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.vam.whitecoats.viewmodel.GetAdSlotsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class FeedsSummary extends BaseActionBarActivity implements CustomModel.OnUiUpdateListener, NetworkConnectionListener, SurveyOptionsAdapter.OnOptionForgotListener {
    public static final String TAG = FeedsSummary.class.getSimpleName();
    ViewPager contentPager;
    TabLayout surveyTabLayout;
    ImageView like_icon_feeds_fullview;
    TextView mTitleTextView, like_texview_feeds_fullview, likes_count_feeds_fullview, comments_count_feeds_fullview;
    TextView view_count_feeds_fullView,tv_share_count;
    ViewGroup like_layout_feeds_fullview, share_layout_feeds_fullview;
    RelativeLayout socialbar_count_feeds_fullview;
    LinearLayout socialbar_layout_feeds_fullview, comment_layout_feeds_fullView;
    private ImageButton delete_btn, edit_btn;
    private int post_id, channel_id, position;
    private Realm realm;
    private RealmManager realmManager;
    private int login_doc_id = 0;
    private int cMsgType = 0;
    private String type = null;
    boolean isParallelDashboardCall = false;
    public static boolean isRecommendationsUpdated;
    public static HashMap<Integer, List> questionsMap;
    private JSONObject socialInteractionObj;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private String doc_name = "";


    FeedSummaryAdapter feedSummaryAdapter;
    private String feedType = "";
    private String displayTag = "";
    private String text_From_Server = "";
    private String url_From_server = "";
    FeedInfo feedInfo;
    FeedCreator feedCreator;
    private RealmBasicInfo basicInfo;
    private boolean isNetworkChannel;
    private boolean menuShow = false;
    private BrandKinesis bkInstance;
    //private String eventId;
    private String channelName;
    private String feedFullviewtag="Full View Tag";
    private CallbackCollectionManager callbackManager;
    private String feed_sub_type;
    private String feed_provider_type="";
    private JSONObject channelObj;
    private boolean customBackButton;
    private LikeActionAsync likeAPICall;
    private RelativeLayout doceree_ad_layout;

    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launchCreatePostActivityResults;
    private GetAdSlotsViewModel getAdSlotsViewModel;

    @Override
    public void onOptionForgot(int position) {
        contentPager.setCurrentItem(position, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds_summary);
        callbackManager=CallbackCollectionManager.getInstance();
        CustomModel.getInstance().setListener(this);
        like_icon_feeds_fullview = findViewById(R.id.like_icon);
        like_texview_feeds_fullview = findViewById(R.id.like_textview);
        like_layout_feeds_fullview = findViewById(R.id.like_action_layout);
        likes_count_feeds_fullview = findViewById(R.id.like_count);
        comments_count_feeds_fullview = findViewById(R.id.comment_count);
        view_count_feeds_fullView = findViewById(R.id.view_count);
        tv_share_count=findViewById(R.id.tv_share_count);
        socialbar_count_feeds_fullview = findViewById(R.id.socialBar_count);
        socialbar_layout_feeds_fullview = findViewById(R.id.social_bar_action);
        comment_layout_feeds_fullView = findViewById(R.id.comment_action_layout);
        share_layout_feeds_fullview = findViewById(R.id.share_layout_dashboard);
        contentPager = findViewById(R.id.content_pager);
        surveyTabLayout = findViewById(R.id.dots);
        doceree_ad_layout=findViewById(R.id.feed_doceree_ad_layout);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = mCustomView.findViewById(R.id.title_communityHeading);
        delete_btn = mCustomView.findViewById(R.id.delete_btn);
        edit_btn = mCustomView.findViewById(R.id.edit_btn);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        NetworkConnectListenerManager.registerNetworkListener(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        login_doc_id = realmManager.getDoc_id(realm);
        String emailId = realmManager.getDoc_EmailId(realm);
        doc_name = realmManager.getDoc_name(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        bkInstance = BrandKinesis.getBKInstance();
        BrandKinesis.getBKInstance().getActivity(this, BKActivityTypes.ACTIVITY_ANY, feedFullviewtag);
        Bundle bundle = getIntent().getExtras();
        channelName = bundle.getString(RestUtils.CHANNEL_NAME,"");
        channel_id = bundle.getInt(RestUtils.CHANNEL_ID);
        channelObj = realmManager.getChannelFromDB("listofChannels", channel_id);
        getAdSlotsViewModel= ViewModelProviders.of(this).get(GetAdSlotsViewModel.class);
        /*Refactoring the deprecated startActivityForResults*/
        //Start
       launchCreatePostActivityResults=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
               result -> {
                   int resultCode = result.getResultCode();
                   Intent data = result.getData();
                   if(resultCode==RESULT_OK){
                       if (data!=null && data.getExtras()!=null) {
                           String feedObj = data.getExtras().getString("POST_OBJ");
                           try {
                               onUpdateUI(new JSONObject(feedObj));
                               delete_btn.setVisibility(View.GONE);
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   }
               });
       //End
        if(channelObj!=null){
            feed_provider_type=channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE);
        }
        if(bundle.containsKey(RestUtils.TAG_IS_NETWORK_CHANNEL)) {
            isNetworkChannel = bundle.getBoolean(RestUtils.TAG_IS_NETWORK_CHANNEL, false);
        }else{
            if(channelObj!=null){
                if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                    isNetworkChannel = true;
                } else {
                    isNetworkChannel = false;
                }
            }
        }

        position = bundle.getInt(RestUtils.TAG_POSITION);
        type = bundle.getString(RestUtils.TAG_TYPE);
        isParallelDashboardCall = bundle.getBoolean(RestUtils.TAG_IS_PARALLEL_CALL, false);
        if (bundle.getString(RestUtils.TAG_C_MSG_TYPE) != null) {
            cMsgType = Integer.parseInt(bundle.getString(RestUtils.TAG_C_MSG_TYPE));
        }
        if(isNetworkChannel){
            mTitleTextView.setText("");
        }else {
            mTitleTextView.setText(channelName); // set Toolbar title
        }
        AppUtil.logFlurryEventWithDocIdAndEmailEvent("FEEDS_SUMMARY", basicInfo.getUserUUID()==null?"":basicInfo.getUserUUID(), emailId);
        //FlurryAgent.logEvent("Community fullview hit :" + login_doc_id);
        try {

            JSONObject feedJsonObj = new JSONObject(bundle.getString(RestUtils.TAG_FEED_OBJECT));
            onUpdateUI(feedJsonObj);
            if (AppUtil.isConnectingToInternet(FeedsSummary.this)) {
                fullViewService(login_doc_id, channel_id, post_id);
                feedSummaryAdapter.recommendationsService(login_doc_id, channel_id, post_id, feedType, displayTag);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        getAdSlotsViewModel.setRequestData(login_doc_id,channel_id,post_id,AppUtil.getRequestHeaders(this));
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
                            AppUtil.logAdEvent(adDefinitionObj, "Feed_Ad_loaded", realmManager.getDoc_id(realm), FeedsSummary.this);
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
                            AppUtil.logAdEvent(adDefinitionObj, "Article_Ad_loaded", realmManager.getDoc_id(realm), FeedsSummary.this);
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
        }else if(feedType.equalsIgnoreCase(RestUtils.TAG_CASE)||feedType.equalsIgnoreCase("Post")){
            ad_slot_type = SlotLocationType.USER_GENERATED_FEED_SLOT.getType();
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
                        AppUtil.logAdEvent(adSlotInfo, "Feed_Ad_loaded", realmManager.getDoc_id(realm), FeedsSummary.this);
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
                        AppUtil.logAdEvent(adSlotInfo, "Article_Ad_loaded", realmManager.getDoc_id(realm), FeedsSummary.this);
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


    private void onUpdateUI(JSONObject feedJsonObj) {
        try {
            Bundle bundle = getIntent().getExtras();
            if(feedInfo==null){
                feedInfo=new FeedInfo();
            }
            if (feedJsonObj.has(RestUtils.TAG_FEED_INFO)) {
                AppUtil.setUpdatedFeedInfo(feedJsonObj.optJSONObject(RestUtils.TAG_FEED_INFO),feedInfo);
            } else {
                AppUtil.setUpdatedFeedInfo(feedJsonObj,feedInfo);
            }

            if (feedJsonObj.has(RestUtils.CHANNEL_ID)) {
                feedInfo.setChannelID(feedJsonObj.optInt(RestUtils.CHANNEL_ID));
            }
            if (feedJsonObj.has(RestUtils.TAG_POST_CREATOR))
                feedCreator = AppUtil.getFeedCreator(feedJsonObj.optJSONObject(RestUtils.TAG_POST_CREATOR));
            post_id = feedInfo.getFeedID();
            feedType = feedInfo.getFeedType();
            displayTag = feedInfo.getDisplayTag();
            upshotEventData(post_id,channel_id,0,"","","","", " ",false);
            // Set view pager adapter here
            if (feedSummaryAdapter == null) {
                feedSummaryAdapter = new FeedSummaryAdapter(this, feedInfo, feedCreator, bundle, new ViewPagerNavigationListener() {
                    @Override
                    public void moveToNextPage() {
                        //it doesn't matter if you're already in the last item
                        contentPager.setCurrentItem(contentPager.getCurrentItem() + 1);
                    }

                    @Override
                    public void moveToPreviousPage() {
                        contentPager.setCurrentItem(contentPager.getCurrentItem() - 1);
                    }
                });
                contentPager.setAdapter(feedSummaryAdapter);
            } else {
                feedSummaryAdapter.notifyDataSetChanged();
            }
            //feedSummaryAdapter.recommendationsService(login_doc_id, channel_id, post_id, feedType, feedSubType);
            // Only for Type SURVEY we need to display the pager indicator
            if (feedInfo.getFeedType() != null && feedInfo.getFeedType().equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
                /**
                 * spacing between tabs
                 */
                int deviceScreen = (int) (AppUtil.getDeviceWidth(this) / 2);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.width = deviceScreen;
                layoutParams.gravity = Gravity.CENTER;
                surveyTabLayout.setLayoutParams(layoutParams);
                // create question map of the same size of questions
                int questionsLength = feedInfo.getSurveyData().getQuestions().size();
                if (questionsLength > 5) {
                    surveyTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                } else {
                    surveyTabLayout.setTabMode(TabLayout.MODE_FIXED);
                }
                questionsMap = new LinkedHashMap<>();
                for (int index = 0; index < questionsLength; index++) {
                    SurveyQuestion question = feedInfo.getSurveyData().getQuestions().get(index);
                    questionsMap.put(question.getQuestionId(), new ArrayList());
                }
                if (feedInfo.getSurveyData() != null && feedInfo.getSurveyData().getQuestions().size() > 1) { // if survey data not null
                    surveyTabLayout.setVisibility(View.VISIBLE);
                    surveyTabLayout.setupWithViewPager(contentPager, true);
                    int tabCount = surveyTabLayout.getTabCount();
                    for (int i = 0; i < tabCount; i++) {
                        surveyTabLayout.getTabAt(i).setText(i + 1 + "");
                    }
                    surveyTabLayout.setTabTextColors(R.color.black, R.color.white);

                } else {
                    surveyTabLayout.setVisibility(View.GONE);
                }
            } else {
                surveyTabLayout.setVisibility(View.GONE);
            }
            if (feedInfo.isDeletable()) {
                delete_btn.setVisibility(View.VISIBLE);
            } else if (feedInfo.isEditable()) {
                edit_btn.setVisibility(View.VISIBLE);
            }



            socialInteractionObj = feedInfo.getSocialInteraction();
            if (socialInteractionObj != null) {
                AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null,tv_share_count);
                if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                    like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_liked);
                    like_texview_feeds_fullview.setTextColor(Color.parseColor("#00A76D"));
                    like_layout_feeds_fullview.setTag(true);
                } else {
                    like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_feeds_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_feeds_fullview.setTag(false);
                }
            } else {
                like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_like);
                like_texview_feeds_fullview.setTextColor(Color.parseColor("#DE231f20"));
                like_layout_feeds_fullview.setTag(false);
            }


            like_layout_feeds_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!AppUtil.isConnectingToInternet(FeedsSummary.this)) {
                        return;
                    }
                    if(likeAPICall!=null && likeAPICall.getStatus()== AsyncTask.Status.RUNNING){
                        return;
                    }
                    AppUtil.logUserEventWithParams("FullViewLikeFeed", basicInfo, post_id, channel_id,channelName,FeedsSummary.this);
                    if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                        Boolean isLiked = false;
                        JSONObject tempLikeObj = new JSONObject();
                        try {
                            tempLikeObj.put(RestUtils.TAG_DOC_ID, login_doc_id);
                            tempLikeObj.put(RestUtils.CHANNEL_ID, channel_id);
                            tempLikeObj.put(RestUtils.TAG_FEED_TYPE_ID, post_id);
                            if ((Boolean) v.getTag()) {
                                isLiked = false;
                                like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_like);
                                like_texview_feeds_fullview.setTextColor(Color.parseColor("#DE231f20"));
                                like_layout_feeds_fullview.setTag(false);
                                if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) > 0) {
                                    int likesCount = Integer.parseInt(socialInteractionObj.optString(RestUtils.TAG_LIKES_COUNT)) - 1;
                                    socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                    socialInteractionObj.put(RestUtils.TAG_IS_LIKE, false);
                                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null,tv_share_count);
                                }
                            } else {
                                isLiked = true;
                                like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_liked);
                                like_texview_feeds_fullview.setTextColor(Color.parseColor("#00A76D"));
                                like_layout_feeds_fullview.setTag(true);
                                if (socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT) >= 0) {
                                    int likesCount = Integer.parseInt(socialInteractionObj.optString(RestUtils.TAG_LIKES_COUNT)) + 1;
                                    socialInteractionObj.put(RestUtils.TAG_LIKES_COUNT, likesCount);
                                    socialInteractionObj.put(RestUtils.TAG_IS_LIKE, true);
                                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null,tv_share_count);
                                }
                                AppUtil.loadBounceAnimation(FeedsSummary.this, like_icon_feeds_fullview);
                            }
                            for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                listener.updateUI(post_id, socialInteractionObj);
                            }
                            tempLikeObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        requestLikeService(isLiked);
                    } else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(FeedsSummary.this, getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(FeedsSummary.this, getString(R.string.mca_uploaded_but_not_verified));
                    }
                }
            });
            comment_layout_feeds_fullView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtil.logUserEventWithParams("FullViewCommentFeedInitiation", basicInfo, post_id, channel_id,channelName,FeedsSummary.this);
                    Intent commentIntent = new Intent(FeedsSummary.this, CommentsActivity.class);
                    commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfo.getTitle());
                    commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, true);
                    commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, post_id);
                    commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                    commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(commentIntent);
                }
            });
            likes_count_feeds_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent likeIntent = new Intent(FeedsSummary.this, CommentsActivity.class);
                    likeIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfo.getTitle());
                    likeIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                    likeIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, post_id);
                    likeIntent.putExtra(RestUtils.NAVIGATATION, RestUtils.TAG_FROM_LIKES_COUNT);
                    likeIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, (socialInteractionObj == null) ? 0 : socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT));
                    likeIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(likeIntent);
                }
            });
            comments_count_feeds_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent commentIntent = new Intent(FeedsSummary.this, CommentsActivity.class);
                    commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfo.getTitle());
                    commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                    commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, post_id);
                    commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                    commentIntent.putExtra(RestUtils.CHANNEL_ID, channel_id);
                    startActivity(commentIntent);
                }
            });
            share_layout_feeds_fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!AppUtil.isConnectingToInternet(FeedsSummary.this)) {
                        return;
                    }
                    try {
                        socialInteractionObj.put(RestUtils.TAG_SHARE_COUNT, socialInteractionObj.optInt(RestUtils.TAG_SHARE_COUNT)+1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null,tv_share_count);
                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                        listener.updateUI(post_id, socialInteractionObj);
                    }
                    AppUtil.logUserEventWithParams("FullViewShareFeedInitiation", basicInfo, post_id, channel_id,channelName,FeedsSummary.this);
                    Calendar calendar = Calendar.getInstance();
                    long selectedTime = calendar.getTimeInMillis();
                    JSONObject shareInfoObj = feedInfo.getShareInfo();
                    if (shareInfoObj != null) {
                        text_From_Server = shareInfoObj.optString(RestUtils.TAG_SERVER_TEXT);
                        url_From_server = shareInfoObj.optString(RestUtils.TAG_SERVER_URL);
                    }
                    if(feed_provider_type.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)){
                        String share_des = getString(R.string.share_des_text, realmManager.getDocSalutation(realm) + " " + doc_name, "post", " \"" + feedInfo.getTitle() + "\" ");
                        //String encryptedStringData=AppUtil.encryptFeedData(FeedsSummary.this,post_id,doc_id,channel_id,selectedTime);
                        if (text_From_Server != null && !text_From_Server.isEmpty()) {
                            share_des = realmManager.getDocSalutation(realm) + " " + doc_name + " " + text_From_Server + " \"" + feedInfo.getTitle() + "\" ";
                        }
                        AppUtil.inviteToWhiteCoatsIntent(FeedsSummary.this, share_des, "Share via", "");
                        final JSONObject shareFeedRequestObj = new JSONObject();
                        JSONArray feedDataArray = new JSONArray();
                        JSONObject innerObj = new JSONObject();
                        try {
                            shareFeedRequestObj.put(RestUtils.TAG_USER_ID, login_doc_id);
                            innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                            innerObj.put(RestUtils.TAG_FEED_ID, post_id);
                            innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                            feedDataArray.put(innerObj);
                            shareFeedRequestObj.put(RestUtils.TAG_SHARED_FEEDS, feedDataArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AppUtil.requestForShareAFeed(FeedsSummary.this, realmManager, shareFeedRequestObj.toString(), innerObj.toString());
                    }else{
                        JSONObject encryptedStringData = AppUtil.encryptFeedData(FeedsSummary.this, post_id, login_doc_id, channel_id, selectedTime, realmManager.getDocSalutation(realm) + " " + doc_name, "\"" + feedInfo.getTitle() + "\" ", text_From_Server, url_From_server);
                        if (encryptedStringData != null) {
                            final JSONObject shareFeedRequestObj = new JSONObject();
                            JSONObject innerObj = new JSONObject();
                            try {
                                shareFeedRequestObj.put(RestUtils.TAG_USER_ID, login_doc_id);
                                innerObj.put(RestUtils.CHANNEL_ID, channel_id);
                                innerObj.put(RestUtils.TAG_FEED_ID, post_id);
                                innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                                shareFeedRequestObj.put(RestUtils.TAG_SHARE_URL, encryptedStringData.optString(RestUtils.TAG_ORIGINAL_URL));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            AppUtil.requestForShortURL(FeedsSummary.this, realmManager, shareFeedRequestObj.toString(), innerObj.toString(), encryptedStringData);
                        }
                    }

                }
            });
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FeedsSummary.this);
                    alertDialogBuilder.setMessage("Are you sure you want to delete this post?");

                    alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            JSONObject requestData = new JSONObject();
                            try {
                                if (isConnectingToInternet()) {
                                    requestData.put(RestUtils.CHANNEL_ID, channel_id);
                                    requestData.put(RestUtils.TAG_POST_ID, post_id);
                                    requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
                                    showProgress();
                                    new VolleySinglePartStringRequest(FeedsSummary.this, Request.Method.POST, RestApiConstants.Community_Delete_Post, requestData.toString(), "DELETE_POST", new OnReceiveResponse() {
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
                                                    postObj.put(RestUtils.TAG_POST_ID, post_id);
                                                    for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                                        listener.notifyUIWithDeleteFeed(post_id, postObj);
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
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
            editor = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            if (cMsgType == 0 && type != null) {
                if (type.equalsIgnoreCase(RestUtils.TAG_TYPE_LIKE)) {
                    likes_count_feeds_fullview.performClick();
                } else if (type.equalsIgnoreCase(RestUtils.TAG_TYPE_COMMENT)) {
                    comments_count_feeds_fullview.performClick();
                }
            } else if (cMsgType == 8) {
                likes_count_feeds_fullview.performClick();
            } else if (cMsgType == 9) {
                comments_count_feeds_fullview.performClick();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_feedview, menu);
        if (feedInfo.isDeletable() && feedInfo.isEditable()) {
            menu.getItem(1).setVisible(true);
            menu.getItem(0).setVisible(true);
            menu.getItem(2).setVisible(true);
            delete_btn.setVisibility(View.GONE);
            edit_btn.setVisibility(View.GONE);
        }else{
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("FeedId",post_id);
                    jsonObject.put("ChannelID",channel_id);
                    AppUtil.logUserUpShotEvent("FeedFullViewBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
        }
        if (id == R.id.feed_edit) {
            String channelObj=null;
            Intent intent = new Intent(this, CreatePostActivity.class);
            JSONArray updatedChannelsList = realmManager.getChannelsListFromDB("listofChannels");
            for(int i=0;i<updatedChannelsList.length();i++){
                JSONObject currentChannelObj = updatedChannelsList.optJSONObject(i);
                if(currentChannelObj!=null && currentChannelObj.optInt(RestUtils.CHANNEL_ID)==channel_id){
                    channelObj=currentChannelObj.toString();
                }
            }
            Bundle b = new Bundle();
            b.putParcelable("feedinfo_object", feedInfo);
            intent.putExtra(RestUtils.NAVIGATATION, "EditPost");
            intent.putExtra("attachmentsArray", feedInfo.getAttachmentJson().toString());
            intent.putExtra(RestUtils.KEY_SELECTED_CHANNEL,channelObj);
            intent.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,isNetworkChannel);
            intent.putExtras(b);
            launchCreatePostActivityResults.launch(intent);
        } else if (id == R.id.feed_delete) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FeedsSummary.this);
            alertDialogBuilder.setMessage("Are you sure you want to delete this post?");

            alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    JSONObject requestData = new JSONObject();
                    try {
                        if (isConnectingToInternet()) {
                            requestData.put(RestUtils.CHANNEL_ID, channel_id);
                            requestData.put(RestUtils.TAG_POST_ID, post_id);
                            requestData.put(RestUtils.TAG_DOC_ID, login_doc_id);
                            showProgress();
                            new VolleySinglePartStringRequest(FeedsSummary.this, Request.Method.POST, RestApiConstants.Community_Delete_Post, requestData.toString(), "DELETE_POST", new OnReceiveResponse() {
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
                                            postObj.put(RestUtils.TAG_POST_ID, post_id);
                                            for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                                                listener.notifyUIWithDeleteFeed(post_id, postObj);
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
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else if (id == R.id.action_report_to_spam) {
            BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
            Bundle bundle = new Bundle();
            bundle.putInt("feedId", feedInfo.getFeedID());
            bundle.putInt("docId", login_doc_id);
            bottomSheet.setArguments(bundle);
            bottomSheet.show(getSupportFragmentManager(),
                    "ModalBottomSheetReportSpam");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setCurrentActivity();
        checkNetworkConnectivity();
        AppUtil.logScreenEvent("FeedFullViewTimeSpent");

    }

    @Override
    protected void onPause() {
        super.onPause();
        feedSummaryAdapter.pauseAudio();
    }

    private void fullViewService(int docID, int channel_id, int post_id) {
        JSONObject fullViewRequestJsonObject = new JSONObject();
        try {
            fullViewRequestJsonObject.put(RestUtils.TAG_DOC_ID, docID);
            fullViewRequestJsonObject.put(RestUtils.CHANNEL_ID, channel_id);
            fullViewRequestJsonObject.put(RestUtils.FEED_ID, post_id);
            new VolleySinglePartStringRequest(FeedsSummary.this, Request.Method.POST, RestApiConstants.VIEW_FEED_REST, fullViewRequestJsonObject.toString(), "VIEW_FEED", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if (successResponse != null) {
                        responseHandler(successResponse);
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    //displayErrorScreen(errorResponse);
                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 0) {
            MaterialShowcaseView.resetSingleUse(FeedsSummary.this, "Sequence_FeedSummary");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "Sequence_FeedSummary");
            sequence.setConfig(config);
            if (!editor.getBoolean("ContentFullview", false)) {
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(this)
                                .setTarget(socialbar_layout_feeds_fullview)
                                .setDismissText("Got it")
                                .setDismissTextColor(Color.parseColor("#00a76d"))
                                .setMaskColour(Color.parseColor("#CC231F20"))
                                .setContentText(R.string.tap_to_coach_mark_share_your_opinion).setListener(new IShowcaseListener() {
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

    private void requestLikeService(Boolean isLiked) {
        JSONObject likeRequest = new JSONObject();
        try {
            likeRequest.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
            likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
            likeRequest.put(RestUtils.FEED_TYPE_ID, feedInfo.getFeedID());
            JSONObject socialInteractionObj = new JSONObject();
            socialInteractionObj.put(RestUtils.TAG_TYPE, "Like");
            socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
            likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!AppUtil.isConnectingToInternet(FeedsSummary.this)) {
            return;
        } else {
            AppConstants.likeActionList.add(channel_id + "_" + feedInfo.getFeedID());
        }
        likeAPICall=new LikeActionAsync(FeedsSummary.this, RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String sResponse) {
                if (AppConstants.likeActionList.contains(channel_id + "_" + feedInfo.getFeedID())) {
                    AppConstants.likeActionList.remove(channel_id + "_" + feedInfo.getFeedID());
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
            //displayErrorScreen(sResponse);
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(sResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonObject != null && jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                if (!jsonObject.has(RestUtils.TAG_DATA)) {
                    return;
                }
                JSONObject likeResponseObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                JSONObject responseSocialInteractionObj = likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                socialInteractionObj = responseSocialInteractionObj;
                if (responseSocialInteractionObj != null) {
                    AppUtil.toggleSocialBarViewCount(responseSocialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null, tv_share_count);
                    if (responseSocialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                        like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_liked);
                        like_texview_feeds_fullview.setTextColor(Color.parseColor("#00A76D"));
                        like_layout_feeds_fullview.setTag(true);
                    } else {
                        like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_like);
                        like_texview_feeds_fullview.setTextColor(Color.parseColor("#DE231f20"));
                        like_layout_feeds_fullview.setTag(false);
                    }
                    for (UiUpdateListener listener : callbackManager.getRegisterListeners()) {
                        listener.updateUI(post_id, responseSocialInteractionObj);
                    }
                } else {
                    like_icon_feeds_fullview.setImageResource(R.drawable.ic_social_like);
                    like_texview_feeds_fullview.setTextColor(Color.parseColor("#DE231f20"));
                    like_layout_feeds_fullview.setTag(false);
                }
            }
        }
    }

    @Override
    public void onUiUpdateForComments(JSONObject mObj) {
        JSONObject socialInteractionObj = mObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
        if (socialInteractionObj != null) {
            AppUtil.toggleSocialBarViewCount(socialInteractionObj, likes_count_feeds_fullview, comments_count_feeds_fullview, view_count_feeds_fullView, socialbar_count_feeds_fullview, socialbar_layout_feeds_fullview, like_layout_feeds_fullview, comment_layout_feeds_fullView, share_layout_feeds_fullview, null,tv_share_count);
            //call adapter method
            feedSummaryAdapter.updateUserComments(socialInteractionObj);
        }

    }

    @Override
    public void onNetworkReconnection() {
        if (!isRecommendationsUpdated && login_doc_id != 0) {
            feedSummaryAdapter.recommendationsService(login_doc_id, channel_id, post_id, feedType, displayTag);
        }
    }

    @Override
    public void onNetworkDisconnect() {

    }
    //endregion


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        if (!realm.isClosed())
            realm.close();
        isRecommendationsUpdated = false;
        /*if (imageViewAdapter != null && imageViewAdapter.getCurrentViewUtility() != null && imageViewAdapter.getCurrentViewUtility().getmPlayer() != null) {
            imageViewAdapter.getCurrentViewUtility().getmPlayer().stop();
        }
        */
        feedSummaryAdapter.stopAudio();
        NetworkConnectListenerManager.removeNetworkListener(this);
        CustomModel.getInstance().setListener(null);
    }

    @Override
    public void onBackPressed() {
        if (isTaskRoot()) {
            if(!customBackButton){
                customBackButton=false;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("FeedId",post_id);
                    jsonObject.put("ChannelID",channel_id);
                    AppUtil.logUserUpShotEvent("FeedFullViewDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra(RestUtils.TAG_IS_PARALLEL_CALL, isParallelDashboardCall);
            startActivity(intent);
        }
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("FeedId",post_id);
                jsonObject.put("ChannelID",channel_id);
                AppUtil.logUserUpShotEvent("FeedFullViewDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ActivityCompat.finishAfterTransition(this);
    }


    public interface ViewPagerNavigationListener {
        void moveToNextPage();

        void moveToPreviousPage();
    }

    public interface AudioFunctionListener {
        public void playAudio();

        public void pauseAudio();

        public void stopAudio();
    }

}

