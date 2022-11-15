package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.database.SerialDB
import kotlinx.coroutines.flow.Flow

interface SerialsRepository {

    suspend fun insertItem(item: SerialDB): Result<Long>
    suspend fun updateItem(item: SerialDB): Result<Int>
    suspend fun deleteItem(item: SerialDB): Result<Int>
    suspend fun getItem(id: Int): Result<Flow<SerialDB>>
    suspend fun getItems(query: String = ""): Result<Flow<List<SerialDB>>>
}