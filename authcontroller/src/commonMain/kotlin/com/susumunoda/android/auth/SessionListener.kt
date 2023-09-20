package com.susumunoda.android.auth

interface SessionListener {
    fun onLogin(userId: String)
    fun onLogout()
}
