package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialEntity
import com.emikhalets.medialib.domain.repository.NetworkRepository

class SerialsSearchUseCase(
    private val networkRepository: NetworkRepository,
) {

    suspend fun searchSerial(id: Int): Result<SerialEntity> {
        return networkRepository.searchSerial(id)
    }
}