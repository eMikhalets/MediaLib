package com.emikhalets.network.api

import com.emikhalets.network.model.movies.MovieDetailsResponse
import com.emikhalets.network.model.movies.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsResponse

    // TODO: paging
    @GET("search/movie")
    suspend fun getSearchedMovies(
        @Query("query") query: String,
    ): MovieSearchResponse
}