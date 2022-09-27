package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.data.network.MoviesApi
import com.emikhalets.medialib.data.network.RetrofitFactory
import com.emikhalets.medialib.utils.AppPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesMoviesRetrofit(prefs: AppPrefs): Retrofit = RetrofitFactory(prefs).retrofit

    @Provides
    @Singleton
    fun providesMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create()
}
