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
            val genresDb = GenresMappers.mapListToDbList(entity.genres)
            genresDao.insert(genresDb)
            val movieDb = MovieMappers.mapEntityToDbEntity(entity)
            moviesDao.insert(movieDb)
        }
    }

    override suspend fun updateMovie(entity: MovieFullEntity): Result<Unit> {
        return execute {
            val genresDb = GenresMappers.mapListToDbList(entity.genres)
            genresDao.insert(genresDb)
            val movieDb = MovieMappers.mapEntityToDbEntity(entity)
            moviesDao.update(movieDb)
        }
    }

    override suspend fun deleteMovie(entity: MovieFullEntity): Result<Unit> {
        return execute {
            val movieDb = MovieMappers.mapEntityToDbEntity(entity)
            moviesDao.delete(movieDb)
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
            val genresDb = GenresMappers.mapListToDbList(entity.genres)
            genresDao.insert(genresDb)
            val serialDb = SerialMappers.mapEntityToDbEntity(entity)
            serialsDao.insert(serialDb)
        }
    }

    override suspend fun updateSerial(entity: SerialFullEntity): Result<Unit> {
        return execute {
            val genresDb = GenresMappers.mapListToDbList(entity.genres)
            genresDao.insert(genresDb)
            val serialDb = SerialMappers.mapEntityToDbEntity(entity)
            serialsDao.update(serialDb)
        }
    }

    override suspend fun deleteSerial(entity: SerialFullEntity): Result<Unit> {
        return execute {
            val serialDb = SerialMappers.mapEntityToDbEntity(entity)
            serialsDao.delete(serialDb)
        }
    }

    // Other

    private suspend fun getGenres(genres: List<String>): List<GenreEntity> {
        return try {
            val list = mutableListOf<GenreEntity>()
            genres.forEach { genreName ->
                val genreDb = genresDao.getItem(genreName)
                list.add(GenresMappers.mapDbEntityToEntity(genreDb))
            }
            list
        } catch (ex: Exception) {
            emptyList()
        }
    }
}