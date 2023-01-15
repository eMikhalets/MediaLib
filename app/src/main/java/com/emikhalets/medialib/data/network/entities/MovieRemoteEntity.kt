package com.emikhalets.medialib.data.network.entities

import com.google.gson.annotations.SerializedName

data class MovieRemoteEntity(
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("Runtime") val runtime: String? = null,
    @SerializedName("Genre") val genre: String? = null,
    @SerializedName("Director") val director: String? = null,
    @SerializedName("Writer") val whiter: String? = null,
    @SerializedName("Actors") val actors: String? = null,
    @SerializedName("Plot") val plot: String? = null,
    @SerializedName("Awards") val awards: String? = null,
    @SerializedName("Poster") val poster: String? = null,
    @SerializedName("Ratings") val ratings: List<RatingsRemoteEntity>? = null,
    @SerializedName("Type") val type: String? = null,
    @SerializedName("Response") val response: Boolean? = null,
    @SerializedName("Error") val error: String? = null,
) {

    fun formatYear(): Int {
        return try {
            val yearValue = year ?: return 0
            if (yearValue.length > 4) {
                year.take(4).toInt()
            } else {
                yearValue.toInt()
            }
        } catch (ex: Exception) {
            0
        }
    }
}