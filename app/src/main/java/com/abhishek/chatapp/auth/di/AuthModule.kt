package com.abhishek.chatapp.auth.di


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.abhishek.chatapp.auth.data.local.LocalDS
import com.abhishek.chatapp.auth.data.local.LocalDSImpl
import com.abhishek.chatapp.auth.data.repository.AuthRepositoryImpl
import com.abhishek.chatapp.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule{

    @Provides
    @Singleton
    fun provideLocalDS(dataStore: DataStore<Preferences>): LocalDS {
        return LocalDSImpl(dataStore)
    }


    @Provides
    @Singleton
    fun provideAuthRepository(localDS: LocalDS): AuthRepository {
        return AuthRepositoryImpl(localDS)
    }
}