package com.cobasendiri.kasirmudah.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

object IdGenerator {

    fun generateProductId(): String{
        return try {
            val formattedDate = getDateForId()
            val randomUuid = UUID.randomUUID().toString()

            "$formattedDate-product-$randomUuid"
        }catch (e: Exception){
            "product-${UUID.randomUUID()}"
        }
    }

    fun generateTransactionId(): String{
        return try {
            val date = getDateForId()
            val randomUuid = UUID.randomUUID().toString()

            "${date.take(8)}$randomUuid${date.takeLast(6)}"
        }catch (e: Exception){
            UUID.randomUUID().toString()
        }
    }

    private fun getDateForId(): String{
        val currentDateTime = Calendar.getInstance().time

        val formatter = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US)
        return formatter.format(currentDateTime)
    }
}