package com.example.fundraisinghome.model

import com.example.fundraisinghome.data.UserRepository
import com.example.fundraisinghome.ui.theme.CartViewModel
import java.security.MessageDigest

// UserSingleton.kt

object UserSingleton {
    private var currentUser: User? = null

    suspend fun loginUser(username: String, password: String, userRepository: UserRepository): Boolean {
        val user = userRepository.getUserByUsernameAndPassword(username, hashPassword(password))
        if (user != null) {
            setCurrentUser(user)
            return true // Login successful
        }
        return false // Login failed
    }

    fun logoutUser()  {
        currentUser = null
    }


    suspend fun registerUser(username: String, password: String, userRepository: UserRepository): Boolean {
        // Check if the username already exists
        val existingUser = userRepository.getUserByUsername(username)
        if (existingUser != null) {
            return false // User with the same username already exists
        }

        val newUser = User(username = username, hashedPassword = hashPassword(password))
        userRepository.addUser(newUser)
        setCurrentUser(userRepository.getUserByUsername(username))
        return true
    }

    // Hash the password using SHA-256 algorithm
    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // Function to get the current user
    fun getCurrentUser(): User? {
        return currentUser
    }
    fun setCurrentUser(user: User?) {
        currentUser = user
    }


}
