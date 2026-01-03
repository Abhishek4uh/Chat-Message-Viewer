package com.abhishek.chatapp.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.chatapp.auth.domain.usecase.LoginUseCase
import com.abhishek.chatapp.auth.domain.usecase.ObserveAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenVM @Inject constructor(
    private val loginUseCase: LoginUseCase): ViewModel(){
    private val _loginScreenState= MutableStateFlow(LoginScreenState())
    val loginScreenState= _loginScreenState.asStateFlow()

    private val _eventFlow= MutableSharedFlow<Boolean>()
    val eventFlow= _eventFlow.asSharedFlow()



    fun onAction(action:LoginScreenEvent){
        when(action){
            LoginScreenEvent.OnLoginClick -> {
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(isLoading = true)
                    }
                    delay(2000)
                    _loginScreenState.update {
                        it.copy(isLoading = false)
                    }
                    _eventFlow.emit(true)
                    loginUseCase()
                }
            }
        }
    }
}