package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.data.repository.MoviesRepository
import com.emikhalets.medialib.data.repository.MoviesRepositoryImpl
import com.emikhalets.medialib.data.repository.SupportRepository
import com.emikhalets.medialib.data.repository.SupportRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

    @Binds
    @Singleton
    fun bindsMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    @Singleton
    fun bindsSupportRepository(impl: SupportRepositoryImpl): SupportRepository
}
