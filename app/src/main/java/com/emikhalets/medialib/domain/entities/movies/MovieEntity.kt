package com.emikhalets.medialib.domain.entities.movies

data class MovieEntity(
    val id: Int,
    val title: String,
    val titleRu: String,
    val overview: String,
    val poster: String,
    val year: Int,
    val imdbRating: Double,
    val saveTimestamp: Long,
    val lastUpdateTimestamp: Long,
    val comment: String,
    val rating: Int,
    val watchStatus: MovieWatchStatus,
)
