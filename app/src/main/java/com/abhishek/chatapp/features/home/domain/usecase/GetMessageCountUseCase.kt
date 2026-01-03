package com.abhishek.chatapp.features.home.domain.usecase

import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessageCountUseCase @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(): Int {
        return repository.getMessageCount()
    }
}
