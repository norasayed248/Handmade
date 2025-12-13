package com.example.handmade

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        val navProfile = findViewById<ImageView>(R.id.navProfile)
        val navWishlist = findViewById<ImageView>(R.id.navWishlist)
        val navCart = findViewById<ImageView>(R.id.navCart)
        val navSearch = findViewById<ImageView>(R.id.navSearch)
        val navHome = findViewById<ImageView>(R.id.navHome)

        navHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        navWishlist.setOnClickListener {
            Toast.makeText(this, "You are already in Wishlist", Toast.LENGTH_SHORT).show()
        }

        navCart.setOnClickListener {
            Toast.makeText(this, "Cart not implemented yet", Toast.LENGTH_SHORT).show()
        }

        navSearch.setOnClickListener {
            Toast.makeText(this, "Search not implemented yet", Toast.LENGTH_SHORT).show()
        }

        val db = AppDatabase.getInstance(this)
        val repo = MainRepository(db)

        lifecycleScope.launch(Dispatchers.IO) {
            val user = repo.getUserByEmail("test@test.com")
            val userId = user?.id

            if (userId == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@WishlistActivity,
                        "No user found: test@test.com",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            val favs = repo.getUserFavourites(userId)
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@WishlistActivity,
                    "Wishlist items: ${favs.size}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
