package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.data.repository.BooksRepository
import com.emikhalets.medialib.data.repository.BooksRepositoryImpl
import com.emikhalets.medialib.data.repository.MoviesRepository
import com.emikhalets.medialib.data.repository.MoviesRepositoryImpl
import com.emikhalets.medialib.data.repository.MusicsRepository
import com.emikhalets.medialib.data.repository.MusicsRepositoryImpl
import com.emikhalets.medialib.data.repository.SerialsRepository
import com.emikhalets.medialib.data.repository.SerialsRepositoryImpl
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
    fun bindsMusicsRepository(impl: MusicsRepositoryImpl): MusicsRepository

    @Binds
    @Singleton
    fun bindsBooksRepository(impl: BooksRepositoryImpl): BooksRepository

    @Binds
    @Singleton
    fun bindsSerialsRepository(impl: SerialsRepositoryImpl): SerialsRepository
}
