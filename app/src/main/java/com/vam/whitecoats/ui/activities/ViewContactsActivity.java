package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.NetworkSearchAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.interfaces.NavigateScreenListener;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class ViewContactsActivity extends BaseActionBarActivity implements NavigateScreenListener {

    private TextView tv_noresults, mTitleTextView;
    public static ArrayList<ContactsInfo> searchinfo;
    private SwipeRefreshLayout swipeContainer;
    private int profileid=0;
    private RecyclerView userConnectsList;
    private RealmManager realmManager;
    private Realm mRealm;
    private int userid;
    private long lastRecordTime = 0;
    private ArrayList<ContactsInfo> connectsResults = new ArrayList<>();
    private NetworkSearchAdapter otherConnectsListAdapter;
    private CustomLinearLayoutManager mLayoutManager;
    private JSONObject requestObj;
    private int pageIndex = 0,recordsCount=0;
    private String navigatedFrom="";
    private boolean is_following;
    private boolean isOtherProfileFollowing;
    private boolean customBackButton=false;
    private String otherDocUUID;
    /*Refactoring the deprecated startActivityForResults*/
    private ActivityResultLauncher<Intent> launcherInviteOrVisitProfileResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_connects_layout);
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_reg, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_edit);
        TextView next_button = (TextView) mCustomView.findViewById(R.id.next_button);
        realmManager = new RealmManager(this);
        mRealm = Realm.getDefaultInstance();
        userid = realmManager.getDoc_id(mRealm);
        // next_button.getLayoutParams().width = 0;
        next_button.setVisibility(View.GONE);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launcherInviteOrVisitProfileResults=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //request code == 1
                    int resultCode = result.getResultCode();
                    try {
                        if (resultCode == 1) {
                                if (requestObj != null) {
                                    recordsCount=0;
                                    lastRecordTime = 0;
                                    pageIndex = 0;
                                    JSONObject requestData=new JSONObject();
                                    String restURL=RestApiConstants.GET_VISITED_CONTACTS;
                                    if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                                        requestData.put(RestUtils.TAG_USER_ID, userid);
                                        requestData.put("is_following", is_following);
                                        if(profileid!=0){
                                            requestData.put(RestUtils.TAG_OTHER_USER_ID, profileid);
                                        }
                                        restURL=RestApiConstants.GET_FOLLOW_LIST;
                                        requestData.put(RestUtils.TAG_COUNT, recordsCount);
                                    }else {
                                        requestData.put(RestUtils.TAG_DOC_ID, userid);
                                        requestData.put(RestUtils.TAG_OTHER_DOC_ID, profileid);
                                        requestData.put(RestUtils.TAG_TIME_KEY, lastRecordTime);
                                        requestData.put(RestUtils.PG_NUM, pageIndex);
                                    }
                                    getOtherUserConnects(requestData.toString(), false, true,restURL);
                                }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        //end
        _initializeUI();
        if (getIntent().getExtras() != null) {
            String suffixText=" Connects";
            if(getIntent().getExtras().containsKey("profileid")) {
                profileid = getIntent().getExtras().getInt("profileid");
            }
            if(getIntent().getExtras().containsKey(RestUtils.NAVIGATATE_FROM)){
                navigatedFrom=getIntent().getExtras().getString(RestUtils.NAVIGATATE_FROM);
                if(navigatedFrom.equalsIgnoreCase("Followers")){
                    suffixText=" Followers";
                    is_following=false;
                }else if(navigatedFrom.equalsIgnoreCase("Following")){
                    suffixText=" Following";
                    is_following=true;
                }
            }
            if(getIntent().getExtras().containsKey("isOtherProfileFollowing")){
                isOtherProfileFollowing=getIntent().getExtras().getBoolean("isOtherProfileFollowing");
            }
            mTitleTextView.setText(getIntent().getExtras().getString("doc_name") + suffixText);
        }
        String doc_name = getIntent().getExtras().getString("doc_name");
        String navigated_From="";
        if(navigatedFrom.equalsIgnoreCase("Followers")){
            if(!doc_name.isEmpty()){
                navigated_From="OtherUserFollowers";
            }else {
                navigated_From = "Followers";
            }

        }else if(navigatedFrom.equalsIgnoreCase("following")){
            if(!doc_name.isEmpty()){
                navigated_From="OtherUserFollowing";
            }else {
                navigated_From="Following";
            }
        }else{
            navigated_From="OtherUserConnects";
        }
        otherDocUUID=getIntent().getExtras().getString("otherDocUUID");
        if(otherDocUUID!=null) {
            upshotEventData(0, 0, 0, otherDocUUID, navigated_From, "", "", "",false);
        }else{
            upshotEventData(0, 0, 0, "", navigated_From, "", "", "",false);

        }
        searchinfo = new ArrayList<ContactsInfo>();
        String restURL=RestApiConstants.GET_VISITED_CONTACTS;
        requestObj = new JSONObject();
        try {
            if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                requestObj.put(RestUtils.TAG_USER_ID, userid);
                requestObj.put("is_following", is_following);
                if(profileid!=0){
                    requestObj.put(RestUtils.TAG_OTHER_USER_ID, profileid);
                }
                restURL=RestApiConstants.GET_FOLLOW_LIST;
                requestObj.put(RestUtils.TAG_COUNT, recordsCount);
            }else {
                requestObj.put(RestUtils.TAG_DOC_ID, userid);
                requestObj.put(RestUtils.TAG_OTHER_DOC_ID, profileid);
                requestObj.put(RestUtils.TAG_TIME_KEY, lastRecordTime);
                requestObj.put(RestUtils.PG_NUM, pageIndex);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        getOtherUserConnects(requestObj.toString(), false, false,restURL);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                JSONObject requestData = new JSONObject();
                if (AppUtil.isConnectingToInternet(mContext)) {
                    try {
                        String restURL=RestApiConstants.GET_VISITED_CONTACTS;
                        lastRecordTime = 0;
                        pageIndex = 0;
                        recordsCount=0;
                        if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                            requestData.put(RestUtils.TAG_USER_ID, userid);
                            requestData.put("is_following", is_following);
                            if(profileid!=0){
                                requestData.put(RestUtils.TAG_OTHER_USER_ID, profileid);
                            }
                            restURL=RestApiConstants.GET_FOLLOW_LIST;
                            requestData.put(RestUtils.TAG_COUNT, recordsCount);
                        }else {
                            requestData.put(RestUtils.TAG_DOC_ID, userid);
                            requestData.put(RestUtils.TAG_OTHER_DOC_ID, profileid);
                            requestData.put(RestUtils.TAG_TIME_KEY, lastRecordTime);
                            requestData.put(RestUtils.PG_NUM, pageIndex);
                        }
                        getOtherUserConnects(requestData.toString(), false, true,restURL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ViewContactsActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                    if (swipeContainer != null) {
                        swipeContainer.setRefreshing(false);
                    }
                }
            }
        });
        otherConnectsListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                JSONObject requestData = new JSONObject();
                if (AppUtil.isConnectingToInternet(mContext)) {
                    try {
                        recordsCount=connectsResults.size();
                        connectsResults.add(null);
                        otherConnectsListAdapter.notifyItemInserted(connectsResults.size());
                        pageIndex++;
                        String restURL=RestApiConstants.GET_VISITED_CONTACTS;
                        if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                            requestData.put(RestUtils.TAG_USER_ID, userid);
                            requestData.put("is_following", is_following);
                            if(profileid!=0){
                                requestData.put(RestUtils.TAG_OTHER_USER_ID, profileid);
                            }
                            restURL=RestApiConstants.GET_FOLLOW_LIST;
                            requestData.put(RestUtils.TAG_COUNT, recordsCount);
                        }else {
                            requestData.put(RestUtils.TAG_DOC_ID, userid);
                            requestData.put(RestUtils.TAG_OTHER_DOC_ID, profileid);
                            requestData.put(RestUtils.TAG_TIME_KEY, lastRecordTime);
                            requestData.put(RestUtils.PG_NUM, pageIndex);
                        }

                        getOtherUserConnects(requestData.toString(), true, false,restURL);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void setCurrentActivity() {

    }

    private void getOtherUserConnects(String request, boolean isFromScroll, final boolean refreshData,String restURL) {
        showProgress();
        new VolleySinglePartStringRequest(ViewContactsActivity.this, Request.Method.POST,restURL, request, "VIEW_OTHER_USER_CONNECTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgress();
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                if (connectsResults.contains(null)) {
                    connectsResults.remove(connectsResults.size() - 1);
                    otherConnectsListAdapter.notifyItemRemoved(connectsResults.size());
                }
                if (successResponse != null) {
                    JSONObject responseObj = null;
                    try {
                        responseObj = new JSONObject(successResponse);
                        if (responseObj == null) {
                            return;
                        }
                        if (refreshData) {
                            connectsResults.clear();
                        }
                        String status = responseObj.optString(RestUtils.TAG_STATUS);
                        if (status.equalsIgnoreCase(RestUtils.TAG_SUCCESS)) {
                            int len=0;
                            JSONObject dataObj = responseObj.optJSONObject(RestUtils.TAG_DATA);
                            if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                                JSONArray followList;
                                if(navigatedFrom.equalsIgnoreCase("Followers")){
                                    followList=dataObj.optJSONArray("followers");
                                }else{
                                    followList=dataObj.optJSONArray("followings");
                                }
                                len=followList.length();
                                for(int j=0;j<len;j++){
                                    JSONObject searchResult = followList.optJSONObject(j);
                                    ContactsInfo contactsInfo = new ContactsInfo();
                                    contactsInfo.setDoc_id(searchResult.optInt(RestUtils.TAG_USER_ID));
                                    contactsInfo.setFull_name(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                    contactsInfo.setName(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                    String speciality=searchResult.optJSONArray("specialities")!=null?searchResult.optJSONArray("specialities").optString(0,""):"";
                                    contactsInfo.setSpeciality(speciality);
                                    String specialist=searchResult.optJSONArray("specialists")!=null?searchResult.optJSONArray("specialists").optString(0,""):"";
                                    contactsInfo.setSpecialist(specialist);
                                    contactsInfo.setSubSpeciality(searchResult.optString(RestUtils.TAG_SUB_SPLTY));
                                    contactsInfo.setLocation(searchResult.optString(RestUtils.TAG_LOCATION));
                                    contactsInfo.setCnt_email(searchResult.optString(RestUtils.TAG_CNT_EMAIL));
                                    contactsInfo.setCnt_num(searchResult.optString(RestUtils.TAG_CNT_NUM));
                                    contactsInfo.setNetworkStatus(searchResult.optString(RestUtils.TAG_CONNECT_STATUS));
                                    contactsInfo.setProfile_pic_original_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    contactsInfo.setPic_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    contactsInfo.setProfile_pic_small_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                    contactsInfo.setUserSalutation(searchResult.optString(RestUtils.TAG_USER_SALUTAION));
                                    contactsInfo.setUserTypeId(searchResult.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    contactsInfo.setPhno_vis(searchResult.optString(RestUtils.TAG_CNNTMUNVIS));
                                    contactsInfo.setEmail_vis(searchResult.optString(RestUtils.TAG_CNNTEMAILVIS));
                                    if(navigatedFrom.equalsIgnoreCase("Following")&& !isOtherProfileFollowing) {
                                        contactsInfo.setFollow_status(searchResult.optString("follow_status"));
                                    }
                                    contactsInfo.setCardPopupNotNeeded(true);
                                    connectsResults.add(contactsInfo);
                                }
                            }else {
                                JSONArray searchResults = dataObj.optJSONArray(RestUtils.TAG_CONTACTS_RESULTS);
                                lastRecordTime = dataObj.optLong(RestUtils.TAG_TIME_KEY);
                                len = searchResults.length();
                                for (int i = 0; i < len; i++) {
                                    JSONObject searchResult = searchResults.optJSONObject(i);
                                    ContactsInfo contactsInfo = new ContactsInfo();
                                    contactsInfo.setDoc_id(searchResult.optInt(RestUtils.TAG_DOC_ID));
                                    contactsInfo.setFull_name(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                    contactsInfo.setName(searchResult.optString(RestUtils.TAG_USER_FULL_NAME));
                                    contactsInfo.setDegree(searchResult.optString(RestUtils.TAG_DEGREES));
                                    contactsInfo.setWorkplace(searchResult.optString(RestUtils.TAG_WORKPLACE));
                                    contactsInfo.setQb_userid(searchResult.optInt(RestUtils.TAG_QB_USER_ID));
                                    contactsInfo.setSpeciality(searchResult.optString(RestUtils.TAG_SPLTY));
                                    contactsInfo.setSpecialist(searchResult.optString(RestUtils.TAG_SPLTY));
                                    contactsInfo.setSubSpeciality(searchResult.optString(RestUtils.TAG_SUB_SPLTY));
                                    contactsInfo.setLocation(searchResult.optString(RestUtils.TAG_LOCATION));
                                    contactsInfo.setCnt_email(searchResult.optString(RestUtils.TAG_CNT_EMAIL));
                                    contactsInfo.setCnt_num(searchResult.optString(RestUtils.TAG_CNT_NUM));
                                    contactsInfo.setProfile_pic_name(searchResult.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                    contactsInfo.setNetworkStatus(searchResult.optString(RestUtils.TAG_CONNECT_STATUS));
                                    contactsInfo.setDesignation(searchResult.optString(RestUtils.TAG_DESIGNATION));
                                    contactsInfo.setCommunity_designation(searchResult.optString(RestUtils.COMMUNITY_DESIGNATION));
                                    contactsInfo.setProfile_pic_original_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    contactsInfo.setPic_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_ORIGINAL_URL));
                                    contactsInfo.setProfile_pic_small_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                    contactsInfo.setPic_url(searchResult.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                    contactsInfo.setUserSalutation(searchResult.optString(RestUtils.TAG_USER_SALUTAION));
                                    contactsInfo.setUserTypeId(searchResult.optInt(RestUtils.TAG_USER_TYPE_ID));
                                    contactsInfo.setPhno_vis(searchResult.optString(RestUtils.TAG_CNNTMUNVIS));
                                    contactsInfo.setEmail_vis(searchResult.optString(RestUtils.TAG_CNNTEMAILVIS));
                                    connectsResults.add(contactsInfo);
                                }
                            }
                            if (connectsResults.size() == 0) {
                                tv_noresults.setVisibility(View.VISIBLE);
                                userConnectsList.setVisibility(View.GONE);

                            } else {
                                tv_noresults.setVisibility(View.GONE);
                                userConnectsList.setVisibility(View.VISIBLE);
                            }
                            otherConnectsListAdapter.notifyDataSetChanged();
                            if (len > 0) {
                                otherConnectsListAdapter.setLoaded();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                hideProgress();
                if (swipeContainer != null) {
                    swipeContainer.setRefreshing(false);
                }
                try {
                    if (errorResponse != null && !errorResponse.isEmpty()) {
                        JSONObject errorJson = new JSONObject(errorResponse);
                        if (errorJson.has(RestUtils.TAG_ERROR_MESSAGE)) {
                            //errorMsg = errorJson.optString(RestUtils.TAG_ERROR_MESSAGE);
                            Toast.makeText(ViewContactsActivity.this, errorJson.optString(RestUtils.TAG_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ViewContactsActivity.this, "Unable to fetch data,please try again", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ViewContactsActivity.this, "Unable to fetch data,please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }).sendSinglePartRequest();
    }
    private void _initializeUI() {
        try {
            userConnectsList = (RecyclerView) findViewById(R.id.userConnectsList);
            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerUserConnects);
            tv_noresults = _findViewById(R.id.noResults_txt);
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            userConnectsList.setHasFixedSize(true);
            mLayoutManager = new CustomLinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            userConnectsList.setLayoutManager(mLayoutManager);
            userConnectsList.addItemDecoration(HeaderDecoration.with(userConnectsList)
                    .inflate(R.layout.chat_list_header)
                    .parallax(0.9f)
                    .dropShadowDp(1)
                    .build(0, mContext));
            otherConnectsListAdapter = new NetworkSearchAdapter(ViewContactsActivity.this, connectsResults, userConnectsList);
            userConnectsList.setAdapter(otherConnectsListAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onTaskCompleted(String searchresponse) {
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton){
            customBackButton=false;
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(mRealm));
                String eventName="";
                String doc_name = getIntent().getExtras().getString("doc_name");
                if(navigatedFrom.equalsIgnoreCase("Followers")){
                    if(!doc_name.isEmpty()){
                        eventName="OtherUserFollowersDeviceBackTapped";
                        jsonObject.put("OtherDocID",otherDocUUID);
                    }else{
                        eventName="FollowersDeviceBackTapped";
                    }
                }else if(navigatedFrom.equalsIgnoreCase("following")){
                    if(!doc_name.isEmpty()){
                        eventName="OtherUserFollowingDeviceBackTapped";
                        jsonObject.put("OtherDocID",otherDocUUID);
                    }else{
                        eventName="FollowingDeviceBackTapped";
                    }

                }else{
                    eventName="OtherUserConnectsDeviceBackTapped";
                    jsonObject.put("OtherDocID",otherDocUUID);
                }
                AppUtil.logUserUpShotEvent(eventName,AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        try {
            if (resultCode == 1) {
                if (requestCode == 1) {
                    if (requestObj != null) {
                        recordsCount=0;
                        lastRecordTime = 0;
                        pageIndex = 0;
                        JSONObject requestData=new JSONObject();
                        String restURL=RestApiConstants.GET_VISITED_CONTACTS;
                        if(navigatedFrom.equalsIgnoreCase("Followers")||navigatedFrom.equalsIgnoreCase("Following")){
                            requestData.put(RestUtils.TAG_USER_ID, userid);
                            requestData.put("is_following", is_following);
                            if(profileid!=0){
                                requestData.put(RestUtils.TAG_OTHER_USER_ID, profileid);
                            }
                            restURL=RestApiConstants.GET_FOLLOW_LIST;
                            requestData.put(RestUtils.TAG_COUNT, recordsCount);
                        }else {
                            requestData.put(RestUtils.TAG_DOC_ID, userid);
                            requestData.put(RestUtils.TAG_OTHER_DOC_ID, profileid);
                            requestData.put(RestUtils.TAG_TIME_KEY, lastRecordTime);
                            requestData.put(RestUtils.PG_NUM, pageIndex);
                        }
                        getOtherUserConnects(requestData.toString(), false, true,restURL);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_connects, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton=true;
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(mRealm));
                    String eventName="";
                    String doc_name = getIntent().getExtras().getString("doc_name");
                    if(navigatedFrom.equalsIgnoreCase("Followers")){
                        if(!doc_name.isEmpty()){
                            eventName="OtherUserFollowersBackTapped";
                            jsonObject.put("OtherDocID",otherDocUUID);
                        }else {
                            eventName = "FollowersBackTapped";
                        }

                    }else if(navigatedFrom.equalsIgnoreCase("following")){
                        if(!doc_name.isEmpty()){
                          eventName="OtherUserFollowingBackTapped";
                          jsonObject.put("OtherDocID",otherDocUUID);
                        }else {
                            eventName="FollowingBackTapped";
                        }
                    }else{
                        eventName="OtherUserConnectsBackTapped";
                        jsonObject.put("OtherDocID",otherDocUUID);
                    }
                    AppUtil.logUserUpShotEvent(eventName,AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            case R.id.action_help:
                Intent intentsupport = new Intent(ViewContactsActivity.this, ContactSupport.class);
                startActivity(intentsupport);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /*Refactoring the deprecated startActivityForResults*/
    @Override
    public void onScreenNavigate(Intent intent) {
        launcherInviteOrVisitProfileResults.launch(intent);
    }
}
