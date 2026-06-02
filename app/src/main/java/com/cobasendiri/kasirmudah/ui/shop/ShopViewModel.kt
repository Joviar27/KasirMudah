package com.cobasendiri.kasirmudah.ui.shop

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.UiMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShopViewModel(
    private val getProductLisUseCase: GetProductLisUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val incrementProductUseCase: IncrementProductUseCase,
    private val decrementProductUseCase: DecrementProductUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> get() = _state

    init {
        loadProductList("")
        getTotalCartAmount()
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

    private fun getTotalCartAmount(){
        viewModelScope.launch {
            getTotalCartAmountUseCase.invoke().collect { result ->
                result.handleResult {
                    _state.value = state.value.copy(
                        totalAmount = it ?: 0L,
                        isFloatingActionVisible = it != null && it > 0L
                    )
                }
            }
        }
    }

    fun incrementProduct(productId: String){
        viewModelScope.launch {
            incrementProductUseCase.invoke(productId).handleResult()
        }
    }

    fun decrementProduct(productId: String){
        viewModelScope.launch {
            decrementProductUseCase.invoke(productId).handleResult()
        }
    }

    fun addNewProduct(productDraft: ProductDraft){
        viewModelScope.launch {
            addProductUseCase.invoke(productDraft).handleResult {
                dismissProductDetailDialog()
            }
        }
    }

    fun showAddProductDialog(){
        _state.value = state.value.copy(
            showAddProductDialog = true
        )
    }

    fun dismissProductDetailDialog(){
        _state.value = state.value.copy(
            showAddProductDialog = false,
            showEditProductDialog = null
        )
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