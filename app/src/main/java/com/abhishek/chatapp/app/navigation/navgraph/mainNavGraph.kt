package com.abhishek.chatapp.app.navigation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhishek.chatapp.app.navigation.routes.MainRoute
import com.abhishek.chatapp.app.navigation.routes.NavGraphRoute
import com.abhishek.chatapp.features.home.presentation.chat.ChatScreen
import com.abhishek.chatapp.features.home.presentation.home.HomeScreenRoot

fun NavGraphBuilder.mainNavGraph(navController: NavHostController){

    navigation<NavGraphRoute.Main>(startDestination = MainRoute.Home){
        composable<MainRoute.Home>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
            }){
            HomeScreenRoot(
                onNavigateToChat = {
                    navController.navigate(MainRoute.ChatScreen)
                }
            )
        }

        composable<MainRoute.ChatScreen>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End)
            }){
            ChatScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}