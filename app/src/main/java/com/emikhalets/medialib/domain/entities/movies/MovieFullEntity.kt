package com.emikhalets.medialib.domain.entities.movies

import com.emikhalets.medialib.domain.entities.GenreEntity

data class MovieFullEntity(
    val movieEntity: MovieEntity,
    val genres: List<GenreEntity>,
)
