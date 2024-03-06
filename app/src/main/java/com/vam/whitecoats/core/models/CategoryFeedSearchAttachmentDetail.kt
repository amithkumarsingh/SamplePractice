package com.vam.whitecoats.core.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryFeedSearchAttachmentDetail (
        @SerializedName("attachment_original_url") @Expose val attachmentOriginalUrl : String,
        @SerializedName("attachment_small_url") @Expose val attachmentSmallUrl : String,
        @SerializedName("attachment_type") @Expose val attachmentType : String,
)
