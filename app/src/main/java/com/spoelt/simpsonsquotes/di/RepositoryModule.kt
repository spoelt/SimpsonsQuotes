package com.spoelt.simpsonsquotes.di

import com.spoelt.simpsonsquotes.repository.QuoteRepository
import com.spoelt.simpsonsquotes.repository.QuoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(): QuoteRepository = QuoteRepositoryImpl()
}