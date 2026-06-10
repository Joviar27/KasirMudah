package com.cobasendiri.kasirmudah.domain.model

data class TransactionHistory(
    val id: String,
    val name: String,
    val total: Long,
    val createdAt: Long,
    val isBookmarked: Boolean
)