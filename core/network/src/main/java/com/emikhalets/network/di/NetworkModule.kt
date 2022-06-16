package com.emikhalets.network.di

import com.emikhalets.network.MOVIES_API_KEY
import com.emikhalets.network.MOVIES_BASE_URL
import com.emikhalets.network.RetrofitFactory
import com.emikhalets.network.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class MoviesRetrofit

@Qualifier
annotation class BooksRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @MoviesRetrofit
    fun providesMoviesRetrofit(): Retrofit =
        RetrofitFactory(MOVIES_BASE_URL, MOVIES_API_KEY).retrofit()

    @Provides
    @Singleton
    @BooksRetrofit
    fun providesBooksRetrofit(): Retrofit =
        RetrofitFactory("", MOVIES_API_KEY).retrofit()

    @Provides
    @Singleton
    fun providesMoviesApi(@MoviesRetrofit retrofit: Retrofit): MoviesApi = retrofit.create()
}
