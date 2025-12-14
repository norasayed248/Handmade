package com.example.handmade.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val userId: Int
)

