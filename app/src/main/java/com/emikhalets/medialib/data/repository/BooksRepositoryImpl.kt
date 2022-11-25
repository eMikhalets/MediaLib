package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.BooksDao
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksDao: BooksDao,
) : BooksRepository {

    override suspend fun insertItem(item: BookDB): Result<Long> {
        return execute { booksDao.insert(item) }
    }

    override suspend fun updateItem(item: BookDB): Result<Int> {
        return execute { booksDao.update(item) }
    }

    override suspend fun deleteItem(item: BookDB): Result<Int> {
        return execute { booksDao.delete(item) }
    }

    override suspend fun getItem(id: Int): Result<Flow<BookDB?>> {
        return execute { booksDao.getItem(id) }
    }

    override suspend fun getItems(query: String): Result<Flow<List<BookDB>>> {
        return execute {
            if (query.isEmpty()) {
                booksDao.getAllItemsFlow()
            } else {
                booksDao.searchByTitle(query)
            }
        }
    }
}