package com.cobasendiri.kasirmudah.data.result

import androidx.room.Embedded
import com.cobasendiri.kasirmudah.data.entity.TransactionEntity

data class TransactionResult(
    @Embedded
    val transaction: TransactionEntity,
    val isBookmarked: Boolean
)
