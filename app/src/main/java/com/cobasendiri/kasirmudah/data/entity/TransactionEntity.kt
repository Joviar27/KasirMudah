package com.cobasendiri.kasirmudah.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,

    val name: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    val items: List<TransactionItem>,

    val total: Long
)

@Serializable
data class TransactionItem(
    val name: String,
    val count: Int,
    val total: Long
)