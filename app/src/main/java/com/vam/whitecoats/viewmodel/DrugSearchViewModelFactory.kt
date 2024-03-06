package com.vam.whitecoats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DrugSearchViewModelFactory (): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DrugSearchViewModel::class.java)){
            return DrugSearchViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}