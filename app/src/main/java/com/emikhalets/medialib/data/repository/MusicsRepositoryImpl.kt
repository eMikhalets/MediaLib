package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.MusicsDao
import com.emikhalets.medialib.data.entity.database.MusicDB
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicsRepositoryImpl @Inject constructor(
    private val musicsDao: MusicsDao,
) : MusicsRepository {

    override suspend fun insertItem(item: MusicDB): Result<Long> {
        return execute { musicsDao.insert(item) }
    }

    override suspend fun updateItem(item: MusicDB): Result<Int> {
        return execute { musicsDao.update(item) }
    }

    override suspend fun deleteItem(item: MusicDB): Result<Int> {
        return execute { musicsDao.delete(item) }
    }

    override suspend fun getItem(id: Int): Result<Flow<MusicDB?>> {
        return execute { musicsDao.getItem(id) }
    }

    override suspend fun getItems(query: String): Result<Flow<List<MusicDB>>> {
        return execute {
            if (query.isEmpty()) {
                musicsDao.getAllItemsFlow()
            } else {
                musicsDao.searchByTitle(query)
            }
        }
    }
}