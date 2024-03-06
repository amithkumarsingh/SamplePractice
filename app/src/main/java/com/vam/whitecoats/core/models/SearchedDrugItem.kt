package com.vam.whitecoats.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchedDrugItem(
        @SerializedName("item_id") @Expose val itemId: Int,
        @SerializedName("item_name")@Expose val itemName: String,
        @SerializedName("item_type") @Expose val itemType: String,
        @SerializedName("manufacturer") @Expose val manufacturer: String,
        @SerializedName("generic") @Expose val generic: String
)