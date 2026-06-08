package com.cobasendiri.kasirmudah.ui.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val getReceiptItemsUseCase: GetReceiptItemsUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ReceiptState())
    val state: StateFlow<ReceiptState> get() = _state

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
}
