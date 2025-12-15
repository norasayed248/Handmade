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
    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String): UserEntity?
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

}