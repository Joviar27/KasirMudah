package com.cobasendiri.kasirmudah.ui.history

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Surface
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudah.ui.component.FilterChip
import com.cobasendiri.kasirmudah.ui.component.TransactionHistoryItem
import com.cobasendiri.kasirmudah.ui.component.alertbar.InformationBar
import com.cobasendiri.kasirmudah.ui.component.alertbar.UiMessageBar
import com.cobasendiri.kasirmudah.ui.component.dialog.EditTransactionDialog
import com.cobasendiri.kasirmudah.ui.component.dialog.NegativeConfirmDialog
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTypography
import com.cobasendiri.kasirmudah.ui.theme.Negative
import com.cobasendiri.kasirmudah.ui.theme.OnPrimary
import com.cobasendiri.kasirmudah.ui.theme.Secondary
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.theme.TertiaryVariant
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import kotlinx.coroutines.delay

@Composable
fun TransactionHistoryScreen(
    innerPadding: PaddingValues,
    onNavigateToDetail: (String) -> Unit
){
    val context = LocalContext.current
    val appContext = context.applicationContext

    val viewModel: TransactionHistoryViewModel = viewModel(
        factory = ViewModelFactory.getInstance(appContext)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L)
            viewModel.uiMessageShown()
        }
    }

    TransactionHistoryContent(
        innerPadding,
        state
    ){ event ->
        when(event){
            is TransactionHistoryEvent.OnUpdateBookmark ->{
                viewModel.updateBookmark(event.transactionId)
            }
            is TransactionHistoryEvent.OnDelete ->{
                viewModel.deleteTransaction(event.transactionId)
            }
            is TransactionHistoryEvent.OnShowConfirmDeleteDialog ->{
                viewModel.showConfirmDeleteDialog(event.transactionId)
            }
            is TransactionHistoryEvent.OnDismissConfirmDeleteDialog -> {
                viewModel.dismissConfirmDeleteDialog()
            }
            is TransactionHistoryEvent.OnFilterChange ->{
                viewModel.updateFilter(event.newFilter)
            }
            is TransactionHistoryEvent.OnNavigateToDetail ->{
                onNavigateToDetail.invoke(event.transactionId)
            }
            is TransactionHistoryEvent.OnShowEditDialog ->{
                viewModel.showEditDialog(event.transactionId, event.name)
            }
            is TransactionHistoryEvent.OnDismissEditDialog ->{
                viewModel.dismissEditDialog()
            }
            is TransactionHistoryEvent.OnUpdateName ->{
                viewModel.updateTransactionName(event.transactionId, event.updatedName)
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
    val context = LocalContext.current

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

    BoxWithConstraints(Modifier
        .fillMaxSize()
        .background(Surface)
    ) {
        val availableHeight = maxHeight

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
                            text = stringResource(R.string.today),
                            filter = TransactionFilter.FILTER_TODAY,
                            isSelected = state.filter == TransactionFilter.FILTER_TODAY
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
            if(state.showEmptyListView){
                item{
                    Column(Modifier.height(availableHeight*0.7f)
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val titleRes = if(state.filter == TransactionFilter.FILTER_BOOKMARKED) {
                            R.string.bookmark_empty_title
                        }else{
                            R.string.history_empty_title
                        }
                        val subTitleRes = if(state.filter == TransactionFilter.FILTER_BOOKMARKED) {
                            R.string.bookmark_empty_subtitle
                        }else{
                            R.string.history_empty_subtitle
                        }
                        Text(
                            text = stringResource(titleRes),
                            style = KasirMudahTypography.titleMedium
                        )
                        Text(
                            text = stringResource(subTitleRes),
                            style = KasirMudahTypography.bodyMedium
                        )
                    }
                }
            }
            items(
                items = state.transactionList,
                key = { transactionItemState -> transactionItemState.id }
            ) { item ->
                Spacer(Modifier.height(16.dp))
                TransactionHistoryItem(
                    Modifier.padding(horizontal = 16.dp),
                    state = item,
                    onUpdateBookmark = {
                        event.invoke(TransactionHistoryEvent.OnUpdateBookmark(it))
                    },
                    onDelete = {
                        event.invoke(TransactionHistoryEvent.OnShowConfirmDeleteDialog(it))
                    },
                    onItemClick = {
                        event.invoke(TransactionHistoryEvent.OnNavigateToDetail(it))
                    },
                    onEditClick = { transactionId, name ->
                        event.invoke(TransactionHistoryEvent.OnShowEditDialog(transactionId, name))
                    }
                )
            }
            item {
                Spacer(Modifier.height(innerPadding.calculateBottomPadding() + 12.dp))
            }
        }
        if(state.showEditDialog != null){
            val dismissEvent = TransactionHistoryEvent.OnDismissEditDialog
            val transactionId = state.showEditDialog.first
            val name = state.showEditDialog.second
            EditTransactionDialog(
                name = name,
                onDismiss = { event.invoke(dismissEvent) },
                onCancel = { event.invoke(dismissEvent) },
                onSave = { updatedName ->
                    event.invoke(TransactionHistoryEvent.OnUpdateName(transactionId, updatedName))
                }
            )
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
                }
            )
        }
        AlertBarAnimatedVisibility(state.uiMessage != null) {
            state.uiMessage?.let {
                UiMessageBar(it)
            }
        }
    }
}