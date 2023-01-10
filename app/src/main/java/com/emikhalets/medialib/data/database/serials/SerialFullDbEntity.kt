package com.emikhalets.medialib.data.database.serials

import androidx.room.Embedded
import androidx.room.Relation
import com.emikhalets.medialib.data.database.genres.GenreDbEntity

data class SerialFullDbEntity(

    @Embedded val serialDbEntity: SerialDbEntity,
    @Relation(parentColumn = "serial_id", entityColumn = "genre_id")
    val genres: List<GenreDbEntity>,
)