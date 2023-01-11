package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.NetworkRepository

class SerialsSearchUseCase(
    private val networkRepository: NetworkRepository,
) {

    suspend fun searchSerial(id: String): Result<SerialFullEntity> {
        return networkRepository.searchSerial(id)
    }
}