package com.cobasendiri.kasirmudah.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.ui.component.InputField
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.model.Shop
import com.cobasendiri.kasirmudah.model.ShopAdded
import com.cobasendiri.kasirmudah.ui.component.ShopItem
import com.cobasendiri.kasirmudah.ui.component.TotalItem
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.TertiaryVariant
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.util.decimalFormat

@Composable
fun ShopScreen(){

    val addedItem = rememberSaveable { mutableListOf<ShopAdded>() }
    var totalAmount by rememberSaveable { mutableLongStateOf(0L) }

    var initialShopState by remember { mutableStateOf<ShopState>(
        ShopState(
            shopName = "Toko Madura A",
            date = "24 Januari 2026",
            totalAmount = "Rp 0,00",
            shopItemList = generateDummyShopItem()
        )
    ) }

    ShopContent(
        state = initialShopState,
    ){ event ->
        when(event){
            is ShopEvent.OnItemIncrease ->{
                totalAmount += event.itemPrice
                addedItem.find { it.id == event.itemId }?.let {
                    it.count += 1
                } ?: run {
                    addedItem.add(ShopAdded(event.itemId, 1))
                }
            }
            is ShopEvent.OnItemDecrease ->{
                totalAmount -= event.itemPrice
                addedItem.find { it.id == event.itemId }?.let {
                    it.count -= 1
                    if(it.count==0) addedItem.remove(it)
                }
            }
        }
        initialShopState = initialShopState.copy(
            totalAmount = "Rp ${totalAmount.toString().decimalFormat()},00"
        )
    }
}

@Composable
fun ShopContent(
    state: ShopState,
    event: (ShopEvent) -> Unit
){

    val listState = rememberLazyListState()

    val topColorAlpha by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val firstItem = layoutInfo.visibleItemsInfo.firstOrNull { it.index == 0 }
            if (firstItem != null) {
                (listState.firstVisibleItemScrollOffset.toFloat() / firstItem.size).coerceIn(0f, 1f)
            } else {
                1f
            }
        }
    }

    val initialGradient = remember {
        Brush.verticalGradient(
            colors = listOf(Primary, Color.Transparent)
        )
    }

    Box(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        //Top decoration view
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .drawBehind {
                drawRect(
                    brush = initialGradient,
                    alpha = 1f-topColorAlpha,
                    size = Size(size.width, 250.dp.toPx())
                )
            }
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(180.dp,25.dp),
                        painter = painterResource(R.drawable.ic_kasirmudah),
                        contentDescription = null,
                        alignment = Alignment.CenterStart
                    )
                    Row(Modifier
                        .background(OnPrimary, RoundedCornerShape(16.dp))
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.setting),
                            style = KasirMudahTypography.bodyMedium
                                .copy(color = White)
                        )
                        Spacer(Modifier.width(4.dp))
                        Image(
                            modifier = Modifier.size(19.dp),
                            painter = painterResource(R.drawable.ic_menu),
                            contentDescription = null
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                Column(Modifier.padding(horizontal = 16.dp)){
                    Text(
                        text = state.shopName,
                        style = KasirMudahTypography.titleLarge
                    )
                    Text(
                        text = state.date,
                        style = KasirMudahTypography.bodyLarge
                    )
                }
            }
            stickyHeader {
                Column(Modifier.drawBehind{
                    val roundedRadius = 24.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(0f, 0f),
                                    size = Size(size.width, 200.dp.toPx())
                                ),
                                topLeft = CornerRadius.Zero,
                                topRight = CornerRadius.Zero,
                                bottomRight = CornerRadius(roundedRadius, roundedRadius),
                                bottomLeft = CornerRadius(roundedRadius, roundedRadius)
                            )
                        )
                    }
                    drawPath(
                        path = path,
                        color = TertiaryVariant,
                        alpha = topColorAlpha,
                    )
                }.padding(horizontal = 16.dp)){
                    Spacer(Modifier.height(24.dp))
                    TotalItem(totalAmount = state.totalAmount) {
                        event.invoke(ShopEvent.OnFinish)
                    }
                    Spacer(Modifier.height(14.dp))
                    Row(Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                    ) {
                        InputField(
                            Modifier.weight(4f),
                            stringResource(R.string.search),
                            background = White,
                            maxCharacter = 22,
                            showTopLabel = false
                        ) { searchQuery ->
                            event.invoke(ShopEvent.OnSearch(searchQuery))
                        }
                        Spacer(Modifier.width(10.dp))
                        Box(Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(OnPrimaryVariant)
                            .clickable(
                                onClick = {
                                    //Show add item dialog
                                },
                            )
                            .padding(horizontal = 12.dp),
                            contentAlignment = Alignment.Center
                        ){
                            Image(
                                alignment = Alignment.Center,
                                painter = painterResource(R.drawable.ic_plus_36),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            items(
                items = state.shopItemList,
                key = { shop -> shop.id}
            ) {
                Spacer(Modifier.height(16.dp))
                ShopItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    colorCode = it.colorCode,
                    itemId = it.id,
                    itemName = it.name,
                    itemDisplayPrice = it.displayPrice,
                    itemRawPrice = it.rawPrice,
                    onClick = {

                    },
                    onItemIncrease = {
                        ShopEvent.OnItemIncrease(
                            itemId = it.first,
                            itemPrice = it.second
                        ).let { event.invoke(it) }
                    },
                    onItemDecrease = {
                        ShopEvent.OnItemDecrease(
                            itemId = it.first,
                            itemPrice = it.second
                        ).let { event.invoke(it) }
                    },
                    onEditColorClick = {

                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopContentPreview(){
    KasirMudahTheme {
        ShopContent(
            state = ShopState(
                shopName = "Toko Madura A",
                date = "24 Januari 2026",
                totalAmount = "Rp 1.575.000,00",
                shopItemList = generateDummyShopItem()
            ),
        ){}
    }
}

fun generateDummyShopItem(): List<Shop>{
    return List(20) {
        Shop(
            it.toString(),
            "Nama item",
            "Rp 15.000,00",
            15000,
            Tertiary
        )
    }
}