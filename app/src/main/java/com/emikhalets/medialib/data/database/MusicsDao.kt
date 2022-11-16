package com.emikhalets.medialib.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emikhalets.medialib.data.entity.database.MusicDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicsDao {

    @Insert
    suspend fun insert(item: MusicDB): Long

    @Update
    suspend fun update(item: MusicDB): Int

    @Delete
    suspend fun delete(item: MusicDB): Int

    @Query("DELETE FROM musics")
    suspend fun drop()

    @Query("SELECT * FROM musics ORDER BY save_date DESC")
    fun getAllItemsFlow(): Flow<List<MusicDB>>

    @Query("SELECT * FROM musics WHERE title LIKE '%' || :query || '%' ORDER BY save_date DESC")
    fun searchByTitle(query: String): Flow<List<MusicDB>>

    @Query("SELECT * FROM musics WHERE id=:id")
    fun getItem(id: Int): Flow<MusicDB>
}