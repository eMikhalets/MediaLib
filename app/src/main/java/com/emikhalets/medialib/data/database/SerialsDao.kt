package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Query
import com.emikhalets.medialib.data.entity.database.SerialDB
import kotlinx.coroutines.flow.Flow

@Dao
interface SerialsDao : BaseDao<SerialDB> {

    @Query("DELETE FROM serials")
    suspend fun drop()

    @Query("SELECT * FROM serials ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<SerialDB>>

    @Query("SELECT * FROM serials " +
            "WHERE title LIKE '%' || :query || '%' OR original_title LIKE '%' || :query || '%' " +
            "ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<SerialDB>>

    @Query("SELECT * FROM serials WHERE id=:id")
    fun getItem(id: Int): Flow<SerialDB>
}