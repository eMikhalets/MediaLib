package com.emikhalets.medialib.domain.entities.movies

data class MovieEntity(
    val id: Long,
    val title: String,
    val titleRu: String,
    val overview: String,
    val poster: String,
    val year: Int,
    val saveTimestamp: Long,
    val lastUpdateTimestamp: Long,
    val comment: String,
    val rating: Int,
    val watchStatus: MovieWatchStatus,
    val runtime: String,
    val awards: String,
)
