package com.vam.whitecoats.ui.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Channel;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CreatePostActivity;
import com.vam.whitecoats.ui.activities.TimeLine;
import com.vam.whitecoats.ui.adapters.SectionedRecyclerViewAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.interfaces.OnChannelsLoadListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.StatelessSection;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.EndlessRecyclerOnScrollListener;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.TabsTagId;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * /**
 * <h1>Subscribed Channels Fragment(TAB 2)!</h1>
 * <p>
 * The Channel Fragment simply get all the subscribed channels from service and
 * displays to user.
 * It displays the subscribed channels in 3 sections <b>
 * a. My Communities
 * b. My Conferences and
 * c. My Subscriptions</b>
 * </p>
 * <p>
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * @author satyasarathim
 * @version 1.0
 * @since 01-06-2017
 */
public class ChannelsFragment extends Fragment implements OnChannelsLoadListener {
    public static final String TAG = ChannelsFragment.class.getSimpleName();
    private static String tabParamString;
    // Recycler adapter to render each section items.
    private SectionedRecyclerViewAdapter sectionAdapter;
    // Realm instances to perform database operation.
    private Realm realm;
    private RealmManager realmManager;
    // This variable stores the doctor id for the logged-in doctor.
    int docId;
    //Progress Dialog to display user.
    // private ProgressBar mProgressBar;
    private CustomLinearLayoutManager mLayoutManager;
    //to check the navigation from ChannelsFragment to FeedSummary.
    boolean hasFullViewSeen = false;
    /*
     * Post action constant. it will be used to compare the action in
     * {@link #onActivityResult() } of this activity.
     */
    public static final int POST_ACTION = 1212;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    RecyclerView recyclerView;
    //pull to refresh instance
    private SwipeRefreshLayout swipeContainer;
    //TextView loadingTxtVw;
    private ShimmerFrameLayout shimmerContainer;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer1, shimmerContainer2;
    private boolean channelsCallNeeded = false;
    public static boolean refreshChannelsOnSubscription = false;
    private ProgressDialog mProgressDialog;
    private ProgressDialog mProgressDialog1;
    private String tabSelected = "";
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private boolean isListExhausted;
    int page_num = 0;


