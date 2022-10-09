package com.emikhalets.medialib.utils.di

import android.content.Context
import com.emikhalets.medialib.data.database.AppDatabase
import com.emikhalets.medialib.data.database.GenresDao
import com.emikhalets.medialib.data.database.MoviesDao
import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.network.RetrofitFactory
import com.emikhalets.medialib.data.network.SupportApi
import com.emikhalets.medialib.utils.AppPrefs
import com.emikhalets.medialib.utils.GenresHelper
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
    fun providesTmdbRetrofit(prefs: AppPrefs): Retrofit = RetrofitFactory(prefs).retrofit

    @Provides
    @Singleton
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()

    @Provides
    @Singleton
    fun providesSupportApi(retrofit: Retrofit): SupportApi = retrofit.create()

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
    fun providesGenresDao(database: AppDatabase): GenresDao = database.genresDao

    // Other

    @Provides
    @Singleton
    fun providesGenresHelper(): GenresHelper = GenresHelper()
}
