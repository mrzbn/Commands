package com.example.commands.di

import com.example.commands.database.CommandDao
import com.example.commands.repositories.CommandRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCommandRepository(commandDao: CommandDao): CommandRepository {
        return CommandRepository(commandDao)
    }
}