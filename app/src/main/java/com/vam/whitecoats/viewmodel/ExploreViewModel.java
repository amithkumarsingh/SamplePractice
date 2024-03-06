package com.vam.whitecoats.viewmodel;

import android.os.Handler;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.Category;
import com.vam.whitecoats.databinding.GetCategoriesRepository;
import com.vam.whitecoats.databinding.NotificationsRepository;
import com.vam.whitecoats.utils.CategoriesApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExploreViewModel extends ViewModel {
    List<Category> data= new ArrayList<>();
    private GetCategoriesRepository mRepo =new GetCategoriesRepository();
    private MutableLiveData<CategoriesApiResponse> exploreList =new MutableLiveData<>();


    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();

    public ExploreViewModel(){
    }

    public void setRequestData(int requestType, String url,String mTag, Map<String,String> headers,int pageNumber,int docId,String tabName){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(pageNumber,docId,tabName);
        //exploreList = mRepo.getUserCategoryList();

    }

    public LiveData<CategoriesApiResponse> getAllExporeItems() {
        return  mRepo.getUserCategoryList();
    }


    public LiveData<Boolean> isListExhausted(){
        return mRepo.isListExhausted();
    }

    public void setListVisibility(boolean visibility){
        isListVisible.set(visibility);
    }

    public void setIsEmptyMsgVisibility(boolean visibility){
        isEmptyMsgVisible.set(visibility);
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

    public void displayLoader(){
        setListVisibility(false);
        setIsEmptyMsgVisibility(false);
        setIsLoaderVisible(true);
    }

    public List<Category> getData() {
        return this.data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
//
//    public List<ExploreItem> getData() {
//        return this.data;
//    }
//
//    public void setData(List<ExploreItem> data) {
//        this.data = data;
//    }



}
