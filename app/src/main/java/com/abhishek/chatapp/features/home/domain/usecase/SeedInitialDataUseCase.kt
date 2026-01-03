package com.abhishek.chatapp.features.home.domain.usecase


import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import javax.inject.Inject

class SeedInitialDataUseCase @Inject constructor(private val repository: MessageRepository){
    suspend operator fun invoke() {
        repository.seedInitialData()
    }
}