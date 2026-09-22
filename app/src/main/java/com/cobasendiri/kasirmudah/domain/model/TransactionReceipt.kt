package com.cobasendiri.kasirmudah.domain.model

data class TransactionReceipt(
    val id: String,
    val name: String,
    val shopName: String,
    val createdAt: Long,
    val shopItems: List<TransactionItemInfo>,
    val transactionTotal: Long,
)