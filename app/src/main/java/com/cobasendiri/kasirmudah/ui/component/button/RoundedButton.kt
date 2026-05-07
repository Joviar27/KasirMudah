package com.cobasendiri.kasirmudah.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Negative
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.White


@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = OnPrimary,
    textStyle: TextStyle = KasirMudahTypography.titleMedium,
    backgroundColor: Color = Color.Transparent,
    borderColor: Color = OnPrimary,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(modifier.fillMaxWidth()
        .clip(RoundedCornerShape(32.dp))
        .background(backgroundColor)
        .border(1.dp, borderColor, RoundedCornerShape(32.dp))
        .clickable{
            if(isEnabled) onClick.invoke()
        }
        .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ){
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}

@Preview
@Composable
fun RoundedButtonPrev() {
    Column(Modifier.width(180.dp)
        .background(White)
        .padding(16.dp)
    ) {
        RoundedButton(
            text = "Base"
        ){}
        Spacer(Modifier.height(8.dp))
    }
}
