package com.cobasendiri.kasirmudah.ui.utils

import com.cobasendiri.kasirmudah.domain.exception.KasirMudahException
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import java.util.UUID

object ErrorMessageMapper {
    fun Throwable.asUiMessage(): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return when(this){
            is KasirMudahException.StorageFullError -> {
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, R.string.error_full_storage
                )
            }
            is KasirMudahException.DatabaseError -> {
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, R.string.error_database
                )
            }
            is KasirMudahException.UnknownError ->{
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, R.string.error_general
                )
            }
            is KasirMudahException.TransactionAmountInvalidError ->{
                UiMessage.StringResource(
                    getRandomId, UiMessageType.ERROR, R.string.error_transaction_amount_invalid
                )
            }
            else -> UiMessage.DynamicString(
                getRandomId, UiMessageType.ERROR, this.message ?: "Something went wrong"
            )
        }
    }
}