package com.emikhalets.medialib.presentation.screens.movies.details

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.use_case.movies.MovieDetailsUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val useCase: MovieDetailsUseCase,
) : BaseViewModel<MovieDetailsState>() {

    override fun initState() = MovieDetailsState()

    fun resetError() = setState { it.copy(error = null) }

    fun getMovie(movieId: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            if (movieId == 0L) {
                setState { it.copy(loading = false) }
            } else {
                useCase.getMovie(movieId)
                    .onSuccess { entity -> setState { it.copy(loading = false, entity = entity) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            }
        }
    }

    fun updateMovieRating(rating: Int) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.updateMovieRating(rating, entity)
                    .onSuccess { setState { it.copy(loading = false) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_save_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    fun updateMoviePoster(poster: String) {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.updateMoviePosterUrl(poster, entity)
                    .onSuccess { setState { it.copy(loading = false) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_save_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    fun deleteMovie() {
        launchIO {
            val entity = currentState.entity
            if (entity != null) {
                setState { it.copy(loading = true) }
                useCase.deleteMovie(entity)
                    .onSuccess { setState { it.copy(loading = false, deleted = true) } }
                    .onFailure { throwable -> handleFailure(throwable) }
            } else {
                val message = R.string.error_delete_entity_null
                setState { it.copy(error = UiString.create(message)) }
            }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }
}
