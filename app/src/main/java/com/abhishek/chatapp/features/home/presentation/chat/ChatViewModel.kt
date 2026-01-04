package com.abhishek.chatapp.features.home.presentation.chat

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.chatapp.features.home.domain.model.FileAttachment
import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.model.MessageType
import com.abhishek.chatapp.features.home.domain.model.Sender
import com.abhishek.chatapp.features.home.domain.usecase.GetMessageCountUseCase
import com.abhishek.chatapp.features.home.domain.usecase.GetMessagesPaginatedUseCase
import com.abhishek.chatapp.features.home.domain.usecase.SeedInitialDataUseCase
import com.abhishek.chatapp.features.home.domain.usecase.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessagesPaginatedUseCase: GetMessagesPaginatedUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val seedInitialDataUseCase: SeedInitialDataUseCase,
    private val getMessageCountUseCase: GetMessageCountUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    private val pageSize = 10
    private var currentOffset = 0
    private var totalMessageCount = 0
    private var isLoadingMore = false

    init {
        loadMessages()
    }

    fun onChatScreenAction(action: ChatScreenAction) {
        when(action){
            ChatScreenAction.DismissFullScreenImage -> {
                dismissFullScreenImage()
            }
            ChatScreenAction.OnScrolledToTop -> {
                onScrolledToTop()
            }
            is ChatScreenAction.OnImageClick ->{
                onImageClick(action.message)
            }
            is ChatScreenAction.OnMessageTextChange -> {
                onMessageTextChange(action.text)
            }
            ChatScreenAction.OnScrolledToBottom -> {
                onScrolledToBottom()
            }
            ChatScreenAction.SendButtonClick -> {
                sendTextMessage()
            }
            is ChatScreenAction.SendImage -> {
                sendImageMessage(action.uri, action.fileSize)
            }
        }
    }

    private fun loadMessages() {
        viewModelScope.launch {
            //Seed initial data if first launch
            seedInitialDataUseCase()
            //Get total count
            totalMessageCount = getMessageCountUseCase()
            //Load initial batch (most recent 10 messages)
            loadInitialMessages()
        }
    }

    private suspend fun loadInitialMessages() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        //Calculate offset to get the last 10 messages
        val initialOffset = if (totalMessageCount > pageSize) {
            totalMessageCount - pageSize
        } else {
            0
        }

        currentOffset = initialOffset

        val messages = getMessagesPaginatedUseCase(
            limit = pageSize,
            offset = initialOffset
        )

        _uiState.update {
            it.copy(
                messages = messages,
                isLoading = false,
                hasMoreMessages = initialOffset > 0,
                shouldScrollToBottom = true //Always scroll to bottom on initial load
            )
        }
    }

    private fun loadMoreMessages() {
        if (isLoadingMore || !_uiState.value.hasMoreMessages) return

        viewModelScope.launch {
            isLoadingMore = true
            _uiState.update { it.copy(isLoadingMore = true) }

            val newOffset = (currentOffset - pageSize).coerceAtLeast(0)
            val itemsToLoad = currentOffset - newOffset

            if (itemsToLoad > 0) {
                val olderMessages = getMessagesPaginatedUseCase(
                    limit = itemsToLoad,
                    offset = newOffset
                )

                currentOffset = newOffset
                val updatedMessages = olderMessages + _uiState.value.messages

                _uiState.update {
                    it.copy(
                        messages = updatedMessages,
                        isLoadingMore = false,
                        hasMoreMessages = newOffset > 0
                    )
                }
            }
            else {
                _uiState.update {
                    it.copy(
                        isLoadingMore = false,
                        hasMoreMessages = false
                    )
                }
            }
            isLoadingMore = false
        }
    }

    private fun onScrolledToTop() {
        loadMoreMessages()
    }

    private fun onMessageTextChange(text: String) {
        _uiState.update { it.copy(messageText = text) }
    }

    private fun sendTextMessage() {
        val text = _uiState.value.messageText.trim()
        if (text.isEmpty()) return

        viewModelScope.launch {
            val message = Message(
                id = "msg-${UUID.randomUUID()}",
                message = text,
                type = MessageType.TEXT,
                file = null,
                sender = Sender.USER,
                timestamp = System.currentTimeMillis()
            )

            sendMessageUseCase(message)
            _uiState.update {
                it.copy(
                    messageText = "",
                    shouldScrollToBottom = true
                )
            }

            //Update total count and reload if needed
            totalMessageCount++
            //Add message to current list
            _uiState.update {
                it.copy(messages = it.messages + message)
            }
            //Simulate agent typing and response
            simulateAgentResponse()
        }
    }

    private fun sendImageMessage(uri: Uri, fileSize: Long) {
        viewModelScope.launch {
            val message = Message(
                id = "msg-${UUID.randomUUID()}",
                message = "",
                type = MessageType.FILE,
                file = FileAttachment(
                    path = uri.toString(),
                    fileSize = fileSize,
                    thumbnail = null
                ),
                sender = Sender.USER,
                timestamp = System.currentTimeMillis()
            )

            sendMessageUseCase(message)

            //Update total count
            totalMessageCount++

            //Add message to current list
            _uiState.update {
                it.copy(
                    messages = it.messages + message,
                    shouldScrollToBottom = true
                )
            }

            //Simulate agent response
            simulateAgentResponse()
        }
    }

    private suspend fun simulateAgentResponse() {
        _uiState.update { it.copy(isAgentTyping = true) }
        delay(2000) //Simulate typing delay

        val responses = listOf(
            "Got it! Let me help you with that.",
            "Thanks for sharing! I'm processing your request.",
            "I've received your message. Give me a moment.",
            "Perfect! Let me look into this for you.",
            "Understood! I'll get back to you shortly."
        )

        val agentMessage = Message(
            id = "msg-${UUID.randomUUID()}",
            message = responses.random(),
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = System.currentTimeMillis()
        )

        sendMessageUseCase(agentMessage)

        //Update total count
        totalMessageCount++

        //Add message to current list
        _uiState.update {
            it.copy(
                messages = it.messages + agentMessage,
                isAgentTyping = false,
                shouldScrollToBottom = true
            )
        }
    }

    private fun onImageClick(message: Message) {
        _uiState.update {
            it.copy(
                selectedImage = message.file?.path,
                showFullScreenImage = true
            )
        }
    }

    private fun dismissFullScreenImage() {
        _uiState.update {
            it.copy(
                selectedImage = null,
                showFullScreenImage = false
            )
        }
    }

    private fun onScrolledToBottom() {
        _uiState.update {
            it.copy(shouldScrollToBottom = false)
        }
    }
}

sealed interface ChatScreenAction{
    data object OnScrolledToTop:ChatScreenAction
    data object OnScrolledToBottom: ChatScreenAction
    data class SendImage(val uri: Uri, val fileSize: Long): ChatScreenAction
    data class OnImageClick(val message: Message): ChatScreenAction
    data class OnMessageTextChange(val text: String): ChatScreenAction
    data object SendButtonClick: ChatScreenAction
    data object DismissFullScreenImage: ChatScreenAction
}

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val messageText: String = "",
    val isAgentTyping: Boolean = false,
    val selectedImage: String? = null,
    val showFullScreenImage: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMoreMessages: Boolean = false,
    val shouldScrollToBottom: Boolean = false
)



