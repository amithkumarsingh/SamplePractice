package com.vam.whitecoats.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vam.whitecoats.databinding.DrugSearchRepository
import com.vam.whitecoats.utils.DrugSearchApiResponse

class DrugSearchViewModel : ViewModel(){

    var repo=DrugSearchRepository()
    //var drugsList=MutableLiveData<ArrayList<SearchedDrugItem>>()
    var isListVisible = ObservableField<Boolean>()
    var isLoaderVisible =ObservableField<Boolean>()
    var isEmptyMsgVisible= ObservableField<Boolean>()

    fun setRequestData(requestType: Int, url: String, mTag: String, headers: HashMap<String, String>, pgNum: Int, searchKeyword: String){
        repo.setRequestData(pgNum, searchKeyword)
        repo.initRequest(requestType, url, mTag, headers)
    }

    fun getSearchedDrugs():LiveData<DrugSearchApiResponse?>{
        return repo.searchedDrugsList
    }

    fun setListVisibility(visibility: Boolean) {
        isListVisible.set(visibility)
    }

    fun setIsEmptyMsgVisibility(visibility: Boolean) {
        isEmptyMsgVisible.set(visibility)
    }

    fun setIsLoaderVisible(visible: Boolean) {
        isLoaderVisible.set(visible)
    }

    fun displayLoader() {
        setListVisibility(false)
        setIsEmptyMsgVisibility(false)
        setIsLoaderVisible(true)
    }
    fun displayUIBasedOnCount(count: Int) {
        if (count > 0) {
            setListVisibility(true)
            setIsEmptyMsgVisibility(false)
        } else {
            setListVisibility(false)
            setIsEmptyMsgVisibility(true)
        }
        setIsLoaderVisible(false)
    }

    fun isListExhausted(): LiveData<Boolean> {
        return repo.isListExhausted
    }
}