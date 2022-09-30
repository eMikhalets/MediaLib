package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.support.GenresResponse
import com.emikhalets.medialib.data.network.SupportApi
import com.emikhalets.medialib.utils.execute
import javax.inject.Inject

class SupportRepositoryImpl @Inject constructor(
    private val supportApi: SupportApi,
) : SupportRepository {

    override suspend fun movieGenres(): Result<GenresResponse> {
        return execute { supportApi.getMovieGenres() }
    }

    override suspend fun tvGenres(): Result<GenresResponse> {
        return execute { supportApi.getTvGenres() }
    }
}