package com.emikhalets.medialib.presentation.screens.search

import androidx.lifecycle.ViewModel
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.network.MovieResponse
import com.emikhalets.medialib.data.repository.MoviesRepository
import com.emikhalets.medialib.utils.enums.SearchType
import com.emikhalets.medialib.utils.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchWebviewViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    fun parseUrl(url: String, type: SearchType) {
        launchDefault {
            try {
                val id = url.split("/")[4]
                when (type) {
                    SearchType.MOVIE -> searchMovie(id)
                    SearchType.SERIAL -> searchMovie(id)
                    SearchType.BOOK -> Unit
                    SearchType.MUSIC -> Unit
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private suspend fun searchMovie(id: String) {
        repo.searchMovie(id)
            .onSuccess { saveMovie(it) }
            .onFailure { ex -> _state.update { it.setError(ex.message ?: "") } }
    }

    private suspend fun saveMovie(response: MovieResponse) {
        val movieDB = MovieDB.fromMoviesResponse(response)
        repo.insertItem(movieDB)
            .onSuccess { _state.update { it.setSaved() } }
            .onFailure { ex -> _state.update { it.setError(ex.message ?: "") } }
    }

    data class State(
        val saved: Boolean = false,
        val error: String = "",
        val errorCounter: Int = 0,
    ) {

        fun setSaved(): State {
            return this.copy(saved = true)
        }

        fun setError(error: String): State {
            return this.copy(error = error, errorCounter = errorCounter + 1)
        }
    }
}