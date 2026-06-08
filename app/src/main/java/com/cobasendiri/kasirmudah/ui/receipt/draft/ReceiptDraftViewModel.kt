package com.cobasendiri.kasirmudah.ui.receipt.draft

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiptDraftViewModel(
    private val getReceiptItemsUseCase: GetReceiptItemsUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ReceiptDraftState())
    val state: StateFlow<ReceiptDraftState> get() = _state

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
