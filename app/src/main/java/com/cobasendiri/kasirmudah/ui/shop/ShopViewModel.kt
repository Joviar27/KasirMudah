package com.cobasendiri.kasirmudah.ui.shop

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.model.Product
import com.cobasendiri.kasirmudah.domain.model.ProductDraft
import com.cobasendiri.kasirmudah.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateProductUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import com.cobasendiri.kasirmudah.ui.UiMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShopViewModel(
    private val getProductLisUseCase: GetProductLisUseCase,
    private val getCartListUseCase: GetCartListUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val incrementProductUseCase: IncrementProductUseCase,
    private val decrementProductUseCase: DecrementProductUseCase,
    private val clearCartUseCase: ClearCartUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ShopState())
    val state: StateFlow<ShopState> get() = _state

    init {
        loadProductList()
        getTotalCartAmount()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateQuery(newQuery: String) {
        _state.update {
            it.copy(searchQuery = newQuery)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateFilter(newFilter: ShopFilter) {
        _state.update {
            it.copy(filter = newFilter)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadProductList(){
        viewModelScope.launch {
            _state.map { Pair(it.searchQuery, it.filter) }
                .distinctUntilChanged()
                .flatMapLatest { condition ->
                    if(condition.second == ShopFilter.FILTER_CART){
                        getCartListUseCase.invoke(condition.first)
                    }else{
                        getProductLisUseCase.invoke(condition.first)
                    }
                }.collect { result ->
                    result.handleResult { products ->
                        _state.update { it.copy(shopItemList = products) }
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

    fun clearCart(){
        viewModelScope.launch {
            clearCartUseCase.invoke().handleResult()
        }
    }

    fun addNewProduct(productDraft: ProductDraft){
        viewModelScope.launch {
            addProductUseCase.invoke(productDraft).handleResult {
                dismissProductDetailDialog()
            }
        }
    }

    fun updateProduct(productDraft: ProductDraft){
        viewModelScope.launch {
            updateProductUseCase.invoke(productDraft).handleResult{
                dismissProductDetailDialog()
            }
        }
    }

    fun showAddProductDialog(){
        _state.update {
            it.copy(showAddProductDialog = true)
        }
    }

    fun showEditProductDialog(product: Product){
        _state.update {
            it.copy(showEditProductDialog = product)
        }
    }

    fun dismissProductDetailDialog(){
        _state.update {
            it.copy(
                showAddProductDialog = false,
                showEditProductDialog = null
            )
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