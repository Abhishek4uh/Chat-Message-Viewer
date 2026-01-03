package com.abhishek.chatapp.features.home.presentation.commonComponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abhishek.chatapp.common.FileUtil
import com.abhishek.chatapp.common.TimestampUtil
import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.model.MessageType
import com.abhishek.chatapp.features.home.domain.model.Sender


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageItem(
    message: Message,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier){


    val isUser = message.sender == Sender.USER

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(300)) +
                slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    ),
                    initialOffsetY = {
                        it / 4
                    }
                ),
        modifier = modifier){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start){

            Surface(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .combinedClickable(
                        onClick = {
                            if (message.type == MessageType.FILE) {
                                onImageClick()
                            }
                        }
                    ),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp){

                Column(
                    modifier = Modifier.padding(12.dp)){

                    //File/Image content
                    if (message.type == MessageType.FILE && message.file != null) {
                        AsyncImage(
                            model = message.file.thumbnail?.path ?: message.file.path,
                            contentDescription = "Attached image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        if (message.file.fileSize > 0){
                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )
                            Text(
                                text = FileUtil.formatFileSize(message.file.fileSize),
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isUser)
                                    MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }

                        if (message.message.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    if (message.message.isNotEmpty()){
                        Text(
                            text = message.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isUser)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Text(
                text = TimestampUtil.formatSmartTimestamp(message.timestamp),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}