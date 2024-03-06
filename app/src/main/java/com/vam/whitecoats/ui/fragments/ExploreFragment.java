package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vam.whitecoats.App_Application;
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
import com.vam.whitecoats.databinding.FragmentExploreBinding;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CategoryLoadingActivity;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.activities.CategoryDistributionTabsActivity;
import com.vam.whitecoats.ui.activities.FeedCategoryDistributionActivity;
import com.vam.whitecoats.ui.activities.SubCategoriesListActivity;
import com.vam.whitecoats.ui.adapters.ExploreAdapter;
import com.vam.whitecoats.ui.interfaces.ExploreItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.GridSpaceDecorator;
import com.vam.whitecoats.utils.RequestLocationType;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.viewmodel.ExploreViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class ExploreFragment extends Fragment {

    private RecyclerView categoryRecycler;
    private ExploreAdapter exploreAdapter;
    //private List<Category> exploreItemList;
    private JSONObject requestObject;
    int page_num=0;
    private Realm realm;
    private RealmManager realmManager;
    private boolean isListExhausted;
    private SwipeRefreshLayout swipeRefreshLayout;


    private ExploreViewModel dataViewModel;

    private boolean loading = false;
    private GridLayoutManager mLayoutManager;
    private List<Category> categoryList=new ArrayList<>();

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataViewModel= ViewModelProviders.of(this).get(ExploreViewModel.class);
        dataViewModel.setIsEmptyMsgVisibility(false);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());

        //exploreItemList = new ArrayList<>();
        //categoryList=new ArrayList<>();
        EventBus.getDefault().register(this);


    }
    private void setRequestData(int pageNumber) {
        dataViewModel.setRequestData(Request.Method.POST,  RestApiConstants.GET_USER_CATEGORIES_LIST_V2, "USER_CATEGORY_LIST",AppUtil.getRequestHeaders(getActivity()),pageNumber,realmManager.getDoc_id(realm), RequestLocationType.REQUEST_LOCATION_HOME.getRequestLocation());
    }


    public void getData() {
        if (categoryList.size() == 0 && AppUtil.isConnectingToInternet(getActivity())) {
            page_num = 0;
            dataViewModel.displayLoader();
            setRequestData(page_num);
            apiCallBack();
        }
    }

    private void apiCallBack() {
        dataViewModel.getAllExporeItems().observe(this, new Observer<CategoriesApiResponse>() {
            @Override
            public void onChanged(@Nullable CategoriesApiResponse apiResponse) {
                exploreAdapter.removeDummyItemFromList();
                if(apiResponse==null){
                    dataViewModel.setIsLoaderVisible(false);
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                if(apiResponse.getError()==null){
                    dataViewModel.displayUIBasedOnCount(apiResponse.getCategories().size());
                    //exploreItemList.addAll(apiResponse.getCategories());
                    categoryList=apiResponse.getCategories();
                    exploreAdapter.setDataList(categoryList);
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    dataViewModel.setIsLoaderVisible(false);
                    loading = false;
                    swipeRefreshLayout.setRefreshing(false);
                    ((BaseActionBarActivity)getActivity()).displayErrorScreen(apiResponse.getError());
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentExploreBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore,container,false);
        exploreAdapter = new ExploreAdapter((exploreItem, position) -> {
            if (AppUtil.isConnectingToInternet(getActivity())) {
                if ((exploreItem.getCategoryType().equalsIgnoreCase("DrugsCategory"))) {
                    Intent intent = new Intent(getActivity(), DrugClassActivity.class);
                    startActivity(intent);
                    EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate", 0, exploreItem.getCategoryId()));
                }
            else{
                    Category currentCategoryDetails = exploreItem;
                    Intent intent=new Intent(getActivity(), CategoryLoadingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentCategoryDetails", currentCategoryDetails);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("DocID",realmManager.getUserUUID(realm));
                    jsonObject.put("CategoryName", exploreItem.getCategoryName());
                    jsonObject.put(RestUtils.EVENT_DOC_SPECIALITY,realmManager.getDocSpeciality(realm));
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"ExploreCategoryTapped",jsonObject,AppUtil.convertJsonToHashMap(jsonObject),getActivity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        binding.setAdapter(exploreAdapter);
        binding.setViewModel(dataViewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        categoryRecycler = binding.categotyGrid;
        swipeRefreshLayout = binding.exploreSwipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AppUtil.isConnectingToInternet(getActivity())) {
                    page_num = 0;
                    if(loading){
                        App_Application.getInstance().cancelPendingRequests("USER_CATEGORY_LIST");
                    }
                    categoryList.clear();
                    exploreAdapter.notifyDataSetChanged();
                    setRequestData(page_num);
                    apiCallBack();
                }else{
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
        categoryRecycler.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(),4);
        categoryRecycler.setLayoutManager(mLayoutManager);
        categoryRecycler.addItemDecoration(new GridSpaceDecorator(16));

        categoryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                categoryRecycler.post(new Runnable() {
                    @Override
                    public void run() {
                        int visiblecount = mLayoutManager.getChildCount();
                        int toatlcount = mLayoutManager.getItemCount();
                        int pastitem = mLayoutManager.findFirstVisibleItemPosition();
                        int lastitem = mLayoutManager.findLastVisibleItemPosition();
                        if(isListExhausted){
                            return;
                        }
                        if(!loading){
                            if(lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount -1) && AppUtil.isConnectingToInternet(getActivity())){
                                exploreAdapter.addDummyItemToList();
//                                exploreAdapter.notifyDataSetChanged();
                                page_num += 1;
                                setRequestData(page_num);
                                apiCallBack();
                                loading = true;
                            }
                            else{
                                loading = false;
                            }
                        }
                    }
                });

                super.onScrolled(recyclerView, dx, dy);
            }
        });




        dataViewModel.isListExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted=aBoolean;
                if(aBoolean) {
                    //Toast.makeText(getActivity(),"No more categories",Toast.LENGTH_SHORT).show();
                    exploreAdapter.setListExhausted();
                }
            }
        });

        return view;
    }


    /*@Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateCategoryUnreadCountEvent event) {
        if(event==null){
            return;
        }
        for(int i=0;i<categoryList.size();i++){
            if(categoryList.get(i).getCategoryId()==event.getCategoryId()){
                categoryList.get(i).setUnreadCount(event.getCountNeedtoUpdate());
                if(exploreAdapter!=null){
                    exploreAdapter.notifyDataSetChanged();
                }
                return;
            }
        }
        /*else if (event.getMessage().equalsIgnoreCase("OnItemClick")) {
            if (AppUtil.isConnectingToInternet(DashboardActivity.this)) {
                if ((event.getCategory().getCategoryType().equalsIgnoreCase("DrugsCategory"))) {
                    Intent intent = new Intent(DashboardActivity.this, DrugClassActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DashboardActivity.this, CategoryLoadingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CurrentCategoryDetails", event.getCategory());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }

        }*/
    }
}