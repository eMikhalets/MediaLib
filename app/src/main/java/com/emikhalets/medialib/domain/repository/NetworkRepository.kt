package com.emikhalets.medialib.domain.repository

import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.serials.SerialEntity

interface NetworkRepository {

    suspend fun searchMovie(id: String): Result<MovieEntity>

    suspend fun searchSerial(id: String): Result<SerialEntity>
}