package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.notduo.util.Hasher
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel() : ViewModel() {
    private val repository = UserRepository()

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var verifyPassword by mutableStateOf("")
    var validPassword by mutableStateOf(true)
    var isRegistered by mutableStateOf(false)

    private fun validatePassword() : Boolean {
        // Booleans for each requirement
        var lower = false
        var upper = false
        var special = false
        var digit = false
        var length = false
        var verify = false

        // Compare password and verifyPassword
        if (password.equals(verifyPassword, ignoreCase = false)) {
            verify = true
        }

        // Check if Password meets requirements

        // At least 8 characters
        if(password.length >= 8){ length = true }

        // String containing recognized special characters
        val specialChar = "!@#$%^&*_+-="

        for (i in password.indices){
            // contains at least 1 lowercase
            if(password[i].isLowerCase()){ lower = true }

            // contains at least 1 uppercase
            if(password[i].isUpperCase()){ upper = true }

            // contains at least 1 digit
            if(password[i].isDigit()){ digit = true }

            // contains at least 1 special !@#$%^&*
            if(specialChar.contains(password[i])) {special = true}

        }

        // Only valid if all requirements are met
        validPassword = verify && length && lower && upper && digit && special

        return validPassword
    }

    suspend fun getToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            null
        }
    }

    fun registerUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = getToken()

            if (token != null) {
                if (validatePassword()) {
                    val passwordHash = Hasher.hashString(password)
                    val user = User(firstName, lastName, username, passwordHash, token)
                    repository.addUser(user)
                    isRegistered = true
                }
            } else {
                // Handle the case where the token couldn't be retrieved
            }
        }
    }
}