    /**
     * Default constructor.
     * <p>
     * <p> Applications might create the object through empty constructor in case
     * no parameters has to pass.</p>
     * Use <b>newInstance(param1,param2)</b> method to create in case there are params to pass.
     */
    public ChannelsFragment() {
        Log.i(TAG, "ChannelsFragment()");
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelsFragment.
     */
    public static ChannelsFragment newInstance(String param1, String param2, String tabParam) {
        Log.i(TAG, "newInstance(String param1, String param2)");
        ChannelsFragment fragment = new ChannelsFragment();
        tabParamString = tabParam;
        return fragment;
    }

    /**
     * Called to do initial creation of  ChannelsFragment. This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * <p>
     * <p>Any restored child fragments will be created before the base
     * <code>Fragment.onCreate</code> method returns.</p>
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));

        /*
         * Instantiate Realm database
         */
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        /*
         * Get the logged-in doctor id
         */
        docId = realmManager.getDoc_id(realm);
        channelsCallNeeded = true;
        /*
         * close realm object in the same thread.
         */
        if (!realm.isClosed())
            realm.close();
        editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    /**
     * Called to have the ChannelsFragment instantiate its user interface view.
     * This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getString(R.string._onCreateView));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        //Instantiate the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.subscriptionCatRecyclerView);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefreshContainer);
        //  mProgressBar= (ProgressBar) view.findViewById(R.id.progressbar_cyclic);
        //loadingTxtVw= (TextView) view.findViewById(R.id.loading_label_txtvw);
        shimmerContentLayout = (ViewGroup) view.findViewById(R.id.shimmer_content_layout);
        shimmerContainer = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container);
        shimmerContainer1 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container1);
        shimmerContainer2 = (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view_container2);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (AppUtil.isConnectingToInternet(getActivity())) {
                    page_num = 0;
                    sectionAdapter.removeAllSections();
                    onChannelsLoaded();
                } else {
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        /*
         * Initialize the recycler adapter and attach to the Recycler view
         */

        sectionAdapter = new SectionedRecyclerViewAdapter();
//        recyclerView.setHasFixedSize(true);
//        mLayoutManager = new CustomLinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        setHasOptionsMenu(true);


        recyclerScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isListExhausted) {
                    return;
                }
                if (AppUtil.isConnectingToInternet(getActivity())) {
                    page_num = page_num + 1;
                    onChannelsLoaded();
                }
            }
        };
        recyclerView.addOnScrollListener(recyclerScrollListener);

        return view;
    }

    /**
     * Called when the ChannelsFragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        /*
         * If user navigates to Full view and comes back to Channels Tab then we should make service call
         * to update the tab level unread count in channels Tab.
         *
         * Once the service call happens change the value to false.
         */

        onChannelsLoaded();

        if (hasFullViewSeen) {
            hasFullViewSeen = false;
            if (AppUtil.isConnectingToInternet(getActivity())) {
                onChannelsLoaded();
            } else {
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
            }
        }

        if (refreshChannelsOnSubscription) {
            refreshChannelsOnSubscription = false;
            if (AppUtil.isConnectingToInternet(getActivity())) {
                onChannelsLoaded();
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                if (channelsCallNeeded) {
                    channelsCallNeeded = false;
                    shimmerContentLayout.setVisibility(View.VISIBLE);
                    shimmerContainer.startShimmerAnimation();
                    shimmerContainer1.startShimmerAnimation();
                    shimmerContainer2.startShimmerAnimation();
                }
                onChannelsLoaded();
            }
        }
    }

    /**
     * Called when the user selects/swipes to Channels Tab.
     * <p>
     * This is tied with {@link ViewPager.OnPageChangeListener#onPageSelected(int)} of
     * {@link com.vam.whitecoats.ui.activities.DashboardActivity}.
     * <p>
     * <p>Every time user comes into Channels Tab we fetch/call the latest subscribed channels from
     * Service and renders in UI.</p>
     */
    @Override
    public void onChannelsLoaded() {
        Log.i(TAG, "onChannelsLoaded()");
        //  mProgressBar.setVisibility(View.VISIBLE);
        //loadingTxtVw.setVisibility(View.VISIBLE);
        AppConstants.unreadChannelsList = new ArrayList<>();
        /*
         * Prepare the request json object which would be passed to the Service.
         */
        JSONObject object = new JSONObject();
        String URL = "";

        try {
            if (CommunityTabFragment.tabSelected.equalsIgnoreCase("COMMUNITY_TAB")) {
                object.put(RestUtils.TAG_USER_ID, docId);
                object.put(RestUtils.TAG_TAG_ID, TabsTagId.COMMUNITY_ORGANIZATIONS.geTagId());
                object.put(RestUtils.TAG_PAGE_NUM, page_num);
                URL = RestApiConstants.SUBSCRIBED_CHANNELS_CONTENT;
            } else if (Professional_Fragment.tabSelected.equalsIgnoreCase("PROFESSIONAL_TAB")) {
                object.put(RestUtils.TAG_USER_ID, docId);
                object.put(RestUtils.TAG_TAG_ID, TabsTagId.PROFESSIONAL_PARTNERS.geTagId());
                object.put(RestUtils.TAG_PAGE_NUM, page_num);
                URL = RestApiConstants.SUBSCRIBED_CHANNELS_CONTENT;
            } else {
                object.put(RestUtils.TAG_DOC_ID, docId);
                URL = RestApiConstants.SUBSCRIBED_CHANNELS;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
         * Hit the service with prepared request object with other params needed.
         * On success response- renders the UI with channels data
         * On Error - Displays the appropriate error message.
         */
        if (AppUtil.isConnectingToInternet(getActivity())) {
            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, URL, object.toString(), ChannelsFragment.TAG, new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if (shimmerContentLayout != null && shimmerContentLayout.getVisibility() == View.VISIBLE) {
                        shimmerContainer.stopShimmerAnimation();
                        shimmerContainer1.stopShimmerAnimation();
                        shimmerContainer2.stopShimmerAnimation();
                        shimmerContentLayout.setVisibility(View.GONE);
                    }
                    Log.i(TAG, "onSuccessResponse()");
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                    if (recyclerScrollListener != null) {
                        recyclerScrollListener.setLoadMoreStatus(true);
                    }
                    //Create a response Json object to hold the initial response of Json
                    JSONObject responseObject = null;

                    try {
                        // convert the string response into JsonObject format by passing the string into JsonObject constructor.
                        responseObject = new JSONObject(successResponse);
                        // check the response whether it's success
                        if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {

                            // remove all previously added sections to avoid duplicate/repeated adding of same sections.
//                            sectionAdapter.removeAllSections();
                            // holds different sections array
                            JSONArray channelSectionsArray = null;
                            // check if the json has <b>channels_sections</b> KEY
                            if (responseObject.optJSONObject(RestUtils.TAG_DATA).has(RestUtils.TAG_CHANNELS)) {
                                //Clear channels list before adding
                                AppConstants.unreadChannelsList.clear();
                                //Get the channels sections array
//                                channelSectionsArray = responseObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.TAG_CHANNELS_SECTIONS);
//                                //Get the channels array length
//                                int length = channelSectionsArray.length();
                                /**
                                 * Iterate the channels sections array and arrange each Section with its respective items.
                                 */
//                                for (int loopCount = 0; loopCount < length; loopCount++) {
//                                    // Get the section items object from the array
//                                    JSONObject sectionObject = channelSectionsArray.optJSONObject(loopCount);
//                                    /*
//                                     * Get the values for <b> section title and </b>
//                                     *                    <b> section items array </b>
//                                     */
//                                    String sectionTitle = sectionObject.optString(RestUtils.TAG_TITLE);
//                                JSONArray dataSectionArray = sectionObject.optJSONArray(RestUtils.TAG_CHANNELS);
                                JSONArray dataSectionArray = responseObject.optJSONObject(RestUtils.TAG_DATA).optJSONArray(RestUtils.TAG_CHANNELS);
                                int dataSectionArrLen = dataSectionArray.length();
                                if (dataSectionArrLen > 0) {
                                    /*
                                     * Add the sections into adapter with section title and section items to render.
                                     */
                                    for (int i = 0; i < dataSectionArrLen; i++) {
                                        int unreadCount = dataSectionArray.getJSONObject(i).optInt("unread_feeds", 0);
                                        boolean isSubscribed = dataSectionArray.getJSONObject(i).optBoolean(RestUtils.TAG_IS_SUBSCRIBED);
                                        if (unreadCount > 0 && isSubscribed) {
                                            AppConstants.unreadChannelsList.add(dataSectionArray.getJSONObject(i).optInt(RestUtils.CHANNEL_ID));
                                        }
                                    }
                                    sectionAdapter.addSection(new CategoriesSection("", getSectionItems(dataSectionArray)));
                                }
//                                }
                                Intent intent = new Intent("update_channels_count");
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                                // Display the List and refresh it
                               /* mProgressBar.setVisibility(View.GONE);
                                loadingTxtVw.setVisibility(View.GONE);*/
                                // Refresh the adapter UI with new data
                                sectionAdapter.notifyDataSetChanged();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // presentShowCase();
                                    }
                                }, 1000);

                            } else {
                                Toast.makeText(getActivity(), "No more channels", Toast.LENGTH_SHORT).show();
                            }
                            /*else {
                                ((BaseActionBarActivity)getActivity()).displayErrorScreen(successResponse);
                            }*/

                        } else if (responseObject.optString(RestUtils.TAG_STATUS).equalsIgnoreCase(RestUtils.TAG_ERROR)) {
                            ((BaseActionBarActivity) getActivity()).displayErrorScreen(successResponse);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    if (shimmerContentLayout.getVisibility() == View.VISIBLE) {
                        shimmerContainer.stopShimmerAnimation();
                        shimmerContainer1.stopShimmerAnimation();
                        shimmerContainer2.stopShimmerAnimation();
                        shimmerContentLayout.setVisibility(View.GONE);
                    }
                    Log.i(TAG, "onErrorResponse()");
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                    ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
                    /*
                     * Hide progress once the channels downloading are over.
                     */
                    /*mProgressBar.setVisibility(View.GONE);
                    loadingTxtVw.setVisibility(View.GONE);*/
                }
            }).sendSinglePartRequest();
        }

    }

