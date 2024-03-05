package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.os.Build
import androidx.annotation.RequiresApi

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

    fun authenticate(activity: FragmentActivity) {
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
                    sendResponse(authToken)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    }
}