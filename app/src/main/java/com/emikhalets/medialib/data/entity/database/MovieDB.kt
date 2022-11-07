package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "movies")
data class MovieDB(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "budget") val budget: Long,
    @ColumnInfo(name = "backdrop_path") val backdrop: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val poster: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "revenue") val revenue: Long,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "rating") val rating: Int = 0,
)