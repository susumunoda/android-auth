package com.susumunoda.android.auth.mock

import com.susumunoda.android.auth.AuthController
import com.susumunoda.android.auth.Session
import com.susumunoda.android.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MockAuthController(session: Session = Session.LOGGED_OUT) : AuthController {
    private val database = mutableMapOf<String, User>()

    init {
        if (session !== Session.LOGGED_OUT) {
            database[session.user.id] = session.user
        }
    }

    private val _sessionFlow = MutableStateFlow(session)
    override val sessionFlow = _sessionFlow.asStateFlow()

    override suspend fun createUser(email: String, password: String): User {
        val user = User("$email+$password")
        database[user.id] = user
        _sessionFlow.update { Session(user) }
        return user
    }

    override suspend fun login(email: String, password: String): User {
        val user = database["$email+$password"]!!
        _sessionFlow.update { Session(user) }
        return user
    }

    override fun logout() {
        _sessionFlow.update { Session.LOGGED_OUT }
    }
}