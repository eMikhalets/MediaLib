package com.emikhalets.medialib.data.network

import com.emikhalets.medialib.data.entity.support.GenresResponse
import retrofit2.http.GET

interface SupportApi {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): GenresResponse

    @GET("genre/tv/list")
    suspend fun getTvGenres(): GenresResponse
}