package com.cobasendiri.kasirmudah.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cobasendiri.kasirmudah.data.entity.ProductEntity
import com.cobasendiri.kasirmudah.data.result.ProductResult
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("""
        SELECT p.*, IFNULL(c.count,0) as count
        FROM products as p
        LEFT JOIN carts as c ON p.id = c.productId
    """)
    fun getAllProducts() : Flow<List<ProductResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("UPDATE products SET colorCode = :newColor WHERE id = :productId")
    suspend fun updateProductColorCode(productId: String, newColor: Long)

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteProduct(id: String)
}