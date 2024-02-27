package com.example.notduo.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val database = Firebase.database
    private val userRef = database.getReference("users")

    fun addUser(user: User) {
        userRef.child(user.username).setValue(user)
    }

    suspend fun getUser(username: String) : User? {
        return try {
            val snapshot = userRef.child(username).get().await()
            snapshot.getValue<User>()
        } catch (e: Exception) {
            // Handle the error appropriately
            null
        }
    }
}