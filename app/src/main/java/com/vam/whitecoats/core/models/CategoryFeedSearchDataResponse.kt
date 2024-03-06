package com.vam.whitecoats.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



data class CategoryFeedSearchDataResponse(
        @SerializedName("status")@Expose val status: String,
        @SerializedName("data") @Expose val data: CategoryFeedSearchData
)