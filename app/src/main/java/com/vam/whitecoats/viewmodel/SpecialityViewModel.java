package com.vam.whitecoats.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.vam.whitecoats.databinding.SpecialityRepository;

public class SpecialityViewModel extends ViewModel {
    private SpecialityRepository specialityRepository;

    public void init() {
        specialityRepository = SpecialityRepository.getInstance();
    }

    public LiveData<String> getSpeciality(Activity activity) {
        return specialityRepository.getSpecialityData(activity);
    }
}
