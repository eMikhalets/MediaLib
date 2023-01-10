package com.emikhalets.medialib.data.database.movies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert
    suspend fun insert(item: MovieDbEntity): Long

    @Update
    suspend fun update(item: MovieDbEntity): Int

    @Delete
    suspend fun delete(item: MovieDbEntity): Int

    @Query("SELECT * FROM movies_table ORDER BY last_update_timestamp DESC")
    fun getAllItemsFlowOrderByLastUpdate(): Flow<List<MovieDbEntity>>

    @Query("SELECT * FROM movies_table WHERE movie_id=:id LIMIT 1")
    fun getItemFlow(id: Int): Flow<MovieDbEntity>

    @Query("SELECT * FROM movies_table WHERE movie_id=:id LIMIT 1")
    suspend fun getItem(id: Int): MovieDbEntity
}