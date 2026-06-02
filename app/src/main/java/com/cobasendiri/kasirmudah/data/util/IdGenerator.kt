package com.cobasendiri.kasirmudah.data.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

object IdGenerator {

    fun generateProductId(): String{
        return try {
            val currentDateTime = Calendar.getInstance().time

            val formatter = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US)
            val formattedDate = formatter.format(currentDateTime)

            val randomUuid = UUID.randomUUID().toString()

            "$formattedDate-product-$randomUuid"
        }catch (e: Exception){
            "product-${UUID.randomUUID()}"
        }
    }
}