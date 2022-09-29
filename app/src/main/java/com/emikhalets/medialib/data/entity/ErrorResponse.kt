package com.emikhalets.medialib.data.entity

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_message") val message: String? = null,
    @SerializedName("status_code") val code: Int? = null,
)
