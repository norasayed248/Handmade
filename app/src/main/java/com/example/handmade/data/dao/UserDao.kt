package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.handmade.data.entities.UserEntity

@Dao
interface UserDao {

    // =====================
    // INSERT
    // =====================
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    // =====================
    // CHECK USERNAME
    // =====================
    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    suspend fun getUserByName(name: String): UserEntity?

    // =====================
    // CHECK EMAIL (Signup فقط)
    // =====================
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    // =====================
    // LOGIN (Username + Password فقط)
    // =====================
    @Query("""
        SELECT * FROM users
        WHERE name = :username
        AND password = :password
        LIMIT 1
    """)
    suspend fun login(username: String, password: String): UserEntity?
}
