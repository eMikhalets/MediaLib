package com.emikhalets.medialib.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.paging.MoviesPagingSource
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
) : MoviesRepository {

    override suspend fun search(query: String): Flow<PagingData<MovieSearchResult>> {
        val source = MoviesPagingSource(query, moviesApi)
        return Pager(PagingConfig(20)) { source }.flow
    }

    override suspend fun details(id: Int): Result<MovieDetailsResponse> {
        return execute { moviesApi.getMovieDetails(id) }
    }
}