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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.toColorLong
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cobasendiri.kasirmudah.ui.component.InputField
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.Cart
import com.cobasendiri.kasirmudah.domain.model.ProductInfo
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.component.FilterChip
import com.cobasendiri.kasirmudah.ui.component.FloatingAction
import com.cobasendiri.kasirmudah.ui.component.ProductItem
import com.cobasendiri.kasirmudah.ui.component.dialog.ProductDetailDialog
import com.cobasendiri.kasirmudah.ui.component.TotalItem
import com.cobasendiri.kasirmudah.ui.component.dialog.NegativeConfirmDialog
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.OnPrimaryVariant
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.TertiaryVariant
import com.cobasendiri.kasirmudah.ui.theme.White
import com.cobasendiri.kasirmudah.ui.utils.ToastUtil.showToast
import kotlin.collections.find

@Composable
fun ShopScreen(
    innerPadding: PaddingValues
){
    val context = LocalContext.current
    val appContext = context.applicationContext

    val viewModel: ShopViewModel = viewModel(
        factory = ViewModelFactory.getInstance(appContext)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    state.errorMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getRandomId()) {
            uiMessage.asString(context).showToast(context)
            viewModel.errorMessageShown()
        }
    }

    //Temporary before viewmodel
    val cartItemList = rememberSaveable { mutableListOf<Cart>() }
    val itemList = rememberSaveable { generateDummyShopItemList() }

    var dummyState by remember { mutableStateOf<ShopState>(
        ShopState(
            shopName = "Toko Madura A",
            date = "24 Januari 2026",
            totalAmount = 0L,
            shopItemList = itemList.map {
                ProductInfo(
                    product = it,
                    count = 0
                )
            },
            isFloatingActionVisible = false,
            filter = ShopFilter.FILTER_ALL,
            showAddProductDialog = false,
            showEditProductDialog = null,
            showConfirmDeleteDialog = null
        )
    ) }

    ShopContent(
        innerPadding = innerPadding,
        state = state,
    ){ event ->
        when(event){
            is ShopEvent.OnIncreaseProduct ->{
                viewModel.incrementProduct(event.itemId)
            }
            is ShopEvent.OnDecreaseProduct ->{
                viewModel.decrementProduct(event.itemId)
            }
            is ShopEvent.OnUpdateProductColor ->{
                val index = itemList.indexOfFirst { it.id == event.productId }
                if(index != -1){
                    itemList[index] = itemList[index].copy(colorCode = event.newColor.toColorLong())
                }

                dummyState = dummyState.copy(
                    shopItemList = dummyState.shopItemList.map {
                        if(it.product.id == event.productId){
                            it.copy(product = it.product.copy(
                                colorCode = event.newColor.toColorLong()
                            ))
                        }else it
                    }
                )
            }
            is ShopEvent.OnReset ->{
                viewModel.clearCart()
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
                                ProductInfo(
                                    product = original,
                                    count = cartItemList.find {
                                        it.productId == original.id
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
                viewModel.loadProductList(event.searchQuery)
            }
            is ShopEvent.OnShowAddProductDialog ->{
                viewModel.showAddProductDialog()
            }
            is ShopEvent.OnShowEditProductDialog ->{
                dummyState = dummyState.copy(
                    showEditProductDialog = event.product,
                )
            }
            is ShopEvent.OnDismissProductDetailDialog ->{
                viewModel.dismissProductDetailDialog()
            }
            is ShopEvent.OnNewProduct ->{
                viewModel.addNewProduct(event.newProduct)
            }
            is ShopEvent.OnUpdateProduct ->{
                //Temporary before viewmodel
                val updatedProductItem = Product(
                    id = event.updatedProduct.id,
                    name = event.updatedProduct.name,
                    price = event.updatedProduct.price.toLong(),
                    colorCode = event.updatedProduct.colorCode
                )
                val index = itemList.indexOfFirst { it.id == updatedProductItem.id }
                if(index != -1){
                    itemList[index] = updatedProductItem
                }

                if(dummyState.filter == ShopFilter.FILTER_CART){
                    return@ShopContent
                }
                dummyState = dummyState.copy(
                    shopItemList = itemList.map { original ->
                        ProductInfo(
                            product = original,
                            count = cartItemList.find {
                                it.productId == original.id
                            }?.count ?: 0
                        )
                    }
                )
            }
            is ShopEvent.OnShowConfirmDeleteDialog ->{
                dummyState = dummyState.copy(
                    showConfirmDeleteDialog = event.itemId,
                )
            }
            is ShopEvent.OnDismissConfirmDeleteDialog ->{
                dummyState = dummyState.copy(
                    showConfirmDeleteDialog = null
                )
            }
            is ShopEvent.OnDeleteProduct ->{
                val index = itemList.indexOfFirst { it.id == event.productId }
                if(index != -1){
                    itemList.removeAt(index)
                }

                val indexAdded = cartItemList.indexOfFirst { it.productId ==event.productId }
                if(indexAdded != -1){
                    cartItemList.removeAt(indexAdded)
                }

                if(dummyState.filter == ShopFilter.FILTER_CART){
                    return@ShopContent
                }
                dummyState = dummyState.copy(
                    shopItemList = itemList.map {
                        ProductInfo(
                            product = it,
                            count = cartItemList.find { addedItem ->
                                addedItem.productId == it.id
                            }?.count ?: 0
                        )
                    },
                    totalAmount = cartItemList.sumOf { addedItem ->
                        val itemPrice = dummyState.shopItemList.find{it.product.id == addedItem.productId}?.product?.price ?: 0
                        addedItem.count * itemPrice
                    },
                    isFloatingActionVisible = cartItemList.isNotEmpty()
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
                    alpha = 1f - topColorAlpha,
                    size = Size(size.width, (topPadding + 250.dp).toPx())
                )
            }
        )
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState
        ) {
            item {
                Spacer(Modifier.height(topPadding+16.dp))
                Row(Modifier
                    .fillMaxWidth()
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
                            text = stringResource(R.string.menu_setting),
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
                                    size = Size(size.width, 245.dp.toPx())
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
                                    event.invoke(ShopEvent.OnShowAddProductDialog)
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
                key = { shopItemState -> shopItemState.product.id}
            ) { item ->
                Spacer(Modifier.height(16.dp))
                ProductItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onEdit = {
                        event.invoke(
                            ShopEvent.OnShowEditProductDialog(it)
                        )
                    },
                    onDelete = {
                        event.invoke(
                            ShopEvent.OnShowConfirmDeleteDialog(it)
                        )
                    },
                    onItemIncrease = {
                        ShopEvent.OnIncreaseProduct(
                            itemId = item.product.id
                        ).let { event.invoke(it) }
                    },
                    onItemDecrease = {
                        ShopEvent.OnDecreaseProduct(
                            itemId = item.product.id
                        ).let { event.invoke(it) }
                    },
                    onColorCodeUpdate = {
                        ShopEvent.OnUpdateProductColor(
                            productId = item.product.id,
                            newColor = it
                        ).let { event.invoke(it) }
                    },
                )
            }
            item {
                val bottomBarSize = innerPadding.calculateBottomPadding() + 12.dp
                val floatingActionSize = if(state.isFloatingActionVisible) 56.dp else 0.dp
                Spacer(Modifier.height(bottomBarSize + floatingActionSize))
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
        if(state.showAddProductDialog){
            val dismissItemDialogEvent = ShopEvent.OnDismissProductDetailDialog
            ProductDetailDialog(
                onDismiss = {
                    event.invoke(dismissItemDialogEvent)
                },
                onCancel = {
                    event.invoke(dismissItemDialogEvent)
                },
                onSave = {
                    event.invoke(ShopEvent.OnNewProduct(it))
                    event.invoke(dismissItemDialogEvent)
                }
            )
        }
        if(state.showEditProductDialog != null){
            val dismissItemDialogEvent = ShopEvent.OnDismissProductDetailDialog
            ProductDetailDialog(
                product = state.showEditProductDialog,
                onDismiss = {
                    event.invoke(dismissItemDialogEvent)
                },
                onCancel = {
                    event.invoke(dismissItemDialogEvent)
                },
                onSave = {
                    event.invoke(ShopEvent.OnUpdateProduct(it))
                    event.invoke(dismissItemDialogEvent)
                }
            )
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = ShopEvent.OnDismissConfirmDeleteDialog
            val itemId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(R.string.delete_shop_title),
                body = stringResource(R.string.delete_shop_body),
                cancelButton = stringResource(R.string.cancel),
                confirmButton = stringResource(R.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(ShopEvent.OnDeleteProduct(itemId))
                    event.invoke(dismissEvent)
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
                    ProductInfo(
                        product = it,
                        count = 0
                    )
                },
                isFloatingActionVisible = true,
                filter = ShopFilter.FILTER_ALL,
                showEditProductDialog = null,
                showAddProductDialog = false,
                showConfirmDeleteDialog = null
            ),
        ){}
    }
}


fun generateDummyShopItemList() : MutableList<Product>{
    return MutableList(20){
        Product(
            it.toString(),
            "Nama item",
            15000,
            Tertiary.toColorLong()
        )
    }
}