package com.example.notduo.data

import android.content.Context
import android.util.Log

object SessionManager {
    private val repository = UserRepository()
    fun startUserSession(context: Context, username: String) {
        val editor = context.getSharedPreferences("NotDuoSession", 0).edit()
        editor.putString("NotDuoUser", username)
        editor.apply()
    }

    suspend fun isSessionActive(context: Context) : Boolean{
        val username = context.getSharedPreferences("NotDuoSession", 0).getString("NotDuoUser", null)
        return (username != null) && (repository.getUser(username) != null)
    }

    fun endUserSession(context: Context) {
        val editor = context.getSharedPreferences("NotDuoSession", 0).edit()
        editor.clear()
        editor.apply()
    }
}