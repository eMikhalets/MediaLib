package com.emikhalets.medialib.data.network

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("Runtime") val runtime: String? = null,
    @SerializedName("Genre") val genre: String? = null,
    @SerializedName("Director") val director: String? = null,
    @SerializedName("Writer") val whiter: String? = null,
    @SerializedName("Actors") val actors: String? = null,
    @SerializedName("Poster") val poster: String? = null,
    @SerializedName("imdbRating") val rating: String? = null,
)