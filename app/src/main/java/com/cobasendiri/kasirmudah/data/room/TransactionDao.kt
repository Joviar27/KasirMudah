package com.cobasendiri.kasirmudah.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cobasendiri.kasirmudah.data.entity.TransactionBookmarkEntity
import com.cobasendiri.kasirmudah.data.entity.TransactionEntity
import com.cobasendiri.kasirmudah.data.result.TransactionHistoryResult
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTransaction(transaction: TransactionEntity)

    @Query(
        """
        SELECT t.id, t.name, t.total, t.created_at as createdAt, (b.transaction_id IS NOT NULL) as isBookmarked
        FROM transactions as t
        LEFT JOIN transaction_bookmark as b ON t.id = b.transaction_id
        WHERE t.created_at >= :timestampFilter
        ORDER BY t.created_at DESC
    """)
    fun getTransactionHistory(timestampFilter: Long): Flow<List<TransactionHistoryResult>>

    @Query(
        """
        SELECT t.id, t.name, t.total, t.created_at as createdAt, 1 as isBookmarked
        FROM transactions as t
        INNER JOIN transaction_bookmark as b ON t.id = b.transaction_id
        ORDER BY b.bookmarked_at DESC
    """)
    fun getBookmarkedTransaction(): Flow<List<TransactionHistoryResult>>

    @Query("DELETE FROM transactions WHERE id = :transactionId")
    fun deleteTransaction(transactionId: String)

    @Query("""
        SELECT t.*, (b.transaction_id IS NOT NULL) as isBookmarked
        FROM transactions as t
        LEFT JOIN transaction_bookmark as b ON t.id = b.transaction_id
        WHERE t.id = :transactionId
    """)
    suspend fun getTransaction(transactionId: String): TransactionEntity

    @Query("SELECT EXISTS(SELECT 1 FROM transaction_bookmark WHERE transaction_id = :transactionId)")
    suspend fun isBookmarked(transactionId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookmark(bookmark: TransactionBookmarkEntity)

    @Query("DELETE FROM transaction_bookmark WHERE transaction_id = :transactionId")
    suspend fun removeBookmark(transactionId: String)

    @Transaction
    suspend fun updateBookmark(transactionId: String, timestamp: Long): Boolean{
        return if(isBookmarked(transactionId)){
            removeBookmark(transactionId)
            false
        }else{
            addBookmark(TransactionBookmarkEntity(transactionId, timestamp))
            true
        }
    }
}