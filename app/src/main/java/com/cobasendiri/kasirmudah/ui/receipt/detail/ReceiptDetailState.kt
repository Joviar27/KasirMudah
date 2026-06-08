package com.cobasendiri.kasirmudah.ui.receipt.detail

import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem

data class ReceiptDetailState(
    val shopName: String = "",
    val transactionId: String = "",
    val transactionCreatedAt: String = "",
    val transactionShopItems: List<TransactionReceiptItem> = listOf(),
    val totalTransaction: Long = 0L,
    val isBookmarked: Boolean = false,
    val showConfirmDeleteDialog: String? = null,
)