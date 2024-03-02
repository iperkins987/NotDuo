package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainAppViewModel() : ViewModel() {
    private var repository = UserRepository()
    var username by mutableStateOf("")
    var isAuth by mutableStateOf(false)
    var authToken by mutableStateOf("")

    init {
        SessionManager.username.value?.let { username ->
            this.username = username
            repository.onAuthUpdate(username, ::onAuthUpdate)
        }
    }

    fun onAuthUpdate(authToken: String) {
        if (authToken != "NO_TOKEN") {
            isAuth = true
            this.authToken = authToken
        }
    }

    fun sendResponse(response: String) {
        repository.addResponseToken(username, response)
    }
}