package com.cobasendiri.kasirmudah.ui.component.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.cobasendiri.kasirmudah.ui.theme.PastelGreen
import com.cobasendiri.kasirmudah.ui.theme.PastelPink
import com.cobasendiri.kasirmudah.ui.theme.PastelPurple
import com.cobasendiri.kasirmudah.ui.theme.PastelRed
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ColorCodePopup(
    colors: List<Color> = listOf(
        Tertiary, PastelRed, PastelPurple, PastelPink, PastelGreen
    ),
    alignment: Alignment,
    offset: IntOffset,
    properties: PopupProperties,
    onColorSelected: (newColor: Color?) -> Unit
) {
    Popup(
        alignment = alignment,
        offset = offset,
        onDismissRequest = {
            onColorSelected.invoke(null)
        },
        properties = properties
    ){
        ColorCodeSelect(colors){
            onColorSelected.invoke(it)
        }
    }
}

@Composable
fun ColorCodeSelect(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit
){
    Row(Modifier
        .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
        .background(White, RoundedCornerShape(8.dp))
        .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        colors.forEach {
            ColorItem(it, onColorSelected = onColorSelected)
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    onColorSelected: (Color) -> Unit
){
    Box(Modifier.size(38.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable{
            onColorSelected.invoke(color)
        }
        .background(color)
    )
}

@Preview
@Composable
fun ColorCodePopupPrev() {
    ColorCodeSelect(
        listOf(
            Tertiary,
            PastelRed,
            PastelPurple,
            PastelPink,
            PastelGreen
        )
    ){

    }
}