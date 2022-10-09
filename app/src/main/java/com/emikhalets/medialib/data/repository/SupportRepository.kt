package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.database.GenreDB
import com.emikhalets.medialib.data.entity.support.GenresResponse
import com.emikhalets.medialib.utils.GenreType

interface SupportRepository {

    suspend fun movieGenres(): Result<GenresResponse>

    suspend fun tvGenres(): Result<GenresResponse>

    suspend fun getGenresLocal(type: GenreType): Result<List<GenreDB>>

    suspend fun saveGenresLocal(genres: GenresResponse, type: GenreType): Result<Boolean>
}