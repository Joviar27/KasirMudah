package com.cobasendiri.kasirmudah.ui.receipt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cobasendiri.kasirmudah.domain.model.TransactionReceipt
import com.cobasendiri.kasirmudah.domain.model.TransactionReceiptItem
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class ReceiptActivity: ComponentActivity() {

    private val viewModel: ReceiptViewModel by viewModels {
        ViewModelFactory.getInstance(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            KasirMudahTheme {
                ReceiptContent(state){ event ->
                    when(event){
                        is ReceiptEvent.OnShowConfirmDeleteDialog -> {
                        }
                        is ReceiptEvent.OnDismissConfirmDeleteDialog -> {
                        }
                    }
                }
            }
        }
    }
}