package com.emikhalets.medialib.domain.entities.crew

import com.emikhalets.medialib.domain.entities.ratings.CrewType

data class CrewEntity(
    val name: String,
    val type: CrewType,
)
