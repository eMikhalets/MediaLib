package com.emikhalets.medialib.data.database.serials

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.emikhalets.medialib.data.database.GenresConverters
import com.emikhalets.medialib.domain.entities.serials.SerialFullEntity
import com.emikhalets.medialib.domain.entities.serials.SerialWatchStatus
import java.util.*

@Entity(tableName = "serials_table")
data class SerialDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "serial_id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "title_ru") val titleRu: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "imdb_rating") val imdbRating: Double,
    @ColumnInfo(name = "save_timestamp") val saveTimestamp: Long,
    @ColumnInfo(name = "last_update_timestamp") val lastUpdateTimestamp: Long,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "watch_status") val watchStatus: SerialWatchStatus,
    @TypeConverters(GenresConverters::class)
    @ColumnInfo(name = "genres") val genres: List<Int>,
) {

    @Ignore
    constructor(
        title: String,
        titleRu: String,
        overview: String,
        poster: String,
        year: Int,
        imdbRating: Double,
        comment: String,
        rating: Int,
        watchStatus: SerialWatchStatus,
        genres: List<Int>,
    ) : this(
        id = 0,
        title = title,
        titleRu = titleRu,
        overview = overview,
        poster = poster,
        year = year,
        imdbRating = imdbRating,
        saveTimestamp = Date().time,
        lastUpdateTimestamp = Date().time,
        comment = comment,
        rating = rating,
        watchStatus = watchStatus,
        genres = genres
    )

    @Ignore
    constructor(
        entity: SerialFullEntity,
    ) : this(
        id = entity.serialEntity.id,
        title = entity.serialEntity.title,
        titleRu = entity.serialEntity.titleRu,
        overview = entity.serialEntity.overview,
        poster = entity.serialEntity.poster,
        year = entity.serialEntity.year,
        imdbRating = entity.serialEntity.imdbRating,
        saveTimestamp = entity.serialEntity.saveTimestamp,
        lastUpdateTimestamp = Date().time,
        comment = entity.serialEntity.comment,
        rating = entity.serialEntity.rating,
        watchStatus = entity.serialEntity.watchStatus,
        genres = entity.genres.map { it.id }
    )
}