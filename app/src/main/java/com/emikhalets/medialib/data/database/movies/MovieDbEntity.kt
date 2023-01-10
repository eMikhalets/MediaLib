package com.emikhalets.medialib.data.database.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.emikhalets.medialib.data.database.GenresConverters
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import java.util.*

@Entity(tableName = "movies_table")
data class MovieDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id") val id: Long,
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
    @ColumnInfo(name = "watch_status") val watchStatus: MovieWatchStatus,
    @TypeConverters(GenresConverters::class)
    @ColumnInfo(name = "genres") val genres: List<String>,
)