package com.emikhalets.medialib.domain.entities.serials

import com.emikhalets.medialib.domain.entities.genres.GenreEntity

data class SerialFullEntity(
    val serialEntity: SerialEntity,
    val genres: List<GenreEntity>,
)
