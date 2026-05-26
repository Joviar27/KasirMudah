package com.cobasendiri.kasirmudah.domain

import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteException
import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException

fun Throwable.asKasirMudahException(): KasirMudahException{
    return when(this){
        is SQLiteDiskIOException -> KasirMudahException.StorageFullError
        is SQLiteException -> KasirMudahException.DatabaseError
        else -> KasirMudahException.UnknownError(this.message)
    }
}