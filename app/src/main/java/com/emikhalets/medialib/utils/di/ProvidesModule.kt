package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.network.RetrofitFactory
import com.emikhalets.medialib.data.network.SupportApi
import com.emikhalets.medialib.utils.AppPrefs
import com.emikhalets.medialib.utils.GenresHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {

    @Provides
    @Singleton
    fun providesTmdbRetrofit(prefs: AppPrefs): Retrofit = RetrofitFactory(prefs).retrofit

    @Provides
    @Singleton
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()

    @Provides
    @Singleton
    fun providesSupportApi(retrofit: Retrofit): SupportApi = retrofit.create()

    @Provides
    @Singleton
    fun providesGenresHelper(): GenresHelper = GenresHelper()
}
