package com.example.handmade.data.repository

import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.entities.FavouriteEntity
import com.example.handmade.data.entities.ProductEntity
import com.example.handmade.data.entities.UserEntity

class MainRepository(private val db: AppDatabase) {

    // -------------------------
    // Users (USERNAME + PASSWORD)
    // -------------------------

    suspend fun insertUser(user: UserEntity) {
        db.userDao().insertUser(user)
    }

    suspend fun getUserByName(name: String): UserEntity? {
        return db.userDao().getUserByName(name)
    }

    // ✅ Login by username + password
    suspend fun login(username: String, password: String): UserEntity? {
        return db.userDao().getUserByName(username)
    }

    // ✅ Signup by username (من غير تكرار)
    suspend fun signup(user: UserEntity): Boolean {
        val existing = db.userDao().getUserByName(user.name)
        if (existing != null) return false
        db.userDao().insertUser(user)
        return true
    }

    // -------------------------
    // Products
    // -------------------------
    suspend fun insertProduct(product: ProductEntity) {
        db.productDao().insertProduct(product)
    }

    suspend fun getAllProducts(): List<ProductEntity> {
        return db.productDao().getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return db.productDao().getProductById(id)
    }

    // -------------------------
    // Favourites
    // -------------------------
    suspend fun addToFavourite(fav: FavouriteEntity) {
        db.favouriteDao().addToFavourite(fav)
    }

    suspend fun removeFromFavourite(fav: FavouriteEntity) {
        db.favouriteDao().removeFromFavourite(fav)
    }

    suspend fun isFavourite(productId: Int, userId: Int): FavouriteEntity? {
        return db.favouriteDao().isFavourite(productId, userId)
    }

    suspend fun getUserFavourites(userId: Int): List<FavouriteEntity> {
        return db.favouriteDao().getUserFavourites(userId)
    }
}