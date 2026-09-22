package com.cobasendiri.kasirmudah.data.result

data class TransactionHistoryResult(
    val id: String,
    val name: String,
    val total: Long,
    val createdAt: Long,
    val isBookmarked: Boolean
)