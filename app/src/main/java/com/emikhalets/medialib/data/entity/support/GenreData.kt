package com.emikhalets.medialib.data.entity.support

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
)
