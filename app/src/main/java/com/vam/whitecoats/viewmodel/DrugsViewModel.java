package com.vam.whitecoats.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.DrugSubClass;
import com.vam.whitecoats.databinding.DrugsRepository;
import com.vam.whitecoats.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugsViewModel extends ViewModel {
    List<DrugSubClass> data= new ArrayList<>();

    private DrugsRepository mRepo =new DrugsRepository();
    private MutableLiveData<ApiResponse> drugList =new MutableLiveData<>();

    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();

    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers, int id,int pageNo){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(id,pageNo);
        drugList = mRepo.getDrugList();

    }

    public LiveData<ApiResponse> getAllDrugItems() {
        return drugList;
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

    public List<DrugSubClass> getData() {
        return this.data;
    }

    public void setData(List<DrugSubClass> data) {
        this.data = data;
    }
}
