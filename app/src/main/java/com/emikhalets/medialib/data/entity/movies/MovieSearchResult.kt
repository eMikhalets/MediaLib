package com.emikhalets.medialib.data.entity.movies

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("genre_ids") val genre_ids: List<Int>? = null,
    @SerializedName("poster_path") val poster_path: String? = null,
    @SerializedName("release_date") val release_date: String? = null,
    @SerializedName("vote_average") val vote_average: Double? = null,
)
