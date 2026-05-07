package com.cobasendiri.kasirmudah.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun RoundedPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit
){
    RoundedButton(
        modifier = modifier,
        text = text,
        backgroundColor = Primary,
        isEnabled = isEnabled,
        onClick = onClick
    )
}

@Preview
@Composable
fun RoundedPrimaryButtonPrev() {
    Column(Modifier.width(180.dp)
        .background(White)
        .padding(16.dp)
    ) {
        RoundedPrimaryButton(
            text = "Primary"
        ) {}
    }
}