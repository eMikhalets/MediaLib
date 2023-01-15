package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject

class MovieEditUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMovie(movieId: Long): Result<MovieFullEntity> {
        return databaseRepository.getMovieById(movieId)
    }

    suspend fun saveMovie(entity: MovieFullEntity): Result<Unit> {
        return if (entity.movieEntity.id == 0L) {
            databaseRepository.insertMovie(entity)
        } else {
            val newMovieEntity = entity.movieEntity
                .copy(lastUpdateTimestamp = Calendar.getInstance().timeInMillis)
            val newEntity = entity.copy(movieEntity = newMovieEntity)
            databaseRepository.updateMovie(newEntity)
        }
    }
}