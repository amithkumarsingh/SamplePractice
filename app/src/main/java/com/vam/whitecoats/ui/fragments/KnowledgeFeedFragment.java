package com.vam.whitecoats.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.flurry.android.FlurryAgent;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.LikeActionAsync;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.BasicInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmChannelFeedInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.ContentFullView;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.DashboardActivity;
import com.vam.whitecoats.ui.activities.FeedsSummary;
import com.vam.whitecoats.ui.activities.JobFeedCompleteView;
import com.vam.whitecoats.ui.activities.MCACardUploadActivity;
import com.vam.whitecoats.ui.activities.ProfileViewActivity;
import com.vam.whitecoats.ui.adapters.AllFeedsAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.customviews.RoundedImageView;
import com.vam.whitecoats.ui.dialogs.BottomSheetDialogReportSpam;
import com.vam.whitecoats.ui.interfaces.OnCustomClickListener;
import com.vam.whitecoats.ui.interfaces.OnFeedItemClickListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnSocialInteractionListener;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
import com.vam.whitecoats.ui.interfaces.ProfileUpdatedListener;
import com.vam.whitecoats.ui.interfaces.UiUpdateListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CallbackCollectionManager;
import com.vam.whitecoats.utils.ProfileUpdateCollectionManager;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class KnowledgeFeedFragment extends Fragment implements OnCustomClickListener, UiUpdateListener, OnFeedItemClickListener, ProfileUpdatedListener {

    public static final String TAG = KnowledgeFeedFragment.class.getSimpleName();
    public static final int POST_ACTION = 1001;
    public static final int PREFERENCE_ACTION = 1002;
    public static final int DELETE_ACTION = 1003;
    private JSONObject currentObj;
    private RecyclerView mRecyclerView;
    private AllFeedsAdapter allFeedsAdapter;
    private ArrayList<JSONObject> listOfFeeds;
    private ImageButton makePost;
    private Realm realm;
    private RealmManager realmManager;
    private String feedArrayString;
    private JSONArray feedsListJsonArray;
    private int channelID = 0;
    private boolean isAdmin;
    public AlertDialog.Builder builder;
    private static String feedProviderType;
    private String feedProviderSubType;
    private TextView noFeeds_textView;
    private String channelName;
    private int doctorID = 0;
    private ProgressDialog mProgressDialog;
    private ViewGroup postAnUpdateLayout;
    private RealmBasicInfo basicInfo;
    private RoundedImageView profilePic;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private CustomLinearLayoutManager mLayoutManager;
    private TextView postUpdateHint;
    private LinearLayout postUpdateLabel_lay;
    static KnowledgeFeedFragment instance;
    private String docName="";
    private AVLoadingIndicatorView aviTimeline;
    private JSONObject requestObj=null;
    private JSONObject channelObj;
    private ImageView addPicSymbol;
    private String timeLineResponse;
    private MySharedPref mySharedPref;
    private boolean isLoadOfflineData;
    private boolean isDashboard;
    private boolean fragmentResume=false;
    private boolean fragmentVisible=false;
    private boolean fragmentOnCreated=false;
    private LikeActionAsync likeAPICall;

    public KnowledgeFeedFragment() {
        // Required empty public constructor
    }

    public static KnowledgeFeedFragment getInstance(){
        return instance;
    }


    public static KnowledgeFeedFragment newInstance(Bundle customArgs) {
        KnowledgeFeedFragment fragment = new KnowledgeFeedFragment();
        Log.i(TAG, "Feeds_Fragment newInstance");
        Bundle args = new Bundle();
        args.putInt(RestUtils.CHANNEL_ID, customArgs.getInt(RestUtils.CHANNEL_ID));
        args.putString(RestUtils.FEED_PROVIDER_NAME, customArgs.getString(RestUtils.FEED_PROVIDER_NAME));
        args.putString(RestUtils.TAG_FEED_PROVIDER_TYPE, customArgs.getString(RestUtils.TAG_FEED_PROVIDER_TYPE));
        args.putString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, customArgs.getString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE));
        args.putBoolean(RestUtils.TAG_IS_ADMIN, customArgs.getBoolean(RestUtils.TAG_IS_ADMIN));
        args.putString("channelTimelineResponse", customArgs.getString("channelTimelineResponse"));
        args.putBoolean("loadOfflineData",customArgs.getBoolean("loadOfflineData"));
        args.putBoolean("isDashboard",customArgs.getBoolean("isDashboard"));
        fragment.setArguments(args);
        return fragment;


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.i(TAG, getString(R.string._onCreate));
        instance=this;
        CallbackCollectionManager.getInstance().registerListener(this);
        ProfileUpdateCollectionManager.registerListener(this);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        doctorID = realmManager.getDoc_id(realm);
        docName=realmManager.getDoc_name(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        if (getArguments() != null) {
            feedProviderType = getArguments().getString(RestUtils.TAG_FEED_PROVIDER_TYPE);
            feedProviderSubType = getArguments().getString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE);
            channelID = getArguments().getInt(RestUtils.CHANNEL_ID);
            channelName = getArguments().getString(RestUtils.FEED_PROVIDER_NAME);
            isAdmin = getArguments().getBoolean(RestUtils.TAG_IS_ADMIN, false);
            timeLineResponse=getArguments().getString("channelTimelineResponse");
            isLoadOfflineData=getArguments().getBoolean("loadOfflineData",false);
            isDashboard = getArguments().getBoolean("isDashboard",false);
            if (AppConstants.unreadChannelsList.contains(channelID)) {
                AppConstants.unreadChannelsList.remove(new Integer(channelID));
                Intent intent = new Intent("update_channels_count");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        }
        listOfFeeds = new ArrayList<>();
        /*
         * Hit the service with prepared request object with other params needed.
         * On success response- renders the UI with Timeline data
         * On Error - Displays the appropriate error message.
         */

        editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mySharedPref=new MySharedPref(getActivity());
    }



    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, getString(R.string._onDetach));
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, getString(R.string._onDestroy));
        super.onDestroy();
        if (CallbackCollectionManager.getInstance().getRegisterListeners().contains(this)) {
            CallbackCollectionManager.getInstance().removeListener(this);
        }
        if (ProfileUpdateCollectionManager.getRegisterListeners().contains(this)) {
            ProfileUpdateCollectionManager.removeListener(this);
        }
    }

    @Override
    public void onStop() {
        Log.i(TAG, getString(R.string._onStop));
        super.onStop();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View getHTabView = inflater.inflate(R.layout.knowledge_tab_feed, container, false);
        Log.i(TAG, getString(R.string._onCreateView));

        View rootView = inflater.inflate(R.layout.knowledge_feeds_tab, null);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        makePost = (ImageButton) rootView.findViewById(R.id.makepost);
        postAnUpdateLayout = (ViewGroup) rootView.findViewById(R.id.postUpdateLayout_Timeline);
        noFeeds_textView = (TextView) rootView.findViewById(R.id.noFeeds_textView);
        postUpdateHint = (TextView) rootView.findViewById(R.id.postUpdateLabel);
        postUpdateLabel_lay = (LinearLayout) rootView.findViewById(R.id.postUpdateLabel_lay);
        profilePic = (RoundedImageView) rootView.findViewById(R.id.profile_pic_timeline);
        aviTimeline=(AVLoadingIndicatorView)rootView.findViewById(R.id.aviInTimeline);
        noFeeds_textView.setLineSpacing(0.0f, 1.3f);
        addPicSymbol = (ImageView) rootView.findViewById(R.id.add_pic_symbol);
        //Customize the Recycler view
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        JSONObject channelObj;
        /*
         * Build the user interface to arrange the views
         */
        buildUserInterface(true);
        /*
         * Set Timeline adapter to display all the feeds and details in list
         */

        setupAdapter();
        /**
         * If user click on Make A Post button, navigate to {@link CreatePostActivity by passing required
         * Bundle params like #CHANNEL_ID, #CHANNEL_NAME and #NAVIGATION.
         */

        postUpdateLabel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                    try {
                        if (AppUtil.isConnectingToInternet(getActivity())) {
                            /*
                             * Create JSON object to pass to CreatePost
                             */
                            JSONObject channelObj = new JSONObject();
                            channelObj.put(RestUtils.CHANNEL_ID, channelID);
                            channelObj.put(RestUtils.FEED_PROVIDER_NAME, channelName);
                            channelObj.put(RestUtils.TAG_FEED_PROVIDER_TYPE,feedProviderType);
                            //Start Activity
                            Intent in = new Intent(getActivity(), CreatePostActivity.class);
                            in.putExtra(RestUtils.NAVIGATATION, "Feeds");
                            in.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj.toString());
                            startActivity(in);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if (AppUtil.getUserVerifiedStatus() == 1) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_not_uploaded));
                } else if (AppUtil.getUserVerifiedStatus() == 2) {
                    AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_uploaded_but_not_verified));
                }
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_profile = new Intent(getActivity(), ProfileViewActivity.class);
                startActivity(intent_profile);
            }
        });
        //if (!fragmentResume && fragmentVisible) {
        if ((timeLineResponse != null && !timeLineResponse.isEmpty()) || isLoadOfflineData) {
            processChannelTimeLineResponse(timeLineResponse, false);
        } else if (requestObj == null) {
            requestObj = new JSONObject();
            try {
                requestObj.put(RestUtils.TAG_DOC_ID, doctorID);
                requestObj.put(RestUtils.CHANNEL_ID, 1282);
                requestObj.put(RestUtils.LAST_FEED_ID, 0);
                requestObj.put("load_next", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (AppUtil.isConnectingToInternet(getActivity())) {
                requestTimeLineService(requestObj.toString(), false);
            } else {
                if (aviTimeline != null && aviTimeline.isEnabled()) {
                    aviTimeline.hide();
                }
            }
        }
        //}
        return rootView;
//        return getHTabView;
    }

    public void buildUserInterface(boolean fromOnCreate) {
        Log.i(TAG, "buildUserInterface()");
        /**
         * Check whether feed data is available, if the list is empty then display message
         * 'No Data Available' otherwise process the User Interface and build the UI.
         */

        if (listOfFeeds.size() == 0 && !fromOnCreate) {
            noFeeds_textView.setVisibility(View.VISIBLE);
            noFeeds_textView.setText(getString(R.string.no_data_available));

        } else {
            noFeeds_textView.setVisibility(View.GONE);
            postAnUpdateLayout.setVisibility(View.GONE);
            // If type is COMMUNITY and user is Admin then display Make A Post Button, otherwise Hide it.
            if (feedProviderType != null && feedProviderType.equalsIgnoreCase(RestUtils.TAG_COMMUNITY)) {
                makePost.setImageResource(R.drawable.btn_create_post);
            }
            // Set user Profile picture if available , else set the default pic
            loadProfileData(basicInfo);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(getActivity()!=null && isAdded()) {
                        if (feedProviderSubType != null && feedProviderSubType.equalsIgnoreCase(getString(R.string.feed_provide_subtype_patients))) {
                            presentShowcaseSequence_Community("Sequence_Content");
                        }
                    }
                }
            }, 1000);
        }
    }

    private void loadProfileData(RealmBasicInfo basicInfo) {
        String picUrl = (basicInfo.getPic_url() != null) ? basicInfo.getPic_url().trim() : "";
        String picPath = (basicInfo.getProfile_pic_path() != null) ? basicInfo.getProfile_pic_path().trim() : "";
        String profilePicPath = (picUrl != null && !picUrl.isEmpty()) ? picUrl : (picPath != null && !picPath.equals("")) ? picPath : null;
        if(!AppUtil.checkWriteExternalPermission(getActivity())){
            if(basicInfo.getPic_url()!=null && !basicInfo.getPic_url().isEmpty()){
                AppUtil.invalidateAndLoadCircularImage(getActivity(),basicInfo.getPic_url().trim(),profilePic,R.drawable.default_profilepic);
            }
        }
        else if(basicInfo.getPic_url()!=null && !basicInfo.getPic_url().isEmpty()){
            AppUtil.invalidateAndLoadCircularImage(getActivity(),basicInfo.getPic_url().trim(),profilePic,R.drawable.default_profilepic);
        }
        else if (basicInfo != null && basicInfo.getProfile_pic_path() != null && !basicInfo.getProfile_pic_path().equals("")) {
            File imgFile = new File(basicInfo.getProfile_pic_path());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profilePic.setImageBitmap(myBitmap);
            } else {
                profilePic.setImageResource(R.drawable.default_profilepic);
            }
        } else {
            Bitmap sourceBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profilepic);
            BitmapDrawable drawable = new BitmapDrawable(getResources(), AppUtil.createCircleBitmap(sourceBitmap));
            profilePic.setImageDrawable(drawable);
        }
        if (basicInfo != null && !basicInfo.getFname().isEmpty()) {
            Spannable padString = new SpannableString("Share an update or a case");
            padString.setSpan(new ForegroundColorSpan(Color.parseColor("#00A76D")), 0, padString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            postUpdateHint.setText("Hi " +basicInfo.getUser_salutation()+" "+ basicInfo.getFname()+",");
        }

        if (profilePicPath != null && !profilePicPath.isEmpty()) {
            addPicSymbol.setVisibility(View.GONE);
        } else {
            addPicSymbol.setVisibility(View.VISIBLE);
        }
    }

    private void setupAdapter() {
        Log.i(TAG, "setupAdapter()");
        allFeedsAdapter = new AllFeedsAdapter(getActivity(), KnowledgeFeedFragment.this, doctorID, listOfFeeds, mRecyclerView, feedProviderType, channelName, channelID, realmManager.getDocSalutation(realm)+" "+docName,new OnSocialInteractionListener() {
            @Override
            public void onSocialInteraction(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {
                makeLikeServiceCall(subItem, channel_id, isLiked, mFeedTypeId);
            }

            @Override
            public void onUIupdateForLike(JSONObject subItem, int channel_id, Boolean isLiked, int mFeedTypeId) {
                try {
                    JSONObject socialInteractionObj = subItem.optJSONObject(RestUtils.TAG_SOCIALINTERACTION);
                    subItem.put(RestUtils.CHANNEL_ID, channel_id);
                    subItem.put(RestUtils.FEED_TYPE_ID, mFeedTypeId);
                    if (socialInteractionObj != null) {
                        subItem.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).put(RestUtils.TAG_IS_LIKE, isLiked);
                        int likesCount = Integer.parseInt(subItem.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).optString(RestUtils.TAG_LIKES_COUNT));
                        if (isLiked) {
                            if (likesCount >= 0) {
                                subItem.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).put(RestUtils.TAG_LIKES_COUNT, likesCount + 1);
                            }
                        } else {
                            subItem.optJSONObject(RestUtils.TAG_SOCIALINTERACTION).put(RestUtils.TAG_LIKES_COUNT, likesCount - 1);
                        }
                    }
                    if (allFeedsAdapter != null) {
                        allFeedsAdapter.notifyDataSetChanged();
                    }
                    //DashboardUpdatesFragment.updatePostWithLikeOrCommentResponse(subItem);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReportSpam(String clickOnSpam, int feedId, int docId) {
                if (clickOnSpam.equalsIgnoreCase("SPAM_CLICK")) {
                    BottomSheetDialogReportSpam bottomSheet = new BottomSheetDialogReportSpam();
                    Bundle bundle = new Bundle();
                    bundle.putInt("feedId", feedId);
                    bundle.putInt("docId", docId);
                    bottomSheet.setArguments(bundle);
                    bottomSheet.show(requireActivity().getSupportFragmentManager(),
                            "ModalBottomSheetReportSpam");

                }
            }
        },this);
        mRecyclerView.setAdapter(allFeedsAdapter);


        allFeedsAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                listOfFeeds.add(null);
                realmManager.insertTestFeedDataIntoDB(realm, -1, false);
                mRecyclerView.post(new Runnable() {
                    public void run() {
                        allFeedsAdapter.notifyItemInserted(listOfFeeds.size());
                    }
                });
