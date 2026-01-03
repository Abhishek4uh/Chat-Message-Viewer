package com.abhishek.chatapp.features.home.domain.usecase

import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import javax.inject.Inject

class GetMessagesPaginatedUseCase @Inject constructor(private val repository: MessageRepository) {
    suspend operator fun invoke(limit: Int, offset: Int): List<Message> {
        return repository.getMessagesPaginated(limit, offset)
    }
}
