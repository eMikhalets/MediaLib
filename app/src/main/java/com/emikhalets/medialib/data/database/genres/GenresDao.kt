package com.emikhalets.medialib.data.database.genres

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenresDao {

    @Insert
    suspend fun insert(item: GenreDbEntity): Long

    @Query("SELECT * FROM genres_table WHERE name = :name LIMIT 1")
    suspend fun getItem(name: String): GenreDbEntity
}