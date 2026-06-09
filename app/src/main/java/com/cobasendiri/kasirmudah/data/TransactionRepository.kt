package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.entity.TransactionEntity
import com.cobasendiri.kasirmudah.data.entity.TransactionItem
import com.cobasendiri.kasirmudah.data.room.TransactionDao
import com.cobasendiri.kasirmudah.data.util.IdGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ITransactionRepository {

    companion object {
        @Volatile
        private var instance: ITransactionRepository? = null

        fun getInstance(transactionDao: TransactionDao): ITransactionRepository {
            return instance ?: synchronized(this) {
                instance ?: TransactionRepository(transactionDao)
                    .also { instance = it }
            }
        }
    }

    override suspend fun insertNewTransaction(
        draftItems: List<TransactionReceiptItem>,
        draftTotal: Long
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            val id = IdGenerator.generateTransactionId()
            val name = "Transaksi ${id.take(10)}..."
            val items = draftItems.map {
                TransactionItem(
                    name = it.name,
                    count = it.count,
                    total = it.totalAmount
                )
            }

            val transaction = TransactionEntity(
                id = id,
                name = name,
                createdAt = System.currentTimeMillis()/1000,
                items = items,
                total = draftTotal
            )

            transactionDao.insertNewTransaction(transaction)
        }
    }
}