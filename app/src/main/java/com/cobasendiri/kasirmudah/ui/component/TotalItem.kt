package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypograhpy
import com.cobasendiri.kasirmudah.ui.theme.Secondary

@Composable
fun TotalItem(
    totalAmount: String,
    onClickDone: () -> Unit
) {
    Row(Modifier.fillMaxWidth()
        .background(White, RoundedCornerShape(24.dp))
        .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                stringResource(R.string.total_item),
                style = KasirMudahTypograhpy.bodyLarge
            )
            Spacer(Modifier.height(2.dp))
            Text(
                totalAmount,
                style = KasirMudahTypograhpy.headlineMedium
            )
        }
        Column(Modifier.wrapContentSize()
            .background(Secondary, RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .clickable(onClick = onClickDone),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.done),
                style = KasirMudahTypograhpy.titleMedium
            )
            Spacer(Modifier.height(4.dp))
            Icon(
                painterResource(R.drawable.ic_right_arrow_white_round),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun TotalItemPrev() {
    Box(Modifier.padding(16.dp)
        .fillMaxWidth()
    ){
        TotalItem("Rp 500.000,00"){}
    }
}