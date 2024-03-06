package com.vam.whitecoats.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.databinding.CategoryDistributionRepository;
import com.vam.whitecoats.databinding.GetCategoriesRepository;
import com.vam.whitecoats.utils.CategoriesApiResponse;
import com.vam.whitecoats.utils.CategoriesDistributionApiResponse;

import java.util.Map;

public class SubCategoriesViewModel extends ViewModel {
    CategoryDistributionRepository mRepo =new CategoryDistributionRepository();
    private MutableLiveData<CategoriesDistributionApiResponse> categoryList =new MutableLiveData<>();

    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();

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
      //  categoryList = mRepo.getUserCategoryDistributionList();
    }

    public LiveData<CategoriesDistributionApiResponse> getAllSubCategories() {
        Log.e("SubCategoryList","SubcategorySuccess");
        return mRepo.getUserCategoryDistributionList();
    }


}
