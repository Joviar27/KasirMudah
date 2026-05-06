package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun <T> FilterChip(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    filter: T,
    selectedBgColor: Color = OnPrimary,
    unselectedBgColor: Color = Color.Transparent,
    selectedTextColor: Color = White,
    unselectedTextColor: Color = OnPrimary,
    borderColor: Color = OnPrimary,
    onSelectChange: (T) -> Unit
) {
    Box(modifier
        .clip(RoundedCornerShape(24.dp))
        .clickable{
            onSelectChange.invoke(filter)
        }
        .background(
            if(isSelected) selectedBgColor else unselectedBgColor,
        ).border(
            1.dp,
            borderColor,
            RoundedCornerShape(24.dp)
        ).padding(vertical = 8.dp, horizontal = 12.dp)
    ){
        Text(
            text,
            style = KasirMudahTypography.bodyMedium,
            color = if(isSelected) selectedTextColor else unselectedTextColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterChipPrev() {
    Column(Modifier.padding(16.dp)) {
        FilterChip(
            text = "Semua",
            filter = "",
            isSelected = false
        ){}
        Spacer(Modifier.height(16.dp))
        FilterChip(
            text = "Keranjang",
            filter = "",
            isSelected = true
        ){}
    }
}