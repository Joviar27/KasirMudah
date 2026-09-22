package com.cobasendiri.kasirmudah.data.converters

import androidx.room.TypeConverter
import com.cobasendiri.kasirmudah.data.entity.TransactionEntityItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TransactionItemConverters {

    @TypeConverter
    fun fromRecordedItemList(value: List<TransactionEntityItem>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toRecordedItemList(value: String?): List<TransactionEntityItem>? {
        return value?.let { Json.decodeFromString(it) }
    }
}