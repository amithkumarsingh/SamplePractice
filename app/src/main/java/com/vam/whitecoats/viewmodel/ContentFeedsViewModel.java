package com.vam.whitecoats.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.databinding.ContentFeedsRepository;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;

import java.util.Map;

public class ContentFeedsViewModel extends ViewModel {
    ContentFeedsRepository mRepo =new ContentFeedsRepository();
    private MutableLiveData<CategoriesDistributionApiResponse> categoryList =new MutableLiveData<>();

    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers, int pageNumber, int docId, int tagId){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(pageNumber,docId,tagId);
        //  categoryList = mRepo.getUserCategoryDistributionList();
    }

    public LiveData<CategoriesDistributionApiResponse> getAllSubCategories() {
        Log.e("SubCategoryList","SubcategorySuccess");
        return mRepo.getUserCategoryDistributionList();
    }
}
