package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.adapters.ContentChannelsAdapter;
import com.vam.whitecoats.ui.interfaces.OnChannelsLoadListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
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

public class ContentChannelsFragment extends Fragment implements OnChannelsLoadListener {
    public static final String TAG = ContentChannelsFragment.class.getSimpleName();
    private static String tabParamString;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private ViewGroup shimmerContentLayout;
    private ShimmerFrameLayout shimmerContainer;
    private ShimmerFrameLayout shimmerContainer1;
    private ShimmerFrameLayout shimmerContainer2;
    private static int page_num=0;
    private ContentChannelsAdapter sectionAdapter;
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private boolean isListExhausted;
    private Realm realm;
    private RealmManager realmManager;
    private int docId;
    private ArrayList<JSONObject> channelsList=new ArrayList<>();
    private boolean hasFullViewSeen=false;
    public static boolean refreshChannelsOnSubscription=false;
    private LinearLayoutManager linearLayoutManager;
    private boolean loading=false;

    public static ContentChannelsFragment newInstance(String s, String s1, String community_tab) {
        Log.i(TAG, "newInstance(String param1, String param2)");
        ContentChannelsFragment fragment = new ContentChannelsFragment();
        tabParamString = community_tab;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);

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

        sectionAdapter = new ContentChannelsAdapter(getActivity(),channelsList,hasFullViewSeen,docId);
//       recyclerView.setHasFixedSize(true);
//        mLayoutManager = new CustomLinearLayoutManager(getActivity());
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(mLayoutManager);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        setHasOptionsMenu(true);


        recyclerScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isListExhausted|| !AppUtil.isConnectingToInternet(getActivity())) {
                    return;
                }

                int toatlcount = linearLayoutManager.getItemCount();
                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                if (!loading) {
                    if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1)) {
                        channelsList.add(null);
                        sectionAdapter.notifyItemInserted(channelsList.size());
                        page_num = page_num + 1;
                        onChannelsLoaded();
                    } else {
                        loading = false;
                    }
                }
               /* if (AppUtil.isConnectingToInternet(getActivity())) {
                    channelsList.add(null);
                    sectionAdapter.notifyItemInserted(channelsList.size());
                    page_num = page_num + 1;
                    onChannelsLoaded();
                }*/
            }
        };
        recyclerView.addOnScrollListener(recyclerScrollListener);
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * Instantiate Realm database
         */
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        /*
         * Get the logged-in doctor id
         */
        docId = realmManager.getDoc_id(realm);
        /*
         * close realm object in the same thread.
         */
        if (!realm.isClosed()) {
            realm.close();
        }
        //editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        /*
         * If user navigates to Full view and comes back to Channels Tab then we should make service call
         * to update the tab level unread count in channels Tab.
         *
         * Once the service call happens change the value to false.
         */

        if (AppUtil.isConnectingToInternet(getActivity())) {
            shimmerContentLayout.setVisibility(View.VISIBLE);
            shimmerContainer.startShimmerAnimation();
            shimmerContainer1.startShimmerAnimation();
            shimmerContainer2.startShimmerAnimation();
            onChannelsLoaded();

        }

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
    public void onDestroy() {
        super.onDestroy();
        page_num=0;
    }

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
                    if(channelsList.contains(null)){
                        channelsList.remove(null);
                        sectionAdapter.notifyDataSetChanged();
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
                                if(page_num==0){
                                    channelsList.clear();
                                }
                                if (dataSectionArrLen > 0) {
                                    isListExhausted=false;
                                    /*
                                     * Add the sections into adapter with section title and section items to render.
                                     */
                                    for (int i = 0; i < dataSectionArrLen; i++) {
                                        int unreadCount = dataSectionArray.getJSONObject(i).optInt("unread_feeds", 0);
                                        boolean isSubscribed = dataSectionArray.getJSONObject(i).optBoolean(RestUtils.TAG_IS_SUBSCRIBED);
                                        channelsList.add(dataSectionArray.optJSONObject(i));
                                        if(unreadCount>0&&isSubscribed) {
                                            AppConstants.unreadChannelsList.add(dataSectionArray.getJSONObject(i).optInt(RestUtils.CHANNEL_ID));
                                        }
                                    }
                                    //sectionAdapter.addSection(new ChannelsFragment.CategoriesSection("", getSectionItems(dataSectionArray)));
                                }else{
                                    isListExhausted=true;
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
}
