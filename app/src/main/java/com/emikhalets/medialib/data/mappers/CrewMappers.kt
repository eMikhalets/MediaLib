package com.emikhalets.medialib.data.mappers

import com.emikhalets.medialib.data.database.crew.CrewDbEntity
import com.emikhalets.medialib.domain.entities.crew.CrewEntity

object CrewMappers {

    fun mapDbEntityToEntity(dbEntity: CrewDbEntity): CrewEntity {
        return CrewEntity(
            name = dbEntity.name,
            type = dbEntity.type,
        )
    }

    fun mapEntityToDbEntity(entity: CrewEntity): CrewDbEntity {
        return CrewDbEntity(
            name = entity.name,
            type = entity.type,
        )
    }

    fun mapListToDbList(list: List<CrewEntity>): List<CrewDbEntity> {
        return list.map { mapEntityToDbEntity(it) }
    }
}