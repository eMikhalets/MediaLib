package com.emikhalets.medialib.domain.entities.serials

import androidx.compose.runtime.Composable
import com.emikhalets.medialib.domain.entities.crew.CrewEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.ratings.CrewType.Companion.getTypeName
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity

data class SerialFullEntity(
    val serialEntity: SerialEntity,
    val genres: List<GenreEntity>,
    val ratings: List<RatingEntity>,
    val crew: List<CrewEntity>,
) {

    fun formatGenres(): String {
        return genres.joinToString(separator = ", ") { it.name }
    }

    fun formatRatings(): List<String> {
        return ratings.map { "${it.source}: ${it.value}" }
    }

    @Composable
    fun formatCrew(): List<String> {
        return crew.map { "${it.type.getTypeName()}: ${it.name}" }
    }

    fun formatListItemInfo(): String {
        return buildString {
            if (serialEntity.runtime.isNotEmpty()) {
                append(serialEntity.runtime)
            }
            if (serialEntity.year > 0) {
                if (this.isEmpty()) {
                    append(serialEntity.year.toString())
                } else {
                    append("  -  ${serialEntity.year}")
                }
            }
            if (genres.isNotEmpty()) {
                if (this.isEmpty()) {
                    append(genres.joinToString { it.name })
                } else {
                    append("  -  ${genres.joinToString { it.name }}")
                }
            }
        }
    }

    fun formatDetailsInfo(): String {
        return buildString {
            if (serialEntity.runtime.isNotEmpty()) {
                append(serialEntity.runtime)
            }
            if (serialEntity.year > 0) {
                if (this.isEmpty()) {
                    append(serialEntity.year.toString())
                } else {
                    append("  -  ${serialEntity.year}")
                }
            }
        }
    }
}
