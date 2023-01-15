package com.emikhalets.medialib.domain.entities.serials

data class SerialEntity(
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
    val watchStatus: SerialWatchStatus,
    val runtime: String,
    val awards: String,
)
