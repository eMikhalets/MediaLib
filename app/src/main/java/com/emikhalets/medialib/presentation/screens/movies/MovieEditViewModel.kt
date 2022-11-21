package com.emikhalets.medialib.presentation.screens.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieEditScreenState(
    val movie: MovieDB? = null,
    val saved: Boolean = false,
) {

    fun setMovie(movie: MovieDB): MovieEditScreenState {
        return this.copy(movie = movie)
    }

    fun setSaved(): MovieEditScreenState {
        return this.copy(saved = true)
    }
}

@HiltViewModel
class MovieEditViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(MovieEditScreenState())
        private set


    fun getMovie(id: Int?) {
        id ?: return
        viewModelScope.launch {
            val moviesResponse = repo.getItem(id)
            moviesResponse.onSuccess { flow ->
                flow.collectLatest { movie -> state = state.setMovie(movie) }
            }
        }
    }

    fun saveMovie(movie: MovieDB) {
        viewModelScope.launch {
            if (movie.id == 0) {
                repo.insertItem(movie).onSuccess { state = state.setSaved() }
            } else {
                repo.updateItem(movie).onSuccess { state = state.setSaved() }
            }
        }
    }
}