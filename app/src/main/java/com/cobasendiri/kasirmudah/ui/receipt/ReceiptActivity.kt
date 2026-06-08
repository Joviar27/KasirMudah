package com.cobasendiri.kasirmudah.ui.receipt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class ReceiptActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val dummyReceipt = remember { generateDummyReceipt() }

            var dummyState by remember {
                mutableStateOf(
                    ReceiptState(
                        previewMode = true,
                        shopName = "Toko Madura A",
                        transactionCreatedAt = dummyReceipt.createdAt,
                        transactionId = dummyReceipt.id,
                        transactionShopItems = dummyReceipt.shopItems,
                        totalTransaction = dummyReceipt.totalTransaction
                    )
                )
            }

            KasirMudahTheme {
                ReceiptContent(dummyState){ event ->
                    when(event){
                        is ReceiptEvent.OnShowConfirmDeleteDialog -> {
                            dummyState = dummyState.copy(
                                showConfirmDeleteDialog = event.transactionId
                            )
                        }
                        is ReceiptEvent.OnDismissConfirmDeleteDialog -> {
                            dummyState = dummyState.copy(
                                showConfirmDeleteDialog = null
                            )
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
                TransactionReceiptItem(
                    name = "Barang Nomor $it",
                    totalAmount = 980000,
                    count = 5
                )
            },
            totalTransaction = "Rp 1.500.255,00"
        )
    }
}