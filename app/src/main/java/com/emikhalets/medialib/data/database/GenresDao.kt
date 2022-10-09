package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.medialib.data.entity.database.GenreDB
import com.emikhalets.medialib.utils.GenreType

@Dao
interface GenresDao {

    @Insert
    suspend fun insertAll(items: List<GenreDB>): List<Long>

    @Update
    suspend fun updateAll(items: List<GenreDB>): Int

    @Query("DELETE FROM genres")
    suspend fun drop()

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<GenreDB>

    @Query("SELECT * FROM genres WHERE type=:type")
    suspend fun getAllByType(type: GenreType): List<GenreDB>
}