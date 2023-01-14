package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.domain.repository.DatabaseRepository
import com.emikhalets.medialib.domain.repository.NetworkRepository
import com.emikhalets.medialib.domain.use_case.movies.MovieDetailsUseCase
import com.emikhalets.medialib.domain.use_case.movies.MovieEditUseCase
import com.emikhalets.medialib.domain.use_case.movies.MoviesListUseCase
import com.emikhalets.medialib.domain.use_case.searching.SearchImdbUseCase
import com.emikhalets.medialib.domain.use_case.serials.SerialDetailsUseCase
import com.emikhalets.medialib.domain.use_case.serials.SerialEditUseCase
import com.emikhalets.medialib.domain.use_case.serials.SerialsListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun providesMovieDetailsUseCase(databaseRepository: DatabaseRepository): MovieDetailsUseCase {
        return MovieDetailsUseCase(databaseRepository)
    }

    @Provides
    @Singleton
    fun providesMovieEditUseCase(databaseRepository: DatabaseRepository): MovieEditUseCase {
        return MovieEditUseCase(databaseRepository)
    }

    @Provides
    @Singleton
    fun providesMoviesListUseCase(databaseRepository: DatabaseRepository): MoviesListUseCase {
        return MoviesListUseCase(databaseRepository)
    }

    @Provides
    @Singleton
    fun providesSearchImdbUseCase(
        databaseRepository: DatabaseRepository,
        networkRepository: NetworkRepository,
    ): SearchImdbUseCase {
        return SearchImdbUseCase(networkRepository, databaseRepository)
    }

    @Provides
    @Singleton
    fun providesSerialDetailsUseCase(databaseRepository: DatabaseRepository): SerialDetailsUseCase {
        return SerialDetailsUseCase(databaseRepository)
    }

    @Provides
    @Singleton
    fun providesSerialEditUseCase(databaseRepository: DatabaseRepository): SerialEditUseCase {
        return SerialEditUseCase(databaseRepository)
    }

    @Provides
    @Singleton
    fun providesSerialsListUseCase(databaseRepository: DatabaseRepository): SerialsListUseCase {
        return SerialsListUseCase(databaseRepository)
    }
}
