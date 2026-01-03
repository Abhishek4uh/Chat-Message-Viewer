package com.abhishek.chatapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.chatapp.app.navigation.AppNavigation
import com.abhishek.chatapp.core.presentation.MainViewModel
import com.abhishek.chatapp.theme.ChatAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel: MainViewModel  by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent{
            ChatAppTheme {
                //startRoute -> either NavGraphRoute.Authentication or NavGraphRoute.Main based on the datastore
                val startRoute by viewmodel.startDestination.collectAsStateWithLifecycle()
                startRoute?.let{
                    AppNavigation(startRoute = it)
                }
            }
        }
    }
}