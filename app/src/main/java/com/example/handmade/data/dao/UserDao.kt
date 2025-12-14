package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.handmade.data.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    // ✅ NEW: search by username (name)
    @Query("SELECT * FROM users WHERE name = :username AND password = :password LIMIT 1")
    suspend fun getUserByNameAndPassword(username: String, password: String): UserEntity?
}