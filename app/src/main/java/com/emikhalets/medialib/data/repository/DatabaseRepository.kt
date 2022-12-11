package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.BooksDao
import com.emikhalets.medialib.data.database.MoviesDao
import com.emikhalets.medialib.data.database.SerialsDao
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.SerialDB
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val serialsDao: SerialsDao,
    private val booksDao: BooksDao,
) {

    suspend fun insertItem(item: MovieDB): Result<Long> = execute { moviesDao.insert(item) }
    suspend fun insertItem(item: SerialDB): Result<Long> = execute { serialsDao.insert(item) }
    suspend fun insertItem(item: BookDB): Result<Long> = execute { booksDao.insert(item) }

    suspend fun updateItem(item: MovieDB): Result<Int> = execute { moviesDao.update(item) }
    suspend fun updateItem(item: SerialDB): Result<Int> = execute { serialsDao.update(item) }
    suspend fun updateItem(item: BookDB): Result<Int> = execute { booksDao.update(item) }

    suspend fun deleteItem(item: MovieDB): Result<Int> = execute { moviesDao.delete(item) }
    suspend fun deleteItem(item: SerialDB): Result<Int> = execute { serialsDao.delete(item) }
    suspend fun deleteItem(item: BookDB): Result<Int> = execute { booksDao.delete(item) }

    suspend fun getMovieFlow(id: Int): Result<Flow<MovieDB?>> = execute { moviesDao.getItem(id) }
    suspend fun getSerialFlow(id: Int): Result<Flow<SerialDB?>> = execute { serialsDao.getItem(id) }
    suspend fun getBookFlow(id: Int): Result<Flow<BookDB?>> = execute { booksDao.getItem(id) }

    suspend fun getMovies() = execute { moviesDao.getAllItemsFlow() }
    suspend fun getMovies(query: String) = execute { moviesDao.searchByTitle(query) }

    suspend fun getSerials() = execute { serialsDao.getAllItemsFlow() }
    suspend fun getSerials(query: String) = execute { serialsDao.searchByTitle(query) }

    suspend fun getBooks() = execute { booksDao.getAllItemsFlow() }
    suspend fun getBooks(query: String) = execute { booksDao.searchByTitle(query) }
}