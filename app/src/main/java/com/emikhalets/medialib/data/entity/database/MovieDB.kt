package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.medialib.data.entity.views.ViewListItem
import java.util.*

@Entity(tableName = "movies")
data class MovieDB(
    @PrimaryKey @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "budget") val budget: Long,
    @ColumnInfo(name = "backdrop_path") val backdrop: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    @ColumnInfo(name = "original_title") override val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") override val poster: String,
    @ColumnInfo(name = "release_date") override val releaseDate: Long,
    @ColumnInfo(name = "revenue") val revenue: Long,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "rating") override val rating: Int = 0,
    @ColumnInfo(name = "tags") val tags: String = "",
) : ViewListItem {

    @Ignore
    constructor(name: String, year: String, comment: String) : this(
        id = 0,
        title = name,
        budget = 0,
        backdrop = "",
        genres = "",
        imdbId = "",
        originalTitle = "",
        overview = "",
        poster = "",
        releaseDate = Calendar.getInstance()
            .apply { set(Calendar.YEAR, year.toInt()) }.timeInMillis,
        revenue = 0,
        runtime = 0,
        status = "",
        tagline = "",
        voteAverage = 0.0,
        comment = comment,
        rating = 0,
    )
}