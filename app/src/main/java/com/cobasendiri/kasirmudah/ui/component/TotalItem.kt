package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypograhpy
import com.cobasendiri.kasirmudah.ui.theme.Secondary

@Composable
fun TotalItem(
    modifier: Modifier = Modifier,
    totalAmount: String,
    onClickDone: () -> Unit
) {
    Row(modifier
        .fillMaxWidth()
        .background(White, RoundedCornerShape(24.dp))
        .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(3f)) {
            Text(
                stringResource(R.string.total_item),
                style = KasirMudahTypograhpy.bodyMedium
            )
            Spacer(Modifier.height(2.dp))
            Text(
                totalAmount,
                style = KasirMudahTypograhpy.headlineSmall
                    .copy(fontSize = calculateTotalFontSize(totalAmount))
            )
        }
        Spacer(Modifier.width(8.dp))
        Column(Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Secondary)
            .clickable(onClick = onClickDone)
            .padding(vertical = 8.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.done),
                style = KasirMudahTypograhpy.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Image(
                painterResource(R.drawable.ic_right_arrow_white_round),
                contentDescription = null
            )
        }
    }
}

private fun calculateTotalFontSize(text: String): TextUnit{
    return when {
        text.length <=13 -> 28.sp
        text.length <=15 -> 25.sp
        text.length <=17 -> 22.sp
        else -> 20.sp
    }
}

@Preview
@Composable
fun TotalItemPrev() {
    Box(Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ){
        TotalItem(totalAmount = "Rp 500.000,00"){}
    }
}