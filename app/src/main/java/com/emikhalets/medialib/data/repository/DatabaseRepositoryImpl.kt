package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.genres.GenresDao
import com.emikhalets.medialib.data.database.movies.MoviesDao
import com.emikhalets.medialib.data.database.serials.SerialsDao
import com.emikhalets.medialib.data.mappers.MovieMappers
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val serialsDao: SerialsDao,
    private val genresDao: GenresDao,
) : DatabaseRepository {

//    suspend fun insertItem(item: MovieDbEntity): Result<Long> = execute { moviesDao.insert(item) }
//    suspend fun insertItem(item: SerialDbEntity): Result<Long> = execute { serialsDao.insert(item) }
//    suspend fun insertItem(item: BookDB): Result<Long> = execute { booksDao.insert(item) }
//
//    suspend fun updateItem(item: MovieDbEntity): Result<Int> = execute { moviesDao.update(item) }
//    suspend fun updateItem(item: SerialDbEntity): Result<Int> = execute { serialsDao.update(item) }
//    suspend fun updateItem(item: BookDB): Result<Int> = execute { booksDao.update(item) }
//
//    suspend fun deleteItem(item: MovieDbEntity): Result<Int> = execute { moviesDao.delete(item) }
//    suspend fun deleteItem(item: SerialDbEntity): Result<Int> = execute { serialsDao.delete(item) }
//    suspend fun deleteItem(item: BookDB): Result<Int> = execute { booksDao.delete(item) }
//
//    suspend fun getMovieFlow(id: Int): Result<Flow<MovieDbEntity?>> = execute { moviesDao.getItem(id) }
//    suspend fun getSerialFlow(id: Int): Result<Flow<SerialDbEntity?>> = execute { serialsDao.getItem(id) }
//    suspend fun getBookFlow(id: Int): Result<Flow<BookDB?>> = execute { booksDao.getItem(id) }
//
//    suspend fun getMovies() = execute { moviesDao.getAllItemsFlow() }
//    suspend fun getMovies(query: String) = execute { moviesDao.searchByTitle(query) }
//
//    suspend fun getSerials() = execute { serialsDao.getAllItemsFlow() }
//    suspend fun getSerials(query: String) = execute { serialsDao.searchByTitle(query) }
//
//    suspend fun getBooks() = execute { booksDao.getAllItemsFlow() }
//    suspend fun getBooks(query: String) = execute { booksDao.searchByTitle(query) }

    // Movies

    override suspend fun getMoviesListFlowOrderByLastUpdated(): Result<Flow<List<MovieEntity>>> {
        return execute {
            val flow = moviesDao.getAllItemsFlowOrderByLastUpdate()
            flow.map { list -> list.map { movie -> MovieMappers.mapMovieDbToMovie(movie) } }
        }
    }

    override suspend fun getMovieFlowById(movieId: Long): Result<Flow<MovieFullEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieById(movieId: Long): Result<MovieFullEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMoviePosterUrl(posterUrl: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMovieRating(rating: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovie(entity: MovieFullEntity): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMovie(entity: MovieFullEntity): Result<Unit> {
        TODO("Not yet implemented")
    }

    // Serials

    override suspend fun getSerialsListFlowOrderByLastUpdated(): Result<Flow<List<SerialFullEntity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSerialFlowById(serialId: Long): Result<Flow<SerialFullEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSerialById(serialId: Long): Result<SerialFullEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSerialPosterUrl(posterUrl: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSerialRating(rating: Int): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSerial(entity: SerialFullEntity): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun updateSerial(entity: SerialFullEntity): Result<Unit> {
        TODO("Not yet implemented")
    }
}