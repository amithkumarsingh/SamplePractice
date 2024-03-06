package com.vam.whitecoats.databinding;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.vam.whitecoats.constants.RestApiConstants;
import com.vam.whitecoats.utils.ApiMethodCalls;
import com.vam.whitecoats.utils.ApiCallbackInterface;


public class SpecialityRepository {
    private static SpecialityRepository specialityRepository;
    private ApiMethodCalls apiMethodCalls = new ApiMethodCalls();

    public static SpecialityRepository getInstance() {
        if(specialityRepository == null) {
            specialityRepository = new SpecialityRepository();
        }
        return specialityRepository;
    }
    public MutableLiveData<String> getSpecialityData(Activity activity){
        MutableLiveData<String> searchResponse = new MutableLiveData<>();
        String url = RestApiConstants.GET_SPECIALITY_LIST;
        apiMethodCalls.postApiData(url, null, activity, new ApiCallbackInterface() {
            @Override
            public void onSuccessResponse(String response) {
                searchResponse.postValue(response);
            }

            @Override
            public void onErrorResponse(String error) {
                searchResponse.postValue(error);
            }
        });
        return  searchResponse;
    }
}
