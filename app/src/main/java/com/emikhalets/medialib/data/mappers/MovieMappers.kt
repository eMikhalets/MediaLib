package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.network.MovieRemoteEntity
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.utils.toDoubleSafe
import com.emikhalets.medialib.utils.toSafeInt

object MovieMappers {

    fun mapMovieRemoteToMovie(entity: MovieRemoteEntity): MovieEntity {
        return MovieEntity(
            id = 0,
            title = entity.title ?: "",
            titleRu = "",
            overview = "",
            poster = entity.poster ?: "",
            year = entity.year.toSafeInt(),
            imdbRating = entity.rating.toDoubleSafe(),
            saveTimestamp = 0,
            lastUpdateTimestamp = 0,
            comment = "",
            rating = 0,
            watchStatus = MovieWatchStatus.NONE,
        )
    }

    fun mapMovieDbToMovie(entity: MovieDbEntity): MovieEntity {
        return MovieEntity(
            id = entity.id,
            title = entity.title,
            titleRu = entity.titleRu,
            overview = entity.overview,
            poster = entity.poster,
            year = entity.year,
            imdbRating = entity.imdbRating,
            saveTimestamp = entity.saveTimestamp,
            lastUpdateTimestamp = entity.lastUpdateTimestamp,
            comment = entity.comment,
            rating = entity.rating,
            watchStatus = entity.watchStatus,
        )
    }

    fun mapMovieDbToMovieList(list: List<MovieDbEntity>): List<MovieEntity> {
        return list.map { mapMovieDbToMovie(it) }
    }
}