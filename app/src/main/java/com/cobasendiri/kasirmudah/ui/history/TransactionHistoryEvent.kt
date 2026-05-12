package com.cobasendiri.kasirmudah.ui.history

interface TransactionHistoryEvent {

    data class OnUpdateBookmark(
        val transactionId: String
    ): TransactionHistoryEvent

    data class OnDelete(
        val transactionId: String
    ): TransactionHistoryEvent

    data class OnShowConfirmDeleteDialog(
        val transactionId: String
    ): TransactionHistoryEvent

    data object OnDismissConfirmDeleteDialog: TransactionHistoryEvent
}