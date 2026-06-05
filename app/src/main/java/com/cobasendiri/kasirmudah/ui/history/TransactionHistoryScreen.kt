package com.cobasendiri.kasirmudah.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.component.FilterChip
import com.cobasendiri.kasirmudah.ui.component.TransactionItem
import com.cobasendiri.kasirmudah.ui.component.dialog.NegativeConfirmDialog
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Secondary
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
            filter = TransactionFilter.FILTER_ALL,
            showConfirmDeleteDialog = null,
            transactionList = transactionList
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
            is TransactionHistoryEvent.OnShowConfirmDeleteDialog ->{
                dummyState = dummyState.copy(
                    showConfirmDeleteDialog = event.transactionId
                )
            }
            is TransactionHistoryEvent.OnDismissConfirmDeleteDialog -> {
                dummyState = dummyState.copy(
                    showConfirmDeleteDialog = null
                )
            }
            is TransactionHistoryEvent.OnFilterChange ->{
                dummyState = dummyState.copy(
                    filter = event.newFilter
                )

                dummyState = when(event.newFilter){
                    TransactionFilter.FILTER_BOOKMARKED -> {
                        dummyState.copy(
                            transactionList = dummyState.transactionList.filter {
                                bookmarked.contains(it.id)
                            }
                        )
                    }
                    else ->{
                        dummyState.copy(
                            transactionList = transactionList.map {
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
    }
}

@Composable
fun TransactionHistoryContent(
    innerPadding: PaddingValues,
    state: TransactionHistoryState,
    event: (TransactionHistoryEvent) -> Unit
){
    val listState = rememberLazyListState()
    val filterScrollState = rememberScrollState()

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
                    val roundedRadius = 16.dp.toPx()
                    val path = Path().apply {
                        addRoundRect(
                            RoundRect(
                                rect = Rect(
                                    offset = Offset(0f, 0f),
                                    size = Size(size.width, 150.dp.toPx())
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
                }.fillMaxWidth()) {
                    Spacer(Modifier.height(topPadding+16.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.history),
                        style = KasirMudahTypography.titleLarge
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        Modifier.horizontalScroll(filterScrollState),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.bookmarked),
                            filter = TransactionFilter.FILTER_BOOKMARKED,
                            isSelected = state.filter == TransactionFilter.FILTER_BOOKMARKED,
                            selectedBgColor = Secondary,
                            selectedTextColor = OnPrimary
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(R.string.all),
                            filter = TransactionFilter.FILTER_ALL,
                            isSelected = state.filter == TransactionFilter.FILTER_ALL
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            text = stringResource(R.string.last_week),
                            filter = TransactionFilter.FILTER_LAST_WEEK,
                            isSelected = state.filter == TransactionFilter.FILTER_LAST_WEEK
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                        FilterChip(
                            modifier = Modifier.padding(end = 16.dp),
                            text = stringResource(R.string.last_month),
                            filter = TransactionFilter.FILTER_LAST_MONTH,
                            isSelected = state.filter == TransactionFilter.FILTER_LAST_MONTH
                        ) {
                            event.invoke(
                                TransactionHistoryEvent.OnFilterChange(it)
                            )
                        }
                    }
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
                        event.invoke(TransactionHistoryEvent.OnShowConfirmDeleteDialog(it))
                    },
                    onItemClick = {
                        //Navigate to transaction detail page
                    }
                )
            }
            item {
                Spacer(Modifier.height(innerPadding.calculateBottomPadding() + 12.dp))
            }
        }
        if(state.showConfirmDeleteDialog != null){
            val dismissEvent = TransactionHistoryEvent.OnDismissConfirmDeleteDialog
            val itemId = state.showConfirmDeleteDialog
            NegativeConfirmDialog(
                title = stringResource(R.string.delete_transaction_title),
                body = stringResource(R.string.delete_transaction_body),
                cancelButton = stringResource(R.string.cancel),
                confirmButton = stringResource(R.string.delete),
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onConfirm = {
                    event.invoke(TransactionHistoryEvent.OnDelete(itemId))
                    event.invoke(dismissEvent)
                }
            )
        }
    }
}



fun generateDummyTransactionItemList(): MutableList<TransactionItemState>{
    return MutableList(20){
        TransactionItemState(
            id = "4shisefhw48t4$it",
            name = "transaksi-4shisefhw48t4$it",
            createdAt = "12 Agustus 2026 - 12:53:01",
            isBookmarked = false
        )
    }
}