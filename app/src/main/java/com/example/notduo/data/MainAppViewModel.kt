package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.biometric.BiometricPrompt
import android.os.Build
import androidx.annotation.RequiresApi

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

        // Make sure the auth token is valid
        if (authToken != "NO_TOKEN") {

            // We are ready to authenticate
            isAuth = true

            // Update the auth token
            this.authToken = authToken
        }
    }

    fun sendResponse(response: String) {

        // Update the response token, the server will compare this to the token it generated
        repository.addResponseToken(username, response)
    }

    fun authenticate(activity: FragmentActivity) {
        // All of this is just boiler plate biometric code

        val executor = ContextCompat.getMainExecutor(activity)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()


        val biometricPrompt = BiometricPrompt(activity, executor,
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    // If the biometric succeeds then send the response token back to the server
                    sendResponse(authToken)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }
}