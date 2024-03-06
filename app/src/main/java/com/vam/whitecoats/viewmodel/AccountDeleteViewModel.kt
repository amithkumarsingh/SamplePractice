package com.vam.whitecoats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vam.whitecoats.models.AccountDeleteAPIResponse
import com.vam.whitecoats.repository.DeleteAccountRepository

class AccountDeleteViewModel : ViewModel() {

    private val repository: DeleteAccountRepository by lazy {
        DeleteAccountRepository()
    }

    fun deleteUserAccountRequest(headers:Map<String,String>,userId:Int,reason:String,suggestion:String):LiveData<AccountDeleteAPIResponse>{
        return repository.requestForDeleteAccount(headers,userId,reason,suggestion)
    }
}