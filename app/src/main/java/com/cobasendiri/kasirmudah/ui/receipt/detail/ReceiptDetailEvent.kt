package com.cobasendiri.kasirmudah.ui.receipt.detail

interface ReceiptDetailEvent {

    data class OnDelete(
        val transactionId: String
    ): ReceiptDetailEvent

    data object OnDismissConfirmDeleteDialog: ReceiptDetailEvent

    data object OnDownload: ReceiptDetailEvent
}