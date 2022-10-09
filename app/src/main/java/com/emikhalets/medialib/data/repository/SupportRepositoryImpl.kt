package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.database.GenresDao
import com.emikhalets.medialib.data.entity.database.GenreDB
import com.emikhalets.medialib.data.entity.support.GenresResponse
import com.emikhalets.medialib.data.network.SupportApi
import com.emikhalets.medialib.utils.GenreType
import com.emikhalets.medialib.utils.execute
import javax.inject.Inject

class SupportRepositoryImpl @Inject constructor(
    private val supportApi: SupportApi,
    private val genresDao: GenresDao,
) : SupportRepository {

    override suspend fun movieGenres(): Result<GenresResponse> {
        return execute { supportApi.getMovieGenres() }
    }

    override suspend fun tvGenres(): Result<GenresResponse> {
        return execute { supportApi.getTvGenres() }
    }

    override suspend fun getGenresLocal(type: GenreType): Result<List<GenreDB>> {
        return execute { genresDao.getAllByType(type) }
    }

    override suspend fun saveGenresLocal(genres: GenresResponse, type: GenreType): Result<Boolean> {
        return execute {
            val genreDB = genres.genres?.map { genre ->
                GenreDB(
                    genreId = genre.id ?: return@execute false,
                    name = genre.name ?: return@execute false,
                    type = type
                )
            }
            genresDao.insertAll(genreDB ?: return@execute false)
            true
        }
    }
}