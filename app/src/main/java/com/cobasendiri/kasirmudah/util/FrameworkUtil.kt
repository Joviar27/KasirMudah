package com.cobasendiri.kasirmudah.util

import com.cobasendiri.kasirmudah.domain.asKasirMudahException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

suspend fun <T> runMapExceptionSuspending(
    block: suspend () -> T
): T {
    return try {
        block()
    }catch (e: Exception){
        throw e.asKasirMudahException()
    }
}

fun <U,T>Flow<U>.mapExceptionFlow(
    dataMapping: (U) -> T
): Flow<T>{
    return this.map{
        dataMapping(it)
    }.catch {
        throw it.asKasirMudahException()
    }
}

fun <T> Flow<T>.mapExceptionFlow(): Flow<T> {
    return this.catch { throw it.asKasirMudahException() }
}