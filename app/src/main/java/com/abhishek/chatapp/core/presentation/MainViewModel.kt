package com.abhishek.chatapp.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.chatapp.app.navigation.routes.NavGraphRoute
import com.abhishek.chatapp.auth.domain.usecase.ObserveAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val observeAuthStateUseCase: ObserveAuthStateUseCase) : ViewModel() {
    private val _startDestination = MutableStateFlow<NavGraphRoute?>(null)
    val startDestination: StateFlow<NavGraphRoute?> = _startDestination

    init {
        viewModelScope.launch {
            val isLoggedIn = observeAuthStateUseCase()
            _startDestination.value =
                if (isLoggedIn) NavGraphRoute.Main
                else NavGraphRoute.Authentication
        }
    }
}
