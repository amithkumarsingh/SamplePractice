package com.vam.whitecoats.databinding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.vam.whitecoats.core.models.*
import com.vam.whitecoats.ui.interfaces.OnReceiveResponse
import com.vam.whitecoats.utils.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class CategorySearchRepository {
    private var category_type: String? = null
    private var category_id = 0
    private var page_num = 0
    private var searchValue: String? = null
    private val speciality_id = 0
    private var headers: Map<String, String>? = null
    private var methodType = 0
    private var requestURL: String? = null
    private var mTag: String? = null
    private val mutableLiveData = SingleLiveEvent<CategoryFeedSearchApiResponse>()
    private val mIsListExhausted = MutableLiveData<Boolean>()
    private val feedCategoriesList = ArrayList<CategoryFeeds>()
    fun setRequestData(category_type: String?, categoryId: Int, pgNum: Int, search_value: String?) {
        this.category_type = category_type
        category_id = categoryId
        page_num = pgNum
        searchValue = search_value
    }

    fun initRequest(methodType: Int, url: String?, mTag: String?, headers: Map<String, String>?) {
        this.methodType = methodType
        requestURL = url
        this.mTag = mTag
        this.headers = headers
    }

    val searchFeedsList: SingleLiveEvent<CategoryFeedSearchApiResponse>
        get() {
            VolleyModifiedSinglepartRequest(methodType, requestURL, prepareGetFeedsListRequest(), mTag, headers, object : OnReceiveResponse {
                override fun onSuccessResponse(successResponse: String) {
                    if (successResponse != null) {
                        try {
                            val jsonObject = JSONObject(successResponse)
                            Log.i("response", jsonObject.toString())
                            if (jsonObject.getString(RestUtils.TAG_STATUS) == RestUtils.TAG_ERROR) {
                                mutableLiveData.setValue(CategoryFeedSearchApiResponse(successResponse))
                            } else {
                                val gson = GsonBuilder().setPrettyPrinting().create()
                                val categoryDataResponse = gson.fromJson(successResponse, CategoryFeedSearchDataResponse::class.java)
                                val responseList: List<CategoryFeeds> = categoryDataResponse.data.feedData
                                if (responseList.size != 0) {
                                    mIsListExhausted.setValue(false)
                                } else {
                                    mIsListExhausted.setValue(true)
                                }
                                 if(page_num==0){
                                feedCategoriesList.clear();
                            }
                            feedCategoriesList.addAll(responseList);
                                mutableLiveData.setValue(CategoryFeedSearchApiResponse(feedCategoriesList))
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onErrorResponse(errorResponse: String) {
                    var error = ""
                    if (errorResponse != null) {
                        error = errorResponse
                    }
                    mutableLiveData.value = CategoryFeedSearchApiResponse(error)
                    Log.e("ERROR", "error")
                }
            })
            return mutableLiveData
        }

    val isListExhausted: LiveData<Boolean>
        get() = mIsListExhausted

    private fun prepareGetFeedsListRequest(): String {
        val reqObj = JSONObject()
        val filtersJsonObject = JSONObject()
        try {
            reqObj.put("category_type", category_type)
            reqObj.put("category_id", category_id)
            reqObj.put("pg_num", page_num)
            filtersJsonObject.put("search_value", searchValue)
            reqObj.put("filter", filtersJsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return reqObj.toString()
    }
}