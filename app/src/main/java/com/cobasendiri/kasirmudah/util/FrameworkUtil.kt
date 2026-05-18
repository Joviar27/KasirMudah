package com.cobasendiri.kasirmudah.util

import com.cobasendiri.kasirmudah.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

suspend fun <T> runCatchSuspending(
    block: suspend () -> T
): Result<T> {
    return try {
        Result.Success(block())
    }catch (e: Exception){
        Result.Error(e.message.toString())
    }
}

fun <T>Flow<T>.mapCatchFlow(): Flow<Result<T>>{
    return this.map{
        Result.Success(it)
    }.catch {
        Result.Error(it.message.toString())
    }
}