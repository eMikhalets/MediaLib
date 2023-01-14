package com.emikhalets.medialib.domain.entities.serials

import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity

data class SerialFullEntity(
    val serialEntity: SerialEntity,
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
            if (serialEntity.year > 0) {
                append(serialEntity.year.toString())
            }
            if (genres.isNotEmpty()) {
                append("  -  ${genres.joinToString { it.name }}")
            }
        }
    }
}
