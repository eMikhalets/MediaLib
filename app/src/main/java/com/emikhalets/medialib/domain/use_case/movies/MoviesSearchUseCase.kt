package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.NetworkRepository

class MoviesSearchUseCase(
    private val networkRepository: NetworkRepository,
) {

    suspend fun searchMovie(id: String): Result<MovieFullEntity> {
        return networkRepository.searchMovie(id)
    }
}