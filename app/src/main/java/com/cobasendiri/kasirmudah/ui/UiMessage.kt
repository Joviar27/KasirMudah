package com.cobasendiri.kasirmudah.ui

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiMessage {
    val id: Long

    data class DynamicString(
        override val id: Long,
        val value: String
    ) : UiMessage

    data class StringResource(
        override val id: Long,
        @StringRes val resId: Int
    ) : UiMessage

    fun getRandomId(): Long = id

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId)
    }
}