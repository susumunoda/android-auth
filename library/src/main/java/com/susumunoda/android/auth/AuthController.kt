package com.susumunoda.android.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthController {
    val sessionFlow: StateFlow<Session>
    suspend fun createUser(email: String, password: String): Session.User
    suspend fun login(email: String, password: String): Session.User
    fun logout()
}
