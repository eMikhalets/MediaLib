package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import javax.inject.Inject

class MovieEditUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMovie(movieId: Long): Result<MovieFullEntity> {
        return databaseRepository.getMovieById(movieId)
    }

    suspend fun saveMovie(entity: MovieFullEntity): Result<Unit> {
        return if (entity.movieEntity.id == 0) {
            databaseRepository.insertMovie(entity)
        } else {
            databaseRepository.updateMovie(entity)
        }
    }
}