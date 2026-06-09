package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem

interface ITransactionRepository {

    suspend fun insertNewTransaction(
        draftItems: List<TransactionReceiptItem>,
        draftTotal: Long
    )
}