package com.emikhalets.medialib.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverters {

    private val genresListType = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun genresToDb(list: List<String>): String {
        return Gson().toJson(list, genresListType)
    }

    @TypeConverter
    fun genresFromDb(string: String): List<String> {
        return Gson().fromJson(string, genresListType)
    }
}