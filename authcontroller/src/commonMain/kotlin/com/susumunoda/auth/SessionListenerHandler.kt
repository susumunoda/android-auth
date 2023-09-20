package com.susumunoda.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SessionListenerHandler(
    listeners: List<SessionListener>,
    authController: AuthController,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            authController.sessionFlow.collect { session ->
                if (session != Session.UNKNOWN) {
                    if (session == Session.LOGGED_OUT) {
                        listeners.forEach { it.onLogout() }
                    } else {
                        listeners.forEach { it.onLogin(session.user.id) }
                    }
                }
            }
        }
    }
}
