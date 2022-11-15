package com.emikhalets.medialib.data.entity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "books")
data class BookDB(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "poster_path") val poster: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "save_date") val saveDate: Long = Date().time,
    @ColumnInfo(name = "comment") val comment: String = "",
    @ColumnInfo(name = "rating") val rating: Int = 0,
    @ColumnInfo(name = "tags") val tags: String = "",
) {

    @Ignore
    constructor(name: String, author: String, year: String, comment: String) : this(
        id = 0,
        title = name,
        author = author,
        genres = "",
        originalTitle = "",
        overview = "",
        poster = "",
        releaseDate = year,
        tagline = "",
        voteAverage = 0.0,
        comment = comment,
        rating = 0,
    )
}