package com.susumunoda.android.auth

class Session(val user: User, private val label: String = "") {
    companion object {
        val LOGGED_OUT = Session(User.ANONYMOUS, "LOGGED_OUT")
        val UNKNOWN = Session(User.ANONYMOUS, "UNKNOWN")
    }

    override fun toString() = "Session(user=$user, label=$label)"
}
