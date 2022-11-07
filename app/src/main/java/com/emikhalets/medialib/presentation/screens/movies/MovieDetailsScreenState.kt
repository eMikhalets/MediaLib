package com.emikhalets.medialib.presentation.screens.movies

import com.emikhalets.medialib.data.entity.views.MovieEntity

data class MovieDetailsScreenState(
    val movie: MovieEntity? = null,
) {

    fun setMovie(movie: MovieEntity): MovieDetailsScreenState {
        return this.copy(movie = movie)
    }
}