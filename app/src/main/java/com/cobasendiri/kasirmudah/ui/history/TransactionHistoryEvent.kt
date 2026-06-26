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

    data class OnFilterChange(
        val newFilter: TransactionFilter
    ): TransactionHistoryEvent

    data class OnNavigateToDetail(
        val transactionId: String
    ): TransactionHistoryEvent

    data class OnShowEditDialog(
        val transactionId: String,
        val name: String,
    ): TransactionHistoryEvent

    data object OnDismissEditDialog: TransactionHistoryEvent

    data class OnUpdateName(
        val transactionId: String,
        val updatedName: String
    ): TransactionHistoryEvent
}