package com.abhishek.chatapp.app.navigation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhishek.chatapp.app.navigation.routes.AuthenticationRoute
import com.abhishek.chatapp.app.navigation.routes.NavGraphRoute
import com.abhishek.chatapp.auth.presentation.login.LoginScreenRoot

fun NavGraphBuilder.authNavGraph(navController: NavHostController){

    navigation<NavGraphRoute.Authentication>(startDestination = AuthenticationRoute.Login){
        composable<AuthenticationRoute.Login>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
            }){
            LoginScreenRoot(
                onSuccess = {
                    navController.navigate(NavGraphRoute.Main){
                        popUpTo(NavGraphRoute.Authentication){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}