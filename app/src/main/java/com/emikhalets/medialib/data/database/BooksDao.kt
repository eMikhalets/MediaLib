package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.medialib.data.entity.database.BookDB
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {

    @Insert
    suspend fun insert(item: BookDB): Long

    @Update
    suspend fun update(item: BookDB): Int

    @Delete
    suspend fun delete(item: BookDB): Int

    @Query("DELETE FROM books")
    suspend fun drop()

    @Query("SELECT * FROM books ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<BookDB>>

    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :query || '%' OR title_ru LIKE '%' || :query || '%' " +
            "ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<BookDB>>

    @Query("SELECT * FROM books WHERE id=:id")
    fun getItem(id: Int): Flow<BookDB?>
}