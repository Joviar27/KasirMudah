package com.cobasendiri.kasirmudah.ui.receipt

interface ReceiptEvent {

    data object OnNavigateBack: ReceiptEvent

    data class OnUpdateBookmark(
        val transactionId: String
    ): ReceiptEvent

    data class OnDelete(
        val transactionId: String
    ): ReceiptEvent

    data class OnShowConfirmDeleteDialog(
        val transactionId: String
    ): ReceiptEvent

    data object OnSave: ReceiptEvent

    data object OnDownload: ReceiptEvent
}