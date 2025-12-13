package com.example.handmade

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page)

        val navPerson = findViewById<ImageView>(R.id.navPerson)
        val navWishlist = findViewById<ImageView>(R.id.navWishlist)
        val navCart = findViewById<ImageView>(R.id.navCart)
        val navSearch = findViewById<ImageView>(R.id.navSearch)

        navPerson.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        navWishlist.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }

        navCart.setOnClickListener {
            Toast.makeText(this, "Cart not implemented yet", Toast.LENGTH_SHORT).show()
        }

        navSearch.setOnClickListener {
            Toast.makeText(this, "Search not implemented yet", Toast.LENGTH_SHORT).show()
        }
    }
}
