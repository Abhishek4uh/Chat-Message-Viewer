package com.abhishek.chatapp.features.home.domain.model


data class Message(
    val id: String,
    val message: String,
    val type: MessageType,
    val file: FileAttachment?,
    val sender: Sender,
    val timestamp: Long
)

enum class MessageType {
    TEXT,
    FILE
}

enum class Sender {
    USER,
    AGENT
}

data class FileAttachment(
    val path: String,
    val fileSize: Long,
    val thumbnail: Thumbnail?
)

data class Thumbnail(
    val path: String
)