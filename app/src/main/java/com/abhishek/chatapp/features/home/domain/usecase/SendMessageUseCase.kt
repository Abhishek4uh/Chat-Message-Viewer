package com.abhishek.chatapp.features.home.domain.usecase


import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository){
    suspend operator fun invoke(message: Message) {
        repository.insertMessage(message)
    }
}