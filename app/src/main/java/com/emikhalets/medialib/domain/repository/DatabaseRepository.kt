package com.emikhalets.medialib.domain.repository

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    // Movies Dao

    suspend fun getMoviesListFlowOrderByLastUpdated(): Result<Flow<List<MovieFullEntity>>>

    suspend fun getMovieFlowById(movieId: Long): Result<Flow<MovieFullEntity>>

    suspend fun getMovieById(movieId: Long): Result<MovieFullEntity>

    suspend fun insertMovie(entity: MovieFullEntity): Result<Unit>

    suspend fun updateMovie(entity: MovieFullEntity): Result<Unit>

    suspend fun deleteMovie(entity: MovieFullEntity): Result<Unit>

    // Serials Dao

    suspend fun getSerialsListFlowOrderByLastUpdated(): Result<Flow<List<SerialFullEntity>>>

    suspend fun getSerialFlowById(serialId: Long): Result<Flow<SerialFullEntity>>

    suspend fun getSerialById(serialId: Long): Result<SerialFullEntity>

    suspend fun insertSerial(entity: SerialFullEntity): Result<Unit>

    suspend fun updateSerial(entity: SerialFullEntity): Result<Unit>

    suspend fun deleteSerial(entity: SerialFullEntity): Result<Unit>
}