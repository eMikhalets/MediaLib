package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.views.MovieEntity
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(MoviesScreenState())
        private set

    private var moviesJob: Job? = null

    fun getSavedMovies(query: String) {
        cancelJob(moviesJob, "Starting a new search request")
        moviesJob = viewModelScope.launch {
            val moviesResponse = if (query.isEmpty()) {
                repo.getMoviesLocal()
            } else {
                delay(750)
                repo.getMoviesLocal(query)
            }
            moviesResponse.onSuccess { movies ->
                movies.collectLatest { state = state.setMovies(it.map { db -> MovieEntity(db) }) }
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}