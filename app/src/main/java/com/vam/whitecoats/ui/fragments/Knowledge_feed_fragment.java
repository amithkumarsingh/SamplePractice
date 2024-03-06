package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.ui.activities.CategoryLoadingActivity;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.adapters.CategoriesAdapter;
import com.vam.whitecoats.ui.interfaces.OnCategoryClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.RequestLocationType;
import com.vam.whitecoats.utils.UpdateCategoryUnreadCountEvent;
import com.vam.whitecoats.viewmodel.ExploreViewModel;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;

public class Knowledge_feed_fragment extends Fragment {


    private KnowledgeContentFeedsFragment dashBoardFeeds;
    private RecyclerView category_list_dashboard;
    private RecyclerView categoryRecyclerView;
    private ExploreViewModel dataViewModel;
    private ArrayList<Category> categoryList = new ArrayList();
    private CategoriesAdapter categoriesAdapter;
    private Realm realm;
    private RealmManager realmManager;
    private boolean loading;
    private LinearLayoutManager linearLayoutManager;
    private boolean isListExhausted;
    private int page_num = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.knowledge_tab_feed, null);
        categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.category_list_dashboard);
        dataViewModel = ViewModelProviders.of(this).get(ExploreViewModel.class);

        realm = Realm.getDefaultInstance();//getInstance(this);
        realmManager = new RealmManager(getActivity());
        requestForCategoriesData(page_num);


        categoriesAdapter = new CategoriesAdapter(getActivity(), categoryList);
        categoriesAdapter.setCategoryClickListener(new OnCategoryClickListener() {
            @Override
            public void onCategoryItemClick(Category categoryItem) {
//                    try {
//                        String eventName = "DashboardCategoryTapped";
//                        JSONObject jsonObjectEvent = new JSONObject();
//                        jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
//                        jsonObjectEvent.put("CategoryName", categoryItem.getCategoryName());
//                        AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent), getContext());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("DocID", realmManager.getUserUUID(realm));
                    jsonObject.put("CategoryName", categoryItem.getCategoryName());
                    AppUtil.logUserActionEvent(realmManager.getDoc_id(realm),"KnowledgeCategoryTapped",jsonObject ,AppUtil.convertJsonToHashMap(jsonObject),getContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (AppUtil.isConnectingToInternet(getContext())) {
                    if ((categoryItem.getCategoryType().equalsIgnoreCase("DrugsCategory"))) {
                        Intent intent = new Intent(getContext(), DrugClassActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().post(new UpdateCategoryUnreadCountEvent("OnRegularUnreadCountUpdate", 0, categoryItem.getCategoryId()));
                    } else {
                        Intent intent = new Intent(getContext(), CategoryLoadingActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("CurrentCategoryDetails", categoryItem);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

            }
        });


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);
        categoryRecyclerView.setAdapter(categoriesAdapter);

        categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                categoryRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int visiblecount = linearLayoutManager.getChildCount();
                        int toatlcount = linearLayoutManager.getItemCount();
                        int pastitem = linearLayoutManager.findFirstVisibleItemPosition();
                        int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                        if (isListExhausted) {
                            return;
                        }
                        if (!loading) {
                            if (lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount - 1) && AppUtil.isConnectingToInternet(getContext())) {
                                page_num += 1;
                                requestForCategoriesData(page_num);
                                loading = true;
                            } else {
                                loading = false;
                            }
                        }
                    }
                });

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        dataViewModel.isListExhausted().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                isListExhausted = aBoolean;
                if (aBoolean) {
                    // Toast.makeText(getContext(),"No more categories",Toast.LENGTH_SHORT).show();
                    //categoriesAdapter.setListExhausted();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        dashBoardFeeds = KnowledgeContentFeedsFragment.newInstance("fromKnowledgeFeeds");


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, dashBoardFeeds);
        fragmentTransaction.addToBackStack(dashBoardFeeds.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        super.onCreate(savedInstanceState);

    }

    public static Knowledge_feed_fragment newInstance() {

        Bundle args = new Bundle();

        Knowledge_feed_fragment fragment = new Knowledge_feed_fragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void requestForCategoriesData(int page_num) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_LIST_V2, "USER_CATEGORY_LIST_KNOWLEDGE", AppUtil.getRequestHeaders(getActivity()), page_num, realmManager.getDoc_id(realm), RequestLocationType.REQUEST_LOCATION_Knowledge.getRequestLocation());
        dataViewModel.getAllExporeItems().observe(getViewLifecycleOwner(), categoriesApiResponse -> {

            if (categoriesApiResponse.getError() != null) {
                loading = false;
                return;
            } else {
                loading = false;
                categoryList.clear();
                categoryList.addAll(categoriesApiResponse.getCategories());
                if (categoryList.size() > 0) {
                    categoriesAdapter.notifyDataSetChanged();
                    /*if (dash_viewPager != null && dash_viewPager.getCurrentItem() == 0) {
                        categoryListLayout.setVisibility(View.VISIBLE);
                        categoryListDummyView.setVisibility(VISIBLE);
                    }*/
                }
            }
          /*  if (categoriesApiResponse.getError() != null) {
                return;
            } else {

                List<Category> categories = categoriesApiResponse.getCategories();
                if (categories.size() > 0) {
                    categoryList = new ArrayList();
                    Category mCategory = new Category();
                    mCategory.setCategoryId(-1);
                    mCategory.setCategoryName("More");
                    mCategory.setCategoryType("");
                    mCategory.setImageUrl("");
                    mCategory.setUnreadCount(0);
                    mCategory.setRank(0);
                    categories.add(categories.size(), mCategory);
                    categoryList.addAll(categories);
                }
            }

            if (categoryList.size() > 0) {

                categoriesAdapter = new CategoriesAdapter(getContext(), categoryList);
                categoriesAdapter.setCategoryClickListener(new OnCategoryClickListener() {
                    @Override
                    public void onCategoryItemClick(Category categoryItem) {

                    }
                });
                categoryRecyclerView.setHasFixedSize(true);
                categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                categoryRecyclerView.setAdapter(categoriesAdapter);
                *//*if (dash_viewPager != null && dash_viewPager.getCurrentItem() == 0) {
                    categoryListLayout.setVisibility(View.VISIBLE);
                    categoryListDummyView.setVisibility(VISIBLE);
                }*//*
            }*/
        });

    }

}
