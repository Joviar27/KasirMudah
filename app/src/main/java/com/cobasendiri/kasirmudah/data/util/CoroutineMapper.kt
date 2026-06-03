package com.cobasendiri.kasirmudah.data.util

import com.cobasendiri.kasirmudah.data.util.ExceptionMapper.asKasirMudahException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

object CoroutineMapper{
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
    ): Flow<T?>{
        return this.map{
            try {
                dataMapping(it)
            }catch (e: Throwable){
                //Catch operation logic, stream continue
                null
            }
        }.catch {
            //Catch data source error, stream cancelled
            throw it.asKasirMudahException()
        }
    }

    fun <T> Flow<T>.mapExceptionFlow(): Flow<T> {
        return this.catch { throw it.asKasirMudahException() }
    }
}
