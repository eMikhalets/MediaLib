package com.emikhalets.medialib.utils.di

import android.content.Context
import com.emikhalets.medialib.data.database.AppDatabase
import com.emikhalets.medialib.data.database.crew.CrewDao
import com.emikhalets.medialib.data.database.genres.GenresDao
import com.emikhalets.medialib.data.database.movies.MoviesDao
import com.emikhalets.medialib.data.database.serials.SerialsDao
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    // Network

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = RetrofitFactory().retrofit

    @Provides
    @Singleton
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()

    // Database

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providesMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao

    @Singleton
    @Provides
    fun providesSerialsDao(database: AppDatabase): SerialsDao = database.serialsDao

    @Singleton
    @Provides
    fun providesGenresDao(database: AppDatabase): GenresDao = database.genresDao

    @Singleton
    @Provides
    fun providesCrewDao(database: AppDatabase): CrewDao = database.crewDao
}
