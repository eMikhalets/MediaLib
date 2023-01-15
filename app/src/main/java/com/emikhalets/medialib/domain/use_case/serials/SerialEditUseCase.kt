package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class SerialEditUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getSerial(serialId: Long): Result<SerialFullEntity> {
        return databaseRepository.getSerialById(serialId)
    }

    suspend fun saveSerial(entity: SerialFullEntity): Result<Unit> {
        return if (entity.serialEntity.id == 0L) {
            databaseRepository.insertSerial(entity)
        } else {
            val newMovieEntity = entity.serialEntity
                .copy(lastUpdateTimestamp = Calendar.getInstance().timeInMillis)
            val newEntity = entity.copy(serialEntity = newMovieEntity)
            databaseRepository.updateSerial(newEntity)
        }
    }
}