package com.cobasendiri.kasirmudah.ui.receipt.draft

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.SaveNewTransactionUseCase
import com.cobasendiri.kasirmudah.nav.Screen
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiptDraftViewModel(
    private val getReceiptItemsUseCase: GetReceiptItemsUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val saveNewTransactionUseCase: SaveNewTransactionUseCase,
    private val clearCartUseCase: ClearCartUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ReceiptDraftState())
    val state: StateFlow<ReceiptDraftState> get() = _state

    private val _navigateEvent = Channel<Screen>()
    val navigateEvent = _navigateEvent.receiveAsFlow()

    init {
        getReceiptItems()
        getReceiptTotalAmount()
    }

    private fun getReceiptItems(){
        viewModelScope.launch {
            getReceiptItemsUseCase.invoke().handleResult{ items ->
                _state.update { it.copy(transactionShopItems = items) }
            }
        }
    }

    private fun getReceiptTotalAmount(){
        viewModelScope.launch {
            getTotalCartAmountUseCase.invoke().firstOrNull()?.handleResult{ total ->
                _state.update { it.copy(totalTransaction = total ?: 0) }
            }
        }
    }

    fun saveNewTransaction(){
        viewModelScope.launch {
            saveNewTransactionUseCase.invoke().handleResult{
                _navigateEvent.trySend(Screen.History)
                clearCart()
            }
        }
    }

    private fun clearCart(){
        viewModelScope.launch {
            clearCartUseCase.invoke()
        }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }
}
