package com.vam.whitecoats.ui.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.RealmNotificationInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.activities.NotificationJson;
import com.vam.whitecoats.ui.adapters.NotificationsTabAdapter;
import com.vam.whitecoats.ui.customviews.CustomLinearLayoutManager;
import com.vam.whitecoats.ui.interfaces.ConnectNotificationPreDataListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.ConnectsNotificationInsertionTODB;
import com.vam.whitecoats.viewmodel.SharedViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;


public class NotifyConnectsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = NotifyConnectsFragment.class.getSimpleName();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RealmManager realmManager;
    private Realm realm;
    //private RealmResults<RealmNotifications> arrayList;
    //private NotificationInviteAdapter adapter;
    private RecyclerView mRecyclerView;
    private updateUIreceiver updateuireceiver;
    private int count;

    public static int scrollX = 0;
    public static int scrollY = -1;
    private SparseArray<Integer> mPositions;
    TextView tv_notify_connects;
    private RealmResults<RealmNotificationInfo> notificationData;
    private CustomLinearLayoutManager mLayoutManager;
    private NotificationsTabAdapter recycleAdapter;
    private MySharedPref sharedPrefObj = null;
    private int docID = 0;
    private long previousNotificationTimeRecieved = 0;
    boolean isLoading = false;
    private boolean predata = true;
    private boolean loadNext;
    private RealmResults<RealmNotificationInfo> default_inviteResponseConnect;
    private ImageView notification_enable_close_btn;
    private TextView notification_enable_textview;
    private CardView notification_enable_popup_cardview;
    private SharedViewModel sharedViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyConnectsFragment newInstance(String param1, String param2) {
        NotifyConnectsFragment fragment = new NotifyConnectsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NotifyConnectsFragment() {
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
        docID = realmManager.getDoc_id(realm);
        sharedPrefObj = new MySharedPref(getContext());

        boolean firstMigrationRun = sharedPrefObj.getPref(MySharedPref.PREF_MIGRATION_FIRST_RUN, false);
        if (!firstMigrationRun) {
            NotificationJson notificationJson = new NotificationJson(getContext());
            notificationJson.startMigration();
        }
        default_inviteResponseConnect = realmManager.getNotificationDataFromDBOnlyReadStatusFalse();

        notificationData = realmManager.getUnReadNotificationsFromDB();


        recycleAdapter = new NotificationsTabAdapter(getActivity(), notificationData);
        if (sharedPrefObj.getPref("connects_notification_sync_completed",true)) {
            if (docID != 0) {
                //getConnectNotificationsFromAPI(false, prepareGetConnectsNotificationRequest(false, false),false);
                predata = false;
                loadNext = true;
            }
        } else {
            predata = true;
            loadNext = false;
            //getConnectNotificationsFromAPI(false, prepareGetConnectsNotificationRequest(false,true),true);
            //previousNotificationTimeRecieved=sharedPrefObj.getPref("last_notification_time_recieved");
        }
        new ConnectsNotificationInsertionTODB(getContext(), realmManager, realm, docID, loadNext, predata, realmManager.getNotificationDataFromDB(), new ConnectNotificationPreDataListener() {
            @Override
            public void notifyUIWithPreData(JSONArray data) {
                recycleAdapter.notifyDataSetChanged();
                setUI();

            }
        });
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connects, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.notify_connects_list);
        tv_notify_connects = (TextView) view.findViewById(R.id.notify_textconnects);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new CustomLinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        notification_enable_close_btn=(ImageView)view.findViewById(R.id.notification_on_close_iv1);
        notification_enable_textview=(TextView)view.findViewById(R.id.notification_on_tv1);
        notification_enable_popup_cardview=(CardView)view.findViewById(R.id.device_notification_on_cv1);
        if(AppUtil.areNotificationsEnabled(getContext())){
            notification_enable_popup_cardview.setVisibility(View.GONE);
        }else{
            notification_enable_popup_cardview.setVisibility(View.VISIBLE);
        }
        /**
         * Get all the notifications from database that is invited to connect or else not yet connected.
         * And display in list adapter
         */
        /*arrayList = realmManager.getNotificationDB(realm);
        adapter = new NotificationInviteAdapter(getActivity(), arrayList);*/

        //notificationData=realmManager.getNotificationDataFromDB();

        //lv.setAdapter(adapter);
        mRecyclerView.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
        //adapter.setNotifications(arrayList);
        //adapter.notifyDataSetChanged();
        setUI();
        initScrollListener();

        notification_enable_close_btn.setOnClickListener(view1 -> {

            sharedViewModel.sendCloseBtnAction("");
            //notification_enable_popup_cardview.setVisibility(View.GONE);
        });

        notification_enable_textview.setOnClickListener(view2 ->{
            Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
            startActivity(settingsIntent);
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel= ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getMessage().observe(getViewLifecycleOwner(), s -> {
            notification_enable_popup_cardview.setVisibility(View.GONE);
        });
    }

    private void initScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == notificationData.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        // getConnectNotificationsFromAPI(true, prepareGetConnectsNotificationRequest(true, false),false);
        new ConnectsNotificationInsertionTODB(getActivity(), realmManager, realm, docID, true, false, realmManager.getNotificationDataFromDB(), new ConnectNotificationPreDataListener() {
            @Override
            public void notifyUIWithPreData(JSONArray data) {

                if(data.length()>0){
                    recycleAdapter.notifyDataSetChanged();
                    setUI();
                }else{
                    Toast.makeText(getActivity(),"No more Notifications",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateuireceiver = new updateUIreceiver();
        LocalBroadcastManager.getInstance(App_Application.getInstance()).registerReceiver(updateuireceiver, new IntentFilter("NotificationCount"));
    }

    private void setUI() {
        if (notificationData.size() > 0) {
            tv_notify_connects.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            tv_notify_connects.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }


    private class updateUIreceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            recycleAdapter.notifyDataSetChanged();
            setUI();
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
        if (!realm.isClosed())
            realm.close();
    }

    @Override
    public void onStop() {
        for(int i=0 ;i<default_inviteResponseConnect.size();i++) {
            String data = default_inviteResponseConnect.get(i).getNotifyData();

            try {
                JSONObject jsonObject = new JSONObject(data);
                String notificationId = jsonObject.optString("noti_id");
                realmManager.updateConnectsNotificationReadStatus(notificationId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onStop();

    }

}

