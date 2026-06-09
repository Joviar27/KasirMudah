package com.cobasendiri.kasirmudah.data.converters

import androidx.room.TypeConverter
import com.cobasendiri.kasirmudah.data.entity.TransactionItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TransactionItemConverters {

    @TypeConverter
    fun fromRecordedItemList(value: List<TransactionItem>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toRecordedItemList(value: String?): List<TransactionItem>? {
        return value?.let { Json.decodeFromString(it) }
    }
}