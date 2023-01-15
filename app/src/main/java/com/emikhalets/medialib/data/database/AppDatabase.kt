package com.emikhalets.medialib.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.emikhalets.medialib.data.database.converters.CrewConverters
import com.emikhalets.medialib.data.database.converters.GenresConverters
import com.emikhalets.medialib.data.database.converters.RatingsConverters
import com.emikhalets.medialib.data.database.crew.CrewDao
import com.emikhalets.medialib.data.database.crew.CrewDbEntity
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
        CrewDbEntity::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = MigrationFrom1To2::class)
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(GenresConverters::class, RatingsConverters::class, CrewConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val serialsDao: SerialsDao
    abstract val genresDao: GenresDao
    abstract val crewDao: CrewDao

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

@DeleteColumn(tableName = "movies_table", columnName = "imdb_rating")
@DeleteColumn(tableName = "serials_table", columnName = "imdb_rating")
class MigrationFrom1To2 : AutoMigrationSpec