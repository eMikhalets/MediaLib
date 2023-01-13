package com.emikhalets.medialib.presentation.screens.movies.list

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.utils.State
import com.emikhalets.medialib.utils.UiString

data class MoviesState(
    val movies: List<MovieFullEntity> = emptyList(),
    val showedMovies: List<MovieFullEntity> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : State