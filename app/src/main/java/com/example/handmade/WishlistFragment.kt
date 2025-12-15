package com.example.handmade

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ✅ خليها نفس اسم ملف الـ XML بتاع الويشليست عندك
        return inflater.inflate(R.layout.activity_wishlist, container, false)
        // لو ملفك اسمه settings_wishlist مثلا غيره هنا
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val userId = prefs.getInt("userId", -1)

        // ✅ ده الـ id اللي عندك في الـ XML
        val ll = view.findViewById<LinearLayout>(R.id.llWishlistDynamic)

        ll.removeAllViews()

        if (userId == -1) {
            ll.addView(TextView(requireContext()).apply {
                text = "Please login first"
                textSize = 18f
            })
            return
        }

        val repo = MainRepository(AppDatabase.getInstance(requireContext()))

        viewLifecycleOwner.lifecycleScope.launch {
            val favs = repo.getUserFavourites(userId)

            ll.removeAllViews()

            if (favs.isEmpty()) {
                ll.addView(TextView(requireContext()).apply {
                    text = "Wishlist is empty"
                    textSize = 18f
                })
                return@launch
            }

            favs.forEach { fav ->
                val p = localProductById(fav.productId) ?: return@forEach
                ll.addView(makeCard(p))
            }
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

    private fun makeCard(p: LocalProduct): View {
        val card = CardView(requireContext()).apply {
            radius = 0f
            cardElevation = 8f
            useCompatPadding = true
            layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 16) }
        }

        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
        }

        val img = ImageView(requireContext()).apply {
            setImageResource(p.imageRes)
            layoutParams = LinearLayout.LayoutParams(220, 220)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val col = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
            ).apply { setMargins(16, 0, 0, 0) }
        }

        val name = TextView(requireContext()).apply {
            text = p.name
            textSize = 18f
        }

        val price = TextView(requireContext()).apply {
            text = p.price
            textSize = 16f
        }

        col.addView(name)
        col.addView(price)

        row.addView(img)
        row.addView(col)

        card.addView(row)
        return card
    }

    data class LocalProduct(
        val name: String,
        val price: String,
        val imageRes: Int
    )
}
