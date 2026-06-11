package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.data.entity.TransactionEntity
import com.cobasendiri.kasirmudah.domain.model.TransactionHistory
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository {

    suspend fun insertNewTransaction(
        draftItems: List<TransactionItemInfo>,
        draftTotal: Long
    )

    suspend fun deleteTransaction(transactionId: String)

    fun getTransactionHistory(
        timestampFilter: Long
    ): Flow<List<TransactionHistory>?>

    suspend fun getTransaction(transactionId: String): TransactionReceipt

    fun getBookmarkedTransaction(): Flow<List<TransactionHistory>?>

    suspend fun updateBookmark(transactionId: String): Boolean

}