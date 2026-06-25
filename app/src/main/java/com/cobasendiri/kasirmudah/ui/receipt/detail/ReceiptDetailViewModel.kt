package com.cobasendiri.kasirmudah.ui.receipt.detail

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetIsTransactionBookmarkedUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudah.utils.GallerySaver
import com.cobasendiri.kasirmudah.ui.utils.UiMessageUtil.asUiMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class ReceiptDetailViewModel(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getIsTransactionBookmarkedUseCase: GetIsTransactionBookmarkedUseCase,
    private val updateTransactionBookmarkUseCase: UpdateTransactionBookmarkUseCase,
    private val deleteTransactionHistoryUseCase: DeleteTransactionHistoryUseCase,
    private val gallerySaver: GallerySaver
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
                        transactionName = receipt.name,
                        transactionCreatedAt = receipt.createdAt,
                        transactionShopItems = receipt.shopItems,
                        totalTransaction = receipt.transactionTotal,
                        shopName = receipt.shopName,
                        processing = false
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

    fun downloadReceipt(bitmap: Bitmap, filename: String) {
        viewModelScope.launch {
            _state.update { it.copy(processing = true) }

            val successSaveReceipt = gallerySaver.saveBitmapToGallery(bitmap, filename)
            val uiMessage = if(successSaveReceipt){
                R.string.success_save_receipt.asUiMessage(UiMessageType.SUCCESS)
            }else{
                R.string.error_save_receipt.asUiMessage(UiMessageType.ERROR)
            }
            showUiMessage(uiMessage)

            //Avoid multiple download
            _state.update {
                delay(500)
                it.copy(processing = false)
            }
        }
    }

    override fun showUiMessage(message: UiMessage) {
        _state.update { it.copy(uiMessage = message) }
    }

    override fun uiMessageShown() {
        _state.update { it.copy(uiMessage = null) }
    }
}