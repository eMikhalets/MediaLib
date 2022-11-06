//package com.emikhalets.medialib.presentation.screens.searched_movie
//
//import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
//
//data class SearchedMovieScreenState(
//    val movie: MovieDetailsResponse? = null,
//    val isAlreadySaved: Boolean = true,
//    val savedMovie: Boolean = false,
//) {
//
//    fun setMovie(movie: MovieDetailsResponse): SearchedMovieScreenState {
//        return this.copy(movie = movie)
//    }
//
//    fun setNotYetSaved(): SearchedMovieScreenState {
//        return this.copy(isAlreadySaved = false)
//    }
//
//    fun setSavedMovie(): SearchedMovieScreenState {
//        return this.copy(savedMovie = true, isAlreadySaved = true)
//    }
//}