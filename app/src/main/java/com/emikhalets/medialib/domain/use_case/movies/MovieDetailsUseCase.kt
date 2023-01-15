package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMovie(movieId: Long): Result<MovieFullEntity> {
        return databaseRepository.getMovieById(movieId)
    }

    suspend fun updateMoviePosterUrl(posterUrl: String, entity: MovieFullEntity): Result<Unit> {
        val newMovieEntity = entity.movieEntity.copy(
            poster = posterUrl,
            lastUpdateTimestamp = Calendar.getInstance().timeInMillis
        )
        val newEntity = entity.copy(movieEntity = newMovieEntity)
        return databaseRepository.updateMovie(newEntity)
    }

    suspend fun updateMovieRating(rating: Int, entity: MovieFullEntity): Result<Unit> {
        val newMovieEntity = entity.movieEntity.copy(
            rating = rating,
            lastUpdateTimestamp = Calendar.getInstance().timeInMillis
        )
        val newEntity = entity.copy(movieEntity = newMovieEntity)
        return databaseRepository.updateMovie(newEntity)
    }

    suspend fun deleteMovie(entity: MovieFullEntity): Result<Unit> {
        return databaseRepository.deleteMovie(entity)
    }
}