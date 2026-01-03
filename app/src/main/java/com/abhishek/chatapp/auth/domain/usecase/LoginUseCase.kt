package com.abhishek.chatapp.auth.domain.usecase

import com.abhishek.chatapp.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor ( private val repository: AuthRepository) {
    suspend operator fun invoke() {
        repository.login()
    }
}
