package com.emikhalets.medialib.utils.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

//    @Binds
//    @Singleton
//    fun bindsMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository
}
