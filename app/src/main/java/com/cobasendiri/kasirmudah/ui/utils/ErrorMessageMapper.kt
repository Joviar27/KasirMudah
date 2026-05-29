package com.cobasendiri.kasirmudah.ui.utils

import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.ui.UiMessage
import com.cobasendiri.kasirmudah.R

object ErrorMessageMapper {
    fun Throwable.asUiMessage(): UiMessage{
        return when(this){
            is KasirMudahException.StorageFullError -> {
                UiMessage.StringResource(R.string.error_full_storage)
            }
            is KasirMudahException.DatabaseError -> {
                UiMessage.StringResource(R.string.error_database)
            }
            is KasirMudahException.UnknownError ->{
                UiMessage.StringResource(R.string.error_general)
            }
            else -> UiMessage.DynamicString(this.message ?: "Something went wrong")
        }
    }
}