package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.database.MusicDB
import kotlinx.coroutines.flow.Flow

interface MusicsRepository {

    suspend fun insertItem(item: MusicDB): Result<Long>
    suspend fun updateItem(item: MusicDB): Result<Int>
    suspend fun deleteItem(item: MusicDB): Result<Int>
    suspend fun getItem(id: Int): Result<Flow<MusicDB?>>
    suspend fun getItems(query: String = ""): Result<Flow<List<MusicDB>>>
}