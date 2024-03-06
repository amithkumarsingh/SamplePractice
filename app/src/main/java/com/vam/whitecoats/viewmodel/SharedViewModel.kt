package com.vam.whitecoats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val message = MutableLiveData<String>()

    // function to send close button action
    fun sendCloseBtnAction(text: String) {
        message.value = text
    }
}