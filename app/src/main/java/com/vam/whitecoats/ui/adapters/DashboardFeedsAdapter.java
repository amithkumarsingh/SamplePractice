package com.vam.whitecoats.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.Request;
import com.doceree.androidadslibrary.ads.AdRequest;
import com.doceree.androidadslibrary.ads.DocereeAdListener;
import com.doceree.androidadslibrary.ads.DocereeAdView;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.FeedSurvey;
import com.vam.whitecoats.core.models.HorizontalListDataObj;
import com.vam.whitecoats.core.models.SurveyOption;
import com.vam.whitecoats.core.realm.RealmAdSlotInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmFeedInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.CategoryLoadingActivity;
import com.vam.whitecoats.ui.activities.CommentsActivity;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.MyBookmarksActivity;
import com.vam.whitecoats.ui.activities.SuggestedListActivity;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.activities.VisitOtherProfile;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.fragments.CommunityFeedsFragments;
import com.vam.whitecoats.ui.fragments.CommunitySpotlightFeedsFragment;
import com.vam.whitecoats.ui.fragments.ContentChannelsFragment;
import com.vam.whitecoats.ui.fragments.ContentFeedsFragment;
import com.vam.whitecoats.ui.fragments.DashboardUpdatesFragment;
import com.vam.whitecoats.ui.fragments.KnowledgeContentFeedsFragment;
import com.vam.whitecoats.ui.fragments.KnowledgeMedicalEventsFeedsFragment;
import com.vam.whitecoats.ui.fragments.ProfessionalFeedsAndOpportuniesFragments;
import com.vam.whitecoats.ui.fragments.ProfessionalJobsFragment;
import com.vam.whitecoats.ui.interfaces.CategoryListLoadmore;
import com.vam.whitecoats.ui.interfaces.OnCategoryClickListener;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnScrollTopListener;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.DateUtils;
import com.vam.whitecoats.utils.LinearLayoutManagerWithSmoothScroller;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by venuv on 6/1/2017.
 */

public class DashboardFeedsAdapter extends RecyclerView.Adapter {
    public static final String TAG = DashboardFeedsAdapter.class.getSimpleName();

    private final Context mContext;
    private boolean fromDbDData;
    private ArrayList<Category> categoryList = new ArrayList<>();
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int selectedTabPosition = 0;
    private Realm realm;
    private BookMarkListener bookMarkStatusListener;
    private OnFeedItemClickListener feedItemClickListener;
    private String docName;
    private int docId = 0;
    private RealmManager realmManager;
    private OnSocialInteractionListener socialInteractionCallback;
    private SparseArray<JSONObject> channelsList = null;
    private RealmList<RealmFeedInfo> mFeedsList = new RealmList<>();
    private List<JSONObject> feedData = new ArrayList<JSONObject>();
    private DataObjectHolder mViewHolder;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int pastVisiblesItems, lastVisibleItem, totalItemCount, visibleItemCount;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean loading;
    private OnScrollTopListener onScrollTopListener;
    private String activityName;
    private String text_From_Server = "";
    private String url_From_server = "";
    private int lastPosition = -1;
    private final static int FADE_DURATION = 1000;
    private BookMarkListener bookmarkListener;
    private boolean bookMarkStatus;
    private CustomLinearLayoutManager mLayoutManager;
    /*
     * For bookmarks
     */
    private OnFeedProfileClickListener onFeedItemClickListener;
    private static final int TYPE_CATEGORY_LIST = 2;
    private static final int TYPE_HORIZONTAL_LIST = 3;
    private static final int TYPE_DOCEREE_AD = 4;
    // private ArrayList<JSONObject> trendingCategoriesList = new ArrayList<>();
    private RecyclerView categoriesListView;
    private HashMap<String, Object> data;
    private JSONObject eventRequestObj;
    private RealmBasicInfo basicInfo;
    private ArrayList<Integer> horizontalFeedsDataList = new ArrayList<>();
    private boolean isSeeMoreVisibility;
    private OnLoadMoreListener onLoadMoreHorizontalItemsListener;
    private boolean isLoadingHorizontalItems = false;
    private int lastFeedId = 0;
    private int horizontalItemPageIndex = 0;
    private View mLastSnappedView;

    //private HashMap<Integer,ArrayList<Integer>> relatedFeedsDataList=new HashMap<>();
    SparseArray<HorizontalListDataObj> horizontalDataList = new SparseArray<>();
    private boolean is_pinned = false;
    private LikeActionAsync likeAPICALL;
    private RealmAdSlotInfo midPostAdSlotInfo;
    private RealmAdSlotInfo topAdSlotInfo;
    //private CategoriesAdapter categoriesAdapter;
    private String tabName = "";
    private String tabFeedEventName = "";
    private String tabFeedName = "";
    private LinearLayoutManager linearLayoutManager;
    private boolean isListExhausted = false;
    private int page_num = 0;
    private CategoryListLoadmore categoryListLoadMoreListener;
    private CategoriesAdapter categoriesAdapter;
    private boolean categoryLoading = false;

