package com.emikhalets.medialib.utils.di

import android.content.Context
import com.emikhalets.medialib.data.database.AppDatabase
import com.emikhalets.medialib.data.database.BooksDao
import com.emikhalets.medialib.data.database.MoviesDao
import com.emikhalets.medialib.data.database.MusicsDao
import com.emikhalets.medialib.data.database.SerialsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    // Network

//    @Provides
//    @Singleton
//    fun providesTmdbRetrofit(prefs: AppPrefs): Retrofit = RetrofitFactory(prefs).retrofit

//    @Provides
//    @Singleton
//    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()

    // Database

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.get(context)

    @Singleton
    @Provides
    fun providesMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao

    @Singleton
    @Provides
    fun providesSerialsDao(database: AppDatabase): SerialsDao = database.serialsDao

    @Singleton
    @Provides
    fun providesBooksDao(database: AppDatabase): BooksDao = database.booksDao

    @Singleton
    @Provides
    fun providesMusicsDao(database: AppDatabase): MusicsDao = database.musicsDao
}
