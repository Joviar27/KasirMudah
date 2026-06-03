package com.cobasendiri.kasirmudah.ui.utils

import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.ui.UiMessage
import com.cobasendiri.kasirmudah.R
import java.util.UUID

object ErrorMessageMapper {
    fun Throwable.asUiMessage(): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return when(this){
            is KasirMudahException.StorageFullError -> {
                UiMessage.StringResource(getRandomId, R.string.error_full_storage)
            }
            is KasirMudahException.DatabaseError -> {
                UiMessage.StringResource(getRandomId, R.string.error_database)
            }
            is KasirMudahException.UnknownError ->{
                UiMessage.StringResource(getRandomId, R.string.error_general)
            }
            else -> UiMessage.DynamicString(getRandomId, this.message ?: "Something went wrong")
        }
    }
}