package com.cobasendiri.kasirmudah.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cobasendiri.kasirmudah.data.converters.TransactionItemConverters
import com.cobasendiri.kasirmudah.data.entity.CartEntity
import com.cobasendiri.kasirmudah.data.entity.ProductEntity
import com.cobasendiri.kasirmudah.data.entity.TransactionBookmarkEntity
import com.cobasendiri.kasirmudah.data.entity.TransactionEntity

@Database(
    entities = [
        ProductEntity::class,
        CartEntity::class,
        TransactionEntity::class,
        TransactionBookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TransactionItemConverters::class)
abstract class ShopDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: ShopDatabase? = null

        fun getDatabase(context: Context): ShopDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShopDatabase::class.java,
                    "Shop.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}