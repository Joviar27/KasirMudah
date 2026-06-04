package com.cobasendiri.kasirmudah.ui.utils

import androidx.annotation.StringRes
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import java.util.UUID

object UiMessageUtil {

    fun String.asUiMessage(
        type: UiMessageType = UiMessageType.INFORMATION
    ): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return UiMessage.DynamicString(getRandomId, type, this)
    }

    fun @receiver:StringRes Int.asUiMessage(
        type: UiMessageType = UiMessageType.INFORMATION
    ): UiMessage{
        val getRandomId = UUID.randomUUID().mostSignificantBits
        return UiMessage.StringResource(getRandomId, type, this)
    }
}