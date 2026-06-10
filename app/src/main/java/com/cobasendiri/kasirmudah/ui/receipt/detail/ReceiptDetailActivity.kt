package com.cobasendiri.kasirmudah.ui.receipt.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudah.domain.model.TransactionItemInfo
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class ReceiptDetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
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

            KasirMudahTheme {
                ReceiptDetailContent(dummyState) { event ->
                    when (event) {
                        is ReceiptDetailEvent.OnShowConfirmDeleteDialog -> {
                        }

                        is ReceiptDetailEvent.OnDismissConfirmDeleteDialog -> {
                        }
                    }
                }
            }
        }
    }

    fun generateDummyReceipt() : TransactionReceipt{
        return TransactionReceipt(
            id = "4shisefhw48t4",
            createdAt = "12 Agustus 2026 - 12:53:01",
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
}