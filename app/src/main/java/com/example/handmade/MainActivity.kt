package com.example.handmade

import android.os.Bundle
import android.util.Log
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



        // ===== Navigation =====
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment

        if (navHostFragment == null) {
            Log.e("NAV", "nav_host_fragment not found in activity_main.xml")
            return
        }

        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView?>(R.id.bottom_nav)
        if (bottomNav == null) {
            Log.e("NAV", "bottom_nav not found in activity_main.xml")
            return
        }

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNav.visibility = when (destination.id) {
                R.id.loginFragment, R.id.signupFragment ,R.id.SplashFragment ,-> View.GONE
                else -> View.VISIBLE
            }
        }
        // ======================
    }
}