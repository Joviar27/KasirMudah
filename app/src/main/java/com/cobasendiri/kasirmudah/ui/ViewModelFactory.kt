package com.cobasendiri.kasirmudah.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cobasendiri.kasirmudah.di.Injection
import com.cobasendiri.kasirmudah.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DeleteProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateProductColorCodeUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateProductUseCase
import com.cobasendiri.kasirmudah.ui.shop.ShopViewModel

class ViewModelFactory(
    private val getProductLisUseCase: GetProductLisUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val updateProductColorCodeUseCase: UpdateProductColorCodeUseCase,
    private val getTotalCartAmountUseCase: GetTotalCartAmountUseCase,
    private val incrementProductUseCase: IncrementProductUseCase,
    private val decrementProductUseCase: DecrementProductUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getCartListUseCase: GetCartListUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
) : ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideGetProductListUseCase(context),
                    Injection.provideAddProductUseCase(context),
                    Injection.provideUpdateProductUseCase(context),
                    Injection.provideUpdateProductColorCodeUseCase(context),
                    Injection.provideGetTotalCartAmountUseCase(context),
                    Injection.provideIncrementProductUseCase(context),
                    Injection.provideDecrementProductUseCase(context),
                    Injection.provideClearCartUseCase(context),
                    Injection.provideGetCartListUseCase(context),
                    Injection.provideDeleteProductUseCase(context)
                ).also { instance = it }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
            return ShopViewModel(getProductLisUseCase, getCartListUseCase, addProductUseCase,
                updateProductUseCase, updateProductColorCodeUseCase, getTotalCartAmountUseCase,
                incrementProductUseCase, decrementProductUseCase, clearCartUseCase,
                deleteProductUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}