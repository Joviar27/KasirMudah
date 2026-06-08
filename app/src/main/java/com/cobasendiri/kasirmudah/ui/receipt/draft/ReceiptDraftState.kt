package com.cobasendiri.kasirmudah.ui.receipt.draft

import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem

data class ReceiptDraftState(
    val shopName: String = "",
    val transactionShopItems: List<TransactionReceiptItem> = listOf(),
    val totalTransaction: Long = 0L,
)