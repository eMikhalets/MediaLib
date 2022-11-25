package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.medialib.data.entity.database.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert
    suspend fun insert(item: MovieDB): Long

    @Update
    suspend fun update(item: MovieDB): Int

    @Delete
    suspend fun delete(item: MovieDB): Int

    @Query("DELETE FROM movies")
    suspend fun drop()

    @Query("SELECT * FROM movies ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<MovieDB>>

    @Query("SELECT * FROM movies " +
            "WHERE title LIKE '%' || :query || '%' OR title_ru LIKE '%' || :query || '%' " +
            "ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<MovieDB>>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getItem(id: Int): Flow<MovieDB?>
}