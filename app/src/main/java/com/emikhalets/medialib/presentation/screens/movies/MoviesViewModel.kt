package com.emikhalets.medialib.presentation.screens.movies

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.use_case.movies.MoviesListUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCase: MoviesListUseCase,
) : BaseViewModel<MoviesState>() {

    private var searchJob: Job? = null

    override fun initState() = MoviesState()

    fun getMoviesList() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.getMoviesList()
                .onSuccess { handleMoviesListSuccess(it) }
                .onFailure { handleFailure(it) }
        }
    }

    fun searchMovies(query: String) {
        cancelJob(searchJob, "Starting a new search request")
        searchJob = launchIO {
            delay(750)
            setState { it.copy(loading = true) }
            useCase.getMoviesListWithQuery(query, currentState.movies)
                .onSuccess { handleSearchMovies(it) }
                .onFailure { handleFailure(it) }
        }
    }

    private suspend fun handleMoviesListSuccess(flow: Flow<List<MovieFullEntity>>) {
        flow.collectLatest { list ->
            setState { it.copy(loading = false, movies = list, showedMovies = list) }
        }
    }

    private suspend fun handleSearchMovies(flow: Flow<List<MovieFullEntity>>) {
        flow.collectLatest { list ->
            setState { it.copy(loading = false, showedMovies = list) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }

    @Suppress("SameParameterValue")
    private fun cancelJob(job: Job?, causeMessage: String) {
        val cause = CancellationException(causeMessage)
        if (job != null && job.isActive) job.cancel(cause)
    }
}