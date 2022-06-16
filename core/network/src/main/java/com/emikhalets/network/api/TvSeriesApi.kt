package com.emikhalets.network.api

import com.emikhalets.network.model.tv_series.TvDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TvSeriesApi {

    @GET("tv/{tv_id}")
    suspend fun getTvDetails(
        @Path("tv_id") tvId: Int,
    ): TvDetailsResponse
}