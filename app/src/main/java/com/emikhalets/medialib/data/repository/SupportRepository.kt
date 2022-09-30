package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.support.GenresResponse

interface SupportRepository {

    suspend fun movieGenres(): Result<GenresResponse>

    suspend fun tvGenres(): Result<GenresResponse>
}