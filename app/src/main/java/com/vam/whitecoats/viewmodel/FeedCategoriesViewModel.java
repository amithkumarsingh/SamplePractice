package com.vam.whitecoats.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.databinding.SubCategoryFeedsRepository;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;
import com.vam.whitecoats.utils.FeedsApiResponse;

import java.util.Map;

public class FeedCategoriesViewModel extends ViewModel {
    private CategoryDistributionRepository mRepo =new CategoryDistributionRepository();

    private SubCategoryFeedsRepository mRepoFeeds =new SubCategoryFeedsRepository();

    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();
    private MutableLiveData<CategoriesDistributionApiResponse> categoryFeedsList;

    private MutableLiveData<FeedsApiResponse> feedsList;

    public void setIsEmptyMsgVisibility(boolean visibility){
        isEmptyMsgVisible.set(visibility);
    }

    public void displayLoader(){
        setListVisibility(false);
        setIsEmptyMsgVisibility(false);
        setIsLoaderVisible(true);
    }
    public void setListVisibility(boolean visibility){
        isListVisible.set(visibility);
    }
    public void setIsLoaderVisible(boolean visible){
        isLoaderVisible.set(visible);
    }

    public void displayUIBasedOnCount(int count){
        if(count>0) {
            setListVisibility(true);
            setIsEmptyMsgVisibility(false);
        }else{
            setListVisibility(false);
            setIsEmptyMsgVisibility(true);
        }
        setIsLoaderVisible(false);
    }
    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers, int pageNumber, int docId, int category_id){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(pageNumber,docId,category_id);
        categoryFeedsList = mRepo.getUserCategoryDistributionList();

    }
    public void setRequestFeedsData(int requestType, String url, String mTag, Map<String,String> headers, int pageNumber, int docId, int feeds_category_id,int sub_category_id){
        mRepoFeeds.initRequest(requestType,url,mTag,headers);
        mRepoFeeds.setRequestData(feeds_category_id,sub_category_id,docId,pageNumber);
        feedsList = mRepoFeeds.getFeedsList();

    }

    public LiveData<CategoriesDistributionApiResponse> getAllCategoryFeeds() {
        return categoryFeedsList;
    }
    public LiveData<FeedsApiResponse> getFeeds() {
        return feedsList;
    }

}
