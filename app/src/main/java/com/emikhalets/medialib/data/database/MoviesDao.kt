package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.medialib.data.entity.database.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao : BaseDao<MovieDB> {

    @Query("DELETE FROM movies")
    suspend fun drop()

    @Query("SELECT * FROM movies ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<MovieDB>>

    @Query("SELECT * FROM movies " +
            "WHERE title LIKE '%' || :query || '%' OR original_title LIKE '%' || :query || '%' " +
            "ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<MovieDB>>

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getItem(id: Int): Flow<MovieDB>
}