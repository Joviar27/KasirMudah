package com.cobasendiri.kasirmudah.ui.receipt.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.component.ReceiptTopBar
import com.cobasendiri.kasirmudah.ui.component.UiMessageBar
import com.cobasendiri.kasirmudah.ui.theme.Negative
import com.cobasendiri.kasirmudah.ui.theme.Primary
import com.cobasendiri.kasirmudah.ui.theme.Tertiary
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import kotlinx.coroutines.delay

@Composable
fun ReceiptDetailScreen(
    transactionId: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext

    val viewmodel: ReceiptDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(applicationContext)
    )

    val state by viewmodel.state.collectAsStateWithLifecycle()

    LaunchedEffect(transactionId) {
        viewmodel.getTransaction(transactionId)
        viewmodel.getIsBookmarked(transactionId)
    }

    LaunchedEffect(Unit) {
        viewmodel.navigateBackEvent.collect {
            onNavigateBack.invoke()
        }
    }

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L)
            viewmodel.uiMessageShown()
        }
    }

    Scaffold(
        topBar = {
            Box{
                ReceiptTopBar(
                    showMenuIcon = true,
                    isBookmarked = state.isBookmarked,
                    onUpdateBookmark = {
                        viewmodel.updateBookmark(state.transactionId)
                    },
                    onDelete = {
                        viewmodel.showConfirmDeleteDialog(state.transactionId)
                    },
                    onNavigateBack = onNavigateBack
                )
                AnimatedVisibility(
                    visible = state.uiMessage != null,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                    modifier = Modifier
                        .statusBarsPadding()
                        .align(Alignment.TopCenter)
                ) {
                    val icon = when(state.uiMessage?.type){
                        UiMessageType.SUCCESS -> painterResource(R.drawable.ic_check_24_white)
                        UiMessageType.ERROR -> painterResource(R.drawable.ic_error_24_white)
                        else -> painterResource(R.drawable.ic_info_outline_24_white)
                    }
                    val color = when(state.uiMessage?.type){
                        UiMessageType.SUCCESS -> Primary
                        UiMessageType.ERROR -> Negative
                        else -> Tertiary
                    }
                    UiMessageBar(
                        imageStart = icon,
                        imageBackground = color,
                        message = state.uiMessage?.asString(context) ?: ""
                    )
                }
            }
        }
    ) { innerPadding ->
        ReceiptDetailContent(
            innerPadding,
            state
        ) { event ->
            when (event) {
                is ReceiptDetailEvent.OnDismissConfirmDeleteDialog -> {
                    viewmodel.dismissConfirmDeleteDialog()
                }
                is ReceiptDetailEvent.OnDelete ->{
                    viewmodel.deleteTransaction(event.transactionId)
                }
                is ReceiptDetailEvent.OnDownload ->{
                    //Download receipt
                }
            }
        }
    }
}

@Preview
@Composable
fun ReceiptDetailScreenPrev() {
    ReceiptDetailScreen(""){}
}