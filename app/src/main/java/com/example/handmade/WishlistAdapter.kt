package com.example.handmade

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class LocalProduct(
    val name: String,
    val price: String,
    val imageRes: Int
)

class WishlistAdapter(
    private var items: List<LocalProduct>
) : RecyclerView.Adapter<WishlistAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.imgProduct)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val price: TextView = itemView.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wishlist, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.img.setImageResource(item.imageRes)
        holder.name.text = item.name
        holder.price.text = item.price
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<LocalProduct>) {
        items = newItems
        notifyDataSetChanged()
    }
}
