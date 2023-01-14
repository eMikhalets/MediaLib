package com.emikhalets.medialib.presentation.screens.searching

import com.emikhalets.medialib.R
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
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
                if (id[0] == 't' && id[1] == 't') {
                    setState { it.copy(imdbId = id) }
                } else {
                    throw RuntimeException("No valid imdb id")
                }
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

    fun searchAndSaveSerial() {
        launchIO {
            setState { it.copy(loading = true) }
            val imdbId = currentState.imdbId
            if (imdbId == null) {
                val message = R.string.error_parsing_imdb_id
                setState { it.copy(loading = false, error = UiString.create(message)) }
            } else {
                useCase.searchSerial(imdbId)
                    .onSuccess { entity -> saveSerial(entity) }
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

    private suspend fun saveSerial(entity: SerialFullEntity) {
        withContext(Dispatchers.IO) {
            useCase.saveSerial(entity)
                .onSuccess { setState { it.copy(loading = false, saved = true) } }
                .onFailure { throwable -> handleFailure(throwable) }
        }
    }

    private fun handleFailure(throwable: Throwable) {
        setState { it.copy(loading = false, error = UiString.create(throwable.message)) }
    }
}