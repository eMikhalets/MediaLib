package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.network.entities.MovieRemoteEntity
import com.emikhalets.medialib.data.network.entities.RatingsRemoteEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.genres.GenreType
import com.emikhalets.medialib.domain.entities.movies.MovieEntity
import com.emikhalets.medialib.domain.entities.movies.MovieFullEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity
import java.util.*

object MovieMappers {

    fun mapRemoteEntityToEntity(entity: MovieRemoteEntity): MovieFullEntity {
        return MovieFullEntity(
            movieEntity = MovieEntity(
                id = 0,
                title = entity.title ?: "",
                titleRu = "",
                overview = entity.plot ?: "",
                poster = entity.poster ?: "",
                year = entity.formatYear(),
                saveTimestamp = Calendar.getInstance().timeInMillis,
                lastUpdateTimestamp = Calendar.getInstance().timeInMillis,
                comment = "",
                rating = 0,
                watchStatus = MovieWatchStatus.NONE,
            ),
            genres = mapGenres(entity.genre),
            ratings = mapRatings(entity.ratings)
        )
    }

    private fun mapGenres(genres: String?): List<GenreEntity> {
        return try {
            val arr = genres?.split(", ") ?: return emptyList()
            arr.map { GenreEntity(it, GenreType.MOVIE) }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    private fun mapRatings(ratings: List<RatingsRemoteEntity>?): List<RatingEntity> {
        return try {
            ratings?.mapNotNull {
                val source = it.source ?: return@mapNotNull null
                val value = it.value ?: return@mapNotNull null
                RatingEntity(source, value)
            } ?: return emptyList()
        } catch (ex: Exception) {
            emptyList()
        }
    }

    fun mapDbEntityToEntity(entity: MovieDbEntity): MovieEntity {
        return MovieEntity(
            id = entity.id,
            title = entity.title,
            titleRu = entity.titleRu,
            overview = entity.overview,
            poster = entity.poster,
            year = entity.year,
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
            saveTimestamp = entity.movieEntity.saveTimestamp,
            lastUpdateTimestamp = entity.movieEntity.lastUpdateTimestamp,
            comment = entity.movieEntity.comment,
            rating = entity.movieEntity.rating,
            watchStatus = entity.movieEntity.watchStatus,
            genres = entity.genres,
            ratings = entity.ratings
        )
    }
}