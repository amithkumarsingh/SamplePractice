package com.vam.whitecoats.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.vam.whitecoats.R;
import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.core.models.CategoryDistributionInfo;
import com.vam.whitecoats.core.models.CategoryDistributionsFeedsInfo;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.core.models.SpecialitiesInfo;
import com.vam.whitecoats.core.realm.RealmManager;
import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.databinding.FragmentCategoriesSpecialityTabBinding;
import com.vam.whitecoats.databinding.FragmentExploreBinding;
import com.vam.whitecoats.databinding.SpecialityDistributionRepository;
import com.vam.whitecoats.ui.activities.BaseActionBarActivity;
import com.vam.whitecoats.ui.activities.CategoryLoadingActivity;
import com.vam.whitecoats.ui.activities.DrugClassActivity;
import com.vam.whitecoats.ui.activities.FeedCategoryDistributionActivity;
import com.vam.whitecoats.ui.adapters.SpecialityTabAdapter;
import com.vam.whitecoats.ui.interfaces.DrugClassClickListener;
import com.vam.whitecoats.ui.interfaces.SpecialityTabItemClickListener;
import com.vam.whitecoats.utils.AppUtil;
import com.vam.whitecoats.utils.CatDistributionApiResponse;
import com.vam.whitecoats.utils.RestUtils;
import com.vam.whitecoats.utils.SimpleDividerItemDecoration;
import com.vam.whitecoats.viewmodel.ExploreViewModel;
import com.vam.whitecoats.viewmodel.SpecialityTabViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesSpecialityTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesSpecialityTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String category_name;
    private int category_id;
    private ArrayList<CategoryDistributionInfo> cat_distribution_list;
    private SpecialityTabViewModel dataViewModel;
    private Realm realm;
    private RealmManager realmManager;
    private int page_num = 0;
    private SpecialityTabAdapter specialityTabAdapter;
    private RecyclerView specialityRecycler;

    int c = 0;
    int j = 0;

    private ArrayList<Integer> groupData;
    private LinearLayoutManager linearLayoutManager;
    private boolean loading = false;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int speciality_id = 0;

    public CategoriesSpecialityTab() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CategoriesSpecialityTab newInstance(ArrayList<CategoryDistributionInfo> category_distributionsList, String categoryName, int categoryId) {
        CategoriesSpecialityTab fragment = new CategoriesSpecialityTab();
        Bundle args = new Bundle();
        args.putSerializable("category_distribution_list", category_distributionsList);
        args.putString("Category_Name", categoryName);
        args.putInt("Category_Id", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments() != null) {
                cat_distribution_list = (ArrayList<CategoryDistributionInfo>) getArguments().getSerializable("category_distribution_list");
                category_name = getArguments().getString("Category_Name");
                category_id = getArguments().getInt("Category_Id");
            }
        }
        dataViewModel = ViewModelProviders.of(this).get(SpecialityTabViewModel.class);
        dataViewModel.setIsEmptyMsgVisibility(false);

        realm = Realm.getDefaultInstance();
        realmManager = new RealmManager(getActivity());
        groupData = new ArrayList<>();
        if (AppUtil.isConnectingToInternet(getActivity())) {
            dataViewModel.displayLoader();
            setRequestData(page_num);
            callBackFromService();
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCategoriesSpecialityTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories_speciality_tab, container, false);

        specialityTabAdapter = new SpecialityTabAdapter(new SpecialityTabItemClickListener() {
            @Override
            public void onItemClicked(SpecialitiesInfo specialitiesInfo) {
                if (AppUtil.isConnectingToInternet(getContext())) {
                    if (specialitiesInfo != null) {
                        try {
                            String eventName = "CategoryDistributionSpecialitySelected";
                            JSONObject jsonObjectEvent = new JSONObject();
                            jsonObjectEvent.put("DocID", realmManager.getUserUUID(realm));
                            jsonObjectEvent.put("CategoryName", getArguments().getString("Category_Name"));
                            jsonObjectEvent.put("Speciality ID", specialitiesInfo.getSpecialityId());
                            jsonObjectEvent.put("Speciality Name", specialitiesInfo.getSpecialityName());

                            AppUtil.logUserActionEvent(realmManager.getDoc_id(realm), eventName, jsonObjectEvent, AppUtil.convertJsonToHashMap(jsonObjectEvent),getActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        speciality_id = specialitiesInfo.getSpecialityId();
                        Intent intent = new Intent(getActivity(), FeedCategoryDistributionActivity.class);
                        intent.putExtra("Speciality_object", specialitiesInfo);
                        intent.putExtra("Navigation_from", "Categories_Tab");
                        intent.putExtra("Category_id",category_id);
                        intent.putExtra("CategoryName",category_name);
                        getActivity().startActivity(intent);
                    }
                }

            }
        });
        binding.setAdapter(specialityTabAdapter);
        binding.setViewModel(dataViewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        specialityRecycler = binding.specialityList;
        swipeRefreshLayout = binding.exploreSwipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppUtil.isConnectingToInternet(getActivity())) {
                    page_num = 0;
                    setRequestData(page_num);
                    callBackFromService();
                } else {
                    if (swipeRefreshLayout != null) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });


        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        specialityRecycler.setLayoutManager(linearLayoutManager);

        specialityRecycler.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

       /* specialityRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int toatlcount = linearLayoutManager.getItemCount();
                int lastitem = linearLayoutManager.findLastVisibleItemPosition();
                if(!loading) {
                    if(lastitem != RecyclerView.NO_POSITION && lastitem == (toatlcount -1) && AppUtil.isConnectingToInternet(getActivity())) {
                        specialityTabAdapter.addDummyItemToList();
                        page_num += 1;
                        setRequestData(page_num);
                        loading = true;;
                    }else{
                        loading = false;
                    }
                }
                super.onScrolled(recyclerView, dx, dy);

            }
        });*/
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return view;


    }

    private void callBackFromService() {
        dataViewModel.getSpecialities().observe(this, new Observer<CatDistributionApiResponse>() {
            @Override
            public void onChanged(CatDistributionApiResponse catDistributionApiResponse) {
                specialityTabAdapter.removeDummyItemFromList();
                swipeRefreshLayout.setRefreshing(false);
                if (catDistributionApiResponse == null) {
                    return;
                }
                if (catDistributionApiResponse.getError() == null) {
                    dataViewModel.displayUIBasedOnCount(catDistributionApiResponse.getSpecialitiesInfos().size());
                    List<SpecialitiesInfo> specialitiesList = catDistributionApiResponse.getSpecialitiesInfos();
                    if (page_num == 0) {
                        groupData.clear();
                        c = 0;
                        j = 0;
                    }
                    for (int i = 0; i < specialitiesList.size(); i++) {
                        SpecialitiesInfo specialitiesClass = specialitiesList.get(i);
                        if (groupData.size() == 0) {
                            groupData.add(c, j);
                            c++;
                            j++;
                        } else {
                            if (specialitiesClass.getSpecialityName().substring(0, 1).equalsIgnoreCase(specialitiesList.get(i - 1).getSpecialityName().substring(0, 1))) {
                                groupData.add(c, j);
                                c++;
                                j++;
                            } else {
                                j = 0;
                                groupData.add(c, j);
                                c++;
                                j++;
                            }
                        }
                    }
                    Log.i("groupdata", groupData.toString());
                    specialityTabAdapter.setSpecialityDataList(specialitiesList, groupData);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    ((BaseActionBarActivity)getActivity()).displayErrorScreen(catDistributionApiResponse.getError());
                }
            }
        });

    }

    private void setRequestData(int page_num) {
        dataViewModel.setRequestData(Request.Method.POST, RestApiConstants.GET_USER_CATEGORIES_SPECIALITY_DISTRIBUTION, "USER_CATEGORY_LIST", AppUtil.getRequestHeaders(getActivity()), category_id, cat_distribution_list.get(0).getCategoryDistributionId(), realmManager.getDoc_id(realm), page_num);

    }

}