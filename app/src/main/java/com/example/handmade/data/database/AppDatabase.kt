package com.example.handmade.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.handmade.data.dao.FavouriteDao
import com.example.handmade.data.dao.ProductDao
import com.example.handmade.data.dao.UserDao
import com.example.handmade.data.entities.FavouriteEntity
import com.example.handmade.data.entities.ProductEntity
import com.example.handmade.data.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        ProductEntity::class,
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun favouriteDao(): FavouriteDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "handmade_db"    // اسم قاعدة البيانات
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}