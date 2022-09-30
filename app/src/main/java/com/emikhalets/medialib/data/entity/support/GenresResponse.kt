package com.emikhalets.medialib.data.entity.support

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<GenreData>? = null,
)
