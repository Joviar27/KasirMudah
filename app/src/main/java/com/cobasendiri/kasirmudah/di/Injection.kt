package com.cobasendiri.kasirmudah.di

import android.content.Context
import com.cobasendiri.kasirmudah.data.ProductRepository
import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.data.room.ShopDatabase
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase

object Injection {

    private fun provideProductDao(context: Context): ProductDao{
        return ShopDatabase.getDatabase(context).productDao()
    }

    private fun provideProductRepository(context: Context): IProductRepository{
        return ProductRepository.getInstance(provideProductDao(context))
    }

    fun provideGetProductListUseCase(context: Context): GetProductLisUseCase{
        return GetProductLisUseCase(provideProductRepository(context))
    }
}