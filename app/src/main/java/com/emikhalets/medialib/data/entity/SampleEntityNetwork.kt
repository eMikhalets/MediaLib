package com.emikhalets.medialib.data.entity

import com.google.gson.annotations.SerializedName

data class SampleEntityNetwork(
    @SerializedName("someVal") val some: String,
)