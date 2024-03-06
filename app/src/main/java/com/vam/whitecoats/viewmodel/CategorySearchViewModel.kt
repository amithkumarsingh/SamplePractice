package com.vam.whitecoats.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vam.whitecoats.databinding.CategorySearchRepository
import com.vam.whitecoats.databinding.DrugSearchRepository
import com.vam.whitecoats.utils.CategoryFeedSearchApiResponse
import com.vam.whitecoats.utils.DrugSearchApiResponse
import com.vam.whitecoats.utils.FeedsBySpecApiResponse

class CategorySearchViewModel : ViewModel() {
    private val mRepoSearchFeeds = CategorySearchRepository()



    //var drugsList=MutableLiveData<ArrayList<SearchedDrugItem>>()
    var isListVisible = ObservableField<Boolean>()
    var isLoaderVisible = ObservableField<Boolean>()
    var isEmptyMsgVisible= ObservableField<Boolean>()

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

    fun setRequestData(requestType: Int, url: String?, mTag: String, headers:HashMap<String,String>, pageNumber: Int, categoryType: String?, categoryId: Int, searchString: String?) {
        mRepoSearchFeeds.initRequest(requestType, url, mTag, headers)
        mRepoSearchFeeds.setRequestData(categoryType, categoryId, pageNumber, searchString)
    }




    fun getSearchedFeeds():LiveData<CategoryFeedSearchApiResponse?>{
        return mRepoSearchFeeds.searchFeedsList
    }

    fun isListExhausted(): LiveData<Boolean> {
        return mRepoSearchFeeds.isListExhausted
    }
}