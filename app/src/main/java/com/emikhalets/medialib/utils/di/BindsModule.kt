package com.emikhalets.medialib.utils.di

import com.emikhalets.medialib.data.repository.DatabaseRepositoryImpl
import com.emikhalets.medialib.data.repository.NetworkRepositoryImpl
import com.emikhalets.medialib.domain.repository.DatabaseRepository
import com.emikhalets.medialib.domain.repository.NetworkRepository
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
    fun bindsDatabaseRepository(impl: DatabaseRepositoryImpl): DatabaseRepository

    @Binds
    @Singleton
    fun bindsNetworkRepository(impl: NetworkRepositoryImpl): NetworkRepository
}
