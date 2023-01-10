package com.emikhalets.medialib.data.database.movies

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.medialib.data.database.genres.GenreDbEntity

data class MovieFullDbEntity(

    @Embedded val movieDbEntity: MovieDbEntity,
    @Relation(parentColumn = "movie_id", entityColumn = "genre_id")
    val genres: List<GenreDbEntity>,
)