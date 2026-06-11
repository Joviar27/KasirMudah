package com.cobasendiri.kasirmudah.ui.receipt.detail

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudah.ui.component.ReceiptTopBar

@Composable
fun ReceiptDetailScreen(
    onNavigateBack: () -> Unit
) {
    val dummyReceipt = remember { generateDummyReceipt() }

    var dummyState by remember {
        mutableStateOf(
            ReceiptDetailState(
                shopName = "Toko Madura A",
                transactionCreatedAt = dummyReceipt.createdAt,
                transactionId = dummyReceipt.id,
                transactionShopItems = dummyReceipt.shopItems,
                totalTransaction = dummyReceipt.transactionTotal
            )
        )
    }

    Scaffold(
        topBar = {
            ReceiptTopBar(
                showMenuIcon = true,
                onUpdateBookmark = {
                },
                onDelete = {
                },
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        ReceiptDetailContent(
            innerPadding,
            dummyState
        ) { event ->
            when (event) {
                is ReceiptDetailEvent.OnShowConfirmDeleteDialog -> {
                }

                is ReceiptDetailEvent.OnDismissConfirmDeleteDialog -> {
                }
            }
        }
    }
}

@Preview
@Composable
fun ReceiptDetailScreenPrev() {
    ReceiptDetailScreen{}
}

fun generateDummyReceipt() : TransactionReceipt{
    return TransactionReceipt(
        id = "4shisefhw48t4",
        createdAt = 1755388800,
        shopItems = MutableList(6){
            TransactionItemInfo(
                name = "Barang Nomor $it",
                itemTotal = 980000,
                count = 5
            )
        },
        transactionTotal = 1500225
    )
}