package com.cobasendiri.kasirmudah.domain.exception

sealed class KasirMudahException() : Exception() {

    data object DatabaseError : KasirMudahException() {
        private fun readResolve(): Any = DatabaseError
    }

    data object StorageFullError : KasirMudahException() {
        private fun readResolve(): Any = StorageFullError
    }

    data class UnknownError(val originalMessage: String?) : KasirMudahException()
}
