package com.vam.whitecoats.ui.activities;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.SubCategoriesDataResponse;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.ActivitySubCategoriesListBinding;
import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.ui.adapters.SubCategoriesAdapter;
import com.vam.whitecoats.ui.interfaces.SubCategoriesItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.EndlessRecyclerOnScrollListener;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.viewmodel.SubCategoriesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SubCategoriesListActivity extends BaseActionBarActivity {

    private SubCategoriesAdapter adapter;
    ArrayList<SubCategoriesInfo> categoriesList = new ArrayList<SubCategoriesInfo>();
    private SubCategoriesViewModel dataViewModel;
    private RecyclerView categoryRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_num = 0;
    CategoryDistributionRepository categoriesData;
    private TextView mTitleTextView;
    private String categoryName;
    private Realm realm;
    private RealmManager realmManager;
    private int categoryId;
    private boolean loading = false;
    private String navigationFrom;
    private EndlessRecyclerOnScrollListener recyclerScrollListener;
    private LinearLayoutManager linearLayoutManager;
    private boolean mIsListExhausted = false;
    private int categoryUnreadCount=0;
    private String categoryType;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataViewModel = ViewModelProviders.of(this).get(SubCategoriesViewModel.class);
        dataViewModel.setIsEmptyMsgVisibility(false);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

        if (getIntent().getExtras() != null) {
            categoriesList = (ArrayList<SubCategoriesInfo>) getIntent().getSerializableExtra("Categories_list");
            categoryName = getIntent().getStringExtra("Category_Name");
            categoryId = getIntent().getIntExtra("Category_id", 0);
            categoryType=getIntent().getStringExtra("Category_type");
            navigationFrom = getIntent().getStringExtra("Navigation_from");
            for(SubCategoriesInfo subCategoriesInfo:categoriesList){
                categoryUnreadCount=categoryUnreadCount+subCategoriesInfo.getUnreadCount();
            }
        }

        ActivitySubCategoriesListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_categories_list);

        adapter = new SubCategoriesAdapter(this,new SubCategoriesItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if (AppUtil.isConnectingToInternet(SubCategoriesListActivity.this)) {
                    try {
                        String eventName = "SubCategorySelected";
                        JSONObject jsonObjectEvent = new JSONObject();
                        jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                        jsonObjectEvent.put("CategoryName",categoryName);
                        jsonObjectEvent.put("SubCategoryName",categoriesList.get(position).getSubCategoryName());
                        jsonObjectEvent.put(RestUtils.EVENT_DOC_SPECIALITY,realmManager.getDocSpeciality(realm));
                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),SubCategoriesListActivity.this);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(SubCategoriesListActivity.this, FeedCategoryDistributionActivity.class);
                    intent.putExtra("CategoryIdMain", categoryId);
                    intent.putExtra("SubCategoryId", categoriesList.get(position).getSubCategoryId());
                    intent.putExtra("Navigation_from", "SubCategoriesActivity");
                    intent.putExtra("Category_Name", categoriesList.get(position).getSubCategoryName());
                    intent.putExtra("Category_type",categoryType);
                    intent.putExtra("SubCategoryName", categoriesList.get(position).getSubCategoryName());
                    intent.putExtra("CategoryNameMain", categoryName);
                    startActivity(intent);
                    EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate",categoryUnreadCount-categoriesList.get(position).getUnreadCount(),categoryId));
                }
            }

            @Override
            public void onItemClickedData(SubCategoriesInfo dataModel, boolean checkboxclicked) {

            }
        });
        if (AppUtil.isConnectingToInternet(this)) {
            if (categoriesList.size() > 0) {
                dataViewModel.displayLoader();
                dataViewModel.displayUIBasedOnCount(categoriesList.size());
                adapter.setDataList(categoriesList);
            } else {
                dataViewModel.setIsEmptyMsgVisibility(true);
            }
        }
        binding.setAdapter(adapter);
        binding.setViewModel(dataViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        categoryRecycler = binding.subCategoriesList;
        swipeRefreshLayout = binding.exploreSwipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(SubCategoriesListActivity.this)) {
                    page_num = 0;
                    setRequestData(page_num);
                    callBackFromRepository();
                } else {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        upshotEventData(0,0,0,"","SubCategory","","", " ",true);
        if (!categoryName.isEmpty()) {
            mTitleTextView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(categoryName);
        }
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        categoryRecycler.setAdapter(adapter);

        /*categoryRecycler.addItemDecoration(HeaderDecoration.with(categoryRecycler)
                .inflate(R.layout.empty_layout)
                .parallax(0.9f)
                .dropShadowDp(1)
                .build(0, getApplicationContext()));*/
        categoryRecycler.addItemDecoration(new SimpleDividerItemDecoration(SubCategoriesListActivity.this));

        linearLayoutManager = new LinearLayoutManager(SubCategoriesListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycler.setLayoutManager(linearLayoutManager);
        recyclerScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                if (mIsListExhausted || !AppUtil.isConnectingToInternet(SubCategoriesListActivity.this)) {
                    return;
                }
                int toatlcount = linearLayoutManager.getItemCount();
                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                if (!loading) {
                    if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1)) {
                        adapter.addDummyItemToList();
                        page_num += 1;
                        setRequestData(page_num);
                        callBackFromRepository();
                    } else {
                        loading = false;
                    }
                }
            }
        };
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        categoryRecycler.addOnScrollListener(recyclerScrollListener);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);

    }

    private void callBackFromRepository() {
        dataViewModel.getAllSubCategories().observe(this, new Observer<CategoriesDistributionApiResponse>() {
            @Override
            public void onChanged(CategoriesDistributionApiResponse categoriesDistributionApiResponse) {
                adapter.removeDummyItemFromList();
                String success = categoriesDistributionApiResponse.getSuccess();
                if (categoriesDistributionApiResponse.isSuccess()) {
                    if (success != null) {
                        ArrayList<SubCategoriesInfo> feedCategoriesList = new ArrayList<>();
                        ;
                        if (success.contains("sub_categories")) {
                            //display sub_categories screen
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            SubCategoriesDataResponse categoryDataResponse = gson.fromJson(success, SubCategoriesDataResponse.class);
                            List<SubCategoriesInfo> responseList = categoryDataResponse.getData().getSubCategories();
                            if (responseList.size() != 0) {
                                mIsListExhausted = false;
                            } else {
                                mIsListExhausted = true;
                            }
                            feedCategoriesList = new ArrayList<>();
                            if (responseList != null) {
                                feedCategoriesList.addAll(responseList);
                            }
                            if (feedCategoriesList != null && feedCategoriesList.size() > 0) {
                                if (page_num == 0) {
                                    categoriesList.clear();
                                    categoryUnreadCount=0;
                                }
                                dataViewModel.displayUIBasedOnCount(feedCategoriesList.size());
                                for (int i = 0; i < responseList.size(); i++) {
                                    SubCategoriesInfo subCategoriesInfo = new SubCategoriesInfo();
                                    subCategoriesInfo.setSubCategoryId(responseList.get(i).getSubCategoryId());
                                    subCategoriesInfo.setCategoryType(responseList.get(i).getCategoryType());
                                    subCategoriesInfo.setImageUrl(responseList.get(i).getImageUrl());
                                    subCategoriesInfo.setRank(responseList.get(i).getRank());
                                    subCategoriesInfo.setSubCategoryName(responseList.get(i).getSubCategoryName());
                                    subCategoriesInfo.setUnreadCount(responseList.get(i).getUnreadCount());
                                    categoriesList.add(subCategoriesInfo);
                                    categoryUnreadCount=categoryUnreadCount+responseList.get(i).getUnreadCount();
                                }
                                adapter.setDataList(categoriesList);
                                EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnSubcategoriesLoad",categoryUnreadCount,categoryId));

                            } else {
                                Toast.makeText(SubCategoriesListActivity.this, "No more data available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //loading = false;
                        swipeRefreshLayout.setRefreshing(false);
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    displayErrorScreen(categoriesDistributionApiResponse.getSuccess());
                }
            }
        });

    }

    @Override
    protected void setCurrentActivity() {

    }

    private void setRequestData(int pageNumber) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_DISTRIBUTION, "USER_CATEGORY_LIST", AppUtil.getRequestHeaders(SubCategoriesListActivity.this), pageNumber, realmManager.getDoc_id(realm), categoryId);

    }

    @Override
    public void onBackPressed() {
        if(!customBackButton) {
            JSONObject jsonObjectEvent = new JSONObject();
            try {
                jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                String eventName = "SubCategoryDeviceBackTapped";
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),SubCategoriesListActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            customBackButton=true;
            JSONObject jsonObjectEvent = new JSONObject();
            try {
                jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                String eventName="SubCategoryBackTapped";
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),SubCategoriesListActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }
}