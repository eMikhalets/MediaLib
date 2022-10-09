package com.emikhalets.medialib.data.repository

import androidx.paging.PagingData
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun search(query: String): Flow<PagingData<MovieSearchResult>>

    suspend fun details(id: Int): Result<MovieDetailsResponse>

    suspend fun saveLocal(movieRemote: MovieDetailsResponse): Result<Boolean>

    suspend fun getMoviesLocal(): Result<Flow<List<MovieDB>>>

    suspend fun getMoviesLocal(query: String): Result<Flow<List<MovieDB>>>

    suspend fun getMovieLocal(id: Int): Result<MovieDB>

    suspend fun isMovieSaved(id: Int): Result<Boolean>
}