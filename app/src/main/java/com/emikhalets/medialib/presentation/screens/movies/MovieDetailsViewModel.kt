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

data class MovieDetailsScreenState(
    val movie: MovieDB? = null,
    val deleted: Boolean = false,
) {

    fun setMovie(movie: MovieDB): MovieDetailsScreenState {
        return this.copy(movie = movie)
    }

    fun setDeleted(): MovieDetailsScreenState {
        return this.copy(deleted = true)
    }
}

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(MovieDetailsScreenState())
        private set

    private var movieDb: MovieDB? = null

    fun getSavedMovie(id: Int?) {
        id ?: return
        viewModelScope.launch {
            val moviesResponse = repo.getItem(id)
            moviesResponse.onSuccess { flow ->
                flow.collectLatest { movie ->
                    movie?.let { state = state.setMovie(movie) }
                    movieDb = movie
                }
            }
        }
    }

    fun updateMovie(rating: Int) {
        val movie = movieDb?.copy(rating = rating) ?: return
        viewModelScope.launch {
            repo.updateItem(movie)
        }
    }

    fun updateMovie(poster: String) {
        if (poster != movieDb?.poster) {
            val movie = movieDb?.copy(poster = poster) ?: return
            viewModelScope.launch {
                repo.updateItem(movie)
            }
        }
    }

    fun deleteMovie() {
        val movie = movieDb ?: return
        viewModelScope.launch {
            repo.deleteItem(movie).onSuccess { state = state.setDeleted() }
        }
    }
}