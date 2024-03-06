package com.vam.whitecoats.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryFeedsSearchItem (
        @SerializedName("feed_id") @Expose val feedId : Int,
        @SerializedName("feed_type_id") @Expose val feedTypeId : Int,
        @SerializedName("channel_id") @Expose val channelId : Int,
        @SerializedName("title") @Expose val title : String,

        @SerializedName("short_desc") @Expose val shortDesc : String,
        @SerializedName("attachment_details") @Expose val attachmentDetails : List<CategoryFeedSearchAttachmentDetail>

        )