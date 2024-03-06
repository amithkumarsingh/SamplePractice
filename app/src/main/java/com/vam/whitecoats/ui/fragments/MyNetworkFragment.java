package com.vam.whitecoats.ui.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.AppConstants;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotifications;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.InviteContactsActivity;
import com.vam.whitecoats.ui.adapters.InviteContactsRecyclerAdapter;
import com.vam.whitecoats.ui.adapters.NotificationInviteAdapter;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.InviteConnectStatusUpdateEvent;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.utils.VolleySinglePartStringRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by pardhasaradhid on 1/22/2018.
 */

public class MyNetworkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MyNetworkFragment.OnFragmentInteractionListener mListener;

    private Realm realm;
    private RealmManager realmManager;
    private ImageView add_img;
    View view;
    private Context mContext;
    boolean coachMarkShown;
    private RecyclerView connectsinviteRecyclerList, notify_requests;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences editor;
    private int doctorId;
    private JSONObject requestData;
    private ProgressDialog mProgressDialog;
    private TextView nolist_textview, connects_count;
    private LinearLayout invite_btn_layout, btn_connects;
    private ArrayList<JSONObject> connectsList = new ArrayList<>();

    private InviteContactsRecyclerAdapter inviteContactsRecyclerAdapter;
    private int lastDocID = 0;
    int page_index = 0;
    private JSONObject connectsRequestJsonObject = null;
    private Button btn_seeall;
    private RealmResults<RealmNotifications> arrayList;
    private RealmResults<RealmNotificationInfo> arrayListDb;
    ArrayList<JSONObject> notificationArrayList = new ArrayList<>();

    private NotificationInviteAdapter notificationadapter;
   // private MyNetworkFragment.InvitedStatusReceiver inviteStatustReceiver;
    private LinearLayout addChildLayout, notify_layout;
    private ProgressBar progressBar_networksTab;
    private SwipeRefreshLayout swipeContainerInNetwork;
    private LinearLayoutManager linearLayoutManager;

    public static MyNetworkFragment newInstance(String param1, String param2) {
        MyNetworkFragment fragment = new MyNetworkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyNetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //arrayList = realmManager.getNotification(realm);
        //contactslist.addAll(realmManager.getMyContacts(realm));
        doctorId = realmManager.getDoc_id(realm);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        //updateMyConnectsBroadcastManagers();
        EventBus.getDefault().register(this);

        arrayListDb = realmManager.getNotificationDataFromDB();
        notificationArrayList.clear();
        connectsList.clear();

        for (int i = 0; i < arrayListDb.size(); i++) {
            try {
                JSONObject notifyData = new JSONObject(arrayListDb.get(i).getNotifyData());
                if (notifyData.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
                    //    addtoanothelist;
                    notificationArrayList.add(notifyData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        arrayListDb.addChangeListener(new RealmChangeListener<RealmResults<RealmNotificationInfo>>() {
            @Override
            public void onChange(RealmResults<RealmNotificationInfo> realmNotificationInfos) {
                ArrayList<JSONObject> notificationTempArrayList = new ArrayList<>();
                for (int i = 0; i < arrayListDb.size(); i++) {
                    try {
                        JSONObject notifyData = new JSONObject(arrayListDb.get(i).getNotifyData());
                        if (notifyData.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())) {
                            //    adding to new list
                            notificationTempArrayList.add(notifyData);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                notificationArrayList.clear();
                if (notificationTempArrayList.size() > 0) {
                    notificationArrayList.addAll(notificationTempArrayList);
                }
                if (inviteContactsRecyclerAdapter != null) {
                    inviteContactsRecyclerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_network, container, false);

        connectsinviteRecyclerList = (RecyclerView) view.findViewById(R.id.connects_recycler_list);
        arrayList = realmManager.getPendingNotificationDB(realm);
        progressBar_networksTab = (ProgressBar) view.findViewById(R.id.progressBar_networksTab);
        swipeContainerInNetwork = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerInNetwork);
        //add_img = (ImageView) view.findViewById(R.id.contacts_img_addcase);

        //setUI();
       /* IntentFilter filter = new IntentFilter();
        filter.addAction("INVITE_CONNECT_ACTION");*/
        progressBar_networksTab.setVisibility(View.GONE);

        /*inviteStatustReceiver = new InvitedStatusReceiver();
        getActivity().registerReceiver(inviteStatustReceiver, filter);*/

        connectsinviteRecyclerList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        connectsinviteRecyclerList.setLayoutManager(linearLayoutManager);
        //Network Tab connects
        inviteContactsRecyclerAdapter = new InviteContactsRecyclerAdapter(getActivity(), connectsList, connectsinviteRecyclerList, notificationArrayList);
        connectsinviteRecyclerList.setAdapter(inviteContactsRecyclerAdapter);

        inviteContactsRecyclerAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                connectsList.add(null);
                inviteContactsRecyclerAdapter.notifyItemInserted(connectsList.size());
                try {
                    page_index++;
                    connectsRequestJsonObject.put(RestUtils.PG_NUM, page_index);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (AppUtil.isConnectingToInternet(getActivity())) {
                    loadInviteConnects(true);
                }
            }
        });
        requestData = new JSONObject();
        try {
            requestData.put(RestUtils.TAG_DOC_ID, doctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        connectsinviteRecyclerList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        JSONObject obj1 = new JSONObject();
        JSONObject obj2 = new JSONObject();
        try {
            obj1.put("header_key", true);
            obj2.put("pending_connects_key", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        connectsList.add(obj1);
        connectsList.add(obj2);
        connectsList.add(null);
        inviteContactsRecyclerAdapter.notifyDataSetChanged();

        swipeContainerInNetwork.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (AppUtil.isConnectingToInternet(getActivity())) {
                    connectsRequestJsonObject = new JSONObject();
                    try {
                        page_index = 0;
                        connectsRequestJsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                        connectsRequestJsonObject.put(RestUtils.PG_NUM, page_index);
                        connectsList.clear();
                        JSONObject obj1 = new JSONObject();
                        JSONObject obj2 = new JSONObject();
                        try {
                            obj1.put("header_key", true);
                            obj2.put("pending_connects_key", true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connectsList.add(obj1);
                        connectsList.add(obj2);
                        connectsList.add(null);
                        inviteContactsRecyclerAdapter.notifyDataSetChanged();
                        App_Application.getInstance().cancelPendingRequests("INVITE_CONNECTS");
                        loadInviteConnects(false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (swipeContainerInNetwork != null) {
                        swipeContainerInNetwork.setRefreshing(false);
                    }
                }
            }
        });
        swipeContainerInNetwork.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        setHasOptionsMenu(true);

        return view;
    }

    public void loadInviteConnects(final boolean isFromOnScroll) {

        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_INVITE_CONNECTS_SERVICE, connectsRequestJsonObject.toString(), "INVITE_CONNECTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (progressBar_networksTab.getVisibility() == View.VISIBLE) {
                    progressBar_networksTab.setVisibility(View.GONE);
                }
                if (swipeContainerInNetwork != null) {
                    swipeContainerInNetwork.setRefreshing(false);
                }
                if (!isFromOnScroll) {
                    if (connectsList.contains(null)) {
                        connectsList.remove(null);
                        inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                    }
                } else {
                    connectsList.remove(connectsList.size() - 1);
                    inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                }
                try {
                    JSONObject jsonObject = new JSONObject(successResponse);
                    if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_SUCCESS)) {

                        JSONObject connectsListObject = jsonObject.optJSONObject(RestUtils.TAG_DATA);
                        JSONArray connectsListArray = connectsListObject.optJSONArray("connects");
                        lastDocID = connectsListObject.optInt("last_docId");
                        if (!isFromOnScroll) {
                            JSONObject obj3 = new JSONObject();
                            obj3.put("contacts_list_header_key", true);
                            connectsList.add(obj3);
                        }
                        for (int i = 0; i < connectsListArray.length(); i++) {
                            connectsList.add(connectsListArray.optJSONObject(i));
                        }
                    }
                    inviteContactsRecyclerAdapter.notifyDataSetChanged();
                    inviteContactsRecyclerAdapter.setLoaded();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(String errorResponse) {
                inviteContactsRecyclerAdapter.setLoaded();
                if (progressBar_networksTab.getVisibility() == View.VISIBLE) {
                    progressBar_networksTab.setVisibility(View.GONE);
                }
                if (swipeContainerInNetwork != null) {
                    swipeContainerInNetwork.setRefreshing(false);
                }
                if (!isFromOnScroll) {
                    if (connectsList.contains(null)) {
                        connectsList.remove(null);
                        inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                    }
                } else {
                    connectsList.remove(connectsList.size() - 1);
                    inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                }
                if (page_index > 1) {
                    page_index--;
                }
                ((BaseActionBarActivity) getActivity()).displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            if (connectsRequestJsonObject == null || connectsList.size() == 2) {
                connectsRequestJsonObject = new JSONObject();
                try {
                    connectsRequestJsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
                    connectsRequestJsonObject.put(RestUtils.PG_NUM, page_index);
                    loadInviteConnects(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

/*
    public void presentShowcaseSequence() {
        if (AppConstants.COACHMARK_INCREMENTER > 1) {
            MaterialShowcaseView.resetSingleUse(getActivity(), "Sequence_Search_Connects");
            ShowcaseConfig config = new ShowcaseConfig();
            config.setDelay(500); // half second between each showcase view
            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), "Sequence_Search_Connects");
            sequence.setConfig(config);
            if (!editor.getBoolean("search_connects", false)) {
                sequence.addSequenceItem(
                        new MaterialShowcaseView.Builder(getActivity())
                                .setTarget(add_img)
                                .setDismissText("Got it")
                                .setDismissTextColor(Color.parseColor("#00a76d"))
                                .setMaskColour(Color.parseColor("#CC231F20"))
                                .setContentText(R.string.tap_to_coach_mark_search).setListener(new IShowcaseListener() {
                            @Override
                            public void onShowcaseDisplayed(MaterialShowcaseView showcaseView) {
                                editor.edit().putBoolean("search_connects", true).commit();
                            }

                            @Override
                            public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {

                            }
                        })
                                .withCircleShape()
                                .build()
                );
                sequence.start();
            }
        }
    }
*/

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

    @Override
    public void onDetach() {
        super.onDetach();
        if (!realm.isClosed())
            realm.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationArrayList.clear();
        EventBus.getDefault().unregister(this);
       // getActivity().unregisterReceiver(inviteStatustReceiver);
        if (!realm.isClosed())
            realm.close();
    }

    public synchronized void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (arrayList != null && inviteContactsRecyclerAdapter != null && connectsList != null) {
            inviteContactsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


/*
    public class InvitedStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
                if (InviteContactsActivity.selectedPosition == -1) {
                    return;
                }
                JSONObject selectedMemeber = connectsList.get(InviteContactsActivity.selectedPosition);
                try {
                    selectedMemeber.put(RestUtils.TAG_CONNECT_STATUS, 1);
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


    public void updatePendingConntectsData(ArrayList<ContactsInfo> pendingNotification) {
        if (inviteContactsRecyclerAdapter != null) {
            if (arrayList != null && connectsList != null) {
                inviteContactsRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent) {

        }
    }

    public void inviteToWhiteCoatsPopup(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String link_val = "https://whitecoats.com/invite";
        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getActivity().getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
        getActivity().startActivity(Intent.createChooser(shareIntent, "Invite via"));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectsRequestJsonObject = new JSONObject();
        try {
            connectsRequestJsonObject.put(RestUtils.TAG_DOC_ID, doctorId);
            connectsRequestJsonObject.put(RestUtils.PG_NUM, page_index);
            loadInviteConnects(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InviteConnectStatusUpdateEvent event) {
        if (event == null) {
            return;
        }

        String intentAction = event.getMessage();
        if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
            if (InviteContactsActivity.selectedPosition == -1) {
                return;
            }
            JSONObject selectedMemeber = connectsList.get(InviteContactsActivity.selectedPosition);
            try {
                selectedMemeber.put(RestUtils.TAG_CONNECT_STATUS, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (inviteContactsRecyclerAdapter != null) {
                inviteContactsRecyclerAdapter.notifyDataSetChanged();
            }
        }
    }


}
