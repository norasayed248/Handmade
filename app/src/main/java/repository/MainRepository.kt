package com.example.handmade.data.repository

import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.entities.FavouriteEntity
import com.example.handmade.data.entities.ProductEntity
import com.example.handmade.data.entities.UserEntity

class MainRepository(private val db: AppDatabase) {

    // =========================
    // USERS
    // =========================

    // 🔒 LOGIN (username + password فقط)
    suspend fun login(username: String, password: String): UserEntity? {
        return db.userDao().login(username, password)
    }

    // ✍️ SIGNUP (username + email + password)
    suspend fun signup(username: String, email: String, password: String): Boolean {

        // username متكرر؟
        if (db.userDao().getUserByName(username) != null) return false

        // email متكرر؟
        if (db.userDao().getUserByEmail(email) != null) return false

        db.userDao().insertUser(
            UserEntity(
                name = username,
                email = email,
                password = password
            )
        )
        return true
    }

    // =========================
    // PRODUCTS
    // =========================
    suspend fun insertProduct(product: ProductEntity) {
        db.productDao().insertProduct(product)
    }

    suspend fun getAllProducts(): List<ProductEntity> {
        return db.productDao().getAllProducts()
    }

    suspend fun getProductById(id: Int): ProductEntity? {
        return db.productDao().getProductById(id)
    }

    // =========================
    // FAVOURITES
    // =========================
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
