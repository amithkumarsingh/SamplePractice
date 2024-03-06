package com.vam.whitecoats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CategorySearchViewModelFactory (): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CategorySearchViewModel::class.java)){
            return CategorySearchViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }
}