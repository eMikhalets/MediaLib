package com.emikhalets.medialib.presentation.screens.movies

import com.emikhalets.medialib.data.entity.database.MovieDB

data class MoviesScreenState(
    val movies: List<MovieDB> = emptyList(),
) {

    fun setMovies(movies: List<MovieDB>): MoviesScreenState {
        return this.copy(movies = movies)
    }
}