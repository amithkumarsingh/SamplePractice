package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.core.models.CategoryFeeds;
import com.vam.whitecoats.databinding.DrugFeedsRepository;
import com.vam.whitecoats.databinding.GetCategoriesRepository;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.DrugFeedsApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugFeedsViewModel extends ViewModel {
    List<CategoryFeeds> data= new ArrayList<>();
    private DrugFeedsRepository mRepo =new DrugFeedsRepository();
    private MutableLiveData<DrugFeedsApiResponse> exploreList =new MutableLiveData<>();
    public DrugFeedsViewModel(){
    }

    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers, int pageNumber, String drugName){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(pageNumber,drugName);
//        exploreList = mRepo.getFeedsList();

    }

    public LiveData<DrugFeedsApiResponse> getFeeds() {
        return mRepo.getFeedsList();
    }


    public LiveData<Boolean> isListExhausted(){
        return mRepo.isListExhausted();
    }
    public List<CategoryFeeds> getData() {
        return this.data;
    }

    public void setData(List<CategoryFeeds> data) {
        this.data = data;
    }
}
