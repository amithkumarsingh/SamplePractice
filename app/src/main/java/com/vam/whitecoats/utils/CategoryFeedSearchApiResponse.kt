package com.vam.whitecoats.utils

import com.vam.whitecoats.core.models.CategoryFeeds
import com.vam.whitecoats.core.models.CategoryFeedsSearchItem
import com.vam.whitecoats.core.models.SearchedDrugItem

class CategoryFeedSearchApiResponse {
    lateinit var feedsList:List<CategoryFeeds>
    var error:String? = null


    constructor(_drugsList:List<CategoryFeeds>){
        feedsList=_drugsList
    }

    constructor(_error:String){
        error=_error
    }
}