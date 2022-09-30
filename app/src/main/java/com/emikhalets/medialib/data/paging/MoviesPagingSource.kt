package com.emikhalets.medialib.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import com.emikhalets.medialib.data.network.MoviesApi

class MoviesPagingSource(
    private val query: String,
    private val moviesApi: MoviesApi,
) : PagingSource<Int, MovieSearchResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchResult> {
        return try {
            val nextPage = params.key ?: 1
            val response = moviesApi.getSearchedMovies(query, nextPage)
            val nextKey = if (response.totalPages == nextPage || response.totalPages == 0) {
                null
            } else {
                nextPage + 1
            }
            LoadResult.Page(response.results ?: emptyList(), null, nextKey)
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieSearchResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}