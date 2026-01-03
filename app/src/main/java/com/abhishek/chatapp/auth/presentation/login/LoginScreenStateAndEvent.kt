package com.abhishek.chatapp.auth.presentation.login



data class LoginScreenState(
    val isLoading: Boolean= false
)


sealed interface LoginScreenEvent{
    data object OnLoginClick: LoginScreenEvent
}