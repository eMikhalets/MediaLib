package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import javax.inject.Inject

class SerialEditUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getSerial(serialId: Long): Result<SerialFullEntity> {
        return databaseRepository.getSerialById(serialId)
    }

    suspend fun saveSerial(entity: SerialFullEntity): Result<Unit> {
        return if (entity.serialEntity.id == 0) {
            databaseRepository.insertSerial(entity)
        } else {
            databaseRepository.updateSerial(entity)
        }
    }
}