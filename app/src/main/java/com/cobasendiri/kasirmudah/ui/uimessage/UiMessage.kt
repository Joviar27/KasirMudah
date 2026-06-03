package com.cobasendiri.kasirmudah.ui.uimessage

sealed interface UiMessage {
    val id: Long

    data class DynamicString(
        override val id: Long,
        val value: String
    ) : UiMessage

    data class StringResource(
        override val id: Long,
        @androidx.annotation.StringRes val resId: Int
    ) : UiMessage

    fun getMessageId(): Long = id

    fun asString(context: android.content.Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId)
    }
}