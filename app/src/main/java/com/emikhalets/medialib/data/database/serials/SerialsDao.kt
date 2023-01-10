package com.emikhalets.medialib.data.database.serials

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SerialsDao {

    @Insert
    suspend fun insert(item: SerialDbEntity): Long

    @Update
    suspend fun update(item: SerialDbEntity): Int

    @Delete
    suspend fun delete(item: SerialDbEntity): Int

    @Query("SELECT * FROM serials_table ORDER BY last_update_timestamp DESC")
    fun getAllItemsFlowOrderByLastUpdate(): Flow<List<SerialDbEntity>>

    @Query("SELECT * FROM serials_table WHERE serial_id=:id LIMIT 1")
    fun getItemFlow(id: Int): Flow<SerialDbEntity>

    @Query("SELECT * FROM serials_table WHERE serial_id=:id LIMIT 1")
    suspend fun getItem(id: Int): SerialDbEntity
}