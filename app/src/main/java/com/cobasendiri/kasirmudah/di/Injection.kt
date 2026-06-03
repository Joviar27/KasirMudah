package com.cobasendiri.kasirmudah.di

import android.content.Context
import com.cobasendiri.kasirmudah.data.CartRepository
import com.cobasendiri.kasirmudah.data.ProductRepository
import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.data.room.ShopDatabase
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import com.cobasendiri.kasirmudah.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.IncrementProductUseCase

object Injection {

    private fun provideProductDao(context: Context): ProductDao{
        return ShopDatabase.getDatabase(context).productDao()
    }

    private fun provideCartDao(context: Context): CartDao{
        return ShopDatabase.getDatabase(context).cartDao()
    }

    private fun provideProductRepository(context: Context): IProductRepository{
        return ProductRepository.getInstance(provideProductDao(context))
    }

    private fun provideCartRepository(context: Context): ICartRepository{
        return CartRepository.getInstance(provideCartDao(context))
    }

    fun provideGetProductListUseCase(context: Context): GetProductLisUseCase{
        return GetProductLisUseCase(provideProductRepository(context))
    }

    fun provideGetCartListUseCase(context: Context): GetCartListUseCase{
        return GetCartListUseCase(provideCartRepository(context))
    }

    fun provideAddProductUseCase(context: Context): AddProductUseCase{
        return AddProductUseCase(provideProductRepository(context))
    }

    fun provideGetTotalCartAmountUseCase(context: Context): GetTotalCartAmountUseCase{
        return GetTotalCartAmountUseCase(provideCartRepository(context))
    }

    fun provideIncrementProductUseCase(context: Context): IncrementProductUseCase{
        return IncrementProductUseCase(provideCartRepository(context))
    }

    fun provideDecrementProductUseCase(context: Context): DecrementProductUseCase{
        return DecrementProductUseCase(provideCartRepository(context))
    }

    fun provideClearCartUseCase(context: Context): ClearCartUseCase{
        return ClearCartUseCase(provideCartRepository(context))
    }
}