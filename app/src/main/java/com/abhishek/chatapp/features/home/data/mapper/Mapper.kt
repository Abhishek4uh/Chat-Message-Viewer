package com.abhishek.chatapp.features.home.data.mapper

import com.abhishek.chatapp.features.home.data.database.MessageEntity
import com.abhishek.chatapp.features.home.domain.model.FileAttachment
import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.model.MessageType
import com.abhishek.chatapp.features.home.domain.model.Sender
import com.abhishek.chatapp.features.home.domain.model.Thumbnail


fun MessageEntity.toDomain(): Message{
    return Message(
        id = id,
        message = message,
        type = MessageType.valueOf(type),
        file = if (filePath != null) {
            FileAttachment(
                path = filePath,
                fileSize = fileSize ?: 0L,
                thumbnail = thumbnailPath?.let {
                    Thumbnail(it)
                }
            )
        } else null,
        sender = Sender.valueOf(sender),
        timestamp = timestamp
    )
}

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = id,
        message = message,
        type = type.name,
        filePath = file?.path,
        fileSize = file?.fileSize,
        thumbnailPath = file?.thumbnail?.path,
        sender = sender.name,
        timestamp = timestamp
    )
}