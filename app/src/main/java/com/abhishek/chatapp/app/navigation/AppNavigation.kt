package com.abhishek.chatapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abhishek.chatapp.app.navigation.navgraph.authNavGraph
import com.abhishek.chatapp.app.navigation.navgraph.mainNavGraph
import com.abhishek.chatapp.app.navigation.routes.NavGraphRoute

@Composable
fun AppNavigation(startRoute:NavGraphRoute){

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startRoute){

        authNavGraph(
            navController= navController
        )

        mainNavGraph(
            navController= navController
        )
    }
}

