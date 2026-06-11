package com.cobasendiri.kasirmudah.ui.receipt.detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.component.ReceiptTopBar

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

    Scaffold(
        topBar = {
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