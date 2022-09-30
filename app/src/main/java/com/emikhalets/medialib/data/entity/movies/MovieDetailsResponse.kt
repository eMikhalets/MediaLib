package com.emikhalets.medialib.data.entity.movies

import com.emikhalets.medialib.data.entity.support.GenreData
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("budget") val budget: Int? = null,
    @SerializedName("backdrop_path") val backdrop: String? = null,
    @SerializedName("genres") val genres: List<GenreData>? = null,
    @SerializedName("imdb_id") val imdbId: String? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("poster_path") val poster: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("revenue") val revenue: Int? = null,
    @SerializedName("runtime") val runtime: Int? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("tagline") val tagline: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
)
