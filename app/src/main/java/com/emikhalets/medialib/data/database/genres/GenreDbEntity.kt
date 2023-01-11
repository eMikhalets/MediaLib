package com.emikhalets.medialib.data.database.genres

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.medialib.domain.entities.genres.GenreType

@Entity(tableName = "genres_table")
data class GenreDbEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: GenreType,
)