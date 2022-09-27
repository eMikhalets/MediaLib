package com.emikhalets.medialib.data.network

import com.emikhalets.medialib.data.entity.tv_series.TvDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvSeriesApi {

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
    ): TvDetailsResponse
}