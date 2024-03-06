package com.vam.whitecoats.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.DrugClass;
import com.vam.whitecoats.databinding.DrugClassRepository;
import com.vam.whitecoats.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugClassViewModel extends ViewModel {

    List<DrugClass> data= new ArrayList<>();

    private DrugClassRepository mRepo =new DrugClassRepository();
    private MutableLiveData<ApiResponse> drugList =new MutableLiveData<>();

    public ObservableField<Boolean> isListVisible=new ObservableField<>();
    public ObservableField<Boolean> isLoaderVisible=new ObservableField<>();
    public ObservableField<Boolean> isEmptyMsgVisible=new ObservableField<>();

    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers,String request,String type){
        mRepo.initRequest(requestType,url,mTag,headers,request);
//        mRepo.setRequestData(pageNumber);
        drugList = mRepo.getDrugList(type);

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

    public List<DrugClass> getData() {
        return this.data;
    }

    public void setData(List<DrugClass> data) {
        this.data = data;
    }

}
