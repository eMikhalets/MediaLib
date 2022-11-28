package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.network.MovieResponse
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchMovieScreenState(
    val saved: Boolean = false,
    val error: String = "",
) {

    fun setSaved(): SearchMovieScreenState {
        return this.copy(saved = true)
    }

    fun setError(error: String): SearchMovieScreenState {
        return this.copy(error = error)
    }
}

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(SearchMovieScreenState())
        private set

    fun parseUrl(url: String) {
        viewModelScope.launch {
            try {
                val id = url.split("/")[4]
                searchMovie(id)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private suspend fun searchMovie(id: String) {
        repo.searchMovie(id)
            .onSuccess { saveMovie(it) }
            .onFailure { state = state.setError(it.message ?: "") }
    }

    private suspend fun saveMovie(response: MovieResponse) {
        val movieDB = MovieDB.fromMoviesResponse(response)
        repo.insertItem(movieDB)
            .onSuccess { state = state.setSaved() }
            .onFailure { state = state.setError(it.message ?: "") }
    }
}