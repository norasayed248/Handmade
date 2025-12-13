package com.example.handmade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import com.example.handmade.data.entities.UserEntity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)



        val db = AppDatabase.getInstance(this)
        val repo = MainRepository(db)

        lifecycleScope.launch {


            repo.insertUser(
                UserEntity(
                    name = "Test User",
                    email = "test@test.com",
                    password = "1234"
                )
            )

            // قراءة يوزر
            val user = repo.getUserByEmail("test@test.com")

            println("✔ DATABASE WORKING — USER: $user")
        }
    }
}
