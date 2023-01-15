package com.emikhalets.medialib.data.database.crew

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CrewDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CrewDbEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<CrewDbEntity>): List<Long>

    @Query("SELECT * FROM crew_table WHERE name = :name LIMIT 1")
    suspend fun getItem(name: String): CrewDbEntity
}