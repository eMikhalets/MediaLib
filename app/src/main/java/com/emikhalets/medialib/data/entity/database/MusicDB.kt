package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.emikhalets.medialib.data.entity.views.ViewListItem
import java.util.*

@Entity(tableName = "musics")
data class MusicDB(
    @PrimaryKey @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "title") override val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "original_title") override val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") override val poster: String,
    @ColumnInfo(name = "release_date") override val releaseDate: Long,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "rating") override val rating: Int = 0,
    @ColumnInfo(name = "tags") val tags: String = "",
) : ViewListItem {

    @Ignore
    constructor(name: String, author: String, year: String, genre: String, comment: String) : this(
        id = 0,
        title = name,
        author = author,
        genre = genre,
        originalTitle = "",
        overview = "",
        poster = "",
        releaseDate = Calendar.getInstance()
            .apply { set(Calendar.YEAR, year.toInt()) }.timeInMillis,
        tagline = "",
        voteAverage = 0.0,
        comment = comment,
        rating = 0,
    )
}