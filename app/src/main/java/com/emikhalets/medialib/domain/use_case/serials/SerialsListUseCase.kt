package com.emikhalets.medialib.domain.use_case.serials

import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class SerialsListUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getSerialsList(): Result<Flow<List<SerialFullEntity>>> {
        return databaseRepository.getSerialsListFlowOrderByLastUpdated()
    }

    suspend fun getSerialsListWithQuery(
        query: String,
        serialsList: List<SerialFullEntity>,
    ): Result<Flow<List<SerialFullEntity>>> {
        return withContext(Dispatchers.IO) {
            val newSerialsList = serialsList.filter {
                it.serialEntity.title.contains(query) ||
                        it.serialEntity.titleRu.contains(query) ||
                        query.contains(it.serialEntity.title) ||
                        query.contains(it.serialEntity.titleRu)
            }
            Result.success(flowOf(newSerialsList))
        }
    }
}