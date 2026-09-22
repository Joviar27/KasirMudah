package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ProfileMenuItem(
    modifier: Modifier = Modifier,
    startIcon: Painter,
    name: String,
    onClick: () -> Unit
) {
    Row(modifier.fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(White)
        .clickable{
            onClick.invoke()
        }
        .padding(vertical = 12.dp)
        .padding(start = 16.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = startIcon,
            contentDescription = null
        )
        Spacer(Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = KasirMudahTypography.bodyMedium
        )
        Image(
            painter = painterResource(R.drawable.ic_chevron_right_24),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun ProfileMenuItemPrev() {
    ProfileMenuItem(
        startIcon = painterResource(R.drawable.ic_profile),
        name = "Nama Menu 1",
    ){}
}