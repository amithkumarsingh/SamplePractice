package com.vam.whitecoats.databinding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.vam.whitecoats.core.models.DrugSearchResponse
import com.vam.whitecoats.core.models.SearchedDrugItem
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse
import com.vam.whitecoats.utils.DrugSearchApiResponse
import com.vam.whitecoats.utils.RestUtils
import com.vam.whitecoats.utils.SingleLiveEvent
import com.vam.whitecoats.utils.VolleyModifiedSinglepartRequest
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DrugSearchRepository {
    private var methodType = 0
    private var requestURL: String? = null
    private var mTag: String? = null
    private var headers: Map<String, String>? = null
    private var page_num = 0
    private var _searchKeyWord: String? = null
    private val mutableLiveData = SingleLiveEvent<DrugSearchApiResponse>()
    private val drugsList = ArrayList<SearchedDrugItem>()
    private val mIsListExhausted = MutableLiveData<Boolean>()
    fun initRequest(methodType: Int, url: String?, mTag: String?, headers: Map<String, String>?) {
        this.methodType = methodType
        requestURL = url
        this.mTag = mTag
        this.headers = headers
    }

    fun setRequestData(pageNumber: Int, searchKeyWord: String?) {
        page_num = pageNumber
        _searchKeyWord = searchKeyWord
    }

    val searchedDrugsList: SingleLiveEvent<DrugSearchApiResponse>
        get() {
            VolleyModifiedSinglepartRequest(methodType, requestURL, prepareSearchDrugsRequest(), mTag, headers, object : OnReceiveResponse {
                override fun onSuccessResponse(successResponse: String) {
                    if (successResponse != null) {
                        try {
                            val jsonObject = JSONObject(successResponse)
                            Log.i("response", jsonObject.toString())
                            if(jsonObject.has(RestUtils.TAG_STATUS)) {
                                if (jsonObject.optString(RestUtils.TAG_STATUS).equals(RestUtils.TAG_ERROR, true)) {
                                    mutableLiveData.setValue(DrugSearchApiResponse(successResponse))
                                } else {
                                    val gson = GsonBuilder().setPrettyPrinting().create()
                                    val (_, responseList) = gson.fromJson(successResponse, DrugSearchResponse::class.java)
                                    if (responseList.isNotEmpty()) {
                                        mIsListExhausted.setValue(false)
                                    } else {
                                        mIsListExhausted.setValue(true)
                                    }
                                    if (page_num == 0) {
                                        drugsList.clear()
                                    }
                                    drugsList.addAll(responseList)
                                    mutableLiveData.setValue(DrugSearchApiResponse(drugsList))
                                }
                            }else{
                                mutableLiveData.setValue(DrugSearchApiResponse(successResponse))
                            }
                        } catch (e: JSONException) {
                            mutableLiveData.setValue(DrugSearchApiResponse(successResponse))
                            e.printStackTrace()
                        }
                    }
                }

                override fun onErrorResponse(errorResponse: String) {
                    var error = ""
                    if (errorResponse != null) {
                        error = errorResponse
                    }
                    mutableLiveData.setValue(DrugSearchApiResponse(error))
                    Log.e("ERROR", "error")
                }
            })
            return mutableLiveData
        }
    val isListExhausted: LiveData<Boolean>
        get() = mIsListExhausted

    private fun prepareSearchDrugsRequest(): String {
        val reqObj = JSONObject()
        try {
            reqObj.put(RestUtils.PG_NUM, page_num)
            reqObj.put("searchValue", _searchKeyWord)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return reqObj.toString()
    }
}