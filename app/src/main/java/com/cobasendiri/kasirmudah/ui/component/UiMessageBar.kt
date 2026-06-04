package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography

@Composable
fun UiMessageBar(
    modifier: Modifier = Modifier,
    imageStart: Painter = painterResource(R.drawable.ic_info_outline_24_white),
    imageBackground: Color = Tertiary,
    message: String,
) {
    Surface(
        modifier.fillMaxWidth()
            .padding(8.dp)
            .background(White, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            Modifier.background(White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier = Modifier.background(imageBackground, CircleShape),
                painter = imageStart,
                contentDescription = null
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = message.take(140),
                style = KasirMudahTypography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun UiMessageBarPrev() {
    UiMessageBar(message = "Example of a message to be displayed")
}