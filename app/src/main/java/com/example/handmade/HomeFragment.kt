package com.example.handmade

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.entities.FavouriteEntity
import com.example.handmade.data.repository.MainRepository
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val repo = MainRepository(db)

        val prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)

        val btnFav1 = view.findViewById<ImageView>(R.id.btnFav1)
        val btnFav2 = view.findViewById<ImageView>(R.id.btnFav2)
        val btnFav3 = view.findViewById<ImageView>(R.id.btnFav3)

        if (userId != -1) {
            viewLifecycleOwner.lifecycleScope.launch {
                updateHeartIcon(repo, btnFav1, productId = 1, userId = userId)
                updateHeartIcon(repo, btnFav2, productId = 2, userId = userId)
                updateHeartIcon(repo, btnFav3, productId = 3, userId = userId)
            }
        } else {
            // لو مش عامل login، خليهم فاضيين
            btnFav1.setImageResource(R.drawable.favourite)
            btnFav2.setImageResource(R.drawable.favourite)
            btnFav3.setImageResource(R.drawable.favourite)
        }

        bindToggle(repo, btnFav1, productId = 1, userId = userId)
        bindToggle(repo, btnFav2, productId = 2, userId = userId)
        bindToggle(repo, btnFav3, productId = 3, userId = userId)
    }

    private fun bindToggle(
        repo: MainRepository,
        btn: ImageView,
        productId: Int,
        userId: Int
    ) {
        btn.setOnClickListener {
            if (userId == -1) {
                Toast.makeText(requireContext(), "Please login first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val existing = repo.isFavourite(productId, userId)

                if (existing == null) {
                    repo.addToFavourite(FavouriteEntity(userId = userId, productId = productId))
                    btn.setImageResource(R.drawable.favoriteda) // filled
                    Toast.makeText(requireContext(), "Added to wishlist", Toast.LENGTH_SHORT).show()
                } else {
                    repo.removeFromFavourite(existing)
                    btn.setImageResource(R.drawable.favourite) // empty
                    Toast.makeText(requireContext(), "Removed from wishlist", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun updateHeartIcon(
        repo: MainRepository,
        btn: ImageView,
        productId: Int,
        userId: Int
    ) {
        val existing = repo.isFavourite(productId, userId)
        if (existing == null) {
            btn.setImageResource(R.drawable.favourite) // empty
        } else {
            btn.setImageResource(R.drawable.favoriteda) // filled
        }
    }
}
