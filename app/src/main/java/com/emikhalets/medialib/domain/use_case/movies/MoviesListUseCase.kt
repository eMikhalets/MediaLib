package com.emikhalets.medialib.domain.use_case.movies

import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class MoviesListUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun getMoviesList(): Result<Flow<List<MovieEntity>>> {
        return databaseRepository.getMoviesListFlowOrderByLastUpdated()
    }

    suspend fun getMoviesListWithQuery(
        query: String,
        moviesList: List<MovieFullEntity>,
    ): Result<Flow<List<MovieFullEntity>>> {
        return withContext(Dispatchers.IO) {
            val newMoviesList = moviesList.filter {
                it.movieEntity.title.contains(query) ||
                        it.movieEntity.titleRu.contains(query) ||
                        query.contains(it.movieEntity.title) ||
                        query.contains(it.movieEntity.titleRu)
            }
            Result.success(flowOf(newMoviesList))
        }
    }
}