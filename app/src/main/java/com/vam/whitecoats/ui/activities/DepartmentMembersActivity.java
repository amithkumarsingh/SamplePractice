package com.vam.whitecoats.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.Member;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.DepartmentMembersAdapter;
import com.vam.whitecoats.ui.customviews.ScrollInterfacedListView;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.InviteConnectStatusUpdateEvent;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by satyasarathi.m on 20-06-2016.
 */
public class DepartmentMembersActivity extends BaseActionBarActivity {
    public static final String TAG = DepartmentMembersActivity.class.getSimpleName();
    ScrollInterfacedListView membersListview;
    TextView noItemTextview;
    TextView mTitleTextView;
    List<Member> membersList;
    int channelid;
    int departmentId;
    String deptTitle;
    int doctorId;
    JSONObject requestData;
    int lastRecordId = 0;
    boolean isLoading = false;
    DepartmentMembersAdapter membersAdapter;
    // private InviteToConnectReceiver inviteToConnectReceiver;
    public static int selectedPosition = -1;
    public static int REFRESH_MEMBERS_ACTION = 2001;
    private boolean isScrollDown;
    private boolean customBackButton = false;
    private Realm realm;
    private RealmManager realmManager;
    private ActivityResultLauncher<Intent> launchSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, getString(R.string._onCreate));
        setContentView(R.layout.activity_department_members);
        membersListview = (ScrollInterfacedListView) findViewById(R.id.membersListview);
        noItemTextview = (TextView) findViewById(R.id.emptyTxtview);
        /**
         * Get all the request params from Intent.
         */
        Bundle bundle = getIntent().getExtras();
        realm = Realm.getDefaultInstance();//getInstance(this);
        realmManager = new RealmManager(this);
        channelid = bundle.getInt(RestUtils.CHANNEL_ID, 0);
        departmentId = bundle.getInt(RestUtils.DEPARTMENT_ID, 0);
        doctorId = bundle.getInt(RestUtils.TAG_DOC_ID, 0);
        lastRecordId = bundle.getInt(RestUtils.LAST_MEMBER_ID, 0);
        deptTitle = bundle.getString(RestUtils.DEPARTMENT_NAME, "WhiteCoats");
        membersList = new ArrayList<Member>();
        membersAdapter = new DepartmentMembersAdapter(this, doctorId, membersList, deptTitle);
        membersListview.setAdapter(membersAdapter);
        EventBus.getDefault().register(this);

        /**
         * Setup the actionbar
         */
        setupActionbar(deptTitle);
        /*IntentFilter filter = new IntentFilter();
        filter.addAction("INVITE_CONNECT_ACTION");*/

        upshotEventData(0, channelid, 0, "", "", deptTitle, "", "", false);

        /*Refactoring the deprecated startActivityForResults*/
        //Start
        launchSearchResults = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    App_Application.setCurrentActivity(DepartmentMembersActivity.this);
                    membersList.clear();
                    loadMore(true);
                });
        //End

      /*  inviteToConnectReceiver = new InviteToConnectReceiver();
        registerReceiver(inviteToConnectReceiver, filter);*/
        membersListview.setOnDetectScrollListener(new ScrollInterfacedListView.OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                isScrollDown = false;
            }

            @Override
            public void onDownScrolling() {
                isScrollDown = true;
            }
        });
        membersListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                this.currentScrollState = scrollState;
                /**
                 * While user Scrolls down, once after the scroll state reaches to 0(IDLE STATE)
                 * we are making the flag as true for explicit calling to load more items.
                 */
                if (scrollState == 0) {
                    isScrollDown = true;
                } else {
                    isScrollDown = false;
                }
                this.isScrollCompleted();

            }

            private void isScrollCompleted() {
                if (isScrollDown) {
                    if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                            && this.currentScrollState == SCROLL_STATE_IDLE) {
                        if (!isLoading) {
                            loadMore(false);
                            isLoading = true;
                        }
                    }
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.i(TAG, "onScroll() " + totalItemCount);
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;

            }

        });
        loadMore(true);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

   /* private void setupList() {
        Log.i(TAG, "setupList()");
        DepartmentMembersAdapter membersAdapter = new DepartmentMembersAdapter(this, doctorId, membersList,deptTitle);
        membersListview.setAdapter(membersAdapter);

    }*/

    private void setupActionbar(String actionbarTitle) {
        Log.i(TAG, "setupActionbar()");
        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_memberinfo, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_dept);
        mTitleTextView.setText(actionbarTitle);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, getString(R.string._onPause));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, getString(R.string._onResume));
        setCurrentActivity();
        checkNetworkConnectivity();
    }


    public void loadMore(final boolean isAdapterintialation) {
        requestData = new JSONObject();
        try {
            requestData.put(RestUtils.CHANNEL_ID, channelid);
            requestData.put(RestUtils.DEPARTMENT_ID, departmentId);
            requestData.put(RestUtils.TAG_DOC_ID, doctorId);
            if (isAdapterintialation) {
                requestData.put(RestUtils.LAST_MEMBER_ID, 0);
            } else {
                requestData.put(RestUtils.LAST_MEMBER_ID, lastRecordId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /**
         * Call the service to get members Info
         */
        showProgress();
        new VolleySinglePartStringRequest(DepartmentMembersActivity.this, Request.Method.POST, RestApiConstants.MEMBER_INFO, requestData.toString(), "GET_DEPT_MEMBERS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                hideProgress();
                JSONObject responseObj = null;
                try {
                    responseObj = new JSONObject(successResponse);
                    if (responseObj.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {
                        lastRecordId = responseObj.optInt("last_member_id");
                        JSONArray dirJsonArray = responseObj.optJSONArray("department_members_info");
                        int size = dirJsonArray.length();
                        if (size > 0) {
                            for (int position = 0; position < size; position++) {
                                JSONObject memberOjb = dirJsonArray.getJSONObject(position);
                                Member member = new Member();
                                member.setDoctorId(memberOjb.optInt(RestUtils.TAG_DOC_ID));
                                member.setContactEmail(memberOjb.optString(RestUtils.TAG_CNT_EMAIL));
                                member.setContactNumber(memberOjb.optString(RestUtils.TAG_CNT_NUM));
                                member.setQbUserId(memberOjb.optInt("qb_user_id"));
                                member.setSpeciality(memberOjb.optString(RestUtils.TAG_SPLTY));
                                member.setSubspeciality(memberOjb.optString(RestUtils.TAG_SUB_SPLTY));
                                member.setFullName(memberOjb.optString(RestUtils.TAG_USER_FULL_NAME));
                                member.setLocation(memberOjb.optString(RestUtils.TAG_LOCATION));
                                member.setWorkplace(memberOjb.optString(RestUtils.TAG_WORKPLACE));
                                member.setDesignation(memberOjb.optString(RestUtils.TAG_DESIGNATION));
                                member.setDegrees(memberOjb.optString(RestUtils.TAG_DEGREES));
                                member.setProfilePicName(memberOjb.optString(RestUtils.TAG_PROFILE_PIC_NAME));
                                member.setProfilePicUrl(memberOjb.optString(RestUtils.TAG_PROFILE_PIC_URL));
                                member.setNetworkStatus(memberOjb.optInt(RestUtils.TAG_NETWORK_STATUS));
                                member.setCommunityDesignation((memberOjb.optString(RestUtils.COMMUNITY_DESIGNATION).equals("null")) ? "" : memberOjb.optString(RestUtils.COMMUNITY_DESIGNATION));
                                member.setUserSalutation(memberOjb.optString(RestUtils.TAG_USER_SALUTAION));
                                member.setUserTypeId(memberOjb.optInt(RestUtils.TAG_USER_TYPE_ID));
                                member.setPhno_vis(memberOjb.optString(RestUtils.TAG_CNNTMUNVIS));
                                member.setEmail_vis(memberOjb.optString(RestUtils.TAG_CNNTEMAILVIS));
                                membersList.add(member);
                            }
                            /*
                             * Set the adapter
                             */
                            if (membersAdapter != null) {
                                membersAdapter.notifyDataSetChanged();
                            }
                            isLoading = false;

                        } else {
                            if (isAdapterintialation) {
                                Toast.makeText(DepartmentMembersActivity.this, deptTitle + " " + getString(R.string.empty_department), Toast.LENGTH_SHORT).show();
                            }
                        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, getString(R.string._onCreateOptionsMenu));
        getMenuInflater().inflate(R.menu.menu_community, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, getString(R.string._onOptionsItemSelected));
        switch (item.getItemId()) {
            case android.R.id.home:
                customBackButton = true;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("ChannelID", channelid);
                    jsonObject.put("DepartmentName", deptTitle);
                    AppUtil.logUserUpShotEvent("DepartmentMembersBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onBackPressed();
                return true;
            case R.id.action_search:
                Intent intent = new Intent(DepartmentMembersActivity.this, SearchContactsActivity.class);
                intent.putExtra(RestUtils.NAVIGATATE_FROM, TAG);
                intent.putExtra(RestUtils.CHANNEL_ID, channelid);
                intent.putExtra(RestUtils.DEPARTMENT_ID, departmentId);
                intent.putExtra(RestUtils.TAG_DOC_ID, doctorId);
                intent.putExtra(RestUtils.DEPARTMENT_NAME, deptTitle);
                launchSearchResults.launch(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, getString(R.string._onStop));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, getString(R.string._onDestroy));
        channelid = 0;
        departmentId = 0;
        doctorId = 0;
        selectedPosition = -1;
        /*if(inviteToConnectReceiver !=null){
            unregisterReceiver(inviteToConnectReceiver);
            inviteToConnectReceiver = null;
        }*/
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        App_Application.setCurrentActivity(this);
    }

    /*public class InviteToConnectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
                if(selectedPosition==-1){
                    return;
                }
                Member selectedMember = membersList.get(selectedPosition);
                selectedMember.setNetworkStatus(1);
                membersList.set(selectedPosition,selectedMember);
                if (membersAdapter != null) {
                    membersAdapter.notifyDataSetChanged();
                }
            }
        }
    }*/

    @Override
    public void onBackPressed() {
        if (!customBackButton) {
            customBackButton = false;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                jsonObject.put("ChannelID", channelid);
                jsonObject.put("DepartmentName", deptTitle);
                AppUtil.logUserUpShotEvent("DepartmentMembersDeviceBackTapped", AppUtil.convertJsonToHashMap(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InviteConnectStatusUpdateEvent event) {
        if (event == null) {
            return;
        }

        String intentAction = event.getMessage();
        if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
            if (selectedPosition == -1) {
                return;
            }
            Member selectedMember = membersList.get(selectedPosition);
            selectedMember.setNetworkStatus(1);
            membersList.set(selectedPosition, selectedMember);
            if (membersAdapter != null) {
                membersAdapter.notifyDataSetChanged();
            }
        }
    }

}
