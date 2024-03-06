package com.vam.whitecoats.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.databinding.SpecialityDistributionRepository;
import com.vam.whitecoats.utils.CatDistributionApiResponse;

import java.util.Map;

public class SpecialityTabViewModel extends ViewModel {
    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();
    private SpecialityDistributionRepository mRepo=new SpecialityDistributionRepository();
    private MutableLiveData<CatDistributionApiResponse> specialityList;

    public void setIsEmptyMsgVisibility(boolean visibility){
        isEmptyMsgVisible.set(visibility);
    }
    public void displayLoader(){
        setListVisibility(false);
        setIsEmptyMsgVisibility(false);
        setIsLoaderVisible(true);
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
    public void setListVisibility(boolean visibility){
        isListVisible.set(visibility);
    }
    public void setIsLoaderVisible(boolean visible){
        isLoaderVisible.set(visible);
    }

    public void setRequestData(int requestType, String url,String mTag, Map<String,String> headers,int category_id, Integer categoryDistributionId, int doc_id, int pageNum){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(category_id,categoryDistributionId,doc_id,pageNum);
      //  specialityList = mRepo.getCatSpecialityList();
    }

    public LiveData<CatDistributionApiResponse> getSpecialities() {
        return mRepo.getCatSpecialityList();
    }
    public LiveData<Boolean> isListExhausted(){
        return mRepo.isListExhausted();
    }
}

