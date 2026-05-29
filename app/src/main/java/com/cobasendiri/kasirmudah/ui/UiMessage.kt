package com.cobasendiri.kasirmudah.ui

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiMessage {
    data class DynamicString(val value: String) : UiMessage
    data class StringResource(@StringRes val resId: Int) : UiMessage

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
        }
    }
}