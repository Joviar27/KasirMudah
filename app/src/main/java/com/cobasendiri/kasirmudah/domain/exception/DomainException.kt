package com.cobasendiri.kasirmudah.domain.exception

sealed class DomainException() : Exception() {

    data object DataAlreadyExist : DomainException() {
        private fun readResolve(): Any = DataAlreadyExist
    }

    data object StorageFullError : DomainException() {
        private fun readResolve(): Any = StorageFullError
    }

    data object DatabaseError : DomainException() {
        private fun readResolve(): Any = DatabaseError
    }

    data class UnknownError(val originalMessage: String?) : DomainException()
}