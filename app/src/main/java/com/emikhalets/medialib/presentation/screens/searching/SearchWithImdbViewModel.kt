package com.emikhalets.medialib.presentation.screens.searching

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.use_case.searching.SearchImdbUseCase
import com.emikhalets.medialib.utils.BaseViewModel
import com.emikhalets.medialib.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchWithImdbViewModel @Inject constructor(
    private val useCase: SearchImdbUseCase,
) : BaseViewModel<SearchImdbState>() {

    override fun initState() = SearchImdbState()

    fun resetError() = setState { it.copy(error = null) }

    fun parseImdbId(url: String) {
        launchIO {
            try {
                val id = url.split("/")[4]
                setState { it.copy(imdbId = id) }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun searchAndSaveMovie() {
        launchIO {
            setState { it.copy(loading = true) }
            val imdbId = currentState.imdbId
            if (imdbId == null) {
                val message = R.string.error_parsing_imdb_id
                setState { it.copy(loading = false, error = UiString.create(message)) }
            } else {
                useCase.searchMovie(imdbId)
                    .onSuccess { entity -> saveMovie(entity) }
                    .onFailure { throwable -> handleFailure(throwable) }
            }
        }
    }

    private suspend fun saveMovie(entity: MovieFullEntity) {
        withContext(Dispatchers.IO) {
            useCase.saveMovie(entity)
                .onSuccess { setState { it.copy(loading = false, saved = true) } }
                .onFailure { throwable -> handleFailure(throwable) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }
}