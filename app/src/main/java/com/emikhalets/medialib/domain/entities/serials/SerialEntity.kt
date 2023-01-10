package com.emikhalets.medialib.domain.entities.serials

import com.emikhalets.medialib.utils.enums.ItemStatus

data class SerialEntity(
    val id: Int,
    val title: String,
    val titleRu: String,
    val overview: String,
    val poster: String,
    val releaseYear: Int,
    val saveDate: Long,
    val comment: String,
    val rating: Int,
    val tags: String,
    val status: ItemStatus,
)
