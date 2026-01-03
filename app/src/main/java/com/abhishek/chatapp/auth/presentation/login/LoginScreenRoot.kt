package com.abhishek.chatapp.auth.presentation.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreenRoot(
    onSuccess:()-> Unit,
    modifier: Modifier = Modifier,
    viewmodel: LoginScreenVM= hiltViewModel()){


    val state by viewmodel.loginScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(viewmodel.eventFlow){
        viewmodel.eventFlow.collect{
            onSuccess()
        }
    }

    LoginScreen(
        state = state,
        onLoginClick = {
            viewmodel.onAction(LoginScreenEvent.OnLoginClick)
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginScreenState,
    onLoginClick: () -> Unit){

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){

        Button(
            onClick = onLoginClick,
            enabled = !state.isLoading){
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp
                )
            }
            else {
                Text("Login")
            }
        }
    }
}
