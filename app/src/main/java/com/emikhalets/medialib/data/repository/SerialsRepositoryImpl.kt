package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.SerialsDao
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SerialsRepositoryImpl @Inject constructor(
    private val serialsDao: SerialsDao,
) : SerialsRepository {

    override suspend fun insertItem(item: SerialDB): Result<Long> {
        return execute { serialsDao.insert(item) }
    }

    override suspend fun updateItem(item: SerialDB): Result<Int> {
        return execute { serialsDao.update(item) }
    }

    override suspend fun deleteItem(item: SerialDB): Result<Int> {
        return execute { serialsDao.delete(item) }
    }

    override suspend fun getItem(id: Int): Result<Flow<SerialDB>> {
        return execute { serialsDao.getItem(id) }
    }

    override suspend fun getItems(query: String): Result<Flow<List<SerialDB>>> {
        return execute {
            if (query.isEmpty()) {
                serialsDao.getAllItemsFlow()
            } else {
                serialsDao.searchByTitle(query)
            }
        }
    }
}