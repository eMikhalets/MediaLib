package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.mappers.MovieMappers
import com.emikhalets.medialib.data.mappers.SerialMappers
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.NetworkRepository
import com.emikhalets.medialib.utils.execute
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
) : NetworkRepository {

    override suspend fun searchMovie(id: String): Result<MovieFullEntity> {
        return execute {
            val response = moviesApi.getMovieDetails(id)
            MovieMappers.mapRemoteEntityToEntity(response)
        }
    }

    override suspend fun searchSerial(id: String): Result<SerialFullEntity> {
        return execute {
            val response = moviesApi.getMovieDetails(id)
            SerialMappers.mapRemoteEntityToEntity(response)
        }
    }
}