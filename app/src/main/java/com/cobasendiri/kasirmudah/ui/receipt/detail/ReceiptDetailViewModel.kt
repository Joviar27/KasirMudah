package com.cobasendiri.kasirmudah.ui.receipt.detail

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetIsTransactionBookmarkedUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudah.ui.utils.UiMessageUtil.asUiMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiptDetailViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getIsTransactionBookmarkedUseCase: GetIsTransactionBookmarkedUseCase,
    private val updateTransactionBookmarkUseCase: UpdateTransactionBookmarkUseCase,
    private val deleteTransactionHistoryUseCase: DeleteTransactionHistoryUseCase,
    private val getShopProfileUseCase: GetShopProfileUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ReceiptDetailState())
    val state: StateFlow<ReceiptDetailState> get() = _state

    private val _navigateBackEvent = Channel<Unit>()
    val navigateBackEvent = _navigateBackEvent.receiveAsFlow()

    fun getTransaction(transactionId: String){
        viewModelScope.launch {
            getTransactionUseCase.invoke(transactionId).handleResult{ receipt ->
                _state.update {
                    it.copy(
                        transactionId = receipt.id,
                        transactionCreatedAt = receipt.createdAt,
                        transactionShopItems = receipt.shopItems,
                        totalTransaction = receipt.transactionTotal,
                        shopName = receipt.shopName
                    )
                }
            }
        }
    }

    fun getIsBookmarked(transactionId: String){
        viewModelScope.launch {
            getIsTransactionBookmarkedUseCase.invoke(transactionId).collect {
                it.handleResult{ isBookmarked ->
                    _state.update { it.copy(isBookmarked = isBookmarked) }
                }
            }
        }
    }

    fun updateBookmark(transactionId: String){
        viewModelScope.launch {
            updateTransactionBookmarkUseCase.invoke(transactionId).handleResult{ isBookmarked ->
                val stringRes = if(isBookmarked) {
                    R.string.success_new_bookmark
                } else {
                    R.string.success_remove_bookmark
                }
                showUiMessage(stringRes.asUiMessage(UiMessageType.SUCCESS))
            }
        }
    }

    fun deleteTransaction(transactionId: String){
        viewModelScope.launch {
            deleteTransactionHistoryUseCase.invoke(transactionId).handleResult{
                _navigateBackEvent.trySend(Unit)
            }
        }
    }

    fun showConfirmDeleteDialog(transactionId: String){
        _state.update { it.copy(showConfirmDeleteDialog = transactionId) }
    }

    fun dismissConfirmDeleteDialog(){
        _state.update { it.copy(showConfirmDeleteDialog = null) }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }
}