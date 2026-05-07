package com.cobasendiri.kasirmudah.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import com.cobasendiri.kasirmudah.ui.component.FilterChip
import com.cobasendiri.kasirmudah.ui.component.FloatingAction
import com.cobasendiri.kasirmudah.ui.component.ShopItem
import com.cobasendiri.kasirmudah.ui.component.ShopItemDetailDialog
import com.cobasendiri.kasirmudah.ui.component.TotalItem
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.TertiaryVariant
import com.cobasendiri.kasirmudah.ui.theme.White
import kotlin.collections.find

@Composable
fun ShopScreen(
    innerPadding: PaddingValues
){
    //Temporary before viewmodel
    val addedItem = rememberSaveable { mutableListOf<ShopAdded>() }
    val itemList = rememberSaveable { generateDummyShopItemList() }

    var dummyState by remember { mutableStateOf<ShopState>(
        ShopState(
            shopName = "Toko Madura A",
            date = "24 Januari 2026",
            totalAmount = 0L,
            shopItemList = itemList.map {
                ShopItemState(
                    shop = it,
                    count = 0
                )
            },
            isFloatingActionVisible = false,
            filter = ShopFilter.FILTER_ALL,
            showAddItemDialog = false,
            showEditItemDialog = null
        )
    ) }

    ShopContent(
        innerPadding = innerPadding,
        state = dummyState,
    ){ event ->
        when(event){
            is ShopEvent.OnItemIncrease ->{
                //Temporary before viewmodel
                addedItem.find {it.id == event.itemId}?.let {
                    it.count += 1
                }?: run {
                    addedItem.add(ShopAdded(event.itemId,1))
                }

                dummyState = dummyState.copy(
                    shopItemList = dummyState.shopItemList.map {
                        if(it.shop.id == event.itemId){
                            ShopItemState(
                                shop = it.shop,
                                count = addedItem.find { addedItem ->
                                    addedItem.id == event.itemId
                                }?.count ?: 0
                            )
                        }else it
                    },
                    totalAmount = addedItem.sumOf { addedItem ->
                        val itemPrice = dummyState.shopItemList.find{it.shop.id == addedItem.id}?.shop?.price ?: 0
                        addedItem.count * itemPrice
                    },
                    isFloatingActionVisible = addedItem.isNotEmpty()
                )
            }
            is ShopEvent.OnItemDecrease ->{
                //Temporary before viewmodel
                addedItem.find {it.id == event.itemId}?.let {
                    it.count -= 1
                    if(it.count<=0){
                        addedItem.remove(it)
                    }
                }

                dummyState = dummyState.copy(
                    shopItemList = dummyState.shopItemList.map {
                        if(it.shop.id == event.itemId){
                            ShopItemState(
                                shop = it.shop,
                                count = addedItem.find { addedItem ->
                                    addedItem.id == event.itemId
                                }?.count ?: 0
                            )
                        }else it
                    },
                    totalAmount = addedItem.sumOf { addedItem ->
                        val itemPrice = dummyState.shopItemList.find{it.shop.id == addedItem.id}?.shop?.price ?: 0
                        addedItem.count * itemPrice
                    },
                    isFloatingActionVisible = addedItem.isNotEmpty()
                )
            }
            is ShopEvent.OnItemNewColor ->{
                val index = itemList.indexOfFirst { it.id == event.itemId }
                if(index != -1){
                    itemList[index] = itemList[index].copy(colorCode = event.newColor)
                }

                dummyState = dummyState.copy(
                    shopItemList = dummyState.shopItemList.map {
                        if(it.shop.id == event.itemId){
                            it.copy(shop = it.shop.copy(
                                colorCode = event.newColor
                            ))
                        }else it
                    }
                )
            }
            is ShopEvent.OnReset ->{
                //Temporary before viewmodel
                addedItem.clear()
                val newList = dummyState.shopItemList.map {
                    it.copy(count = 0)
                }
                dummyState = dummyState.copy(
                    isFloatingActionVisible = false,
                    totalAmount = 0,
                    shopItemList = newList
                )
            }
            is ShopEvent.OnFilterChange ->{
                dummyState = dummyState.copy(
                    filter = event.newFilter
                )
                dummyState = when(event.newFilter){
                    ShopFilter.FILTER_CART -> {
                        dummyState.copy(
                            shopItemList = dummyState.shopItemList.filter {
                                it.count > 0
                            }
                        )
                    }
                    ShopFilter.FILTER_ALL -> {
                        dummyState.copy(
                            shopItemList = itemList.map { original ->
                                ShopItemState(
                                    shop = original,
                                    count = addedItem.find {
                                        it.id == original.id
                                    }?.count ?: 0
                                )
                            }
                        )
                    }
                }
            }
            is ShopEvent.OnFinish ->{

            }
            is ShopEvent.OnSearch ->{
                dummyState = dummyState.copy(
                    shopItemList = itemList.filter { it.name.contains(event.searchQuery) }.map {
                        ShopItemState(
                            shop = it,
                            count = addedItem.find { addedItem ->
                                addedItem.id == it.id
                            }?.count ?: 0
                        )
                    }
                )
            }
            is ShopEvent.OnShowAddItemDialog ->{
                dummyState = dummyState.copy(
                    showAddItemDialog = true,
                )
            }
            is ShopEvent.OnShowEditItemDialog ->{
                dummyState = dummyState.copy(
                    showEditItemDialog = event.shop,
                )
            }
            is ShopEvent.OnDismissItemDialog ->{
                dummyState = dummyState.copy(
                    showAddItemDialog = false,
                    showEditItemDialog = null
                )
            }
            is ShopEvent.OnNewShopItem ->{
                //Temporary before viewmodel
                val newShopItem = Shop(
                    id = itemList.size.toString(),
                    name = event.newShop.name,
                    price = event.newShop.price.toLong(),
                    colorCode = event.newShop.colorCode
                )
                itemList.add(newShopItem)

                if(dummyState.filter == ShopFilter.FILTER_CART){
                    return@ShopContent
                }
                dummyState = dummyState.copy(
                    shopItemList = itemList.map { original ->
                        ShopItemState(
                            shop = original,
                            count = addedItem.find {
                                it.id == original.id
                            }?.count ?: 0
                        )
                    }
                )
            }
            is ShopEvent.OnUpdateShopItem ->{
                //Temporary before viewmodel
                val updatedShopItem = Shop(
                    id = event.updatedShop.id,
                    name = event.updatedShop.name,
                    price = event.updatedShop.price.toLong(),
                    colorCode = event.updatedShop.colorCode
                )
                val index = itemList.indexOfFirst { it.id == updatedShopItem.id }
                if(index != -1){
                    itemList[index] = updatedShopItem
                }

                if(dummyState.filter == ShopFilter.FILTER_CART){
                    return@ShopContent
                }
                dummyState = dummyState.copy(
                    shopItemList = itemList.map { original ->
                        ShopItemState(
                            shop = original,
                            count = addedItem.find {
                                it.id == original.id
                            }?.count ?: 0
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ShopContent(
    innerPadding: PaddingValues,
    state: ShopState,
    event: (ShopEvent) -> Unit
){

    val listState = rememberLazyListState()
    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

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

    Box(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        //Top decoration view
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(topPadding + 250.dp)
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Primary, Color.Transparent)
                    ),
                    alpha = 1f-topColorAlpha,
                    size = Size(size.width, (topPadding+250.dp).toPx())
                )
            }
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                Spacer(Modifier.height(topPadding+16.dp))
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
                                    size = Size(size.width, (topPadding+235.dp).toPx())
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
                    Spacer(Modifier.height(topPadding+8.dp))
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
                                    event.invoke(ShopEvent.OnShowAddItemDialog)
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
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            text = stringResource(R.string.all),
                            filter = ShopFilter.FILTER_ALL,
                            isSelected = state.filter == ShopFilter.FILTER_ALL
                        ) {
                            event.invoke(
                                ShopEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(R.string.cart),
                            filter = ShopFilter.FILTER_CART,
                            isSelected = state.filter == ShopFilter.FILTER_CART
                        ) {
                            event.invoke(
                                ShopEvent.OnFilterChange(it)
                            )
                        }
                    }
                }
            }
            items(
                items = state.shopItemList,
                key = { shopItemState -> shopItemState.shop.id}
            ) { item ->
                Spacer(Modifier.height(16.dp))
                ShopItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onClick = {
                        event.invoke(
                            ShopEvent.OnShowEditItemDialog(it)
                        )
                    },
                    onItemIncrease = {
                        ShopEvent.OnItemIncrease(
                            itemId = item.shop.id
                        ).let { event.invoke(it) }
                    },
                    onItemDecrease = {
                        ShopEvent.OnItemDecrease(
                            itemId = item.shop.id
                        ).let { event.invoke(it) }
                    },
                    onColorCodeUpdate = {
                        ShopEvent.OnItemNewColor(
                            itemId = item.shop.id,
                            newColor = it
                        ).let { event.invoke(it) }
                    }
                )
            }
        }

        if(state.isFloatingActionVisible){
            FloatingAction(
                Modifier.padding(bottom = innerPadding.calculateBottomPadding()+12.dp)
                    .align(Alignment.BottomCenter),
                onDelete = {
                    event.invoke(ShopEvent.OnReset)
                },
                onDone = {
                    event.invoke(ShopEvent.OnFinish)
                }
            )
        }
        if(state.showAddItemDialog){
            ShopItemDetailDialog(
                onSave = {
                    event.invoke(ShopEvent.OnNewShopItem(it))
                },
                onDismiss = {
                    event.invoke(ShopEvent.OnDismissItemDialog)
                }
            )
        }
        if(state.showEditItemDialog != null){
            ShopItemDetailDialog(
                shop = state.showEditItemDialog,
                onSave = {
                    event.invoke(ShopEvent.OnUpdateShopItem(it))
                },
                onDismiss = {
                    event.invoke(ShopEvent.OnDismissItemDialog)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShopContentPreview(){
    KasirMudahTheme {
        ShopContent(
            innerPadding = PaddingValues(bottom = 60.dp),
            state = ShopState(
                shopName = "Toko Madura A",
                date = "24 Januari 2026",
                totalAmount = 1575000L,
                shopItemList = generateDummyShopItemList().map {
                    ShopItemState(
                        shop = it,
                        count = 0
                    )
                },
                isFloatingActionVisible = true,
                filter = ShopFilter.FILTER_ALL,
                showEditItemDialog = null,
                showAddItemDialog = false
            ),
        ){}
    }
}


fun generateDummyShopItemList() : MutableList<Shop>{
    return MutableList(20){
        Shop(
            it.toString(),
            "Nama item",
            15000,
            Tertiary
        )
    }
}