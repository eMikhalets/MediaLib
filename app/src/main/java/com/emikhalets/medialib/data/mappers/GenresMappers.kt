package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.genres.GenreDbEntity
import com.emikhalets.medialib.domain.entities.genres.GenreEntity

object GenresMappers {

    fun mapDbEntityToEntity(dbEntity: GenreDbEntity): GenreEntity {
        return GenreEntity(
            name = dbEntity.name,
            type = dbEntity.type,
        )
    }
}