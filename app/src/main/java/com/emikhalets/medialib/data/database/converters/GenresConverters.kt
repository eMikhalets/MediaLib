package com.emikhalets.medialib.data.database.converters

import androidx.room.TypeConverter
import com.emikhalets.medialib.domain.entities.genres.GenreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverters {

    private val genresListType = object : TypeToken<List<GenreEntity>>() {}.type

    @TypeConverter
    fun genresToDb(list: List<GenreEntity>): String {
        return Gson().toJson(list, genresListType)
    }

    @TypeConverter
    fun genresFromDb(string: String): List<GenreEntity> {
        return Gson().fromJson(string, genresListType)
    }
}