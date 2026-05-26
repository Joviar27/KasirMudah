package com.cobasendiri.kasirmudah.data

sealed class Result<out T> private constructor() {
    data class Success<out T>(val data : T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}