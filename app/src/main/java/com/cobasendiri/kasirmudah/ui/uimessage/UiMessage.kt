package com.cobasendiri.kasirmudah.ui.uimessage

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiMessage {
    val id: Long
    val type: UiMessageType

    data class DynamicString(
        override val id: Long,
        override val type: UiMessageType,
        val value: String
    ) : UiMessage

    data class StringResource(
        override val id: Long,
        override val type: UiMessageType,
        @StringRes val resId: Int
    ) : UiMessage

    fun getMessageId(): Long = id

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId)
    }
}