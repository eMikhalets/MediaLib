package com.emikhalets.medialib.data.repository

import androidx.paging.PagingData
import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun search(query: String): Flow<PagingData<MovieSearchResult>>

    suspend fun details(id: Int): Result<MovieDetailsResponse>
}