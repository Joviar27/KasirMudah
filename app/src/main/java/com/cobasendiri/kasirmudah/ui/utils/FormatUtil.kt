package com.cobasendiri.kasirmudah.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object FormatUtil{
    fun String.decimalFormat(): String{
        return this.replace(Regex("(?<=\\d)(?=(\\d{3})+(?!\\d))"), ".")
    }

    fun String.rawFormat(): String{
        return this.replace(".", "")
    }

    fun Long.dateFormat(): String{
        return try {
            val date = Date(this * 1000)
            val formatter = SimpleDateFormat("dd MMMM yyyy - HH:mm:ss", Locale("id", "ID"))
            formatter.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
            return formatter.format(date)
        }catch (e: Exception){
            "Invalid date"
        }
    }
}
