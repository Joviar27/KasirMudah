package com.cobasendiri.kasirmudah.ui.receipt.detail

import android.graphics.Bitmap

interface ReceiptDetailEvent {

    data class OnDelete(
        val transactionId: String
    ): ReceiptDetailEvent

    data object OnDismissConfirmDeleteDialog: ReceiptDetailEvent

    data class OnDownload(
        val receiptBitmap: Bitmap,
        val fileName: String
    ): ReceiptDetailEvent
}