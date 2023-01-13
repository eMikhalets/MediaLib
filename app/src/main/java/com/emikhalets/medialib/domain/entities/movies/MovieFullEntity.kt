package com.emikhalets.medialib.domain.entities.movies

import com.emikhalets.medialib.domain.entities.genres.GenreEntity

data class MovieFullEntity(
    val movieEntity: MovieEntity,
    val genres: List<GenreEntity>,
) {

    fun formatGenres(): String {
        return genres.joinToString(separator = " ,") { it.name }
    }
}