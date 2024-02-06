package com.example.notduo.data

import com.google.firebase.Firebase
import com.google.firebase.database.database

class UserRepository {
    private val database = Firebase.database
    private val userRef = database.getReference("Users")

    fun addUser(user: User) {
        userRef.child(user.username).setValue(user)
    }
}