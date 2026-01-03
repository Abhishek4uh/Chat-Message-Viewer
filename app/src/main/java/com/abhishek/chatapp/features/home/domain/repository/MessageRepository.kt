package com.abhishek.chatapp.features.home.domain.repository

import com.abhishek.chatapp.features.home.domain.model.Message
import kotlinx.coroutines.flow.Flow



interface MessageRepository {
    fun getAllMessages(): Flow<List<Message>>
    suspend fun getMessagesPaginated(limit: Int, offset: Int): List<Message>
    suspend fun insertMessage(message: Message)
    suspend fun insertMessages(messages: List<Message>)
    suspend fun getMessageCount(): Int
    suspend fun seedInitialData()
}

