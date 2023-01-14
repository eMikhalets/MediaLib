package com.emikhalets.medialib.data.database.crew

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.medialib.domain.entities.ratings.CrewType

@Entity(tableName = "crew_table")
data class CrewDbEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: CrewType,
)