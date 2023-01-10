package com.emikhalets.medialib.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emikhalets.medialib.data.database.genres.GenreDbEntity
import com.emikhalets.medialib.data.database.genres.GenresDao
import com.emikhalets.medialib.data.database.movies.MovieDbEntity
import com.emikhalets.medialib.data.database.movies.MoviesDao
import com.emikhalets.medialib.data.database.serials.SerialDbEntity
import com.emikhalets.medialib.data.database.serials.SerialsDao

@Database(
    entities = [
        MovieDbEntity::class,
        SerialDbEntity::class,
        GenreDbEntity::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val serialsDao: SerialsDao
    abstract val genresDao: GenresDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "MediaLib.db").build()
    }
}