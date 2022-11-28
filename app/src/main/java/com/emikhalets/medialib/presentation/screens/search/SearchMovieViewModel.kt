package com.emikhalets.medialib.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchMovieScreenState(
    val imdbId: String = "",
) {

    fun setImdbId(id: String): SearchMovieScreenState {
        return this.copy(imdbId = id)
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
                val array = url.split("/")
                state = state.setImdbId(array[4])
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}