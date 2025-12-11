package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.handmade.data.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
}