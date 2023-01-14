package com.emikhalets.medialib.data.database.converters

import androidx.room.TypeConverter
import com.emikhalets.medialib.domain.entities.ratings.RatingEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingsConverters {

    private val ratingsListType = object : TypeToken<List<RatingEntity>>() {}.type

    @TypeConverter
    fun ratingsToDb(list: List<RatingEntity>): String {
        return Gson().toJson(list, ratingsListType)
    }

    @TypeConverter
    fun ratingsFromDb(string: String): List<RatingEntity> {
        return Gson().fromJson(string, ratingsListType)
    }
}