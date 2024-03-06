package com.vam.whitecoats.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryFeedSearchData (
        @SerializedName("feed_data") @Expose val feedData:List<CategoryFeeds>
)
