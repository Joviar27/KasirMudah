package com.cobasendiri.kasirmudah.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypograhpy
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.White

@Composable
fun ShopItem(
    modifier: Modifier = Modifier,
    colorCode: Color,
    itemName: String,
    itemDisplayPrice: String,
    itemRawPrice: Long,
    onClick: () -> Unit,
    onEditColorClick: () -> Unit,
    onTotalChange: (Long) -> Unit
) {

    Row(modifier.fillMaxWidth()
        .clip(RoundedCornerShape(24.dp))
        .background(White)
        .clickable(onClick = onClick)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ColorCode(colorCode, onEditColorClick)
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(
                itemName,
                style = KasirMudahTypograhpy.titleMedium
                    .copy(fontWeight = FontWeight.Bold)
            )
            Text(
                itemDisplayPrice,
                style = KasirMudahTypograhpy.bodyMedium
            )
        }
        Counter {
            onTotalChange.invoke(it*itemRawPrice)
        }
    }
}

@Composable
fun Counter(
    initialValue: Int = 0,
    onValueChange: (Int) -> Unit
){
    var count by remember { mutableIntStateOf(initialValue) }

    val onCountChange = {
        onValueChange.invoke(count)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Box(Modifier.clip(CircleShape)
            .background(Color.Transparent)
            .border(1.dp, OnPrimaryVariant, CircleShape)
            .padding(1.dp)
            .clickable{
                if(count>0){
                    count--
                    onCountChange.invoke()
                }
            }
        ){
            Image(
                painterResource(R.drawable.ic_minus_13),
                contentDescription = null
            )
        }
        Box(Modifier
            .width(22.dp)
            .background(Surface, RoundedCornerShape(4.dp))
            .padding(2.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                count.toString(),
                style = KasirMudahTypograhpy.bodyMedium
            )
        }
        Box(Modifier.clip(CircleShape)
            .background(Color.Transparent)
            .border(1.dp, OnPrimaryVariant, CircleShape)
            .padding(1.dp)
            .clickable{
                count++
                onCountChange.invoke()
            }
        ){
            Image(
                painterResource(R.drawable.ic_plus_13),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ColorCode(
    color: Color,
    onEditClick: () -> Unit
){
    Box(Modifier.size(64.dp)
        .background(color, RoundedCornerShape(16.dp))
        .padding(8.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Box(Modifier.clip(CircleShape)
            .background(OnPrimary, CircleShape)
            .size(16.dp)
            .clickable(onClick = onEditClick),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun ShopItemPrev() {
    Box(Modifier.padding(16.dp)){
        ShopItem(
            colorCode = Tertiary,
            itemName = "Nama Item 1",
            itemDisplayPrice = "Rp 15.000,00",
            itemRawPrice = 15000,
            onClick = {},
            onEditColorClick = {},
        ){}
    }
}