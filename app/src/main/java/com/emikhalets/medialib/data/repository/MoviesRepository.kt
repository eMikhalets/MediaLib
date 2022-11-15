package com.emikhalets.medialib.data.repository

import com.emikhalets.medialib.data.entity.database.MovieDB
import kotlinx.coroutines.flow.Flow

interface MoviesRepository{

    suspend fun insertItem(item: MovieDB): Result<Long>
    suspend fun updateItem(item: MovieDB): Result<Int>
    suspend fun deleteItem(item: MovieDB): Result<Int>
    suspend fun getItem(id: Int): Result<Flow<MovieDB>>
    suspend fun getItems(query: String = ""): Result<Flow<List<MovieDB>>>}