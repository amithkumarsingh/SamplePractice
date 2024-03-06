package com.vam.whitecoats.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.google.gson.GsonBuilder
import com.vam.whitecoats.constants.RestApiConstants
import com.vam.whitecoats.models.AccountDeleteAPIResponse
import com.vam.whitecoats.models.AccountDeleteResponse
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse
import com.vam.whitecoats.utils.RestUtils
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest
import org.json.JSONException
import org.json.JSONObject


class DeleteAccountRepository {
    var responseLiveData= MutableLiveData<AccountDeleteAPIResponse>()
    fun requestForDeleteAccount(headers: Map<String, String>,userId:Int,reason:String,suggestion:String):MutableLiveData<AccountDeleteAPIResponse> {
            VolleyModifiedSinglepartRequest(Request.Method.POST, RestApiConstants.DELETE_USER_ACCOUNT_API, prepareRequestData(userId,reason,suggestion), "DELETE_ACCOUNT", headers, object : OnReceiveResponse {
                override fun onSuccessResponse(successResponse: String) {
                    if (successResponse != null) {
                        try {
                            val jsonObject = JSONObject(successResponse)
                            Log.i("response", jsonObject.toString())
                            if (jsonObject.has(RestUtils.TAG_STATUS)) {
                                if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR, true)) {
                                    responseLiveData.setValue(AccountDeleteAPIResponse(successResponse))
                                } else {
                                    val gson = GsonBuilder().setPrettyPrinting().create()
                                    val deleteResponse = gson.fromJson(successResponse, AccountDeleteResponse::class.java)
                                    responseLiveData.setValue(AccountDeleteAPIResponse(deleteResponse))
                                }
                            } else {
                                responseLiveData.setValue(AccountDeleteAPIResponse(successResponse))
                            }
                        } catch (e: JSONException) {
                            responseLiveData.value = AccountDeleteAPIResponse(successResponse)
                            e.printStackTrace()
                        }
                    }else{
                        responseLiveData.value = AccountDeleteAPIResponse("Something went wrong,please try again.")
                    }
                }

                override fun onErrorResponse(errorResponse: String) {
                    var error = ""
                    if (errorResponse != null) {
                        error = errorResponse
                    }
                    responseLiveData.value = AccountDeleteAPIResponse(error)
                    Log.e("ERROR", "error")
                }
            })
            return responseLiveData
    }

    private fun prepareRequestData(userId: Int, reason: String, suggestion: String): String? {
        val reqObj = JSONObject()
        try {
            reqObj.put(RestUtils.TAG_USER_ID, userId)
            reqObj.put("reason", reason)
            reqObj.put("suggestions",suggestion)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return reqObj.toString()
    }
}