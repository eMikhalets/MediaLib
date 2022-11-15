package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.MoviesDao
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
) : MoviesRepository {

    override suspend fun insertItem(item: MovieDB): Result<Long> {
        return execute { moviesDao.insert(item) }
    }

    override suspend fun updateItem(item: MovieDB): Result<Int> {
        return execute { moviesDao.update(item) }
    }

    override suspend fun deleteItem(item: MovieDB): Result<Int> {
        return execute { moviesDao.delete(item) }
    }

    override suspend fun getItem(id: Int): Result<Flow<MovieDB>> {
        return execute { moviesDao.getItem(id) }
    }

    override suspend fun getItems(query: String): Result<Flow<List<MovieDB>>> {
        return execute {
            if (query.isEmpty()) {
                moviesDao.getAllItemsFlow()
            } else {
                moviesDao.searchByTitle(query)
            }
        }
    }
}