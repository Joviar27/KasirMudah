package com.cobasendiri.kasirmudah.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cobasendiri.kasirmudah.data.entity.CartEntity
import com.cobasendiri.kasirmudah.data.result.ProductResult
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("""
        SELECT p.*, c.count
        FROM products as p
        INNER JOIN carts as c ON p.id = c.productId
    """)
    fun getAllCartProducts(): Flow<List<ProductResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(cart: CartEntity): Long

    @Query("UPDATE carts SET count = count + 1 WHERE productId = :productId ")
    suspend fun incrementProduct(productId: String)

    @Transaction
    suspend fun addOrIncrementProduct(productId: String){
        val inserted = addToCart(CartEntity(productId, 1))
        if(inserted == -1L){
            incrementProduct(productId)
        }
    }

    @Query("UPDATE carts SET count = MAX(0, count - 1) WHERE productId = :productId ")
    suspend fun decrementProduct(productId: String)

    @Query("DELETE FROM carts WHERE productId = :productId AND count <= 0")
    suspend fun removeFromCartIfCountZero(productId: String)

    @Transaction
    suspend fun decrementOrRemoveProduct(productId: String){
        decrementProduct(productId)
        removeFromCartIfCountZero(productId)
    }

    @Query("""
        SELECT SUM (p.price * c.count)
        FROM products AS p
        INNER JOIN carts AS C ON p.id = c.productId
    """)
    fun getTotalCartAmount(): Flow<Long?>

    @Query("DELETE FROM carts")
    suspend fun deleteAllCart()
}