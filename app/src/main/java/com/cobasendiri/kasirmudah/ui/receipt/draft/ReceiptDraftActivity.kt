package com.cobasendiri.kasirmudah.ui.receipt.draft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.theme.KasirMudahTheme

class ReceiptDraftActivity: ComponentActivity() {

    private val viewModel: ReceiptDraftViewModel by viewModels {
        ViewModelFactory.getInstance(this.applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            KasirMudahTheme {
                ReceiptDraftContent(state){ event ->
                }
            }
        }
    }
}