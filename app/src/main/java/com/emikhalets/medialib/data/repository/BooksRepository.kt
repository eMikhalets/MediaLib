package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.database.BookDB
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    suspend fun insertItem(item: BookDB): Result<Long>
    suspend fun updateItem(item: BookDB): Result<Int>
    suspend fun deleteItem(item: BookDB): Result<Int>
    suspend fun getItem(id: Int): Result<Flow<BookDB>>
    suspend fun getItems(query: String = ""): Result<Flow<List<BookDB>>>
}