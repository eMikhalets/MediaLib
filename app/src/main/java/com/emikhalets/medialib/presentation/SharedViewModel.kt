package com.emikhalets.medialib.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.medialib.data.repository.SupportRepository
import com.emikhalets.medialib.utils.AppPrefs
import com.emikhalets.medialib.utils.GenreType
import com.emikhalets.medialib.utils.GenresHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repo: SupportRepository,
    private val prefs: AppPrefs,
    private val genresHelper: GenresHelper,
) : ViewModel() {

    fun getGenres() {
        viewModelScope.launch {
            if (prefs.moviesGenresSaved) {
                repo.getGenresLocal(GenreType.MOVIE).onSuccess { genresHelper.setMoviesGenres(it) }
            } else {
                launch {
                    repo.movieGenres().onSuccess {
                        repo.saveGenresLocal(it, GenreType.MOVIE)
                        prefs.moviesGenresSaved = true
                    }
                }
            }

            if (prefs.tvGenresSaved) {
                repo.getGenresLocal(GenreType.TV).onSuccess { genresHelper.setTvGenres(it) }
            } else {
                launch {
                    repo.tvGenres().onSuccess {
                        repo.saveGenresLocal(it, GenreType.TV)
                        prefs.tvGenresSaved = true
                    }
                }
            }
        }
    }
}