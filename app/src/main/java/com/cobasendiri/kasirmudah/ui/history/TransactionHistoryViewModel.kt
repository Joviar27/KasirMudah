package com.cobasendiri.kasirmudah.ui.history

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.R
import com.cobasendiri.kasirmudah.domain.filter.DateFilter
import com.cobasendiri.kasirmudah.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetBookmarkedTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessageType
import com.cobasendiri.kasirmudah.ui.utils.UiMessageUtil.asUiMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel(
    private val getTransactionHistoryUseCase: GetTransactionHistoryUseCase,
    private val getBookmarkedTransactionUseCase: GetBookmarkedTransactionUseCase,
    private val updateTransactionBookmarkUseCase: UpdateTransactionBookmarkUseCase,
    private val deleteTransactionHistoryUseCase: DeleteTransactionHistoryUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(TransactionHistoryState())
    val state: StateFlow<TransactionHistoryState> get() = _state

    init {
        getTransactionHistory()
    }

    fun updateFilter(newFilter: TransactionFilter) {
        _state.update {
            it.copy(filter = newFilter)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTransactionHistory(){
        viewModelScope.launch {
            _state.map { it.filter }
                .flatMapLatest { filter ->
                    if(filter == TransactionFilter.FILTER_BOOKMARKED){
                        getBookmarkedTransactionUseCase.invoke()
                    }else{
                        getTransactionHistoryUseCase.invoke(mapFilter(filter))
                    }
                }.collect {
                    it.handleResult{ transactions ->
                        _state.update { it.copy(transactionList = transactions) }
                    }
                }
        }
    }

    fun deleteTransaction(transactionId: String){
        viewModelScope.launch {
            deleteTransactionHistoryUseCase.invoke(transactionId).handleResult{
                showUiMessage(R.string.success_delete_transaction.asUiMessage(UiMessageType.SUCCESS))
                dismissConfirmDeleteDialog()
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

    private fun mapFilter(transactionFilter: TransactionFilter): DateFilter{
        return when(transactionFilter){
            TransactionFilter.FILTER_TODAY -> DateFilter.TODAY
            TransactionFilter.FILTER_LAST_WEEK -> DateFilter.LAST_WEEK
            TransactionFilter.FILTER_LAST_MONTH -> DateFilter.LAST_MONTH
            else -> DateFilter.ALL_TIME
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