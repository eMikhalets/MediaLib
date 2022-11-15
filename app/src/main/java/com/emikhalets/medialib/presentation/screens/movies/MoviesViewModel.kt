package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.views.MovieEntity
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesScreenState(
    val movies: List<MovieEntity> = emptyList(),
) {

    fun setMovies(movies: List<MovieEntity>): MoviesScreenState {
        return this.copy(movies = movies)
    }
}

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
                repo.getItems()
            } else {
                delay(750)
                repo.getItems(query)
            }
            moviesResponse.onSuccess { movies ->
                movies.collectLatest { state = state.setMovies(it.map { db -> MovieEntity(db) }) }
            }
        }
    }

    fun addMovie(name: String, year: String, comment: String) {
        viewModelScope.launch {
            val entity = MovieDB(name, year, comment)
            repo.insertItem(entity)
        }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}