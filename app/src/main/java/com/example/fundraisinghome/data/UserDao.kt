package com.example.fundraisinghome.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundraisinghome.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)


    @Query("Select * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>


    @Query("SELECT * FROM user_table WHERE username = :username AND hashedPassword = :hashedPassword LIMIT 1")
    suspend fun getUserByUsernameAndPassword(username: String, hashedPassword: String): User?

    @Query("SELECT * FROM user_table WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?




}