    public DashboardFeedsAdapter(Context context, RealmList<RealmFeedInfo> feedsList, int login_doc_id, RecyclerView recyclerView, SparseArray<JSONObject> mChannelsList /*ArrayList<JSONObject> trendingCategoriesJsonArray*/, String doc_name, OnSocialInteractionListener onSocialInteractionListener, BookMarkListener mBookMarkListener, OnFeedItemClickListener mFeedItemClickListener, int mTabPosition) {
        this.mContext = context;
        this.activityName = context.getClass().getName();
        this.mFeedsList = feedsList;
        this.channelsList = mChannelsList;
        this.realm = Realm.getDefaultInstance();
        this.realmManager = new RealmManager(context);
        this.basicInfo = realmManager.getRealmBasicInfo(realm);
        this.docId = login_doc_id;
        this.docName = doc_name;
        fromDbDData = true;
        // this.trendingCategoriesList = trendingCategoriesJsonArray;
        //this.categoryList=_categoryList;
        this.socialInteractionCallback = onSocialInteractionListener;
        this.bookMarkStatusListener = mBookMarkListener;
        this.feedItemClickListener = mFeedItemClickListener;
        this.selectedTabPosition = mTabPosition;
        recycledViewPool = new RecyclerView.RecycledViewPool();
        if (recyclerView.getLayoutManager() instanceof CustomLinearLayoutManager) {
            final CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView
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
                                if (pastVisiblesItems == 0) {
                                    if (onScrollTopListener != null) {
                                        onScrollTopListener.OnScrollTop();
                                    }
                                }
                                return;
                            }
                            if (!loading) {
                                if (lastVisibleItem != RecyclerView.NO_POSITION && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    if (onLoadMoreListener != null && AppUtil.isConnectingToInternet(mContext)) {
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
        data = new HashMap<>();
        eventRequestObj = new JSONObject();

    }

    public DashboardFeedsAdapter(Context context, List<JSONObject> feedData, int docID, RecyclerView recyclerView, SparseArray<JSONObject> mChannelsList, String doc_name, String tabName, OnSocialInteractionListener onSocialInteractionListener, OnFeedItemClickListener mFeedItemclickListener) {
        this.mContext = context;
        this.activityName = context.getClass().getName();
        this.feedData = feedData;
        this.channelsList = mChannelsList;
        this.realm = Realm.getDefaultInstance();
        this.realmManager = new RealmManager(context);
        this.docId = docID;
        this.docName = doc_name;
        this.tabName = tabName;
        this.socialInteractionCallback = onSocialInteractionListener;
        this.feedItemClickListener = mFeedItemclickListener;
        this.basicInfo = realmManager.getRealmBasicInfo(realm);
        fromDbDData = false;
        if (recyclerView.getLayoutManager() instanceof CustomLinearLayoutManager) {
            final CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView
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
                                if (pastVisiblesItems == 0) {
                                    if (onScrollTopListener != null) {
                                        onScrollTopListener.OnScrollTop();
                                    }
                                }
                                return;
                            }
                            if (!loading) {
                                if (lastVisibleItem != RecyclerView.NO_POSITION && lastVisibleItem == (totalItemCount - 1)) {
                                    if (onLoadMoreListener != null && AppUtil.isConnectingToInternet(mContext)) {
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

    public void setCategoriesListView(RecyclerView categoriesListView) {
        this.categoriesListView = categoriesListView;
    }

    public RecyclerView getCategoriesListView() {
        return categoriesListView;
    }

    public void setHorizontalListData(ArrayList<Integer> feedIdList) {
        this.horizontalFeedsDataList.addAll(feedIdList);

    }

    public void clearHorizontalListData() {
        this.horizontalFeedsDataList.clear();
    }

    public void addHorizontalListDataObj(int position, HorizontalListDataObj listDataObj) {
        this.horizontalDataList.put(position, listDataObj);
    }

    public void clearHorizontalListDataObjects() {
        this.horizontalDataList.clear();
    }

    public SparseArray<HorizontalListDataObj> getHorizontalObjList() {
        return this.horizontalDataList;
    }

    public void setLikeAPICallAsync(LikeActionAsync _likeAPICall) {
        likeAPICALL = _likeAPICall;
    }

    public void setMidPostAdInfo(RealmAdSlotInfo _midPostSlotInfo) {
        midPostAdSlotInfo = _midPostSlotInfo;
    }

    public void setTopSlotAdInfo(RealmAdSlotInfo _realmAdSlotInfo) {
        topAdSlotInfo = _realmAdSlotInfo;
    }

    public void setCategoriesListData(ArrayList<Category> mCategoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(mCategoryList);
        if (categoriesAdapter != null) {
            categoriesAdapter.notifyDataSetChanged();
        }
    }

    public void setCategoryListExhaustedStatus(boolean isListExhausted) {
        this.isListExhausted = isListExhausted;
    }

    public void setCategoriesListLoadingStatus(boolean loading) {
        this.categoryLoading = loading;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View mParentView, dashboard_latest_commented_layout;
        private final ImageView communityImgvw, communityImgvw1, communityImgvw2, like_icon_dashboard, attachment_icon, attachment_icon1, attachment_icon2, bookmark_dashboard_imageView, attachment_video_type, already_registered_icon, spam_report_dashboard_imageView;
        private final ViewGroup createdBy_layout, share_layout_dashboard, last_action_viewgroup;
        private final ImageView attachment_video_type1, attachment_video_type2;
        private final ViewGroup subscription_overLay;
        private final Button subscription_button, register_now, join_now;
        private final ProgressBar progressBar, simplePB;
        private final LinearLayout webinar_time_date_inner_lay;
        private final Button eoi_lay, view_recording_btn;
        private TextView postdesc, edited_text_view, webinar_desc, webinar_title, date_webinar, webinar_time, already_registered_text, webinar_text, webinar_status, ended_text, tv_date_time_label, tv_organizer_details;
        private RoundedImageView profileOrChannelLogo, docProfilePic, dashboard_commented_doc_image;
        private TextView profileOrChannelLabel, postedTime, docNameLable, remainingCountText,
                like_label_dashboard, like_count_dashboard, comment_count_dashboard,
                view_count_dashboard, postTitle, post_type,
                dashboard_commented_doc_name, dashboard_commented_text, last_action_name_text, tv_share_count;
        private RelativeLayout socialBar_count_dashboard, webinar_lay, webinar_time_date_lay;
        private LinearLayout comment_layout_dashboard, like_in_dashboard, social_bar_dashboard, surveyRootLayout, surveyOptionsLayout;
        private ViewGroup bookmark_dashboard;
        private ProgressBar subscriptionLoadingProgress;
        private ViewGroup spam_report_dashboard;


        public DataObjectHolder(View mItemView) {
            super(mItemView);
            mParentView = mItemView;
            mParentView.setOnClickListener(this);
            profileOrChannelLogo = (RoundedImageView) mParentView.findViewById(R.id.profile_or_channel_logo);
            profileOrChannelLabel = (TextView) mParentView.findViewById(R.id.channel_or_doc_name_label);
            postTitle = (TextView) mParentView.findViewById(R.id.post_tile_label);
            postdesc = (TextView) mParentView.findViewById(R.id.post_desc_label);
            postedTime = (TextView) mParentView.findViewById(R.id.post_made_time);
            post_type = (TextView) mParentView.findViewById(R.id.post_type);
            docProfilePic = (RoundedImageView) mParentView.findViewById(R.id.doc_profile_pic);
            docNameLable = (TextView) mParentView.findViewById(R.id.doc_name_label);
            communityImgvw = (ImageView) mParentView.findViewById(R.id.communityImage);
            communityImgvw1 = (ImageView) mParentView.findViewById(R.id.secondImageofCommunity);
            communityImgvw2 = (ImageView) mParentView.findViewById(R.id.thirdImageofCommunity);
            remainingCountText = (TextView) mParentView.findViewById(R.id.remaining_images_count);
            like_icon_dashboard = (ImageView) mParentView.findViewById(R.id.like_icon);
            like_label_dashboard = (TextView) mParentView.findViewById(R.id.like_textview);
            attachment_icon = (ImageView) mParentView.findViewById(R.id.attachment_icon);
            attachment_icon1 = (ImageView) mParentView.findViewById(R.id.attachment_icon1);
            attachment_icon2 = (ImageView) mParentView.findViewById(R.id.attachment_icon2);
            like_count_dashboard = (TextView) mParentView.findViewById(R.id.like_count);
            comment_count_dashboard = (TextView) mParentView.findViewById(R.id.comment_count);
            view_count_dashboard = (TextView) mParentView.findViewById(R.id.view_count);
            tv_share_count = (TextView) mParentView.findViewById(R.id.tv_share_count);
            socialBar_count_dashboard = (RelativeLayout) mParentView.findViewById(R.id.socialBar_count);
            comment_layout_dashboard = (LinearLayout) mParentView.findViewById(R.id.comment_action_layout);
            like_in_dashboard = (LinearLayout) mParentView.findViewById(R.id.like_action_layout);
            social_bar_dashboard = (LinearLayout) mParentView.findViewById(R.id.social_bar_action);
            surveyRootLayout = (LinearLayout) mParentView.findViewById(R.id.survey_root_layout);

            createdBy_layout = (ViewGroup) mParentView.findViewById(R.id.createdby_layout);
            share_layout_dashboard = (ViewGroup) mItemView.findViewById(R.id.share_layout_dashboard);
            //
            dashboard_latest_commented_layout = mParentView.findViewById(R.id.dashboard_comment_viewgroup);
            dashboard_commented_doc_image = (RoundedImageView) dashboard_latest_commented_layout.findViewById(R.id.commented_doc_pic);
            dashboard_commented_doc_name = (TextView) dashboard_latest_commented_layout.findViewById(R.id.commented_doc_name);
            dashboard_commented_text = (TextView) dashboard_latest_commented_layout.findViewById(R.id.latest_comment);

            last_action_viewgroup = (ViewGroup) mParentView.findViewById(R.id.last_action_viewgroup);
            last_action_name_text = (TextView) mParentView.findViewById(R.id.last_action_doc_name);

            bookmark_dashboard = (ViewGroup) mParentView.findViewById(R.id.bookmark_dashboard);
            bookmark_dashboard_imageView = (ImageView) mParentView.findViewById(R.id.bookmark_dashboard_imageView);
            spam_report_dashboard_imageView = (ImageView) mParentView.findViewById(R.id.spam_report_dashboard_imageView);
            spam_report_dashboard = (ViewGroup) mParentView.findViewById(R.id.spam_report_dashboard);
            attachment_video_type = (ImageView) itemView.findViewById(R.id.attachment_video_type);
            attachment_video_type1 = (ImageView) itemView.findViewById(R.id.attachment_video_type1);
            attachment_video_type2 = (ImageView) itemView.findViewById(R.id.attachment_video_type2);
            subscription_overLay = (ViewGroup) itemView.findViewById(R.id.subscription_overLay);
            subscription_button = (Button) itemView.findViewById(R.id.subscription_button);
            subscriptionLoadingProgress = (ProgressBar) itemView.findViewById(R.id.subscription_loading_progress);

            webinar_lay = (RelativeLayout) mParentView.findViewById(R.id.webinar_lay);
            webinar_time_date_lay = (RelativeLayout) mParentView.findViewById(R.id.webinar_time_date_lay);

            webinar_title = (TextView) mParentView.findViewById(R.id.webinar_title);
            webinar_desc = (TextView) mParentView.findViewById(R.id.webinar_desc);
            date_webinar = (TextView) mParentView.findViewById(R.id.date_webinar);
            webinar_time = (TextView) mParentView.findViewById(R.id.webinar_time);
            register_now = (Button) mParentView.findViewById(R.id.register_now);
            join_now = (Button) mParentView.findViewById(R.id.join_now);
            simplePB = (ProgressBar) mParentView.findViewById(R.id.simplePB);
            already_registered_icon = (ImageView) mParentView.findViewById(R.id.already_registered_icon);
            already_registered_text = (TextView) mParentView.findViewById(R.id.already_registered_text);

            webinar_time_date_inner_lay = (LinearLayout) mParentView.findViewById(R.id.webinar_time_date_inner_lay);

            edited_text_view = (TextView) itemView.findViewById(R.id.edited);
            webinar_text = (TextView) mParentView.findViewById(R.id.webinar_text);
            webinar_status = (TextView) mParentView.findViewById(R.id.webinar_status);
            progressBar = (ProgressBar) mParentView.findViewById(R.id.progressBar);
            ended_text = (TextView) mParentView.findViewById(R.id.ended_text);
            eoi_lay = (Button) mParentView.findViewById(R.id.if_interested_button);
            tv_date_time_label = (TextView) mParentView.findViewById(R.id.tv_webinar_date_time_label);
            tv_organizer_details = (TextView) mParentView.findViewById(R.id.tv_organizer);
            view_recording_btn = mParentView.findViewById(R.id.view_recording_btn);
            //postdesc.setSingleLine(true);

        }

        @Override
        public void onClick(View v) {
            try {
                /*
                 * Differentiate between bookmarks and Dashboard
                 */
                String tabNames = tabName;
                JSONObject feedJson = null;
                JSONObject channelObj = null;
                RealmFeedInfo currentObj = null;
                int channelID = 0;
                boolean isNetworkChannel;
                if (!fromDbDData) {
                    feedJson = feedData.get(getAdapterPosition());
                    channelObj = channelsList.get(feedJson.optInt(RestUtils.CHANNEL_ID));
                    channelID = feedJson.optInt(RestUtils.CHANNEL_ID);
                } else {
                    currentObj = mFeedsList.get(getAdapterPosition());
                    feedJson = new JSONObject(currentObj.getFeedsJson());
                    channelObj = channelsList.get(currentObj.getChannelId());
                    channelID = currentObj.getChannelId();
                }
                JSONObject feedInfoObj = feedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                if (channelObj == null || !channelObj.optBoolean(RestUtils.TAG_SUBSCRIBED, true)) {
                    return;
                }
                is_pinned = feedJson.optBoolean("is_pinned");
                if (is_pinned) {
//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
//                        jsonObject.put("ChannelID", channelID);
//                        jsonObject.put("FeedID", feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
//                        AppUtil.logUserUpShotEvent("PinnedFeedFullView", AppUtil.convertJsonToHashMap(jsonObject));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("FeedID", feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "PinnedFeedFullView", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                        //AppUtil.logUserUpShotEvent("PinnedFeedFullView",AppUtil.convertJsonToHashMap(jsonObject));
                        jsonObject.put("Tab", tabFeedName);
                        AppUtil.logUserUpShotEvent(tabFeedEventName, AppUtil.convertJsonToHashMap(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (tabName != null && !tabName.isEmpty()) {
                    if (tabName.equalsIgnoreCase("fromKnowledgeFeeds")) {
                        tabFeedEventName = "KnowledgeFeedFullView";
                        tabFeedName = "Feeds";
                    } else if (tabName.equalsIgnoreCase("fromKnowledgeMedicalEvents")) {
                        tabFeedEventName = "KnowledgeFeedFullView";
                        tabFeedName = "Medical Events";
                    } else if (tabName.equalsIgnoreCase("fromCommunitySpotlight")) {
                        tabFeedEventName = "CommunityFeedFullView";
                        tabFeedName = "Spotlights";
                    } else if (tabName.equalsIgnoreCase("fromCommunityFeeds")) {
                        tabFeedEventName = "CommunityFeedFullView";
                        tabFeedName = "Feeds";
                    } else if (tabName.equalsIgnoreCase("fromProfessionalFeeds")) {
                        tabFeedEventName = "ProfessionalFeedFullView";
                        tabFeedName = "Feeds";
                    } else if (tabName.equalsIgnoreCase("fromProfessionalSkilling")) {
                        tabFeedEventName = "ProfessionalFeedFullView";
                        tabFeedName = "Skilling";
                    } else if (tabName.equalsIgnoreCase("fromProfessionalOpportunities")) {
                        tabFeedEventName = "ProfessionalFeedFullView";
                        tabFeedName = "Opportunities";
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                        jsonObject.put("FeedID", feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                        jsonObject.put("TabName", tabFeedName);
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), tabFeedEventName, jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    logUserActionEvent("DashboardFeedFullView", feedInfoObj.optInt(RestUtils.TAG_FEED_ID), channelID, feedInfoObj.optString(RestUtils.FEED_TYPE), channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                }
                if (feedItemClickListener != null) {
                    JSONArray attachmentsList = feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                    ImageView contentImage = null;
                    String channelName = "";
                    if (attachmentsList != null && attachmentsList.length() > 0) {
                        if (attachmentsList.length() == 2) {
                            contentImage = communityImgvw1;
                        } else if (attachmentsList.length() == 1 || attachmentsList.length() >= 3) {
                            contentImage = communityImgvw;
                        }
                    }
                    channelName = channelObj.optString(RestUtils.FEED_PROVIDER_NAME);
                    if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                        isNetworkChannel = true;
                    } else {
                        isNetworkChannel = false;
                    }
                    feedItemClickListener.onItemClickListener(feedJson, isNetworkChannel, channelID, channelName, contentImage, getAdapterPosition());
                } else {
                    if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                        Intent in = new Intent(mContext, ContentFullView.class);
                        in.putExtra(RestUtils.CHANNEL_ID, channelID);
                        in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedJson.toString());
                        mContext.startActivity(in);
                    } else if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                        if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                            isNetworkChannel = true;
                        } else {
                            isNetworkChannel = false;
                        }
                        Intent in = new Intent(mContext, JobFeedCompleteView.class);
                        in.putExtra(RestUtils.CHANNEL_ID, channelID);
                        in.putExtra(RestUtils.CHANNEL_NAME, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        in.putExtra(RestUtils.TAG_POSITION, getAdapterPosition());
                        in.putExtra(RestUtils.TAG_FEED_OBJECT, feedJson.toString());
                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
                        mContext.startActivity(in);
                    } else {
                        if (channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_NETWORK)) {
                            isNetworkChannel = true;
                        } else {
                            isNetworkChannel = false;
                        }
                        Intent in = new Intent(mContext, FeedsSummary.class);
                        in.putExtra(RestUtils.CHANNEL_ID, channelID);
                        in.putExtra(RestUtils.CHANNEL_NAME, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        in.putExtra(RestUtils.TAG_POSITION, getAdapterPosition());
                        in.putExtra(RestUtils.TAG_FEED_OBJECT, feedJson.toString());
                        in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL, isNetworkChannel);
                        mContext.startActivity(in);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void clearAnimation() {
            mParentView.clearAnimation();
        }
    }

    private class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView categories_list;
        private View mParentView;

        public CategoriesViewHolder(View view) {
            super(view);
            mParentView = view;
            categories_list = view.findViewById(R.id.category_list_dashboard);
            setCategoriesListView(categories_list);
        }

        public void clearAnimation() {
            mParentView.clearAnimation();
        }
    }

    private class DocereeAdViewHolder extends RecyclerView.ViewHolder {

        private final DocereeAdView docereeAdView;
        private final RelativeLayout rl_docereeAdView;

        public DocereeAdViewHolder(View view) {
            super(view);
            docereeAdView = new DocereeAdView(view.getContext());
            rl_docereeAdView = (RelativeLayout) view;
            rl_docereeAdView.setGravity(Gravity.CENTER_HORIZONTAL);
            rl_docereeAdView.addView(docereeAdView);
        }
    }

    private class HorizontalListViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout horizontalCategoryMoreInfo;
        private View mParentView;
        private final TextView horizontalCategoryName;
        //private HeightWrappingViewPager horizontalViewpager;
        private RecyclerView horizontalCategoryRecyclerView;

        public HorizontalListViewHolder(View view) {
            super(view);
            mParentView = view;
            horizontalCategoryName = (TextView) view.findViewById(R.id.horizontal_category_name);
            horizontalCategoryMoreInfo = (RelativeLayout) view.findViewById(R.id.more_into_text_lay);
            horizontalCategoryRecyclerView = view.findViewById(R.id.horizontal_category_recycler_view);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.channel_main, parent, false);
            viewHolder = new DataObjectHolder(view);
        } else if (viewType == TYPE_CATEGORY_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_parent_dashboard, parent, false);
            return new CategoriesViewHolder(view);
        } else if (viewType == TYPE_HORIZONTAL_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_list_layout, parent, false);
            return new HorizontalListViewHolder(view);
        } else if (viewType == TYPE_DOCEREE_AD) {
            RelativeLayout layout = new RelativeLayout(mContext);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            return new DocereeAdViewHolder(layout);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setFeedsItemData(holder, position);
    }

    @Override
    public long getItemId(int position) {
        if (!fromDbDData) {
            long hashCode = Calendar.getInstance().getTimeInMillis();
            if (feedData.get(position) != null) {
                hashCode = feedData.get(position).hashCode();
            }
            return hashCode;
        } else {
            return mFeedsList.get(position).hashCode();
        }

    }

    private void setFeedsItemData(RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                try {
                    final DataObjectHolder viewHolder = (DataObjectHolder) holder;
                    int tempItemChannelID = 0;
                    int tempItemDoctorID = 0;
                    int tempItemFeedID = 0;
                    JSONObject tempItemChannelObj = null;
                    JSONObject tempItemFeedJson = null;
                    RealmFeedInfo currentObj = null;
                    if (!fromDbDData) {
                        tempItemChannelObj = channelsList.get(feedData.get(position).optInt(RestUtils.CHANNEL_ID));
                        tempItemFeedJson = feedData.get(position);
                        if (tempItemFeedJson == null) {
                            return;
                        }
                        tempItemChannelID = tempItemFeedJson.optInt(RestUtils.CHANNEL_ID);
                        JSONObject postCreatorObj = tempItemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR);
                        if (postCreatorObj != null)
                            tempItemDoctorID = postCreatorObj.optInt(RestUtils.TAG_DOC_ID); // app crash fix
                        JSONObject feedObj = tempItemFeedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                        if (feedObj != null)
                            tempItemFeedID = feedObj.optInt(RestUtils.TAG_FEED_ID); // app crash fix
                    } else {
                        currentObj = mFeedsList.get(position);
                        tempItemChannelObj = channelsList.get(currentObj.getChannelId());
                        tempItemFeedJson = new JSONObject(currentObj.getFeedsJson());
                        if (tempItemFeedJson == null) {
                            return;
                        }
                        tempItemChannelID = currentObj.getChannelId();
                        tempItemDoctorID = currentObj.getDocId();
                        tempItemFeedID = currentObj.getFeedId();
                    }

                    final JSONObject feedInfoObj = tempItemFeedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                    if (feedInfoObj == null) {
                        return;
                    }
                    final int itemChannelID = tempItemChannelID;
                    final int itemDoctorID = tempItemDoctorID;
                    final int itemFeedID = tempItemFeedID;
                    final JSONObject itemChannelObj = tempItemChannelObj;
                    final JSONObject itemFeedJson = tempItemFeedJson;
                    viewHolder.attachment_icon.setVisibility(View.GONE);
                    viewHolder.attachment_icon1.setVisibility(View.GONE);
                    viewHolder.attachment_icon2.setVisibility(View.GONE);
                    viewHolder.attachment_video_type.setVisibility(View.GONE);
                    viewHolder.attachment_video_type1.setVisibility(View.GONE);
                    viewHolder.attachment_video_type2.setVisibility(View.GONE);
                    viewHolder.subscription_overLay.setVisibility(View.GONE);
                    viewHolder.communityImgvw.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewHolder.communityImgvw1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewHolder.communityImgvw2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewHolder.webinar_lay.setVisibility(View.GONE);
                    viewHolder.eoi_lay.setVisibility(View.GONE);
                    viewHolder.webinar_time_date_lay.setVisibility(View.GONE);
                    Spannable feedDesc = null;
                    String displayTag = "";
                    String feedType = feedInfoObj.optString(RestUtils.FEED_TYPE);
                    String feedSubType = feedInfoObj.optString(RestUtils.TAG_FEED_SUB_TYPE);

                    if (feedType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                        displayTag = (!feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase() : "";
                    } else {
                        displayTag = (!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase() : "";
                    }
                    if (!displayTag.isEmpty()) {
                        viewHolder.post_type.setVisibility(View.VISIBLE);
                        viewHolder.post_type.setText(displayTag);
                    } else {
                        viewHolder.post_type.setVisibility(View.GONE);
                    }
                    /*
                     * TAG_TEMPLATE is to differentiate between Community Post and Network Post
                     * 1 - Community/Content Post
                     * 2 - Network Post
                     */
                    if (feedInfoObj.optInt(RestUtils.TAG_TEMPLATE) == 1) {
                        if (itemChannelObj != null) {
                            viewHolder.profileOrChannelLabel.setText(itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME, RestUtils.TAG_COMMUNITY));
                            // set channel logo
                            if (itemChannelObj.has(RestUtils.TAG_CHANNEL_LOGO) && !itemChannelObj.optString(RestUtils.TAG_CHANNEL_LOGO).isEmpty()) {
                                /*Picasso.with(mContext)
                                        .load(itemChannelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim()).placeholder(R.drawable.default_channel_logo)
                                        .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                        .into(viewHolder.profileOrChannelLogo);*/
                                AppUtil.loadCircularImageUsingLib(mContext, itemChannelObj.optString(RestUtils.TAG_CHANNEL_LOGO).trim(), viewHolder.profileOrChannelLogo, R.drawable.default_channel_logo);
                                viewHolder.profileOrChannelLogo.setContentDescription(itemChannelObj.optString(RestUtils.TAG_CHANNEL_LOGO));
                            } else {
                                viewHolder.profileOrChannelLogo.setContentDescription("");
                                viewHolder.profileOrChannelLogo.setImageResource(R.drawable.default_communitypic);
                            }
                            if (!itemChannelObj.optBoolean(RestUtils.TAG_IS_SUBSCRIBED, true)) {
                                viewHolder.subscription_overLay.setVisibility(View.VISIBLE);
                                viewHolder.subscription_overLay.bringToFront();
                            } else {
                                viewHolder.subscription_overLay.setVisibility(View.GONE);
                            }
                        } else {
                            viewHolder.profileOrChannelLogo.setContentDescription("");
                            viewHolder.profileOrChannelLabel.setText("");
                            viewHolder.profileOrChannelLogo.setImageResource(R.drawable.default_channel_logo);
                        }

                        // display post creator layout
                        if (itemFeedJson.has(RestUtils.TAG_POST_CREATOR) && itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).length() > 0) {
                            viewHolder.createdBy_layout.setVisibility(View.VISIBLE);
                            if (docId == itemDoctorID) {
                                viewHolder.docNameLable.setText(R.string.label_you);
                            } else {
                                viewHolder.docNameLable.setText(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_SALUTAION, "Dr. ") + " " + itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_FULL_NAME, "Name"));
                            }
                            if (itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).has(RestUtils.TAG_PROFILE_URL) && !itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).isEmpty()) {
                                /*Picasso.with(mContext)
                                        .load(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_profilepic)
                                        .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                        .into(viewHolder.docProfilePic);*/

                                AppUtil.loadCircularImageUsingLib(mContext, itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim(), viewHolder.docProfilePic, R.drawable.default_profilepic);
                                viewHolder.docProfilePic.setContentDescription(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL));
                            } else {
                                viewHolder.docProfilePic.setImageResource(R.drawable.default_profilepic);
                                viewHolder.docProfilePic.setContentDescription("");
                            }
                        } else {
                            viewHolder.createdBy_layout.setVisibility(View.GONE);
                        }

                    } else {
                        // hide post creator layout
                        viewHolder.createdBy_layout.setVisibility(View.GONE);
                        if (itemFeedJson.has(RestUtils.TAG_POST_CREATOR) && itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).length() > 0) {
                            if (docId == itemDoctorID) {
                                viewHolder.profileOrChannelLabel.setText(R.string.label_you);
                            } else {
                                viewHolder.profileOrChannelLabel.setText(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_SALUTAION, "Dr. ") + " " + itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_USER_FULL_NAME, "Name"));
                            }
                            // set post creator logo into channel image
                            if (itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).has(RestUtils.TAG_PROFILE_URL) && !itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).isEmpty()) {
                                /*Picasso.with(mContext)
                                        .load(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim()).placeholder(R.drawable.default_channel_logo)
                                        .error(R.drawable.default_channel_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                                        .into(viewHolder.profileOrChannelLogo);*/
                                AppUtil.loadCircularImageUsingLib(mContext, itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL).trim(), viewHolder.profileOrChannelLogo, R.drawable.default_channel_logo);
                                viewHolder.profileOrChannelLogo.setContentDescription(itemFeedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optString(RestUtils.TAG_PROFILE_URL));
                            } else {
                                viewHolder.profileOrChannelLogo.setImageResource(R.drawable.default_profilepic);
                                viewHolder.profileOrChannelLogo.setContentDescription("");
                            }
                        }
                    }
                    if (feedType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_SURVEY)) {
                        viewHolder.webinar_lay.setVisibility(View.GONE);
                        viewHolder.eoi_lay.setVisibility(View.GONE);
                        viewHolder.webinar_time_date_lay.setVisibility(View.GONE);
                        viewHolder.surveyRootLayout.setVisibility(View.VISIBLE);
                        viewHolder.surveyRootLayout.removeAllViews();
                        LinearLayout surveyOptionViewGroup = new LinearLayout(mContext);
                        surveyOptionViewGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        surveyOptionViewGroup.setOrientation(LinearLayout.VERTICAL);
                        //displayTag = (!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase() : "";
                        feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString(RestUtils.FEED_DESC), Linkify.WEB_URLS);
                        try {
                            FeedSurvey surveyData = AppUtil.getSurveyData(feedInfoObj);
                            for (int i = 0; i < 2; i++) {
                                SurveyOption surveyOption = surveyData.getQuestions().get(0).getOptions().get(i);
                                View optionRowLayout = LayoutInflater.from(mContext).inflate(R.layout.survey_option_item, null);
                                CheckBox checkBox = optionRowLayout.findViewById(R.id.option_chk_box);
                                RadioButton radioButton = optionRowLayout.findViewById(R.id.option_radio_btn);
                                TextView optionTxt = optionRowLayout.findViewById(R.id.option_txt_vw);
                                TextView optionPercentTxt = optionRowLayout.findViewById(R.id.option_percent_txt);
                                // Survey option percentage condition
                                // <b> 1. </b> If user participated and survey is immediate (or) if survey is closed,then display results
                                if ((surveyData.isParticipated() && surveyData.isImmediate()) || !surveyData.isOpen()) {
                                    optionPercentTxt.setVisibility(View.VISIBLE);
                                    optionPercentTxt.setText(surveyOption.getParticipatedPercent() + "%");
                                    if (surveyData.getQuestions().get(0) != null) {
                                        if (surveyOption.getParticipatedPercent() == surveyData.getQuestions().get(0).getHighPercentage()) {
                                            optionPercentTxt.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_dark_blue));
                                        } else {
                                            optionPercentTxt.setBackground(mContext.getResources().getDrawable(R.drawable.rounded_light_blue));
                                        }
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
                                        optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                                    } else {
                                        optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
                                    }
                                } else {
                                    if (surveyData.getQuestions().get(0).isMultiSelect()) {
                                        checkBox.setVisibility(View.VISIBLE);
                                        radioButton.setVisibility(View.GONE);
                                        checkBox.setClickable(false);
                                        if (surveyOption.isSelected()) {
                                            optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                                            checkBox.setChecked(true);
                                        } else {
                                            optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
                                            checkBox.setChecked(false);
                                        }
                                    } else {
                                        checkBox.setVisibility(View.GONE);
                                        radioButton.setVisibility(View.VISIBLE);
                                        radioButton.setClickable(false);
                                        if (surveyOption.isSelected()) {
                                            optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.border_text));
                                            radioButton.setChecked(true);
                                        } else {
                                            optionRowLayout.setBackground(mContext.getResources().getDrawable(R.drawable.option_shape_grey));
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

                            }
                            LinearLayout surveyOtherInfoLayout = new LinearLayout(mContext);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.weight = 1.0f;
                            layoutParams.setMargins(0, 16, 0, 8);
                            surveyOtherInfoLayout.setLayoutParams(layoutParams);
                            surveyOtherInfoLayout.setOrientation(LinearLayout.HORIZONTAL);
                            //surveyOtherInfoLayout.set


                            //<b> 3. </b> If survey is open, then display close time otherwise display "Final Results".
                            TextView surveyCloseView = new TextView(mContext);
                            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params1.weight = .4f;
                            params1.gravity = Gravity.START;
                            surveyCloseView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            surveyCloseView.setLayoutParams(params1);

                            TextView surveyMoreOptionsView = new TextView(mContext);
                            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params2.weight = .6f;
                            //params2.gravity = Gravity.END;
                            surveyMoreOptionsView.setGravity(Gravity.RIGHT);
                            surveyMoreOptionsView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            surveyMoreOptionsView.setTextColor(ContextCompat.getColor(mContext, R.color.app_green));
                            surveyMoreOptionsView.setLayoutParams(params2);
                            surveyOtherInfoLayout.addView(surveyCloseView);
                            surveyOtherInfoLayout.addView(surveyMoreOptionsView);
                            viewHolder.surveyRootLayout.addView(surveyOptionViewGroup);
                            viewHolder.surveyRootLayout.addView(surveyOtherInfoLayout);
                            if (surveyData.isOpen()) {
                                String time = AppUtil.getSurveyClosingTime(surveyData.getCloseTime(), surveyData.getTimeStamp());
                                if (!time.isEmpty()) {
                                    surveyCloseView.setText("Survey closes in " + time);
                                } else {
                                    surveyCloseView.setText(mContext.getResources().getString(R.string.final_result_short_msg));
                                }
                            } else {
                                surveyCloseView.setText(mContext.getResources().getString(R.string.final_result_short_msg));
                            }
                            int totalOptions = surveyData.getQuestions().get(0).getOptions().size();
                            if (totalOptions > 2) {
                                surveyMoreOptionsView.setVisibility(View.VISIBLE);
                                surveyMoreOptionsView.setText("+" + (totalOptions - 2) + " more options");
                            } else {
                                surveyMoreOptionsView.setVisibility(View.GONE);
                            }
                            loadFeedAttachment(viewHolder, surveyData.getQuestions().get(0).getQuestionJsonObj());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else if (feedType.equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
                        viewHolder.surveyRootLayout.setVisibility(View.GONE);
                        viewHolder.webinar_lay.setVisibility(View.GONE);
                        viewHolder.eoi_lay.setVisibility(View.GONE);
                        viewHolder.webinar_time_date_lay.setVisibility(View.GONE);
                        feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString(RestUtils.TAG_SHORT_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS);
                        //displayTag = (!feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_ARTICLE_TYPE).toUpperCase() : "";
                        loadFeedAttachment(viewHolder, feedInfoObj);
                    } else {
                        if (feedSubType.equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                            viewHolder.surveyRootLayout.setVisibility(View.GONE);
                            viewHolder.webinar_lay.setVisibility(View.VISIBLE);
                            viewHolder.eoi_lay.setVisibility(View.GONE);
                            viewHolder.webinar_time_date_lay.setVisibility(View.VISIBLE);
                            viewHolder.postTitle.setVisibility(View.GONE);
                            viewHolder.postdesc.setVisibility(View.GONE);
                            feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString(RestUtils.FEED_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS);
                            loadFeedAttachment(viewHolder, feedInfoObj);
                            viewHolder.postTitle.setVisibility(View.GONE);
                            viewHolder.webinar_title.setVisibility(View.VISIBLE);
                            viewHolder.webinar_title.setText(feedInfoObj.optString(RestUtils.TAG_TITLE));
                            // displayTag = (!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase() : "";
                            if (feedDesc != null) {
                                viewHolder.postdesc.setVisibility(View.GONE);
                                viewHolder.webinar_desc.setVisibility(View.VISIBLE);
                                viewHolder.webinar_desc.setMovementMethod(LinkMovementMethod.getInstance());
                                viewHolder.webinar_desc.setText(feedDesc);
                            } else {
                                viewHolder.webinar_desc.setVisibility(View.GONE);
                            }
                            if (feedInfoObj.has(RestUtils.TAG_EVENT_DETAILS)) {
                                JSONObject eventDetails = feedInfoObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS);
                                if (eventDetails.optBoolean("is_registration_event")) {
                                    //viewHolder.post_type.setText(eventDetails.optString("tag"));
                                    viewHolder.webinar_text.setText("EVENT");
                                    AppUtil.processRegisterEventData(mContext, docId, feedInfoObj.optInt(RestUtils.TAG_FEED_ID), feedInfoObj.optString(RestUtils.TITLE), eventDetails, viewHolder.date_webinar, viewHolder.webinar_time,
                                            viewHolder.register_now, viewHolder.join_now, viewHolder.already_registered_icon, viewHolder.already_registered_text, viewHolder.progressBar, viewHolder.webinar_status, viewHolder.webinar_time_date_inner_lay, viewHolder.ended_text, viewHolder.tv_date_time_label, viewHolder.tv_organizer_details);
                                } else {
                                    //viewHolder.post_type.setText(displayTag);
                                    viewHolder.webinar_text.setText("WEBINAR");
                                    AppUtil.processWebinarData(mContext, docId, feedInfoObj.optInt(RestUtils.TAG_FEED_ID), feedInfoObj.optString(RestUtils.TITLE), eventDetails, viewHolder.date_webinar, viewHolder.webinar_time,
                                            viewHolder.register_now, viewHolder.join_now, viewHolder.already_registered_icon, viewHolder.already_registered_text, viewHolder.progressBar, viewHolder.webinar_status, viewHolder.webinar_time_date_inner_lay, viewHolder.ended_text, viewHolder.view_recording_btn, viewHolder.simplePB);
                                }
                            }

                        } else {
                            viewHolder.surveyRootLayout.setVisibility(View.GONE);
                            viewHolder.webinar_lay.setVisibility(View.GONE);
                            viewHolder.webinar_time_date_lay.setVisibility(View.GONE);
                            /*if(feedType.equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
                                feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString("job_description").replaceAll("<img .*?/>", ""), Linkify.WEB_URLS);
                            }else {

                            }*/
                            feedDesc = AppUtil.linkifyHtml(feedInfoObj.optString(RestUtils.FEED_DESC).replaceAll("<img .*?/>", ""), Linkify.WEB_URLS);
                            // displayTag = (!feedInfoObj.optString(RestUtils.TAG_DISPLAY).isEmpty()) ? feedInfoObj.optString(RestUtils.TAG_DISPLAY).toUpperCase() : "";
                            loadFeedAttachment(viewHolder, feedInfoObj);
                        }
                    }
                    if (feedSubType.equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                        viewHolder.postTitle.setVisibility(View.GONE);
                        viewHolder.webinar_title.setVisibility(View.VISIBLE);
                        viewHolder.webinar_title.setText(feedInfoObj.optString(RestUtils.TAG_TITLE));
                        if (feedDesc != null) {
                            viewHolder.postdesc.setVisibility(View.GONE);
                            viewHolder.webinar_desc.setVisibility(View.VISIBLE);
                            viewHolder.webinar_desc.setMovementMethod(LinkMovementMethod.getInstance());
                            viewHolder.webinar_desc.setText(feedDesc);
                        } else {
                            viewHolder.webinar_desc.setVisibility(View.GONE);
                        }
                    } else {
                        viewHolder.webinar_title.setVisibility(View.GONE);
                        viewHolder.webinar_desc.setVisibility(View.GONE);
                        viewHolder.postTitle.setVisibility(View.VISIBLE);
                        String feedTitle = feedInfoObj.optString(RestUtils.TAG_TITLE);
                        if (feedType.equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)) {
                            feedTitle = "JOB OPENING: " + feedInfoObj.optString(RestUtils.TAG_TITLE) + " with " + feedInfoObj.optString("organization");
                        }
                        viewHolder.postTitle.setText(feedTitle);
                        if (feedDesc != null) {
                            viewHolder.postdesc.setVisibility(View.VISIBLE);
                            viewHolder.postdesc.setMovementMethod(LinkMovementMethod.getInstance());
                            viewHolder.postdesc.setText(feedDesc);
                        } else {
                            viewHolder.postdesc.setVisibility(View.GONE);
                        }
                    }

                    AppUtil.ifInterestedButtonVisibility(mContext, feedInfoObj.optJSONObject(RestUtils.TAG_EVENT_DETAILS), viewHolder.eoi_lay, feedInfoObj.optInt(RestUtils.TAG_FEED_ID), docId);
                    viewHolder.postedTime.setText(DateUtils.longToMessageListHeaderDate(feedInfoObj.optLong(RestUtils.TAG_POSTING_TIME)));

                    if (feedInfoObj.optBoolean(RestUtils.TAG_IS_EDITED)) {
                        if (displayTag.length() < 11) {
                            viewHolder.edited_text_view.setVisibility(View.VISIBLE);
                        } else {
                            viewHolder.edited_text_view.setVisibility(View.GONE);
                        }
                    } else {
                        viewHolder.edited_text_view.setVisibility(View.GONE);
                    }
                    // Bookmarks data parsing
                    final boolean isBookmarked = feedInfoObj.optBoolean(RestUtils.TAG_IS_BOOKMARKED, false);
                    if (isBookmarked) {
                        viewHolder.bookmark_dashboard_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                    } else {
                        viewHolder.bookmark_dashboard_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                    }

                    if (bookMarkStatusListener != null && position == 1) {
                        bookMarkStatusListener.isBookmarkClicked(isBookmarked);
                    }

                    viewHolder.postdesc.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                                if (viewHolder.mParentView != null) {
                                    viewHolder.mParentView.performClick();
                                }
                            }
                            return true;
                        }
                    });

                    viewHolder.webinar_desc.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                                if (viewHolder.mParentView != null) {
                                    viewHolder.mParentView.performClick();
                                }
                            }
                            return true;
                        }
                    });

                    viewHolder.subscription_button.setOnClickListener(view -> {
                        if (AppUtil.isConnectingToInternet(mContext)) {
                            viewHolder.subscriptionLoadingProgress.setVisibility(View.VISIBLE);
                            viewHolder.subscription_button.setVisibility(View.GONE);
                            boolean isSubscribedValue = !itemChannelObj.optBoolean(RestUtils.TAG_IS_SUBSCRIBED);
                            JSONObject subscribeJsonRequest = new JSONObject();
                            try {
                                subscribeJsonRequest.put(RestUtils.TAG_USER_ID, docId);
                                subscribeJsonRequest.put(RestUtils.CHANNEL_ID, itemChannelID);
                                subscribeJsonRequest.put(RestUtils.TAG_IS_SUBSCRIBED, isSubscribedValue);

                                new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.SUBSCRIPTION_SERVICE, subscribeJsonRequest.toString(), "SUBSCRIPTION_SERVICE_BOOKMARKS", new OnReceiveResponse() {
                                    @Override
                                    public void onSuccessResponse(String successResponse) {
                                        try {
                                            itemChannelObj.put(RestUtils.TAG_IS_SUBSCRIBED, isSubscribedValue);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        notifyDataSetChanged();
                                        DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                        ContentChannelsFragment.refreshChannelsOnSubscription = true;
                                    }

                                    @Override
                                    public void onErrorResponse(String errorResponse) {

                                        if (errorResponse != null) {
                                            //revert back to pre stage
                                            notifyDataSetChanged();
                                            viewHolder.subscriptionLoadingProgress.setVisibility(View.GONE);
                                            viewHolder.subscription_button.setVisibility(View.VISIBLE);
                                            try {
                                                if (!errorResponse.isEmpty()) {
                                                    JSONObject jsonObject = new JSONObject(errorResponse);
                                                    String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                    if (errorMessage != null && !errorMessage.isEmpty()) {
                                                        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }).sendSinglePartRequest();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    final JSONObject socialInteractionObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                    if (socialInteractionObj != null) {
                        AppUtil.toggleSocialBarViewCount(socialInteractionObj, viewHolder.like_count_dashboard, viewHolder.comment_count_dashboard, viewHolder.view_count_dashboard, viewHolder.socialBar_count_dashboard, viewHolder.social_bar_dashboard, viewHolder.like_in_dashboard, viewHolder.comment_layout_dashboard, viewHolder.share_layout_dashboard, viewHolder.bookmark_dashboard, viewHolder.tv_share_count);
                        //TODO: review and resolve conflicts

                        if (socialInteractionObj.optBoolean(RestUtils.TAG_IS_LIKE)) {
                            viewHolder.like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                            viewHolder.like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                            viewHolder.like_in_dashboard.setTag(true);
                        } else {
                            viewHolder.like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                            viewHolder.like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                            viewHolder.like_in_dashboard.setTag(false);
                        }

                        //Handle last comment section ui
                        if (socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) {
                            viewHolder.dashboard_latest_commented_layout.setVisibility(View.VISIBLE);
                            AppUtil.displayLatestCommentUI(mContext, docId, socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO), viewHolder.dashboard_commented_doc_image, viewHolder.dashboard_commented_doc_name, viewHolder.dashboard_commented_text);
                        } else {
                            viewHolder.dashboard_latest_commented_layout.setVisibility(View.GONE);
                        }

                        //Handle last action section ui
                        if ((socialInteractionObj.has(RestUtils.TAG_COMMENT_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_COMMENT_INFO).length() > 0) || (socialInteractionObj.has(RestUtils.TAG_LIKE_INFO) && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO) != null && socialInteractionObj.optJSONObject(RestUtils.TAG_LIKE_INFO).length() > 0)) {
                            viewHolder.last_action_viewgroup.setVisibility(View.VISIBLE);
                            AppUtil.displayLatestActionUI(socialInteractionObj, docId, viewHolder.last_action_name_text, viewHolder.last_action_viewgroup);
                        } else {
                            viewHolder.last_action_viewgroup.setVisibility(View.GONE);
                        }
                    } else {
                        viewHolder.like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                        viewHolder.like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                        viewHolder.like_in_dashboard.setTag(false);
                        viewHolder.dashboard_latest_commented_layout.setVisibility(View.GONE);
                        viewHolder.last_action_viewgroup.setVisibility(View.GONE);
                    }
                    viewHolder.like_in_dashboard.setOnClickListener(v -> {
                        if (!AppUtil.isConnectingToInternet(mContext) || itemChannelObj == null) {
                            return;
                        }
                        if (likeAPICALL != null && likeAPICALL.getStatus() == AsyncTask.Status.RUNNING) {
                            return;
                        }
                        logUserActionEvent("DashboardLikeFeed", itemFeedID, itemChannelID, feedType, itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        Boolean isLiked = true;
                        if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                            if ((Boolean) v.getTag()) {
                                isLiked = false;
                                viewHolder.like_icon_dashboard.setImageResource(R.drawable.ic_social_like);
                                viewHolder.like_label_dashboard.setTextColor(Color.parseColor("#DE231f20"));
                                viewHolder.like_in_dashboard.setTag(false);
                            } else {
                                isLiked = true;
                                viewHolder.like_icon_dashboard.setImageResource(R.drawable.ic_social_liked);
                                viewHolder.like_label_dashboard.setTextColor(Color.parseColor("#00A76D"));
                                viewHolder.like_in_dashboard.setTag(true);
                                AppUtil.loadBounceAnimation(mContext, viewHolder.like_icon_dashboard);
                            }
                            try {
                                JSONObject socialObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                                if (isLiked) {
                                    socialObj.put(RestUtils.TAG_LIKES_COUNT, socialObj.optInt(RestUtils.TAG_LIKES_COUNT) + 1);
                                    socialObj.put(RestUtils.TAG_IS_LIKE, isLiked);
                                } else {
                                    if (socialObj.optInt(RestUtils.TAG_LIKES_COUNT) > 0) {
                                        socialObj.put(RestUtils.TAG_LIKES_COUNT, socialObj.optInt(RestUtils.TAG_LIKES_COUNT) - 1);
                                    }
                                    socialObj.put(RestUtils.TAG_IS_LIKE, isLiked);
                                }
                                if (activityName.contains("MyBookmarksActivity")) {
                                    MyBookmarksActivity.selectedPosition = position;
                                } else {
                                    if (tabName != null) {
                                        if (tabName.equalsIgnoreCase("fromKnowledgeFeeds")) {
                                            KnowledgeContentFeedsFragment.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromKnowledgeMedicalEvents")) {
                                            KnowledgeMedicalEventsFeedsFragment.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromCommunitySpotlight")) {
                                            CommunitySpotlightFeedsFragment.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromCommunityFeeds")) {
                                            CommunityFeedsFragments.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromProfessionalFeeds")) {
                                            ProfessionalFeedsAndOpportuniesFragments.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromProfessionalSkilling")) {
                                            ContentFeedsFragment.selectedPosition = position;
                                        } else if (tabName.equalsIgnoreCase("fromProfessionalOpportunities")) {
                                            ProfessionalJobsFragment.selectedPosition = position;
                                        }
                                    }
                                }
                                realmManager.UpdateFeedWithSocialInteraction(itemFeedID, socialObj);
                                //notifyDataSetChanged();
                                socialInteractionCallback.onSocialInteraction(feedInfoObj, itemChannelID, isLiked, itemFeedID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                        }

                    });

                    viewHolder.dashboard_latest_commented_layout.setOnClickListener(v -> {
                        Intent commentIntent = new Intent(mContext, CommentsActivity.class);
                        commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoObj.optString(RestUtils.TAG_TITLE));
                        commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                        commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                        commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                        commentIntent.putExtra(RestUtils.CHANNEL_ID, itemChannelID);
                        mContext.startActivity(commentIntent);

                    });

                    viewHolder.comment_layout_dashboard.setOnClickListener(v -> {
                        if (itemChannelObj == null) {
                            return;
                        }
                        logUserActionEvent("DashboardCommentFeedInitiation", feedInfoObj.optInt(RestUtils.TAG_FEED_ID), itemChannelID, feedType, itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        Intent commentIntent = new Intent(mContext, CommentsActivity.class);
                        commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoObj.optString(RestUtils.TAG_TITLE));
                        commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, true);
                        commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                        commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                        commentIntent.putExtra(RestUtils.CHANNEL_ID, itemChannelID);
                        mContext.startActivity(commentIntent);
                    });
                    viewHolder.share_layout_dashboard.setOnClickListener(v -> {
                        if (!AppUtil.isConnectingToInternet(mContext) || itemChannelObj == null) {
                            return;
                        }

                        try {
                            JSONObject socialObj = feedInfoObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                            socialObj.put(RestUtils.TAG_SHARE_COUNT, socialObj.optInt(RestUtils.TAG_SHARE_COUNT) + 1);
                            realmManager.UpdateFeedWithSocialInteraction(itemFeedID, socialObj);
                            notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        logUserActionEvent("DashboardShareFeedInitiation", feedInfoObj.optInt(RestUtils.TAG_FEED_ID), itemChannelID, feedType, itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME));
                        Calendar calendar = Calendar.getInstance();
                        long selectedTime = calendar.getTimeInMillis();
                        docName = realmManager.getDocSalutation(realm) + " " + realmManager.getDoc_name(realm);
                        if (feedInfoObj.has(RestUtils.TAG_SHARE_INFO)) {
                            JSONObject shareInfoObj = feedInfoObj.optJSONObject(RestUtils.TAG_SHARE_INFO);
                            text_From_Server = shareInfoObj.optString(RestUtils.TAG_SERVER_TEXT);
                            url_From_server = shareInfoObj.optString(RestUtils.TAG_SERVER_URL);
                        }

                        if (itemChannelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
                            String feedTitle = viewHolder.postTitle.getText().toString();
                            if (feedSubType.equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                                feedTitle = viewHolder.webinar_title.getText().toString();
                            }
                            String share_des = mContext.getString(R.string.share_des_text, docName, "post", "\"" + feedTitle + "\" ");
                            if (text_From_Server != null && !text_From_Server.isEmpty()) {
                                share_des = docName + " " + text_From_Server + " \"" + feedTitle + "\" ";
                            }
                            AppUtil.inviteToWhiteCoatsIntent(mContext, share_des, "Share via", "");
                            //isEncryptionSucess = true;
                            final JSONObject shareFeedRequestObj = new JSONObject();
                            JSONArray feedDataArray = new JSONArray();
                            JSONObject innerObj = new JSONObject();
                            try {
                                shareFeedRequestObj.put(RestUtils.TAG_USER_ID, docId);
                                innerObj.put(RestUtils.CHANNEL_ID, itemChannelID);
                                innerObj.put(RestUtils.TAG_FEED_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                                innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                                feedDataArray.put(innerObj);
                                shareFeedRequestObj.put(RestUtils.TAG_SHARED_FEEDS, feedDataArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //

                            AppUtil.requestForShareAFeed(mContext, realmManager, shareFeedRequestObj.toString(), innerObj.toString());
                        } else {
                            String feedTitle = viewHolder.postTitle.getText().toString();
                            if (feedSubType.equalsIgnoreCase(RestUtils.TAG_WEBINAR)) {
                                feedTitle = viewHolder.webinar_title.getText().toString();
                            }
                            JSONObject encryptedStringData = AppUtil.encryptFeedData(mContext, feedInfoObj.optInt(RestUtils.TAG_FEED_ID), docId, itemChannelID, selectedTime, docName, "\"" + feedTitle + "\" ", text_From_Server, url_From_server);
                            if (encryptedStringData != null) {
                                final JSONObject shareFeedRequestObj = new JSONObject();
                                //JSONArray feedDataArray = new JSONArray();
                                JSONObject innerObj = new JSONObject();
                                try {
                                    shareFeedRequestObj.put(RestUtils.TAG_USER_ID, docId);
                                    innerObj.put(RestUtils.CHANNEL_ID, itemChannelID);
                                    innerObj.put(RestUtils.TAG_FEED_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                                    innerObj.put(RestUtils.TAG_TIMESTAMP, selectedTime);
                                    //feedDataArray.put(innerObj);
                                    shareFeedRequestObj.put(RestUtils.TAG_SHARE_URL, encryptedStringData.optString(RestUtils.TAG_ORIGINAL_URL));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                AppUtil.requestForShortURL(mContext, realmManager, shareFeedRequestObj.toString(), innerObj.toString(), encryptedStringData);
                            }
                        }
                    });


                    viewHolder.like_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent likeIntent = new Intent(mContext, CommentsActivity.class);
                            likeIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoObj.optString(RestUtils.TAG_TITLE));
                            likeIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                            likeIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                            likeIntent.putExtra(RestUtils.NAVIGATATION, RestUtils.TAG_FROM_LIKES_COUNT);
                            likeIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, (socialInteractionObj == null) ? 0 : socialInteractionObj.optInt(RestUtils.TAG_LIKES_COUNT));
                            likeIntent.putExtra(RestUtils.CHANNEL_ID, itemChannelID);
                            mContext.startActivity(likeIntent);
                        }
                    });

                    viewHolder.comment_count_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent commentIntent = new Intent(mContext, CommentsActivity.class);
                            commentIntent.putExtra(RestUtils.TAG_POST_NAME, feedInfoObj.optString(RestUtils.TAG_TITLE));
                            commentIntent.putExtra(RestUtils.TAG_ENABLE_KEYBOARD, false);
                            commentIntent.putExtra(RestUtils.TAG_FEED_TYPE_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                            commentIntent.putExtra(RestUtils.TAG_TOTAL_COMMENTS, socialInteractionObj.optInt(RestUtils.TAG_COMMENTS_COUNT));
                            commentIntent.putExtra(RestUtils.CHANNEL_ID, itemChannelID);
                            mContext.startActivity(commentIntent);

                        }
                    });
                    viewHolder.profileOrChannelLogo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextScreenNavigation(itemFeedJson, itemChannelObj, v);

                        }
                    });

                    viewHolder.profileOrChannelLabel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            nextScreenNavigation(itemFeedJson, itemChannelObj, v);
                        }
                    });
                    viewHolder.bookmark_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (AppUtil.getUserVerifiedStatus() == 3) {
                                if (AppUtil.isConnectingToInternet(mContext)) {
                                    String channel = "";
                                    if (itemChannelObj != null) {
                                        if (itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME) != null) {
                                            channel = itemChannelObj.optString(RestUtils.FEED_PROVIDER_NAME);
                                        }
                                    }
                                    logUserActionEvent("DashboardFeedBookmark", feedInfoObj.optInt(RestUtils.TAG_FEED_ID), itemFeedJson.optInt(RestUtils.CHANNEL_ID), feedInfoObj.optString(RestUtils.FEED_TYPE), channel);
                                    if (isBookmarked) {
                                        viewHolder.bookmark_dashboard_imageView.setImageResource(R.drawable.ic_bookmark_feed);
                                    } else {
                                        viewHolder.bookmark_dashboard_imageView.setImageResource(R.drawable.ic_bookmarked_feed);
                                        AppUtil.loadBounceAnimation(mContext, viewHolder.bookmark_dashboard_imageView);
                                    }
                                    try {
                                        JSONObject requestObj = new JSONObject();
                                        requestObj.put(RestUtils.TAG_DOC_ID, docId);
                                        requestObj.put(RestUtils.TAG_FEED_ID, feedInfoObj.optInt(RestUtils.TAG_FEED_ID));
                                        requestObj.put(RestUtils.CHANNEL_ID, itemFeedJson.optInt(RestUtils.CHANNEL_ID));
                                        requestObj.put(RestUtils.TAG_IS_BOOKMARK, !isBookmarked); // on click set the opposite one.
                                        // Service call
                                        new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.BOOKMARK, requestObj.toString(), "PERSIST_BOOKMARK", new OnReceiveResponse() {
                                            @Override
                                            public void onSuccessResponse(String successResponse) {
                                                Log.i(TAG, "onSuccessResponse()");
                                                try {
                                                    // Implement success Error condition here
                                                    if (successResponse != null) {
                                                        JSONObject jsonObject = new JSONObject(successResponse);
                                                        if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                                            JSONObject dataObject = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                                            boolean isBookMarked = dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED);
                                                            // differentiate betn MyBookmarks & Dashboard

                                                            if (isBookMarked) {
                                                                Toast.makeText(mContext, mContext.getString(R.string.label_bookmark_added), Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(mContext, mContext.getString(R.string.label_bookmark_removed), Toast.LENGTH_SHORT).show();
                                                            }
                                                            for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                                                listener.onBookmark(dataObject.optBoolean(RestUtils.TAG_IS_BOOKMARKED), dataObject.optInt(RestUtils.TAG_FEED_ID), false, dataObject.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                                            }
                                                        } else if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                                                            Toast.makeText(mContext, jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onErrorResponse(String errorResponse) {
                                                Log.i(TAG, "onErrorResponse() - " + errorResponse);
                                                if (errorResponse != null) {
                                                    //revert back to pre stage
                                                    notifyDataSetChanged();
                                                    try {
                                                        if (!errorResponse.isEmpty()) {
                                                            JSONObject jsonObject = new JSONObject(errorResponse);
                                                            String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                            if (errorMessage != null && !errorMessage.isEmpty()) {
                                                                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            }
                                        }).sendSinglePartRequest();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
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

                    viewHolder.spam_report_dashboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            socialInteractionCallback.onReportSpam("SPAM_CLICK",feedInfoObj.optInt(RestUtils.TAG_FEED_ID),docId);
                        }
                    });




                    viewHolder.docNameLable.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (docId == itemDoctorID) {
                                return;
                            }
                            AppUtil.logUserEventWithDocIDAndSplty("DashboardOtherProfileIcon", basicInfo, mContext);
                            Intent intent = new Intent(mContext, VisitOtherProfile.class);
                            intent.putExtra(RestUtils.TAG_DOC_ID, itemDoctorID);
                            mContext.startActivity(intent);
                        }
                    });
                    viewHolder.docProfilePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (docId == itemDoctorID) {
                                return;
                            }
                            if (AppUtil.isConnectingToInternet(mContext)) {
                                AppUtil.logUserEventWithDocIDAndSplty("DashboardOtherProfileIcon", basicInfo, mContext);
                                Intent intent = new Intent(mContext, VisitOtherProfile.class);
                                intent.putExtra(RestUtils.TAG_DOC_ID, itemDoctorID);
                                mContext.startActivity(intent);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAnimation(holder.itemView, position);
            } else if (holder instanceof CategoriesViewHolder) {
                categoriesAdapter = new CategoriesAdapter(mContext, categoryList);

                categoriesAdapter.setCategoryClickListener(new OnCategoryClickListener() {
                    @Override
                    public void onCategoryItemClick(Category categoryItem) {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("DocID", realmManager.getUserUUID(realm));
                            jsonObject.put("CategoryName", categoryItem.getCategoryName());
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "KnowledgeCategoryTapped", jsonObject, AppUtil.convertJsonToHashMap(jsonObject), mContext);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (AppUtil.isConnectingToInternet(mContext)) {
                            if ((categoryItem.getCategoryType().equalsIgnoreCase("DrugsCategory"))) {
                                Intent intent = new Intent(mContext, DrugClassActivity.class);
                                mContext.startActivity(intent);
                                EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate", 0, categoryItem.getCategoryId()));
                            } else {
                                Intent intent = new Intent(mContext, CategoryLoadingActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("CurrentCategoryDetails", categoryItem);
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);
                            }
                        }

                    }
                });


                linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((CategoriesViewHolder) holder).categories_list.setLayoutManager(linearLayoutManager);
                ((CategoriesViewHolder) holder).categories_list.setAdapter(categoriesAdapter);

                ((CategoriesViewHolder) holder).categories_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        ((CategoriesViewHolder) holder).categories_list.post(new Runnable() {
                            @Override
                            public void run() {
                                int visiblecount = linearLayoutManager.getChildCount();
                                int toatlcount = linearLayoutManager.getItemCount();
                                int pastitem = linearLayoutManager.findFirstVisibleItemPosition();
                                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                                if (isListExhausted) {
                                    return;
                                }
                                if (!categoryLoading) {
                                    if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1) && AppUtil.isConnectingToInternet(mContext)) {
                                        categoryListLoadMoreListener.onLoadMore();
                                        categoryLoading = true;
                                    } else {
                                        categoryLoading = false;
                                    }
                                }
                            }
                        });

                        super.onScrolled(recyclerView, dx, dy);
                    }
                });


               /* ((CategoriesViewHolder) holder).categories_list.setHasFixedSize(true);
                ((CategoriesViewHolder) holder).categories_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                ((CategoriesViewHolder) holder).categories_list.setAdapter(categoriesAdapter);*/

            } else if (holder instanceof HorizontalListViewHolder) {
                HorizontalListViewHolder horizontalDataHolder = ((HorizontalListViewHolder) holder);
                int feedId = mFeedsList.get(position - 1).getFeedId();
                HorizontalListDataObj horizontalListObj = horizontalDataList.get(feedId);
                if (horizontalListObj == null) {
                    return;
                }
                if (horizontalListObj.getChildFeedIds().size() > 0) {
                    if (horizontalListObj.isMoreVisible()) {
                        horizontalDataHolder.horizontalCategoryMoreInfo.setVisibility(View.VISIBLE);
                    } else {
                        horizontalDataHolder.horizontalCategoryMoreInfo.setVisibility(View.GONE);
                    }

                    horizontalDataHolder.horizontalCategoryName.setText(horizontalListObj.getHorizontalListTitle());
                    horizontalDataHolder.mParentView.setVisibility(View.VISIBLE);
                    horizontalDataHolder.horizontalCategoryRecyclerView.setHasFixedSize(true);
                    HorizontalRecyclerAdapter horizontalListAdapter = new HorizontalRecyclerAdapter(mContext, getDisplymetrics(), horizontalListObj, position);
                    horizontalDataHolder.horizontalCategoryRecyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(mContext, LinearLayoutManager.HORIZONTAL, false));
                    int margin = 8;
                    if (horizontalListObj.getChildFeedIds().size() > 1) {
                        margin = 32;
                    }
                    horizontalListAdapter.setItemMargin(AppUtil.dpToPx(mContext, margin));
                    horizontalListAdapter.updateDisplayMetrics();
                    //horizontalListAdapter.setHasStableIds(true);
                    horizontalDataHolder.horizontalCategoryRecyclerView.setAdapter(horizontalListAdapter);
                    horizontalDataHolder.horizontalCategoryRecyclerView.setOnFlingListener(null);
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(horizontalDataHolder.horizontalCategoryRecyclerView);
                    if (recycledViewPool != null) {
                        horizontalDataHolder.horizontalCategoryRecyclerView.setRecycledViewPool(recycledViewPool);
                    }
                    lastFeedId = horizontalListObj.getLastFeedId();
                    horizontalDataHolder.horizontalCategoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                            if (horizontalListAdapter.getInitialHorizontalLayout() != null) {
                                horizontalListAdapter.getInitialHorizontalLayout().setBackgroundResource(0);
                            }
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                View view = snapHelper.findSnapView(horizontalDataHolder.horizontalCategoryRecyclerView.getLayoutManager());
                                RelativeLayout horizontalContentlayout = (RelativeLayout) view.findViewById(R.id.horizontal_content_layout);
                                horizontalContentlayout.setBackgroundResource(R.drawable.grey_cardview_border);
                                mLastSnappedView = horizontalContentlayout;
                                int snapPosition = getSnapPosition(horizontalDataHolder.horizontalCategoryRecyclerView, snapHelper);
                                horizontalListObj.setSelectedPosition(snapPosition);
                                if (!horizontalListObj.getHorizontalListType().equalsIgnoreCase("suggested")) {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                                        jsonObject.put("PageNumber", snapPosition);
                                        AppUtil.logUserUpShotEvent("DashboardRelatedFeedsScroll", AppUtil.convertJsonToHashMap(jsonObject));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                } else {
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("DocID", realmManager.getUserUUID(realm));
                                        jsonObject.put("PageNumber", snapPosition);
                                        AppUtil.logUserUpShotEvent("DashboardSuggestedFeedsScroll", AppUtil.convertJsonToHashMap(jsonObject));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (horizontalListAdapter != null && AppUtil.isConnectingToInternet(mContext)) {
                                    if (!isLoadingHorizontalItems && snapPosition + 5 == horizontalListAdapter.getItemCount() && lastFeedId != -1) {
                                        isLoadingHorizontalItems = true;
                                        horizontalItemPageIndex++;
                                        JSONObject requestObject = new JSONObject();
                                        try {
                                            requestObject.put(RestUtils.TAG_USER_ID, docId);
                                            requestObject.put(RestUtils.TAG_PAGE_NUM, horizontalItemPageIndex);
                                            new VolleySinglePartStringRequest(mContext, Request.Method.POST, RestApiConstants.GET_SUGGESTED_FEEDS_LIST_API, requestObject.toString(), "GET_DASHBOARD_SUGGESTED_FEEDS", new OnReceiveResponse() {
                                                @Override
                                                public void onSuccessResponse(String successResponse) {
                                                    if (successResponse != null) {
                                                        try {
                                                            isLoadingHorizontalItems = false;
                                                            JSONObject jsonObject = new JSONObject(successResponse);
                                                            if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                                                if (jsonObject.has(RestUtils.TAG_DATA)) {
                                                                    JSONObject dataJsonObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                                                    horizontalListObj.setLastFeedId(dataJsonObj.optInt(RestUtils.LAST_FEED_ID));
                                                                    JSONArray feedJsonArray = dataJsonObj.optJSONArray(RestUtils.FEED_DATA);
                                                                    if (feedJsonArray.length() == 0) {
                                                                        return;
                                                                    }
                                                                    ArrayList<Integer> feedIdList = new ArrayList<>();
                                                                    for (int i = 0; i < feedJsonArray.length(); i++) {
                                                                        JSONObject completedJson = feedJsonArray.optJSONObject(i);
                                                                        JSONObject feedJson = completedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
                                                                        feedIdList.add(feedJson.optInt(RestUtils.TAG_FEED_ID));
                                                                        realmManager.insertSuggestedFeedInDB(feedJson.optInt(RestUtils.TAG_FEED_ID), completedJson);
                                                                    }
                                                                    horizontalListObj.getChildFeedIds().addAll(feedIdList);
                                                                    horizontalListAdapter.notifyDataSetChanged();
                                                                }
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onErrorResponse(String errorResponse) {
                                                    //horizontalFeedsDataList.remove(null);
                                                    isLoadingHorizontalItems = false;
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            } else if (mLastSnappedView != null) {
                                mLastSnappedView.setBackgroundResource(0);
                                mLastSnappedView = null;
                            }
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                        }
                    });
                    horizontalDataHolder.horizontalCategoryRecyclerView.getLayoutManager().scrollToPosition(horizontalListObj.getSelectedPosition());
                    horizontalDataHolder.horizontalCategoryMoreInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                                AppUtil.logUserUpShotEvent("SuggestedFeedSeeAllTapped", AppUtil.convertJsonToHashMap(jsonObject));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent in = new Intent(mContext, SuggestedListActivity.class);
                            mContext.startActivity(in);
                        }
                    });
                } else {
                    horizontalDataHolder.mParentView.setVisibility(View.GONE);
                    /*realmManager.removeFeedDataByFeedId(realm,-4);
                    notifyDataSetChanged();*/
                }

            } else if (holder instanceof DocereeAdViewHolder) {
                AdRequest adRequest = new AdRequest.AdRequestBuilder().build();
                RealmAdSlotInfo adSlot = midPostAdSlotInfo;
                int paddingTop = 0, paddingBottom = 32;
                if (mFeedsList.get(position).getFeedId() == -6) {
                    adSlot = topAdSlotInfo;
                    paddingTop = 16;
                    paddingBottom = 16;
                }
                if (adSlot != null) {
                    try {
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, paddingTop, 0, paddingBottom);
                        ((DocereeAdViewHolder) holder).docereeAdView.setLayoutParams(params);
                        ((DocereeAdViewHolder) holder).docereeAdView.setVisibility(View.GONE);
                        //((DocereeAdViewHolder) holder).rl_docereeAdView.setVisibility(View.VISIBLE);
                        ((DocereeAdViewHolder) holder).docereeAdView.setAdSlotId(adSlot.getSource_slot_id());
                        ((DocereeAdViewHolder) holder).docereeAdView.setAdSize(adSlot.getDimensions());
                        //((DocereeAdViewHolder) holder).rl_docereeAdView.setPadding(0, paddingTop, 0, paddingBottom);
                        final RealmAdSlotInfo adSlotInfo = adSlot;
                        ((DocereeAdViewHolder) holder).docereeAdView.loadAd(adRequest, new DocereeAdListener() {
                            @Override
                            public void onAdOpened() {
                                super.onAdOpened();
                            }

                            @Override
                            public void onAdClicked() {
                                AppUtil.logAdEvent(adSlotInfo, "Ad_clicked", docId, mContext);
                                super.onAdClicked();

                            }

                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                            }

                            @Override
                            public void onAdLoaded() {
                                ((DocereeAdViewHolder) holder).docereeAdView.setVisibility(View.VISIBLE);
                                AppUtil.logAdEvent(adSlotInfo, "Ad_loaded", docId, mContext);
                                super.onAdLoaded();


                            }

                            @Override
                            public void onAdFailedToLoad(String message) {
                                ((DocereeAdViewHolder) holder).docereeAdView.setVisibility(View.GONE);
                                super.onAdFailedToLoad(message);

                            }

                            @Override
                            public void onAdLeftApplication() {
                                super.onAdLeftApplication();
                            }
                        });
                    } catch (Exception e) {
                        ((DocereeAdViewHolder) holder).docereeAdView.setVisibility(View.GONE);
                        Log.e("LOADING_EXCEPTION", e.getLocalizedMessage());
                    }
                } else {
                    ((DocereeAdViewHolder) holder).docereeAdView.setVisibility(View.GONE);
                }

            } else if (holder instanceof ProgressViewHolder) {
                ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
            }
        }
    }

    private DisplayMetrics getDisplymetrics() {
        WindowManager wm = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private void logUserActionEvent(String eventName, int itemFeedID, int itemChannelID, String feedType, String channelName) {
        if (data != null && eventRequestObj != null) {
            data.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
            data.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
            data.put(RestUtils.EVENT_FEED_ID, itemFeedID);
            data.put(RestUtils.EVENT_FEED_TYPE, feedType);
            data.put(RestUtils.EVENT_CHANNEL_ID, itemChannelID);
            data.put(RestUtils.EVENT_CHANNEL_NAME, channelName);
            try {
                eventRequestObj.put(RestUtils.EVENT_DOCID, basicInfo.getUserUUID());
                eventRequestObj.put(RestUtils.EVENT_DOC_SPECIALITY, basicInfo.getSplty());
                eventRequestObj.put(RestUtils.EVENT_FEED_ID, itemFeedID);
                eventRequestObj.put(RestUtils.EVENT_FEED_TYPE, feedType);
                eventRequestObj.put(RestUtils.EVENT_CHANNEL_ID, itemChannelID);
                eventRequestObj.put(RestUtils.EVENT_CHANNEL_NAME, channelName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppUtil.logUserActionEvent(basicInfo.getDoc_id(), eventName, eventRequestObj, data, mContext);
        }
    }

    private void loadFeedAttachment(DataObjectHolder viewHolder, JSONObject feedInfoObj) {
        if (feedInfoObj.has(RestUtils.ATTACHMENT_DETAILS) && feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS) != null && feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS).length() > 0) {
            JSONArray attachmentsList = feedInfoObj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
            if (attachmentsList.length() > 1) {
                AppUtil.loadMultipleAttachments(mContext, attachmentsList, viewHolder.communityImgvw, viewHolder.communityImgvw1, viewHolder.communityImgvw2, viewHolder.remainingCountText, viewHolder.attachment_icon, viewHolder.attachment_icon1, viewHolder.attachment_icon2, viewHolder.attachment_video_type, viewHolder.attachment_video_type1, viewHolder.attachment_video_type2);
            } else if (attachmentsList.length() == 1) {
                viewHolder.communityImgvw1.setVisibility(View.GONE);
                viewHolder.communityImgvw2.setVisibility(View.GONE);
                viewHolder.remainingCountText.setVisibility(View.GONE);
                viewHolder.attachment_video_type.setVisibility(View.GONE);
                JSONObject attachObj = attachmentsList.optJSONObject(0);
                String attachment_type = attachObj.optString(RestUtils.ATTACHMENT_TYPE);
                String original_url = attachObj.optString(RestUtils.ATTACH_ORIGINAL_URL);
                String small_url = attachObj.optString(RestUtils.ATTACH_SMALL_URL);
                switch (attachment_type.toLowerCase()) {
                    case RestUtils.TAG_TYPE_IMAGE:
                        if (!original_url.isEmpty()) {
                            if (original_url.contains(RestUtils.TAG_TYPE_GIF)) {
                                viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                                viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_gif);
                            } else {
                                viewHolder.attachment_icon.setVisibility(View.GONE);
                            }
                            viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                            AppUtil.loadImageUsingGlide(mContext, original_url, viewHolder.communityImgvw, R.drawable.default_image_feed);


                        } else {
                            viewHolder.communityImgvw.setVisibility(View.GONE);
                        }
                        break;
                    case RestUtils.TAG_TYPE_PDF:
                        viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                        viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_pdf);
                        if (!small_url.isEmpty()) {

                            viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                            AppUtil.loadImageUsingGlide(mContext, small_url, viewHolder.communityImgvw, R.drawable.default_image_feed);

                        } else {
                            viewHolder.communityImgvw.setVisibility(View.GONE);
                        }
                        break;
                    case RestUtils.TAG_TYPE_VIDEO:
                        viewHolder.attachment_icon.setVisibility(View.VISIBLE);
                        viewHolder.attachment_video_type.setVisibility(View.VISIBLE);
                        viewHolder.attachment_video_type.setImageResource(R.drawable.ic_playvideo);
                        viewHolder.attachment_icon.setImageResource(R.drawable.ic_attachment_type_video);
                        if (!small_url.isEmpty()) {
                            viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                            AppUtil.loadImageUsingGlide(mContext, small_url, viewHolder.communityImgvw, R.drawable.default_image_feed);

                        } else {
                            viewHolder.communityImgvw.setVisibility(View.GONE);
                        }
                        break;
                    /**
                     * Multimedia article
                     */
                    case RestUtils.TAG_TYPE_AUDIO:
                        viewHolder.communityImgvw.setBackgroundColor(Color.parseColor("#E8E8E8"));
                        viewHolder.communityImgvw.setImageResource(R.drawable.ic_attachment_type_audio);
                        viewHolder.communityImgvw.setScaleType(ImageView.ScaleType.CENTER);
                        viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                        break;
                    default:
                        viewHolder.attachment_icon.setVisibility(View.GONE);
                        viewHolder.attachment_video_type.setVisibility(View.GONE);
                        if (!small_url.isEmpty()) {
                            viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                            AppUtil.loadImageUsingGlide(mContext, small_url, viewHolder.communityImgvw, R.drawable.default_image_feed);

                        } else {
                            viewHolder.communityImgvw.setVisibility(View.GONE);
                        }
                        break;
                }

            }
        } else {
            viewHolder.communityImgvw1.setVisibility(View.GONE);
            viewHolder.communityImgvw2.setVisibility(View.GONE);
            viewHolder.remainingCountText.setVisibility(View.GONE);
            if (feedInfoObj.has(RestUtils.TAG_LARGE_IMAGE) && !feedInfoObj.optString(RestUtils.TAG_LARGE_IMAGE).isEmpty()) {
                viewHolder.communityImgvw.setVisibility(View.VISIBLE);
                AppUtil.loadImageUsingGlide(mContext, feedInfoObj.optString(RestUtils.TAG_LARGE_IMAGE), viewHolder.communityImgvw, R.drawable.default_image_feed);
            } else {
                viewHolder.communityImgvw.setVisibility(View.GONE);
            }
        }
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(FADE_DURATION);
            itemView.startAnimation(anim);*/
            /*Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
            animation.setDuration(FADE_DURATION);
            itemView.startAnimation(animation);*/
            ScaleAnimation anim = new ScaleAnimation(0.8f, 1.0f, 0.8f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(350);
            anim.setFillAfter(true);
            itemView.startAnimation(anim);
            lastPosition = position;
        }
    }


    private void nextScreenNavigation(JSONObject feedJson, JSONObject channelObj, View v) {
        JSONObject feedInfoObj = feedJson.optJSONObject(RestUtils.TAG_FEED_INFO);
        if (feedInfoObj.optInt(RestUtils.TAG_TEMPLATE) == 1) {
            if (channelObj == null || !channelObj.optBoolean(RestUtils.TAG_SUBSCRIBED, true)) {
                return;
            }
            Intent intent = new Intent(mContext, TimeLine.class);
            intent.putExtra(RestUtils.FEED_PROVIDER_NAME, channelObj.optString(RestUtils.FEED_PROVIDER_NAME));
            intent.putExtra(RestUtils.CHANNEL_ID, feedJson.optInt(RestUtils.CHANNEL_ID));
            intent.putExtra(RestUtils.TAG_IS_ADMIN, channelObj.optBoolean(RestUtils.TAG_IS_ADMIN));
            intent.putExtra(RestUtils.TAG_FEED_PROVIDER_TYPE, channelObj.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));
            //onFeedItemClickListener.onProfileClick(v,intent);
            mContext.startActivity(intent);
        } else {
            if (feedJson.has(RestUtils.TAG_POST_CREATOR) && feedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).length() > 0) {
                if (feedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optInt(RestUtils.TAG_DOC_ID) == docId) {
                    return;
                } else {
                    AppUtil.logUserEventWithDocIDAndSplty("DashboardOtherProfileIcon", basicInfo, mContext);
                    Intent intent = new Intent(mContext, VisitOtherProfile.class);
                    intent.putExtra(RestUtils.TAG_DOC_ID, feedJson.optJSONObject(RestUtils.TAG_POST_CREATOR).optInt(RestUtils.TAG_DOC_ID));
                    mContext.startActivity(intent);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        if (!fromDbDData) {
            return feedData == null ? 0 : feedData.size();
        } else {
            return mFeedsList == null ? 0 : mFeedsList.size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (!fromDbDData) {
            if (feedData.get(position) == null) {
                return VIEW_PROG;
            } else if (feedData.get(position).has("CATEGORY_ITEM_TYPE")) {
                return TYPE_CATEGORY_LIST;
            } else {
                return VIEW_ITEM;
            }
           /* if(feedData.get(0).optString("CATEGORY_ITEM_TYPE")){

            }else{
                return feedData.get(position) == null ? VIEW_PROG : VIEW_ITEM;
            }*/

        } else if (mFeedsList.get(position).getFeedId() == -3) {
            return TYPE_CATEGORY_LIST;
        } else if (mFeedsList.get(position).getFeedId() == -4) {
            return TYPE_HORIZONTAL_LIST;
        } else if (mFeedsList.get(position).getChannelId() == -5) {
            return TYPE_HORIZONTAL_LIST;
        } else if (mFeedsList.get(position).getFeedId() == -6 || mFeedsList.get(position).getChannelId() == -6) {
            return TYPE_DOCEREE_AD;
        } else {
            return mFeedsList.get(position).getFeedId() == -1 || mFeedsList.get(position).getFeedId() == -2 ? VIEW_PROG : VIEW_ITEM;
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnScrollTopListener(OnScrollTopListener onScrollTopListener) {
        this.onScrollTopListener = onScrollTopListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public interface BookMarkListener {
        void isBookmarkClicked(boolean isBookmarked);
    }

    public void setLastPositionToDefalut() {
        lastPosition = -1;
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        //super.onViewDetachedFromWindow(holder);
        if (holder != null) {
            if (holder instanceof DataObjectHolder) {
                ((DataObjectHolder) holder).clearAnimation();
            } else if (holder instanceof CategoriesViewHolder) {
                ((CategoriesViewHolder) holder).clearAnimation();
            }
        }
    }


    public interface OnFeedProfileClickListener {

        void onProfileClick(View v, Intent in);
    }

    public void setOnFeedItemClickListener(OnFeedProfileClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    public void setSelectedTabPosition(int tabPosition) {
        this.selectedTabPosition = tabPosition;
    }


    public void setSeeMoreVisibility(boolean isVisibility) {
        this.isSeeMoreVisibility = isVisibility;
    }

    public void setOnLoadMoreHorizontalItemsListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreHorizontalItemsListener = onLoadMoreListener;
    }

    public void setIsLoadingHorizontalItems(boolean loading) {
        this.isLoadingHorizontalItems = loading;
    }

    public ArrayList<Integer> getHorizontalFeedsDataList() {
        return horizontalFeedsDataList;
    }

    public void setHorizontalLastFeedId(int feedId) {
        this.lastFeedId = feedId;
    }

    private int getSnapPosition(RecyclerView recyclerView, SnapHelper snapHelper) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }

        View snapView = snapHelper.findSnapView(layoutManager);
        if (snapView == null) {
            return RecyclerView.NO_POSITION;
        }

        return layoutManager.getPosition(snapView);
    }

    public void updateCategoriesDataWithList(ArrayList<Category> _categoryList) {
        this.categoryList.clear();
        this.categoryList.addAll(_categoryList);
    }

    public void setCategoryListLoadMoreListener(CategoryListLoadmore categoryListLoadMoreListener) {
        this.categoryListLoadMoreListener = categoryListLoadMoreListener;

    }

}
