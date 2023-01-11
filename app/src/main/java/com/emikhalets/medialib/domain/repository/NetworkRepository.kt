package com.emikhalets.medialib.domain.repository

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity

interface NetworkRepository {

    suspend fun searchMovie(id: String): Result<MovieFullEntity>

    suspend fun searchSerial(id: String): Result<SerialFullEntity>
}