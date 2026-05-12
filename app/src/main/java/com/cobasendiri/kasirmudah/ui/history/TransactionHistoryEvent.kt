package com.cobasendiri.kasirmudah.ui.history

interface TransactionHistoryEvent {

    data class OnBookmark(
        val transactionId: String
    ): TransactionHistoryEvent

    data class OnDelete(
        val transactionId: String
    ): TransactionHistoryEvent
}