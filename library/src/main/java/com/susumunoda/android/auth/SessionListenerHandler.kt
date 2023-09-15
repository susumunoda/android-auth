package com.susumunoda.android.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SessionListenerHandler(
    repositories: List<SessionListener>,
    authController: AuthController,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            authController.sessionFlow.collect { session ->
                if (session != Session.UNKNOWN) {
                    if (session == Session.LOGGED_OUT) {
                        repositories.forEach { it.onLogout() }
                    } else {
                        repositories.forEach { it.onLogin(session.user.id) }
                    }
                }
            }
        }
    }
}
