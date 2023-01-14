package com.emikhalets.medialib.data.network.entities

import com.google.gson.annotations.SerializedName

data class RatingsRemoteEntity(
    @SerializedName("Source") val source: String? = null,
    @SerializedName("Value") val value: String? = null,
)