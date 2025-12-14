package com.example.handmade

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.activity_wishlist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)

        val ll = view.findViewById<LinearLayout>(R.id.llWishlistDynamic)

        val db = AppDatabase.getInstance(requireContext())
        val repo = MainRepository(db)

        if (userId == -1) {
            ll.removeAllViews()
            ll.addView(TextView(requireContext()).apply { text = "Please login first" })
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {

            val favList = repo.getUserFavourites(userId)

            ll.removeAllViews()

            if (favList.isEmpty()) {
                ll.addView(TextView(requireContext()).apply { text = "Wishlist is empty" })
                return@launch
            }

            favList.forEach { fav ->
                ll.addView(TextView(requireContext()).apply {
                    text = "ProductId: ${fav.productId}"   // لو productId أحمر شوفي تحت
                    textSize = 16f
                })
            }
        }

    }
}

