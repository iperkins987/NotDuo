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
    // Create repository
    private val repository = UserRepository()

    // Initialize input variables as empty strings
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var verifyPassword by mutableStateOf("")

    // Boolean variables
    var validPassword by mutableStateOf(true)
    var isRegistered by mutableStateOf(false)
    var validUsername by mutableStateOf(true)

    suspend fun validateUsername() : Boolean {
        // Check if username already taken

        if(repository.getUser(username) != null){
            // username already exists
            validUsername = false
        }

        return validUsername
    }

    private fun validatePassword() : Boolean {
        // Check if password meets requirements and matches verifyPassword

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
        // Attempts to retrieve FBM token
        return try {
            // returns token as string if successful
            FirebaseMessaging.getInstance().token.await()
        } catch (e: Exception) {
            // returns Null if unsuccessful
            null
        }
    }

    fun registerUser() {

        // Launch coroutine with Dispatchers.IO
        CoroutineScope(Dispatchers.IO).launch {

            // Retrieve token
            val token = getToken()

            // Check if token was acquired
            if (token != null) {

                // Make sure username and password are valid
                if(validateUsername() && validatePassword()){

                        // Encrypt password using hash
                        val passwordHash = Hasher.hashString(password)

                        // Create user object and add to repository
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