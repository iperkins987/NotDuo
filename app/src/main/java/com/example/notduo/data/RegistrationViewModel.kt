package com.example.notduo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notduo.util.Hasher

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
        // TODO: Validate Password

        // Booleans for each requirement
        var lower by mutableStateOf(false)
        var upper by mutableStateOf(false)
        var special by mutableStateOf(false)
        var digit by mutableStateOf(false)
        var length by mutableStateOf(false)
        var verify by mutableStateOf(false)

        // Compare password and verifyPassword
        if (password.equals(verifyPassword, ignoreCase = false)) {
            verify = true
        }

        // FIXME: if password invalid when registering have to close app to try again

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

    fun registerUser() {

        if (validatePassword()) {
            val passwordHash = Hasher.hashString(password)
            val user = User(firstName, lastName, username, passwordHash)
            repository.addUser(user)
            isRegistered = true
        }
    }
}