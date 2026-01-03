package com.abhishek.chatapp.app.navigation.routes

import kotlinx.serialization.Serializable

sealed interface NavGraphRoute {
    @Serializable
    data object Authentication : NavGraphRoute
    @Serializable
    data object Main : NavGraphRoute
}


sealed class AuthenticationRoute{
    //This can be Extensible with Signup, OTP, Verification etc
    @Serializable
    data object Login:AuthenticationRoute()
}

sealed class MainRoute{
    @Serializable
    data object Home:MainRoute()
    @Serializable
    data object ChatScreen:MainRoute()
}
