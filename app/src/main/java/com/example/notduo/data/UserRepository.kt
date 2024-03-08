package com.example.notduo.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val database = Firebase.database
    private val userRef = database.getReference("users")

    fun addUser(user: User) {

        // Add a user to the database
        userRef.child(user.username).setValue(user)
    }

    fun addResponseToken(username: String, token: String) {

        // send response token to the database
        userRef.child(username).child("responseToken").setValue(token)
    }

    fun onAuthUpdate(username: String, callback: (String) -> Unit) {

        // Listen for new auth tokens and call the callback if it changes
        val authListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<User>()
                if (user != null) {
                    callback(user.authToken)
                    userRef.child(user.username).updateChildren(hashMapOf<String, Any>("/authToken" to "NO_TOKEN"))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

                // Getting Post failed, log a message
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException())
            }
        }

        userRef.child(username).addValueEventListener(authListener)
    }

    suspend fun getUser(username: String) : User? {

        // Try to return a user object
        return try {
            val snapshot = userRef.child(username).get().await()
            snapshot.getValue<User>()
        } catch (e: Exception) {
            null
        }
    }
}