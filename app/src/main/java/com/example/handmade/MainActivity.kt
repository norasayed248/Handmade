package com.example.handmade

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.entities.UserEntity
import com.example.handmade.data.repository.MainRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // ===== Room test (زي ما هو) =====
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

            val user = repo.getUserByEmail("test@test.com")
            println("✔ DATABASE WORKING — USER: $user")
        }
        // ===============================

        // ===== Navigation =====
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.visibility = when (destination.id) {
                R.id.loginFragment, R.id.signupFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
        // ======================
    }
}
