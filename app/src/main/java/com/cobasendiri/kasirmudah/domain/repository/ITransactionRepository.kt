package com.cobasendiri.kasirmudah.domain.repository

import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo

interface ITransactionRepository {

    suspend fun insertNewTransaction(
        draftItems: List<TransactionItemInfo>,
        draftTotal: Long
    )
}