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

    fun mapEntityToDbEntity(entity: GenreEntity): GenreDbEntity {
        return GenreDbEntity(
            name = entity.name,
            type = entity.type,
        )
    }

    fun mapListToDbList(list: List<GenreEntity>): List<GenreDbEntity> {
        return list.map { mapEntityToDbEntity(it) }
    }
}