package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.data.network.entities.MovieRemoteEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import java.util.*

object SerialMappers {

    fun mapRemoteEntityToEntity(entity: MovieRemoteEntity): SerialFullEntity {
        return SerialFullEntity(
            serialEntity = SerialEntity(
                id = 0,
                title = entity.title ?: "",
                titleRu = "",
                overview = entity.plot ?: "",
                poster = entity.poster ?: "",
                year = entity.formatYear(),
                saveTimestamp = Calendar.getInstance().timeInMillis,
                lastUpdateTimestamp = Calendar.getInstance().timeInMillis,
                comment = "",
                rating = 0,
                watchStatus = SerialWatchStatus.NONE,
            ),
            genres = mapGenres(entity.genre)
        )
    }

    private fun mapGenres(genres: String?): List<GenreEntity> {
        return try {
            val arr = genres?.split(", ") ?: return emptyList()
            arr.map { GenreEntity(it, GenreType.SERIAL) }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    fun mapDbEntityToEntity(entity: SerialDbEntity): SerialEntity {
        return SerialEntity(
            id = entity.id,
            title = entity.title,
            titleRu = entity.titleRu,
            overview = entity.overview,
            poster = entity.poster,
            year = entity.year,
            saveTimestamp = entity.saveTimestamp,
            lastUpdateTimestamp = entity.lastUpdateTimestamp,
            comment = entity.comment,
            rating = entity.rating,
            watchStatus = entity.watchStatus,
        )
    }

    fun mapEntityToDbEntity(entity: SerialFullEntity): SerialDbEntity {
        return SerialDbEntity(
            id = entity.serialEntity.id,
            title = entity.serialEntity.title,
            titleRu = entity.serialEntity.titleRu,
            overview = entity.serialEntity.overview,
            poster = entity.serialEntity.poster,
            year = entity.serialEntity.year,
            saveTimestamp = entity.serialEntity.saveTimestamp,
            lastUpdateTimestamp = entity.serialEntity.lastUpdateTimestamp,
            comment = entity.serialEntity.comment,
            rating = entity.serialEntity.rating,
            watchStatus = entity.serialEntity.watchStatus,
            genres = entity.genres.map { it.name }
        )
    }
}