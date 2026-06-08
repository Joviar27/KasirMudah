package com.cobasendiri.kasirmudah.ui.receipt.draft

interface ReceiptDraftEvent {

    data object OnNavigateBack: ReceiptDraftEvent

    data object OnSave: ReceiptDraftEvent
}