package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.genres.GenresDao
import com.emikhalets.medialib.data.database.movies.MoviesDao
import com.emikhalets.medialib.data.database.serials.SerialsDao
import com.emikhalets.medialib.data.mappers.GenresMappers
import com.emikhalets.medialib.data.mappers.MovieMappers
import com.emikhalets.medialib.data.mappers.SerialMappers
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
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

    override suspend fun getMoviesListFlowOrderByLastUpdated(): Result<Flow<List<MovieFullEntity>>> {
        return execute {
            val flow = moviesDao.getAllItemsFlowOrderByLastUpdate()
            flow.map { list ->
                list.map { movieDb ->
                    val movieEntity = MovieMappers.mapDbEntityToEntity(movieDb)
                    val genres = getGenres(movieDb.genres)
                    MovieFullEntity(movieEntity, genres)
                }
            }
        }
    }

    override suspend fun getMovieFlowById(movieId: Long): Result<Flow<MovieFullEntity>> {
        return execute {
            val flow = moviesDao.getItemFlow(movieId)
            flow.map { movieDb ->
                val movieEntity = MovieMappers.mapDbEntityToEntity(movieDb)
                val genres = getGenres(movieDb.genres)
                MovieFullEntity(movieEntity, genres)
            }
        }
    }

    override suspend fun getMovieById(movieId: Long): Result<MovieFullEntity> {
        return execute {
            val movieDb = moviesDao.getItem(movieId)
            val movieEntity = MovieMappers.mapDbEntityToEntity(movieDb)
            val genres = getGenres(movieDb.genres)
            MovieFullEntity(movieEntity, genres)
        }
    }

    override suspend fun insertMovie(entity: MovieFullEntity): Result<Unit> {
        return execute {
            val movieDb = MovieMappers.mapEntityToDbEntity(entity)
            moviesDao.insert(movieDb)
        }
    }

    override suspend fun updateMovie(entity: MovieFullEntity): Result<Unit> {
        return execute {
            val movieDb = MovieMappers.mapEntityToDbEntity(entity)
            moviesDao.update(movieDb)
        }
    }

    // Serials

    override suspend fun getSerialsListFlowOrderByLastUpdated(): Result<Flow<List<SerialFullEntity>>> {
        return execute {
            val flow = serialsDao.getAllItemsFlowOrderByLastUpdate()
            flow.map { list ->
                list.map { serialDb ->
                    val serialEntity = SerialMappers.mapDbEntityToEntity(serialDb)
                    val genres = getGenres(serialDb.genres)
                    SerialFullEntity(serialEntity, genres)
                }
            }
        }
    }

    override suspend fun getSerialFlowById(serialId: Long): Result<Flow<SerialFullEntity>> {
        return execute {
            val flow = serialsDao.getItemFlow(serialId)
            flow.map { serialDb ->
                val serialEntity = SerialMappers.mapDbEntityToEntity(serialDb)
                val genres = getGenres(serialDb.genres)
                SerialFullEntity(serialEntity, genres)
            }
        }
    }

    override suspend fun getSerialById(serialId: Long): Result<SerialFullEntity> {
        return execute {
            val serialDb = serialsDao.getItem(serialId)
            val serialEntity = SerialMappers.mapDbEntityToEntity(serialDb)
            val genres = getGenres(serialDb.genres)
            SerialFullEntity(serialEntity, genres)
        }
    }

    override suspend fun insertSerial(entity: SerialFullEntity): Result<Unit> {
        return execute {
            val serialDb = SerialMappers.mapEntityToDbEntity(entity)
            serialsDao.insert(serialDb)
        }
    }

    override suspend fun updateSerial(entity: SerialFullEntity): Result<Unit> {
        return execute {
            val serialDb = SerialMappers.mapEntityToDbEntity(entity)
            serialsDao.update(serialDb)
        }
    }

    // Other

    private suspend fun getGenres(genres: List<String>): List<GenreEntity> {
        val list = mutableListOf<GenreEntity>()
        genres.forEach { genreName ->
            val genreDb = genresDao.getItem(genreName)
            list.add(GenresMappers.mapDbEntityToEntity(genreDb))
        }
        return list
    }
}