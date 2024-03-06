package com.vam.whitecoats.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryDistributionInfo;
import com.vam.whitecoats.core.models.CategoryDistributionsDataResponse;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.CategoryFeedsDataResponse;
import com.vam.whitecoats.core.models.SubCategoriesDataResponse;
import com.vam.whitecoats.core.models.SubCategoriesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class CategoryLoadingActivity extends BaseActionBarActivity {

    private FrameLayout frame_layout;
    private AVLoadingIndicatorView aviInCategory;
    private Realm realm;
    private RealmManager realmManager;
    private Category currentCategory;
    private TextView mTitleTextView;
    private TextView noFeeds_textView;
    private SwipeRefreshLayout category_swipe_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_loading);

        frame_layout=(FrameLayout)findViewById(R.id.frame_layout);
        aviInCategory=(AVLoadingIndicatorView)findViewById(R.id.aviInCategory);
        noFeeds_textView=(TextView)findViewById(R.id.noFeeds_textView);
        category_swipe_refresh=(SwipeRefreshLayout)findViewById(R.id.category_swipe_refresh);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(this);

        Intent intent1 = this.getIntent();
        Bundle bundle = intent1.getExtras();

        currentCategory=(Category)bundle.getSerializable("CurrentCategoryDetails");
        if(!currentCategory.getCategoryName().isEmpty()){
            mTitleTextView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(currentCategory.getCategoryName());
        }else{
            mTitleTextView.setVisibility(View.GONE);
        }
        if (AppUtil.isConnectingToInternet(CategoryLoadingActivity.this)) {
            aviInCategory.setVisibility(View.VISIBLE);
            loadCategoryData();
        }
        category_swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(CategoryLoadingActivity.this)) {
                    loadCategoryData();
                } else if (category_swipe_refresh != null) {
                    category_swipe_refresh.setRefreshing(false);
                }
            }
        });
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    private void loadCategoryData() {
        CategoryDistributionRepository categoriesData = new CategoryDistributionRepository(realmManager.getDoc_id(realm), currentCategory.getCategoryId(), 0);
        categoriesData.initRequest(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_DISTRIBUTION, "USER_CATEGORY_LIST", AppUtil.getRequestHeaders(CategoryLoadingActivity.this));
        categoriesData.getUserCategoryDistributionList().observe(this, (Observer<CategoriesDistributionApiResponse>) categoriesDistributionApiResponse -> {

            String success_response = categoriesDistributionApiResponse.getSuccess();
            aviInCategory.setVisibility(View.GONE);
            category_swipe_refresh.setRefreshing(false);
            if (categoriesDistributionApiResponse.isSuccess()) {
                if (success_response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(success_response);
                        Log.i("response", jsonObject.toString());
                        if (jsonObject.getString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR)) {
                            // mutableLiveData.setValue(new CategoriesApiResponse(successResponse));
                        }else{
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            if (success_response.contains("feed_data") && success_response.contains("category_distributions")) {
                                //displaytabs screen.
                                CategoryDistributionsDataResponse categoryDataResponse = gson.fromJson(success_response, CategoryDistributionsDataResponse.class);
                                CategoryFeedsDataResponse categoryFeedDataResponse = gson.fromJson(success_response, CategoryFeedsDataResponse.class);
                                List<CategoryFeeds> responseList = categoryFeedDataResponse.getData().getFeedData();
                                List<CategoryDistributionInfo> distributionsList = categoryDataResponse.getData().getCategoryDistributions();
                                ArrayList<CategoryFeeds> feedCategoriesList = new ArrayList<>();
                                ArrayList<CategoryDistributionInfo> CategoriesCategoryDistributionsList = new ArrayList<>();
                                if(responseList!=null) {
                                    feedCategoriesList.addAll(responseList);
                                }
                                if(distributionsList!=null) {
                                    CategoriesCategoryDistributionsList.addAll(distributionsList);
                                }
                                if(CategoriesCategoryDistributionsList!=null && CategoriesCategoryDistributionsList.size()>0) {
                                    Intent intent = new Intent(CategoryLoadingActivity.this, CategoryDistributionTabsActivity.class);
                                    intent.putExtra("feeds_list", feedCategoriesList);
                                    intent.putExtra("Categories_Category_DistributionsList", CategoriesCategoryDistributionsList);
                                    intent.putExtra("Category_Name", categoryDataResponse.getData().getCategoryName());
                                    intent.putExtra("Category_id", categoryDataResponse.getData().getCategoryId());
                                    intent.putExtra("Category_type",categoryDataResponse.getData().getCategoryType());
                                    startActivity(intent);
                                    finish();
                                }else{
                                    noFeeds_textView.setVisibility(View.VISIBLE);
                                    noFeeds_textView.setText(getString(R.string.no_data_available));
                                }
                            } else if (success_response.contains("feed_data")) {
                                //display feeds screen.
                                CategoryFeedsDataResponse categoryDataResponse = gson.fromJson(success_response, CategoryFeedsDataResponse.class);
                                List<CategoryFeeds> responseList = categoryDataResponse.getData().getFeedData();
                                ArrayList<CategoryFeeds> feedCategoriesList = new ArrayList<>();
                                if(responseList!=null) {
                                    feedCategoriesList.addAll(responseList);
                                }
                                if(feedCategoriesList!=null && feedCategoriesList.size()>0) {
                                    Intent intent = new Intent(CategoryLoadingActivity.this, FeedCategoryDistributionActivity.class);
                                    intent.putExtra("feeds_list", feedCategoriesList);
                                    intent.putExtra("Category_Name", categoryDataResponse.getData().getCategoryName());
                                    intent.putExtra("Category_id", categoryDataResponse.getData().getCategoryId());
                                    intent.putExtra("Category_type",categoryDataResponse.getData().getCategoryType());
                                    startActivity(intent);
                                    finish();
                                }else{
                                    noFeeds_textView.setVisibility(View.VISIBLE);
                                    noFeeds_textView.setText(getString(R.string.no_data_available));
                                }
                            } else if (success_response.contains("sub_categories")) {
                                //display sub_categories screen.
                                SubCategoriesDataResponse categoryDataResponse = gson.fromJson(success_response, SubCategoriesDataResponse.class);
                                List<SubCategoriesInfo> responseList = categoryDataResponse.getData().getSubCategories();
                                ArrayList<SubCategoriesInfo> feedCategoriesList = new ArrayList<>();
                                if(responseList!=null) {
                                    feedCategoriesList.addAll(responseList);
                                }
                                if(feedCategoriesList!=null && feedCategoriesList.size()>0) {
                                    Intent intent = new Intent(CategoryLoadingActivity.this, SubCategoriesListActivity.class);
                                    intent.putExtra("Categories_list", feedCategoriesList);
                                    intent.putExtra("Category_Name", categoryDataResponse.getData().getCategoryName());
                                    intent.putExtra("Category_id", categoryDataResponse.getData().getCategoryId());
                                    intent.putExtra("Navigation_from", "Dashboard");
                                    intent.putExtra("Category_type",categoryDataResponse.getData().getCategoryType());
                                    startActivity(intent);
                                    finish();
                                }else{
                                    noFeeds_textView.setVisibility(View.VISIBLE);
                                    noFeeds_textView.setText(getString(R.string.no_data_available));
                                }
                            }else{
                                noFeeds_textView.setVisibility(View.VISIBLE);
                                noFeeds_textView.setText(getString(R.string.no_data_available));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else {
                aviInCategory.setVisibility(View.GONE);
                displayErrorScreen(categoriesDistributionApiResponse.getSuccess());
            }

        });
    }

    @Override
    protected void setCurrentActivity() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();

        }
        return true;
    }
}