package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.data.network.entities.MovieRemoteEntity
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.utils.toDoubleSafe
import com.emikhalets.medialib.utils.toSafeInt

object SerialMappers {

    fun mapRemoteEntityToEntity(entity: MovieRemoteEntity): SerialFullEntity {
        return SerialFullEntity(
            serialEntity = SerialEntity(
                id = 0,
                title = entity.title ?: "",
                titleRu = "",
                overview = "",
                poster = entity.poster ?: "",
                year = entity.year.toSafeInt(),
                imdbRating = entity.rating.toDoubleSafe(),
                saveTimestamp = 0,
                lastUpdateTimestamp = 0,
                comment = "",
                rating = 0,
                watchStatus = SerialWatchStatus.NONE,
            ),
            genres = emptyList()
        )
    }

    fun mapDbEntityToEntity(entity: SerialDbEntity): SerialEntity {
        return SerialEntity(
            id = entity.id,
            title = entity.title,
            titleRu = entity.titleRu,
            overview = entity.overview,
            poster = entity.poster,
            year = entity.year,
            imdbRating = entity.imdbRating,
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
            imdbRating = entity.serialEntity.imdbRating,
            saveTimestamp = entity.serialEntity.saveTimestamp,
            lastUpdateTimestamp = entity.serialEntity.lastUpdateTimestamp,
            comment = entity.serialEntity.comment,
            rating = entity.serialEntity.rating,
            watchStatus = entity.serialEntity.watchStatus,
            genres = entity.genres.map { it.name }
        )
    }
}