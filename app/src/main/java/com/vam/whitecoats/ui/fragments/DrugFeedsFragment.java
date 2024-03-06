package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.activities.EmptyActivity;
import com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter;
import com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener;
import com.vam.whitecoats.ui.interfaces.SubCategoriesItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.DrugFeedsApiResponse;
import com.vam.whitecoats.utils.HeaderDecoration;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.DrugFeedsViewModel;
import com.vam.whitecoats.viewmodel.ExploreViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class DrugFeedsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView feedsRecycler;
    private TextView noFeeds;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private FeedsCategoryDistributionAdapter adapter;

    private DrugFeedsViewModel drugFeedsViewModel;
    private int page_num =0;
    private boolean loading = false;
    private ArrayList<CategoryFeeds> categoryFeeds = new ArrayList<>();
    private Realm realm;
    private RealmManager realmManager;
    private Boolean isListExhausted;

    public DrugFeedsFragment() {
    }
    public static DrugFeedsFragment newInstance(String drugName, int drugId) {
        DrugFeedsFragment fragment = new DrugFeedsFragment();
        Bundle args = new Bundle();
        args.putString("drugName",drugName);
        args.putInt("drugId",drugId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drugFeedsViewModel= ViewModelProviders.of(this).get(DrugFeedsViewModel.class);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug_feeds, container, false);
        swipeRefreshLayout = view.findViewById(R.id.drug_feeds_swipe_refresh);
        feedsRecycler = view.findViewById(R.id.drug_feeds_notification_list);
        noFeeds = view.findViewById(R.id.no_drug_feeds);
        avLoadingIndicatorView = view.findViewById(R.id.drug_feeds_loader);

        noFeeds.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        feedsRecycler.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(getActivity())) {
                    isListExhausted=false;
                    page_num = 0;
                    setRequestData(page_num,getArguments().getString("drugName"));
                    apiCall();
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        adapter=new FeedsCategoryDistributionAdapter(new CategoryFeedsItemClickListener() {
            @Override
            public void onItemClicked(CategoryFeeds category_feeds) {
                if(category_feeds !=null) {
                    if(!AppUtil.isConnectingToInternet(getActivity())){
                        return;
                    }


                    JSONObject jsonObject = new JSONObject();
                    try {

                        JSONObject jsonObjectEvent = new JSONObject();
                        jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                        jsonObjectEvent.put("Channel ID",category_feeds.getChannelId());
                        jsonObjectEvent.put("Feed ID",category_feeds.getFeedId());
                        jsonObjectEvent.put("DrugName",getArguments().getString("drugName"));
                        jsonObjectEvent.put("DrugNameId",getArguments().getInt("drugId"));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), "DrugInfoFeedsFullView", jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),getActivity());

                        jsonObject.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
                        jsonObject.put(RestUtils.CHANNEL_ID, category_feeds.getChannelId());
                        jsonObject.put(RestUtils.TAG_TYPE, category_feeds.getFeedTypeId());
                        jsonObject.put(RestUtils.FEED_ID, category_feeds.getFeedId());
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
        });
        feedsRecycler.setAdapter(adapter);
        /*feedsRecycler.addItemDecoration(HeaderDecoration.with(feedsRecycler)
                .inflate(R.layout.empty_layout)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, getActivity()));*/
        feedsRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        feedsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(isListExhausted || !AppUtil.isConnectingToInternet(getActivity())){
                    return;
                }
                int toatlcount = linearLayoutManager.getItemCount();
                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                if(!loading) {
                    if(lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount -1) && AppUtil.isConnectingToInternet(getActivity())) {
                        adapter.addDummyItemToList();
                        page_num += 1;
                        setRequestData(page_num,getArguments().getString("drugName"));
                        apiCall();
                        loading = true;
                    }else{
                        loading = false;
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        if(AppUtil.isConnectingToInternet(getActivity())) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            setRequestData(page_num,getArguments().getString("drugName"));
            apiCall();


        }
        drugFeedsViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted=aBoolean;
                if(aBoolean) {
                    //Toast.makeText(getActivity(),"No more feeds",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private void apiCall(){
        drugFeedsViewModel.getFeeds().observe(this, new Observer<DrugFeedsApiResponse>() {
            @Override
            public void onChanged(@Nullable DrugFeedsApiResponse apiResponse) {

                if(apiResponse==null){
                    return;
                }
                if(apiResponse.getError()==null){
                    if(page_num == 0){
                        if(apiResponse.getFeeds().size() == 0){
                            noFeeds.setVisibility(View.VISIBLE);
                        }
                        avLoadingIndicatorView.setVisibility(View.GONE);
                    }
                    else{
                        adapter.removeDummyItemFromList();
                    }
                    categoryFeeds =  apiResponse.getFeeds();
                    adapter.setDataList(categoryFeeds);
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    if(AppUtil.isJSONValid(apiResponse.getError())){
                        try {
                            JSONObject jsonObject=new JSONObject(apiResponse.getError());
                            if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("99")){
                                AppUtil.showSessionExpireAlert("Error", getActivity().getResources().getString(R.string.session_timedout), getContext());
                            }
                            else if(jsonObject.optString(RestUtils.TAG_ERROR_CODE).equals("603")){
                                AppUtil.AccessErrorPrompt(getActivity(), jsonObject.optString(RestUtils.TAG_ERROR_MESSAGE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(),"Something went wrong,please try again.",Toast.LENGTH_SHORT).show();
                    }
                }
                avLoadingIndicatorView.setVisibility(View.GONE);
            }
        });
    }
    private void setRequestData(int pageNumber,String drugName) {
        drugFeedsViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_DRUG_FEEDS, "DRUG_FEEDS_LIST", AppUtil.getRequestHeaders(getActivity()),pageNumber,drugName);
    }
}