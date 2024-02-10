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

        // password == verifyPassword

        // password meets requirements
            // contain 1 special char !@#$%^&*
            // contain both lower and upper char
            // contain 1 digit
            // at least 8 chars

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