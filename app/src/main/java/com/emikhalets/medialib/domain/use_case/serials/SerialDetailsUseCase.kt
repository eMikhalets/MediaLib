package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import java.util.*

class SerialDetailsUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getSerial(serialId: Long): Result<SerialFullEntity> {
        return databaseRepository.getSerialById(serialId)
    }

    suspend fun updateSerialPosterUrl(posterUrl: String, entity: SerialFullEntity): Result<Unit> {
        val newMovieEntity = entity.serialEntity.copy(
            poster = posterUrl,
            lastUpdateTimestamp = Calendar.getInstance().timeInMillis
        )
        val newEntity = entity.copy(serialEntity = newMovieEntity)
        return databaseRepository.updateSerial(newEntity)
    }

    suspend fun updateSerialRating(rating: Int, entity: SerialFullEntity): Result<Unit> {
        val newMovieEntity = entity.serialEntity.copy(
            rating = rating,
            lastUpdateTimestamp = Calendar.getInstance().timeInMillis
        )
        val newEntity = entity.copy(serialEntity = newMovieEntity)
        return databaseRepository.updateSerial(newEntity)
    }

    suspend fun deleteSerial(entity: SerialFullEntity): Result<Unit> {
        return databaseRepository.deleteSerial(entity)
    }
}