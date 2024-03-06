package com.vam.whitecoats.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.NotificationInfo;
import com.vam.whitecoats.core.models.ProfessionalInfo;
import com.vam.whitecoats.core.realm.RealmBasicInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.FragmentFeedNotificationBinding;
import com.vam.whitecoats.tools.MySharedPref;
import com.vam.whitecoats.ui.adapters.FeedsNotificationsAdapter;
import com.vam.whitecoats.ui.interfaces.NotificationItemClickListener;
import com.vam.whitecoats.utils.ApiResponse;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.EndlessRecyclerOnScrollListener;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.viewmodel.NotificationViewModel;
import com.vam.whitecoats.viewmodel.SharedViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;


public class FeedNotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView feedsList;
    private NotificationViewModel dataViewModel;


    private JSONObject requestObject;
    int page_num = 0;
    private Realm realm;
    private RealmManager realmManager;
    private RealmBasicInfo basicInfo;
    private FeedsNotificationsAdapter mAdapter;
    private int docId;
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private boolean isListExhausted;
    private MySharedPref sharedPref;
    private ProfessionalInfo realmProfessionInfo;
    String name, speciality, onBoardDate, lastLoginDate, location;
    private SharedViewModel sharedViewModel;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedNotificationFragment newInstance(String param1, String param2) {
        FeedNotificationFragment fragment = new FeedNotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        docId = realmManager.getDoc_id(realm);
        basicInfo = realmManager.getRealmBasicInfo(realm);
        realmProfessionInfo = realmManager.getProfessionalInfoOfShowoncard(realm);
        sharedPref = new MySharedPref(getActivity());
        requestObject = new JSONObject();
        try {
            requestObject.put(RestUtils.TAG_USER_ID, docId);
            requestObject.put(RestUtils.TAG_PAGE_NUM, page_num);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sharedViewModel=ViewModelProviders.of(requireActivity()).get(SharedViewModel.class);
        dataViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        dataViewModel.setIsEmptyMsgVisibility(false);
        dataViewModel.setIsNotificationsEnabled(!AppUtil.areNotificationsEnabled(getActivity()));
        if (AppUtil.isConnectingToInternet(getActivity())) {
            dataViewModel.displayLoader();
            setRequestData(requestObject.toString());
            getFeedNotificationsFromAPI();
        }
        lastLoginDate = sharedPref.getPref(MySharedPref.PREF_LAST_LOGIN_TIME);
        onBoardDate = sharedPref.getPref(MySharedPref.PREF_ON_BOARD_DATE);
        location = realmProfessionInfo.getLocation();
        name = basicInfo.getFname() + " " + basicInfo.getLname();
        speciality = basicInfo.getSplty();
    }

    private void getFeedNotificationsFromAPI() {
        dataViewModel.getAllNotifications().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                mAdapter.removeDummyItemFromList();
                if (recyclerScrollListener != null) {
                    recyclerScrollListener.setLoadMoreStatus(true);
                }
                if (apiResponse == null) {
                    return;
                }
                if (apiResponse.getError() == null) {
                    dataViewModel.displayUIBasedOnCount(apiResponse.getObjList().size());
                    mAdapter.setDataList(apiResponse.getObjList());
                } else {
                    if (AppUtil.isJSONValid(apiResponse.getError())) {
                        try {
                            JSONObject jsonObject = new JSONObject(apiResponse.getError());
                            if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")) {
                                AppUtil.showSessionExpireAlert("Error", getActivity().getResources().getString(R.string.session_timedout), getContext());
                            } else if (jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")) {
                                AppUtil.AccessErrorPrompt(getActivity(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong,please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFeedNotificationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_notification, container, false);

        //dataViewModel.setAdapter();
        //dataViewModel.getAllNotifications().getValue();
        mAdapter = new FeedsNotificationsAdapter(new NotificationItemClickListener() {
            @Override
            public void onItemClick(NotificationInfo notificationInfo, Integer position) {
                if (notificationInfo != null && position != null) {
                    if (!AppUtil.isConnectingToInternet(getActivity())) {
                        return;
                    }
                    notificationInfo.setNotificationRead(true);
                    mAdapter.updateDataItem(position, notificationInfo);

                    JSONObject jsonObjectUpshot = new JSONObject();
                    try {
                        jsonObjectUpshot.put("DocID", realmManager.getUserUUID(realm));
                        jsonObjectUpshot.put("FeedID", notificationInfo.getFeedId());
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "InAppNotificationTapped", jsonObjectUpshot, AppUtil.convertJsonToHashMap(jsonObjectUpshot), getContext());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(RestUtils.TAG_DOC_ID, docId);
                        jsonObject.put(RestUtils.CHANNEL_ID, notificationInfo.getChannelId());
                        jsonObject.put(RestUtils.TAG_TYPE, notificationInfo.getType());
                        jsonObject.put(RestUtils.FEED_ID, notificationInfo.getFeedId());
                        jsonObject.put(RestUtils.TAG_IS_PERSONALIZED_NOTIFICATION, notificationInfo.getIs_personalized());
                        Intent intent = new Intent(getActivity(), EmptyActivity.class);
                        intent.putExtra(RestUtils.KEY_REQUEST_BUNDLE, jsonObject.toString());
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }, name, speciality, location, onBoardDate, lastLoginDate);
        /*ENGG-3376 -- Scroll flickering issue */

        mAdapter.setHasStableIds(true);
        binding.setAdapter(mAdapter);
        binding.setViewModel(dataViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        feedsList = binding.feedsNotificationList;
        feedsList.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        feedsList.setLayoutManager(mLayoutManager);
        recyclerScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (isListExhausted) {
                    return;
                }
                if (AppUtil.isConnectingToInternet(getActivity())) {
                    mAdapter.addDummyItemToList();
                    page_num = page_num + 1;
                    try {
                        requestObject.put(RestUtils.TAG_PAGE_NUM, page_num);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setRequestData(requestObject.toString());
                    getFeedNotificationsFromAPI();
                }
            }
        };
        feedsList.addOnScrollListener(recyclerScrollListener);
        binding.notificationOnTv.setOnClickListener(view1 -> {
            Intent settingsIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
            startActivity(settingsIntent);
        });
        binding.notificationOnCloseIv.setOnClickListener(view12 -> {
            sharedViewModel.sendCloseBtnAction("");
            //dataViewModel.setIsNotificationsEnabled(false);
        });

        dataViewModel.isListExhausted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted = aBoolean;
                if (aBoolean) {
                    Toast.makeText(getActivity(), "No more notifications", Toast.LENGTH_SHORT).show();
                    mAdapter.setListExhausted();
                }
            }
        });

        return view;
    }

    private void setRequestData(String requestString) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_NOTIFICATIONS_LIST_API, requestString, "GET_ALL_NOTIFICATIONS", AppUtil.getRequestHeaders(getActivity()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel.getMessage().observe(getViewLifecycleOwner(), s -> {
            dataViewModel.setIsNotificationsEnabled(false);
        });
    }
}
