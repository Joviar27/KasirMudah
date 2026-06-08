package com.cobasendiri.kasirmudah.ui.receipt.detail

interface ReceiptDetailEvent {

    data object OnNavigateBack: ReceiptDetailEvent

    data class OnUpdateBookmark(
        val transactionId: String
    ): ReceiptDetailEvent

    data class OnDelete(
        val transactionId: String
    ): ReceiptDetailEvent

    data class OnShowConfirmDeleteDialog(
        val transactionId: String
    ): ReceiptDetailEvent

    data object OnDismissConfirmDeleteDialog: ReceiptDetailEvent

    data object OnDownload: ReceiptDetailEvent
}