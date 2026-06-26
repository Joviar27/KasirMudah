package com.cobasendiri.kasirmudah.ui.receipt.draft

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.AddTransactionUseCase
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
    private val addTransactionUseCase: AddTransactionUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getShopProfileUseCase: GetShopProfileUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ReceiptDraftState())
    val state: StateFlow<ReceiptDraftState> get() = _state

    private val _navigateBackEvent = Channel<Unit>()
    val navigateBackEvent = _navigateBackEvent.receiveAsFlow()

    init {
        getReceiptItems()
        getReceiptTotalAmount()
        getShopProfile()
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

    private fun getShopProfile(){
        viewModelScope.launch {
            getShopProfileUseCase.invoke().collect {
                it.handleResult{ shopProfile ->
                    _state.update {
                        it.copy(shopName = shopProfile.shopName)
                    }
                }
            }
        }
    }

    fun saveNewTransaction(){
        viewModelScope.launch {
            addTransactionUseCase.invoke().handleResult{
                _navigateBackEvent.trySend(Unit)
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
