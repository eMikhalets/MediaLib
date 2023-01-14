package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class MoviesListUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMoviesList(): Result<Flow<List<MovieFullEntity>>> {
        return databaseRepository.getMoviesListFlowOrderByLastUpdated()
    }

    suspend fun getMoviesListWithQuery(
        query: String,
        moviesList: List<MovieFullEntity>,
    ): Result<Flow<List<MovieFullEntity>>> {
        return withContext(Dispatchers.IO) {
            if (query.isEmpty()) {
                Result.success(flowOf(moviesList))
            } else {
                val newMoviesList = moviesList.filter {
                    val title = it.movieEntity.title.lowercase()
                    val titleRu = it.movieEntity.titleRu.lowercase()
                    title.contains(query) || titleRu.contains(query)
                }
                Result.success(flowOf(newMoviesList))
            }
        }
    }
}