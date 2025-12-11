package com.example.handmade.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.handmade.data.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :productId LIMIT 1")
    suspend fun getProductById(productId: Int): ProductEntity?
}