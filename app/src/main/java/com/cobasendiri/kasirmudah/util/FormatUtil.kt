package com.cobasendiri.kasirmudah.util

fun String.decimalFormat(): String{
    return this.replace(Regex("(?<=\\d)(?=(\\d{3})+(?!\\d))"), ".")
}

fun String.rawFormat(): String{
    return this.replace(".", "")
}