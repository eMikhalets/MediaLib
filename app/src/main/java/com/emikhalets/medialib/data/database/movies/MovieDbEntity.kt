package com.emikhalets.medialib.data.database.movies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.emikhalets.medialib.data.database.converters.CrewConverters
import com.emikhalets.medialib.data.database.converters.GenresConverters
import com.emikhalets.medialib.data.database.converters.RatingsConverters
import com.emikhalets.medialib.domain.entities.crew.CrewEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.emikhalets.medialib.domain.entities.movies.MovieWatchStatus
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity
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
    @ColumnInfo(name = "save_timestamp") val saveTimestamp: Long,
    @ColumnInfo(name = "last_update_timestamp") val lastUpdateTimestamp: Long,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "watch_status") val watchStatus: MovieWatchStatus,
    @TypeConverters(GenresConverters::class)
    @ColumnInfo(name = "genres") val genres: List<GenreEntity>,
    @TypeConverters(RatingsConverters::class)
    @ColumnInfo(name = "ratings", defaultValue = "") val ratings: List<RatingEntity>,
    @ColumnInfo(name = "runtime", defaultValue = "") val runtime: String,
    @TypeConverters(CrewConverters::class)
    @ColumnInfo(name = "crew", defaultValue = "") val crew: List<CrewEntity>,
    @ColumnInfo(name = "awards", defaultValue = "") val awards: String,
)