package com.cobasendiri.kasirmudah.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cobasendiri.kasirmudah.model.Transaction
import com.cobasendiri.kasirmudah.model.TransactionShopItem
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.TransactionItem
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.TertiaryVariant

@Composable
fun TransactionHistoryScreen(
    innerPadding: PaddingValues
){
    //Dummy before viewmodel
    val bookmarked = rememberSaveable { mutableListOf<String>() }
    val transactionList = rememberSaveable { generateDummyTransactionItemList() }

    var dummyState by remember { mutableStateOf(
        TransactionHistoryState(
            filter = "ALL",
            transactionList = transactionList.map {
                TransactionItemState(
                    id = it.id,
                    name = it.name,
                    createdAt = it.createdAt,
                    isBookmarked = false
                )
            }
        )
    ) }

    TransactionHistoryContent(
        innerPadding,
        dummyState
    ){ event ->
        when(event){
            is TransactionHistoryEvent.OnUpdateBookmark ->{
                val isBookmarked = bookmarked.contains(event.transactionId)
                if(isBookmarked){
                    bookmarked.remove(event.transactionId)
                }else{
                    bookmarked.add(event.transactionId)
                }

                dummyState = dummyState.copy(
                    transactionList = dummyState.transactionList.map {
                        TransactionItemState(
                            id = it.id,
                            name = it.name,
                            createdAt = it.createdAt,
                            isBookmarked = bookmarked.contains(it.id)
                        )
                    }
                )

            }
            is TransactionHistoryEvent.OnDelete ->{
                transactionList.removeIf { it.id == event.transactionId }
                bookmarked.remove(event.transactionId)

                dummyState = dummyState.copy(
                    transactionList =  transactionList.map {
                        TransactionItemState(
                            id = it.id,
                            name = it.name,
                            createdAt = it.createdAt,
                            isBookmarked = bookmarked.contains(it.id)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TransactionHistoryContent(
    innerPadding: PaddingValues,
    state: TransactionHistoryState,
    event: (TransactionHistoryEvent) -> Unit
){
    val listState = rememberLazyListState()
    val topPadding = remember(innerPadding){
        innerPadding.calculateTopPadding()
    }

    val topColorAlpha by remember {
        derivedStateOf {
            val firstItemIndex = listState.firstVisibleItemIndex
            val scrollOffset = listState.firstVisibleItemScrollOffset

            val transitionThreshold = 100f
            if (firstItemIndex > 0) {
                1f
            } else {
                (scrollOffset / transitionThreshold).coerceIn(0f, 1f)
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
            stickyHeader {
                Column(Modifier.drawBehind{
                    val roundedRadius = 24.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(0f, 0f),
                                    size = Size(size.width, 100.dp.toPx())
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
                }.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Spacer(Modifier.height(topPadding+16.dp))
                    Text(
                        text = stringResource(R.string.history),
                        style = KasirMudahTypography.titleLarge
                    )
                }
            }
            items(
                items = state.transactionList,
                key = { transactionItemState -> transactionItemState.id }
            ) { item ->
                Spacer(Modifier.height(16.dp))
                TransactionItem(
                    Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onUpdateBookmark = {
                        event.invoke(TransactionHistoryEvent.OnUpdateBookmark(it))
                    },
                    onDelete = {
                        event.invoke(TransactionHistoryEvent.OnDelete(it))
                    }
                )
            }
        }
    }
}

fun generateDummyTransactionItemList() : MutableList<Transaction>{
    return MutableList(20){
        Transaction(
            id = "4shisefhw48t4w3r$it",
            name = "transaksi-4shisefhw48t4w3r$it",
            createdAt = "12 Agustus 2026 - 12:53:01",
            shopItems = MutableList(6){
                TransactionShopItem(
                    itemName = "Barang Nomor $it",
                    totalPrice = "Rp 863.000,00"
                )
            }
        )
    }
}