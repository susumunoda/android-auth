package com.susumunoda.auth

interface SessionListener {
    fun onLogin(userId: String)
    fun onLogout()
}
