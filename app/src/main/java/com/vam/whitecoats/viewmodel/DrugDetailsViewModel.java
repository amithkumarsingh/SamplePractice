package com.vam.whitecoats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.core.models.DrugDetail;
import com.vam.whitecoats.databinding.DrugDetailsRepository;
import com.vam.whitecoats.utils.DrugDetailsApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DrugDetailsViewModel extends ViewModel {
    List<DrugDetail> data= new ArrayList<>();

    private DrugDetailsRepository mRepo =new DrugDetailsRepository();
    private MutableLiveData<DrugDetailsApiResponse> drugList =new MutableLiveData<>();

    public void setRequestData(int requestType, String url, String mTag, Map<String,String> headers, int id,boolean isBrandInfo){
        mRepo.initRequest(requestType,url,mTag,headers);
        mRepo.setRequestData(id,isBrandInfo);
        drugList = mRepo.getDrugList();

    }

    public LiveData<DrugDetailsApiResponse> getDrugDetails() {
        return drugList;
    }


    public List<DrugDetail> getData() {
        return this.data;
    }

    public void setData(List<DrugDetail> data) {
        this.data = data;
    }


}
