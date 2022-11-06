package com.emikhalets.medialib.data.entity.views

import com.emikhalets.medialib.data.entity.database.MovieDB
import java.util.*

data class MovieEntity(
    override val id: Int,
    val title: String,
    val budget: Long,
    val backdrop: String,
    val genres: String,
    val imdbId: String,
    val originalTitle: String,
    val overview: String,
    val poster: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val voteAverage: Double,
    val saveDate: Long = Date().time,
    val comment: String = "",
) : ViewListItem {

    constructor(movieDB: MovieDB) : this(
        id = movieDB.id,
        title = movieDB.title,
        budget = movieDB.budget,
        backdrop = movieDB.backdrop,
        genres = movieDB.genres,
        imdbId = movieDB.imdbId,
        originalTitle = movieDB.originalTitle,
        overview = movieDB.overview,
        poster = movieDB.poster,
        releaseDate = movieDB.releaseDate,
        revenue = movieDB.revenue,
        runtime = movieDB.runtime,
        status = movieDB.status,
        tagline = movieDB.tagline,
        voteAverage = movieDB.voteAverage,
        saveDate = movieDB.saveDate,
        comment = movieDB.comment,
    )
}