package com.cobasendiri.kasirmudah.ui.receipt.draft

import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class ReceiptDraftState(
    val shopName: String = "",
    val transactionShopItems: List<TransactionItemInfo> = listOf(),
    val totalTransaction: Long = 0L,
    val uiMessage: UiMessage? = null
)