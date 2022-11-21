package com.emikhalets.medialib.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.emikhalets.medialib.data.entity.database.BookDB
import com.emikhalets.medialib.data.entity.database.MovieDB
import com.emikhalets.medialib.data.entity.database.MusicDB
import com.emikhalets.medialib.data.entity.database.SerialDB

@Database(
    entities = [
        MovieDB::class,
        SerialDB::class,
        BookDB::class,
        MusicDB::class,
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = MigrationFrom1To2::class)
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val serialsDao: SerialsDao
    abstract val booksDao: BooksDao
    abstract val musicsDao: MusicsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "MediaLibDB").build()
    }
}

@DeleteColumn.Entries(
    DeleteColumn(tableName = "books", columnName = "vote_average"),
    DeleteColumn(tableName = "movies", columnName = "vote_average"),
    DeleteColumn(tableName = "movies", columnName = "runtime"),
    DeleteColumn(tableName = "serials", columnName = "vote_average")
)
@RenameColumn(tableName = "musics", fromColumnName = "genre", toColumnName = "genres")
private class MigrationFrom1To2 : AutoMigrationSpec