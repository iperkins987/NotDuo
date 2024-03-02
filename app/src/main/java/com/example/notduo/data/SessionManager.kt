package com.example.notduo.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object SessionManager {
    private val repository = UserRepository()
    var username: MutableState<String?> = mutableStateOf(null)

    fun startUserSession(context: Context, username: String) {
        val editor = context.getSharedPreferences("NotDuoSession", 0).edit()
        editor.putString("NotDuoUser", username)
        editor.apply()
        this.username.value = username
    }

    suspend fun isSessionActive(context: Context) : Boolean{
        val username = context.getSharedPreferences("NotDuoSession", 0).getString("NotDuoUser", null)
        this.username.value = username
        return (username != null) && (repository.getUser(username) != null)
    }

    fun getUsername() : String? {
        return username.value
    }

    fun endUserSession(context: Context) {
        val editor = context.getSharedPreferences("NotDuoSession", 0).edit()
        editor.clear()
        editor.apply()
    }
}