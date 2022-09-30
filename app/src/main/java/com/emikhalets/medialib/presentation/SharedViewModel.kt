package com.emikhalets.medialib.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.repository.SupportRepository
import com.emikhalets.medialib.utils.GenresHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: SupportRepository,
    private val genresHelper: GenresHelper,
) : ViewModel() {

    fun getGenres() {
        viewModelScope.launch {
            launch {
                repo.movieGenres().onSuccess { genresHelper.setMoviesGenres(it.genres) }
            }
            launch {
                repo.tvGenres().onSuccess { genresHelper.setTvGenres(it.genres) }
            }
        }
    }
}