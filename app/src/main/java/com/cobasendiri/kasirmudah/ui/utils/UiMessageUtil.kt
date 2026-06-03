package com.cobasendiri.kasirmudah.ui.utils

import androidx.annotation.StringRes
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import java.util.UUID

object UiMessageUtil {

    fun String.asUiMessage(): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return UiMessage.DynamicString(getRandomId, this)
    }

    fun @receiver:StringRes Int.asUiMessage(): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return UiMessage.StringResource(getRandomId, this)
    }
}