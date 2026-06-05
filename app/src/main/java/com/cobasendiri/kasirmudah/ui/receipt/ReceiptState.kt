package com.cobasendiri.kasirmudah.ui.receipt

import com.cobasendiri.kasirmudah.domain.model.ReceiptItem

data class ReceiptState(
    val previewMode: Boolean = false,
    val shopName: String = "",
    val transactionId: String = "",
    val transactionCreatedAt: String = "",
    val transactionShopItems: List<ReceiptItem> = listOf(),
    val totalTransaction: String = "",
    val showConfirmDeleteDialog: String? = null
)