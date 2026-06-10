package com.cobasendiri.kasirmudah.domain.model

data class TransactionReceipt(
    val id: String,
    val createdAt: String,
    val shopItems: List<TransactionItemInfo>,
    val transactionTotal: Long,
)