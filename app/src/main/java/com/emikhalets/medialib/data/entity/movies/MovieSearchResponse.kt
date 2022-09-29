package com.emikhalets.medialib.data.entity.movies

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<MovieSearchResult>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
)
