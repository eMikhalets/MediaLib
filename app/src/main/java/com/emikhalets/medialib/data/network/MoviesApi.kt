package com.emikhalets.medialib.data.network

import com.emikhalets.medialib.data.network.entities.MovieRemoteEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET(RetrofitFactory.MOVIES_BASE_URL)
    suspend fun getMovieDetails(
        @Query("i") id: String,
        @Query("plot") plot: String = "full",
    ): MovieRemoteEntity
}