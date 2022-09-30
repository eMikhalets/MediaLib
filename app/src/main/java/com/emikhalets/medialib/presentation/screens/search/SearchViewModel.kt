package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.emikhalets.medialib.data.repository.MoviesRepository
import com.emikhalets.medialib.utils.GenresHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: MoviesRepository,
    val genreHelper: GenresHelper,
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())
        private set

    private var searchJob: Job? = null

    fun search(query: String) {
        cancelJob(searchJob, "Starting a new search request")
        searchJob = viewModelScope.launch {
            delay(750)
            val moviesFlow = repo.search(query).cachedIn(viewModelScope)
            state = state.setMoviesFlow(moviesFlow)
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}