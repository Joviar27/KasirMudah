package com.cobasendiri.kasirmudah.ui

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiMessage {
    data class DynamicString(val id: Long, val value: String) : UiMessage
    data class StringResource(val id: Long, @StringRes val resId: Int) : UiMessage

    fun getId(): Long {
        return id
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
        }
    }
}