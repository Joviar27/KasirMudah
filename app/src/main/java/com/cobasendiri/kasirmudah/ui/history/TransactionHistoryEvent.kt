package com.cobasendiri.kasirmudah.ui.history

import com.cobasendiri.kasirmudah.ui.shop.ShopEvent
import com.cobasendiri.kasirmudah.ui.shop.ShopFilter

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
}