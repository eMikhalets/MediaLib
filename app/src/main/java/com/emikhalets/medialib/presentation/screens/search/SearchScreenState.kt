package com.emikhalets.medialib.presentation.screens.search

import androidx.paging.PagingData
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchScreenState(
    val moviesFlow: Flow<PagingData<MovieSearchResult>> = flowOf(),
) {

    fun setMoviesFlow(moviesFlow: Flow<PagingData<MovieSearchResult>>): SearchScreenState {
        return this.copy(moviesFlow = moviesFlow)
    }
}