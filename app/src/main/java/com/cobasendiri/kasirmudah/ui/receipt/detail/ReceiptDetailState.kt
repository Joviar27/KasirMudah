package com.cobasendiri.kasirmudah.ui.receipt.detail

import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class ReceiptDetailState(
    val shopName: String = "",
    val transactionName: String = "",
    val transactionId: String = "",
    val transactionCreatedAt: Long = 0L,
    val transactionShopItems: List<TransactionItemInfo> = listOf(),
    val totalTransaction: Long = 0L,
    val isBookmarked: Boolean = false,
    val uiMessage: UiMessage? = null,
    val showConfirmDeleteDialog: String? = null,
    val processing: Boolean = true
)