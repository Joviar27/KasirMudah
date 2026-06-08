package com.cobasendiri.kasirmudah.ui.receipt

import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class ReceiptState(
    val previewMode: Boolean = false,
    val shopName: String = "",
    val transactionId: String = "",
    val transactionCreatedAt: String = "",
    val transactionShopItems: List<TransactionReceiptItem> = listOf(),
    val totalTransaction: Long = 0L,
    val showConfirmDeleteDialog: String? = null
)