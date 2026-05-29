package com.cobasendiri.kasirmudah.ui.shop

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopViewModel(
    private val getProductLisUseCase: GetProductLisUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> get() = _state

    init {
        loadProductList("")
    }

    fun loadProductList(newQuery: String){
        _state.value = state.value.copy(
            searchQuery = newQuery
        )
        viewModelScope.launch {
            getProductLisUseCase.invoke(newQuery).collect { result ->
                result.handleResult {
                    _state.value = state.value.copy(shopItemList = it)
                }
            }
        }
    }

    override fun showErrorMessage(errorMessage: UiMessage) {
        _state.value = state.value.copy(
            errorMessage = errorMessage
        )
    }

    override fun errorMessageShown() {
        _state.value = state.value.copy(
            errorMessage = null
        )
    }
}