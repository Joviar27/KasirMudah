package com.cobasendiri.kasirmudah.domain.model

data class Receipt(
    val id: String,
    val name: String, //Not displayed on receipt page yet
    val createdAt: String,
    val shopItems: List<ReceiptItem>,
    val totalTransaction: String,
)

data class ReceiptItem(
    val itemName: String,
    val count: Int,
    val totalPrice: String
)