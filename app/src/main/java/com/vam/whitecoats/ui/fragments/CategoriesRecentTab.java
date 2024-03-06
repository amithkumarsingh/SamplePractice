package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.CategoryFeedsDataResponse;
import com.vam.whitecoats.core.models.FeedsBySpecInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.FragmentCategoriesRecentTabBinding;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CategorySearchActivity;
import com.vam.whitecoats.ui.activities.EmptyActivity;
import com.vam.whitecoats.ui.adapters.FeedsCategoryDistributionAdapter;
import com.vam.whitecoats.ui.interfaces.CategoryFeedsItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.FeedsApiResponse;
import com.vam.whitecoats.utils.FeedsBySpecApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.RecentCategoriesViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesRecentTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesRecentTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FeedsCategoryDistributionAdapter adapter;
    private ArrayList<CategoryFeeds> feeds_list=new ArrayList<>();
    private String category_name;
    private int category_id;
    private RecentCategoriesViewModel dataViewModel;
    private Realm realm;
    private RealmManager realmManager;
    private RecyclerView categoryRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page_num=0;
    private boolean loading=false;
    private LinearLayoutManager linearLayoutManager;
    private String navigationFrom;
    private int sub_category_id;
    private int speciality_Id;
    private Boolean mIsListExhausted=false;
    private String category_type;
    private EditText search;

    public CategoriesRecentTab() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static CategoriesRecentTab newInstance(ArrayList<CategoryFeeds> feedsList, int categoryId, int sub_category_id, String navigationFrom, int specialityId, String categoryType, boolean fromDistributionRecent, String categoryName,String MainCategoryName) {
        CategoriesRecentTab fragment = new CategoriesRecentTab();
        Bundle args = new Bundle();
        args.putSerializable("feeds_list", feedsList);
        args.putString("Navigation_From", navigationFrom);
        args.putInt("Category_Id", categoryId);
        args.putInt("Sub_Category_Id", sub_category_id);
        args.putInt("Speciality_Id", specialityId);
        args.putString("Category_type",categoryType);
        args.putBoolean("FromDistributionRecent",fromDistributionRecent);
        args.putString("CategoryName",categoryName);
        args.putString("SubCategoryNameMain",MainCategoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feeds_list = (ArrayList<CategoryFeeds>) getArguments().getSerializable("feeds_list");
            navigationFrom = getArguments().getString("Navigation_From");
            category_id = getArguments().getInt("Category_Id");
            sub_category_id = getArguments().getInt("Sub_Category_Id");
            speciality_Id=getArguments().getInt("Speciality_Id");
            category_type=getArguments().getString("Category_type");
            category_name=getArguments().getString("CategoryName");
        }

        dataViewModel= ViewModelProviders.of(this).get(RecentCategoriesViewModel.class);
        dataViewModel.setIsEmptyMsgVisibility(false);
        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());

        if(AppUtil.isConnectingToInternet(getActivity())) {
            dataViewModel.displayLoader();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCategoriesRecentTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories_recent_tab,container,false);
        adapter=new FeedsCategoryDistributionAdapter(new CategoryFeedsItemClickListener() {
            @Override
            public void onItemClicked(CategoryFeeds categoryFeeds) {
                if(categoryFeeds !=null) {
                    if(AppUtil.isConnectingToInternet(getActivity())) {
                        try {
                            String eventName="";
                        if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                            eventName="SubCategoryFeedFullView";
                        }else if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("Categories_Tab")){
                            eventName="CategoryDistributionSpecialityFeedsFullView";
                        }else{
                             if(getArguments().getBoolean("FromDistributionRecent")) {
                                 eventName = "CategoryDistributionRecentFeedsFullView";
                             }else {
                                 eventName = "RecentFeedsFullView";
                             }
                        }
                        JSONObject jsonObjectEvent=new JSONObject();
                            jsonObjectEvent.put("DocID",realmManager.getUserUUID(realm));
                            if(navigationFrom!=null&&navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                                jsonObjectEvent.put("SubCategoryName",getArguments().getString("CategoryName"));
                                jsonObjectEvent.put("CategoryName",getArguments().getString("SubCategoryNameMain"));
                            }else if(navigationFrom!=null&&navigationFrom.equalsIgnoreCase("Categories_Tab")){
                                jsonObjectEvent.put("CategoryName",getArguments().getString("SubCategoryNameMain"));
                                jsonObjectEvent.put("SpecialityID",speciality_Id);
                                jsonObjectEvent.put("SpecialityName",category_name);
                            }else{
                                jsonObjectEvent.put("CategoryName",getArguments().getString("CategoryName"));
                            }
                            jsonObjectEvent.put("FeedID",categoryFeeds.getFeedId());
                            jsonObjectEvent.put("ChannelID",categoryFeeds.getChannelId());
                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),eventName,jsonObjectEvent,AppUtil.convertJsonToHashMap(jsonObjectEvent),getActivity());

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put(RestUtils.TAG_DOC_ID, realmManager.getDoc_id(realm));
                            jsonObject.put(RestUtils.CHANNEL_ID, categoryFeeds.getChannelId());
                            jsonObject.put(RestUtils.TAG_TYPE, categoryFeeds.getFeedTypeId());
                            jsonObject.put(RestUtils.FEED_ID, categoryFeeds.getFeedId());
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

            }
        });
        if(AppUtil.isConnectingToInternet(getContext())) {
            if (feeds_list != null && feeds_list.size() > 0) {
                dataViewModel.displayUIBasedOnCount(feeds_list.size());
                adapter.setDataList(feeds_list);
            } else {
                if (navigationFrom != null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")) {
                    // service for subCategory click to display feeds.
                    setRequestFeedData(0);
                    getFeedsInSubcategoryAPI();
                    getFeedsInSubCategoryAPIListListExhausted();
                } else {
                    setRequestDataForFeedsBySpec(0);
                    callBackFromFeedsBySpec();
                    getFeedsBySpecAPIListListExhausted();
                }
            }
        }

        binding.setAdapter(adapter);
        binding.setViewModel(dataViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        categoryRecycler = binding.feedList;
        swipeRefreshLayout = binding.exploreSwipeRefresh;
        //search=binding.categorySearch;
        search= view.findViewById(R.id.et_search_drug);
        if(getArguments().getBoolean("FromDistributionRecent")){
            search.setVisibility(View.GONE);
        }else{
            search.setVisibility(View.VISIBLE);
            search.setHint("Search"+" "+category_name);
        }
        search.setOnClickListener(view1 -> {
            Intent intent=new Intent(getContext(), CategorySearchActivity.class);
            int categoryId;
            if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                categoryId=sub_category_id;
            }else{
                categoryId=category_id;
            }
            intent.putExtra("CategoryId",categoryId);
            intent.putExtra("CategoryType",category_type);
            intent.putExtra("CategoryName",category_name);
            intent.putExtra("NavigationFrom",navigationFrom);
            startActivity(intent);
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if(AppUtil.isConnectingToInternet(getActivity())) {
                page_num = 0;
                if(navigationFrom!=null && navigationFrom.equalsIgnoreCase("Categories_Tab")){
                    setRequestDataForFeedsBySpec(0);
                    callBackFromFeedsBySpec();
                    getFeedsBySpecAPIListListExhausted();
                }else if(navigationFrom!=null&&navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                    feeds_list.clear();
                    adapter.notifyDataSetChanged();
                    setRequestFeedData(0);
                    getFeedsInSubcategoryAPI();
                    getFeedsInSubCategoryAPIListListExhausted();
                }else {
                    feeds_list.clear();
                    adapter.notifyDataSetChanged();
                    setRequestData(page_num);
                    callBackFromService();
                }
            }else{
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        categoryRecycler.setAdapter(adapter);


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycler.setLayoutManager(linearLayoutManager);

        categoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(mIsListExhausted || !AppUtil.isConnectingToInternet(getActivity())){
                    return;
                }
                    int toatlcount = linearLayoutManager.getItemCount();
                    int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading) {
                        if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1)) {
                            adapter.addDummyItemToList();
                            page_num += 1;
                            if (navigationFrom != null && navigationFrom.equalsIgnoreCase("Categories_Tab")) {
                                setRequestDataForFeedsBySpec(page_num);
                                callBackFromFeedsBySpec();
                                getFeedsBySpecAPIListListExhausted();
                            }else if(navigationFrom!=null&&navigationFrom.equalsIgnoreCase("SubCategoriesActivity")){
                                setRequestFeedData(page_num);
                                getFeedsInSubcategoryAPI();
                                getFeedsInSubCategoryAPIListListExhausted();
                            } else{
                                setRequestData(page_num);
                                callBackFromService();
                            }
                            loading = true;
                        } else {
                            loading = false;
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy);

                }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        categoryRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return  view;
    }

    private void getFeedsBySpecAPIListListExhausted() {
        dataViewModel.feedsBasedOnSpecIsListExhausted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mIsListExhausted=aBoolean;
                if(mIsListExhausted){
                    Toast.makeText(getContext(),"No more feeds",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFeedsInSubCategoryAPIListListExhausted() {
        dataViewModel.subCatFeedsIsListExhausted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mIsListExhausted=aBoolean;
                if(mIsListExhausted){
                    Toast.makeText(getContext(),"No more feeds",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFeedsInSubcategoryAPI() {
        dataViewModel.getFeeds().observe(getViewLifecycleOwner(), new Observer<FeedsApiResponse>() {
            @Override
            public void onChanged(FeedsApiResponse feedsApiResponse) {
                adapter.removeDummyItemFromList();
                if(feedsApiResponse==null){
                    return;
                }
                if(feedsApiResponse.getError()==null){
                    dataViewModel.displayUIBasedOnCount(feedsApiResponse.getSubCategoryFeeds().size());
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    List<CategoryFeeds> responseList = feedsApiResponse.getSubCategoryFeeds();
                    ArrayList<CategoryFeeds> feedCategoriesList = new ArrayList<>();
                    if(responseList!=null) {
                        feedCategoriesList.addAll(responseList);
                    }
                    if(feedCategoriesList!=null&&feedCategoriesList.size()>0) {
                        feeds_list = feedCategoriesList;
                        adapter.setDataList(feeds_list);
                    }/*else{
                        Toast.makeText(getContext(),"No more feeds",Toast.LENGTH_SHORT).show();
                    }*/
                }else{
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    ((BaseActionBarActivity)getActivity()).displayErrorScreen(feedsApiResponse.getError());
                }
            }
        });
    }

    private void callBackFromFeedsBySpec() {
        dataViewModel.getFeedsBySpec().observe(getViewLifecycleOwner(), new Observer<FeedsBySpecApiResponse>() {
            @Override
            public void onChanged(FeedsBySpecApiResponse feedsBySpecApiResponse) {
                adapter.removeDummyItemFromList();
                if(feedsBySpecApiResponse==null){
                    return;
                }
                if(feedsBySpecApiResponse.getError()==null){
                    dataViewModel.displayUIBasedOnCount(feedsBySpecApiResponse.getFeedsBySpec().size());
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    List<FeedsBySpecInfo> responseList = feedsBySpecApiResponse.getFeedsBySpec();
                    ArrayList<FeedsBySpecInfo> feedCategoriesList = new ArrayList<>();
                    if(responseList!=null) {
                        feedCategoriesList.addAll(responseList);
                    }
                    if(feedCategoriesList.size()>0) {
                        dataViewModel.displayUIBasedOnCount(feedCategoriesList.size());
                        CategoryFeeds categoryFeeds;
                        ArrayList<CategoryFeeds> feeds_by_spec_list=new ArrayList<>();
                        if(page_num==0){
                            feeds_by_spec_list.clear();
                            adapter.notifyDataSetChanged();
                        }
                        for (int i = 0; i < feedCategoriesList.size(); i++) {
                            categoryFeeds=new CategoryFeeds();
                            categoryFeeds.setFeedId(feedCategoriesList.get(i).getFeedId());
                            categoryFeeds.setFeedTypeId(feedCategoriesList.get(i).getFeedTypeId());
                            categoryFeeds.setChannelId(feedCategoriesList.get(i).getChannelId());
                            categoryFeeds.setTitle(feedCategoriesList.get(i).getTitle());
                            categoryFeeds.setShortDesc(feedCategoriesList.get(i).getShortDesc());
                            categoryFeeds.setAttachmentDetails(feedCategoriesList.get(i).getAttachmentDetails());
                            feeds_by_spec_list.add(categoryFeeds);
                        }
                        adapter.setDataList(feeds_by_spec_list);
                    }/*else{
                        Toast.makeText(getContext(),"No more feeds",Toast.LENGTH_SHORT).show();
                    }*/
                }else{
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    ((BaseActionBarActivity)getActivity()).displayErrorScreen(feedsBySpecApiResponse.getError());
                }

            }
        });
    }


    private void callBackFromService() {
        dataViewModel.getAllCategoryFeeds().observe(getViewLifecycleOwner(), new Observer<CategoriesDistributionApiResponse>() {
            @Override
            public void onChanged(CategoriesDistributionApiResponse categoriesDistributionApiResponse) {
              //  Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                adapter.removeDummyItemFromList();
                String success = categoriesDistributionApiResponse.getSuccess();
                if (categoriesDistributionApiResponse.isSuccess()) {
                    if (success != null) {
                        ArrayList<CategoryFeeds> feedCategoriesList = new ArrayList<>();;
                        if (success.contains("feed_data")) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            CategoryFeedsDataResponse feedDataResponse = gson.fromJson(success, CategoryFeedsDataResponse.class);
                            List<CategoryFeeds> responseList = feedDataResponse.getData().getFeedData();
                            if(responseList.size()!=0){
                                mIsListExhausted=false;
                            }else{
                                mIsListExhausted=true;
                            }                            feedCategoriesList = new ArrayList<>();
                            if(responseList!=null) {
                                feedCategoriesList.addAll(responseList);
                            }
                            if(feedCategoriesList.size()>0) {
                                dataViewModel.displayUIBasedOnCount(feedCategoriesList.size());
                                if(page_num==0){
                                    feeds_list.clear();
                                }
                                for(int i = 0; i < responseList.size(); i++){
                                CategoryFeeds categoryFeeds = new CategoryFeeds();
                                categoryFeeds.setFeedId(responseList.get(i).getFeedId());
                                categoryFeeds.setFeedTypeId(responseList.get(i).getFeedTypeId());
                                categoryFeeds.setChannelId(responseList.get(i).getChannelId());
                                categoryFeeds.setTitle(responseList.get(i).getTitle());
                                categoryFeeds.setShortDesc(responseList.get(i).getShortDesc());
                                categoryFeeds.setAttachmentDetails(responseList.get(i).getAttachmentDetails());
                                feeds_list.add(categoryFeeds);
                                }
                                adapter.setDataList(feeds_list);
                            }else{
                                Toast.makeText(getContext(),"No more feeds",Toast.LENGTH_SHORT).show();
                            }
                        }

                        loading = false;
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }else{
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    ((BaseActionBarActivity)getActivity()).displayErrorScreen(categoriesDistributionApiResponse.getSuccess());
                }

            }
        });
    }

    private void setRequestData(int page_num) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_DISTRIBUTION, "USER_CATEGORY_LIST",AppUtil.getRequestHeaders(getActivity()),page_num,realmManager.getDoc_id(realm),category_id);

    }
    private void setRequestFeedData(int page_num) {
        dataViewModel.setRequestFeedsData(Request.Method.POST, RestApiConstants.GET_USER_SUB_CATEGORY_FEEDS, "USER_CATEGORY_LIST",AppUtil.getRequestHeaders(getActivity()),page_num,realmManager.getDoc_id(realm),category_id,sub_category_id);
    }
    private void setRequestDataForFeedsBySpec(int pageNum) {
        dataViewModel.setRequestFeedsDataBySpec(Request.Method.POST, RestApiConstants.GET_USER_FEEDS_BY_SPECIALITY, "USER_CATEGORY_LIST",AppUtil.getRequestHeaders(getActivity()),pageNum,speciality_Id,category_id);
    }



}