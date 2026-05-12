package com.cobasendiri.kasirmudah.ui.history

data class TransactionHistoryState(
    val filter: TransactionFilter,
    val transactionList: List<TransactionItemState>,
    val showConfirmDeleteDialog: String?
)

data class TransactionItemState(
    val id: String,
    val name: String,
    val createdAt: String,
    val isBookmarked: Boolean
)