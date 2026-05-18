package com.cobasendiri.kasirmudah.util

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDiskIOException
import com.cobasendiri.kasirmudah.domain.exception.DomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.sql.SQLException

suspend fun <T> runMapExceptionSuspending(
    block: suspend () -> T
): T {
    return try {
        block()
    }catch (e: Exception){
        throw e.toDomainException()
    }
}

fun <U,T>Flow<U>.mapExceptionFlow(
    dataMapping: (U) -> T
): Flow<T>{
    return this.map{
        dataMapping(it)
    }.catch {
        throw it.toDomainException()
    }
}

fun <T> Flow<T>.mapExceptionFlow(): Flow<T> {
    return this.catch { throw it.toDomainException() }
}

fun Throwable.toDomainException(): DomainException{
    return when(this){
        is SQLiteConstraintException -> DomainException.DataAlreadyExist
        is SQLiteDiskIOException -> DomainException.StorageFullError
        is SQLException -> DomainException.DatabaseError
        else -> DomainException.UnknownError(this.message)
    }
}