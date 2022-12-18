package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.network.MovieResponse
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.utils.execute
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val moviesApi: MoviesApi,
) {

    suspend fun searchOmdb(id: String): Result<MovieResponse> {
        return execute { moviesApi.getMovieDetails(id) }
    }
}