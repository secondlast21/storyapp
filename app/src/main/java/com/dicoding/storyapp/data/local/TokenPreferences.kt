package com.dicoding.storyapp.data.local

import android.content.Context

class TokenPreferences(context: Context) {
    private val data = context.getSharedPreferences(TITLE, Context.MODE_PRIVATE)

    fun getUser(user: User) {
        data.edit().putString(USERNAME, user.name).apply()
        data.edit().putString(TOKEN, user.token).apply()
        data.edit().putBoolean(LOGIN, user.isLogin).apply()
    }

    fun isLogin(): Boolean {
        return data.getBoolean(LOGIN, false)
    }

    fun getToken(): User {
        val user = User()
        user.token = data.getString(TOKEN, "").toString()
        return user
    }

    companion object {
        private const val TITLE = "User Data"
        private const val USERNAME = "name"
        private const val TOKEN = "token"
        private const val LOGIN = "isLogin"
    }
}