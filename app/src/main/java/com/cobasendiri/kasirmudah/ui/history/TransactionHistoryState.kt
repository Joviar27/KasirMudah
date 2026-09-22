package com.cobasendiri.kasirmudah.ui.history

import com.cobasendiri.kasirmudah.domain.model.TransactionHistory
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage

data class TransactionHistoryState(
    val filter: TransactionFilter = TransactionFilter.FILTER_ALL,
    val transactionList: List<TransactionHistory> = listOf(),
    val uiMessage: UiMessage? = null,
    val showConfirmDeleteDialog: String? = null,
    val showEditDialog: Pair<String, String>? = null,
    val showEmptyListView: Boolean = false
)