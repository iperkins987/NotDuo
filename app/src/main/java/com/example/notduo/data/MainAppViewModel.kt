package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainAppViewModel() : ViewModel() {
    // Create repository
    private var repository = UserRepository()

    // Initialize variables
    var username by mutableStateOf("")
    var isAuth by mutableStateOf(false)
    var authToken by mutableStateOf("")

    init {
        // Retrieve username from SessionManager
        SessionManager.username.value?.let { username ->

            // Assign username property
            this.username = username

            // Inform repository of current username
            repository.onAuthUpdate(username, ::onAuthUpdate)
        }
    }

    fun onAuthUpdate(authToken: String) {

        // If not already authenticated
        if (authToken != "NO_TOKEN") {

            // Update authentication state
            isAuth = true

            // Assign authToken property with input
            this.authToken = authToken
        }
    }

    fun sendResponse(response: String) {
        // Sends response to repository containing username
        repository.addResponseToken(username, response)
    }
}