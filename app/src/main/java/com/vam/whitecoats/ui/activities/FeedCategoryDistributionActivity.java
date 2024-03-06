package com.vam.whitecoats.ui.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.vam.whitecoats.App_Application;
import com.vam.whitecoats.R;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.SpecialitiesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter;
import com.vam.whitecoats.ui.fragments.CategoriesRecentTab;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.viewmodel.FeedCategoriesViewModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class FeedCategoryDistributionActivity extends BaseActionBarActivity {

    private FeedsCategoryDistributionAdapter adapter;
    private FeedCategoriesViewModel dataViewModel;
    private Realm realm;
    private RealmManager realmManager;
    private RecyclerView feedsRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_num;
    private TextView mTitleTextView;
    private ArrayList<CategoryFeeds> feedsList;
    private String categoryName;
    private int categoryId;
    private int feeds_category_id;
    private int sub_category_id;
    private String navigationFrom;
    private CategoriesRecentTab categoriesRecentTab;
    private static final int CONTENT_VIEW_ID = 10101010;
    private SpecialitiesInfo specialityInfo;
    private int speciality_id=0;
    private String categoryType;
    private String MainCategoryName;
    private boolean customBackButton=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(this);
        mCustomView = mInflater.inflate(R.layout.actionbar_community, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_communityHeading);
        actionBar.setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(FeedCategoryDistributionActivity.this);

        if(getIntent().getExtras()!=null) {

            feedsList = (ArrayList<CategoryFeeds>) getIntent().getSerializableExtra("feeds_list");
            categoryName = getIntent().getStringExtra("Category_Name");
            navigationFrom = getIntent().getStringExtra("Navigation_from");
            if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                categoryId = getIntent().getIntExtra("CategoryIdMain", 0);
                sub_category_id = getIntent().getIntExtra("SubCategoryId", 0);
                speciality_id=0;
                categoryType=getIntent().getStringExtra("Category_type");
                MainCategoryName=getIntent().getStringExtra("SubCategoryName");
            }else if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("Categories_Tab")){
                specialityInfo= (SpecialitiesInfo) getIntent().getSerializableExtra("Speciality_object");
                speciality_id=specialityInfo.getSpecialityId();
                categoryName = specialityInfo.getSpecialityName();
                categoryId = getIntent().getIntExtra("Category_id", 0);
                categoryType="";
                MainCategoryName =getIntent().getStringExtra("CategoryName");

            }else {
                categoryId = getIntent().getIntExtra("Category_id", 0);
                sub_category_id = 0;
                speciality_id=0;
                categoryType=getIntent().getStringExtra("Category_type");
                EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate",0,categoryId));
                //EventBus.getDefault().post(new CategoryItemClickEvent("OnRegularUnreadCountUpdate",null));
            }
        }
        String navigatedFrom="";
        if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("Categories_Tab")){
            navigatedFrom="CategoryDistributionSpecialityFeeds";
        }else if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")) {
            navigatedFrom="SubCategoryFeeds";
        }else{
            navigatedFrom="RecentFeeds";
        }
        upshotEventData(0,0,0,"",navigatedFrom,"","", " ",true);


        if(categoryName!=null && !categoryName.isEmpty()) {
            mTitleTextView.setVisibility(View.VISIBLE);
            mTitleTextView.setText(categoryName);
        }else{
            mTitleTextView.setVisibility(View.GONE);
        }

        FrameLayout frame = new FrameLayout(this);
        frame.setId(CONTENT_VIEW_ID);
        setContentView(frame, new ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        categoriesRecentTab=CategoriesRecentTab.newInstance(feedsList,categoryId,sub_category_id,navigationFrom,speciality_id,categoryType, false, categoryName, MainCategoryName);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(CONTENT_VIEW_ID, categoriesRecentTab );
            fragmentTransaction.addToBackStack(categoriesRecentTab.toString());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(mCustomView);
    }

    @Override
    protected void setCurrentActivity() {
        App_Application.setCurrentActivity(this);
    }

    @Override
    public void onBackPressed() {
        if(!customBackButton) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("DocID", realmManager.getUserUUID(realm));
                String eventName = "";
                if (navigationFrom != null && navigationFrom.equalsIgnoreCase("Categories_Tab")) {
                    eventName = "CategoryDistributionSpecialityFeedsDeviceBackTapped";
                } else if (navigationFrom != null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")) {
                    eventName = "SubCategoryFeedsDeviceBackTapped";
                } else {
                    eventName = "RecentFeedsDeviceBackTapped";
                }
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObject, AppUtil.convertJsonToHashMap(jsonObject),FeedCategoryDistributionActivity.this);
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
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("DocID",realmManager.getUserUUID(realm));
                String eventName="";
                if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("Categories_Tab")){
                    eventName="CategoryDistributionSpecialityFeedsBackTapped";
                }else if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")) {
                    eventName="SubCategoryFeedsBackTapped";
                }else{
                    eventName="RecentFeedsBackTapped";
                }
                AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),eventName,jsonObject,AppUtil.convertJsonToHashMap(jsonObject),FeedCategoryDistributionActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onBackPressed();

        }
        return true;
    }

}