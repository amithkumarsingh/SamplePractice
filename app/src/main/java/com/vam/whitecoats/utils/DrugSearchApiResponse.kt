package com.vam.whitecoats.utils

import com.vam.whitecoats.core.models.SearchedDrugItem

class DrugSearchApiResponse{
    lateinit var drugsList:List<SearchedDrugItem>
    var error:String? = null


    constructor(_drugsList:List<SearchedDrugItem>){
        drugsList=_drugsList
    }

    constructor(_error:String?){
        error=_error
    }

}