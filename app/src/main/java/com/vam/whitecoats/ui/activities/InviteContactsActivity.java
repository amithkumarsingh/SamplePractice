package com.vam.whitecoats.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.InviteContactsRecyclerAdapter;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.HeaderDecoration;
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

import io.realm.Realm;

public class InviteContactsActivity extends BaseActionBarActivity {

    private RelativeLayout invite_lay;
    private RecyclerView inviteRecyclerList;
    private TextView mTitleTextView;
    private ImageView next_button;

    private Realm realm;
    private RealmManager realmManager;
    private int doctorId;
    private JSONObject connectsRequestJsonObject;
    public static final String TAG = InviteContactsActivity.class.getSimpleName();

    private ArrayList<JSONObject> connectsList= new ArrayList<>();
    private ArrayList<ContactsInfo> pendingNotificationsList = new ArrayList<>();
    private InviteContactsRecyclerAdapter inviteContactsRecyclerAdapter;
    private int lastDocID=0;
    int page_index=0;
    public static int selectedPosition=-1;
    //private InvitedStatusReceiver inviteStatustReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_contacts);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        next_button = (ImageView) mCustomView.findViewById(R.id.next_button);

        inviteRecyclerList=(RecyclerView)findViewById(R.id.invite_connects_list_recyclerView);
        invite_lay=(RelativeLayout)findViewById(R.id.invite_button_other_apps);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

        mTitleTextView.setText("Add Connects");

        next_button.setVisibility(View.GONE);
        doctorId = realmManager.getDoc_id(realm);

       connectsRequestJsonObject = new JSONObject();
        try {
            connectsRequestJsonObject.put(RestUtils.TAG_DOC_ID,doctorId);
            connectsRequestJsonObject.put(RestUtils.PG_NUM,page_index);

        } catch (JSONException e) {
            e.printStackTrace();
        }

      /*  IntentFilter filter = new IntentFilter();
        filter.addAction("INVITE_CONNECT_ACTION");*/
        EventBus.getDefault().register(this);

       /* inviteStatustReceiver = new InvitedStatusReceiver();
        registerReceiver(inviteStatustReceiver, filter);*/

        inviteRecyclerList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InviteContactsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        inviteRecyclerList.setLayoutManager(linearLayoutManager);
        inviteContactsRecyclerAdapter =new InviteContactsRecyclerAdapter(InviteContactsActivity.this,connectsList,inviteRecyclerList,null);
        inviteRecyclerList.setAdapter(inviteContactsRecyclerAdapter);


        inviteContactsRecyclerAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                    connectsList.add(null);
                    inviteContactsRecyclerAdapter.notifyItemInserted(connectsList.size());
                try {
                    page_index++;
                    connectsRequestJsonObject.put(RestUtils.PG_NUM,page_index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(AppUtil.isConnectingToInternet(InviteContactsActivity.this)){
                        loadConnects(true);
                }
            }
        });

        inviteRecyclerList.setAdapter(inviteContactsRecyclerAdapter);
        inviteRecyclerList.addItemDecoration(HeaderDecoration.with(inviteRecyclerList)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, InviteContactsActivity.this));

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

        invite_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtil.inviteToWhiteCoatsIntent(InviteContactsActivity.this,getResources().getString(R.string.inviate_to_whiteCoats),"Invite via","");
            }
        });
        if(isConnectingToInternet()) {
            loadConnects(false);
        }
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

    private void loadConnects(final boolean isFromOnScroll) {
        if(!isFromOnScroll) {
            showProgress();
        }
        new VolleySinglePartStringRequest(InviteContactsActivity.this, Request.Method.POST, RestApiConstants.GET_INVITE_CONNECTS_SERVICE, connectsRequestJsonObject.toString(), "INVITE_CONNECTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if(!isFromOnScroll) {
                    hideProgress();
                }
                else{
                    connectsList.remove(connectsList.size() - 1);
                    inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                }
                try {
                    JSONObject jsonObject = new JSONObject(successResponse);
                    if(jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)){

                        JSONObject connectsListObject=jsonObject.optJSONObject(RestUtils.TAG_DATA);
                        JSONArray connectsListArray = connectsListObject.optJSONArray("connects");
                        lastDocID=connectsListObject.optInt("last_docId");
                        for (int i = 0; i < connectsListArray.length(); i++) {
                            connectsList.add(connectsListArray.optJSONObject(i));
                        }
                    }
                    inviteContactsRecyclerAdapter.setLoaded();
                    inviteContactsRecyclerAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                inviteContactsRecyclerAdapter.setLoaded();
                if(!isFromOnScroll) {
                    hideProgress();
                }
                else{
                    connectsList.remove(connectsList.size() - 1);
                    inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                }
                if(page_index>1){
                    page_index--;
                }
                displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectedPosition=-1;
        EventBus.getDefault().unregister(this);
       /* if(inviteStatustReceiver !=null){
            unregisterReceiver(inviteStatustReceiver);
            inviteStatustReceiver = null;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_community, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                Intent intent = new Intent(InviteContactsActivity.this, SearchContactsActivity.class);
                intent.putExtra(RestUtils.NAVIGATATE_FROM, TAG);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*
    public  class InvitedStatusReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
                if(selectedPosition==-1){
                    return;
                }
                JSONObject selectedMemeber = connectsList.get(selectedPosition);
                try {
                    selectedMemeber.put(RestUtils.TAG_CONNECT_STATUS,1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (inviteContactsRecyclerAdapter != null) {
                    inviteContactsRecyclerAdapter.notifyDataSetChanged();
                }
            }
        }
    }
*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InviteConnectStatusUpdateEvent event) {
        if (event == null) {
            return;
        }

        String intentAction = event.getMessage();
        if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
            if(selectedPosition==-1){
                return;
            }
            JSONObject selectedMemeber = connectsList.get(selectedPosition);
            try {
                selectedMemeber.put(RestUtils.TAG_CONNECT_STATUS,1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (inviteContactsRecyclerAdapter != null) {
                inviteContactsRecyclerAdapter.notifyDataSetChanged();
            }
        }

    }

}
