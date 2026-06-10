package com.cobasendiri.kasirmudah.ui.receipt.detail

import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo

data class ReceiptDetailState(
    val shopName: String = "",
    val transactionId: String = "",
    val transactionCreatedAt: String = "",
    val transactionShopItems: List<TransactionItemInfo> = listOf(),
    val totalTransaction: Long = 0L,
    val isBookmarked: Boolean = false,
    val showConfirmDeleteDialog: String? = null,
)