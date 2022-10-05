package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.medialib.data.entity.database.MovieDB

@Dao
interface MoviesDao {

    @Insert
    suspend fun insert(item: MovieDB): Long?

    @Insert
    suspend fun insertAll(items: List<MovieDB>): List<Long>

    @Update
    suspend fun update(item: MovieDB): Int

    @Update
    suspend fun updateAll(items: List<MovieDB>): Int

    @Delete
    suspend fun delete(item: MovieDB): Int

    @Query("DELETE FROM movies")
    suspend fun drop()

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieDB>

    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun getItem(id: Int): MovieDB

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id=:id)")
    suspend fun isExist(id: Int): Boolean
}