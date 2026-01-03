package com.abhishek.chatapp.auth.data.repository

import com.abhishek.chatapp.auth.data.local.LocalDS
import com.abhishek.chatapp.auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val localDS: LocalDS): AuthRepository{

    override suspend fun isLoggedIn(): Boolean {
        return localDS.isLoggedIn()
    }
    override suspend fun login() {
        localDS.setLoggedIn(true)
    }

    override suspend fun logout() {
        localDS.setLoggedIn(false)
    }
}