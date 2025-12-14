package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.handmade.data.entities.FavouriteEntity
import com.example.handmade.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow


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



        @Query("SELECT * FROM favourites WHERE userId = :userId")
        fun observeUserFavourites(userId: Int): Flow<List<FavouriteEntity>>
    @Query("""
    SELECT p.*
    FROM products p
    INNER JOIN favourites f ON f.productId = p.id
    WHERE f.userId = :userId
""")
    fun observeWishlistProducts(userId: Int): kotlinx.coroutines.flow.Flow<List<ProductEntity>>

}
