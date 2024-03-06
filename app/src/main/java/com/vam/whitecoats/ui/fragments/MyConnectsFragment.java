package com.vam.whitecoats.ui.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.async.ConnectNotificationsAsync;
import com.vam.whitecoats.constants.NotificationType;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CaseroomNotifyInfo;
import com.vam.whitecoats.core.models.ContactsInfo;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.core.realm.RealmNotifications;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.InviteContactsActivity;
import com.vam.whitecoats.ui.activities.Network_MyConnects;
import com.vam.whitecoats.ui.adapters.InviteContactsRecyclerAdapter;
import com.vam.whitecoats.ui.adapters.NotificationInviteAdapter;
import com.vam.whitecoats.ui.interfaces.OnLoadMoreListener;
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse;
import com.vam.whitecoats.ui.interfaces.OnTaskCompleted;
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
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.vam.whitecoats.ui.activities.PublicationsActivity.mLastClickTime;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyConnectsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyConnectsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyConnectsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Realm realm;
    private RealmManager realmManager;
    //private ImageView add_img;
    View view;
    private Context mContext;
    boolean coachMarkShown;
    private RecyclerView connectsinviteRecyclerList;
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
    //private Button btn_seeall;
    //private ArrayList<ContactsInfo> arrayList;
    private NotificationInviteAdapter notificationadapter;
    private ArrayList<CaseroomNotifyInfo> caseroomNotifyInfoArrayList;
   // private InvitedStatusReceiver inviteStatustReceiver;
    private LinearLayout addChildLayout,notify_layout;
    private RealmResults<RealmNotifications> arrayList;
    private RealmResults<RealmNotificationInfo> arrayListDb;
    ArrayList<JSONObject> notificationArrayList =new ArrayList<>();


    public static MyConnectsFragment newInstance(String param1, String param2) {
        MyConnectsFragment fragment = new MyConnectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyConnectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        editor = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //contactslist.addAll(realmManager.getMyContacts(realm));
        doctorId = realmManager.getDoc_id(realm);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(getString(R.string.dlg_wait_please));
        updateMyConnectsBroadcastManagers();
        EventBus.getDefault().register(this);
        arrayListDb = realmManager.getNotificationDataFromDB();
        for(int i=0;i<arrayListDb.size();i++){
            try {
                JSONObject notifyData = new JSONObject(arrayListDb.get(i).getNotifyData());
                if(notifyData.optString(RestUtils.TAG_TYPE).equalsIgnoreCase(NotificationType.INVITE_USER_FOR_CONNECT.name())){
                    //    addtoanothelist;
                    notificationArrayList.add(notifyData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_network, container, false);
        // ListView my_connects_listView = (ListView) view.findViewById(R.id.my_connects_listView);
        // connectsRecyclerList = (RecyclerView) view.findViewById(R.id.connects_recycler_list);
        connectsinviteRecyclerList = (RecyclerView) view.findViewById(R.id.connects_recycler_list);
        //notifyconnectslist = (ListView) view.findViewById(R.id.notify_connects);
        nolist_textview = (TextView) view.findViewById(R.id.nolist_textview);
        // swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        connects_count = (TextView) view.findViewById(R.id.network_text_cont_num);
        //btn_seeall = (Button) view.findViewById(R.id.btnreq_seeall);
        //addChildLayout = (LinearLayout) view.findViewById(R.id.add_child_layout);
        notify_layout = (LinearLayout) view.findViewById(R.id.notify_layout);
        //new ArrayList<ContactsInfo>();
       /* MyContactsAdapter adapter = new MyContactsAdapter(getActivity(), contactslist);
        my_connects_listView.setAdapter(adapter);*/

        /*
        * adapter for connects requests
        * */
        arrayList = realmManager.getNotificationDB(realm);
        //notificationadapter = new NotificationInviteAdapter(getActivity(), arrayList);

        //ViewGroup header = (ViewGroup) inflater.inflate(R.layout.connects_network_header, notifyconnectslist, false);
        //notifyconnectslist.addHeaderView(header);
        //notifyconnectslist.setAdapter(notificationadapter);
        int size=arrayList.size();
        if(size>0){
            for(int i=0;i<size;i++){
                View view=inflater.inflate(R.layout.notify_pending,null);
                notifyitemdata(view,i);
                addChildLayout.addView(view);
                if(i==1){
                    break;
                }
            }
        }
        setUI();
        /**
         * Update notification read status.
         */

        try {
            for (int i = 0; i < size; i++) {
                realmManager.updateNotificationReadStatus(realm, arrayList.get(i).getNotification_id());

                Intent intent = new Intent("NotificationCount");
                LocalBroadcastManager.getInstance(App_Application.getInstance()).sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   IntentFilter filter = new IntentFilter();
        filter.addAction("INVITE_CONNECT_ACTION");*/

       /* inviteStatustReceiver = new InvitedStatusReceiver();
        getActivity().registerReceiver(inviteStatustReceiver, filter);*/

        connectsinviteRecyclerList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        connectsinviteRecyclerList.setLayoutManager(linearLayoutManager);
        //connects_count.setText(realmManager.getMyContacts(realm).size());
        inviteContactsRecyclerAdapter = new InviteContactsRecyclerAdapter(getActivity(), connectsList, connectsinviteRecyclerList,notificationArrayList);
        //sortConnects(connectsList);
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
        /*if (contactslist.size() == 0) {
            nolist_textview.setVisibility(View.VISIBLE);
        }*/

        //connects count list
        int connects_cou = realmManager.getMyContactsDB(realm).size();
        connects_count.setText("" + connects_cou);

        //invite button click
        invite_btn_layout = (LinearLayout) view.findViewById(R.id.btn_invite_layout);
        invite_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String link_val = "https://whitecoats.com/invite";
                String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Join me on WhiteCoats");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.inviate_to_whiteCoats) + Html.fromHtml(body));
                startActivity(Intent.createChooser(shareIntent, "Invite via"));
            }
        });

        //connects button click
        btn_connects = (LinearLayout) view.findViewById(R.id.linear_connects);
        btn_connects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inviteIntent = new Intent(getActivity(), Network_MyConnects.class);
                getActivity().startActivity(inviteIntent);
            }
        });

        //seeall button click

        requestData = new JSONObject();
        try {
            requestData.put(RestUtils.TAG_DOC_ID, doctorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        connectsinviteRecyclerList.addItemDecoration(HeaderDecoration.with(connectsinviteRecyclerList)
                .inflate(R.layout.chat_list_header)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, getActivity()));

        return view;
    }


    public void notifyitemdata(final View view, final int i){

        ImageView profile_notify,ig_accept,ig_reject;
        TextView tv_name,tv_splty;

        String pic_name ="";
        int doc_id=arrayList.get(i).getDoc_id();

        ig_accept         = (ImageView) view.findViewById(R.id.notify_accept_ig);
        ig_reject         = (ImageView) view.findViewById(R.id.notify_reject_ig);
        profile_notify = (ImageView) view.findViewById(R.id.nwnotification_img);
        tv_name = (TextView) view.findViewById(R.id.nwnotify_item_name_txt);
        tv_splty = (TextView) view.findViewById(R.id.nwnotify_item_splty_txt);

        tv_name.setText(arrayList.get(i).getDoc_name());
        tv_splty.setText(arrayList.get(i).getDoc_speciality());

        if(arrayList.get(i).getDoc_pic_url()!=null && !arrayList.get(i).getDoc_pic_url().isEmpty()) {
            /*Picasso.with(getActivity())
                    .load(arrayList.get(i).getDoc_pic_url().trim())
                    .placeholder(R.drawable.default_profilepic) //this is optional the image to display while the url image is downloading
                    .error(R.drawable.default_profilepic)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                    .into(profile_notify);*/
            AppUtil.loadCircularImageUsingLib(getActivity(),arrayList.get(i).getDoc_pic_url().trim(),profile_notify,R.drawable.default_profilepic);
        }else{
            profile_notify.setImageResource(R.drawable.default_profilepic);
        }


        ig_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtil.isConnectingToInternet(getActivity())){

                    if(AppUtil.getUserVerifiedStatus() == 3) {
                        try {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            } else {
                                mLastClickTime = SystemClock.elapsedRealtime();
                                JSONObject object = new JSONObject();
                                object.put("from_doc_id", realmManager.getDoc_id(realm));
                                object.put("to_doc_id", arrayList.get(i).getDoc_id());
                                object.put("resp_status", "accept");
                                String reqData = object.toString();
                                new ConnectNotificationsAsync(getActivity(), RestApiConstants.CONNECT_INVITE, AppUtil.convertConnectNotificationToPojo(arrayList.get(i)), new OnTaskCompleted() {
                                    @Override
                                    public void onTaskCompleted(String s) {
                                        addChildLayout.removeView(view);
                                    }
                                }).executeOnExecutor(App_Application.getCommonThreadPoolExecutor(), reqData, "connects_notification");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (AppUtil.getUserVerifiedStatus() == 1) {
                        AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_not_uploaded));
                    } else if (AppUtil.getUserVerifiedStatus() == 2) {
                        AppUtil.AccessErrorPrompt(mContext, mContext.getString(R.string.mca_uploaded_but_not_verified));
                    }

                }
            }
        });

        ig_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                //addChildLayout.removeView(view);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {
            if (connectsRequestJsonObject == null) {
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



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
        EventBus.getDefault().unregister(this);
        if (!realm.isClosed())
            realm.close();
    }

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

    @Override
    public void onResume() {
        super.onResume();
        loadMyConnects();
    }

    private void loadMyConnects() {
        /*if (connectsRecyclerViewAdapter != null) {
            contactslist.clear();
            contactslist.addAll(realmManager.getMyContacts(realm));
            sortConnects(contactslist);
            connectsRecyclerViewAdapter.notifyDataSetChanged();
        }*/
    }

    private void updateMyConnectsBroadcastManagers() {
        BroadcastReceiver updateMyConnectsBroadcastReceiver = new UpdateMyConnectsBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateMyConnectsBroadcastReceiver,
                new IntentFilter("update_my_connects"));
    }

    private class UpdateMyConnectsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*if (connectsRecyclerViewAdapter != null) {
                contactslist.clear();
                contactslist.addAll(realmManager.getMyContacts(realm));
                sortConnects(contactslist);
                connectsRecyclerViewAdapter.notifyDataSetChanged();
            }*/
        }
    }

    /*
                        * Sort the list in alphabetical order
                        */
    private void sortConnects(ArrayList<ContactsInfo> connectsList) {

        Collections.sort(connectsList, new Comparator<ContactsInfo>() {

            @Override
            public int compare(ContactsInfo lhs, ContactsInfo rhs) {
                try {
                    return lhs.getName().toUpperCase().compareTo(rhs.getName().toUpperCase());
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }

    public void loadInviteConnects(final boolean isFromOnScroll) {
        if (!isFromOnScroll) {
            showProgress();
        }
        new VolleySinglePartStringRequest(getActivity(), Request.Method.POST, RestApiConstants.GET_INVITE_CONNECTS_SERVICE, connectsRequestJsonObject.toString(), "INVITE_CONNECTS", new OnReceiveResponse() {
            @Override
            public void onSuccessResponse(String successResponse) {
                if (!isFromOnScroll) {
                    hideProgress();
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
//                        connectsList.clear();
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
                if (!isFromOnScroll) {
                    hideProgress();
                } else {
                    connectsList.remove(connectsList.size() - 1);
                    inviteContactsRecyclerAdapter.notifyItemRemoved(connectsList.size());
                }
                if (page_index > 1) {
                    page_index--;
                }
                ((BaseActionBarActivity)getActivity()).displayErrorScreen(errorResponse);
            }
        }).sendSinglePartRequest();
    }

    private void setUI() {
        if (arrayList.size() > 0) {
            notify_layout.setVisibility(View.VISIBLE);
        } else {
            notify_layout.setVisibility(View.GONE);
        }
    }


/*
    public class InvitedStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
                if (InviteContactsActivity.selectedPosition == -1 ) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InviteConnectStatusUpdateEvent event) {
        if (event == null) {
            return;
        }
        String intentAction = event.getMessage();
        if (intentAction != null && intentAction.equalsIgnoreCase("INVITE_CONNECT_ACTION")) {
            if (InviteContactsActivity.selectedPosition == -1 ) {
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
