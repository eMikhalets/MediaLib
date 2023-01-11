package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.network.MovieRemoteEntity
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.utils.toDoubleSafe
import com.emikhalets.medialib.utils.toSafeInt

object MovieMappers {

    fun mapRemoteEntityToEntity(entity: MovieRemoteEntity): MovieFullEntity {
        return MovieFullEntity(
            movieEntity = MovieEntity(
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
            ),
            genres = emptyList()
        )
    }

    fun mapDbEntityToEntity(entity: MovieDbEntity): MovieEntity {
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

    fun mapEntityToDbEntity(entity: MovieFullEntity): MovieDbEntity {
        return MovieDbEntity(
            id = entity.movieEntity.id,
            title = entity.movieEntity.title,
            titleRu = entity.movieEntity.titleRu,
            overview = entity.movieEntity.overview,
            poster = entity.movieEntity.poster,
            year = entity.movieEntity.year,
            imdbRating = entity.movieEntity.imdbRating,
            saveTimestamp = entity.movieEntity.saveTimestamp,
            lastUpdateTimestamp = entity.movieEntity.lastUpdateTimestamp,
            comment = entity.movieEntity.comment,
            rating = entity.movieEntity.rating,
            watchStatus = entity.movieEntity.watchStatus,
            genres = entity.genres.map { it.name }
        )
    }
}