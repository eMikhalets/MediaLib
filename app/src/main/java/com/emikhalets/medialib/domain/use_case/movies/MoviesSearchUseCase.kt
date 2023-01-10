package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.repository.NetworkRepository

class MoviesSearchUseCase(
    private val networkRepository: NetworkRepository,
) {

    suspend fun searchMovie(id: Int): Result<MovieEntity> {
        return networkRepository.searchMovie(id)
    }
}