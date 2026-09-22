package com.cobasendiri.kasirmudah.ui.component.alertbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.Negative
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudah.ui.utils.UiMessageUtil.asUiMessage

@Composable
fun UiMessageBar(
    uiMessage: UiMessage
) {
    val context = LocalContext.current

    val icon = when(uiMessage.type){
        UiMessageType.SUCCESS -> painterResource(R.drawable.ic_check_24_white)
        UiMessageType.ERROR -> painterResource(R.drawable.ic_error_24_white)
        else -> painterResource(R.drawable.ic_info_outline_24_white)
    }
    val color = when(uiMessage.type){
        UiMessageType.SUCCESS -> Primary
        UiMessageType.ERROR -> Negative
        else -> Tertiary
    }
    InformationBar(
        imageStart = icon,
        imageBackground = color,
        message = uiMessage.asString(context)
    )
}

@Preview
@Composable
fun UiMessageBarPrev(){
    UiMessageBar(
        R.string.success_update.asUiMessage()
    )
}