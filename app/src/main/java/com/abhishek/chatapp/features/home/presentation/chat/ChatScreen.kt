package com.abhishek.chatapp.features.home.presentation.chat


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.chatapp.features.home.presentation.commonComponent.FullScreenImageViewer
import com.abhishek.chatapp.features.home.presentation.commonComponent.MessageInputBar
import com.abhishek.chatapp.features.home.presentation.commonComponent.MessageItem
import com.abhishek.chatapp.features.home.presentation.commonComponent.TypingIndicator
import com.abhishek.chatapp.theme.ChatAppTheme


@Composable
fun ChatScreenRoot(
    onNavigateBack: () -> Unit,
    viewmodel: ChatViewModel = hiltViewModel()) {

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    ChatScreen(
        onNavigateBack= onNavigateBack,
        uiState = uiState,
        onChatScreenAction = viewmodel::onChatScreenAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    uiState:ChatUiState,
    onChatScreenAction: (ChatScreenAction)-> Unit,
    onNavigateBack: () -> Unit){

    val context = LocalContext.current

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = maxOf(0, uiState.messages.size - 1)
    )

    //Auto-scroll to bottom when new message is sent
    LaunchedEffect(uiState.messages.size, uiState.shouldScrollToBottom) {
        if (uiState.shouldScrollToBottom && uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
            onChatScreenAction.invoke(ChatScreenAction.OnScrolledToBottom)
        }
    }

    //Detect when user scrolls to top to load more
    LaunchedEffect(listState.canScrollBackward, listState.canScrollForward) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { firstVisibleIndex ->
                if (firstVisibleIndex <= 2 && uiState.hasMoreMessages && !uiState.isLoadingMore) {
                    onChatScreenAction.invoke(ChatScreenAction.OnScrolledToTop)
                }
            }
    }

    //Image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()){ uri: Uri? ->
        uri?.let {
            val fileSize = try {
                context.contentResolver.openInputStream(uri)?.available()?.toLong() ?: 0L
            }
            catch (e: Exception) {
                0L
            }
            onChatScreenAction.invoke(ChatScreenAction.SendImage(uri, fileSize))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("AI Support Chat")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }){padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)){
            Column(
                modifier = Modifier.fillMaxSize()){

                Box(modifier = Modifier.weight(1f)) {
                    if (uiState.isLoading) {
                        //Initial loading
                        CircularProgressIndicator(
                            color = Blue,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)){
                            if (uiState.isLoadingMore){
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        contentAlignment = Alignment.Center){
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            }

                            //Messages
                            items(
                                items = uiState.messages,
                                key = { it.id }){ message ->
                                MessageItem(
                                    message = message,
                                    onImageClick = {
                                        onChatScreenAction.invoke(
                                            ChatScreenAction.OnImageClick(
                                                message
                                            )
                                        )
                                    },
                                    modifier = Modifier.animateContentSize(
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                )
                            }

                            //Typing indicator
                            if (uiState.isAgentTyping) {
                                item {
                                    TypingIndicator()
                                }
                            }
                        }
                    }
                }
                //Input Bar
                MessageInputBar(
                    messageText = uiState.messageText,
                    onMessageTextChange = {
                        onChatScreenAction.invoke(ChatScreenAction.OnMessageTextChange(it))
                    },
                    onSendClick ={
                        onChatScreenAction.invoke(ChatScreenAction.SendButtonClick)
                    },
                    onAttachClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
        }
    }

    //Full screen image viewer
    if (uiState.showFullScreenImage && uiState.selectedImage != null) {
        FullScreenImageViewer(
            imageUrl = uiState.selectedImage,
            onDismiss = {
                onChatScreenAction.invoke(ChatScreenAction.DismissFullScreenImage)
            }
        )
    }
}

@Preview
@Composable
private fun ChatScreenPreview(){
    ChatAppTheme{
        ChatScreen(
            uiState = ChatUiState(

            ),
            onChatScreenAction = {},
            onNavigateBack = {}
        )
    }
}