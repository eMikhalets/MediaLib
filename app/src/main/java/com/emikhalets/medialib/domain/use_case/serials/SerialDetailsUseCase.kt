package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class SerialDetailsUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getSerial(serialId: Long): Result<Flow<SerialFullEntity>> {
        return databaseRepository.getSerialFlowById(serialId)
    }

    suspend fun updateSerialPosterUrl(posterUrl: String, entity: SerialFullEntity): Result<Unit> {
        val newMovieEntity = entity.serialEntity.copy(poster = posterUrl)
        val newEntity = entity.copy(serialEntity = newMovieEntity)
        return databaseRepository.updateSerial(newEntity)
    }

    suspend fun updateSerialRating(rating: Int, entity: SerialFullEntity): Result<Unit> {
        val newMovieEntity = entity.serialEntity.copy(rating = rating)
        val newEntity = entity.copy(serialEntity = newMovieEntity)
        return databaseRepository.updateSerial(newEntity)
    }
}