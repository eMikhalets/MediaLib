package com.emikhalets.medialib.data.entity.movies

import com.google.gson.annotations.SerializedName

data class MovieSearchResult(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("genre_ids") val genres: List<Int>? = null,
    @SerializedName("poster_path") val poster: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
)
