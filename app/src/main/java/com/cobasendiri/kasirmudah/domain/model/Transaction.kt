package com.cobasendiri.kasirmudah.domain.model

data class Transaction(
    val id: String,
    val name: String,
    val createdAt: String,
    val shopItems: List<TransactionShopItem>
)

data class TransactionShopItem(
    val itemName: String,
    val totalPrice: String
)