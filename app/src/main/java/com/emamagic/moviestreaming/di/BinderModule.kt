package com.emamagic.moviestreaming.di

import com.emamagic.moviestreaming.repository.home.HomeRepository
import com.emamagic.moviestreaming.repository.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

}