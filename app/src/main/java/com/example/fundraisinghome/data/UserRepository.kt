package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import com.example.fundraisinghome.model.User
class UserRepository(private val userDao: UserDao) {


    suspend fun addUser(user: User){
        userDao.addUser(user)
    }


    // Get user by username and password (using hashed password)
    suspend fun getUserByUsernameAndPassword(username: String, hassedPassword: String): User? {
        return userDao.getUserByUsernameAndPassword(username, hassedPassword)
    }

    // Get user by username
    suspend fun getUserByUsername(username:String):User? {
        return userDao.getUserByUsername(username)
    }



}