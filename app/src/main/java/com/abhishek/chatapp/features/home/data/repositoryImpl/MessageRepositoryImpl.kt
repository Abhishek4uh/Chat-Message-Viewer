package com.abhishek.chatapp.features.home.data.repositoryImpl

import com.abhishek.chatapp.features.home.data.database.MessageDao
import com.abhishek.chatapp.features.home.data.mapper.toDomain
import com.abhishek.chatapp.features.home.data.mapper.toEntity
import com.abhishek.chatapp.features.home.domain.MockData
import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject



class MessageRepositoryImpl @Inject constructor(private val messageDao: MessageDao) : MessageRepository {

    override fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages().map {entities ->
            entities.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getMessagesPaginated(limit: Int, offset: Int): List<Message> {
        return messageDao.getMessagesPaginated(limit, offset).map {
            it.toDomain()
        }
    }

    override suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message.toEntity())
    }

    override suspend fun insertMessages(messages: List<Message>) {
        messageDao.insertMessages(messages.map { it.toEntity() })
    }

    override suspend fun getMessageCount(): Int {
        return messageDao.getMessageCount()
    }

    override suspend fun seedInitialData() {
        val count = getMessageCount()
        if (count == 0) {
            insertMessages(getSeedMessages())
        }
    }

    private fun getSeedMessages(): List<Message> = MockData.messages
}
