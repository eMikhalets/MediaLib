//package com.emikhalets.medialib.data.network
//
//import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
//import com.emikhalets.medialib.data.entity.movies.MovieSearchResponse
//import retrofit2.http.GET
//import retrofit2.http.Path
//import retrofit2.http.Query
//
//interface MoviesApi {
//
//    @GET("movie/{movie_id}")
//    suspend fun getMovieDetails(
//        @Path("movie_id") movieId: Int,
//    ): MovieDetailsResponse
//
//    @GET("search/movie")
//    suspend fun getSearchedMovies(
//        @Query("query") query: String,
//        @Query("page") page: Int,
//        @Query("include_adult") adult: Boolean = true,
//    ): MovieSearchResponse
//}