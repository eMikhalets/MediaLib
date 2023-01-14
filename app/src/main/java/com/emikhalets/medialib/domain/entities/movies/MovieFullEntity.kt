package com.emikhalets.medialib.domain.entities.movies

import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity

data class MovieFullEntity(
    val movieEntity: MovieEntity,
    val genres: List<GenreEntity>,
    val ratings: List<RatingEntity>,
) {

    fun formatGenres(): String {
        return genres.joinToString(separator = ", ") { it.name }
    }

    fun formatRatings(): List<String> {
        return ratings.map { "${it.source}: ${it.value}" }
    }

    fun formatListItemInfo(): String {
        return buildString {
            if (movieEntity.year > 0) {
                append(movieEntity.year.toString())
            }
            if (genres.isNotEmpty()) {
                append("  -  ${genres.joinToString { it.name }}")
            }
        }
    }
}