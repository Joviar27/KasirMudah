package com.cobasendiri.kasirmudah.di

import android.content.Context
import com.cobasendiri.kasirmudah.data.CartRepository
import com.cobasendiri.kasirmudah.data.ProductRepository
import com.cobasendiri.kasirmudah.data.ProfileRepository
import com.cobasendiri.kasirmudah.data.TransactionRepository
import com.cobasendiri.kasirmudah.data.datastore.DataStoreManager
import com.cobasendiri.kasirmudah.data.room.CartDao
import com.cobasendiri.kasirmudah.data.room.ProductDao
import com.cobasendiri.kasirmudah.data.room.ShopDatabase
import com.cobasendiri.kasirmudah.data.room.TransactionDao
import com.cobasendiri.kasirmudah.domain.repository.ICartRepository
import com.cobasendiri.kasirmudah.domain.repository.IProductRepository
import com.cobasendiri.kasirmudah.domain.repository.IProfileRepository
import com.cobasendiri.kasirmudah.domain.repository.ITransactionRepository
import com.cobasendiri.kasirmudah.domain.usecase.AddProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.ClearCartUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DecrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DeleteProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.DeleteTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetBookmarkedTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetCartListUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetIsTransactionBookmarkedUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetProductLisUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetReceiptItemsUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTotalCartAmountUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTransactionHistoryUseCase
import com.cobasendiri.kasirmudah.domain.usecase.GetTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.IncrementProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.AddTransactionUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateShopProfileUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateProductColorCodeUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateProductUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateTransactionBookmarkUseCase
import com.cobasendiri.kasirmudah.domain.usecase.UpdateTransactionNameUseCase
import com.cobasendiri.kasirmudah.utils.GallerySaver

object Injection {

    private fun provideProductDao(context: Context): ProductDao{
        return ShopDatabase.getDatabase(context).productDao()
    }

    private fun provideCartDao(context: Context): CartDao{
        return ShopDatabase.getDatabase(context).cartDao()
    }

    private fun provideTransactionDao(context: Context): TransactionDao{
        return ShopDatabase.getDatabase(context).transactionDao()
    }

    private fun provideDataStoreManager(context: Context): DataStoreManager{
        return DataStoreManager.getInstance(context)
    }

    private fun provideProductRepository(context: Context): IProductRepository{
        return ProductRepository.getInstance(provideProductDao(context))
    }

    private fun provideCartRepository(context: Context): ICartRepository{
        return CartRepository.getInstance(provideCartDao(context))
    }

    private fun provideTransactionRepository(context: Context): ITransactionRepository{
        return TransactionRepository.getInstance(provideTransactionDao(context))
    }

    private fun provideProfileRepository(context: Context): IProfileRepository{
        return ProfileRepository.getInstance(provideDataStoreManager(context))
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

    fun provideUpdateProductUseCase(context: Context): UpdateProductUseCase{
        return UpdateProductUseCase(provideProductRepository(context))
    }

    fun provideUpdateProductColorCodeUseCase(context: Context): UpdateProductColorCodeUseCase{
        return UpdateProductColorCodeUseCase(provideProductRepository(context))
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

    fun provideDeleteProductUseCase(context: Context): DeleteProductUseCase{
        return DeleteProductUseCase(provideProductRepository(context))
    }

    fun provideGetReceiptItemsUseCase(context: Context): GetReceiptItemsUseCase{
        return GetReceiptItemsUseCase(provideCartRepository(context))
    }

    fun provideAddTransactionUseCase(context: Context): AddTransactionUseCase{
        return AddTransactionUseCase(
            provideTransactionRepository(context),
            provideCartRepository(context),
            provideProfileRepository(context)
        )
    }

    fun provideGetTransactionHistoryUseCase(context: Context): GetTransactionHistoryUseCase{
        return GetTransactionHistoryUseCase(provideTransactionRepository(context))
    }

    fun provideGetBookmarkedTransactionUseCase(context: Context): GetBookmarkedTransactionUseCase{
        return GetBookmarkedTransactionUseCase(provideTransactionRepository(context))
    }

    fun provideUpdateTransactionBookmarkUseCase(context: Context): UpdateTransactionBookmarkUseCase{
        return UpdateTransactionBookmarkUseCase(provideTransactionRepository(context))
    }

    fun provideDeleteTransactionHistoryUseCase(context: Context): DeleteTransactionHistoryUseCase{
        return DeleteTransactionHistoryUseCase(provideTransactionRepository(context))
    }

    fun provideUpdateTransactionNameUseCase(context: Context): UpdateTransactionNameUseCase{
        return UpdateTransactionNameUseCase(provideTransactionRepository(context))
    }

    fun provideGetTransactionUseCase(context: Context): GetTransactionUseCase{
        return GetTransactionUseCase(provideTransactionRepository(context))
    }

    fun provideGetIsTransactionBookmarkedUseCase(context: Context): GetIsTransactionBookmarkedUseCase{
        return GetIsTransactionBookmarkedUseCase(provideTransactionRepository(context))
    }

    fun provideGetShopProfileUseCase(context: Context): GetShopProfileUseCase{
        return GetShopProfileUseCase(provideProfileRepository(context))
    }

    fun provideUpdateShopProfileUseCase(context: Context): UpdateShopProfileUseCase{
        return UpdateShopProfileUseCase(provideProfileRepository(context))
    }

    fun provideGallerySaver(context: Context): GallerySaver{
        return GallerySaver(context)
    }
}