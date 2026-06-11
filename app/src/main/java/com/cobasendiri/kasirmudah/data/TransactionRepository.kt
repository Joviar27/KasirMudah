package com.cobasendiri.kasirmudah.data

import com.cobasendiri.kasirmudah.data.entity.TransactionEntity
import com.cobasendiri.kasirmudah.data.entity.TransactionEntityItem
import com.cobasendiri.kasirmudah.data.room.TransactionDao
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.mapExceptionFlow
import com.cobasendiri.kasirmudah.data.util.IdGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cobasendiri.kasirmudah.data.util.CoroutineMapper.runMapExceptionSuspending
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapListToDomain
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapToTransactionReceipt
import com.cobasendiri.kasirmudah.data.util.DataMapper.mapTransactionHistoryToDomain
import com.cobasendiri.kasirmudah.domain.model.TransactionHistory
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

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
        draftItems: List<TransactionItemInfo>,
        draftTotal: Long
    ) = withContext(ioDispatcher){
        runMapExceptionSuspending {
            val id = IdGenerator.generateTransactionId()
            val name = "Transaksi ${id.take(10)}..."
            val items = draftItems.map {
                TransactionEntityItem(
                    name = it.name,
                    count = it.count,
                    total = it.itemTotal
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

    override suspend fun deleteTransaction(
        transactionId: String
    ) = withContext(ioDispatcher) {
        runMapExceptionSuspending {
            transactionDao.deleteTransaction(transactionId)
        }
    }

    override fun getTransactionHistory(timestampFilter: Long): Flow<List<TransactionHistory>?> {
        return transactionDao.getTransactionHistory(timestampFilter).mapExceptionFlow {
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override suspend fun getTransaction(transactionId: String): TransactionReceipt {
        return withContext(ioDispatcher) {
            runMapExceptionSuspending {
                transactionDao.getTransaction(transactionId).mapToTransactionReceipt()
            }
        }
    }

    override fun getBookmarkedTransaction(): Flow<List<TransactionHistory>?> {
        return transactionDao.getBookmarkedTransaction().mapExceptionFlow{
            it.mapTransactionHistoryToDomain()
        }.flowOn(ioDispatcher)
    }

    override suspend fun updateBookmark(transactionId: String): Boolean {
        return withContext(ioDispatcher){
            runMapExceptionSuspending {
                transactionDao.updateBookmark(
                    transactionId,
                    System.currentTimeMillis()/1000
                )
            }
        }
    }
}