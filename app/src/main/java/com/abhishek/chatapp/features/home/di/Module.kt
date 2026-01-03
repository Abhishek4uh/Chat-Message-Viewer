package com.abhishek.chatapp.features.home.di

import com.abhishek.chatapp.features.home.data.database.MessageDao
import com.abhishek.chatapp.features.home.data.repositoryImpl.MessageRepositoryImpl
import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository {
        return MessageRepositoryImpl(messageDao)
    }
}