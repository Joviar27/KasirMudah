package com.cobasendiri.kasirmudah.data.util

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException

object ExceptionMapper {

    fun Throwable.asKasirMudahException(): KasirMudahException{
        return when(this){
            is SQLiteDiskIOException -> KasirMudahException.StorageFullError
            is SQLiteException -> KasirMudahException.DatabaseError
            else -> KasirMudahException.UnknownError(this.message)
        }
    }
}