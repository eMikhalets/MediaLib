package com.emikhalets.medialib.domain.use_case.searching

import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import com.emikhalets.medialib.domain.repository.NetworkRepository

class SearchImdbUseCase(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) {

    suspend fun saveMovie(entity: MovieFullEntity): Result<Unit> {
        return databaseRepository.insertMovie(entity)
    }

    suspend fun saveSerial(entity: SerialFullEntity): Result<Unit> {
        return databaseRepository.insertSerial(entity)
    }

    suspend fun searchMovie(id: String): Result<MovieFullEntity> {
        return networkRepository.searchMovie(id)
    }

    suspend fun searchSerial(id: String): Result<SerialFullEntity> {
        return networkRepository.searchSerial(id)
    }
}