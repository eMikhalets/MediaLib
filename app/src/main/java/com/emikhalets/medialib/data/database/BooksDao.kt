package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.medialib.data.entity.database.BookDB
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao : BaseDao<BookDB> {

    @Query("DELETE FROM books")
    suspend fun drop()

    @Query("SELECT * FROM books ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<BookDB>>

    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :query || '%' OR original_title LIKE '%' || :query || '%' " +
            "ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<BookDB>>

    @Query("SELECT * FROM books WHERE id=:id")
    fun getItem(id: Int): Flow<BookDB>
}