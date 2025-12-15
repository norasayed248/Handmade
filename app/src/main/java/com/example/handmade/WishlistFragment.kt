package com.example.handmade

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    private lateinit var adapter: WishlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_wishlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        val rv = view.findViewById<RecyclerView>(R.id.rvWishlist)
        adapter = WishlistAdapter(emptyList())
        rv.adapter = adapter

        if (userId == -1) {
            // لو عندك TextView في التصميم للرسائل يبقى استخدميه، غير كده هسيبها Toast عندك
            // هنا مثال بسيط:
            view.findViewById<TextView?>(R.id.tvWishlistTitle)?.append(" (Login required)")
            return
        }

        val repo = MainRepository(AppDatabase.getInstance(requireContext()))

        viewLifecycleOwner.lifecycleScope.launch {
            val favs = repo.getUserFavourites(userId)

            if (favs.isEmpty()) {
                // نفس الفكرة: لو عندك empty state text حطيه
                // أو اعملي list فاضية وخلاص
                adapter.submitList(emptyList())
                return@launch
            }

            val products = favs.mapNotNull { fav ->
                localProductById(fav.productId)
            }

            adapter.submitList(products)
        }
    }

    private fun localProductById(id: Int): LocalProduct? {
        return when (id) {
            1 -> LocalProduct("Choker Monica", "EGP 1499.00", R.drawable.neck1)
            2 -> LocalProduct("Ring Heart", "EGP 1299.00", R.drawable.ring1)
            3 -> LocalProduct("Earrings Luna", "EGP 999.00", R.drawable.ear1)
            else -> null
        }
    }
}
