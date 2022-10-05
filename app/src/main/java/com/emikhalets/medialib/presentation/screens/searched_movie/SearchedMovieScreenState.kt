package com.emikhalets.medialib.presentation.screens.searched_movie

import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse

data class SearchedMovieScreenState(
    val movie: MovieDetailsResponse? = null,
    val isAlreadySaved: Boolean = false,
    val savedMovie: Boolean = false,
) {

    fun setMovie(movie: MovieDetailsResponse): SearchedMovieScreenState {
        return this.copy(movie = movie)
    }

    fun setAlreadySaved(): SearchedMovieScreenState {
        return this.copy(isAlreadySaved = true)
    }

    fun setSavedMovie(): SearchedMovieScreenState {
        return this.copy(savedMovie = true, isAlreadySaved = true)
    }
}