package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.handmade.data.entities.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert
    suspend fun addToFavourite(fav: FavouriteEntity)

    @Delete
    suspend fun removeFromFavourite(fav: FavouriteEntity)

    @Query("SELECT * FROM favourites WHERE userId = :userId")
    suspend fun getUserFavourites(userId: Int): List<FavouriteEntity>

    @Query("SELECT * FROM favourites WHERE productId = :productId AND userId = :userId LIMIT 1")
    suspend fun isFavourite(productId: Int, userId: Int): FavouriteEntity?
}