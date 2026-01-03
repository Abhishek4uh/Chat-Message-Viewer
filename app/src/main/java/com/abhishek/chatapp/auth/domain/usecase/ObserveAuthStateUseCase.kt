package com.abhishek.chatapp.auth.domain.usecase

import com.abhishek.chatapp.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ObserveAuthStateUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke():Boolean{
        return repository.isLoggedIn()
    }
}