/*
    public void presentShowCase() {
        if (AppConstants.COACHMARK_INCREMENTER > 0) {
            MaterialShowcaseView.resetSingleUse(getActivity(), "Sequence_Channels");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Sequence_Channels");
            sequence.setConfig(config);
            if (!editor.getBoolean("channels_subsricption", false) && recyclerView != null && recyclerView.getChildAt(1) != null) {
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(getActivity())
                                .setTarget(recyclerView.getChildAt(1))
                                .setDismissText("Got it")
                                .setDismissTextColor(Color.parseColor("#00a76d"))
                                .setContentText(R.string.tap_to_coach_mark_channel).setListener(new IShowcaseListener() {
                            @Override
                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                editor.edit().putBoolean("channels_subsricption", true).commit();
                            }

                            @Override
                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                            }
                        })
                                .setMaskColour(Color.parseColor("#CC231F20"))
                                .withRectangleShape()
                                .build()
                );
            }
            sequence.start();
        }
    }
*/

    /**
     * This method converts the subscribed channels JSON array into List<Channel> objects
     *
     * @param jsonArray The json array contains all channels subscribed
     * @return The List of Channels subscribed for that particular section or, null;
     */
    private List<Channel> getSectionItems(JSONArray jsonArray) {
        Log.i(TAG, "getSectionItems()");
        // get the json array length
        int arrayLength = jsonArray.length();
        //Create a List<Channel> object to store all Channels model
        List<Channel> channelsList = new ArrayList<Channel>();
        //Loop through the channels to convert into Channels object.
        for (int loopCount = 0; loopCount < arrayLength; loopCount++) {
            // Create a new Channel
            Channel channel = new Channel();
            //Get the Channel JSON object from Channels JSON array
            JSONObject jsonObject = jsonArray.optJSONObject(loopCount);
            /*
             * Check for type "Network" and if found don't add it to list.
             */
            if (jsonObject.has(RestUtils.TAG_FEED_PROVIDER_TYPE)) {
                if (!jsonObject.optString(RestUtils.TAG_FEED_PROVIDER_TYPE).equalsIgnoreCase(getString(R.string.label_network))) {
                    /*
                     * Check through all JSON keys, if KEY available add respective
                     * values to Channels Object.
                     * If KEY not available then respective types default value will be assigned.
                     */
                    if (jsonObject.has(RestUtils.CHANNEL_ID)) {
                        channel.setChannelId(jsonObject.optInt(RestUtils.CHANNEL_ID));
                    }
                    if (jsonObject.has(RestUtils.TAG_CHANNEL_LOGO)) {
                        channel.setChannelLogo(jsonObject.optString(RestUtils.TAG_CHANNEL_LOGO));
                    }
                    if (jsonObject.has(RestUtils.TAG_IS_ADMIN)) {
                        channel.setAdmin(jsonObject.optBoolean(RestUtils.TAG_IS_ADMIN));
                    }
                    if (jsonObject.has(RestUtils.TAG_FEED_PROVIDER_TYPE)) {
                        channel.setFeedProviderType(jsonObject.optString(RestUtils.TAG_FEED_PROVIDER_TYPE));
                    }
                    if (jsonObject.has(RestUtils.TAG_FEED_PROVIDER_SUBTYPE)) {
                        channel.setFeedProviderSubType(jsonObject.optString(RestUtils.TAG_FEED_PROVIDER_SUBTYPE));
                    }
                    if (jsonObject.has(RestUtils.FEED_PROVIDER_NAME)) {
                        channel.setFeedProviderName(jsonObject.optString(RestUtils.FEED_PROVIDER_NAME));
                    }
                    if (jsonObject.has(RestUtils.TAG_MEMBERS_COUNT)) {
                        channel.setMembersCount(jsonObject.optInt(RestUtils.TAG_MEMBERS_COUNT));
                    }
                    if (jsonObject.has(RestUtils.TAG_UNREAD_FEEDS)) {
                        channel.setUnreadFeeds(jsonObject.optInt(RestUtils.TAG_UNREAD_FEEDS));
                    }
                    if (jsonObject.has(RestUtils.TAG_LATEST_FEED_TIME)) {
                        channel.setLatestFeedTime(jsonObject.optLong(RestUtils.TAG_LATEST_FEED_TIME));
                    }
                    if (jsonObject.has(RestUtils.TAG_IS_SUBSCRIBED)) {
                        channel.setSubscribed(jsonObject.optBoolean(RestUtils.TAG_IS_SUBSCRIBED));
                    }
                    if (jsonObject.has(RestUtils.TAG_IS_MANDATORY)) {
                        channel.setMandatory(jsonObject.optBoolean(RestUtils.TAG_IS_MANDATORY));
                    }
                    // Add Channels object into List<Channel>
                    channelsList.add(channel);
                }
            }
        }
        return channelsList;
    }

    /**
     * Implementation of {@link StatelessSection} that
     * represents each items as a {@link com.vam.whitecoats.ui.interfaces.Section}.
     * <p>
     * <p>This is the high level implementation of {@link RecyclerView.Adapter}
     * where we are simply adding section Title and sectionItems List.</p>
     * <p>
     * <p>When using CategoriesSection the host Activity/Fragment must add section items list and section Title.
     * </p>
     * <p>
     * <p>Subclasses only need to implement {@link #onBindItemViewHolder(RecyclerView.ViewHolder, int)}
     * and {@link #onBindHeaderViewHolder(RecyclerView.ViewHolder)} to have a working adapter.</p>
     */
    class CategoriesSection extends StatelessSection {
        // To hold section Title
        String mSectionTitle;
        // To hold list of section items
        List<Channel> mSectionItems;

        /**
         * Constructor to create CategoriesSection by passing title & List<Channel> .
         * <p>
         * Passes the respective layouts to super class{@link StatelessSection} to render Header as well as
         * it's section items.
         *
         * @param sectionTitle
         * @param sectionItems
         */
        public CategoriesSection(String sectionTitle, List<Channel> sectionItems) {
            super(R.layout.subsciption_cat_section_header, R.layout.subscription_cat_item);

            this.mSectionTitle = sectionTitle;
            this.mSectionItems = sectionItems;
        }

        @Override
        public int getContentItemsTotal() {
            return mSectionItems.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;
            // Get the Channel object based on position
            final Channel channel = mSectionItems.get(position);
            /*
             * If the feed type is #Content
             *  1 . make a post button
             *  2 . Members count
             */

            if (channel.getFeedProviderType().equalsIgnoreCase(RestUtils.CONTENT)) {
                /*
                 * View.GONE- Makes the view invisible as well as free-up the space in UI
                 * View.INVISIBLE - Makes the view invisible but the view still occupy the UI space.
                 *
                 * <p> Based on different UI requirements we use one of the property, here also we need to use
                 * different properties for different UI elements as per our requirement. </p>
                 *
                 */
                itemHolder.createPostLayout.setVisibility(View.GONE);
                itemHolder.communityMembers.setVisibility(View.GONE);
                if (!channel.getIsMandatory()) {
                    itemHolder.follow_unFollow_btn.setVisibility(View.VISIBLE);
                } else {
                    itemHolder.follow_unFollow_btn.setVisibility(View.GONE);
                }

            }
            /*
             * (or) If user is not Admin in this particular #Community then hide
             *  1. make a post button
             */
            if (channel.isAdmin()) {
                itemHolder.createPostLayout.setVisibility(View.VISIBLE);
            } else {
                itemHolder.createPostLayout.setVisibility(View.GONE);
            }
            // set channel logo through Glide, if logo not found set error place holder.
            AppUtil.loadImageUsingGlide(getActivity(), channel.getChannelLogo(), itemHolder.communityLogo, R.drawable.default_communitypic);
            // Set community name
            itemHolder.communityName.setText(channel.getFeedProviderName());
            if (channel.getMembersCount() == 1) {
                itemHolder.communityMembers.setText(AppUtil.suffixNumber(channel.getMembersCount()) + " member");
            } else {
                itemHolder.communityMembers.setText(AppUtil.suffixNumber(channel.getMembersCount()) + " members");
            }
            if (channel.getIsSubscribed()) {
                if (channel.getUnreadFeeds() > 0) {
                    itemHolder.postCount.setVisibility(View.VISIBLE);
                    if (channel.getUnreadFeeds() > 99) {
                        itemHolder.postCount.setText("99+");
                    } else {
                        itemHolder.postCount.setText(channel.getUnreadFeeds() + "");
                    }
                } else {
                    itemHolder.postCount.setVisibility(View.GONE);
                }
            } else {
                itemHolder.postCount.setVisibility(View.GONE);
            }

            if (!channel.getIsSubscribed()) {
                itemHolder.follow_unFollow_btn.setText("FOLLOW");
                itemHolder.follow_unFollow_btn.setBackgroundResource(R.drawable.accept_button);
                itemHolder.follow_unFollow_btn.setTextColor(getResources().getColor(R.color.white));
            } else {
                itemHolder.follow_unFollow_btn.setText("UNFOLLOW");
                itemHolder.follow_unFollow_btn.setBackgroundResource(R.drawable.button_grey);
                itemHolder.follow_unFollow_btn.setTextColor(getResources().getColor(R.color.black_radio));

            }
            /*
             * If Create post button clicked, navigate into MakeAPost screen
             * by passing required extra params.
             */
            itemHolder.createPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (AppUtil.getUserVerifiedStatus() == 3 || AppUtil.getCommunityUserVerifiedStatus()) {
                            /*
                             *Create JSON object to pass to CreatePost
                             */
                            JSONObject channelObj = new JSONObject();
                            channelObj.put(RestUtils.CHANNEL_ID, channel.getChannelId());
                            channelObj.put(RestUtils.FEED_PROVIDER_NAME, channel.getFeedProviderName());
                            channelObj.put(RestUtils.TAG_FEED_PROVIDER_TYPE, channel.getFeedProviderType());
                            //Start Activity
                            Intent in = new Intent(getActivity(), CreatePostActivity.class);
                            in.putExtra(RestUtils.NAVIGATATION, "Channels");
                            in.putExtra(RestUtils.KEY_SELECTED_CHANNEL, channelObj.toString());
                            startActivityForResult(in, POST_ACTION);
                        } else if (AppUtil.getUserVerifiedStatus() == 1) {
                            AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_not_uploaded));
                        } else if (AppUtil.getUserVerifiedStatus() == 2) {
                            AppUtil.AccessErrorPrompt(getActivity(), getActivity().getString(R.string.mca_uploaded_but_not_verified));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Make the navigation value true and in #OnResume use it to call the service
                    if(channel==null){
                        return;
                    }
                    boolean navigateToTimeLine = true;
                    if (channel.getFeedProviderType().equalsIgnoreCase(RestUtils.CONTENT)) {
                        if (!channel.getIsSubscribed()) {
                            navigateToTimeLine = false;
                        }
                    }
                    if (navigateToTimeLine) {
                        hasFullViewSeen = true;
                        Intent intent = new Intent(getActivity(), TimeLine.class);
                        intent.putExtra(RestUtils.FEED_PROVIDER_NAME, channel.getFeedProviderName());
                        intent.putExtra(RestUtils.CHANNEL_ID, channel.getChannelId());
                        intent.putExtra(RestUtils.TAG_IS_ADMIN, channel.isAdmin());
                        intent.putExtra(RestUtils.TAG_FEED_PROVIDER_TYPE, channel.getFeedProviderType());
                        intent.putExtra(RestUtils.TAG_FEED_PROVIDER_SUBTYPE, channel.getFeedProviderSubType());
                        startActivity(intent);
                    }
                }
            });

            itemHolder.follow_unFollow_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtil.isConnectingToInternet(getActivity())) {
                        //sendSubscribeUnsubscribeRequest(!channel.getIsSubscribed(),channel.getChannelId());
                        boolean isSubscribedValue = !channel.getIsSubscribed();
                        JSONObject subscribeJsonRequest = new JSONObject();
                        try {
                            subscribeJsonRequest.put(RestUtils.TAG_USER_ID, docId);
                            subscribeJsonRequest.put(RestUtils.CHANNEL_ID, channel.getChannelId());
                            subscribeJsonRequest.put(RestUtils.TAG_IS_SUBSCRIBED, isSubscribedValue);
                            showProgress();

                            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.SUBSCRIPTION_SERVICE, subscribeJsonRequest.toString(), "SUBSCRIPTION_SERVICE", new OnReceiveResponse() {
                                @Override
                                public void onSuccessResponse(String successResponse) {
                                    hideProgress();
                                    channel.setSubscribed(isSubscribedValue);
                                    if (channel.getUnreadFeeds() > 0) {
                                        if (isSubscribedValue) {
                                            AppConstants.unreadChannelsList.add(channel.getChannelId());
                                        } else {
                                            AppConstants.unreadChannelsList.remove(Integer.valueOf(channel.getChannelId()));
                                        }
                                        Intent intent = new Intent("update_channels_count");
                                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                                    }

                                    if (sectionAdapter != null) {
                                        sectionAdapter.notifyDataSetChanged();
                                    }
                                    DashboardUpdatesFragment.dashboardRefreshOnSubscription = true;
                                }

                                @Override
                                public void onErrorResponse(String errorResponse) {
                                    hideProgress();
                                    if (errorResponse != null) {
                                        //revert back to pre stage
                                        try {
                                            if (!errorResponse.isEmpty()) {
                                                JSONObject jsonObject = new JSONObject(errorResponse);
                                                String errorMessage = jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE);
                                                if (errorMessage != null && !errorMessage.isEmpty()) {
                                                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
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
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.sectionTitle.setText(mSectionTitle);
        }
    }

  /*  private void sendSubscribeUnsubscribeRequest(boolean isSubscribedValue, int channelId) {

        JSONObject subscribeJsonRequest = new JSONObject();
        try {
            subscribeJsonRequest.put(RestUtils.TAG_USER_ID,docId);
            subscribeJsonRequest.put(RestUtils.CHANNEL_ID,channelId);
            subscribeJsonRequest.put(RestUtils.TAG_IS_SUBSCRIBED,isSubscribedValue);

            new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.SUBSCRIPTION_SERVICE, subscribeJsonRequest.toString(), "SUBSCRIPTION_SERVICE", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();


                }

                @Override
                public void onErrorResponse(String errorResponse) {

                }
            }).sendSinglePartRequest();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/

    /**
     * ViewHolder for section header
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView sectionTitle;

        public HeaderViewHolder(View view) {
            super(view);
            sectionTitle = (TextView) view.findViewById(R.id.sectionTitleTxtVw);
        }
    }

    /**
     * ViewHolder for section items
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final Button follow_unFollow_btn;
        LinearLayout createPostLayout;
        private final ImageView communityLogo;
        private final TextView communityName;
        private final TextView communityMembers;
        private final TextView postCount;
        private final TextView createPost;

        public ItemViewHolder(View view) {
            super(view);

            rootView = (LinearLayout) view.findViewById(R.id.rootView);
            createPostLayout = (LinearLayout) view.findViewById(R.id.createPostLayout);
            communityLogo = (ImageView) view.findViewById(R.id.communityLogoImgVw);
            communityName = (TextView) view.findViewById(R.id.communityNameTxtVw);
            communityMembers = (TextView) view.findViewById(R.id.communityMembersTxtVw);
            postCount = (TextView) view.findViewById(R.id.postCountTxtVw);
            createPost = (TextView) view.findViewById(R.id.createPostButton);
            follow_unFollow_btn = (Button) view.findViewById(R.id.follow_unFollow_btn);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == POST_ACTION) {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                onChannelsLoaded();
            }
        }

    }


    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public synchronized void showProgress() {
        //if (progress == null && progress.getActivity() == null) {
        try {
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
    }

}
