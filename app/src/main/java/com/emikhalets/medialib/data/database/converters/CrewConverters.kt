package com.emikhalets.medialib.data.database.converters

import androidx.room.TypeConverter
import com.emikhalets.medialib.domain.entities.crew.CrewEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CrewConverters {

    private val ratingsListType = object : TypeToken<List<CrewEntity>>() {}.type

    @TypeConverter
    fun crewToDb(list: List<CrewEntity>): String {
        return Gson().toJson(list, ratingsListType)
    }

    @TypeConverter
    fun crewFromDb(string: String): List<CrewEntity> {
        return Gson().fromJson(string, ratingsListType)
    }
}