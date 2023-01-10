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

    suspend fun updateSerialPosterUrl(posterUrl: String): Result<Unit> {
        return databaseRepository.updateSerialPosterUrl(posterUrl)
    }

    suspend fun updateSerialRating(rating: Int): Result<Unit> {
        return databaseRepository.updateSerialRating(rating)
    }
}