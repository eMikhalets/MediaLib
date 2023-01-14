package com.emikhalets.medialib.domain.entities.serials

import com.emikhalets.medialib.domain.entities.genres.GenreEntity

data class SerialFullEntity(
    val serialEntity: SerialEntity,
    val genres: List<GenreEntity>,
) {

    fun formatGenres(): String {
        return genres.joinToString(separator = ", ") { it.name }
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
