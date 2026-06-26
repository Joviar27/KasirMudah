package com.cobasendiri.kasirmudah.ui.receipt.draft 
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cobasendiri.kasirmudah.ui.ViewModelFactory
import com.cobasendiri.kasirmudah.ui.animation.AlertBarAnimatedVisibility
import com.cobasendiri.kasirmudah.ui.component.ReceiptTopBar
import com.cobasendiri.kasirmudah.ui.component.alertbar.UiMessageBar
import kotlinx.coroutines.delay

@Composable
fun ReceiptDraftScreen(
    onNavigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val appContext = context.applicationContext

    val viewModel: ReceiptDraftViewModel = viewModel(
        factory = ViewModelFactory.getInstance(appContext)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack.invoke()
        }
    }

    state.uiMessage?.let { uiMessage ->
        LaunchedEffect(uiMessage.getMessageId()) {
            delay(3000L)
            viewModel.uiMessageShown()
        }
    }

    Scaffold(
        topBar = {
            Box {
                ReceiptTopBar(
                    showMenuIcon = false,
                    onNavigateBack = onNavigateBack
                )
                AlertBarAnimatedVisibility(state.uiMessage != null) {
                    state.uiMessage?.let {
                        UiMessageBar(it)
                    }
                }
            }
        }
    ) { innerPadding ->
        ReceiptDraftContent(
            innerPadding = innerPadding,
            state = state
        ){ event ->
            when(event){
                is ReceiptDraftEvent.OnNavigateBack ->{
                    onNavigateBack.invoke()
                }
                is ReceiptDraftEvent.OnSave -> {
                    viewModel.saveNewTransaction()
                }
            }
        }
    }
}

@Preview
@Composable
fun ReceiptDraftScreenPrev(){
    ReceiptDraftScreen{}
}