package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMovie(movieId: Long): Result<Flow<MovieFullEntity>> {
        return databaseRepository.getMovieFlowById(movieId)
    }

    suspend fun updateMoviePosterUrl(posterUrl: String, entity: MovieFullEntity): Result<Unit> {
        val newMovieEntity = entity.movieEntity.copy(poster = posterUrl)
        val newEntity = entity.copy(movieEntity = newMovieEntity)
        return databaseRepository.updateMovie(newEntity)
    }

    suspend fun updateMovieRating(rating: Int, entity: MovieFullEntity): Result<Unit> {
        val newMovieEntity = entity.movieEntity.copy(rating = rating)
        val newEntity = entity.copy(movieEntity = newMovieEntity)
        return databaseRepository.updateMovie(newEntity)
    }
}