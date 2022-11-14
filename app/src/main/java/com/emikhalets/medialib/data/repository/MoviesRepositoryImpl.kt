package com.emikhalets.medialib.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emikhalets.medialib.data.database.MoviesDao
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.movies.MovieDetailsResponse
import com.emikhalets.medialib.data.entity.movies.MovieSearchResult
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.paging.MoviesRemotePagingSource
import com.emikhalets.medialib.utils.GenresHelper
import com.emikhalets.medialib.utils.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    private val moviesDao: MoviesDao,
) : MoviesRepository {

    override suspend fun search(query: String): Flow<PagingData<MovieSearchResult>> {
        val source = MoviesRemotePagingSource(query, moviesApi)
        return Pager(PagingConfig(20)) { source }.flow
    }

    override suspend fun details(id: Int): Result<MovieDetailsResponse> {
        return execute { moviesApi.getMovieDetails(id) }
    }

    override suspend fun saveLocal(movieRemote: MovieDetailsResponse): Result<Boolean> {
        val genresString = movieRemote.genres?.joinToString(", ")

        val mappedMovie = MovieDB(
            id = movieRemote.id ?: return Result.success(false),
            title = movieRemote.title ?: return Result.success(false),
            budget = movieRemote.budget ?: -1,
            backdrop = movieRemote.backdrop ?: "",
            genres = genresString ?: GenresHelper.NO_GENRES,
            imdbId = movieRemote.imdbId ?: "",
            originalTitle = "${movieRemote.originalTitle} + (${movieRemote.originalLanguage})",
            overview = movieRemote.overview ?: "",
            poster = movieRemote.poster ?: "",
            releaseDate = movieRemote.releaseDate ?: "",
            revenue = movieRemote.revenue ?: -1,
            runtime = movieRemote.runtime ?: 0,
            status = movieRemote.status ?: "",
            tagline = movieRemote.tagline ?: "",
            voteAverage = movieRemote.voteAverage ?: 0.0,
        )

        return execute {
            val result = moviesDao.insert(mappedMovie)
            result != null
        }
    }

    override suspend fun getMoviesLocal(): Result<Flow<List<MovieDB>>> {
        return execute { moviesDao.getAllOrderByDateDesc() }
    }

    override suspend fun getMoviesLocal(query: String): Result<Flow<List<MovieDB>>> {
        return execute { moviesDao.getAllOrderByDateDesc(query) }
    }

    override suspend fun getMovieLocal(id: Int): Result<Flow<MovieDB>> {
        return execute { moviesDao.getItem(id) }
    }

    override suspend fun updateMovieLocal(movieDB: MovieDB): Result<Int> {
        return execute { moviesDao.update(movieDB) }
    }

    override suspend fun deleteMovieLocal(movieDB: MovieDB): Result<Int> {
        return execute { moviesDao.delete(movieDB) }
    }

    override suspend fun isMovieSaved(id: Int): Result<Boolean> {
        return execute { moviesDao.isExist(id) }
    }

    override suspend fun addMovieLocal(movieDB: MovieDB): Result<Long> {
        return execute { moviesDao.insert(movieDB) ?: -1 }
    }
}