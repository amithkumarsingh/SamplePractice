package com.vam.whitecoats.ui.activities;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.MediaFilesListAdapter;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class MediaFilesActivity extends BaseActionBarActivity {

    private RecyclerView mediaFilesRecycler;
    private GridLayoutManager gridLayoutManger;
    private ArrayList<JSONObject> mediaFilesList=new ArrayList<>();
    private ArrayList<String> titlesList=new ArrayList<>();
    private int channelId;
    private String medialTypes;
    private MediaFilesListAdapter mediaFilesAdapter;
    private JSONArray mediaTypesArray=new JSONArray();
    private Realm realm;
    private RealmManager realmManager;
    private int doctorId;
    private String timeStamp;
    private JSONObject requestObj;
    private String sectionName="";
    private int sectionCount;
    private TextView mTitleTextView;
    private int fileDataCount=0;
    private SwipeRefreshLayout mediaSwipeContainer;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_files);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);

        gridLayoutManger = new GridLayoutManager(MediaFilesActivity.this, 3);
        mediaSwipeContainer = (SwipeRefreshLayout)findViewById(R.id.mediaSwipeContainer);
        mediaFilesRecycler=(RecyclerView)findViewById(R.id.media_files_recycler);
        mediaFilesRecycler.setHasFixedSize(true);
        mediaFilesRecycler.setLayoutManager(gridLayoutManger);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            channelId=bundle.getInt(RestUtils.CHANNEL_ID);
            medialTypes=bundle.getString(RestUtils.TAG_MEDIA_TYPES);
            sectionName=bundle.getString(RestUtils.TAG_SECTION_NAME);
            sectionCount=bundle.getInt(RestUtils.TAG_SECTION_COUNT);
            try {
                mediaTypesArray = new JSONArray(medialTypes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mTitleTextView.setText(sectionName +" ("+sectionCount+")");
        upshotEventData(0,channelId,0,"","MediaTimeline","","", "",false);
        mediaFilesAdapter=new MediaFilesListAdapter(MediaFilesActivity.this,mediaFilesList,mediaFilesRecycler,sectionName,sectionCount);
        mediaFilesRecycler.setAdapter(mediaFilesAdapter);
        gridLayoutManger.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                switch(mediaFilesAdapter.getItemViewType(position)){
                    case MediaFilesListAdapter.VIEW_ITEM:
                        return 1;
                    case MediaFilesListAdapter.VIEW_ITEM_WIHT_TITLE:
                    case MediaFilesListAdapter.VIEW_PROG:
                        return 3;
                    default:
                        return 1;
                }

            }
        });
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);
        doctorId = realmManager.getDoc_id(realm);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        getMediaFiles(false);
        mediaFilesAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mediaFilesList.add(null);
                mediaFilesAdapter.notifyItemInserted(mediaFilesList.size());
                getMediaFiles(true);
            }
        });

        mediaSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(MediaFilesActivity.this)) {
                    fileDataCount=0;
                    timeStamp=null;
                    getMediaFiles(false);
                } else {
                    if (mediaSwipeContainer != null) {
                        mediaSwipeContainer.setRefreshing(false);
                    }
                }
            }
        });
        mediaSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
    }

    private void getMediaFiles(final boolean isFromLoadMore) {
        requestObj=new JSONObject();
        try {
            requestObj.put(RestUtils.TAG_DOC_ID,doctorId);
            if(timeStamp!=null){
                if(timeStamp.equalsIgnoreCase("0")){
                    if(isFromLoadMore) {
                        mediaFilesList.remove(mediaFilesList.size() - 1);
                        mediaFilesAdapter.notifyItemRemoved(mediaFilesList.size());
                    }
                    return;
                }
                requestObj.put("timestamp",timeStamp);
            }
            requestObj.put(RestUtils.TAG_MEDIA_TYPES,mediaTypesArray);
            JSONArray mediaChannelJsonArrayrequest = new JSONArray();
            mediaChannelJsonArrayrequest.put(channelId);
            requestObj.put(RestUtils.CHANNEL_IDS, mediaChannelJsonArrayrequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(AppUtil.isConnectingToInternet(this)){
            if(!isFromLoadMore) {
                showProgress();
            }
            new VolleySinglePartStringRequest(this, Request.Method.POST, RestApiConstants.MEDIA_SECTION_SERVICE, requestObj.toString(), "GET_MEDIAL_FILES", new OnReceiveResponse() {
                @Override
                public void onSuccessResponse(String successResponse) {
                    if(isFromLoadMore) {
                        mediaFilesList.remove(mediaFilesList.size() - 1);
                        mediaFilesAdapter.notifyItemRemoved(mediaFilesList.size());
                    }
                    else {
                        mediaFilesList.clear();
                        titlesList.clear();
                        hideProgress();
                    }
                    if (mediaSwipeContainer != null) {
                        mediaSwipeContainer.setRefreshing(false);
                    }
                    JSONObject responseObj = null;
                    try {
                        responseObj = new JSONObject(successResponse);
                        if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                            if(responseObj.has(RestUtils.TAG_DATA)){
                                JSONObject currentObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                                if(timeStamp==null){
                                    sectionCount=currentObj.optInt("count");
                                    mTitleTextView.setText(sectionName +" ("+sectionCount+")");
                                }
                                timeStamp=currentObj.optString("timestamp");
                                JSONArray medialJsonArray = currentObj.optJSONArray("mediaSections");
                                for(int i=0;i<medialJsonArray.length();i++){
                                    if(!titlesList.contains(medialJsonArray.optJSONObject(i).optString(RestUtils.TAG_TITLE))){
                                        titlesList.add(medialJsonArray.optJSONObject(i).optString(RestUtils.TAG_TITLE));
                                        JSONObject titleObj=new JSONObject();
                                        titleObj.put("title",medialJsonArray.optJSONObject(i).optString(RestUtils.TAG_TITLE));
                                        mediaFilesList.add(titleObj);
                                    }
                                    JSONArray fileData = medialJsonArray.optJSONObject(i).optJSONArray("fileData");
                                    for(int j=0;j<fileData.length();j++){
                                        fileDataCount++;
                                        fileData.optJSONObject(j).put(RestUtils.TAG_COUNT,fileDataCount);
                                        mediaFilesList.add(fileData.optJSONObject(j));
                                    }

                                }
                            }
                            if(isFromLoadMore){
                                mediaFilesAdapter.notifyItemInserted(mediaFilesList.size());
                            }
                            else {
                                mediaFilesAdapter.notifyDataSetChanged();
                            }
                            mediaFilesAdapter.setLoaded();
                        }
                        else if(responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)){
                            displayErrorScreen(successResponse);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(String errorResponse) {
                    if(isFromLoadMore) {
                        mediaFilesList.remove(mediaFilesList.size() - 1);
                        mediaFilesAdapter.notifyItemRemoved(mediaFilesList.size());
                    }
                    if (mediaSwipeContainer != null) {
                        mediaSwipeContainer.setRefreshing(false);
                    }
                    hideProgress();
                    displayErrorScreen(errorResponse);
                }
            }).sendSinglePartRequest();
        }
        else{
            if(isFromLoadMore) {
                mediaFilesList.remove(mediaFilesList.size() - 1);
                mediaFilesAdapter.notifyItemRemoved(mediaFilesList.size());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("ChannelID",channelId);
                    AppUtil.logUserUpShotEvent("MediaTimelineBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        mediaFilesAdapter.pauseAudio();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                jsonObject.put("ChannelID",channelId);
                AppUtil.logUserUpShotEvent("MediaTimelineDeviceBackTapped",AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }
}
