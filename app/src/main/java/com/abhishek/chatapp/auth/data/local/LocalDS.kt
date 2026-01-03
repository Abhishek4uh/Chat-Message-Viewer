package com.abhishek.chatapp.auth.data.local


interface LocalDS {
    suspend fun isLoggedIn(): Boolean
    suspend fun setLoggedIn(value: Boolean)
    suspend fun clear()
}