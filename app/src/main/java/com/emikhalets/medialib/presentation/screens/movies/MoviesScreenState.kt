package com.emikhalets.medialib.presentation.screens.movies

import com.emikhalets.medialib.data.entity.views.MovieEntity

data class MoviesScreenState(
    val movies: List<MovieEntity> = emptyList(),
) {

    fun setMovies(movies: List<MovieEntity>): MoviesScreenState {
        return this.copy(movies = movies)
    }
}