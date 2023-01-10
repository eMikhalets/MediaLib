package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.network.MovieRemoteEntity
import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import com.emikhalets.medialib.utils.toDoubleSafe
import com.emikhalets.medialib.utils.toSafeInt

object SerialMappers {

    fun mapSerialRemoteToSerial(entity: MovieRemoteEntity): SerialEntity {
        return SerialEntity(
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
        )
    }
}