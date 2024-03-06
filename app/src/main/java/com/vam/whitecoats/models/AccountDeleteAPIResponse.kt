package com.vam.whitecoats.models

class AccountDeleteAPIResponse{
    lateinit var success:AccountDeleteResponse
    var error:String? = null


    constructor(_success:AccountDeleteResponse){
        success=_success
    }

    constructor(_error:String?){
        error=_error
    }
}
