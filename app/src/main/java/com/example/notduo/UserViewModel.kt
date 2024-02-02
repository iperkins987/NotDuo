package com.example.notduo

import androidx.lifecycle.ViewModel

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    fun registerUser(firstName: String, lastName: String, username: String) {
        val user = User(firstName, lastName, username)
        repository.addUser(user)
    }
}