//                allFeedsAdapter.notifyItemInserted(listOfFeeds.size());
                JSONObject requestData = new JSONObject();
                try {
                    requestData.put(RestUtils.CHANNEL_ID, channelID);
                    requestData.put(RestUtils.TAG_DOC_ID, doctorID);
                    requestData.put(RestUtils.LAST_FEED_ID, listOfFeeds.get(listOfFeeds.size() - 2).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID));
                    requestData.put("load_next", true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "feeds Service call");
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.CHANNEL_TIMELINE, requestData.toString(), "FEED_FRAGMENT_LOADMORE", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        if (getActivity() != null && isAdded()) {
                            listOfFeeds.remove(listOfFeeds.size() - 1);
                            allFeedsAdapter.notifyItemRemoved(listOfFeeds.size());
                            if (successResponse != null) {
                                if (successResponse.equals("SocketTimeoutException") || successResponse.equals("Exception")) {
                                    Log.i(TAG, "onTaskCompleted(String response) " + successResponse);
                                    hideProgress();
                                    ShowSimpleDialog("Error", getResources().getString(R.string.timeoutException));
                                } else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(successResponse);
                                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                                            JSONObject dataJsonObj = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                                            //int last_feed_id = dataJsonObj.optInt("last_feed_id");
                                            JSONArray nextFeedsArray = dataJsonObj.optJSONArray("feed_data");
                                            if (nextFeedsArray.length() > 0) {
                                                for (int i = 0; i < nextFeedsArray.length(); i++) {
                                                    listOfFeeds.add(nextFeedsArray.optJSONObject(i));
                                                }
                                                allFeedsAdapter.setLoaded();
                                            } else {
                                                Toast.makeText(getActivity(), "No more feeds", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else if(jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR))
                                        {
                                            String errorMsg="Something went wrong,please try again.";
                                            if(jsonObject.has(RestUtils.TAG_ERROR_MESSAGE)){
                                                errorMsg=jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                            }

                                            if(jsonObject.optString((RestUtils.TAG_ERROR_CODE)).equals("4045")) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                builder.setMessage(errorMsg);
                                                builder.setCancelable(true);
                                                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        if(!(getActivity() instanceof DashboardActivity)) {
                                                            if (getActivity() != null) {
                                                                getActivity().finish();
                                                            }
                                                        }
                                                    }
                                                }).create().show();
                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    allFeedsAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        if(getActivity() != null && isAdded()) {
                            //String errorMsg = getResources().getString(R.string.unable_to_connect_server);
                            if(listOfFeeds.contains(null)) {
                                listOfFeeds.remove(listOfFeeds.size() - 1);
                                allFeedsAdapter.notifyItemRemoved(listOfFeeds.size());
                            }
                            if(!(getActivity() instanceof DashboardActivity)) {
                                if(getActivity()!=null) {
                                    ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                                }
                            }
                        }
                    }
                }).sendSinglePartRequest();
            }
        });
    }

    public void requestTimeLineService(String requestObject, final boolean loadPreData) {
        Log.i(TAG, "requestTimeLineService()");
        FlurryAgent.logEvent("Timeline hit :" + AppConstants.login_doc_id);
        AppUtil.captureSentryMessage("Timeline hit :" + AppConstants.login_doc_id);
        if (!loadPreData) {
            if(aviTimeline!=null){
                aviTimeline.show();
            }
        }
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.CHANNEL_TIMELINE, requestObject, Feeds_Fragment.TAG, new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                requestObj=new JSONObject();
                processChannelTimeLineResponse(successResponse,loadPreData);
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                if(getActivity() != null && isAdded()) {
                    Log.i(TAG, "onErrorResponse()");
                    if (!loadPreData) {
                        if(aviTimeline!=null){
                            aviTimeline.hide();
                        }
                        if(!(getActivity() instanceof DashboardActivity)) {
                            if(getActivity()!=null) {
                                ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                            }
                        }
                    }
                }
            }
        }).sendSinglePartRequest();


    }

    private void processChannelTimeLineResponse(String successResponse,boolean loadPreData) {
        if(getActivity() != null && isAdded()) {
            Log.i(TAG, "onSuccessResponse()");
            if (!loadPreData) {
                if(aviTimeline!=null){
                    aviTimeline.hide();
                }
            }
            if(isLoadOfflineData && loadPreData == false){
                try {
                    RealmList<RealmChannelFeedInfo> channelData = realmManager.getChannelFeedDataFromDB();
                    for(int i=0;i<channelData.size();i++){
                        listOfFeeds.add(new JSONObject(channelData.get(i).getFeedsJson()));
                    }
                    allFeedsAdapter.notifyDataSetChanged();
                    //refresh the UI
                    buildUserInterface(false);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return;
            }
            JSONObject responseObject = null;
            try {
                responseObject = new JSONObject(successResponse);
                if (responseObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                    JSONArray feedDataArray = responseObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.FEED_DATA);
                    int len = feedDataArray.length();
                    if (loadPreData) {
                        for (int x = len - 1; x >= 0; x--) {
                            listOfFeeds.add(0, feedDataArray.optJSONObject(x));
                        }
                    } else {
                        for (int i = 0; i < len; i++) {
                            try {
                                listOfFeeds.add(feedDataArray.optJSONObject(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    int savedChannelId = mySharedPref.getPref(MySharedPref.PREF_SAVED_PREF_CHANNEL, -1);
                    if(savedChannelId==channelID && isDashboard == false){
                        realmManager.insertChannelFeedDataIntoDB(realm,feedDataArray,loadPreData ? true : false);
                    }
                    isDashboard = false;
                    //setupAdapter();
                    //Refresh the adapter after populating the data into list
                    allFeedsAdapter.notifyDataSetChanged();
                    //refresh the UI
                    buildUserInterface(false);

                }
                else if(responseObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR))
                {
                    String errorMsg="Something went wrong,please try again.";
                    if(responseObject.has(RestUtils.TAG_ERROR_MESSAGE)){
                        errorMsg=responseObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                    }

                    if(responseObject.optString((RestUtils.TAG_ERROR_CODE)).equals("4045")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(errorMsg);
                        builder.setCancelable(true);
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!(getActivity() instanceof DashboardActivity)){
                                    getActivity().finish();
                                }
                            }
                        }).create().show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    private void makeLikeServiceCall(JSONObject subItem, final int channel_id, Boolean isLiked, final int mFeedTypeId) {
        {
            JSONObject likeRequest = new JSONObject();
            try {
                likeRequest.put(RestUtils.TAG_DOC_ID, doctorID);
                likeRequest.put(RestUtils.CHANNEL_ID, channel_id);
                likeRequest.put(RestUtils.FEED_TYPE_ID, mFeedTypeId);
                JSONObject socialInteractionObj = new JSONObject();
                socialInteractionObj.put("type", "Like");
                socialInteractionObj.put(RestUtils.TAG_IS_LIKE, isLiked);
                likeRequest.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!AppUtil.isConnectingToInternet(getActivity())) {
                return;
            } else {
                AppConstants.likeActionList.add(channel_id + "_" + mFeedTypeId);
            }
            likeAPICall=new LikeActionAsync(getActivity(), RestApiConstants.SOCIAL_INTERACTIONS, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String sResponse) {
                    if (AppConstants.likeActionList.contains(channel_id + "_" + mFeedTypeId)) {
                        AppConstants.likeActionList.remove(channel_id + "_" + mFeedTypeId);
                    }
                    if (sResponse != null) {
                        if (sResponse.equals("SocketTimeoutException") || sResponse.equals("Exception")) {
                            Log.i(TAG, "onTaskCompleted(String response) " + sResponse);
                            Log.e("Error like response", getResources().getString(R.string.timeoutException));
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(sResponse);
                                if (jsonObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                                    JSONObject likeResponseObj = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                                    for (UiUpdateListener listener : CallbackCollectionManager.getInstance().getRegisterListeners()) {
                                        int feedId=likeResponseObj.optInt(RestUtils.FEED_TYPE_ID);
                                        if(likeResponseObj.has(RestUtils.TAG_FEED_ID)){
                                            feedId=likeResponseObj.optInt(RestUtils.TAG_FEED_ID);
                                        }
                                        listener.updateUI(feedId, likeResponseObj.optJSONObject(RestUtils.TAG_SOCIALINTERACTION));
                                    }
                                    //updateTimeLinePostWithLikeOrCommentResponse(likeResponseObj);
                                } else {
                                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                                            if (jsonObject.getString(RestUtils.TAG_ERROR_CODE).equals("603")) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setMessage(jsonObject.getString(RestUtils.TAG_ERROR_MESSAGE));
                                                builder.setCancelable(true);
                                                builder.setPositiveButton("Verify Now", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Intent verifyLikeIntent = new Intent(getActivity(), MCACardUploadActivity.class);
                                                        startActivity(verifyLikeIntent);
                                                    }
                                                });

                                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                }).create().show();
                                            } else {

                                                //Toast.makeText(getActivity(), jsonObject.getString("errorMsg"), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }
            });
            likeAPICall.execute(likeRequest.toString());
            allFeedsAdapter.setLikeAPIAsync(likeAPICall);

        }
    }

    @Override
    public void OnCustomClick(View aView, int position) {

    }

    @Override
    public void updateUI(int feedId, JSONObject socialInteractionObj) {
        int selectedPosition = 0;
        for (int i = 0; i < listOfFeeds.size(); i++) {
            if (feedId == listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                selectedPosition = i;
                currentObj = listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO);
                break;
            }
        }
        if (currentObj != null) {
            try {
                currentObj.put(RestUtils.TAG_SOCIALINTERACTION, socialInteractionObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //listOfFeeds.set(selectedPosition, currentObj);
            if (allFeedsAdapter != null) {
                allFeedsAdapter.notifyDataSetChanged();
            }
            /*try {
                currentObj.put("channel_id", channelID);
                currentObj.put("feedTypeId", socialInteractionObj.optInt("feedTypeId"));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }

    @Override
    public void notifyUIWithNewData(JSONObject newUpdate) {
        if(getActivity()!=null && isAdded()) {
            if(newUpdate.has(RestUtils.TAG_FROM_EDIT_POST)) {
                int len = listOfFeeds.size();
                for (int i = 0; i < len; i++) {
                    if (listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && newUpdate.optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) == listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                        listOfFeeds.set(i, newUpdate);
                        break;
                    }
                }
                allFeedsAdapter.notifyDataSetChanged();
            } else {
                if (newUpdate != null && newUpdate.has(RestUtils.CHANNEL_ID) && newUpdate.optInt(RestUtils.CHANNEL_ID) == channelID) {
                    if (newUpdate.has(RestUtils.ATTACHMENT_DETAILS)) {
                        JSONArray attachmentDetailsArray = newUpdate.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                        if (attachmentDetailsArray.length() > 0) {
                            AppConstants.isMediaServiceRequired = true;
                        }
                    }
                    JSONObject requestData = new JSONObject();
                    try {
                        requestData.put(RestUtils.CHANNEL_ID, 1282);
                        requestData.put(RestUtils.TAG_DOC_ID, doctorID);
                        requestData.put(RestUtils.LAST_FEED_ID, listOfFeeds.size() > 0 ? (listOfFeeds.get(0).optJSONObject(RestUtils.TAG_FEED_INFO) != null ? listOfFeeds.get(0).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID) : 0) : 0);
                        requestData.put("load_next", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (AppUtil.isConnectingToInternet(getActivity())) {
                        if (listOfFeeds != null) {
                            requestTimeLineService(requestData.toString(), true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void notifyUIWithDeleteFeed(int feedId, JSONObject deletedFeedObj) {
        if (channelID == deletedFeedObj.optInt(RestUtils.CHANNEL_ID)) {
            int post_id_fromDel = deletedFeedObj.optInt(RestUtils.TAG_POST_ID);
            for (int i = 0; i < listOfFeeds.size(); i++) {
                if (listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO) != null && post_id_fromDel == listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                    listOfFeeds.remove(i);
                    break;
                }
            }
            allFeedsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBookmark(boolean isBookmarked, int feedID,boolean isAutoRefresh, JSONObject socialInteractionObj) {
        Log.i(TAG, "onBookmark(boolean isBookmarked,int feedID)");
        try {
            int position=0;
            int len=listOfFeeds.size();
            for(int i=0;i<len;i++){
                if(listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)==feedID){
                    position=i;
                    break;
                }
            }
            listOfFeeds.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_IS_BOOKMARKED,isBookmarked);
            listOfFeeds.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put(RestUtils.TAG_SOCIALINTERACTION,socialInteractionObj);
            allFeedsAdapter.notifyDataSetChanged();
//            DashboardUpdatesFragment.getInstance().onBookmark(isBookmarked,feedID);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void notifyUIWithFeedSurveyResponse(int feedId, JSONObject surveyResponse) {
        int selectedPosition = -1;
        JSONObject feedObj=null;
        for (int i = 0; i < listOfFeeds.size(); i++) {
            if (feedId == listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                selectedPosition = i;
                feedObj = listOfFeeds.get(i);
                break;
            }
        }
        if(selectedPosition!=-1 && feedObj!=null){
            listOfFeeds.set(selectedPosition,AppUtil.processFeedSurveyResponse(feedObj,surveyResponse));
            if (allFeedsAdapter != null) {
                allFeedsAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void notifyUIWithFeedWebinarResponse(int feedId, JSONObject webinarRegisterResponse) {
        for (int i = 0; i < listOfFeeds.size(); i++) {
            if (feedId == listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)) {
                currentObj = listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO);
                break;
            }
        }
        if (currentObj != null) {
            try {
                currentObj.put("event_details", webinarRegisterResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //listOfFeeds.set(selectedPosition, currentObj);
            if (allFeedsAdapter != null) {
                allFeedsAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void notifyUIWithJobApplyStatus(int feedId, JSONObject jobApplyResponse) {
        Log.i(TAG, "apply job status change");
        try {
            int position=0;
            int len=listOfFeeds.size();
            for(int i=0;i<len;i++){
                if(listOfFeeds.get(i).optJSONObject(RestUtils.TAG_FEED_INFO).optInt(RestUtils.TAG_FEED_ID)==feedId){
                    position=i;
                    break;
                }
            }
            listOfFeeds.get(position).getJSONObject(RestUtils.TAG_FEED_INFO).put("is_applied",true);
            allFeedsAdapter.notifyDataSetChanged();
//            DashboardUpdatesFragment.getInstance().onBookmark(isBookmarked,feedID);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickListener(JSONObject feedObj,boolean isNetworkChannel, int channelId, String channelName, View sharedView, int selectedPosition) {
        JSONObject feedInfoObj = feedObj.optJSONObject(RestUtils.TAG_FEED_INFO);
        if (feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.CHANNEL_TYPE_ARTICLE)) {
            Intent in = new Intent(getActivity(), ContentFullView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.TAG_CONTENT_PROVIDER, channelName);
            in.putExtra(RestUtils.TAG_CONTENT_OBJECT, feedObj.toString());
            if(sharedView!=null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                //ActivityCompat.startActivity(getActivity(), in, options.toBundle());
                getActivity().startActivity(in);
            }else{
                getActivity().startActivity(in);
            }
        }
        else if(feedInfoObj.optString(RestUtils.FEED_TYPE).equalsIgnoreCase(RestUtils.TAG_JOB_POSTING_TYPE)){
            Intent in = new Intent(getActivity(), JobFeedCompleteView.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,isNetworkChannel);
            if(sharedView!=null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                //ActivityCompat.startActivity(getActivity(), in, options.toBundle());
                getActivity().startActivity(in);
            }else{
                getActivity().startActivity(in);
            }
        }else {
            Intent in = new Intent(getActivity(), FeedsSummary.class);
            in.putExtra(RestUtils.CHANNEL_ID, channelId);
            in.putExtra(RestUtils.CHANNEL_NAME, channelName);
            in.putExtra(RestUtils.TAG_POSITION, selectedPosition);
            in.putExtra(RestUtils.TAG_FEED_OBJECT, feedObj.toString());
            in.putExtra(RestUtils.TAG_IS_NETWORK_CHANNEL,isNetworkChannel);
            if(sharedView!=null) {
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(),
                                sharedView,
                                ViewCompat.getTransitionName(sharedView));
                //ActivityCompat.startActivity(getActivity(), in, options.toBundle());
                getActivity().startActivity(in);
            }else{
                getActivity().startActivity(in);
            }
        }
    }

    public void handleOnActivityResults(int requestCode, int resultCode, Intent data) {
        if (resultCode == POST_ACTION) {
            if (data != null) {
                try {
                    JSONObject post_obj = new JSONObject(data.getExtras().getString("POST_OBJ"));
                    if (channelID == post_obj.optInt(RestUtils.CHANNEL_ID)) {
                        if (post_obj.has(RestUtils.ATTACHMENT_DETAILS)) {
                            JSONArray attachmentDetailsArray = post_obj.optJSONArray(RestUtils.ATTACHMENT_DETAILS);
                            if (attachmentDetailsArray.length() > 0) {
                                AppConstants.isMediaServiceRequired = true;
                            }
                        }
                        listOfFeeds.add(0, post_obj);
                        allFeedsAdapter.notifyDataSetChanged();
                        if (listOfFeeds.size() == 0) {
                            noFeeds_textView.setVisibility(View.VISIBLE);
                            noFeeds_textView.setText(getString(R.string.no_data_available));

                        } else {
                            noFeeds_textView.setVisibility(View.GONE);
                        }
                        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                                .getLayoutManager();
                        layoutManager.scrollToPositionWithOffset(0, 0);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == DELETE_ACTION) {
            if (data != null) {
                try {
                    JSONObject post_obj = new JSONObject(data.getExtras().getString("POST_OBJ"));
                    if (channelID == post_obj.optInt(RestUtils.CHANNEL_ID)) {
                        int post_id_fromDel = post_obj.optInt("post_id");
                        for (int i = 0; i < listOfFeeds.size(); i++) {
                            if (post_id_fromDel == listOfFeeds.get(i).optInt("post_id")) {
                                listOfFeeds.remove(i);
                                break;
                            }
                        }
                        allFeedsAdapter.notifyDataSetChanged();
                        /*LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                                .getLayoutManager();
                        layoutManager.scrollToPositionWithOffset(0, 0);*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == PREFERENCE_ACTION) {
            listOfFeeds.clear();
            allFeedsAdapter.notifyDataSetChanged();
            JSONObject requestData = new JSONObject();
            try {
                requestData.put(RestUtils.CHANNEL_ID, channelID);
                requestData.put(RestUtils.TAG_DOC_ID, doctorID);
                requestData.put(RestUtils.LAST_FEED_ID, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (AppUtil.isConnectingToInternet(getActivity())) {
                /*
                 * Display progress while channels are being downloaded in background.
                 */
                showProgress();
                new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.Content_Timeline, requestData.toString(), "FEEDS_FRAGMENT", new OnReceiveResponse() {
                    @Override
                    public void onSuccessResponse(String successResponse) {
                        hideProgress();
                        try {
                            JSONObject jsonObject = new JSONObject(successResponse);
                            JSONObject dataJsonObj = jsonObject.getJSONObject(RestUtils.TAG_DATA);
                            JSONArray nextFeedsArray = dataJsonObj.getJSONArray("articles");
                            for (int i = 0; i < nextFeedsArray.length(); i++) {
                                listOfFeeds.add(nextFeedsArray.getJSONObject(i));
                            }
                            dataJsonObj.put(RestUtils.CHANNEL_ID, channelID);
                            DashboardUpdatesFragment.updateDashboardWithNewContent(dataJsonObj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        allFeedsAdapter.notifyDataSetChanged();
                        if (listOfFeeds.size() == 0) {
                            noFeeds_textView.setVisibility(View.VISIBLE);
                            noFeeds_textView.setText("No feeds available as per the selected preferences. Please edit your preferences.");
                        }
                    }

                    @Override
                    public void onErrorResponse(String errorResponse) {
                        hideProgress();
                        Toast.makeText(getActivity(), getResources().getString(R.string.unable_to_connect_server), Toast.LENGTH_SHORT).show();
                    }
                }).sendSinglePartRequest();
            }
        }

    }

    /**
     * This method simply hides the progress dialog if it
     * is currently showing on UI.
     */
    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * This method simply displays the progress dialog if it
     * is currently not showing on UI.
     */
    public synchronized void showProgress() {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShowSimpleDialog(final String title, final String message) {
        try {
            builder = new AlertDialog.Builder(getActivity());
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("OK", null);
            builder.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void presentShowcaseSequence_Community(String SequenceId) {
        if (AppConstants.COACHMARK_INCREMENTER > 0 && getActivity() != null && isAdded()) {
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500);
            config.setShapePadding(1);// half second between each showcase view

            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SequenceId);
            sequence.setConfig(config);
            if (mRecyclerView.getChildAt(0) != null) {
                if (!editor.getBoolean("feeds_fragment_content", false)) {
                    if (SequenceId.equalsIgnoreCase("Sequence_Content")) {
                        if (mRecyclerView.getChildAt(0).findViewById(R.id.share_layout_dashboard) != null) {
                            sequence.addSequenceItem(
                                    new MaterialShowcaseView.Builder(getActivity())
                                            .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.share_layout_dashboard))
                                            .setDismissText("Got it")
                                            .setDismissTextColor(Color.parseColor("#00a76d"))
                                            .setMaskColour(Color.parseColor("#CC231F20"))
                                            .setContentText(R.string.tap_to_coach_mark_patient_timeline).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("feeds_fragment_content", true).commit();
                                        }
                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                                        }
                                    })
                                            .withCircleShape()
                                            .build()
                            );
                        }
                    }
                }
            }
            sequence.start();
        }
    }/*else {
                    if (isAdmin) {
                        if (!editor.getBoolean("feeds_fragment_content", false)) {
                            sequence.addSequenceItem(
                                    new MaterialShowcaseView.Builder(getActivity())
                                            .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.share_textview_dashboard))
                                            .setDismissText("Next")
                                            .setDismissTextColor(Color.parseColor("#00a76d"))
                                            .setMaskColour(Color.parseColor("#CC231F20"))
                                            .setContentText(R.string.tap_to_coach_mark_patient_timeline).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("feeds_fragment_content", true).commit();
                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                        }
                                    })
                                            .withRectangleShape(true)
                                            .build()
                            );
                        }
                    } else {
                        if (!editor.getBoolean("feeds_fragment_content", false)) {
                            sequence.addSequenceItem(
                                    new MaterialShowcaseView.Builder(getActivity())
                                            .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.tv_feeds_title))
                                            .setDismissText("Got it")
                                            .setDismissTextColor(Color.parseColor("#00a76d"))
                                            .setMaskColour(Color.parseColor("#CC231F20"))
                                            .setContentText(R.string.tap_to_coach_mark_start_reading).setListener(new IShowcaseListener() {
                                        @Override
                                        public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                            editor.edit().putBoolean("feeds_fragment_content", true).commit();
                                        }

                                        @Override
                                        public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                        }
                                    })
                                            .withRectangleShape(true)
                                            .build()
                            );
                        }
                    }
                }

        }
            if (feedProviderType != null && feedProviderType.equalsIgnoreCase("Community") && (makePost.getVisibility() == View.VISIBLE)) {
                if (!editor.getBoolean("feeds_fragment_community", false)) {
                    sequence.addSequenceItem(
                            new MaterialShowcaseView.Builder(getActivity())
                                    .setTarget(makePost)
                                    .setDismissText("Got it")
                                    .setDismissTextColor(Color.parseColor("#00a76d"))
                                    .setMaskColour(Color.parseColor("#CC231F20"))
                                    .setContentText(R.string.tap_to_coach_mark_update_case_query_community).setListener(new IShowcaseListener() {
                                @Override
                                public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                    editor.edit().putBoolean("feeds_fragment_community", true).commit();
                                }

                                @Override
                                public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                }
                            })
                                    .withCircleShape()
                                    .build()
                    );
                }
            }
            if (mRecyclerView.getChildAt(0)!=null && feedProviderType != null && feedProviderType.equalsIgnoreCase("Community")) {
                if (!editor.getBoolean("feeds_fragment_community", false)) {
                    sequence.addSequenceItem(
                            new MaterialShowcaseView.Builder(getActivity())
                                    .setTarget(mRecyclerView.getChildAt(0).findViewById(R.id.tv_feeds_title))
                                    .setDismissText("Got it")
                                    .setDismissTextColor(Color.parseColor("#00a76d"))
                                    .setMaskColour(Color.parseColor("#CC231F20"))
                                    .setContentText(R.string.tap_to_coach_mark_start_reading).setListener(new IShowcaseListener() {
                                @Override
                                public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                    editor.edit().putBoolean("feeds_fragment_community", true).commit();
                                }

                                @Override
                                public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                                }
                            })
                                    .withRectangleShape(true)
                                    .build()
                    );
                }
            }
            sequence.start();
        }
    }*/

    @Override
    public void onProfileUpdate(BasicInfo basicInfo) {
        if(getActivity()!=null && isAdded()){
            loadProfileData(realmManager.getRealmBasicInfo(realm));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()){   // only at fragment screen is resumed
            fragmentResume=true;
            fragmentVisible=false;
            fragmentOnCreated=true;
            //allFeedsAdapter.notifyDataSetChanged();
        }else  if (isVisibleToUser){        // only at fragment onCreated
            fragmentResume=false;
            fragmentVisible=true;
            fragmentOnCreated=true;
        }
        else if(!isVisibleToUser && fragmentOnCreated){// only when you go out of fragment screen
            fragmentVisible=false;
            fragmentResume=false;
        }
    }

}
