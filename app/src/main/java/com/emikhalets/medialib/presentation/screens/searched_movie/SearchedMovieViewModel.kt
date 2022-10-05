package com.emikhalets.medialib.presentation.screens.searched_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchedMovieViewModel @Inject constructor(
    private val repo: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(SearchedMovieScreenState())
        private set

    fun getMovie(id: Int) {
        viewModelScope.launch {
            repo.details(id).onSuccess { state = state.setMovie(it) }
        }
    }

    fun isAlreadySaved(id: Int) {
        viewModelScope.launch {
            repo.isMovieSaved(id).onSuccess { state = state.setAlreadySaved() }
        }
    }

    fun saveMovie() {
        state.movie?.let { movie ->
            viewModelScope.launch {
                repo.saveLocal(movie).onSuccess { state = state.setSavedMovie() }
            }
        }
    }
}